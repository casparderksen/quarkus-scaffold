# Extracting a Bounded Context to a Microservice

The scaffold is a modular monolith whose bounded contexts are already isolated by package, ownership, and the Open-Host Service boundary. Extraction turns one context into a separately deployed service without redesigning it. This document is the procedure; the rules it relies on live in [README — Cross-context integration](../README.md#cross-context-integration).

## What makes extraction cheap

The monolith is built so that the only thing that changes on extraction is the *implementation* behind a port, never the port itself:

- **No cross-context internals.** A context is reached only through its `application.port.in` (Open-Host Service). No consumer imports another context's `domain`, `application.port.out`, `infrastructure`, or reads its tables. There are no compile edges to sever except the ones landing in `application.port.in`.
- **No shared tables.** Each context owns its tables and migrations; there are no cross-context foreign keys or joins. A context's data moves with it.
- **Events, not shared transactions.** Cross-context state changes already flow through integration events and the transactional outbox, not through a shared transaction. That machinery is unchanged by extraction.

## Procedure

1. **Move the migration folder.** `src/main/resources/db/migration/<context>/` moves to the new service. It runs the same migrations against the new service's own database. The monolith drops that location from its Flyway configuration. Table names are unprefixed and need no renaming — nothing in another context referenced them.

2. **Swap the Open-Host Service implementation.** In the monolith, a consumer injected the provider's `application.port.in` and the call was an in-process CDI bean. After extraction, the same port is implemented by a REST client adapter in the consumer's `infrastructure.adapter.out.client.rest`. The consumer's application logic is untouched — it still depends on the same inbound port interface.

3. **Publish the shared contract.** The provider's `application.port.in` contracts (and its integration event schemas) become the published language between the two services. The provider publishes its OpenAPI spec; consumer and provider gain contract tests at the now-remote boundary (see [Testing Strategy — Cross-context Open-Host Service](testing-strategy.md)).

4. **Repoint messaging.** Integration events already travel over CloudEvents through the outbox and Kafka. After extraction they cross a real broker between services instead of staying in one deployment. The producer, outbox, and consumer code are unchanged; only topic wiring and broker configuration are.

## Prefer async projection over synchronous calls

In the monolith a synchronous Open-Host Service call is a cheap in-process call. Across the network the same call couples the two services at runtime and availability: the caller now fails or stalls when the provider is down or slow. Reaching for synchronous cross-service calls on the request path is how a set of services becomes a [distributed monolith](glossary.md).

After extraction, prefer building a **local projection fed by the provider's integration events** (CloudEvents) over calling the provider synchronously:

- The consumer subscribes to the provider's integration events and maintains its own read model of exactly the data it needs.
- Reads then hit local data — no runtime dependency on the provider being up.
- Consistency is eventual, which the architecture already assumes for cross-context data.

Reserve the synchronous REST client (the extracted Open-Host Service call) for reads whose freshness genuinely requires an up-to-the-moment answer and where a projection's staleness is unacceptable.

## What does not change

- The consumer's `application.port.in` / `application.port.out` interfaces.
- The domain model and aggregate boundaries of either context.
- The outbox, CloudEvents envelope, event versioning, and schema-registry policy.
- The isolation rules — they applied in the monolith and still apply across services.
