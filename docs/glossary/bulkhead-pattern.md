# Bulkhead Pattern

The bulkhead pattern isolates resources so that failure in one part of a
system cannot exhaust the resources another part depends on.

## Why

Named after the compartments that keep a breached ship afloat, it partitions
things like connection or thread pools per dependency. A slow or failing
dependency can drain only its own compartment, leaving the rest of the system
able to keep working.

## See also

[Circuit Breaker](circuit-breaker.md), [Timeout Pattern](timeout-pattern.md).
