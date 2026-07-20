# Extracting a Bounded Context to a Microservice

The scaffold is a modular monolith whose bounded contexts are isolated by package, ownership, and the Open-Host Service boundary. Inside the monolith the contexts already integrate in two ways: a cross-context **read** is a synchronous in-process call through the provider's `application.port.in`, and a cross-context **write** propagates as an integration event through the transactional outbox, consumed in-process by the other context. These interaction patterns and the rules behind them are described in [Cross-Context Integration Flows](cross-context-flows.md).

Extraction deploys one context as its own service. It leaves the inbound port interfaces untouched and changes only the *mechanism* behind each cross-context boundary. This document is the procedure; the rules it relies on live in [README — Cross-context integration](../README.md#cross-context-integration).

## What stays the same

- **The port interface.** A context is reached only through its `application.port.in`. No consumer imports another context's `domain`, `application.port.out`, `infrastructure`, or reads its tables. The inbound port interface the consumer depends on does not change.
- **No shared tables.** Each context owns its tables and migrations; there are no cross-context foreign keys or joins. A context's data moves with it.
- **The messaging machinery.** The outbox, CloudEvents envelope, event versioning, and schema-registry policy are unchanged. Cross-context writes already flow through them in the monolith; extraction only repoints them across a real broker.

## What changes

**Writes** already travel as integration events, so extraction is mostly configuration: the events that were dispatched to an in-process consumer now cross a real broker between two services. The producer, outbox, and consumer code are unchanged; only topic wiring and broker configuration are.

**Reads** are where a real choice appears. In the monolith a Foreign Read is a cheap in-process call. Across the network the same call is no longer free — it couples the two services at runtime and availability. For each cross-context read you choose one of two patterns, decided in the procedure below.

## Procedure

Move the context's data, repoint the write events, choose a mechanism for each read, then publish the contract that results.

### 1. Move the migration folder and data

`src/main/resources/db/migration/<context>/` moves to the new service and runs the same migrations against its own database. The monolith drops that location from its Flyway configuration. Table names are unprefixed and need no renaming — nothing in another context referenced them. Existing rows move with a one-off data migration; no cross-context foreign key blocks it.

### 2. Repoint the write events

Integration events that were consumed in-process now cross a real broker. Update the topic wiring and broker configuration; the producer, outbox, and consumer code are unchanged.

### 3. Choose a mechanism for each read

For each cross-context read across the extracted boundary, choose one of the two patterns below. Default to a local projection, which fits the staleness-tolerant nature of a Foreign Read. Reserve the synchronous REST client for reads whose freshness genuinely requires an up-to-the-moment answer and where a projection's staleness would be unacceptable. The consumer's inbound-port dependency is unchanged either way; only the implementation behind the boundary is new.

#### Local projection (asynchronous)

The consumer subscribes to the provider's integration events and maintains its own read model of exactly the data it needs, updated through a command handler. Reads then hit local data.

- *Advantages:* no runtime or availability coupling — the consumer reads even when the provider is down or slow; the services scale and fail independently.
- *Disadvantages:* the consumer builds and maintains a read model (event handler, storage, migration); consistency is eventual, so there is no read-after-write freshness.

Migrating an in-process OHS read to a projection is not a wiring change — it inverts the direction of data flow, and the work is mostly on the provider side:

1. **Ensure the provider emits the state the read exposed.** A synchronous OHS read can return data the provider never published as an integration event. Where that is the case, add integration events (through the outbox) covering the fields the consumer needs, and version them like any other event. This is the substance of the migration; if the events already exist because a write flow emits them, there is nothing to add.
2. **Build the projection on the consumer.** Add an event handler, its own storage, and a migration for a read model holding exactly the fields the read needs. The handler updates the read model from the provider's events through a command handler, like any other cross-context write consumer.
3. **Backfill before cutover.** Events only carry changes from now on. Seed the projection from the provider's current state — a one-off export/replay — so the read model is complete before the first read hits it.
4. **Swap the port implementation.** Re-point the consumer's `application.port.in` implementation from the in-process OHS call to the local read model. Application logic and the port interface are unchanged; only the adapter behind it is.

After cutover the read is eventually consistent and has no runtime dependency on the provider.

#### Synchronous REST client (extracted Open-Host Service)

The same `application.port.in` port is implemented by a REST client adapter in the consumer's `infrastructure.adapter.out.client.rest`. The consumer's application logic is untouched — it still depends on the same inbound port interface.

- *Advantages:* a mechanical swap, the port interface unchanged; reads are up-to-the-moment; no read model to build.
- *Disadvantages:* runtime and availability coupling — the caller now fails or stalls when the provider is down or slow. Reaching for synchronous cross-service calls on the request path is how a set of services becomes a [distributed monolith](glossary/distributed-monolith.md).

Migrating an in-process OHS read to a REST client is the mechanical swap named above: implement the port with a client adapter pointing at the provider's published OpenAPI endpoint. There is no read model to build and no backfill; application logic and the port interface are unchanged.

### 4. Publish the shared contract

The published language between the two services depends on the mechanisms chosen in step 3; add contract tests at the now-remote boundary for each (see [Testing Strategy](testing-strategy.md)).

- **Integration event schemas** are always part of the contract: every cross-context write, and every read served by a local projection, flows over them. Consumer and provider gain contract tests on those events.
- **The provider's OpenAPI spec** is published only when a read is served by the synchronous REST client. If every read is a local projection, no OpenAPI contract crosses the boundary.

## What does not change

- The consumer's `application.port.in` / `application.port.out` interfaces.
- The domain model and aggregate boundaries of either context.
- The outbox, CloudEvents envelope, event versioning, and schema-registry policy.
- The isolation rules — they applied in the monolith and still apply across services.
