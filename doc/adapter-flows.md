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

```
HTTP POST /<resource>
  │
  ▼
<X>Resource (infrastructure.adapter.in.rest)
  ├─ Jakarta Validation on wire DTO (@NotBlank, @Size, ...)
  ├─ JSON parse via Jackson
  └─ <X>RestMapper.toCommand(request) → <Verb><Noun>Command
       │
       ▼
<Verb><Noun>Handler (application.service.command) [tx]
  ├─ unpack command primitives → domain value objects
  ├─ load aggregate via domain repository (if update) OR
  │  <Aggregate>.create(...) static factory (if new) — invariants enforced inside
  ├─ <Aggregate>Repository.save(aggregate) → domain.repository port
  │      ▼
  │   <X>JpaRepositoryAdapter (infrastructure.adapter.out.persistence.jpa)
  │      └─ Hibernate persist/merge
  ├─ aggregate.pullDomainEvents() → outbox append [tx]
  └─ map aggregate → response DTO or return aggregate ID
       │
       ▼
<X>Resource returns 2xx + response DTO (or 201 + Location)

Error branch:
  DomainException → registered Problem Detail mapper
    → RFC 9457 response (type, status, extensions, correlationId)
  Unhandled → generic 500, full detail logged with correlationId
```

### 2. REST query (read)

Two sub-flows depending on read shape (see README "Read vs write persistence").

**2a. Single-aggregate read (shape matches aggregate)**
```
HTTP GET /<resource>/{id}
  ▼
<X>Resource → <Query>Query (application.port.in.query)
  ▼
<X>QueryHandler (application.service.query) [tx, readOnly]
  ├─ <Aggregate>Repository.findById(id) → JPA adapter
  └─ map aggregate → projection DTO (inside tx; lazy assoc resolved)
  ▼
<X>Resource returns 200 + response DTO
```

**2b. List / search / cross-aggregate read**
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

```
Kafka record on subscribed topic
  ▼
<X>EventConsumer (infrastructure.adapter.in.kafka)
  ├─ CloudEvents binding extract → CloudEvent envelope
  │     (shared.infrastructure.adapter.out.messaging.cloudevents helpers)
  ├─ schema validate payload against dataschema
  ├─ map external event vocabulary → local command
  └─ invoke <Verb><Noun>Handler — same handler as REST path
       ▼
   (continues as flow #1 — aggregate mutation, outbox append, ack)
  ▼
Kafka offset committed only after handler returns successfully
  (failure → retry / DLQ per consumer config)
```

### 4. Scheduler

```
Quarkus @Scheduled trigger
  ▼
<X>Job (infrastructure.adapter.in.scheduler)
  ├─ extract args (time window, batch size, page cursor) from config/clock
  └─ invoke command or query handler — one per job
       ▼
   (continues as flow #1 or #2)
```

---

## Outbound adapters

### 5. JPA persistence (write)

```
Handler [tx] → <Aggregate>Repository (domain.repository port)
  ▼
<X>JpaRepositoryAdapter (infrastructure.adapter.out.persistence.jpa)
  ├─ EntityManager persist / merge / remove
  └─ Hibernate dirty checking on managed entities
  ▼
PostgreSQL (flush at tx commit)
```
Returns: aggregate root only (never projection DTOs — query-shape methods belong to flow #6).

### 6. Query adapter (read)

```
Query handler [tx, readOnly] → <X>QueryPort (application.port.out.query)
  ▼
<X>QueryAdapter (infrastructure.adapter.out.persistence.query)
  ├─ JPQL / native SQL / Criteria / read model
  └─ direct DTO projection (constructor expression / native rowmapper)
  ▼
Projection DTO returned to handler — no aggregate hydrated, no lazy proxies
```

### 7. Kafka producer via transactional outbox

```
Phase A — capture (in originating tx):
  Aggregate emits domain event
    ▼
  Handler pulls events → OutboxStore.append(event) [tx]
    ▼
  Tx commit: aggregate row + outbox row written atomically

Phase B — publish (separate process / scheduled poller) [async]:
  OutboxPoller selects unsent rows
    ▼
  Translate internal domain event → versioned external event
    (mapping owned by messaging adapter)
    ▼
  CloudEvents wrap (envelope, type=<...>.vN, dataschema, traceparent)
    (shared.infrastructure.adapter.out.messaging.cloudevents)
    ▼
  Schema registry compatibility check (BACKWARD)
    ▼
  KafkaProducer.send(topic, key, cloudEvent)
    ▼
  On broker ack → mark outbox row sent; on failure → retry with backoff
```
Guarantees at-least-once delivery aligned with source transaction. Consumers deduplicate by event ID.

### 8. REST client (outbound HTTP)

```
Handler [tx] → <X>ServiceClient port (application.port.out.client)
  ▼
<X>ServiceRestClient (infrastructure.adapter.out.client.rest)
  ├─ build request from domain types → wire DTO
  ├─ MicroProfile REST Client / Quarkus REST Client call
  ├─ resilience: timeout, retry, circuit breaker (SmallRye Fault Tolerance)
  └─ map response wire DTO → domain type, or translate error → DomainException
  ▼
External service
```
Outbound HTTP is not part of the local tx. Long-running or cross-service consistency uses outbox + eventing (flow #7), not synchronous calls inside the tx.

### 9. Cache adapter

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

- **Correlation ID** — propagated via OpenTelemetry context across inbound → handler → outbound → log → Problem Detail.
- **Metrics** — Micrometer timers wrap handler entry; per-adapter counters at edges.
- **Logging** — structured JSON; correlation ID + tenant/user context attached at adapter ingress.
- **Security** — OIDC/JWT verified at inbound adapter; principal forwarded to handler; RBAC/ABAC decision in `domain.policy`.
- **Transaction boundary** — `@Transactional` only on application handlers. Inbound adapter opens nothing; outbound adapter enlists in caller's tx.
