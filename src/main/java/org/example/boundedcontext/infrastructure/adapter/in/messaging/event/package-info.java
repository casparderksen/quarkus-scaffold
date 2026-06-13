/**
 * Inbound integration event DTOs (transport-neutral).
 *
 * <p>External event payloads consumed by this bounded context, decoupled from the wire envelope
 * (e.g., CloudEvents) and from the transport (Kafka).
 *
 * <p>Naming convention: {@code <Noun><PastTense>Event}. Example: {@code OrderShippedEvent}.
 */
package org.example.boundedcontext.infrastructure.adapter.in.messaging.event;
