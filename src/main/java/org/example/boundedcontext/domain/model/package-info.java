/**
 * Aggregates, entities, and value objects.
 *
 * <ul>
 *   <li>Aggregate root: primary entity controlling access to an aggregate; enforces invariants.
 *   <li>Entity: domain object defined primarily by identity.
 *   <li>Value object: immutable object defined only by values; no identity.
 * </ul>
 *
 * <p>Invariants are enforced inside the model (constructors and methods).
 *
 * <p>Naming convention: noun. Example: {@code Order}, {@code OrderLine}, {@code Money},
 * {@code OrderId}.
 */
package org.example.boundedcontext.domain.model;
