/**
 * Shared persistence infrastructure: cross-cutting database access utilities reused across bounded
 * contexts.
 *
 * <p>JPA-specific cross-cutting concerns (attribute converters, base listeners, naming strategies)
 * live under {@code jpa}. Per-context repository adapters live in
 * {@code infrastructure.adapter.out.persistence}.
 */
package org.example.shared.infrastructure.adapter.out.persistence;
