/**
 * Shared messaging infrastructure reused across bounded contexts.
 *
 * <p>Contains the CloudEvents envelope/binding ({@code cloudevents}), the transactional outbox
 * publisher ({@code outbox}), and the Kafka connector wiring ({@code kafka}) — the only package
 * coupled to the broker. Per-context integration event DTOs and domain event translators live in
 * {@code infrastructure.adapter.out.messaging}.
 */
package org.example.shared.infrastructure.adapter.out.messaging;
