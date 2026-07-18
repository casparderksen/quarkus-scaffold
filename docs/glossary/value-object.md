# Value Object

A value object is an immutable domain object defined entirely by its values,
with no identity of its own.

## Why

Because two value objects with the same values are interchangeable, they can
be shared freely and compared by value, and their immutability rules out a
whole class of aliasing bugs where one holder's change surprises another.

## Example

An amount of money combining a quantity and a currency, or an identifier
value — equal whenever their contents are equal.

## Contrast

An [entity](entity.md) is defined by identity and persists across changes to
its values.

## See also

[Entity](entity.md), [Aggregate](aggregate.md).
