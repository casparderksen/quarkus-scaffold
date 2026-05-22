/**
 * Read-side projection models: query-optimized, often denormalized views.
 *
 * <p>Returned by query handlers. Bypass aggregates entirely.
 *
 * <p>Naming convention: {@code <Noun>Projection}. Example: {@code OrderHistoryProjection}.
 */
package org.example.boundedcontext.application.dto.projection;
