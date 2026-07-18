# Aggregate

An aggregate is a consistency boundary that groups entities and value
objects mutated as a single unit.

## Why

An aggregate enforces its invariants atomically. Because everything inside
it commits together, it also defines the transaction scope: one aggregate
per transaction.

## When

Reach for an aggregate when the domain has rules spanning multiple objects
that must hold together. If no rule couples the objects, they do not belong
in the same aggregate.

## Example

An order rejects a transition to shipped unless its state is paid. The rule
lives on the aggregate, so no caller can bypass it.

## Rules

External code accesses an aggregate only through its root. References
between aggregates are held by identity, never by direct object reference,
and child entities have no repository of their own. An aggregate holds only
the data its invariants depend on; display-only fields belong in a
projection.

## Contrast

An [entity](entity.md) has identity but no consistency role. A
[value object](value-object.md) has no identity and is immutable.

## See also

[Aggregate Root](aggregate-root.md), [Invariant](invariant.md),
[Consistency Boundary](consistency-boundary.md), [Repository](repository.md).
