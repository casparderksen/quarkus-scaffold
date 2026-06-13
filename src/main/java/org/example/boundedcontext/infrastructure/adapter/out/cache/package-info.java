/**
 * Cache adapters for this bounded context.
 *
 * <p>Implement {@code application.port.out.cache} ports against a cache backend (e.g., Redis,
 * Caffeine). Stores projection DTOs or value-object snapshots; never the source of truth.
 *
 * <p>Naming convention: {@code <Aggregate>CacheRepository}. Example: {@code OrderCacheRepository}.
 */
package org.example.boundedcontext.infrastructure.adapter.out.cache;
