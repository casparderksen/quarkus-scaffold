# Observability Testing

Observability testing verifies that a system emits the logs, metrics, and
traces it is supposed to, and that its monitoring signals are correct.

## Why

Telemetry is what operators rely on during an incident, yet it is easy to
break unnoticed because nothing functional depends on it. Testing it treats
observability as a feature in its own right, so that when a failure happens,
the signals needed to diagnose it are actually present.

## See also

[Observability](observability.md), [Structured Logging](structured-logging.md),
[Distributed Tracing](distributed-tracing.md).
