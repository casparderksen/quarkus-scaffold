/**
 * CloudEvents envelope, binding helpers, and SDK dependency.
 *
 * <p>Sole module that imports the CloudEvents SDK. Reused by inbound Kafka consumers when
 * unwrapping incoming events and by outbound messaging adapters when wrapping domain events for
 * publication. Application and domain layers never depend on this package.
 *
 * <p>Naming convention: {@code CloudEvent<Concept>}. Example: {@code CloudEventEnvelope}.
 */
package org.example.shared.infrastructure.adapter.out.messaging.cloudevents;
