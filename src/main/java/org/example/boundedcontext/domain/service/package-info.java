/**
 * Domain services for logic spanning multiple aggregates.
 *
 * <p>Used only when the behavior does not naturally belong to a single aggregate. Stateless where
 * possible.
 *
 * <p>Naming convention: {@code <Capability>Service}. Example: {@code PricingService}.
 */
package org.example.boundedcontext.domain.service;
