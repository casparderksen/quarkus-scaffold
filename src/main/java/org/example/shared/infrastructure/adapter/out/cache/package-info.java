/**
 * Shared cache infrastructure: cross-cutting backends, clients, and templates (Redis, Caffeine).
 *
 * <p>Houses framework wiring reused by per-context cache adapters in
 * {@code infrastructure.adapter.out.cache}. No bounded-context cache repositories live here.
 *
 * <p>Naming convention: {@code <Backend><Concept>}. Example: {@code RedisCacheTemplate}.
 */
package org.example.shared.infrastructure.adapter.out.cache;
