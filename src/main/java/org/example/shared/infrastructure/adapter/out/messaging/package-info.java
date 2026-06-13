/**
 * Shared messaging infrastructure reused across bounded contexts.
 *
 * <p>Contains the CloudEvents envelope/binding ({@code cloudevents}) and the transactional outbox
 * publisher ({@code outbox}). Per-context producers and integration event DTOs live in
 * {@code infrastructure.adapter.out.messaging}.
 */
package org.example.shared.infrastructure.adapter.out.messaging;
