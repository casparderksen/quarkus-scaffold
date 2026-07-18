# Chaos Test

A chaos test deliberately injects failures into a running system to verify
that it withstands them.

## Why

A system's resilience mechanisms — retries, breakers, failovers — are only
proven when they actually face failure. By intentionally killing instances,
severing connections, or slowing dependencies, a chaos test confirms the
system degrades gracefully instead of collapsing, exposing fragile
assumptions before a real outage does.

## See also

[Circuit Breaker](circuit-breaker.md), [Bulkhead Pattern](bulkhead-pattern.md),
[Performance Test](performance-test.md).
