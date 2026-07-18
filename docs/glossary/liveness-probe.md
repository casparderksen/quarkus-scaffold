# Liveness Probe

A liveness probe checks whether a process is still alive and functioning, as
opposed to hung or deadlocked.

## Why

A process can be running yet unable to make progress. A failing liveness
probe signals the platform to restart the instance, recovering it from a
state it could not escape on its own.

## Contrast

A [readiness probe](readiness-probe.md) asks whether an instance can serve
traffic now; a liveness probe asks whether it should be restarted.

## See also

[Health Check](health-check.md), [Readiness Probe](readiness-probe.md).
