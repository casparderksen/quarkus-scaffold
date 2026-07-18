# Transactional Outbox

## Problem

A command often has to do two things atomically: change aggregate state in the database, and publish an event that other components or contexts react to. The database and the message broker (or an in-process consumer) are separate concerns with no shared transaction. Publishing before the commit risks a phantom event when the transaction rolls back; committing before publishing risks a lost event when the process dies before the publish. There is no ordering of "commit the row" and "publish the message" that is safe on its own — this is the dual-write problem.

The transactional outbox removes the second system from the transaction. Instead of publishing during the command, the command records the event as a row in an **outbox table** in the *same* database transaction as the aggregate change. One database, one transaction, one atomic commit. A separate relay then reads outbox rows and delivers them, decoupled from the source transaction.

## The outbox row

The outbox table is shared infrastructure, owned by the shared kernel and migrated under `db/migration/shared/`. Each row carries:

| Column | Purpose |
|---|---|
| `aggregate_id` | routing and ordering key (same aggregate → same partition) |
| `seq` | per-aggregate sequence number, preserves FIFO |
| `type` | event type and major version (the CloudEvents `type`) |
| `payload_bytes` | serialized integration event, frozen at write time so later schema changes do not affect rows already written |
| `traceparent` | correlation context, replayed at dispatch |
| `status` | `PENDING` → `SENDING` → `SENT`, or `FAILED` |
| `attempts`, `last_error` | retry bookkeeping |
| `created_at`, `sent_at` | timestamps |

## Phase A — capture (inside the source transaction)

The command handler, within its own transaction, mutates exactly one aggregate, translates the emitted domain event into a versioned integration event, wraps it as a CloudEvent, validates it against the schema registry, and inserts the outbox row with status `PENDING`. The aggregate row and the outbox row commit together or not at all. See [Adapter Flows](adapter-flows.md) flow #7 Phase A for the full chain.

Because capture is a plain database insert in the source transaction, the event exists **if and only if** the state change committed. No dual write.

## Phase B — dispatch (the relay, a separate transaction)

A relay, outside the source transaction, reads `PENDING` rows in per-aggregate order and delivers each one, marking it `SENT` on success. On failure it retries with exponential backoff; after a bounded number of attempts the row becomes `FAILED` and is the dead-letter record for manual replay. The transport is either a broker (a Kafka producer keyed by `aggregate_id`, so a given aggregate's events keep their order through one partition) or, when the consumer is co-deployed, an in-process dispatch — see [In-JVM communication](#in-jvm-communication).

## Guarantees

- **Atomic capture** — the event is persisted with the state change or not at all.
- **At-least-once delivery** — the relay retries until a row is `SENT`; duplicates are possible, so consumers are idempotent (see [Idempotent Consumer](idempotent_consumer.md)).
- **Per-aggregate ordering** — `seq` plus ordered reads plus a stable partition key keep one aggregate's events in order.

## Status lifecycle

```
PENDING ──(relay claims)──▶ SENDING ──(delivered)──▶ SENT
                              │
                              └──(N failed attempts)──▶ FAILED
```

Claiming a row (`PENDING → SENDING` via a locked or compare-and-set update) before delivery stops two relays — or a relay and a sweeper — from dispatching the same row concurrently.

## In-JVM communication

When producer and consumer are co-deployed (the modular monolith), the relay may deliver in process rather than through a broker. Two things need care: how the row is marked `SENT`, and how to avoid the polling delay.

### Setting SENT

**At-least-once (default).** The consumer processes the event in its *own* new transaction and commits; the relay then updates the outbox row to `SENT`. If the process dies between the consumer's commit and the `SENT` update, the row is still `PENDING` and is redelivered; the idempotent consumer skips the duplicate. Deduplication is required.

**Exactly-once (single database only, optional).** Because the consumer's tables and the outbox table live in the same database, the relay may run the consumer's processing **and** the `SENT` update inside one local transaction. They commit together, so there is no redelivery window. This is available only while the consumer is co-deployed on one database: a broker, or an extracted service with its own database, cannot share a transaction with the outbox and reverts to at-least-once — so the consumer must stay idempotent regardless, and this mode is an optimization, never a correctness crutch. Writing the consumer's aggregate and the `SENT` flag in one transaction does not violate one-aggregate-per-transaction: the outbox row is infrastructure bookkeeping, not a second aggregate. It is symmetric with Phase A, where the producer already writes its aggregate and the outbox row in one transaction.

### Immediate dispatch (removing the polling delay)

A poller adds latency equal to its poll interval. To remove that on the happy path, trigger dispatch as soon as the source transaction commits, using a post-commit hook: a CDI transactional observer, `@Observes(during = TransactionPhase.AFTER_SUCCESS)` (or, lower level, a JTA `Synchronization.afterCompletion` acting on `STATUS_COMMITTED`). The observer runs after the commit and dispatches the just-written rows in a new transaction, marking them `SENT`.

The post-commit hook does **not** replace the outbox. On its own it is *at-most-once*: it runs in memory after the commit, so a crash between the commit and the observer — or a failure inside it — loses the event with no durable record that it was owed. Durability stays in the outbox row; the hook only accelerates delivery. The poller remains, but as a low-frequency **fallback sweeper** that picks up any `PENDING` rows the immediate dispatch missed. Steady state then has no poll delay, and the poll interval bounds only the crash-recovery tail.

Because the observer fires after the source transaction has closed, the immediate-dispatch consumer runs in its own new transaction — the at-least-once mode above, so the consumer deduplicates. The exactly-once single-transaction variant is only reachable on the sweeper path on one database.

The immediate dispatch and the sweeper must not both send a row: the immediate dispatch claims the row (`PENDING → SENDING`) before dispatching, and the sweeper ignores rows already `SENDING`. Deduplication covers any residual overlap.

## Extraction

The capture (Phase A), the outbox row, and the command handler are unchanged when a context is extracted to its own service. Only the relay's transport changes: the in-JVM dispatch (or the `AFTER_SUCCESS` publish) targets a Kafka producer instead of the co-deployed consumer, and the consumer becomes a Kafka consumer. Keep the relay behind an outbound port so the change is one adapter on each side. See [Extracting a Bounded Context to a Microservice](extracting-microservices.md).

## See also

- [Adapter Flows](adapter-flows.md) flow #7 — the capture and dispatch call chains.
- [Idempotent Consumer](idempotent_consumer.md) — the consumer-side half of at-least-once delivery.
- [Glossary](glossary.md) — Transactional Outbox, At-least-once Delivery, Idempotent Consumer, CloudEvents.
