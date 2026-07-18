# Distributed Tracing

Distributed tracing follows a single request as it flows across the services
and components that handle it, stitching the pieces into one end-to-end view.

## Why

In a system of many services, a single user request touches several of them,
and no one service's logs tell the whole story. A trace links the spans of
work across services, so latency and failures can be attributed to the exact
step that caused them.

## Example

Spans of work for one request, correlated across services by identifiers
carried in the request headers.

## See also

[Observability](observability.md), [Correlation ID](correlation-id.md),
[Metrics](metrics.md).
