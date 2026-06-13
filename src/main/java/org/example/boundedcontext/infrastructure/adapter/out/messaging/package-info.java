/**
 * Outbound messaging implementations for this bounded context.
 *
 * <p>Implement {@code application.port.out.messaging} ports. Split into {@code event} (versioned
 * integration event DTOs), {@code mapper} (domain event → integration event translation), and
 * {@code kafka} (transport bindings). Wrap payloads in CloudEvents envelopes via
 * {@code shared.infrastructure.adapter.out.messaging.cloudevents} and publish via the transactional
 * outbox in {@code shared.infrastructure.adapter.out.messaging.outbox}.
 */
package org.example.boundedcontext.infrastructure.adapter.out.messaging;
