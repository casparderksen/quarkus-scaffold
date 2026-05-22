/**
 * Application layer: orchestrates domain logic and external dependencies.
 *
 * <p>Manages transactions and workflow execution. Contains no core business rules. Depends only on
 * {@code domain}; must not depend on {@code infrastructure}.
 */
package org.example.boundedcontext.application;
