/**
 * Read-side projection models: query-optimized, often denormalized views.
 *
 * <p>Returned by query handlers and by {@code application.port.out.query}. Bypass aggregates
 * entirely. Stable, transport-free, built from primitives or domain value objects. Wire-format
 * DTOs (Jackson/OpenAPI-annotated response bodies) do <em>not</em> live here — they belong to
 * the REST adapter under {@code infrastructure.adapter.in.rest.dto}.
 *
 * <p>Must not appear in the domain layer.
 *
 * <p>Naming convention: {@code <Noun>Projection}. Example: {@code OrderHistoryProjection}.
 */
package org.example.boundedcontext.application.projection;
