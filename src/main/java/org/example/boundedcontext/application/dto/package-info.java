/**
 * Application-layer transport models: use case input/output types that are not commands or queries.
 *
 * <p>Stable, transport-free, built from primitives or domain value objects. Wire-format DTOs
 * (Jackson/OpenAPI-annotated request and response bodies) do <em>not</em> live here — they belong
 * to the REST adapter under {@code infrastructure.adapter.in.rest.dto}. Read-side models live in
 * {@code projection}.
 *
 * <p>Must not appear in the domain layer.
 */
package org.example.boundedcontext.application.dto;
