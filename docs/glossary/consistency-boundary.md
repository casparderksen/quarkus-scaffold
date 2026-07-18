# Consistency Boundary

A consistency boundary is the scope within which invariants must hold
atomically — everything inside is always mutually consistent after any
change.

## Why

The boundary coincides with the aggregate and defines the transaction scope.
Inside it, consistency is immediate and guaranteed.

## Rule

Consistency across boundaries is not immediate: separate aggregates reach
agreement eventually, through domain events, rather than in one atomic step.

## See also

[Aggregate](aggregate.md), [Eventual Consistency](eventual-consistency.md).
