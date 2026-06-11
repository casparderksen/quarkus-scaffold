/**
 * Wire DTO ↔ command/projection mapping for the REST adapter.
 *
 * <p>Keeps Jackson and OpenAPI annotations out of the application layer. Mapping is local to the
 * adapter unless reused across multiple use cases.
 *
 * <p>Naming convention: {@code <Noun>RestMapper}. Example: {@code OrderRestMapper}.
 */
package org.example.boundedcontext.infrastructure.adapter.in.rest.mapper;
