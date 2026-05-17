# Glossary

## Architecture Glossary

### Architectural quality concepts

- **Separation of Concerns** Division of responsibilities into distinct components or layers.
  - Reduces coupling and complexity.

- **Coupling** The degree of interdependency between components or modules.
  - Lower coupling improves maintainability, testability, and reusability, and reduces ripple effect of changes.
  - Low coupling is achieved through information hiding, interfaces, and dependency inversion.

- **Cohesion** The degree to which responsibilities within a component belong together.
  - High cohesion improves clarity, maintainability, testability, and reusability.
  - Achieved through encapsulation and Single Responsibility Principle (SRP).

- **Encapsulation** The practice of bundling of data and behavior together into a single cohesive unit.
  - Enforces information hiding and separation of concerns.
  - Helps maintain a clean interface and reduce coupling.
  - Common in object-oriented programming.

- **Information Hiding** The practice of hiding internal implementation details and data from the outside world.
  - Helps maintain a clean interface and reduce coupling.

- **Dependency Inversion** A principle where high-level business logic depends on abstractions rather than implementations.
  - Central to hexagonal architecture.

- **Single Responsibility Principle (SRP)** A principle that states that a unit should have only one reason to change.
  - Enforces loose coupling and separation of concerns.
  - Helps maintain a clean and maintainable codebase.
  - Achieved through encapsulation and information hiding.

### Layered Architecture

- **Layered Architecture** An architecture separating concerns into logical layers.
  - Common layers: domain, application, infrastructure.
  - Dependencies flow inward toward business logic.

- **Domain Layer** The core business layer containing domain logic and business rules.
  - Independent of frameworks and infrastructure.
  - Contains aggregates, entities, value objects, and domain services.

- **Application Layer** The orchestration layer coordinating use cases and domain interactions.
  - Contains no core business rules.
  - Manages transactions and workflow execution.

- **Infrastructure Layer** The technical implementation layer integrating frameworks and external systems.
  - Contains adapters and configuration.
  - Depends on application and domain layers.

### Hexagonal Architecture Concepts

- **Hexagonal Architecture** An architectural style isolating business logic from external systems through ports and adapters.
  - Also called Ports and Adapters architecture.
  - Separates application core from infrastructure concerns.
  - Enables easier testing and technology replacement.

- **Port** An abstraction defining communication with the application core.
  - Separates core logic from technical implementation.

- **Inbound Port** A contract exposing application capabilities to external actors.
  - Represents use case interfaces.

- **Outbound Port** A contract describing dependencies required by the application core.
  - Examples: repositories, HTTP clients, messaging publishers.

- **Adapter** An implementation connecting ports to external systems.
  - Converts between external and internal representations.

- **Inbound Adapter** An adapter driving the application.
  - Examples: REST controllers, Kafka consumers, schedulers.

- **Outbound Adapter** An adapter implementing outbound dependencies.
  - Examples: repositories, Kafka producers, REST clients.

- **Driving Adapter** Another term for inbound adapter.
  - Initiates interaction with the application.

- **Driven Adapter** Another term for outbound adapter.
  - Invoked by the application core.

- **Application Core** The business-centric part of the system isolated from infrastructure concerns.
  - Typically, contains domain and application layers.

- **Dependency Inversion** A principle where high-level business logic depends on abstractions rather than implementations.
  - Central to hexagonal architecture.

- **Boundary** A separation point between architectural responsibilities.
  - Often enforced through ports and package structure.

#### Adapter Types

- **Infrastructure Adapter** Infrastructure component implementing integration with external systems.

- **Persistence Adapter** Infrastructure adapter implementing persistent storage.
  - Handles database interaction and ORM mapping.

- **Messaging Adapter** Infrastructure adapter integrating asynchronous messaging systems.
  - Examples: Kafka and RabbitMQ producers and consumers.

- **Client Adapter** Infrastructure adapter integrating external services.
  - Examples: REST, gRPC, SOAP clients.

- **Cache Adapter** Infrastructure adapter integration with caching systems.
  - Examples: Redis, Memcached.

### Domain-Driven Design (DDD) Concepts

