# Extracting a Bounded Context to a Microservice

The scaffold is a modular monolith whose bounded contexts are isolated by package, ownership, and the Open-Host Service boundary. Inside the monolith the contexts already integrate in two ways: a cross-context **read** is a synchronous in-process call through the provider's `application.port.in`, and a cross-context **write** propagates as an integration event through the transactional outbox, consumed in-process by the other context. These interaction patterns and the rules behind them are described in [Cross-Context Integration Flows](cross-context-flows.md).

Extraction deploys one context as its own service. It leaves the inbound port interfaces untouched and changes only the *mechanism* behind each cross-context boundary. This document is the procedure; the rules it relies on live in [README — Cross-context integration](../README.md#cross-context-integration).

## What stays the same

- **The port interface.** A context is reached only through its `application.port.in`. No consumer imports another context's `domain`, `application.port.out`, `infrastructure`, or reads its tables. The inbound port interface the consumer depends on does not change.
- **No shared tables.** Each context owns its tables and migrations; there are no cross-context foreign keys or joins. A context's data moves with it.
- **The messaging machinery.** The outbox, CloudEvents envelope, event versioning, and schema-registry policy are unchanged. Cross-context writes already flow through them in the monolith; extraction only repoints them across a real broker.

## What changes

**Writes** already travel as integration events, so extraction is mostly configuration: the events that were dispatched to an in-process consumer now cross a real broker between two services. The producer, outbox, and consumer code are unchanged; only topic wiring and broker configuration are.

**Reads** are where a real choice appears. In the monolith a Foreign Read is a cheap in-process call. Across the network the same call is no longer free — it couples the two services at runtime and availability. For each cross-context read you choose one of two patterns.

### Foreign Read: local projection, or synchronous REST client

**Local projection (asynchronous).** The consumer subscribes to the provider's integration events and maintains its own read model of exactly the data it needs, updated through a command handler. Reads then hit local data.

- *Advantages:* no runtime or availability coupling — the consumer reads even when the provider is down or slow; the services scale and fail independently.
- *Disadvantages:* the consumer builds and maintains a read model (event handler, storage, migration); consistency is eventual, so there is no read-after-write freshness.

**Synchronous REST client (extracted Open-Host Service).** The same `application.port.in` port is implemented by a REST client adapter in the consumer's `infrastructure.adapter.out.client.rest`. The consumer's application logic is untouched — it still depends on the same inbound port interface.

- *Advantages:* a mechanical swap, the port interface unchanged; reads are up-to-the-moment; no read model to build.
- *Disadvantages:* runtime and availability coupling — the caller now fails or stalls when the provider is down or slow. Reaching for synchronous cross-service calls on the request path is how a set of services becomes a [distributed monolith](glossary/distributed-monolith.md).

*Guidance:* default to a local projection, which fits the staleness-tolerant nature of a Foreign Read. Reserve the synchronous REST client for reads whose freshness genuinely requires an up-to-the-moment answer and where a projection's staleness would be unacceptable.

## Procedure

1. **Move the migration folder and data.** `src/main/resources/db/migration/<context>/` moves to the new service and runs the same migrations against its own database. The monolith drops that location from its Flyway configuration. Table names are unprefixed and need no renaming — nothing in another context referenced them. Existing rows move with a one-off data migration; no cross-context foreign key blocks it.

2. **Publish the shared contract.** The provider's `application.port.in` contracts and its integration event schemas become the published language between the two services. The provider publishes its OpenAPI spec; consumer and provider gain contract tests at the now-remote boundary (see [Testing Strategy](testing-strategy.md)).

3. **Repoint the write events.** Integration events that were consumed in-process now cross a real broker. Update the topic wiring and broker configuration; the producer, outbox, and consumer code are unchanged.

4. **Choose a mechanism for each read.** For every Foreign Read across the extracted boundary, implement a local projection or a synchronous REST client per the guidance above. The consumer's inbound-port dependency is unchanged; only the implementation behind the boundary is new.

## What does not change

- The consumer's `application.port.in` / `application.port.out` interfaces.
- The domain model and aggregate boundaries of either context.
- The outbox, CloudEvents envelope, event versioning, and schema-registry policy.
- The isolation rules — they applied in the monolith and still apply across services.
