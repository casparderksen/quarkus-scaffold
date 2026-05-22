# AGENTS.md

## Architecture rules

- Domain must not depend on application or infrastructure
- No framework imports in domain, except for JPA annotations
- Application layer orchestrates domain only; no business rules in application services
- Transactions only in application layer (use case/handler boundary)
- Infrastructure implements application ports only and must not contain business logic
- Infrastructure adapters (REST/Kafka/scheduler) are entrypoints only (no domain logic)
- Shared kernel contains only stable primitives (no domain-specific logic)

## Coupling rules

- No cross bounded-context dependencies except via shared kernel primitives
- No DTOs in domain layer
- No direct external calls (HTTP/Kafka/DB) from domain or application logic; use ports/adapters instead

## Use case rules

- One command/query = one handler
- Command handlers mutate state; query handlers are read-only
- Use cases must be self-contained (no orchestration across multiple use cases)
- Mapping is local unless reused across multiple use cases

## Domain rules

- Invariants enforced inside domain model (constructors/methods)
- Domain services only for cross-aggregate logic
- Domain events emitted from aggregates, not application layer

## Eventing rules

- No direct external publishing from domain/application layer
- External events go through outbound ports only
- Use transactional outbox pattern

## Testing rules

- Domain tests run without framework boot
- Integration tests use Testcontainers for external dependencies
- Contract tests required for external APIs and messaging
- Prefer deterministic tests (e.g. fixed clock abstraction for time-dependent logic)