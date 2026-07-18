# Observability

Observability is the ability to understand a system's internal behavior from
the telemetry it emits, without having to reproduce a problem or attach a
debugger.

## Why

In a distributed system, failures are often emergent and hard to reproduce.
Rich telemetry lets an operator ask new questions of a running system after
the fact — why is this slow, where did this request fail — rather than only
checking conditions someone anticipated in advance.

## Pillars

Logging records what happened, metrics measure how much and how fast, and
tracing follows a single request across the components it touches.

## See also

[Structured Logging](structured-logging.md), [Metrics](metrics.md),
[Distributed Tracing](distributed-tracing.md).
