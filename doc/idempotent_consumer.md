# Idempotent Consumer

## Why

At-least-once delivery — from the [transactional outbox](transactional_outbox.md), from Kafka, or from a broker's own retries — means a consumer may receive the same message more than once: redelivery after a crash between processing and acknowledgment, a relay resending a row it could not mark `SENT`, or a broker retry. A consumer must therefore produce the same result whether it processes a message once or several times. That property is idempotency, and it is what makes at-least-once delivery safe.

## Two approaches

### Business-key idempotency (default)

Aggregates expose a unique business identifier — an external order ID, a payment reference — enforced by an aggregate invariant, a repository lookup, and a database unique constraint. A duplicate operation is detected by repository lookup before creation, and the handler returns the existing aggregate on duplicate input. No extra infrastructure is needed: the natural business key *is* the deduplication key. Prefer this whenever a natural key exists.

### Technical idempotency (deduplication store)

When there is no natural business key — payment APIs, or consumers processing events with no domain-unique field — deduplicate on the message identity itself: the CloudEvents `id`, or a client-supplied `Idempotency-Key` header. Processed identifiers are recorded in a store; each incoming message is checked against it, skipped if already seen, otherwise processed and recorded.

This is a cross-cutting capability:

- an `IdempotencyStore` port under `shared.application.port.out.idempotency`,
- an inbound interceptor under `shared.infrastructure.adapter.in.idempotency`,
- a JDBC or Redis store under `shared.infrastructure.adapter.out.idempotency`.

These packages are added to the tree only when a use case requires the framework; they are not part of the default scaffold. The deduplication table is shared infrastructure, migrated under `db/migration/shared/`.

## Where the check lives

The check sits in the inbound messaging adapter, before the handler runs (see [Adapter Flows](adapter-flows.md) flow #3): unwrap the envelope, check the CloudEvents `id` against the store, acknowledge and skip if it was already processed, otherwise map the event to a local command and invoke the handler.

## Correctness notes

- **Record and effect should be consistent.** Ideally the processed identifier is recorded in the same transaction as the handler's state change (single database), so a crash cannot apply the effect without recording the identifier or the reverse. When the store is a separate system (Redis), the deduplication is best-effort; rely on business-key idempotency where correctness is critical.
- **Idempotency is required even under exactly-once in-JVM dispatch.** That mode does not survive extraction — it reverts to at-least-once once a broker or a separate database is involved — so the consumer must remain idempotent regardless. See [Transactional Outbox — In-JVM communication](transactional_outbox.md#in-jvm-communication).
- **Ordering is separate from deduplication.** Per-aggregate order is preserved by the outbox `seq` and the partition key; idempotency handles duplicates, ordering handles sequence. They are independent guarantees.

## See also

- [Transactional Outbox](transactional_outbox.md) — the producer-side half of at-least-once delivery.
- [Adapter Flows](adapter-flows.md) flow #3 — the inbound consumer call chain.
- [Glossary](glossary.md) — Idempotent Consumer, Idempotency, Business-key Idempotency, Technical Idempotency, At-least-once Delivery.
