/**
 * Domain-specific business exceptions.
 *
 * <p>Transport-free: carry only business context as typed fields. No HTTP status, Problem Detail
 * URIs, or library annotations — reusable across REST, gRPC, and messaging adapters. HTTP status
 * and RFC 9457 extension fields are declared in {@code infrastructure.adapter.in.rest.error}.
 *
 * <p>Naming convention: {@code <Concept>Exception}. Example: {@code OrderNotFoundException}.
 */
package org.example.boundedcontext.domain.exception;
