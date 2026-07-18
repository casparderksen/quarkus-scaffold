# Adapter Flows

End-to-end call chains for each inbound and outbound adapter. Each flow shows where a request enters, which layers it traverses, and where it exits. Flows are derived from the architecture rules in [README.md](../README.md) — they do not introduce new rules.

Notation:
- `→` synchronous call
- `▼` next stage in same flow
- `[tx]` runs inside the application handler's transaction
- `[async]` runs outside the originating transaction

---

## Inbound adapters

### 1. REST command (write)

Synchronous write entrypoint. The REST adapter validates the wire shape, translates to an application command, and invokes a single command handler. The handler owns the transaction, loads or creates exactly one aggregate, persists it, and emits domain events through the outbox. Mapping to the response DTO happens inside the transaction so no managed entity or lazy proxy leaves the application layer.

```
HTTP POST /<resource>
  ▼
<X>Resource (infrastructure.adapter.in.rest)
  ├─ JSON parse via Jackson → wire DTO
  ├─ Jakarta Validation on wire DTO (@NotBlank, @Size, ...)
  └─ <X>RestMapper.toCommand(request) → <Verb><Noun>Command
       ▼
<Verb><Noun>Handler (application.service.command) [tx]
  ├─ unpack command primitives → domain value objects
  ├─ load aggregate via domain repository (if update) OR
  │  <Aggregate>.create(...) static factory (if new) — invariants enforced inside
  ├─ <Aggregate>Repository.save(aggregate) → domain.repository port
  │      ▼
  │   <X>JpaRepositoryAdapter (infrastructure.adapter.out.persistence.jpa)
  │      └─ Hibernate persist/merge
  ├─ aggregate.pullDomainEvents() → DomainEventPublisher.publish(...) (see #7 Phase A)
  └─ map aggregate → response DTO or return aggregate ID
       ▼
<X>Resource returns 2xx + response DTO (or 201 + Location)

Error branch:
  Validation failure → Problem Detail 400 (before handler invoked)
  DomainException → registered Problem Detail mapper
    → RFC 9457 response (type, status, extensions, correlationId)
  Unhandled → RFC 9457 response with status 500
```

### 2. REST query (read)

Synchronous read entrypoint. Two sub-flows depending on whether the response shape matches an aggregate. The split exists to avoid hydrating aggregates for display-only reads (lazy loading, N+1, unnecessary object graph), while still letting trivial single-aggregate reads reuse the domain repository instead of duplicating a query adapter. The shape of the response, not its size, decides which path is used.

**2a. Single-aggregate read (shape matches aggregate)**

Used when the projection is naturally the aggregate itself or a thin view over it. The query handler loads the aggregate through `domain.repository` and maps it to a response DTO inside the read-only transaction so any lazy associations resolve before the session closes.
```
HTTP GET /<resource>/{id}
  ▼
<X>Resource → <Query>Query (application.port.in.query)
  ▼
<X>QueryHandler (application.service.query) [tx, readOnly]
  ├─ <Aggregate>Repository.findById(id) → JPA adapter
  │     └─ not found → throw <X>NotFoundException → Problem Detail 404
  └─ map aggregate → projection DTO (inside tx; lazy assoc resolved)
  ▼
<X>Resource returns 200 + response DTO
```

**2b. List / search / cross-aggregate read**

Used for lists, searches, reports, dashboards, or any read combining data from multiple aggregates. The path bypasses the domain entirely and goes through a dedicated query port implemented by a query adapter that projects directly into DTOs via JPQL, native SQL, or a read model. No aggregate is hydrated, no domain repository is touched. This is the only acceptable place for filter/sort/pagination shapes.

```
HTTP GET /<resource>?filter=...
  ▼
<X>Resource → <Query>Query
  ▼
<X>QueryHandler [tx, readOnly]
  └─ <X>QueryPort.find(...) → application.port.out.query
       ▼
    <X>QueryAdapter (infrastructure.adapter.out.persistence.query)
       └─ JPQL / native SQL / read model → projection DTO
  ▼
<X>Resource returns 200 + response DTO (no aggregate hydrated)
```

### 3. Kafka consumer (inbound integration event)

Asynchronous write entrypoint driven by external integration events. The consumer unwraps the CloudEvent, validates payload, checks idempotency (delivery is at-least-once), translates external vocabulary to a local command, and invokes the same command handler the REST adapter would call. The consumer never executes domain logic itself — it is an entrypoint, identical in role to a REST resource.

