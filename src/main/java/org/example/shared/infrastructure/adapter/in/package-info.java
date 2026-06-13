/**
 * Shared inbound (driving) adapters reused across bounded contexts.
 *
 * <p>Cross-cutting entrypoints that belong to no single bounded context, such as the outbox poller
 * scheduler. Per-context inbound adapters live in {@code infrastructure.adapter.in}.
 */
package org.example.shared.infrastructure.adapter.in;
