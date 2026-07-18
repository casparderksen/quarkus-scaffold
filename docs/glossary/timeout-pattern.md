# Timeout Pattern

The timeout pattern bounds how long a request may run before it is abandoned
as failed.

## Why

A call with no time limit can hang indefinitely on a degraded dependency,
holding resources and stalling whatever waits behind it. A timeout converts
an unbounded wait into a prompt, handleable failure.

## See also

[Circuit Breaker](circuit-breaker.md), [Retry Policy](retry-policy.md),
[Bulkhead Pattern](bulkhead-pattern.md).
