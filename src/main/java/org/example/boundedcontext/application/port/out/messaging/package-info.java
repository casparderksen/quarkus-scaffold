/**
 * Outbound asynchronous messaging and eventing contracts.
 *
 * <p>No direct external publishing from domain or application logic — events flow through these
 * ports to adapters using the transactional outbox pattern.
 *
 * <p>Naming convention: {@code <Event>Publisher} or {@code <Topic>Producer}.
 */
package org.example.boundedcontext.application.port.out.messaging;
