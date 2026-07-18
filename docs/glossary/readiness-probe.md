# Readiness Probe

A readiness probe checks whether an instance is currently able to serve
traffic.

## Why

An instance may be alive but not yet ready — still warming up, or temporarily
lacking a dependency. A failing readiness probe tells the platform to hold
traffic back without restarting the instance, then to resume once it reports
ready.

## Contrast

A [liveness probe](liveness-probe.md) decides whether to restart; a readiness
probe decides whether to route traffic.

## See also

[Health Check](health-check.md), [Liveness Probe](liveness-probe.md).
