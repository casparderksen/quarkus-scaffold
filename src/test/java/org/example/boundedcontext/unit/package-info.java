/**
 * Fast isolated unit tests for the bounded context.
 *
 * <p>No external systems (DB, HTTP, Kafka). No framework boot. Domain tests use only real objects
 * and fakes — never mocks. Application tests may mock outbound ports only.
 *
 * <p>Naming convention: {@code <Type>Test}. Example: {@code OrderTest}.
 */
package org.example.boundedcontext.unit;
