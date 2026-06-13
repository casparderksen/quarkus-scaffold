# Quarkus Scaffold

Template for Quarkus applications.

## Tech Stack

- Runtime: Quarkus 3.33.1.1, Java 25
- Build: Maven
- Static analysis: Checkstyle, PMD, Spotbugs, Spotless (Palantir formatting)
- Docker: Multi-stage builds for JVM and native images, Red Hat UBI runtime images
- REST: `quarkus-rest-jackson` (reactive), RFC 9457 Problem Details, OpenAPI-first contract generation
- ORM: Hibernate ORM with Panache, Flyway migrations, PostgreSQL + H2 for dev
- Mapping: MapStruct
- Testing (unit): JUnit 5 + AssertJ + Mockito
- Testing (integration): QuarkusTest + Testcontainers
- Testing (contract): REST Assured + OpenAPI contract validation + Pact (bidirectional)
- Testing (e2e): Black-box tests via REST Assured + Cucumber (BDD feature specs) + Playwright (if UI exists)
- Messaging: Apache Kafka (via SmallRye Reactive Messaging)
- Observability: Micrometer metrics, OpenTelemetry tracing, structured JSON logging, correlation IDs
- API documentation: SmallRye OpenAPI (`/q/openapi`), Swagger UI (`/q/swagger-ui`)
- Security: Quarkus Security (OIDC/OAuth2), JWT validation, RBAC/ABAC via policy layer
- Caching: Redis (via Quarkus Redis Client), optional Caffeine for local in-process caching
- Health: SmallRye Health (`/q/health`, `/q/health/live`, `/q/health/ready`) aligned with Kubernetes probes

## Architecture

### Design principles

The application is structured according to Hexagonal / DDD / Clean Architecture concepts.
- DDD strategic design: bounded contexts, aggregates, repositories
- Hexagonal architecture: ports and adapters
- Clean architecture: use cases, dependency flow, and infrastructure isolation
- CQRS-lite separation (command/query): use for read-heavy UI pages and reports

The structure enables (eventual) extraction of Maven modules or independent services from a modular monolith.
It also supports sharing cross-cutting concerns (shared kernel, infrastructure) across teams.

See [Glossary](doc/glossary.md) for an explanation of concepts, [Adapter Flows](doc/adapter-flows.md) for
end-to-end call chains per inbound and outbound adapter, and [Testing Strategy](doc/testing-strategy.md)
for testing guidelines supported by this template.

### Precedence between styles

This template combines hexagonal architecture, DDD, and clean architecture. When the styles conflict, the order of precedence is:

1. **DDD** — for strategic and tactical modeling (bounded contexts, aggregates, repositories, ubiquitous language).
2. **Hexagonal** — for the dependency structure between the domain and the outside world (ports and adapters).
3. **Clean Architecture** — for the dependency rule (outside-in only) and for the role separation within the application layer (use cases as explicit handlers).

Concrete consequences:
- Repository interfaces live in `domain.repository`, following DDD, not in the use-case layer as Clean Architecture would place them.
- DTOs are owned by the layer that introduces the transport mismatch (adapter for wire formats, application for use-case input/output), following hexagonal port boundaries rather than Clean Architecture's universal "boundary objects".
- Application services are named after use cases (`<Verb><Noun>Handler`), following the Clean Architecture convention, but their dependency on the domain follows DDD's aggregate-centric model.

### Guidelines for use

Use this architecture when the system has non-trivial domain rules.
It can be relaxed for CRUD-heavy services, prototypes, or small and stable domains.

#### Mandatory baseline

