# Encapsulation

Encapsulation is the bundling of data together with the behavior that
operates on it into one cohesive unit that guards its own state.

## Why

By exposing behavior rather than raw data, a unit controls how its state
changes and can guarantee that it never enters an invalid configuration.
This enforces information hiding and separation of concerns: callers depend
on what the unit does, not on how it stores things.

## See also

[Information Hiding](information-hiding.md), [Cohesion](cohesion.md),
[Invariant](invariant.md).
