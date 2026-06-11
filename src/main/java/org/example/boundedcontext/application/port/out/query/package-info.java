/**
 * Read-side query ports: outbound contracts for projection retrieval.
 *
 * <p>Implemented by adapters in {@code infrastructure.adapter.out.persistence.query}. Bypass the
 * domain entirely for list, search, report, and cross-aggregate reads.
 *
 * <p>Naming convention: {@code <Noun>QueryPort}. Example: {@code OrderHistoryQueryPort}.
 */
package org.example.boundedcontext.application.port.out.query;
