# Eventual Consistency

Eventual consistency is a model in which the state across separate services
or aggregates converges over time rather than being consistent at every
instant.

## Why

Holding a single atomic transaction across services is expensive and
fragile. Eventual consistency accepts a window in which parts of the system
disagree, in exchange for autonomy and availability, trusting them to
reconcile through asynchronous events.

## See also

[Consistency Boundary](consistency-boundary.md),
[Compensating Transaction](compensating-transaction.md).
