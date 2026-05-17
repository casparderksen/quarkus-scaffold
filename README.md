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
- Testing (contract): REST Assured + OpenAPI contract validation + Pact
- Testing (e2e): Black-box tests via REST Assured + Playwright (if UI exists) + Cucumber (BDD feature specs)
- Messaging: Apache Kafka (via SmallRye Reactive Messaging)
- Observability: Micrometer metrics, OpenTelemetry tracing, structured JSON logging, correlation IDs
- API documentation: SmallRye OpenAPI (`/q/openapi`), Swagger UI (`/q/swagger-ui`)
- Security: Quarkus Security (OIDC/OAuth2), JWT validation, RBAC/ABAC via policy layer
- Caching: Redis (via Quarkus Redis Client), optional Caffeine for local in-process caching
- Health: SmallRye Health (`/q/health`, `/q/health/live`, `/q/health/ready`) aligned with Kubernetes probes

## Architecture

### Package structure

The application is structured according to Hexagonal / DDD / Clean Architecture concepts.
- DDD strategic design: bounded contexts, aggregates, repositories
- Hexagonal architecture: ports and adapters
- Clean architecture: use cases, dependency flow and infrastructure isolation
- CQRS-lite separation (command/query): use for read-heavy UI pages and reports

The structure allows (eventual) extraction of services from a modulith, 
and sharing cross-cuttings concerns between teams (DDD kernel and infrastructure).

See [Glossary](doc/glossary.md) for an exlanation of concepts and see [Testing Strategy](doc/testing-strategy.md)
for testing guidelines supported by this template.

