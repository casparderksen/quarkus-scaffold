# Cross-Context Integration Flows

This document describes how one bounded context integrates with another inside the modular monolith: the interaction patterns available, when to choose each, and the design decisions behind them. It complements [README — Cross-context integration](../README.md#cross-context-integration) (the rules), [Adapter Flows](adapter-flows.md) (per-adapter call chains), and [Extracting a Bounded Context to a Microservice](extracting-microservices.md) (what each pattern becomes when a context is deployed separately).

Every cross-context interaction enters another context only through its Open-Host Service — its inbound published API in `application.port.in`, the same command and query use cases the context's own REST adapter drives. A context never reaches into another's `domain`, `application.port.out`, `infrastructure`, or tables; those are private.

Each interaction has a direction. The context that provides — the Open-Host Service being called, or the context whose events are consumed — is the [upstream](glossary/upstream-downstream-context.md) context, the supplier; the context that reaches for it or reacts to it is downstream, the consumer that depends on it. Each scenario below names which context plays which role, except a choreographed Saga, where the roles are relative per event (see Saga).

Notation follows [Adapter Flows](adapter-flows.md): `→` a synchronous call, `▼` the next stage in the same flow, `[tx]` runs inside the application handler's transaction.

## Choosing a scenario

Start from what the consuming context actually needs, and answer two questions.

**Is this a read or a write?** A read obtains data owned by another context. A write causes a state change in another context.

For a **read**, ask whether the response is a view of one context's data or a composite of several. Reading one context's data to display alongside your own — a customer name on an order, a product description on an invoice — is a *Foreign Read*. Combining data across several contexts into a list, report, or dashboard is a *Cross-Context Report*.

For a **write**, ask whether the initiating context's own state or flow depends on the result. If it does not — the other context is simply told that something happened and decides for itself what to do — it is a *Notification*. If it does — the initiator must know the outcome to proceed, or must undo its own work when the other context fails — it is a *Saga*.

| The consuming context needs to… | Scenario |
|---|---|
| Read another context's data for display; a slightly stale value is acceptable | **Foreign Read** |
| Combine data across several contexts into a list, report, or dashboard | **Cross-Context Report** |
| Cause a change in another context, with no dependency on the result | **Notification** |
| Cause a change in another context whose outcome its own state depends on, or must compensate on failure | **Saga** |

A fifth situation is not a scenario but a warning sign — see *Shared Invariant* under Rules.

## Rules

These constraints apply to every scenario and follow from the architecture rules in the README.

**Reads cross the boundary synchronously; nothing is copied.** A read is a synchronous call into the provider's inbound query port. The consumer holds no copy of the provider's data inside the monolith.

**Writes never cross the boundary as a synchronous command.** A synchronous cross-context write cannot be both atomic and respect the one-aggregate-per-transaction boundary: sharing the caller's transaction would mutate two aggregates in two contexts at once, and using a separate transaction would risk a partial, unrollbackable dual write. State changes therefore propagate as integration events through the transactional outbox. The initiating context mutates its own aggregate and appends an event in a single transaction; the other context consumes the event and reacts in its own transaction. Consistency between the two is eventual.

**A context publishes facts in its own vocabulary; a consumer translates a fact into its own command.** In reactive integration a context does not send another context a command: the producer publishes what happened (`OrderPlaced`), and each consumer's inbound adapter maps that fact to a local command, deciding for itself how to react. This keeps each context's decisions inside its own boundary and lets new consumers subscribe without changing the producer. The one exception is an orchestrated saga's process manager, which issues step and compensation commands to its participants as part of an explicit, owned process (see Saga).

**Shared Invariant (a smell, not a scenario).** If a context cannot decide correctly without another context's up-to-the-moment state — that is, a single invariant spans two contexts — the boundary is wrong. A synchronous read does not fix this: eventual data cannot hold an invariant, and a shared transaction violates the aggregate boundary. Resolve it instead by moving the invariant so it sits inside one consistency boundary, or, if the rule is genuinely a multi-step agreement between contexts, model it as a Saga.

## Foreign Read

A bounded context often needs a piece of data owned by another context in order to render a response — an order screen showing the customer's name, an invoice listing product descriptions. From the consumer's side the data is read-only, and a slightly stale value is acceptable: the customer name on an order confirmation need not reflect a rename made moments earlier.

In the modular monolith this is a synchronous call through the provider's Open-Host Service. The provider is the upstream context; the reading context is downstream. The consumer injects the provider's inbound query port — the same use case the provider's own REST adapter drives — and receives a projection DTO. The call is an ordinary in-process CDI invocation, so it is cheap and immediate, and the consumer never sees the provider's aggregate, entities, or tables.

```
Consumer handler [tx, readOnly]  (consumer context, application.service)
  ▼
Provider <X>Query  (provider application.port.in.query)   — in-process CDI call
  ▼
Provider <X>QueryHandler [tx, readOnly]   (continues as adapter-flow #2)
  ▼
Consumer receives a projection DTO  (never a provider aggregate or entity)
```

Because the call is in-process and inexpensive, the consumer keeps no copy of the provider's data. Building a local read model inside the monolith would be premature: it trades a near-free call for a table, an event subscription, and an eventual-consistency window.

When the provider is later extracted to its own service, this read must cross the network and the trade-offs change — keep the synchronous call as a REST client, or replace it with a locally maintained projection. That decision belongs to the extraction procedure; see [Extracting a Bounded Context to a Microservice](extracting-microservices.md).

**Design decisions**

- The cross-context edge is permitted only into the provider's `application.port.in`; every other cross-context dependency is rejected.
- Inject the provider's inbound port directly. Add a consumer-owned outbound port and an anti-corruption layer only when the provider's vocabulary would otherwise leak into the consumer and cause harm.
- Keep no local copy of the provider's data in the monolith; the synchronous call is the mechanism.
- When several providers must be read, issue one Open-Host Service call per provider; never join across contexts.

## Cross-Context Report

A report, list, or dashboard often draws on data owned by several contexts at once — an operations dashboard combining orders, shipments, and payments; a customer view listing invoices with their product descriptions. As with a Foreign Read the data is read-only and a slightly stale value is acceptable, but unlike a Foreign Read it spans several contexts and must filter, sort, paginate, or aggregate across them.

This cannot be served by reading each context in turn. Cross-context joins are forbidden, so the data cannot be combined in the database; and fanning out a synchronous Open-Host Service call per context — and per row — is an N+1 explosion that coalesces the contexts on the request path. A cross-context report is therefore backed by a **dedicated read model**, owned by the reporting context and maintained from the source contexts' integration events. This holds inside the monolith, not only after extraction — it is the one read that justifies a projection before a context is ever split out.

Each source context is upstream and the reporting context downstream: each source context publishes integration events; the reporting context subscribes and updates its read model through a command handler, the same way any projection is updated. The read model is denormalized for the shape of the report, so the query path is an ordinary local read — filtering, sorting, pagination, and joins all happen within the read model, which the reporting context owns in full.

```
Feed (asynchronous, continuous — one subscription per source context):
  Context A integration event ─┐
  Context B integration event ─┼─▶ <Report>EventConsumer  (infrastructure.adapter.in.messaging.kafka)
  Context C integration event ─┘        └─ map event → Update<Report>ProjectionCommand
                                             ▼
                                          projection command handler [tx]
                                             └─ upsert read-model row(s)   (reporting-context-owned table)

Read (request path, fully local):
  Report query handler [tx, readOnly]
    ▼
  <Report>QueryPort  (application.port.out.query)
    ▼
  <Report>QueryAdapter  (infrastructure.adapter.out.persistence.query)
    └─ JPQL / native SQL over the read model — filter, sort, paginate, join within it
    ▼
  projection DTO (report rows)
```

Because the read model is populated asynchronously, its data is eventually consistent with the source contexts — which a report tolerates by definition.

**Design decisions**

- A cross-context report always uses a read model, even in the monolith. The no-cross-context-join rule forbids combining the data in the database, and a synchronous fan-out is an N+1 across contexts.
- The read model is owned by the reporting context and fed by each source context's integration events, updated through a command handler — keeping the transaction boundary, idempotency, and audit trail consistent.
- All filtering, sorting, pagination, joining, and aggregation happen within the read model; the query never crosses a context boundary.
- Store only the fields the report needs, denormalized for the query shape.
- Place the read model in the context that owns the report. When the report belongs to no single context, place it in a dedicated reporting context.
- This scenario is for reads spanning several contexts. A list or report over a single context — even a large one — is an ordinary local query (adapter-flow #2b), not a cross-context concern.
- A database view is not an alternative to the read model when the report spans contexts: a view over another context's tables is a cross-context join that binds to that context's private structure — forbidden by the same rule that forbids the join, and impossible to keep once the context owns a separate database, so it would not survive extraction. A view or materialized view is fine only within a single context, including over the reporting context's own read-model tables.

*Extraction note:* the read model already exists; at extraction only its event feeds repoint onto the broker. There is no projection-versus-REST choice here — a report is a projection by construction.

## Notification

A context sometimes needs another context to know that something happened, without depending on what the other context does about it — an order was placed, a payment was captured, an account was closed. The initiating context owns the fact; each interested context decides for itself how to react. The initiator is upstream (it owns and publishes the fact); each interested context is downstream (it depends on that fact and reacts).

Because a cross-context write can never be a synchronous call, the initiating context calls no one. It records the fact as a domain event, written to the transactional outbox in the same transaction that changed its own aggregate. The two commit atomically, so the fact is never lost and never published for a change that rolled back. The event carries the fact in the initiator's own vocabulary — `OrderPlaced`, not `ReserveStock` — and names no consumer.

Each interested context subscribes to that event, translates it into a command in its own vocabulary, and reacts within its own transaction. The initiator does not wait, does not learn the outcome, and does not know which contexts consume the fact; a new consumer can be added without touching it.

```
Producer side (initiating context) — within its own write, adapter-flow #1 + #7 Phase A:
  A command handler [tx]
    ├─ mutate A's own aggregate
    └─ emit domain event → outbox  (same tx)      — a fact in A's vocabulary
    ▼ commit: aggregate row + outbox row, atomically
  (the outbox poller dispatches later — adapter-flow #7 Phase B)

Consumer side (each interested context) — adapter-flow #3:
  A's integration event
    ▼
  <B>EventConsumer  (B infrastructure.adapter.in.messaging.kafka)
    ├─ unwrap CloudEvent, idempotency check
    └─ map fact → local command  (B's vocabulary)
         ▼
      B command handler [tx]  → mutates B's own aggregate  (continues as adapter-flow #1)
```

This is the transactional-outbox publication (adapter-flow #7) on the producer side and the inbound-event flow (adapter-flow #3) on the consumer side, applied across a context boundary. Delivery is at-least-once, so a consumer deduplicates by the CloudEvent id and its reaction is idempotent. A consumer that fails to process an event is handled by broker redelivery or a dead-letter topic; the initiator, having already committed, is unaffected.

**Design decisions**

- The initiator publishes a fact in its own vocabulary and names no consumer; each consumer translates the fact into its own command and decides how to react.
- The event is appended to the outbox in the same transaction as the initiator's aggregate change — atomic, with no dual write.
- The initiator never waits for or learns the outcome. If its own state or flow depends on the result, this is a Saga, not a Notification.
- Any number of contexts may subscribe to the same fact independently; adding a consumer does not change the initiator.
- Consumers are idempotent and deduplicate by CloudEvent id; delivery is at-least-once.

*Extraction note:* only the transport changes — the event crosses a broker between services instead of an in-process dispatch. Producer, outbox, and consumer code are unchanged.

## Saga

Some cross-context writes are not fire-and-forget: the initiating context must know whether the other context succeeded — to continue its own work, or to undo it when the other context fails. An order that reserves stock and then captures payment, where a failed payment must release the reserved stock, is the archetype. No step can be a synchronous cross-context call and no single transaction can span the contexts, so a saga is always a sequence of local transactions linked by events. There is no distributed transaction and no lock held across contexts. Delivery is at-least-once, so every step and every compensation is idempotent, and compensations are **semantic** — they undo the effect of a committed step (release the reservation), never roll back a transaction that committed long ago. A step that cannot be compensated is ordered last, after every compensatable step has succeeded.

Unlike the directional scenarios above, a saga has no fixed upstream/downstream pair: each participant is upstream for the outcome facts it publishes and downstream for the facts — or, under orchestration, the commands — it consumes. The roles are relative to each event, not to the saga as a whole.

A saga runs in one of two styles, chosen by how complex the process is and whether any single context owns it.

| Use… | When |
|---|---|
| **Choreography** | The flow is short (two or three steps) and stable, and each participant can react and compensate on its own. No coordinator; only facts cross boundaries. This is the default. |
| **Orchestration** | The flow is long or complex, needs visible state or process-level control (timeouts, retries, whole-saga audit), or coordinates peers owned by no single context. A dedicated process manager owns the flow, at the cost of one sanctioned cross-context command channel. |

### Choreography

No context owns the process. Each participant reacts to facts published by the others, performs its own local step, and publishes its own outcome fact; failure facts trigger each participant's own compensating reaction. The process is defined implicitly by the chain of subscriptions.

```
Order context [tx]:      place order → emit OrderPlaced (fact) → outbox

Inventory (reacts to OrderPlaced):
  [tx] reserve stock → emit StockReserved   (or StockReservationFailed)

Payment (reacts to StockReserved):
  [tx] capture payment → emit PaymentCaptured   (or PaymentFailed)

Order (reacts to PaymentCaptured):
  [tx] confirm order

Compensation path (each participant reacts to the failure fact):
  PaymentFailed →
    Inventory (reacts): [tx] release stock → emit StockReleased
    Order     (reacts): [tx] reject order
```

Each reaction is adapter-flow #3 → #1 → #7: consume a fact, map it to a local command, mutate the own aggregate, emit the next fact. No orchestrator, no cross-context command, no new machinery. The trade-off is that the process definition is distributed across participants' subscriptions — no single place shows the whole flow or its state, and the compensation logic is spread across the participants.

### Orchestration

A dedicated **process manager** (the saga) owns the process. It holds the state of one running process — which steps have completed, which is pending, whether the process is compensating — and advances a state machine: on each participant's outcome event it either issues the next step or, on failure, issues compensating steps that walk the completed work backward. The process manager owns process state only; it never holds another context's domain data and never mutates a participant's aggregate directly.

The process manager issues a step to a participant as a command message and consumes the participant's outcome as an event, both through the outbox and broker; each participant executes the step in its own transaction and reports success or failure by publishing its own outcome event. This is the one place a message crossing a context boundary is a command rather than a fact — justified because a saga is an explicit process with a named owner, unlike the autonomous reaction of a Notification. A participant still maps the incoming command message to its own local command and remains autonomous in how it executes.

```
Initiator (context that owns the process) — starts the saga in its own write:
  <Process> command handler [tx]
    ├─ create <Process> saga state = STARTED
    └─ emit "Step 1 command" → outbox  (same tx)
    ▼ commit

Participant P1  (adapter-flow #3 → #1):
  receive "Step 1 command" → map to local command → P1 handler [tx] mutates P1's aggregate
    └─ emit "Step 1 succeeded"  (or "Step 1 failed") → outbox

Process manager  (consumes outcomes, advances state — adapter-flow #3 → #1 over saga state):
  on "Step 1 succeeded":   state = STEP1_DONE;    emit "Step 2 command"
  on "Step 2 succeeded":   state = COMPLETED
  on "Step 2 failed":      state = COMPENSATING;  emit "Compensate Step 1 command"
  on "Step 1 compensated": state = ABORTED

Participant compensation:
  receive "Compensate Step 1 command" → P1 handler [tx] semantically undoes Step 1
    └─ emit "Step 1 compensated" → outbox
```

The process manager lives in the context that owns the business process. When the process belongs to no single participant — it coordinates peers that each own only their step — it lives in a dedicated process (coordination) context.

### Design decisions

- A saga is used whenever the initiator's state or flow depends on another context's outcome, or must be undone on that context's failure. If neither holds, it is a Notification.
- The process is a sequence of local transactions linked by events; there is no distributed transaction and no cross-context lock.
- Default to choreography for short, stable flows; move to orchestration once the flow is long or complex, needs a visible state or process-level control, or is owned by no single participant.
- Under orchestration, one process manager owns the flow — its state machine, step sequence, and compensations — and owns process state only, never a participant's domain data or aggregate.
- Under orchestration, the process manager issues step and compensation commands to participants and consumes their outcome events, asynchronously through the outbox and broker, never as a synchronous call. This is the one sanctioned case of a command crossing a context boundary. Under choreography, only facts cross boundaries and no command exception is needed.
- Every step and compensation is idempotent (at-least-once delivery); compensations are semantic reversals applied by walking completed steps backward.
- A non-compensatable step is ordered last.

*Extraction note:* participants (and, under orchestration, the process manager) already communicate through the outbox and events; at extraction those messages cross a broker between services instead of in-process. The saga logic is unchanged.
