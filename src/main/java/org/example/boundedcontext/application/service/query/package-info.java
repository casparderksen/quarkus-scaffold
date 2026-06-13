/**
 * Query handlers: read-only orchestration returning projection DTOs.
 *
 * <p>One query equals one handler. Does not mutate state.
 *
 * <p>Read-path choice is shape-driven: single-aggregate reads whose projection matches the
 * aggregate shape may load via {@code domain.repository} and map inside the handler. List, search,
 * report, and cross-aggregate reads must go through a query port in
 * {@code application.port.out.query} — never extend the domain repository to serve display needs.
 *
 * <p>Naming convention: {@code <Noun>Handler}. Example: {@code OrderHistoryHandler}.
 */
package org.example.boundedcontext.application.service.query;
