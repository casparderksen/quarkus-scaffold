# Anemic Domain Model

An anemic domain model is one whose objects hold data but almost no behavior,
with the business logic that should live in them leaking into surrounding
services instead.

## Why it is bad

When the model is just data, its invariants have nowhere to be enforced, so
rules scatter across services and the same object can be left in an invalid
state by any of them. The application layer swells into a procedural monolith
that does the domain's real work.

## Fix

Move business behavior back onto the aggregates and domain services where it
belongs, so the model guards its own state.

## See also

[Transaction Script](transaction-script.md),
[Smart Controller / Thin Domain](smart-controller-thin-domain.md),
[Aggregate](aggregate.md).