- **Domain-Driven Design (DDD)** An approach to software design centered around business domains and domain models.
  - Focuses on modeling business concepts explicitly.
  - Encourages collaboration with domain experts.
  - Encourages bounded contexts and ubiquitous language.

- **Bounded Context** A logical boundary within which a domain model is consistent and meaningful.
  - Defines ownership of terminology and rules.
  - Reduces coupling between business domains.

- **Ubiquitous Language** A shared language between developers and domain experts.
  - Reflected directly in code and architecture.
  - Reduces translation ambiguity.

- **Domain Layer** The core business layer containing domain logic and business rules.
  - Independent from frameworks and infrastructure.
  - Contains aggregates, entities, value objects, and domain services.

- **Aggregate** A consistency boundary grouping related domain objects (entities and value objects).
  - Treated as a single, cohesive unit for data changes.
  - Enforces invariants and transactional consistency.
  - Accessed through a single aggregate root.
  - Example: `Order` ensuring it cannot transition to `Shipped` unless it is `Paid`.

- **Aggregate Root** The primary entity controlling access to an aggregate.
  - External code interacts only through the root.
  - Responsible for maintaining invariants.
  - Example: `Order` aggregating `OrderLine` items.

- **Entity** A domain object defined primarily by identity.
  - Identity persists across state changes.
  - Usually mutable.

- **Value Object** A domain object defined only by values.
  - Immutable by design.
  - Has no identity.

- **Domain Event** A representation of something meaningful that happened in the domain.
  - Captures business-significant state changes.
  - Used for decoupling and integration.

- **Domain Service** Domain logic that does not naturally belong to a single aggregate.
  - Contains business behavior spanning multiple aggregates.
  - Should remain business-focused and stateless where possible.

- **Repository** An abstraction for loading and persisting aggregates.
  - Hides persistence details from the domain.
  - Works with aggregates, not database tables.

- **Specification** A reusable business predicate expressing selection or validation rules.
  - Often used for filtering and eligibility logic.
  - Encapsulates boolean business rules.
  - Example: `EligibleForFreeShippingSpec` returns `true` if `order.total > 50`.

- **Policy** A business decision rule or strategy.
  - Encapsulates business decisions and computations.
  - Often stateless and reusable.
  - Example: `ShippingCostPolicy` computes shipping fee based on order weight, destination, and carrier rules.

- **Factory (Domain Factory)** A component responsible for creating complex aggregates or entities.
  - Encapsulates creation invariants.
  - Prevents invalid object creation.

- **Invariant** A business rule that must always remain true within an aggregate boundary.
  - Enforced by aggregates and domain logic.

- **Shared Kernel** A small explicitly shared subset of a domain model between bounded contexts.
  - Requires strong coordination between teams.

- **Anti-Corruption Layer (ACL)** A translation layer protecting one bounded context from another model.
  - Prevents leakage of external concepts.

- **Context Map** A high-level representation of relationships between bounded contexts.
  - Documents integration patterns and ownership boundaries.

### Clean Architecture Concepts

- **Clean Architecture** An architectural style organizing code around dependency direction toward business rules.
    - Domain logic is isolated from infrastructure concerns.
    - Inner layers must not depend on outer layers.
    - Encourages loose coupling and separation of concerns.
  
- **Use Case** A business operation exposed by the application layer.
  - Represents an application capability.
  - Coordinates domain behavior and ports (external interfaces).

- **Application Layer** The orchestration layer coordinating workflows and transactions.
  - Contains no core business rules.

- **DTO (Data Transfer Object)** A structure used to transfer data between layers or systems.
  - Prevents leaking domain models externally.
  - Usually serialization-friendly.

- **Mapper** A component translating between DTOs and domain models.
  - Keeps transformation logic centralized.

- **Transaction Boundary** The scope within which changes are committed atomically.
  - Usually managed in application layer use cases.

- **Validation** Verification that incoming data satisfies structural or business requirements.
  - Often performed at application or API boundaries.
  - Input validation belongs outside domain invariants.

- **Separation of Concerns** Division of responsibilities into distinct components or layers.
  - Reduces coupling and complexity.

- **Coupling** The degree of dependency between components.
  - Lower coupling improves maintainability.

- **Cohesion** The degree to which responsibilities within a component belong together.
  - High cohesion improves clarity.

