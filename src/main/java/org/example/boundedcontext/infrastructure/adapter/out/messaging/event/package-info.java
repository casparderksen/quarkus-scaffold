/**
 * Outbound integration event DTOs (transport-neutral).
 *
 * <p>Versioned external event payloads published by this bounded context, decoupled from the
 * wire envelope (e.g., CloudEvents) and from the transport (Kafka).
 *
 * <p>Naming convention: {@code <Noun><PastTense>EventV<n>}. Example: {@code OrderPlacedEventV1}.
 */
package org.example.boundedcontext.infrastructure.adapter.out.messaging.event;
