# Health Check

A health check is an endpoint that reports whether an application is healthy,
so that orchestration infrastructure can act on the answer.

## Why

An automated platform needs a programmatic way to ask "is this instance
alright?" so it can restart a stuck process or stop sending traffic to one
that is not ready. A health check gives it that answer, commonly split into
liveness and readiness.

## See also

[Liveness Probe](liveness-probe.md), [Readiness Probe](readiness-probe.md).