- **Dependency Direction** The rule governing allowed compile-time dependencies.
  - Dependencies should point toward business logic.

- **Configuration Mapping** Binding configuration values into typed application objects.
  - Keeps configuration centralized and type-safe.

### Microservices & Distributed Systems Patterns

- **Event-Driven Architecture** An architecture centered around publishing and reacting to events.
  - Encourages loose coupling and asynchronous workflows.
  - Commonly implemented as a microservice architecture.

- **Microservices** An architectural pattern that organizes an application into a collection of loosely coupled, fine-grained services
  - Commonly implemented as containers.
  - Services communicate through APIs and message brokers.
  
#### Consistency Patterns

- **Saga Pattern** A distributed transaction pattern coordinating multiple local transactions through events or commands.
  - Used instead of distributed ACID transactions.
  - Can be orchestration-based or choreography-based.

- **Transactional Outbox** A reliability pattern ensuring DB writes and event publishing occur atomically.
  - Prevents lost or duplicated events.
  - Common in event-driven systems.

- **Idempotent Consumer** A consumer that safely processes duplicate messages without changing the outcome.
  - Critical in at-least-once delivery systems.

- **Idempotency** The property allowing repeated execution without changing the outcome.
  - Critical for distributed messaging systems.

- **Eventual Consistency** A consistency model where distributed state converges asynchronously over time.
  - Common in microservice systems.

#### Reliability & Resilience Patters

- **Dead Letter Queue (DLQ)** A queue storing messages that cannot be processed successfully.
  - Prevents poison messages from blocking processing.

- **Retry Policy** A strategy controlling repeated execution after transient failures.
  - Often includes backoff and retry limits.

- **Circuit Breaker** A resilience mechanism preventing repeated calls to failing dependencies.
  - Helps avoid cascading failures.

- **Bulkhead Pattern** An isolation strategy limiting failure impact between components.
  - Prevents resource exhaustion from spreading.

- **Timeout Pattern** A strategy controlling the duration of a request before it is considered failed.
  - Helps avoid long-running requests.
  - Common in asynchronous systems.

- **Load Balancing** A strategy distributing incoming requests across multiple services.
  - Improves overall throughput and availability.

#### CQRS Concepts

- **CQRS (Command Query Responsibility Segregation)** Separation of write operations and read operations.
  - Commands modify state.
  - Queries retrieve optimized projections.

- **Command** A request that changes system state.
  - Typically handled by command use cases.

- **Query** A request retrieving data without modifying state.
  - Typically optimized for reads.
  - Often separated from commands in CQRS.

- **Read Model** A query-optimized representation of data.
  - Often denormalized for performance.

- **Projection** A transformed view of domain data optimized for reading.

- **Event** A representation of something meaningful that happened in the domain.
  - Captures business-significant state changes.
  - Used for decoupling and integration.
  - Common in CQRS architectures.
  - Often denormalized for performance.

- **Event Store** A persistent repository for domain events.
  - Often used for event sourcing.

- **Event Sourcing** A system for storing and replaying domain events.
  - Often used for eventual consistency.

### Integration Concepts

- **Integration Event** An event published for consumption by external systems.
  - Separate from internal domain events.

- **Integration Pattern** A common approach for integrating systems.

- **Claim-Check Pattern** Integration pattern used in messaging and event-driven systems to manage large data payloads efficiently.
  - Instead of passing bulky data directly through a message broker, the system stores the large payload in an external storage service and sends only a lightweight reference (the "claim check") to consumers.

#### Integration Standards

- **CloudEvents** A standardized event metadata specification.
  - Promotes interoperability across event systems.

- **OpenAPI (Open API Specification)** A standardized API description format.
  - Commonly used for API documentation and contract validation.

- **AsyncAPI (Async API Specification)** A standardized asynchronous messaging specification.
  - Commonly used for asynchronous communication.

- **JSON Schema** A standardized data description format.
  - Commonly used for data validation and documentation.

- **XML Schema** A standardized data description format.

- **Message Specification** A standardized format for message payloads.
  - Commonly used for message serialization and deserialization.

- **Message Format** A standardized format for message transport.

#### Integration Protocols

- **REST (Representational State Transfer)** A web-based protocol for accessing resources.
  - Commonly used for APIs and web services.