```
Kafka record on subscribed topic
  ▼
<X>EventConsumer (infrastructure.adapter.in.messaging.kafka)
  ├─ CloudEvents binding extract → CloudEvent envelope
  │     (shared.infrastructure.adapter.out.messaging.cloudevents helpers)
  ├─ schema validate payload against dataschema
  ├─ idempotency check: CloudEvent `id` against IdempotencyStore port
  │     → already-processed → ack + skip
  ├─ map external event vocabulary → local command
  └─ invoke <Verb><Noun>Handler — same handler as REST path
       ▼
   (continues as flow #1 — aggregate mutation, outbox append, ack)
  ▼
Kafka offset committed only after handler returns successfully

Failure branch:
  Transient failure (infra, optimistic lock) → throw → broker redelivery + backoff
  Non-retryable (DomainException, schema mismatch, mapping failure)
    → publish to DLQ topic + commit offset (no infinite redelivery loop)
```

### 4. Scheduler

Time-driven inbound adapter. The job class extracts arguments (time window, batch size, page cursor) from configuration or a clock abstraction and invokes a single command or query handler. Domain logic, transactions, and direct repository access stay out of the job class — it is purely an entrypoint, like REST or Kafka. Time is injected (fixed clock in tests) so jobs remain deterministic.

```
Quarkus @Scheduled trigger
  ▼
<X>Job (infrastructure.adapter.in.scheduler)
  ├─ extract args (time window, batch size, page cursor) from config/clock
  └─ invoke command or query handler — one per job
       ▼
   (continues as flow #1 or #2)

Error branch:
  Exception logged with correlation ID; Quarkus scheduler retries per @Scheduled config
```

---

## Outbound adapters

### 5. JPA persistence (write)

Aggregate-shaped write path. The domain repository exposes only aggregate-level operations needed by command handlers: `save`, `delete`, `findById`, and business-key lookups used to enforce invariants or detect duplicates. The JPA adapter is the only place that touches `EntityManager` and the only place where Hibernate's managed entities are observable. Because the handler maps the aggregate to a DTO before returning, no managed state escapes the transaction.

```
Handler [tx] → <Aggregate>Repository (domain.repository port)
  ▼
<X>JpaRepositoryAdapter (infrastructure.adapter.out.persistence.jpa)
  ├─ EntityManager persist / merge / remove
  └─ Hibernate dirty checking on managed entities
  ▼
PostgreSQL (flush at tx commit)
```
Returns: aggregate root only. No projection DTOs, no `Page<T>` / `Slice<T>`, no filter/sort/pagination parameters — query-shape operations belong to flow #6.

### 6. Query adapter (read)

Projection-shaped read path. The query port is defined by the application layer in terms of what the query handler needs (filters, sort keys, page cursor, projection shape); the query adapter implements it however is most efficient — JPQL constructor expression, native SQL with a row mapper, Criteria, or a dedicated read model fed by domain events. Nothing is hydrated as an aggregate; nothing is held as a managed entity beyond the query.

```
Query handler [tx, readOnly] → <X>QueryPort (application.port.out.query)
  ▼
<X>QueryAdapter (infrastructure.adapter.out.persistence.query)
  ├─ JPQL / native SQL / Criteria / read model
  └─ direct DTO projection (constructor expression / native rowmapper)
  ▼
Projection DTO returned to handler — no aggregate hydrated, no lazy proxies
```

### 7. Domain event publication via transactional outbox

The application layer publishes domain events through a single outbound port. The adapter side is split into a generic outbox-writing component and per-context translators that map domain event → versioned integration event. The Kafka producer itself runs out-of-band in Phase B and has no knowledge of domain types.

```
Phase A — capture (in originating tx):
  <Verb><Noun>Handler (application.service.command) [tx]
    ├─ aggregate.pullDomainEvents()
    └─ DomainEventPublisher.publish(events)
         → single outbound port (shared.application.port.out.messaging)
         ▼
      DomainEventOutboxAdapter
        (shared.infrastructure.adapter.out.messaging.outbox)
        For each domain event:
          ├─ resolve per-context DomainEventTranslator<E>
          │     (infrastructure.adapter.out.messaging.kafka, per bounded context)
          │     → IntegrationEvent (type=<...>.vN, dataschema URL)
          ├─ CloudEvents wrap: id, source, time, traceparent, dataschema
          │     (shared.infrastructure.adapter.out.messaging.cloudevents)
          ├─ schema registry compatibility check (BACKWARD)
          ├─ serialize payload (bytes are frozen here — later registry changes
          │   do not affect rows already written)
          └─ INSERT outbox row in same tx:
               (aggregate_id, seq, type, payload_bytes, traceparent,
                status=PENDING, created_at)
  ▼
  Tx commit: aggregate row + outbox row written atomically.
```

