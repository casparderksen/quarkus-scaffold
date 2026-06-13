/**
 * Mappers from domain events to outbound integration events.
 *
 * <p>Translate internal domain events into versioned external event contracts. Implementations of
 * the per-bounded-context {@code DomainEventTranslator} live here.
 *
 * <p>Naming convention: {@code <DomainEvent>Translator}. Example: {@code OrderPlacedTranslator}.
 */
package org.example.boundedcontext.infrastructure.adapter.out.messaging.mapper;
