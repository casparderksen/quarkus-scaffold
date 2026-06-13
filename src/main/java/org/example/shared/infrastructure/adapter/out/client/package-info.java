/**
 * Shared external client infrastructure: cross-cutting HTTP, gRPC, and SOAP plumbing reused across
 * bounded contexts (interceptors, retry/timeout policies, auth propagation, base client config).
 *
 * <p>Per-context client adapters live in {@code infrastructure.adapter.out.client}.
 */
package org.example.shared.infrastructure.adapter.out.client;
