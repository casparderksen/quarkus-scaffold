/**
 * Idempotency framework for inbound entrypoints.
 *
 * <p>Allows repeated execution without changing the outcome; critical for at-least-once delivery.
 *
 * <p>Naming convention: {@code Idempotent<Concept>}. Example: {@code IdempotencyFilter}.
 */
package org.example.shared.infrastructure.adapter.in.idempotency;
