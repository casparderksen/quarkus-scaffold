# Entity

An entity is a domain object defined primarily by its identity, which
remains the same object even as its attributes change.

## Why

Some things in a domain are tracked as the same individual over time — a
particular customer stays that customer whether their name or address
changes. Identity, not the current values, is what defines them.

## Example

A customer with a stable identifier: rename them and they are still the same
customer.

## Contrast

A [value object](value-object.md) has no identity and is defined only by its
values.

## See also

[Aggregate](aggregate.md), [Value Object](value-object.md).
