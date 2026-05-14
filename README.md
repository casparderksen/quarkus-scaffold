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

Hexagonal Architecture / DDD package layout.

```
org.examople.<bounded-context>
 ├── application                    # Application orchestration layer
 │   ├── port                       # Outbound ports (notifications, external services)
 │   └── usecase                    # Use cases exposed to adapters
 │       ├── command                # Inbound commands ports
 │       └── query                  # Inbound query ports
 │
 ├── domain                         # Business domain core
 │   ├── event                      # Domain events emitted by aggregates
 │   ├── model                      # Aggregate roots, entities, value objects
 │   ├── repository                 # Repository ports (outbound contracts)
 │   └── service                    # Domain services containing cross-aggregate business rules
 │
 ├── infrastructure                 # Technical implementations and adapters
 │   ├── config                     # Configuration adapters
 │   ├── health                     # Health checks
 │   ├── messaging                  # Messaging adapters
 │   │   ├── event                  # CloudEvents mappers
 │   │   ├── kafka                  # Kafka inbound and outbound adapters
 │   │   └── outbox                 # Transactional outbox
 │   ├── persistence                # Persistence adapters
 │   │   ├── query                  # Query implementations (JPQL)
 │   │   └── repository             # Repository implementations (JPA/Panache)
 │   ├── api                        # Inbound REST resources
 │   │   ├── dto                    # HTTP request/response DTOs
 │   │   └── mapper                 # REST API ↔ application mapping
 │   └── client                     # Outbound REST/gRPC/SOAP adapters
 │
 └── shared                         # Cross-cutting technical utilities
     ├── util                       # Small generic helpers
     └── validation                 # Shared validators
```

## TODO

- Testcontainers 
- Jaccoco code coverage
- IT for info endpoint
- IT for health endpoint
- OIDC security
- ExmORM: Hibernate ORM with Panache + Flyway + PostgreSQL/H2
- Mapping: MapStruct (annotation processor wired in compiler plugin)
- Testing: AssertJ, REST Assured, Mockito, BDD functional tests

## See also

- [Another Quarkus scaffold](https://github.com/andredesousa/advanced-quarkus-scaffold/tree/main)
- [Quarkus best practices](https://github.com/andredesousa/quarkus-best-practices)