/**
 * Scheduled jobs: inbound driving adapters triggered by a timer or cron expression.
 *
 * <p>Job methods extract arguments (time window, batch size) and invoke a command or query handler.
 * No domain logic, transactions, or direct repository access.
 *
 * <p>Naming convention: {@code <Noun>Job}. Example: {@code OrderReconciliationJob}.
 */
package org.example.boundedcontext.infrastructure.adapter.in.scheduler;