- **SOAP (Simple Object Access Protocol)** A standardized messaging protocol.
  - Used for legacy systems and interoperability.

- **JSON (JavaScript Object Notation)** A lightweight data interchange format.
  - Commonly used for APIs and web services.

- **XML (eXtensible Markup Language)** A lightweight data interchange format.
  - Commonly used for legacy systems and interoperability.

- **HTTP (Hypertext Transfer Protocol)** A protocol for transferring hypertext documents.
  - Commonly used for web services.

- **GraphQL (Graph Query Language)** A query language for APIs.
  - Commonly used for APIs and web services.

#### Integration middlewares

- **Message Broker** A system for routing messages between services.
  - Commonly used for asynchronous communication.

- **Event Bus** A system for publishing and subscribing to domain events.

- **Service Discovery** A mechanism allowing services to dynamically locate each other.
  - Common in container orchestration platforms.

- **API Gateway** A centralized entrypoint routing and managing external API traffic.
  - Often handles auth, rate limiting, and routing.

- **Schema Registry** A centralized store for message schemas.
  - Common with Kafka and Avro-based systems.

### Security Concepts

- **Authentication** Verification of identity.
  - Determines who the caller is.

- **Authorization** Verification of permissions.
  - Determines what the caller may do.

- **RBAC (Role-Based Access Control)** Authorization model based on assigned roles.

- **JWT (JSON Web Token)** A compact token format commonly used for authentication and authorization.

- **OpenId Connect** An open standard for authentication and authorization.
  - Commonly used with OAuth 2.0. 

- **OAuth 2.0** An authorization protocol for web applications.
  - Commonly used with OpenId Connect.

- **Zero Trust** A security model assuming no implicit trust between components.
  - Requires explicit verification for every interaction.

- **Multi-Factor Authentication (MFA)** A second factor in authentication.
  - Provides an additional layer of security.

- **Two-Factor Authentication (2FA)** A second factor in authentication.
  - Requires a physical token or a mobile app.

- **Encryption** The process of converting plaintext data into a ciphertext.
  - Prevents unauthorized access to sensitive information.

- **PKI (Public Key Infrastructure)** A system for managing digital certificates.
  - Commonly used for digital signatures and encryption.

- **Certificate** A digital representation of a public key.
  - Used for digital signatures and encryption.
  - Commonly issued by a trusted authority.

- **Certificate Authority (CA)** A trusted authority issuing digital certificates.
  - Commonly used for digital signatures.

- **Digital Signature** A cryptographic signature of a message.
  - Ensures data integrity and authenticity.
  - Signed with a private key and verified with a public key.

- **Private Key** A secret key used for signing or to decrypt a ciphertext.
  - Stored securely and never shared.

- **Public Key** A public representation of a private key
  - Shared with anyone who needs to verify a digital signature.
  - Used for encryption and digital signatures.

- **Signing** The process of converting plaintext data into a digital signature.
  - Ensures data integrity and authenticity.

### Observability Concepts

- **Observability** The ability to understand system behavior through telemetry.
  - Includes logging, metrics, and tracing.

- **Structured Logging** Logging using machine-readable structured formats.
  - Typically JSON-based.

- **Metrics** Numerical measurements describing system behavior.
  - Examples: latency, throughput, error rate.

- **Distributed Tracing** Tracking requests across multiple services and components.
  - Enables end-to-end request visibility.

- **Health Check** An endpoint or probe reporting application readiness and liveness.

- **Liveness Probe** A check determining whether the application process is alive.

- **Readiness Probe** A check determining whether the application can serve traffic.

- **Correlation ID** An identifier propagated across requests and services for traceability.

### Anti-Patterns

- **Anemic Domain Model** A domain model containing only data with no business behavior.
  - Business logic leaks out of the domain into application services, weakening invariants and core domain ownership.
  - Use case handlers accumulate procedural logic, validation, branching, and orchestration,
    effectively creating a “service layer monolith” that bypasses domain modeling (Transaction Script).
  - Generally discouraged in DDD.

- **God Service** An oversized service accumulating unrelated responsibilities.
  - Indicates weak boundaries and low cohesion.

