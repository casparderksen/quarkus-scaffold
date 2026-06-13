/**
 * JPA attribute converters for generic value types.
 *
 * <p>Maps cross-cutting types (e.g., {@link java.net.URI}) between Java and database column
 * representations. Domain-specific converters belong to their bounded context.
 *
 * <p>Naming convention: {@code <Type>AttributeConverter}. Example: {@code UriAttributeConverter}.
 */
package org.example.shared.infrastructure.adapter.out.persistence.jpa.converter;
