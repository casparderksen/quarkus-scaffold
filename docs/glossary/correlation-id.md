# Correlation ID

A correlation ID is an identifier propagated across requests and services so
that all the work belonging to one logical operation can be tied together.

## Why

It joins a client-visible error to the corresponding server-side logs
without leaking any internal detail. Given the ID from a failed response, an
operator can find exactly the trace that produced it.

## Surfacing

The identifier appears both as a field in the error payload and as a
response header, so a client always has a handle to quote when reporting a
problem.

## See also

[Distributed Tracing](distributed-tracing.md), [Observability](observability.md).
