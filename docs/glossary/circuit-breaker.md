# Circuit Breaker

A circuit breaker is a resilience mechanism that stops calling a dependency
that is currently failing, and periodically checks whether it has recovered.

## Why

Hammering a struggling dependency with more calls tends to make things worse
and ties up the caller's own resources waiting on failures. By tripping open
after repeated failures, the breaker fails fast and gives the dependency room
to recover, preventing a cascade.

## See also

[Retry Policy](retry-policy.md), [Bulkhead Pattern](bulkhead-pattern.md),
[Timeout Pattern](timeout-pattern.md).
