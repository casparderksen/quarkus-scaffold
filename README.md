# Quarkus Scaffold

Template for Quarkus applications.

## Tech Stack

- Runtime: Quarkus 3.33.1.1, Java 25
- Maven builds
- Static analysis: Checkstyle, PMD, Spotbugs, Spotless (Palantir formatting)
- Dockerfiles for JVM and native image builds in Docker container (2 stages each)
- REST: `quarkus-rest-jackson` (reactive) with HTTP problem responses according to RFC9457 and RFC7807
- ORM: Hibernate ORM with Panache + Flyway + PostgreSQL/H2
- Mapping: MapStruct 
- Testing: AssertJ, REST Assured, Mockito
- Observability: Micrometer metrics, OpenTelemetry traces, JSON logging for prod
- API docs: SmallRye OpenAPI at `/q/openapi`, Swagger UI at `/q/swagger-ui`
- OpenAPI: docs served at `/q/openapi`, compiled to `target/openapi`
- Swagger UI at `/q/swagger-ui`
- Health endpoint at `/q/health`

## Architecture

### Package structure

The application is structured according to Hexagonal / DDD / Clean Architecture concepts. 

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
│   │   │      ├── client             # Outbound external system contracts (HTTP/gRPC/SOAP)
│   │   │      └── messaging          # Outbound async messaging/eventing contracts
│   │   ├── usecase                   # UC implementations: manages transactions and orchestrates domain & outbound ports
│   │   ├── dto                       # Request/response models
│   │   ├── validation                # Input validations
│   │   └── mapper                    # DTO ↔ Domain mapping
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
│           │       └── http          # REST/HTTP clients
│           └── config                # Application configuration interfaces (ConfigMapping)
│   
└── shared                            # Reusable frameworks and technical utilities (if applicable)
    ├── domain                        # Domain layer shared
    │   ├── kernel                    # Shared domain primitives
    │   ├── event                     # Business events base (abstract)
    │   └── exception                 # Business exceptions base (abstract)
    │
    ├── application                   # Application layer
    │   └── cloudevents               # CloudEvents model
    │
    └── infrastructure                # Infrastructure layer
        ├── adapter                   # Hexagonal adapters
        │   ├── in                    # Inbound adapters (driving adapters)
        │   │   ├── idempotency       # Idempotency framework 
        │   │   └── scheduler         # Scheduler implementation
        │   └── out                   # Outbound adapters (driven adapters)
        │       ├── messaging         # Messaging implementations
        │       │   └── outbox        # Transactional outbox implementation
        │       ├── client            # External service integrations
        │       │   └── notifications # Notifications framework
        │       └── cache             # External cache implementations (Redis)
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

### Naming conventions



# TODO

- Testcontainers 
- Jaccoco code coverage
- IT for info endpoint
- IT for health endpoint
- OIDC security
- ORM: Hibernate ORM with Panache + Flyway + PostgreSQL/H2
- Mapping: MapStruct (annotation processor wired in compiler plugin)
- Testing: AssertJ, REST Assured, Mockito, BDD functional tests

## See also

- [Another Quarkus scaffold](https://github.com/andredesousa/advanced-quarkus-scaffold/tree/main)
- [Quarkus best practices](https://github.com/andredesousa/quarkus-best-practices)
