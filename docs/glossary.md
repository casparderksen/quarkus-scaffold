# Glossary

The concepts this project's architecture, design, and testing rest on.

## Contents

- [Architectural quality](#architectural-quality)
- [Domain-Driven Design](#domain-driven-design)
- [Layered architecture](#layered-architecture)
- [Hexagonal architecture](#hexagonal-architecture)
- [Clean Architecture](#clean-architecture)
- [CQRS](#cqrs)
- [Persistence](#persistence)
- [Error model](#error-model)
- [Eventing and messaging](#eventing-and-messaging)
- [Integration](#integration)
- [Resilience](#resilience)
- [Security](#security)
- [Observability](#observability)
- [Anti-patterns](#anti-patterns)
- [Testing — core test types](#testing--core-test-types)
- [Testing — test doubles](#testing--test-doubles)
- [Testing — test data](#testing--test-data)
- [Testing — execution](#testing--execution)
- [Testing — contract and infrastructure](#testing--contract-and-infrastructure)
- [Testing — quality](#testing--quality)

## Architectural quality

- [Cohesion](glossary/cohesion.md)
- [Coupling](glossary/coupling.md)
- [Dependency Direction](glossary/dependency-direction.md)
- [Dependency Inversion](glossary/dependency-inversion.md)
- [Encapsulation](glossary/encapsulation.md)
- [Information Hiding](glossary/information-hiding.md)
- [Outside-in Rule](glossary/outside-in-rule.md)
- [Separation of Concerns](glossary/separation-of-concerns.md)
- [Single Responsibility Principle (SRP)](glossary/single-responsibility-principle.md)

## Domain-Driven Design

- [Aggregate](glossary/aggregate.md)
- [Aggregate Root](glossary/aggregate-root.md)
- [Anti-Corruption Layer (ACL)](glossary/anti-corruption-layer.md)
- [Bounded Context](glossary/bounded-context.md)
- [Consistency Boundary](glossary/consistency-boundary.md)
- [Context Map](glossary/context-map.md)
- [Domain Event](glossary/domain-event.md)
- [Domain Service](glossary/domain-service.md)
- [Domain-Driven Design (DDD)](glossary/domain-driven-design.md)
- [Entity](glossary/entity.md)
- [Factory (Domain Factory)](glossary/domain-factory.md)
- [Invariant](glossary/invariant.md)
- [Modular Monolith](glossary/modular-monolith.md)
- [Open-Host Service (OHS)](glossary/open-host-service.md)
- [Policy](glossary/policy.md)
- [Published Language](glossary/published-language.md)
- [Repository](glossary/repository.md)
- [Shared Kernel](glossary/shared-kernel.md)
- [Specification](glossary/specification.md)
- [Ubiquitous Language](glossary/ubiquitous-language.md)
- [Upstream / Downstream Context](glossary/upstream-downstream-context.md)
- [Value Object](glossary/value-object.md)

## Layered architecture

- [Application Layer](glossary/application-layer.md)
- [Domain Layer](glossary/domain-layer.md)
- [Infrastructure Layer](glossary/infrastructure-layer.md)
- [Layered Architecture](glossary/layered-architecture.md)

## Hexagonal architecture

- [Adapter](glossary/adapter.md)
- [Application Core](glossary/application-core.md)
- [Boundary](glossary/boundary.md)
- [Cache Adapter](glossary/cache-adapter.md)
- [Client Adapter](glossary/client-adapter.md)
- [Driven Adapter](glossary/driven-adapter.md)
- [Driving Adapter](glossary/driving-adapter.md)
- [Hexagonal Architecture](glossary/hexagonal-architecture.md)
- [Inbound Adapter](glossary/inbound-adapter.md)
- [Inbound Port](glossary/inbound-port.md)
- [Messaging Adapter](glossary/messaging-adapter.md)
- [Outbound Adapter](glossary/outbound-adapter.md)
- [Outbound Port](glossary/outbound-port.md)
- [Persistence Adapter](glossary/persistence-adapter.md)
- [Port](glossary/port.md)

## Clean Architecture

- [Clean Architecture](glossary/clean-architecture.md)
- [Configuration Mapping](glossary/configuration-mapping.md)
- [DTO (Data Transfer Object)](glossary/dto.md)
- [Handler](glossary/handler.md)
- [Handler Return Contract](glossary/handler-return-contract.md)
- [Mapper](glossary/mapper.md)
- [Projection DTO](glossary/projection-dto.md)
- [Transaction Boundary](glossary/transaction-boundary.md)
- [Use Case](glossary/use-case.md)
- [Validation](glossary/validation.md)
- [Wire DTO](glossary/wire-dto.md)

## CQRS

- [CQRS (Command Query Responsibility Segregation)](glossary/cqrs.md)
- [CQRS-lite](glossary/cqrs-lite.md)
- [Command](glossary/command.md)
- [Event Sourcing](glossary/event-sourcing.md)
- [Event Store](glossary/event-store.md)
- [Projection](glossary/projection.md)
- [Query](glossary/query.md)
- [Query Port](glossary/query-port.md)
- [Read Model](glossary/read-model.md)
- [Read Path](glossary/read-path.md)
- [Write Path](glossary/write-path.md)

## Persistence

- [Detached Entity](glossary/detached-entity.md)
- [Dirty Checking](glossary/dirty-checking.md)
- [Managed Entity](glossary/managed-entity.md)
- [N+1 Query](glossary/n-plus-one-query.md)
- [ORM in the Domain Model](glossary/orm-in-the-domain-model.md)
- [Open-Session-In-View (OSIV)](glossary/open-session-in-view.md)
- [Persistence Context](glossary/persistence-context.md)

## Error model

- [Correlation ID](glossary/correlation-id.md)
- [Domain Exception](glossary/domain-exception.md)
- [Problem Detail Catalog](glossary/problem-detail-catalog.md)
- [RFC 9457 Problem Details](glossary/rfc-9457-problem-details.md)
- [Status Code Policy](glossary/status-code-policy.md)

## Eventing and messaging

- [At-least-once Delivery](glossary/at-least-once-delivery.md)
- [Backward Compatibility](glossary/backward-compatibility.md)
- [Business-key Idempotency](glossary/business-key-idempotency.md)
- [Choreography](glossary/choreography.md)
- [CloudEvents](glossary/cloudevents.md)
- [Compensating Transaction](glossary/compensating-transaction.md)
- [Dead Letter Queue (DLQ)](glossary/dead-letter-queue.md)
- [Event Versioning](glossary/event-versioning.md)
- [Eventual Consistency](glossary/eventual-consistency.md)
- [Idempotency](glossary/idempotency.md)
- [Idempotent Consumer](glossary/idempotent-consumer.md)
- [Integration Event](glossary/integration-event.md)
- [Orchestration](glossary/orchestration.md)
- [Process Manager](glossary/process-manager.md)
- [Saga Pattern](glossary/saga-pattern.md)
- [Schema Registry](glossary/schema-registry.md)
- [Technical Idempotency](glossary/technical-idempotency.md)
- [Transactional Outbox](glossary/transactional-outbox.md)

## Integration

- [API Gateway](glossary/api-gateway.md)
- [AsyncAPI](glossary/asyncapi.md)
- [Claim-Check Pattern](glossary/claim-check-pattern.md)
- [Event Bus](glossary/event-bus.md)
- [Event-Driven Architecture](glossary/event-driven-architecture.md)
- [GraphQL](glossary/graphql.md)
- [HTTP](glossary/http.md)
- [Integration Pattern](glossary/integration-pattern.md)
- [JSON](glossary/json.md)
- [JSON Schema](glossary/json-schema.md)
- [Load Balancing](glossary/load-balancing.md)
- [Message Broker](glossary/message-broker.md)
- [Microservices](glossary/microservices.md)
- [OpenAPI](glossary/openapi.md)
- [REST](glossary/rest.md)
- [SOAP](glossary/soap.md)
- [Service Discovery](glossary/service-discovery.md)
- [XML](glossary/xml.md)

## Resilience

- [Bulkhead Pattern](glossary/bulkhead-pattern.md)
- [Circuit Breaker](glossary/circuit-breaker.md)
- [Retry Policy](glossary/retry-policy.md)
- [Timeout Pattern](glossary/timeout-pattern.md)

## Security

- [ABAC (Attribute-Based Access Control)](glossary/abac.md)
- [Authentication](glossary/authentication.md)
- [Authorization](glossary/authorization.md)
- [Certificate](glossary/certificate.md)
- [Certificate Authority (CA)](glossary/certificate-authority.md)
- [Digital Signature](glossary/digital-signature.md)
- [Encryption](glossary/encryption.md)
- [JWT (JSON Web Token)](glossary/jwt.md)
- [MFA / 2FA](glossary/mfa-2fa.md)
- [OAuth 2.0](glossary/oauth-2.md)
- [OpenID Connect](glossary/openid-connect.md)
- [PKI (Public Key Infrastructure)](glossary/pki.md)
- [Private Key / Public Key](glossary/private-public-key.md)
- [RBAC (Role-Based Access Control)](glossary/rbac.md)
- [Zero Trust](glossary/zero-trust.md)

## Observability

- [Distributed Tracing](glossary/distributed-tracing.md)
- [Health Check](glossary/health-check.md)
- [Liveness Probe](glossary/liveness-probe.md)
- [Metrics](glossary/metrics.md)
- [Observability](glossary/observability.md)
- [Readiness Probe](glossary/readiness-probe.md)
- [Structured Logging](glossary/structured-logging.md)

## Anti-patterns

- [Anemic Domain Model](glossary/anemic-domain-model.md)
- [Big Ball of Mud](glossary/big-ball-of-mud.md)
- [Chatty Service Communication](glossary/chatty-service-communication.md)
- [Distributed Monolith](glossary/distributed-monolith.md)
- [Entity Service Trap](glossary/entity-service-trap.md)
- [God Service](glossary/god-service.md)
- [Golden Hammer](glossary/golden-hammer.md)
- [Leaky Abstraction](glossary/leaky-abstraction.md)
- [Over-Mocking](glossary/over-mocking.md)
- [Premature Optimization](glossary/premature-optimization.md)
- [Shared Database Anti-Pattern](glossary/shared-database-anti-pattern.md)
- [Smart Controller / Thin Domain](glossary/smart-controller-thin-domain.md)
- [Technical Debt](glossary/technical-debt.md)
- [Temporal Coupling](glossary/temporal-coupling.md)
- [Transaction Script](glossary/transaction-script.md)
- [Vendor Lock-In](glossary/vendor-lock-in.md)

## Testing — core test types

- [Acceptance Test](glossary/acceptance-test.md)
- [Architecture Test](glossary/architecture-test.md)
- [Bidirectional Contract Testing](glossary/bidirectional-contract-testing.md)
- [Contract Test](glossary/contract-test.md)
- [End-to-End (E2E) Test](glossary/end-to-end-test.md)
- [Integration Test](glossary/integration-test.md)
- [Performance Test](glossary/performance-test.md)
- [Regression Test](glossary/regression-test.md)
- [Smoke Test](glossary/smoke-test.md)
- [Snapshot Test](glossary/snapshot-test.md)
- [Unit Test](glossary/unit-test.md)

## Testing — test doubles

- [Dummy](glossary/dummy.md)
- [Fake](glossary/fake.md)
- [Mock](glossary/mock.md)
- [Spy](glossary/spy.md)
- [Stub](glossary/stub.md)
- [Test Double](glossary/test-double.md)

## Testing — test data

- [Factory (Test Factory)](glossary/test-factory.md)
- [Fixture](glossary/fixture.md)
- [Fixture File](glossary/fixture-file.md)
- [Generator](glossary/generator.md)
- [Object Mother](glossary/object-mother.md)
- [Seed Data](glossary/seed-data.md)
- [Test Data Builder](glossary/test-data-builder.md)

## Testing — execution

- [Assertion](glossary/assertion.md)
- [Flaky Test](glossary/flaky-test.md)
- [Golden Master Test](glossary/golden-master-test.md)
- [Parameterized Test](glossary/parameterized-test.md)
- [Property-Based Test](glossary/property-based-test.md)
- [Setup / Teardown](glossary/setup-teardown.md)
- [Test Case](glossary/test-case.md)
- [Test Suite](glossary/test-suite.md)

## Testing — contract and infrastructure

- [Consumer-Driven Contract](glossary/consumer-driven-contract.md)
- [Embedded Database](glossary/embedded-database.md)
- [OpenAPI Validation](glossary/openapi-validation.md)
- [Pact](glossary/pact.md)
- [Sandbox Environment](glossary/sandbox-environment.md)
- [Testcontainers](glossary/testcontainers.md)
- [WireMock](glossary/wiremock.md)

## Testing — quality

- [Chaos Test](glossary/chaos-test.md)
- [Code Coverage](glossary/code-coverage.md)
- [Concurrency Test](glossary/concurrency-test.md)
- [Deterministic Test](glossary/deterministic-test.md)
- [Idempotent Test](glossary/idempotent-test.md)
- [Isolation](glossary/isolation.md)
- [Mutation Testing](glossary/mutation-testing.md)
- [Observability Testing](glossary/observability-testing.md)