**Main package structure (`src/main/java`):**
```
org.example
├── <bounded-context>                 # Business domain bounded context
│   ├── domain                        # Domain core
│   │   ├── model                     # Aggregates, entities, value objects
│   │   ├── event                     # Domain events
│   │   ├── repository                # Repository contracts for aggregates
│   │   ├── service                   # Domain services spanning multiple aggregates
│   │   ├── policy                    # Decision logic (stateless business decisions)
│   │   ├── specification             # Selection/filtering predicates
│   │   ├── factory                   # Complex aggregate creation rules
│   │   └── exception                 # Business exceptions
│   │
│   ├── application                   # Application layer: orchestrates domain and external dependencies
│   │   ├── port                      # Hexagonal ports
│   │   │   ├── in                    # Inbound contracts (defines what the application provides)
│   │   │   │   ├── command           # State-changing use case contracts (write operations)
│   │   │   │   └── query             # Read-only use case contracts (queries / projections)
│   │   │   └──out                    # Outbound contracts (defines dependencies that the application needs)
│   │   │      ├── client             # Outbound external system contracts (REST/gRPC/SOAP)
│   │   │      ├── messaging          # Outbound async messaging/eventing contracts
│   │   │      └── cache              # Cache contracts (if needed)
│   │   ├── service                   # Application services / use case handlers
│   │   │   ├── command               # Command handlers: orchestrate outbound ports and domain / manage transactions
│   │   │   └── query                 # Query handlers/read orchestration
│   │   ├── dto                       # Transport oriented models
│   │   │   ├── request               # Incoming request models
│   │   │   ├── response              # Outgoing response models
│   │   │   └── projection            # Read-side projection models
│   │   ├── validation                # Input validations
│   │   └── mapper                    # DTO ↔ Domain mapping (pure structural mappings only)
│   │
│   └── infrastructure                # Technical implementation layer (adapters, frameworks, external systems)
│       └── adapter                   # Hexagonal adapters
│           │── in                    # Inbound adapters (driving adapters)
│           │   ├── rest              # REST controllers / HTTP entrypoints
│           │   └── kafka             # Kafka consumers (event-driven inbound)
│           │── out                   # Outbound adapters (driven adapters)
│           │   ├── persistence       # Database access implementations
│           │   │   ├── jpa           # JPA/Hibernate implementations (repositories)
│           │   │   └── query         # Read-side optimized queries / projections
│           │   ├── messaging         # Messaging implementations
│           │   │   └── kafka         # Kafka producers (event-driven outbound)
│           │   └── client            # External service integrations (REST/gRPC/SOAP)
│           └── config                # Application configuration interfaces (ConfigMapping)
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
    ├── application                   # Application layer
    │   └── cloudevents               # CloudEvents models/contracts
    │
    └── infrastructure                # Infrastructure layer
        ├── adapter                   # Hexagonal adapters
        │   ├── in                    # Inbound adapters (driving adapters)
        │   │   ├── idempotency       # Idempotency framework 
        │   │   └── scheduler         # Scheduler infrastructure
        │   └── out                   # Outbound adapters (driven adapters)
        │       ├── messaging         # Messaging implementations
        │       │   └── outbox        # Transactional outbox infrasructure
        │       ├── client            # External service integrations
        │       └── cache             # Cache infrasructure
        ├── security                  # Authentication & authorization infrastructure
        ├── observability             # Logging, metrics, tracing, correlation ID propagation (add subpackages)
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
│   ├── e2e                           # Black-box end-to-end system verification
│   └── performance                   # Load, stress, concurrency, and benchmark tests
│
└── shared                            # Reusable test infrastructure shared across bounded contexts
    ├── architecture                  # Structural and dependency rule enforcement tests
    ├── container                     # Shared Testcontainers setup (Postgres, Kafka, Redis, etc.)
    ├── config                        # Shared test configuration (ConfigMapping inferfaces)
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

### Pragmatic Exception: JPA in the Domain Model

Allowing JPA in the domain is a pragmatic exception to strict Hexagonal/Clean/DDD rules because it preserves 
Hibernate optimizations. With managed entities, Hibernate performs dirty checking and batched updates without 
extra SELECTs or explicit merge logic. Separating domain and persistence models can unintentionally force extra 
database roundtrips when re-attaching detached objects and introduces mapping overhead, while gaining relatively 
little in return for most CRUD-heavy systems.

Use queries and projections when you don’t need aggregate behavior or transactional consistency at the domain level,
typically for read-heavy use cases like lists, search, and reporting. In those cases, bypass entities entirely and 
return DTOs via JPQL, native queries, or dedicated read models to avoid lazy loading, N+1 queries, and unnecessary 
entity hydration. Command paths use entities; query paths use projections.

### Dependency rules

All dependencies must follow a strict outside-in direction:
- `domain` must not depend on `application` or `infrastructure`

- `application` may depend only on `domain`
- `application` must not depend on `infrastructure`
- `application.usecase` depends only on `domain` and `application.port`.

- `infrastructure` may depend on `application` and `domain`
- `infrastructure.adapter.in` depends only on `application.port.in`.
- `infrastructure.adapter.out` depends only on `application.port.out` and `domain`.

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
| Specification        | `domain.specification` | `EligibleForDiscountSpecification` |
| Factory              | `domain.factory`       | `OrderFactory`                     |
| Domain Exception     | `domain.exception`     | `OrderNotFoundException`           |

#### Application types 

| Type             | Package                       | Example                  |
|------------------|-------------------------------|--------------------------|
| Command Contract | `application.port.in.command` | `CreateOrderCommand`     |
| Query Contract   | `application.port.in.query`   | `OrderHistoryQuery`      |
| Use Case Handler | `application.service.command` | `CreateOrderHandler`     |
| Query Handler    | `application.service.query`   | `OrderHistoryHandler`    |
| Request DTO      | `application.dto.request`     | `CreateOrderRequest`     |
| Response DTO     | `application.dto.response`    | `OrderResponse`          |
| Projection DTO   | `application.dto.projection`  | `OrderHistoryProjection` |
| Mapper           | `application.mapper`          | `OrderMapper`            |
| Validator        | `application.validation`      | `CreateOrderValidator`   |

#### Infrastructure types

| Type                | Package                                                 | Example                     |
|---------------------|---------------------------------------------------------|-----------------------------|
| REST Resource       | `infrastructure.adapter.in.rest`                        | `OrderResource`             |
| Kafka Consumer      | `infrastructure.adapter.in.kafka`                       | `OrderEventConsumer`        |
| Scheduler           | `infrastructure.adapter.in.scheduler`                   | `OrderReconciliationJob`    |
| Persistence Adapter | `infrastructure.adapter.out.persistence.aggregate.jpa`  | `OrderJpaRepositoryAdapter` |
| Query Adapter       | `infrastructure.adapter.out.persistence.projection.sql` | `OrderQueryRepository`      |
| Kafka Producer      | `infrastructure.adapter.out.messaging.kafka`            | `OrderEventProducer`        |
| Outbox Publisher    | `infrastructure.adapter.out.messaging.outbox`           | `OutboxEventPublisher`      |
| REST Client         | `infrastructure.adapter.out.client.rest`                | `PaymentServiceClient`      |
| Cache Adapter       | `infrastructure.adapter.out.cache.redis`                | `OrderCacheRepository`      |

# TODO

- Testcontainers 
- ArchUnit
- Jaccoco code coverage
- Owasp dependency check
- ErrorProne
- OIDC security
- Example code
- Example tests

## See also

- [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Hexagonal Architecture](https://herbertograca.com/2017/11/16/explicit-architecture-01-ddd-hexagonal-onion-clean-cqrs-how-i-put-it-all-together/)
- [CQRS](https://martinfowler.com/bliki/CQRS.html)
- [Another Quarkus scaffold](https://github.com/andredesousa/advanced-quarkus-scaffold/tree/main)
- [Quarkus best practices](https://github.com/andredesousa/quarkus-best-practices)
