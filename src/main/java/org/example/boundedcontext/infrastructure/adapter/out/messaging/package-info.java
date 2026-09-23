/**
 * Outbound messaging implementations for this bounded context.
 *
 * <p>Implement {@code application.port.out.messaging} ports. Split into {@code event} (versioned
 * integration event DTOs) and {@code mapper} (domain event → integration event translation). Wrap
 * payloads in CloudEvents envelopes via
 * {@code shared.infrastructure.adapter.out.messaging.cloudevents} and publish via the transactional
 * outbox in {@code shared.infrastructure.adapter.out.messaging.outbox}.
 *
 * <p>No per-context publishing code. Translation and versioning happen in the originating
 * transaction (Phase A); the generic outbox publisher performs the broker send out-of-band (Phase
 * B) and is the only component that touches the transport. See
 * {@code docs/transactional_outbox.md}.
 */
package org.example.boundedcontext.infrastructure.adapter.out.messaging;
