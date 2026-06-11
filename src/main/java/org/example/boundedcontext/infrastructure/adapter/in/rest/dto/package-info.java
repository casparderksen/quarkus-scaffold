/**
 * REST wire DTOs: HTTP request and response bodies with Jackson and OpenAPI annotations.
 *
 * <p>Transport-coupled. Mapped to/from application commands and projections by the wire mapper.
 *
 * <p>Naming convention: {@code <Verb><Noun>Request}, {@code <Noun>Response}. Example: {@code
 * CreateOrderRequest}, {@code OrderResponse}.
 */
package org.example.boundedcontext.infrastructure.adapter.in.rest.dto;
