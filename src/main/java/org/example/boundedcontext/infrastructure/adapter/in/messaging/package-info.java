/**
 * Inbound messaging adapters: drive the application from external integration events.
 *
 * <p>The adapter unwraps the envelope, validates, deduplicates, and maps the external event to a
 * local command before invoking a command handler.
 *
 * <p>Consumers live directly in this package, beside the {@code event} (inbound integration event
 * DTOs) and {@code mapper} (event → command translation) subpackages — the same layout as
 * {@code infrastructure.adapter.in.rest}.
 *
 * <p>A consumer binds to a <em>channel</em>, not to a broker. The channel name in
 * {@code @Incoming} is mapped to a connector and a topic in configuration
 * ({@code mp.messaging.incoming.<channel>.connector}), so switching broker, or substituting the
 * in-memory connector in tests, changes configuration only and moves no code. Channel name and
 * topic name are configured independently and need not match, so consumers are named after the
 * event they consume, never after a topic. Classes that do import broker types belong in
 * {@code shared.infrastructure.adapter.out.messaging.kafka}.
 *
 * <p>Naming convention: {@code <Event>Consumer}. Example: {@code OrderShippedEventConsumer}.
 */
package org.example.boundedcontext.infrastructure.adapter.in.messaging;
