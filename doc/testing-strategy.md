# Testing strategy

## Unit tests

- **Goal:** Verify behavior in strict isolation
- **Layer:** Domain, Application
- **Test doubles:**
  - Real objects (preferred)
  - Fakes (preferred for domain dependencies)
  - Stubs (allowed in the application layer for inbound data / ports)
  - Mocks (allowed in the application layer for outbound ports only)
- **Guidelines:**
  - No external systems (DB, Kafka, HTTP, filesystem)
  - Domain tests must never use mocks (only real objects and fakes)
  - Application tests may mock outbound ports (repositories, clients, messaging)
  - Focus on behavior in the domain, and orchestration in the application layer
  - Keep tests deterministic and fast

## Integration tests

- **Goal:** Verify collaboration between components and real infrastructure behavior
- **Layer:** Application, Infrastructure (+ domain behavior)
- **Test doubles:**
  - Real infrastructure (preferred: DB, Kafka, Redis via Testcontainers)
  - Stubs (allowed only for external third-party systems)
  - Fakes (only clock, small adapters)
- **Guidelines:**
  - Validate wiring of ports and adapters
  - Prefer real database, messaging, cache systems (via Testcontainers)
  - Ensure repository implementations behave correctly
  - Do not replace infrastructure with mocks unless an external system is unavailable
  - Focus on the correctness of integration, not internal logic

## Contract tests

- **Goal:** Ensure compatibility between services (API/event schema stability)
- **Layer:** Application, Infrastructure boundaries
- **Test doubles:**
  - Stubs (consumer/provider simulation)
  - Fakes (lightweight provider implementations)
  - Mocks (occasionally for strict interaction verification)
- **Guidelines:**
  - Validate API schemas (OpenAPI) and event contracts (Kafka/CloudEvents)
  - Do not test business logic
  - Ensure backward/forward compatibility
  - Keep tests focused on contracts, not behavior

## End-to-End (E2E) tests

- **Goal:** Validate full system workflows from entrypoint to persistence/messaging
- **Layer:** All layers (Domain + Application + Infrastructure)
- **Test doubles:**
  - Real systems (preferred: full stack)
  - External stubs (only for uncontrollable third-party dependencies)
  - Do not use mocks (avoid completely) and fakes (unless absolutely necessary)
- **Guidelines:**
  - Treat systems as black box
  - Validate real business scenarios (user journeys)
  - Use production-like configuration
  - Minimize test doubles to external systems only
  - Focus on critical flows, not edge cases

## Architecture tests

- **Goal:** Enforce structural rules (DDD boundaries, hexagonal layering, dependency direction)
- **Layer:** Cross-cutting (no runtime execution)
- **Guidelines:**
  - Ensure the domain does not depend on infrastructure
  - Enforce application depends only on ports
  - Validate bounded context isolation
  - Check package-level dependency rules
  - Fail build on architectural violations

## Performance tests

- **Goal:** Validate scalability, throughput, latency, and concurrency behavior
- **Layer:** Application, Infrastructure (+ sometimes full stack)
- **Test doubles:**
  - Real systems (preferred)
  - Fakes (only for isolating external dependencies)
- **Guidelines:**
  - Must use production-like infrastructure
  - Measure DB, cache, messaging, and API performance
  - Avoid artificial test doubles that hide bottlenecks
  - Validate concurrency and load behavior
  - Focus on system-level metrics, not unit behavior