- Use bounded-context packages to prevent cross-domain coupling.
- Keep aggregates as the consistency boundary; enforce invariants inside them.
- Separation of `domain`, `application`, `infrastructure`, dependencies outside-in.
- Application layer only orchestrates and owns transactions, not core business rules.
- Define ports explicitly for all external dependencies (DB, messaging, external APIs, SDKs).
- Keep shared kernel minimal and stable.
- Architecture rules are enforced by ArchUnit tests (see [Enforcement](#enforcement) below). Rules without a test are aspirational and subject to drift.

#### What can be relaxed

- CQRS separation (command/query split) can be skipped for simple CRUD domains.
- The policy package can be merged into domain service in small domains.
- Projection DTOs can be flattened into use cases or inlined where defined.
- Test slicing (unit/integration/contract separation) can be simplified in early phases.
- Shared kernel can be ignored entirely in single bounded context systems.

### Coding agents

[CLAUDE.md](CLAUDE.md) (or `AGENTS.md`) is not documentation; it specifies constraints for preventing structurally
plausible but architecturally invalid code. Everything obvious or inferable from the codebase should be left out.

### Aggregate boundaries

Aggregates define the consistency boundary. The following rules apply.

- **One aggregate per transaction.** A command modifies exactly one aggregate. Cross-aggregate consistency is eventual, achieved through domain events and downstream handlers.
- **References by ID.** An aggregate references other aggregates by their identifier (`OrderId`, `CustomerId`), never by direct object reference. No ORM association links aggregate roots.
- **Aggregate contains only invariant-bearing data.** Fields that exist only for display belong in projections, not in the aggregate.
- **Root owns child lifecycle.** Child entities are created, modified, and deleted through the aggregate root. Repositories exist only for aggregate roots, never for child entities.

Practical warning signs that an aggregate is the wrong size:
- Unbounded collections inside an aggregate (every order ever placed by a customer).
- More than a handful of entities inside one aggregate.
- Loading the aggregate to use only a small unrelated subset.
- Single-entity aggregates with no invariants beyond field nullability.

When boundaries are wrong, the fix is either to split the aggregate, move display-only fields to a projection, or merge a too-small aggregate into the one that owns its invariants.

### Package structure

**Main package structure (`src/main/java`):**
```
org.example
├── <bounded-context>                 # Business domain bounded context
│   ├── domain                        # Domain core
│   │   ├── model                     # Aggregates, entities, value objects
│   │   ├── event                     # Domain events
│   │   ├── repository                # Repository contracts for aggregate roots
│   │   ├── service                   # Domain services spanning multiple aggregates
│   │   ├── policy                    # Decision logic (stateless business decisions)
│   │   └── exception                 # Business exceptions
│   │
│   ├── application                   # Application layer: orchestrates domain and external dependencies
│   │   ├── port                      # Hexagonal ports
│   │   │   ├── in                    # Inbound contracts (defines what the application provides)
│   │   │   │   ├── command           # State-changing use case contracts (write operations)
│   │   │   │   └── query             # Read-only use case contracts (queries / projections)
│   │   │   └── out                   # Outbound contracts (defines dependencies that the application needs)
│   │   │       ├── client            # Outbound external system contracts (REST/gRPC/SOAP)
│   │   │       ├── messaging         # Outbound async messaging/eventing contracts
│   │   │       ├── query             # Read-side query ports (projection retrieval)
│   │   │       └── cache             # Cache contracts (if needed)
│   │   ├── service                   # Application services / use case handlers
│   │   │   ├── command               # Command handlers: orchestrate outbound ports and domain / manage transactions
│   │   │   └── query                 # Query handlers / read orchestration
│   │   └── dto                       # Transport-oriented models
│   │       └── projection            # Read-side projection models
│   │
│   └── infrastructure                # Technical implementation layer (adapters, frameworks, external systems)
│       ├── adapter                   # Hexagonal adapters
│       │   ├── in                    # Inbound adapters (driving adapters)
│       │   │   ├── rest              # REST controllers / HTTP entrypoints
│       │   │   │   ├── dto           # REST wire DTOs (request/response, transport-coupled)
│       │   │   │   ├── mapper        # Wire DTO ↔ command/projection mapping
│       │   │   │   └── error         # Problem Detail mapping + per-context catalog
│       │   │   ├── messaging         # Inbound messaging implementations
│       │   │   │   ├── event         # Inbound integration event DTOs (transport-neutral)
│       │   │   │   ├── mapper        # Inbound event → Command mappers
│       │   │   │   └── kafka         # Kafka consumers (event-driven outbound)
│       │   │   └── scheduler         # Scheduled jobs (driving adapters)
│       │   └── out                   # Outbound adapters (driven adapters)
│       │       ├── persistence       # Database access implementations
│       │       │   ├── jpa           # JPA/Hibernate implementations (aggregate repositories)
│       │       │   └── query         # Read-side optimized queries / projections
│       │       ├── messaging         # Outbound messaging implementations
│       │       │   ├── event         # Outbound integration event DTOs (transport-neutral)
│       │       │   ├── mapper        # Domain event → outbound event mappers
│       │       │   └── kafka         # Kafka producers (event-driven outbound)
│       │       └── client            # External service integrations (REST/gRPC/SOAP)
│       └── config                    # Application configuration interfaces (ConfigMapping)
│
└── shared                            # Shared technical and kernel components (if applicable)
    ├── domain                        # Domain layer shared
    │   ├── kernel                    # Shared domain primitives
    │   │   ├── model                 # Base aggregate/entity/value object abstractions
    │   │   ├── event                 # Base domain event abstractions
    │   │   ├── identifier            # Shared identifiers
    │   │   └── type                  # Shared primitive types (Money, etc.)
    │   └── exception                 # Shared abstract business exceptions
    │
    └── infrastructure                # Infrastructure layer
        ├── adapter                   # Hexagonal adapters
        │   ├── in                    # Inbound adapters (driving adapters)
        │   │   └── scheduler         # Scheduled jobs (e.g. outbox poller)
        │   └── out                   # Outbound adapters (driven adapters)
        │       ├── messaging         # Messaging implementations
        │       │   ├── cloudevents   # CloudEvents envelope + binding
        │       │   └── outbox        # Transactional outbox infrastructure
        │       ├── persistence       # Shared persistence infrastructure
        │       │   └── jpa           # JPA/Hibernate cross-cutting utilities
        │       │       └── converter # Generic JPA attribute converters (URI, etc.)
        │       ├── client            # External service integrations
        │       └── cache             # Cache infrastructure
        ├── config                    # Cross-cutting bootstrap and ConfigMapping interfaces
        ├── security                  # Authentication & authorization infrastructure
        ├── observability             # Logging, metrics, tracing, correlation ID propagation
        └── health                    # Health check endpoints (liveness/readiness)
```

**Test package structure (`src/test/java`):**
```
org.example
├── <bounded-context>                 # Business domain bounded context
│   ├── fixture                       # Object mothers, builders, test data generators
│   ├── unit                          # Fast isolated tests without framework/infrastructure
│   ├── integration                   # Tests using real framework/runtime infrastructure
│   ├── contract                      # API and messaging contract compatibility tests
│   │   ├── provider                  # OpenAPI spec validation (this service as provider)
│   │   └── consumer                  # Pact tests (this service as consumer)
│   ├── e2e                           # Black-box end-to-end system verification
│   └── performance                   # Load, stress, concurrency, and benchmark tests
│
└── shared                            # Reusable test infrastructure shared across bounded contexts
    ├── architecture                  # Structural and dependency rule enforcement tests (ArchUnit)
    ├── container                     # Shared Testcontainers setup (Postgres, Kafka, Redis, etc.)
    ├── config                        # Shared test configuration (ConfigMapping interfaces)
    ├── mock                          # Shared mocks, stubs, fakes
    ├── security                      # JWT/authentication test helpers
    ├── contract                      # Shared consumer/provider contract test utilities
    ├── clock                         # Fixed/test clock implementations for deterministic testing
    └── util                          # Low-level test-only utilities
```

**Test resources (`src/test/resources`):**
```
resources
├── fixture                           # Static fixture assets
│   └── <bounded-context>             # Business domain bounded context
│
├── config                            # Test-specific application configuration files
└── certificate                       # Test certificates, keys, and truststores
```

**Database migrations (`src/main/resources/db/migration`):**
```
db/migration
├── <bounded-context>/                # Per-context migrations, table prefix <context>_
└── shared/                           # Shared kernel tables (outbox, idempotency), prefix shared_
```

### Pragmatic Exception: JPA in the Domain Model

The domain model should be framework-free and agnostic of persistence technology.
Allowing JPA in the domain is a pragmatic exception to this rule because it preserves
Hibernate optimizations. With managed entities, Hibernate can perform dirty checking and batched updates without
extra SELECTs or explicit merge logic. Separating domain and persistence models can introduce extra
database roundtrips when re-attaching detached objects and requires maintenance of additional mappings,
while gaining relatively little for CRUD-heavy systems.

Use queries and projections when you don't need aggregate behavior or transactional consistency at the domain level,
typically for read-heavy use cases like lists, search, and reporting. In those cases, bypass entities entirely and
return DTOs via JPQL, native queries, or dedicated read models to avoid lazy loading, N+1 queries, and unnecessary
entity hydration. Command paths use entities; query paths use projections.

### Handler return contract

Application handlers never return managed JPA entities or aggregates across the transaction boundary. A handler returns one of:

- a response DTO or projection, constructed inside the transaction while the persistence context is open,
- a value object representing identity (`OrderId`) for write commands,
- `void`.

Mapping from aggregate to DTO happens inside the handler, so that any lazy associations needed by the DTO are resolved while the session is still active. Adapters receive plain data with no JPA proxies and no managed state.

Returning the entity instead would couple the wire format to the persistence model, expose the aggregate's mutation API outside any transaction, and require *Open-Session-In-View* — keeping the Hibernate session alive through serialization, which hides N+1 queries and runs reads outside the transaction's consistency guarantees. Open-Session-In-View is disabled.

### Read vs write persistence

The write path and read path are separated.

**Write path.** Command handlers load and mutate aggregates through `domain.repository`, implemented by `infrastructure.adapter.out.persistence.jpa`. Domain repositories expose only aggregate-shaped operations needed by commands: `save`, `delete`, `findById`, and business-key lookups used to enforce invariants or detect duplicates. They never accept query-shape parameters such as filters, sorts, or pagination for display purposes.

**Read path.** Query handlers return projection DTOs. The path used depends on the shape of the read:

- *Single-aggregate reads* (e.g., "get order by ID for display") may load the aggregate through the existing domain repository and map it to a response DTO inside the query handler. This avoids duplicating a query adapter for trivial cases where the aggregate shape already matches the read shape.

- *List, search, report, and cross-aggregate reads* must bypass the domain entirely. They go through a query port in `application.port.out.query`, implemented by `infrastructure.adapter.out.persistence.query` using JPQL, native SQL, or dedicated read models. These reads never hydrate aggregates and never go through `domain.repository`.

The boundary between the two is shape-driven, not size-driven. The question is not "is this read simple?" but "does the projection match the aggregate shape?" If the answer is yes and only one aggregate is involved, the domain repository is acceptable. Otherwise the query path is required.

Domain repositories must not grow query-shape methods (filters, sorts, pagination, projections) to serve display needs. When a read no longer matches an aggregate shape, move it to the query path rather than extending the domain repository.

### DTOs and commands

The application layer exposes its use cases through commands and queries in `application.port.in`. These types are part of the application contract: stable, transport-free, and built from primitives or domain value objects.

Wire-format DTOs (HTTP request/response bodies, with Jackson and OpenAPI annotations) live in the adapter, under `infrastructure.adapter.in.rest.dto`. The adapter maps wire DTO ↔ command/projection using local mappers in `infrastructure.adapter.in.rest.mapper`.

Separating the two prevents three problems: dependency leakage (Jackson/OpenAPI annotations on application types pull transport libraries into the application module), lifecycle coupling (wire format and use-case contract change for different reasons), and semantic mismatch (commands hold value objects like `Money` and `CustomerId`; wire DTOs hold primitives and flat structures).

Handlers unpack the command into domain types and pass them to the aggregate's constructor or to a static factory method on the aggregate itself (e.g., `Order.create(customerId, lines, ...)`). The aggregate never sees application command types — the dependency rule forbids it.

### Validation

Validation lives with the rule.

- **Wire validation** (`@NotBlank`, `@Email`, `@Size`, JSON parseability) is declared on the wire DTO in the REST adapter and triggered by the framework before reaching the application layer.
- **Structural validation** of commands (required fields, enum values, format) is declared on the command record itself or as guard clauses in the handler.
- **Business validation** (invariants, eligibility, state transitions) lives in the aggregate, `domain.policy`, or `domain.service`.

There is no dedicated `application.validation` package. A validator only makes sense as a pre-flight orchestration step (e.g., "command references customer X and product Y, both must exist before opening transaction"), and such checks are inlined in the handler.

### Error model

HTTP error responses are RFC 9457 Problem Details, serialized by the `quarkus-http-problem` library.

**Domain exceptions are transport-free.** They extend `DomainException` and carry only business context as typed fields — no HTTP status, no Problem Detail URIs, no library annotations. The same exceptions are reusable across REST, gRPC, and messaging adapters.

**Mapping lives in the adapter.** The REST adapter registers each domain exception with the library, declaring HTTP status, Problem Detail `type` URI, and which exception fields are surfaced as RFC 9457 extension fields. Registration and a per-context catalog of problem types live in `infrastructure.adapter.in.rest.error`.

**Status code policy.** Consistent across bounded contexts: not found → 404, invariant violation → 409, authorization failure → 403, authentication failure → 401, validation failure → 400, unexpected → 500.

**Extension fields are reviewed.** Fields surfaced in Problem Details must not contain internal state, PII, or implementation detail. The catalog declares per-exception what is exposed; library defaults are not relied on.

**Unhandled exceptions return generic 500.** No stack trace, class name, or message reaches the client. Server-side log retains full detail, joined via the correlation ID included in every Problem Detail (`correlationId` extension field and response header).

### Idempotency

The default approach is business-key idempotency. Aggregates expose a unique business identifier (external order ID, payment reference, etc.) enforced by aggregate invariant, repository lookup, and database unique constraint. Duplicate operations are detected via repository lookup before creation; handlers return the existing aggregate on duplicate input.

Technical idempotency (client-supplied `Idempotency-Key` headers, message-ID dedup for at-least-once delivery) is added per use case only when business-key dedup is insufficient — typically for payment APIs and Kafka consumers without a natural dedup key. When required, it is implemented as a cross-cutting capability with an `IdempotencyStore` port under `shared.application.port.out.idempotency`, an inbound interceptor under `shared.infrastructure.adapter.in.idempotency`, and a JDBC or Redis store implementation under `shared.infrastructure.adapter.out.idempotency`. These packages are added to the tree only when a use case requires the framework; they are not part of the default scaffold.

### Eventing

Domain events are emitted from aggregates and captured by the application layer. External publication goes through outbound ports only, implemented by the messaging adapter, with the transactional outbox pattern guaranteeing at-least-once delivery aligned with the source transaction.

**Inbound integration events always map to commands.** The adapter unwraps the envelope, translates external vocabulary to a local command, and invokes a handler. The handler decides what (if anything) happens, loads the aggregate, mutates, and emits its own domain event. Projection updates go through a command handler the same way as state changes — keeping transaction boundary, idempotency, and audit trail consistent.

**CloudEvents** is the envelope format for all messaging boundaries. The envelope, binding helpers, and SDK dependency live in `shared.infrastructure.adapter.out.messaging.cloudevents` and are reused by inbound Kafka consumers when unwrapping incoming events. The application and domain layers never import the CloudEvents SDK; they work with domain events and let the adapter wrap or unwrap them.

### Event versioning

Events crossing bounded-context or service boundaries are contracts and must be versioned.

**Default policy: backward compatibility.** New consumers must be able to read historical events. Additive changes (new optional fields, new optional metadata) are non-breaking. Field removal, renaming, type change, or semantic change are breaking and require a new event type.

**Versioning convention.** Major version is encoded in the CloudEvents `type` attribute (`com.example.order.OrderPlaced.v1`, `...v2`). Minor (backward-compatible) changes do not change the `type`; the `dataschema` attribute points to the current schema URL.

**Schema registry.** Event payloads validate against JSON Schema (or Avro) registered in a schema registry. Compatibility mode is `BACKWARD` per subject. Producers cannot publish events that fail compatibility check.

**Coexistence on breaking change.** When introducing a new major version, producers publish both old and new versions during a transition window. Consumers migrate independently. Old version is retired only after all consumers have moved and retention window has passed.

**Scope.** Events within a single bounded context (deployed together) may evolve freely. Events crossing bounded-context boundaries require backward compatibility and schema registry validation. Events published to external consumers require an explicit deprecation policy and version SLA, defined at the system level.

**Ownership.** Domain events emitted from aggregates are internal types and carry no version. The outbound messaging adapter translates them to versioned external event contracts; the mapping between internal domain event and external event version lives in the adapter and isolates the domain from versioning concerns.

### Contract testing

Contracts are provider-driven and schema-first. The provider's OpenAPI specification is the authoritative contract.

**Provider services** verify that the implementation matches the published spec using REST Assured plus OpenAPI request/response validation. Tests live under `<context>.contract.provider`.

**Consumer services** depend on the provider's OpenAPI spec as a build artifact and verify their usage stays within it using Pact tests in bidirectional mode. Tests live under `<context>.contract.consumer`. Consumer-driven Pact (provider verifies live consumer pacts) is not used.

When a Pact Broker is available, bidirectional verification gates deployment via `can-i-deploy`.

### Database migrations

Each bounded context owns its database tables and Flyway migrations. Migrations live under `src/main/resources/db/migration/<context>/`. The shared kernel owns cross-cutting tables (outbox, idempotency store) under `db/migration/shared/`. All migrations target a single schema; bounded contexts are isolated by table-name prefix (`order_*`, `customer_*`, `shared_*`).

Migration version numbers form a single global sequence. When adding a migration in any context, use the next available version. Concurrent migrations across contexts are resolved in review, the same way as concurrent code changes.

Cross-context coupling is forbidden at three levels:
- **Migrations.** A migration in `db/migration/<context>/` may only touch tables owned by that context.
- **Data movement.** SQL must not copy or move rows between contexts. Cross-context data flow is implemented as application code using domain events.
- **References.** Tables in one context must not declare foreign keys to tables in another context, and queries must not join across contexts. A context holds an ID (plain column, no FK constraint) and obtains data via the source context's API or via a local projection built from domain events.

When a bounded context is extracted to a separate service, its migration folder moves with it. The new service runs the same migrations against its own database; the monolith drops the location from its Flyway configuration. Tables may be renamed to drop the prefix at that point as a cosmetic follow-up.

### Scheduled jobs

Scheduled jobs are inbound adapters under `infrastructure.adapter.in.scheduler`. The job method extracts arguments (time window, batch size) and invokes a command or query handler. Domain logic, transactions, and direct repository access do not appear in the job class.

### Dependency rules

All dependencies must follow a strict outside-in direction:
- `domain` must not depend on `application` or `infrastructure`
- `application` may depend only on `domain`
- `application` must not depend on `infrastructure`
- `application.service` depends only on `domain` and `application.port`
- `infrastructure` may depend on `application` and `domain`
- `infrastructure.adapter.in` depends only on `application.port.in`
- `infrastructure.adapter.out` depends only on `application.port.out` and `domain`

### Enforcement

Architecture rules in this document are enforced by ArchUnit tests under `src/test/java/org/example/shared/architecture`. Rules without a corresponding test are considered aspirational and subject to drift. When adding a new architectural constraint, add the test in the same change. When a test fails, fix the code — do not weaken the rule without explicit team discussion.

The minimum rule set encoded:
1. Layer dependency direction (outside-in only).
2. No framework dependencies in the domain (except JPA) or in the application (except Jakarta Validation and Jakarta Transaction annotations).
3. Inbound adapters depend only on `application.port.in` (plus projection DTOs).
4. Outbound adapters depend only on `application.port.out` and `domain`.
5. No cross bounded-context dependencies (except via `shared`).
6. Transaction boundary at the application handler: `@Transactional` only on `application.service.*`; domain and adapter free of transaction annotations; handlers do not invoke other handlers (exemptions documented in a named allow-list, not introduced ad hoc).
7. `domain.repository` methods do not return projection DTOs; query-shape parameters belong to the query path.
8. One command/query = one handler.
9. Handlers (`@Transactional` methods) do not return types from `domain.model.*`.

Each test carries Javadoc explaining the rule, why it exists, common failure modes, and how to fix violations. The Javadoc is the canonical reference for "why this codebase looks the way it does".

### Naming convention

#### Domain types

| Type                 | Package                | Example                            |
|----------------------|------------------------|------------------------------------|
| Aggregate Root       | `domain.model`         | `Order`                            |
| Entity               | `domain.model`         | `OrderLine`, `Customer`            |
| Value Object         | `domain.model`         | `Money`, `OrderId`                 |
| Domain Event         | `domain.event`         | `OrderPlacedEvent`                 |
| Repository Interface | `domain.repository`    | `OrderRepository`                  |
| Domain Service       | `domain.service`       | `PricingService`                   |
| Policy               | `domain.policy`        | `DiscountPolicy`                   |
| Domain Exception     | `domain.exception`     | `OrderNotFoundException`           |

#### Application types

| Type             | Package                       | Example                  |
|------------------|-------------------------------|--------------------------|
| Command Contract | `application.port.in.command` | `CreateOrderCommand`     |
| Query Contract   | `application.port.in.query`   | `OrderHistoryQuery`      |
| Query Port       | `application.port.out.query`  | `OrderHistoryQueryPort`  |
| Use Case Handler | `application.service.command` | `CreateOrderHandler`     |
| Query Handler    | `application.service.query`   | `OrderHistoryHandler`    |
| Projection DTO   | `application.dto.projection`  | `OrderHistoryProjection` |

#### Infrastructure types

| Type                | Package                                                 | Example                     |
|---------------------|---------------------------------------------------------|-----------------------------|
| REST Resource       | `infrastructure.adapter.in.rest`                        | `OrderResource`             |
| REST Wire DTO       | `infrastructure.adapter.in.rest.dto`                    | `CreateOrderRequest`        |
| REST Wire Mapper    | `infrastructure.adapter.in.rest.mapper`                 | `OrderRestMapper`           |
| Kafka Consumer      | `infrastructure.adapter.in.messaging.kafka`             | `OrderEventConsumer`        |
| Scheduler           | `infrastructure.adapter.in.scheduler`                   | `OrderReconciliationJob`    |
| Persistence Adapter | `infrastructure.adapter.out.persistence.jpa`            | `OrderJpaRepositoryAdapter` |
| Query Adapter       | `infrastructure.adapter.out.persistence.query`          | `OrderHistoryQueryAdapter`  |
| Kafka Producer      | `infrastructure.adapter.out.messaging.kafka`            | `OrderEventProducer`        |
| Outbox Publisher    | `infrastructure.adapter.out.messaging.outbox`           | `OutboxEventPublisher`      |
| REST Client         | `infrastructure.adapter.out.client.rest`                | `PaymentServiceClient`      |
| Cache Adapter       | `infrastructure.adapter.out.cache.redis`                | `OrderCacheRepository`      |

# TODO

- Naming convention for test classes and methods
- Contract test http error code (500)
- Testcontainers
- Wire ArchUnit dependency and implement the minimum rule set 1–9 (see "Enforcement")
- Correlation ID propagation (verify Quarkus OpenTelemetry coverage before designing custom solution)
- JaCoCo code coverage
- OWASP dependency check
- ErrorProne
- OIDC security
- Example code
- Example tests

## AI Disclosure

This project uses artificial intelligence tools for research, coding, or documentation.
All final content was reviewed, edited, and validated by the human author before publication.

## See also

- [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Putting together DDD, Hexagonal, Onion, Clean, CQRS](https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/)
- [CQRS](https://martinfowler.com/bliki/CQRS.html)
- [Another Quarkus scaffold](https://github.com/andredesousa/advanced-quarkus-scaffold/tree/main)
- [Quarkus best practices](https://github.com/andredesousa/quarkus-best-practices)
