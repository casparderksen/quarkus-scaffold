/**
 * Problem Detail mapping and per-context catalog of problem types.
 *
 * <p>Registers each domain exception with the {@code quarkus-http-problem} library, declaring HTTP
 * status, Problem Detail {@code type} URI, and which fields surface as RFC 9457 extensions.
 *
 * <p>Naming convention: {@code <Exception>ProblemMapper}, {@code <Context>ProblemCatalog}.
 */
package org.example.boundedcontext.infrastructure.adapter.in.rest.error;
