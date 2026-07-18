# Factory (Domain Factory)

A domain factory is a component that creates complex aggregates or entities,
encapsulating the rules that a valid construction must satisfy.

## Why

Some objects cannot be assembled field by field without risking an invalid
state. A factory concentrates the creation invariants in one place, so a new
aggregate is born valid rather than being validated after the fact.

## Example

A creation method on the order aggregate that takes a customer and lines and
returns a fully valid order, refusing inputs that could not form one.

## See also

[Aggregate Root](aggregate-root.md), [Invariant](invariant.md).