```
Phase B — dispatch [async, no domain knowledge]:
  OutboxPoller (shared.infrastructure.adapter.in.scheduler)
    └─ SELECT PENDING outbox rows
         ORDER BY aggregate_id, seq         ← preserves per-aggregate FIFO
         LIMIT <batch>
    ▼
  OutboxEventPublisher (shared.infrastructure.adapter.out.messaging.outbox)
    └─ KafkaProducer.send(topic, key=aggregate_id, payload=payload_bytes)
       (key drives partition assignment, so same aggregate → same partition
        → ordering preserved through the broker)
    ▼
  Broker ack → UPDATE outbox row status=SENT, sent_at
  Send failure → retry with exponential backoff
    → after N attempts: status=FAILED, attempts, last_error
       (the row itself is the dead-letter record; an operator alert fires
        and replay is manual. No Kafka-side DLQ topic — the publisher
        cannot reach Kafka by definition of the failure.)
```

Notes.
- Integration events (not domain events) are written to the outbox. Translation, versioning, and schema validation happen in Phase A inside the originating transaction.
- The outbox poller and publisher are generic. Per-context code lives only in the `DomainEventTranslator<E>` implementations.
- Guarantees at-least-once delivery aligned with the source transaction. Consumers deduplicate by CloudEvent `id` (flow #3).
- Correlation: `traceparent` captured at Phase A is replayed by the publisher in Phase B so the consumer trace links back to the originating request.

### 8. REST client (outbound HTTP)

Synchronous outbound call to an external service. The port is defined by the application in domain terms; the adapter handles the wire format, the transport library, and resilience (timeout, retry, circuit breaker). Errors from the remote service are translated into `DomainException` subtypes so the calling handler can react without knowing it is talking to HTTP. The call is *not* part of the local transaction — see the note below for the consequences.

```
Handler [tx] → <X>ServiceClient port (application.port.out.client)
  ▼
<X>ServiceRestClient (infrastructure.adapter.out.client.rest)
  ├─ build request from domain types or application command fields → wire DTO
  ├─ MicroProfile REST Client / Quarkus REST Client call
  ├─ resilience: timeout, retry, circuit breaker (SmallRye Fault Tolerance)
  └─ map response wire DTO → domain type, projection DTO, or value object,
     depending on what the calling handler needs; translate error → DomainException
  ▼
External service
```
Outbound HTTP is not part of the local tx and does not enlist in it. Long-running or cross-service consistency uses outbox + eventing (flow #7), not synchronous calls inside the tx. A handler that calls a REST client inside its tx accepts that the remote side-effect cannot be rolled back on tx rollback — design the call site accordingly (idempotent remote operation, or move the call out of the tx via flow #7).

### 9. Cache adapter

Optional read-through or write-aside cache, behind a port. The cache holds projection DTOs or value-object snapshots — never managed entities, never source of truth. The handler decides per use case whether to consult the cache before a query (read-through) or write to it after a domain update (write-aside). Invalidation is driven by domain events for the relevant aggregate, so cache freshness follows the same transactional guarantees as the rest of the system (Phase A of flow #7).

```
Handler → <X>CachePort (application.port.out.cache)
  ▼
<X>CacheRepository (infrastructure.adapter.out.cache.redis)
  ├─ key derivation from domain identifier
  ├─ serialize value (projection DTO or value object snapshot)
  └─ Redis GET / SET / DEL via Quarkus Redis Client
  ▼
Redis
```
Cache is read-through or write-aside per use case. Invalidation triggered by domain events for the relevant aggregate; cache is never source of truth.

---

## Cross-cutting concerns (applied to every flow)

- **Correlation ID** — propagated via OpenTelemetry context across inbound → handler → outbound → log → Problem Detail. For asynchronous outbound (flow #7), the `traceparent` is captured into the outbox row at Phase A and replayed by the publisher at Phase B, so the eventual Kafka publication links back to the originating request.
- **Metrics** — Micrometer timers wrap handler entry; per-adapter counters at edges.
- **Logging** — structured JSON; correlation ID + tenant/user context attached at adapter ingress.
- **Security** — OIDC/JWT verified at inbound adapter; principal forwarded to handler; RBAC/ABAC decision in `domain.policy`.
- **Transaction boundary** — `@Transactional` only on application handlers. Inbound adapter opens no tx. JPA outbound (flow #5) enlists in the caller's tx; messaging (flow #7) and outbound HTTP (flow #8) do not — cross-system consistency is handled via the transactional outbox, not by extending the local tx.