- **Big Ball of Mud** A system with no clear architectural structure.
  - Framework or persistence concerns leak into domain or application layers.
  - Repositories evolve into generic query services instead of aggregate-focused persistence gateways.
  - Command and query responsibilities mix, breaking read/write separation assumptions.
  - Cross-context imports introduce hidden coupling and reduce modular independence.
  - The shared module expands beyond primitives and silently couples bounded contexts.
  - Mapping logic starts containing transformation rules, conditional enrichment, 
    or decisions that should belong in domain policies or factories.
  - Excessive subpackages reduce cohesion and increase cognitive overhead without real benefit.
  - Heavy integration/e2e reliance replaces fast, focused unit and domain-level testing.
  - High coupling and poor maintainability.

- **Shared Database Anti-Pattern** Multiple services directly sharing the same database schema.
  - Creates strong coupling and coordination problems.

- **Chatty Service Communication** Excessive synchronous calls between services.
  - Increases latency and failure propagation.

- **Distributed Monolith** A microservice system with tight runtime coupling between services.
  - Services cannot operate independently.

- **Leaky Abstraction** An abstraction exposing implementation details to consumers.
  - Breaks encapsulation and increases coupling.

- **Entity Service Trap** Organizing services purely around CRUD entities rather than business capabilities.
  - Produces weak domain boundaries.

- **Smart Controller / Thin Domain** Business logic placed in controllers or application services instead of domain model.
  - Use case handlers accumulate procedural logic and become hidden monoliths.
  - Weakens domain encapsulation.

- **Over-Mocking** Excessive reliance on mocks in tests.
  - Leads to brittle tests focused on implementation details.

- **Temporal Coupling** Components requiring strict execution ordering to function correctly.
  - Reduces resilience and flexibility.

- **Golden Hammer** Overusing a familiar technology or pattern regardless of suitability.
  - Leads to poor architectural decisions.

- **Vendor Lock-In** Excessive dependence on proprietary vendor features.
  - Reduces portability and flexibility.

- **Premature Optimization** Optimizing before identifying real bottlenecks.
  - Adds complexity without measurable benefit.

- **Technical Debt** The long-term cost of short-term implementation compromises.
  - Cross-context imports occur for convenience, leading to implicit coupling and making 
    eventual service extraction difficult.
  - Over-reliance on integration/e2e tests due to weak domain/unit design, resulting in slow 
    feedback loops and fragile test suites.

## Testing

### Core test types

- **Unit Test** A test of a single unit of behavior, in isolation.
  - No external systems (DB, HTTP, Kafka).
  - Fast and deterministic.
  - Typically tests domain logic or application orchestration.

- **Integration Test** A test verifying collaboration between components and real infrastructure.
  - Uses real DBs, brokers, caches, or frameworks.
  - Verifies wiring, persistence, messaging, and adapter behavior.
  - Often uses Testcontainers.

- **Contract Test** A test ensuring compatibility between communicating systems.
  - Verifies API schemas or event contracts.
  - Commonly used between microservices.
  - Focuses on interfaces, not business logic.

- **End-to-End (E2E) Test** A test exercising the system as a black box.
  - Covers full workflows across all layers.
  - Uses real runtime configuration where possible.
  - Focuses on user/business scenarios.

- **Architecture Test** A test enforcing structural and dependency rules.
  - Verifies layering and package boundaries.
  - Ensures DDD and hexagonal constraints.
  - Typically static analysis (e.g., ArchUnit).

- **Performance Test** A test measuring scalability and runtime characteristics.
  - Measures throughput, latency, and concurrency.
  - Uses production-like infrastructure.
  - Includes load, stress, and soak testing.

- **Smoke Test** A lightweight test verifying the system starts and basic flows work.
  - Often executed after deployment.
  - Intended to catch catastrophic failures quickly.

- **Regression Test** A test ensuring previously working behavior remains correct.
  - Protects against unintended changes.
  - Can exist at any test level.

- **Acceptance Test** A test validating business requirements from a stakeholder perspective.
  - Usually scenario-oriented.
  - Often overlaps with E2E testing.

- **Snapshot Test** A test comparing current output against a stored reference snapshot.
  - Common for APIs, UI responses, and serialized events.
  - Useful for detecting unintended output changes.

### Test doubles

