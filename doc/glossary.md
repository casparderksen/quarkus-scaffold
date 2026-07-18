# Glossary

Each entry uses a fixed shape:

- **Definition** — one line.
- **Why** — what problem the concept solves.
- **When** — when to reach for it.
- **Example** — concrete instance (where useful).
- **Contrast** — adjacent concepts and how they differ.
- **See also** — related entries.

Slots are omitted when not applicable. Terms are defined once; duplicates redirect via `See [X](#x)`.

---

## Architectural quality

### Separation of Concerns
Division of responsibilities into distinct components or layers.

- **Why** Reduces coupling and complexity; isolates change.
- **See also** [Coupling](#coupling), [Cohesion](#cohesion), [Single Responsibility Principle](#single-responsibility-principle-srp)

### Coupling
Degree of interdependence between components.

- **Why** Lower coupling improves maintainability, testability, reusability; limits ripple effect of changes.
- **How** Achieved via information hiding, interfaces, dependency inversion.
- **See also** [Cohesion](#cohesion), [Dependency Inversion](#dependency-inversion), [Information Hiding](#information-hiding)

### Cohesion
Degree to which responsibilities within a component belong together.

- **Why** High cohesion improves clarity, maintainability, testability, reusability.
- **How** Achieved via encapsulation and SRP.
- **See also** [Single Responsibility Principle](#single-responsibility-principle-srp), [Encapsulation](#encapsulation)

### Encapsulation
Bundling data and behavior into one cohesive unit.

- **Why** Enforces information hiding and separation of concerns.
- **See also** [Information Hiding](#information-hiding), [Cohesion](#cohesion)

### Information Hiding
Hiding internal implementation details from the outside world.

- **Why** Keeps interfaces clean; reduces coupling.
- **See also** [Encapsulation](#encapsulation), [Leaky Abstraction](#leaky-abstraction)

### Dependency Inversion
High-level logic depends on abstractions, not implementations.

- **Why** Central to hexagonal architecture; lets domain be tested and re-targeted without touching infrastructure.
- **Example** Application defines `OrderRepository` interface; JPA adapter implements it.
- **See also** [Hexagonal Architecture](#hexagonal-architecture), [Port](#port), [Dependency Direction](#dependency-direction)

### Single Responsibility Principle (SRP)
A unit has only one reason to change.

- **Why** Enforces loose coupling and separation of concerns.
- **See also** [Cohesion](#cohesion), [Encapsulation](#encapsulation)

### Dependency Direction
Rule governing allowed compile-time dependencies.

- **Why** Prevents domain or application from depending on transport/persistence concerns.
- **Rule** Outside-in only: `infrastructure → application → domain`. Never reverse.
- **See also** [Clean Architecture](#clean-architecture), [Outside-in Rule](#outside-in-rule)

### Outside-in Rule
Dependencies point inward toward the domain core.

- **Why** Keeps inner layers framework-free and stable.
- **Example** REST adapter depends on application port; application port does not know REST exists.
- **See also** [Dependency Direction](#dependency-direction), [Clean Architecture](#clean-architecture), [Hexagonal Architecture](#hexagonal-architecture)

---

## Layered architecture

### Layered Architecture
Architecture separating concerns into logical layers (domain, application, infrastructure).

- **Why** Predictable structure; supports outside-in dependency rule.
- **See also** [Hexagonal Architecture](#hexagonal-architecture), [Clean Architecture](#clean-architecture)

### Domain Layer
Core business layer containing domain logic and rules.

- **Why** Independent of frameworks and infrastructure; stable across technology changes.
- **Contents** Aggregates, entities, value objects, domain services, policies, repositories (interfaces), domain events, domain exceptions.
- **See also** [Application Layer](#application-layer), [Infrastructure Layer](#infrastructure-layer), [Domain-Driven Design](#domain-driven-design-ddd)

### Application Layer
Orchestration layer coordinating use cases and external dependencies.

- **Why** Owns transactions and workflow; keeps domain free of orchestration noise.
- **Rule** No core business rules; pure coordination.
- **Contents** Command/query contracts, handlers, outbound ports, projection DTOs.
- **See also** [Use Case](#use-case), [Handler](#handler), [Transaction Boundary](#transaction-boundary)

### Infrastructure Layer
Technical implementation layer integrating frameworks and external systems.

- **Contents** Adapters (REST, Kafka, JPA, scheduler), configuration.
- **Rule** Depends on application and domain; never the reverse.
- **See also** [Adapter](#adapter), [Outside-in Rule](#outside-in-rule)

---

## Hexagonal architecture

### Hexagonal Architecture
Architectural style isolating business logic from external systems via ports and adapters.

- **Also called** Ports and Adapters.
- **Why** Enables testing the core without infrastructure; lets technologies be swapped behind stable ports.
- **See also** [Port](#port), [Adapter](#adapter), [Dependency Inversion](#dependency-inversion)

### Port
Abstraction defining communication with the application core.

- **Why** Separates core logic from technical implementation.
- **Contrast** [Inbound Port](#inbound-port) = what the application offers; [Outbound Port](#outbound-port) = what the application needs.

### Inbound Port
Contract exposing application capabilities to external actors.

- **Example** `CreateOrderHandler` interface invoked by REST resource.
- **Contrast** Outbound port describes a dependency; inbound describes a capability.
- **See also** [Use Case](#use-case), [Command](#command), [Query](#query)

### Outbound Port
Contract describing a dependency required by the application core.

- **Categories** Persistence (`OrderRepository`), messaging (`OrderEventPublisher`), client (`PaymentServiceClient`), query (`OrderHistoryQueryPort`), cache (`OrderCacheRepository`).
- **See also** [Adapter](#adapter), [Dependency Inversion](#dependency-inversion)

### Adapter
Implementation connecting a port to an external system.

- **Why** Converts between external representation and internal model.
- **Contrast** [Inbound Adapter](#inbound-adapter) drives the application; [Outbound Adapter](#outbound-adapter) is driven by it.

### Inbound Adapter
Adapter driving the application (also: driving adapter).

- **Examples** REST controllers, Kafka consumers, scheduled jobs.
- **Rule** Entrypoint only; no domain logic, no transaction boundary.
- **See also** [Driving Adapter](#driving-adapter)

### Outbound Adapter
Adapter implementing an outbound port (also: driven adapter).

- **Examples** JPA repository, Kafka producer, REST client, Redis cache.
- **See also** [Driven Adapter](#driven-adapter)

### Driving Adapter
See [Inbound Adapter](#inbound-adapter).

### Driven Adapter
See [Outbound Adapter](#outbound-adapter).

### Application Core
Business-centric part of the system isolated from infrastructure.

- **Contents** Domain and application layers.
- **See also** [Hexagonal Architecture](#hexagonal-architecture)

### Boundary
Separation point between architectural responsibilities.

- **How** Enforced through ports, package structure, ArchUnit tests.

### Persistence Adapter
Outbound adapter implementing persistent storage.

- **Example** `OrderJpaRepositoryAdapter` implements `OrderRepository`.

### Messaging Adapter
Outbound or inbound adapter integrating asynchronous messaging.

- **Examples** Kafka producer/consumer.

### Client Adapter
Outbound adapter integrating external services over the wire.

- **Examples** REST, gRPC, SOAP clients.

### Cache Adapter
Outbound adapter integrating caching systems.

- **Examples** Redis, Caffeine.

---

## Domain-Driven Design

### Domain-Driven Design (DDD)
Approach to design centered on business domains and explicit domain models.

- **Why** Aligns code with business reality; reduces translation loss between experts and engineers.
- **Pillars** Bounded contexts, ubiquitous language, aggregates, repositories.

### Bounded Context
Logical boundary within which a domain model is consistent and meaningful.

- **Why** Same word (e.g., "Customer") means different things in Sales vs Billing; explicit boundaries prevent silent coupling.
- **Example** `order` context owns `Order`, `OrderLine`; `customer` context owns `CustomerProfile`.
- **Rule** No cross-context imports except via shared kernel primitives.
- **See also** [Context Map](#context-map), [Shared Kernel](#shared-kernel), [Anti-Corruption Layer](#anti-corruption-layer-acl)

### Ubiquitous Language
Shared language between developers and domain experts, reflected in code.

- **Why** Reduces translation ambiguity; class and method names match conversation.

### Aggregate
Consistency boundary grouping entities and value objects mutated as a single unit.

- **Why** Enforces invariants atomically; defines transaction scope (one aggregate per transaction).
- **When** Domain has rules spanning multiple objects that must hold together.
- **Example** `Order` rejects transition to `Shipped` unless state is `Paid`.
- **Rules** External access only through the root. References between aggregates by ID, never object reference. Children have no repository. Aggregate holds only invariant-bearing data; display-only fields go to projections.
- **Contrast** Entity = identity, no consistency role. Value Object = no identity, immutable.
- **See also** [Aggregate Root](#aggregate-root), [Invariant](#invariant), [Consistency Boundary](#consistency-boundary), [Repository](#repository)

### Aggregate Root
Primary entity controlling access to an aggregate.

- **Why** Single entrypoint guarantees invariants are enforced on every mutation.
- **Example** `Order` aggregates `OrderLine` items; external code never touches `OrderLine` directly.
- **See also** [Aggregate](#aggregate)

### Consistency Boundary
Scope within which invariants must hold atomically.

- **Why** Matches the aggregate; defines transaction scope.
- **Rule** Cross-aggregate consistency is eventual, via domain events.
- **See also** [Aggregate](#aggregate), [Eventual Consistency](#eventual-consistency)

### Entity
Domain object defined primarily by identity.

- **Why** Identity persists across state changes.
- **Example** `Customer` with stable `CustomerId`.
- **Contrast** [Value Object](#value-object) has no identity.

### Value Object
Immutable domain object defined only by values.

- **Why** No identity, freely shareable, free from aliasing bugs.
- **Example** `Money(amount, currency)`, `OrderId`.
- **Contrast** [Entity](#entity) has identity.

### Domain Event
Representation of something meaningful that happened in the domain.

- **Why** Captures business-significant state changes; decouples emitters from reactors.
- **Example** `OrderPlacedEvent(orderId, customerId, total)`.
- **Rule** Emitted from aggregates, not from application layer. Internal type, unversioned; external publication goes through messaging adapter that maps to versioned event.
- **See also** [Integration Event](#integration-event), [Transactional Outbox](#transactional-outbox)

### Domain Service
Domain logic that does not naturally belong to a single aggregate.

- **Why** Captures cross-aggregate behavior without forcing a god-aggregate.
- **Example** `PricingService` computes price across customer segment, order, and current promotions.
- **Contrast** [Policy](#policy) = stateless decision/computation. [Domain Service](#domain-service) = coordination across aggregates.

### Repository
Abstraction for loading and persisting aggregates.

- **Why** Hides persistence details from the domain.
- **Rules** Exists only for aggregate roots, never for child entities. Exposes aggregate-shaped operations (`save`, `delete`, `findById`, business-key lookups). Does not return projection DTOs. Does not take query-shape parameters (filters, sorts, pagination for display).
- **Contrast** [Query Port](#query-port) handles list/search/report shapes that don't match the aggregate.
- **See also** [Aggregate](#aggregate), [Query Port](#query-port)

### Specification
Reusable business predicate expressing selection or validation rules.

- **Why** Encapsulates a boolean rule so it can be reused across queries and validation.
- **Example** `EligibleForFreeShippingSpec.isSatisfiedBy(order) → boolean`.
- **Contrast** [Policy](#policy) returns a decision or value; Specification returns boolean.

### Policy
Stateless business decision or computation rule.

- **Why** Isolates "how we decide X" from aggregate state; survives independently of aggregate evolution.
- **Example** `ShippingCostPolicy.compute(weight, destination, carrier) → Money`.
- **Contrast** [Specification](#specification) returns boolean. [Domain Service](#domain-service) coordinates multiple aggregates; Policy decides.

### Factory (Domain Factory)
Component creating complex aggregates or entities.

- **Why** Encapsulates creation invariants; prevents invalid construction.
- **Example** `Order.create(customerId, lines, ...)` static factory on the aggregate.
- **See also** [Aggregate Root](#aggregate-root)

### Invariant
Business rule that must always hold within an aggregate boundary.

- **Example** "Order total equals sum of line totals after discounts."
- **Enforced** Inside aggregate constructors and methods.

### Shared Kernel
Small, explicitly shared subset of a domain model between bounded contexts.

- **Why** Allows reuse of stable primitives (IDs, Money, base types) without coupling business behavior.
- **Rule** Keep minimal; require strong coordination to change.
- **Contrast** [Anti-Corruption Layer](#anti-corruption-layer-acl) isolates rather than shares.

### Anti-Corruption Layer (ACL)
Translation layer protecting a bounded context from another model.

- **Why** Prevents foreign concepts from leaking into the local ubiquitous language.
- **Example** Adapter that translates legacy CRM's `Account` into local `Customer`.

### Context Map
High-level representation of relationships between bounded contexts.

- **Why** Documents integration patterns, ownership, and direction of dependency.
- **See also** [Open-Host Service](#open-host-service-ohs), [Upstream / Downstream Context](#upstream--downstream-context), [Shared Kernel](#shared-kernel), [Anti-Corruption Layer](#anti-corruption-layer-acl)

### Open-Host Service (OHS)
Published inbound API through which a bounded context offers its capabilities to other contexts.

- **Why** Gives consumers one stable front door (the provider's `application.port.in`) instead of reaching into its internals; the integration mechanism (in-process call vs REST) can change without touching consumers.
- **How** Consumer injects the provider's inbound port. In a modular monolith it is an in-process CDI call; on extraction a REST client adapter implements the same port unchanged.
- **Rule** The only permitted cross-context entry point. A consumer never touches another context's `domain`, `application.port.out`, `infrastructure`, or tables.
- **Contrast** [Shared Kernel](#shared-kernel) shares a model; OHS shares a *service*. [Anti-Corruption Layer](#anti-corruption-layer-acl) protects the consumer from a foreign model.
- **See also** [Published Language](#published-language), [Context Map](#context-map), [Distributed Monolith](#distributed-monolith), [Modular Monolith](#modular-monolith)

### Published Language
Well-documented shared contract a context exposes for integration.

- **Why** Consumers depend on a deliberately published vocabulary, not on internal domain types.
- **Example** The command/query contracts in `application.port.in`; the versioned integration events a context publishes.
- **See also** [Open-Host Service](#open-host-service-ohs), [Integration Event](#integration-event)

### Upstream / Downstream Context
Direction of influence between two integrated contexts.

- **Definition** Upstream (supplier) provides; downstream (consumer) depends. Changes in the upstream flow down to the downstream.
- **See also** [Open-Host Service](#open-host-service-ohs), [Context Map](#context-map), [Anti-Corruption Layer](#anti-corruption-layer-acl)

### Modular Monolith
Single deployable composed of independently designed modules (bounded contexts) with explicit boundaries.

- **Why** Captures most benefits of microservices (boundaries, ownership) without operational cost; supports later extraction.
- **See also** [Bounded Context](#bounded-context)

---

## Clean Architecture

### Clean Architecture
Architectural style organizing code around dependency direction toward business rules.

- **Rule** Inner layers must not depend on outer layers.
- **See also** [Outside-in Rule](#outside-in-rule), [Use Case](#use-case)

### Use Case
Business operation exposed by the application layer.

- **Why** Names a single application capability; one input, one outcome.
- **Example** "Create order", "List order history".
- **Implementation** [Command](#command) or [Query](#query) contract handled by a single [Handler](#handler).

### Handler
Application component implementing one use case.

- **Why** "One command/query = one handler" prevents service-layer monoliths.
- **Rules** Owns the transaction boundary. Does not invoke other handlers (no use-case chaining). Returns response DTO, projection, identifier value object, or void — never a managed JPA entity.
- **Naming** `<Verb><Noun>Handler` (e.g., `CreateOrderHandler`).
- **See also** [Use Case](#use-case), [Transaction Boundary](#transaction-boundary), [Handler Return Contract](#handler-return-contract)

### Handler Return Contract
Application handlers never return managed JPA entities or aggregates across the transaction boundary.

- **Why** Returning entities exposes mutation API and lazy associations to callers outside any transaction.
- **Rule** Return DTO/projection (built inside the transaction), identity value object (`OrderId`), or void.
- **See also** [Handler](#handler), [Open-Session-In-View](#open-session-in-view-osiv)

### DTO (Data Transfer Object)
Structure used to transfer data between layers or systems.

- **Why** Prevents leaking domain models externally; decouples wire format from internal model.
- **Variants** [Wire DTO](#wire-dto) (transport-coupled, in REST adapter), [Projection DTO](#projection-dto) (read-side application response shape), [Command](#command) / [Query](#query) (use-case input shape).

### Wire DTO
Transport-coupled request/response model living in the REST adapter.

- **Why** Holds Jackson and OpenAPI annotations; isolates transport concerns from the application contract.
- **Example** `CreateOrderRequest`, `OrderResponse`.
- **Rule** Owned by `infrastructure.adapter.in.rest.dto`; never reaches application or domain.
- **See also** [Command](#command), [Projection DTO](#projection-dto)

### Projection DTO
Read-side response shape returned by a query handler.

- **Why** Decouples read model from aggregate shape; enables optimized read paths.
- **Example** `OrderHistoryProjection`.
- **See also** [Projection](#projection), [Read Model](#read-model)

### Mapper
Component translating between models (Wire DTO ↔ Command/Projection, Aggregate ↔ Projection).

- **Why** Centralizes transformation; keeps adapters and handlers focused.
- **Rule** Pure transformation. No conditional enrichment or business decisions — those belong in policies or factories.

### Transaction Boundary
Scope within which changes are committed atomically.

- **Rule** `@Transactional` lives on application service methods only. Domain and adapters are transaction-annotation-free.
- **Rule** One aggregate mutated per transaction.
- **See also** [Handler](#handler), [Aggregate](#aggregate)

### Validation
Verification that incoming data satisfies structural or business requirements.

- **Layers**
  - **Wire validation** (`@NotBlank`, `@Size`, JSON parseability) on the Wire DTO in the REST adapter.
  - **Structural validation** of commands (required fields, enum values) on the command record or as guard clauses in the handler.
  - **Business validation** (invariants, eligibility, state transitions) in aggregate, policy, or domain service.
- **Rule** No dedicated `application.validation` package; pre-flight orchestration checks inline in the handler.

### Configuration Mapping
Binding configuration values into typed application objects.

- **Example** Quarkus `@ConfigMapping`.

---

## CQRS

### CQRS (Command Query Responsibility Segregation)
Separation of write operations from read operations.

- **Why** Different shapes, different scaling profiles, different consistency needs.
- **See also** [CQRS-lite](#cqrs-lite), [Command](#command), [Query](#query)

### CQRS-lite
Pragmatic CQRS without separate write/read databases or eventual-consistency projections.

- **Why** Captures the benefit of separating command and query paths in code without paying the operational cost of two stores.
- **When** Read-heavy UI pages and reports where aggregate shape doesn't match read shape.
- **See also** [CQRS](#cqrs-command-query-responsibility-segregation), [Projection](#projection)

### Command
Request that changes system state.

- **Why** Names the intent; carries domain values, not transport types.
- **Example** `CreateOrderCommand(customerId, lines)`.
- **Rule** Built from primitives or domain value objects; transport-free.
- **Contrast** [Query](#query) does not modify state.

### Query
Request retrieving data without modifying state.

- **Example** `OrderHistoryQuery(customerId, pageSize)`.
- **See also** [Query Port](#query-port), [Projection](#projection)

### Query Port
Outbound port for read-side queries that bypass aggregates.

- **Why** List, search, report, and cross-aggregate reads must not hydrate aggregates.
- **Example** `OrderHistoryQueryPort` implemented by JPQL or native SQL adapter returning projections.
- **See also** [Read Path](#read-path), [Projection](#projection)

### Read Model
Query-optimized representation of data.

- **Why** Denormalized for read performance; matches UI shape, not domain shape.
- **See also** [Projection](#projection)

### Projection
Transformed view of domain data optimized for reading.

- **Why** Avoids aggregate hydration for display-only reads.
- **Example** `OrderHistoryProjection` returned by a JPQL query.
- **Contrast** [Read Model](#read-model) is the broader concept; Projection is a specific shape returned from a query.

### Write Path
Command handlers load and mutate aggregates through `domain.repository`.

- **Rule** Domain repositories expose aggregate-shaped operations only.

### Read Path
Query handlers return projection DTOs.

- **Routing**
  - Single-aggregate read where projection matches aggregate shape: load through domain repository, map inside handler.
  - List, search, report, cross-aggregate: go through query port; never hydrate aggregates.
- **See also** [Query Port](#query-port), [Projection](#projection)

### Event Store
Persistent repository for domain events.

- **When** Event sourcing.

### Event Sourcing
Storing and replaying domain events as the source of truth.

- **Contrast** Standard persistence stores current state; event sourcing stores the sequence of changes.

---

## Persistence

### JPA in the Domain Model
Pragmatic exception: JPA annotations allowed on aggregates and entities.

- **Why** Preserves Hibernate dirty checking, batched updates, and avoids extra SELECTs from re-attaching detached objects.
- **Tradeoff** Domain is not fully framework-free, but separating domain and persistence models costs more than it saves for CRUD-heavy systems.
- **Boundary** Query paths bypass entities entirely and return DTOs via JPQL or native SQL.

### Persistence Context
Hibernate's session-scoped cache of managed entities.

- **Why** Enables dirty checking and identity guarantees.
- **Rule** Bound to the transaction; closes when the handler returns.

### Managed Entity
Entity attached to an active persistence context.

- **Why** Hibernate tracks changes and flushes them at commit.
- **Rule** Never returned across the transaction boundary.
- **See also** [Handler Return Contract](#handler-return-contract), [Open-Session-In-View](#open-session-in-view-osiv)

### Detached Entity
Entity that has left the persistence context.

- **Risk** Lazy associations throw on access; re-attachment requires explicit merge.

### Dirty Checking
Hibernate detects field changes on managed entities and writes them at flush.

- **Why** Removes need for explicit `update` calls.
- **Requires** Active persistence context for the duration of mutation.

### Open-Session-In-View (OSIV)
Anti-pattern keeping the Hibernate session open through serialization to the wire.

- **Why disabled** Hides N+1 queries; runs reads outside transaction consistency; couples wire format to persistence model.
- **Rule** Disabled. Mapping aggregate → DTO happens inside the handler while the session is open.
- **See also** [Handler Return Contract](#handler-return-contract), [N+1 Query](#n1-query)

### N+1 Query
Pattern where loading a list issues one query for the list plus one per item.

- **Why bad** Linear blow-up of database round-trips.
- **Cause** Lazy associations accessed during serialization or in loops.
- **Fix** Eager fetch where needed, or use the read path (projection query) instead of aggregate hydration.

---

## Error model

### RFC 9457 Problem Details
Standard format for HTTP error responses (`application/problem+json`).

- **Why** Machine-readable, extensible error payload with `type`, `title`, `status`, `detail`, `instance`, plus extension fields.
- **Replaces** RFC 7807 (superseded).

### Domain Exception
Transport-free exception carrying business context as typed fields.

- **Why** Reusable across REST, gRPC, messaging adapters; no HTTP coupling.
- **Rule** No HTTP status, no Problem Detail URI, no library annotations on the exception itself.
- **Example** `OrderNotFoundException(OrderId id)`.

### Problem Detail Catalog
Per-context registry mapping domain exceptions to HTTP status, Problem Detail `type` URI, and exposed extension fields.

- **Where** `infrastructure.adapter.in.rest.error`.
- **Why** Centralizes the mapping; makes the public error contract reviewable.

### Status Code Policy
Consistent mapping across bounded contexts: not found → 404, invariant violation → 409, authorization failure → 403, authentication failure → 401, validation failure → 400, unexpected → 500.

### Correlation ID
Identifier propagated across requests and services for traceability.

- **Why** Joins client-visible error and server-side log without leaking internal detail.
- **Surfacing** `correlationId` extension field in Problem Detail and response header.
- **See also** [Distributed Tracing](#distributed-tracing), [Observability](#observability)

---

## Eventing and messaging

### Integration Event
Event published for consumption by external systems or other bounded contexts.

- **Contrast** [Domain Event](#domain-event) is internal and unversioned; integration event is a versioned contract.
- **Rule** Created by the outbound messaging adapter by translating a domain event.

### Transactional Outbox
Reliability pattern ensuring DB write and event publication are atomic.

- **How** Events written to an outbox table in the same transaction as the aggregate; separate publisher reads and forwards them.
- **Why** Prevents lost or duplicated events under failure.
- **See also** [At-least-once Delivery](#at-least-once-delivery), [Idempotent Consumer](#idempotent-consumer)

### CloudEvents
Standardized event envelope specification.

- **Why** Interoperable metadata (`id`, `source`, `type`, `time`, `datacontenttype`, `dataschema`).
- **Location** Envelope/binding helpers in `shared.infrastructure.adapter.out.messaging.cloudevents`.
- **Rule** Domain and application never import the SDK.

### Event Versioning
Policy for evolving event contracts that cross context or service boundaries.

- **Default** Backward compatible: new optional fields are non-breaking; field removal/rename/type-change requires a new major version.
- **Convention** Major version in CloudEvents `type` (`com.example.order.OrderPlaced.v1`). Minor changes don't change `type`; `dataschema` points to current URL.
- **Coexistence** Producers publish old and new during transition; consumers migrate independently.
- **See also** [Schema Registry](#schema-registry), [Backward Compatibility](#backward-compatibility)

### Backward Compatibility
A new producer can be consumed by an old consumer.

- **Why** Allows producers to evolve without breaking deployed consumers.
- **Contrast** Forward compatibility (old producer, new consumer); full compatibility (both).

### Schema Registry
Centralized store for event schemas with compatibility enforcement.

- **Why** Rejects events that violate the configured compatibility mode (`BACKWARD` per subject).
- **Examples** Confluent Schema Registry, Apicurio.

### At-least-once Delivery
Delivery guarantee where a message may be delivered more than once.

- **Implication** Consumers must be idempotent.
- **See also** [Idempotent Consumer](#idempotent-consumer), [Transactional Outbox](#transactional-outbox)

### Idempotent Consumer
Consumer that safely processes duplicate messages without changing the outcome.

- **Critical for** At-least-once systems.

### Idempotency
Property allowing repeated execution without changing the outcome.

- **See also** [Business-key Idempotency](#business-key-idempotency), [Technical Idempotency](#technical-idempotency)

### Business-key Idempotency
Duplicate detection via a unique business identifier.

- **How** Repository lookup before creation; database unique constraint as backstop. Duplicate input returns the existing aggregate.
- **Example** External order ID, payment reference.
- **Default** Preferred over technical idempotency when a natural dedup key exists.

### Technical Idempotency
Duplicate detection via client-supplied key or message ID.

- **When** No natural business key (payment APIs, Kafka consumers without dedup key).
- **How** `Idempotency-Key` header or message ID stored in an idempotency store with the response/outcome.
- **See also** [Idempotent Consumer](#idempotent-consumer)

### Saga Pattern
Distributed transaction pattern coordinating local transactions via events or commands.

- **Why** Replaces distributed ACID transactions.
- **Variants** Orchestration-based (central coordinator), choreography-based (peer events).
- **See also** [Orchestration](#orchestration), [Choreography](#choreography), [Process Manager](#process-manager), [Compensating Transaction](#compensating-transaction)

### Choreography
Saga coordination in which each context reacts to events and emits its own, with no central coordinator.

- **Why** Maximum autonomy; only facts cross context boundaries; a new participant subscribes without changing the others.
- **Cost** The process definition and compensation logic are distributed across participants; no single place shows the flow or its state.
- **See also** [Saga Pattern](#saga-pattern), [Orchestration](#orchestration)

### Orchestration
Saga coordination in which a central [Process Manager](#process-manager) owns the flow, issuing steps to participants and consuming their outcome events.

- **Why** One visible, testable place holds the flow, its state, and its compensations; supports timeouts, retries, and whole-saga audit.
- **Cost** A coordination component to build; the orchestrator issues commands across a context boundary — the one sanctioned case of a cross-context command.
- **See also** [Saga Pattern](#saga-pattern), [Choreography](#choreography), [Process Manager](#process-manager)

### Process Manager
Component that owns the state of one running saga and advances it, reacting to participants' outcome events and issuing the next step or a compensation.

- **Why** Centralizes an orchestrated saga's state machine and failure handling.
- **Rule** Owns process state only, never a participant's domain data or aggregate; lives in the context that owns the process, or a dedicated coordination context when no single participant does.
- **See also** [Orchestration](#orchestration), [Saga Pattern](#saga-pattern), [Compensating Transaction](#compensating-transaction)

### Compensating Transaction
A local transaction that semantically undoes the effect of a previously committed saga step.

- **Why** A committed local transaction cannot be rolled back; failure is handled by applying an inverse action (release the reservation) rather than reverting.
- **Note** Idempotent under [At-least-once Delivery](#at-least-once-delivery); a step that cannot be compensated is ordered last.
- **See also** [Saga Pattern](#saga-pattern), [Eventual Consistency](#eventual-consistency)

### Eventual Consistency
State across services converges asynchronously.

- **Why** Trade-off accepted when ACID across services is too costly.

### Dead Letter Queue (DLQ)
Queue for messages that cannot be processed successfully.

- **Why** Prevents poison messages from blocking the consumer.

### Retry Policy
Strategy for repeated execution after transient failures.

- **Includes** Backoff, retry limits, jitter.

### Circuit Breaker
Resilience mechanism preventing repeated calls to a failing dependency.

- **Why** Avoids cascading failure and exhausting threads.

### Bulkhead Pattern
Isolation strategy limiting failure impact between components.

- **Why** Prevents resource exhaustion from spreading.

### Timeout Pattern
Bounded request duration before failure.

- **Why** Prevents indefinite waits on degraded dependencies.

### Load Balancing
Distributing requests across instances.

---

## Integration

### Event-Driven Architecture
Architecture centered on publishing and reacting to events.

- **Why** Loose coupling, asynchronous workflows.

### Microservices
Independently deployable, fine-grained services communicating over APIs and message brokers.

- **Contrast** [Modular Monolith](#modular-monolith) separates modules without separate deployments.

### Integration Pattern
Common approach for integrating systems.

### Claim-Check Pattern
Large payloads stored externally; the message carries only a reference.

- **Why** Avoids broker payload limits.

### OpenAPI
Standardized API description format.

- **Use** Documentation, contract validation, code generation.

### AsyncAPI
Standardized asynchronous messaging description format.

### JSON Schema
Data description format for validation.

### Message Broker
System routing messages between services.

- **Examples** Kafka, RabbitMQ.

### Event Bus
Publish/subscribe system for domain events.

### Service Discovery
Mechanism for services to locate each other at runtime.

### API Gateway
Centralized entrypoint for external API traffic.

- **Responsibilities** Auth, rate limiting, routing.

### REST
Web protocol for accessing resources.

### SOAP
Standardized messaging protocol used by legacy systems.

### GraphQL
Query language for APIs allowing clients to select response shape.

### HTTP
Transport protocol for hypertext and web APIs.

### JSON
Lightweight data interchange format.

### XML
Markup-based data interchange format.

---

## Security

### Authentication
Verification of identity (who the caller is).

### Authorization
Verification of permissions (what the caller may do).

- **See also** [RBAC](#rbac-role-based-access-control), [ABAC](#abac-attribute-based-access-control)

### RBAC (Role-Based Access Control)
Authorization model based on roles assigned to subjects.

### ABAC (Attribute-Based Access Control)
Authorization model based on attributes of subject, resource, and context.

- **Contrast** [RBAC](#rbac-role-based-access-control) uses roles only; ABAC supports finer-grained policy.

### JWT (JSON Web Token)
Compact token format for authentication and authorization claims.

### OpenID Connect
Identity layer on top of OAuth 2.0.

### OAuth 2.0
Authorization protocol for delegated access.

### Zero Trust
Security model assuming no implicit trust between components.

### MFA / 2FA
Multi-factor / two-factor authentication.

### Encryption
Converting plaintext to ciphertext.

### PKI (Public Key Infrastructure)
System for managing digital certificates.

### Certificate
Digital representation of a public key issued by a trusted authority.

### Certificate Authority (CA)
Authority issuing digital certificates.

### Digital Signature
Cryptographic signature verifying integrity and authenticity.

### Private Key / Public Key
Asymmetric keypair: private signs/decrypts, public verifies/encrypts.

---

## Observability

### Observability
Ability to understand system behavior through telemetry.

- **Pillars** Logging, metrics, tracing.

### Structured Logging
Logging in a machine-readable format, typically JSON.

### Metrics
Numerical measurements (latency, throughput, error rate).

### Distributed Tracing
Tracking requests across services and components.

- **Example** OpenTelemetry spans propagated via headers.

### Health Check
Endpoint reporting application liveness/readiness.

### Liveness Probe
Check determining whether the process is alive.

### Readiness Probe
Check determining whether the process can serve traffic.

---

## Anti-patterns

### Anemic Domain Model
Domain holds only data; behavior leaks to application services.

- **Why bad** Aggregate invariants weaken; application becomes [Transaction Script](#transaction-script) monolith.
- **Fix** Move business behavior back to aggregates and domain services.

### Transaction Script
Procedural application service containing branching, validation, and orchestration that should live in the domain.

- **Why bad** Bypasses domain modeling; hides invariants in handler chains.

### God Service
Oversized service accumulating unrelated responsibilities.

### Big Ball of Mud
System with no clear architectural structure.

- **Signs** Framework leaks into domain; repositories become generic query services; command/query mix; cross-context imports; shared module grows beyond primitives; mappers contain business decisions; integration tests substituted for unit/domain tests.

### Shared Database Anti-Pattern
Multiple services sharing one schema.

- **Why bad** Creates hidden coupling and coordination tax.

### Chatty Service Communication
Excessive synchronous calls between services.

### Distributed Monolith
Microservices with tight runtime coupling; cannot deploy independently.

- **Why bad** Combines the operational cost of microservices with the coupling of a monolith; one service's outage or change cascades to others.
- **Cause** Synchronous cross-service calls on the request path where an asynchronous local projection would do.
- **Fix** After extraction, prefer a local projection fed by the provider's integration events over a synchronous [Open-Host Service](#open-host-service-ohs) call across the network.
- **See also** [Chatty Service Communication](#chatty-service-communication), [Eventual Consistency](#eventual-consistency)

### Leaky Abstraction
Abstraction exposing implementation details to consumers.

### Entity Service Trap
Services organized around CRUD entities instead of business capabilities.

### Smart Controller / Thin Domain
Business logic in controllers or application services.

- **See also** [Anemic Domain Model](#anemic-domain-model)

### Over-Mocking
Excessive reliance on mocks producing tests coupled to implementation.

### Temporal Coupling
Components requiring strict execution ordering to function.

### Golden Hammer
Overusing a familiar technology regardless of fit.

### Vendor Lock-In
Excessive dependence on proprietary vendor features.

### Premature Optimization
Optimizing before identifying real bottlenecks.

### Technical Debt
Long-term cost of short-term implementation compromises.

---

## Testing — core test types

### Unit Test
Test of a single unit of behavior in isolation.

- **Rule** No external systems; no framework boot.
- **Use** Domain logic, application orchestration with mocked ports.

### Integration Test
Test verifying collaboration with real infrastructure (DB, broker, framework).

- **Tools** QuarkusTest, Testcontainers.

### Contract Test
Test ensuring compatibility between communicating systems.

- **Variants**
  - **Provider-driven** (this template): provider publishes OpenAPI; provider tests verify implementation matches spec; consumers verify usage stays within it.
  - **Consumer-driven**: consumer defines contracts; provider verifies them. Not used in this template.
- **See also** [Bidirectional Contract Testing](#bidirectional-contract-testing), [OpenAPI Validation](#openapi-validation), [Pact](#pact)

### Bidirectional Contract Testing
Pact mode where consumer and provider verify against the published OpenAPI spec independently, then matched in the Pact Broker.

- **Why** Combines schema-first authority with Pact's deployment-gating (`can-i-deploy`).
- **Contrast** Classic consumer-driven Pact has providers verify live consumer pacts; not used here.

### End-to-End (E2E) Test
Black-box test exercising full workflows across all layers.

### Architecture Test
Test enforcing structural and dependency rules.

- **Tool** ArchUnit.
- **Rule** Every architectural constraint in documentation has a test; rules without a test are aspirational and subject to drift.

### Performance Test
Test measuring scalability and runtime characteristics (throughput, latency, concurrency, soak).

### Smoke Test
Lightweight test verifying basic startup and flow after deployment.

### Regression Test
Test ensuring previously working behavior remains correct.

### Acceptance Test
Test validating business requirements from stakeholder perspective.

### Snapshot Test
Test comparing current output against a stored reference.

---

## Testing — test doubles

### Test Double
Generic term for any substitute of a real dependency.

### Stub
Test double returning predefined responses.

- **Contrast** No interaction assertions; pure input source.

### Mock
Test double verifying interactions.

- **Risk** Over-mocking couples tests to implementation.
- **See also** [Over-Mocking](#over-mocking)

### Fake
Lightweight working implementation (e.g., in-memory repository).

### Spy
Wrapper around a real object that records interactions.

- **Smell** Indicates partial mocking or hard-to-isolate code.

### Dummy
Placeholder passed to satisfy a signature; never used during the test.

---

## Testing — test data

### Fixture
Predefined test data or object graph.

### Object Mother
Test utility creating predefined valid domain objects.

- **Example** `paidOrder()`, `expiredSubscription()`.
- **Contrast** [Test Data Builder](#test-data-builder) is flexible; Object Mother is named-scenario.

### Test Data Builder
Fluent builder for creating customized test objects.

### Factory (Test Factory)
Helper constructing test objects with default values.

### Generator
Component producing randomized or fuzzed test data.

- **Use** Property-based testing.

### Seed Data
Initial data inserted before test execution.

### Fixture File
Static resource (JSON payloads, SQL scripts, Avro schemas).

---

## Testing — execution

### Test Suite
Collection of related tests executed together.

### Test Case
Single test scenario with defined inputs and expected outcomes.

### Assertion
Statement verifying expected behavior or state.

### Setup / Teardown
Initialization / cleanup logic around a test.

### Parameterized Test
Test executed multiple times with varying inputs.

### Property-Based Test
Test verifying invariants across many generated inputs.

### Flaky Test
Nondeterministic test producing inconsistent results.

- **Causes** Timing, shared state, external dependencies.

### Golden Master Test
Test comparing current output against a trusted historical baseline.

---

## Testing — contract and infrastructure

### OpenAPI Validation
Verification that an API implementation matches its OpenAPI specification.

### Pact
Contract testing framework; used here in bidirectional mode.

### Consumer-Driven Contract
Contract defined from the consumer's perspective.

- **Status in this template** Not used; provider-driven OpenAPI is authoritative.

### Testcontainers
Framework for running disposable infrastructure containers during tests.

### Embedded Database
In-memory or lightweight DB for tests (e.g., H2).

### Sandbox Environment
Controlled external environment for integration testing.

### WireMock
Tool for stubbing HTTP services.

---

## Testing — quality

### Code Coverage
Metric of executed code (line, branch, path).

- **Caveat** High coverage does not guarantee quality.

### Mutation Testing
Evaluating test quality by introducing code changes ("mutants") that good tests should detect.

### Deterministic Test
Test producing identical results on every execution.

### Idempotent Test
Test that can run repeatedly without affecting future executions.

### Isolation
Tests should not affect each other.

### Observability Testing
Verification of logs, metrics, traces, and monitoring signals.

### Concurrency Test
Test validating behavior under parallel execution.

### Chaos Test
Test intentionally introducing failures to verify resilience.
