/**
 * Shared scheduled jobs (inbound driving adapters).
 *
 * <p>Cross-cutting timer-driven jobs that belong to no single bounded context, such as the
 * transactional outbox poller.
 *
 * <p>Naming convention: {@code <Noun>Job} or {@code <Noun>Poller}. Example: {@code OutboxPoller}.
 */
package org.example.shared.infrastructure.adapter.in.scheduler;
