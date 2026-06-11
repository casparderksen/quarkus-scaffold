# Testing strategy

Test types map to the package structure documented in [README](../README.md#package-structure).
Each section lists the toolchain, the package location, what the tests cover, and which test doubles
are permitted.

## Test types overview

| Type          | Package                          | Tooling                                                   |
|---------------|----------------------------------|-----------------------------------------------------------|
| Unit          | `<context>.unit`                 | JUnit 5, AssertJ, Mockito                                 |
| Integration   | `<context>.integration`          | QuarkusTest, Testcontainers, REST Assured                 |
| Contract      | `<context>.contract.{provider,consumer}` | REST Assured + OpenAPI validator, Pact (bidirectional) |
| E2E           | `<context>.e2e`                  | REST Assured, Cucumber (BDD), Playwright (if UI)          |
| Architecture  | `shared.architecture`            | ArchUnit                                                  |
| Performance   | `<context>.performance`          | JMH (micro), Gatling/k6 (load)                            |

Shared test infrastructure lives under `shared.{architecture, container, config, mock, security, contract, clock, util}`
and `src/test/resources/{fixture, config, certificate}`.

## Unit tests

- **Goal:** Verify behavior in strict isolation.
- **Layer:** Domain, Application.
- **Package:** `<context>.unit`.
- **Toolchain:** JUnit 5 + AssertJ + Mockito.
- **Test doubles:**
  - Real objects (preferred).
  - Fakes (preferred for domain dependencies; e.g., in-memory repository fake).
  - Stubs (allowed in application layer for inbound data).
  - Mocks (application layer only, for outbound ports — repositories, clients, messaging).
- **Guidelines:**
  - No external systems (DB, Kafka, HTTP, filesystem).
  - No framework boot (no `@QuarkusTest`).
  - Domain tests use only real objects and fakes — never mocks.
  - Application tests may mock outbound ports.
  - Time-dependent logic uses fixed clock from `shared.clock`.
  - Fixtures (object mothers, builders) live in `<context>.fixture`.

## Integration tests

- **Goal:** Verify collaboration between components and real infrastructure behavior.
- **Layer:** Application, Infrastructure (plus domain behavior under real persistence).
- **Package:** `<context>.integration`.
- **Toolchain:** `@QuarkusTest` / `@QuarkusIntegrationTest`, Testcontainers (Postgres, Kafka, Redis),
  REST Assured for HTTP-driven cases.
- **Test doubles:**
  - Real infrastructure (preferred): DB, Kafka, Redis via Testcontainers.
  - Stubs (allowed only for uncontrollable third-party systems).
  - Fakes (clock, lightweight adapters).
- **Guidelines:**
  - Validate wiring of ports and adapters.
  - Shared Testcontainers setup lives in `shared.container`.
  - Open-Session-In-View is disabled — assert DTOs/projections returned from handlers, never managed entities.
  - Cover Flyway migration runs against a real Postgres container.
  - Assert outbox row persisted in same transaction as aggregate mutation
    ([README — Eventing](../README.md#eventing)).
  - Cover idempotency store behavior where applicable
    ([README — Idempotency](../README.md#idempotency)).
  - Do not replace infrastructure with mocks unless the system is external and unavailable.

## Contract tests

Contracts are provider-driven and schema-first. The provider's OpenAPI specification is authoritative.
See [README — Contract testing](../README.md#contract-testing).

### Provider side (`<context>.contract.provider`)

- **Goal:** Verify the running service matches its published OpenAPI spec.
- **Toolchain:** REST Assured + OpenAPI request/response validator (Atlassian swagger-request-validator),
  Pact provider verification in bidirectional mode.
- **Guidelines:**
  - Boot the service with `@QuarkusTest`.
  - Validate every documented operation against the spec.
  - For events: validate published CloudEvents envelope and payload against the JSON Schema
    registered in the schema registry. Compatibility mode `BACKWARD`.
  - No business assertions — shape and status only.

### Consumer side (`<context>.contract.consumer`)

- **Goal:** Verify this service's usage of an upstream provider stays within the provider's published spec.
- **Toolchain:** Pact in bidirectional mode against the provider's OpenAPI artifact.
- **Guidelines:**
  - Pact files published to the Pact Broker; deployment gated by `can-i-deploy` when available.
  - Consumer-driven Pact (provider verifies live consumer pacts) is not used.

Shared contract test utilities live in `shared.contract`.

## End-to-end tests

- **Goal:** Validate full system workflows from entrypoint to persistence/messaging.
- **Layer:** All layers, black box.
- **Package:** `<context>.e2e`.
- **Toolchain:** REST Assured for HTTP, Cucumber for BDD feature specs, Playwright for UI flows (when present).
- **Test doubles:**
  - Real systems (preferred: full stack via Testcontainers or deployed environment).
  - External stubs (only for uncontrollable third-party dependencies).
  - No mocks. Fakes only when unavoidable.
- **Guidelines:**
  - Production-like configuration.
  - Drive through public entrypoints (REST, Kafka topics).
  - Cover critical user journeys, not edge cases.
  - Feature files (`.feature`) live under `src/test/resources/fixture/<context>`.

## Architecture tests

- **Goal:** Enforce structural rules (DDD boundaries, hexagonal layering, dependency direction).
- **Layer:** Cross-cutting, no runtime execution.
- **Package:** `shared.architecture`.
- **Toolchain:** ArchUnit.
- **Guidelines:**
  - Encode the minimum rule set 1–9 from [README — Enforcement](../README.md#enforcement).
  - Every architectural constraint in the documentation has a corresponding ArchUnit test;
    rules without a test are aspirational and subject to drift.
  - Each test carries Javadoc explaining the rule, common failure modes, and how to fix violations.
  - Fail the build on architectural violations.

## Performance tests

- **Goal:** Validate scalability, throughput, latency, and concurrency behavior.
- **Layer:** Application, Infrastructure (sometimes full stack).
- **Package:** `<context>.performance`.
- **Toolchain:** JMH for micro-benchmarks, Gatling or k6 for load and concurrency.
- **Test doubles:**
  - Real systems (preferred).
  - Fakes only to isolate external dependencies that would skew measurements.
- **Guidelines:**
  - Production-like infrastructure.
  - Measure DB, cache, messaging, and API performance.
  - Avoid test doubles that hide bottlenecks.
  - Run on a dedicated environment; not part of the standard CI gate.

## Cross-cutting test infrastructure

| Package                  | Purpose                                                                 |
|--------------------------|-------------------------------------------------------------------------|
| `<context>.fixture`      | Object mothers, builders, test data generators per bounded context.     |
| `shared.architecture`    | ArchUnit rules (minimum rule set 1–9).                                  |
| `shared.container`       | Reusable Testcontainers definitions (Postgres, Kafka, Redis).           |
| `shared.config`          | Shared test `ConfigMapping` interfaces.                                 |
| `shared.mock`            | Shared mocks, stubs, fakes.                                             |
| `shared.security`        | JWT/authentication test helpers (token minting, role injection).        |
| `shared.contract`        | Shared consumer/provider contract test utilities.                       |
| `shared.clock`           | Fixed/test clock implementations for deterministic time-based tests.    |
| `shared.util`            | Low-level test-only utilities.                                          |

Static fixture assets (JSON payloads, Cucumber `.feature` files, schemas) live under
`src/test/resources/fixture/<context>/`. Test configuration and certificates live under
`src/test/resources/config/` and `src/test/resources/certificate/`.
