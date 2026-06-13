/**
 * Inbound messaging adapters: drive the application from external integration events.
 *
 * <p>The adapter unwraps the envelope, validates, deduplicates, and maps the external event to a
 * local command before invoking a command handler.
 */
package org.example.boundedcontext.infrastructure.adapter.in.messaging;
