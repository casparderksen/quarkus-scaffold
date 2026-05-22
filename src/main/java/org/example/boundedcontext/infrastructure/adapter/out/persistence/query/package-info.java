/**
 * Read-side optimized queries and projections.
 *
 * <p>Bypass entities; return DTOs via JPQL, native queries, or dedicated read models to avoid lazy
 * loading and N+1 queries.
 *
 * <p>Naming convention: {@code <Noun>QueryRepository}. Example: {@code OrderQueryRepository}.
 */
package org.example.boundedcontext.infrastructure.adapter.out.persistence.query;
