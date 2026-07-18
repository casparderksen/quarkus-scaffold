# Invariant

An invariant is a business rule that must always hold true within an
aggregate's boundary, before and after every operation.

## Why

Invariants are the reason aggregates exist: they are the truths the model
promises never to violate. Enforcing them at the point of change is what
makes the model trustworthy.

## Example

"An order's total equals the sum of its line totals after discounts" — a
statement that is never allowed to be false.

## Rule

Invariants are enforced inside the aggregate's own construction and methods,
not checked from outside after the fact.

## See also

[Aggregate](aggregate.md), [Consistency Boundary](consistency-boundary.md).
