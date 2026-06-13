/**
 * Repository contracts for aggregate roots.
 *
 * <p>Abstractions for loading and persisting aggregates. Work with aggregates, not database tables.
 * Implementations live in {@code infrastructure.adapter.out.persistence.jpa}.
 *
 * <p>Expose only aggregate-shaped operations needed by commands: {@code save}, {@code delete},
 * {@code findById}, and business-key lookups used to enforce invariants or detect duplicates. Never
 * accept query-shape parameters (filters, sorts, pagination) or return projection DTOs — those
 * belong to the query path under {@code application.port.out.query}.
 *
 * <p>Repositories exist only for aggregate roots, never for child entities.
 *
 * <p>Naming convention: {@code <Aggregate>Repository}. Example: {@code OrderRepository}.
 */
package org.example.boundedcontext.domain.repository;