- **Test Double** Generic term for any substitute of a real dependency in tests.

- **Stub** A test double providing predefined responses to calls.
  - No behavior verification.
  - Used to feed inputs into the system.

- **Mock** A test double verifying interactions between components.
  - Used to assert method calls and invocation patterns.
  - Common in application-layer orchestration tests.

- **Fake** A lightweight working implementation replacing a real dependency.
  - Contains simplified but functional logic.
  - Common examples: in-memory repositories, fake caches.

- **Spy** A wrapper around a real object that records interactions.
  - Executes real behavior unless overridden.
  - Usually indicates partial mocking or difficult-to-isolate code.

- **Dummy** A placeholder object passed only to satisfy method signatures.
  - Never actually used during the test.

### Test data concepts

- **Fixture** Predefined test data or object graph used during testing.
  - Can be executable code or static resources.
  - Helps keep tests concise and reusable.

- **Object Mother** A test utility creating predefined valid domain objects.
  - Encodes meaningful business scenarios.
  - Example: `paidOrder()`, `expiredSubscription()`.

- **Test Data Builder** A fluent builder for creating customized test objects.
  - Flexible alternative to Object Mother.
  - Reduces constructor noise in tests.

- **Factory (Test Factory)** A helper responsible for constructing test objects.
  - Often encapsulates repetitive setup logic.
  - May combine builders and default values.

- **Generator** A component producing randomized or fuzzed test data.
  - Useful for property-based and robustness testing.

- **Seed Data** Initial data inserted before test execution.
  - Common for DB integration tests.

- **Fixture File** A static resource used by tests.
  - Examples: JSON payloads, SQL scripts, Avro schemas.

### Test execution concepts

- **Test Suite** A collection of related tests executed together.

- **Test Case** A single test scenario with defined inputs and expected outcomes.

- **Assertion** A statement verifying expected behavior or state.

- **Setup** Initialization logic executed before a test.

- **Teardown** Cleanup logic executed after a test.

- **Parameterized Test** A test executed multiple times with varying inputs.

- **Property-Based Test** A test verifying invariants across many generated inputs.
  - Focuses on properties rather than fixed examples.

- **Flaky Test** A nondeterministic test producing inconsistent results.
  - Often caused by timing, shared state, or external dependencies.

- **Golden Master Test** A test comparing current output against a trusted historical baseline.
  - Common in legacy system refactoring.

### API and contract testing concepts

- **OpenAPI Validation** Verification that an API implementation matches its OpenAPI specification.
  - Validates requests and responses.
  - Ensures schema correctness and API consistency.

- **Pact** A consumer-driven contract testing framework.
  - Verifies provider APIs satisfy consumer expectations.
  - Common in microservice architectures.

- **Schema Registry** A centralized store for message schemas (e.g., Kafka Avro schemas).
  - Helps enforce compatibility between producers and consumers.

- **Consumer-Driven Contract** A contract defined from the consumer’s perspective.
  - Providers verify they satisfy the consumer expectations.

### Infrastructure testing concepts

- **Testcontainers** A framework for running disposable infrastructure containers during tests.
  - Common for Postgres, Kafka, Redis integration testing.

- **Embedded Database** An in-memory or lightweight database used during tests.
  - Example: H2.

- **Sandbox Environment** A controlled external environment used for integration testing.
  - Common for payment or identity providers.

- **WireMock** A tool for stubbing and simulating HTTP services.
  - Frequently used in integration and contract tests.

### Quality and reliability concepts

- **Code Coverage** A metric indicating how much code is executed during tests.
  - Includes line, branch, and path coverage.
  - High coverage does not guarantee quality.

- **Mutation Testing** A technique evaluating test quality by introducing code changes (“mutants”).
  - Good tests should detect the mutations.

- **Deterministic Test** A test producing identical results on every execution.

- **Idempotent Test** A test that can run repeatedly without affecting future executions.

- **Isolation** The principle that tests should not affect each other.

- **Observability Testing** Verification of logs, metrics, traces, and monitoring signals.

- **Concurrency Test** A test validating behavior under parallel execution.
  - Common for locking and race-condition verification.

- **Chaos Test** A test intentionally introducing failures into the system.
  - Used to verify resilience and fault tolerance in distributed systems.