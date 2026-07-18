# Bounded Context

A bounded context is a boundary within which a particular domain model is
consistent and its terms have a single, agreed meaning.

## Why

The same word means different things in different parts of a business — a
"customer" in sales is not the same object as a "customer" in billing.
Drawing an explicit boundary lets each side keep its own coherent model and
prevents the two from silently coupling into one confused model.

## Example

An ordering context owns orders and their lines; a customer context owns
customer profiles. Each defines the terms it needs without deferring to the
other's shape.

## Rule

No model crosses a context boundary directly; contexts integrate only
through deliberate, published means, never by importing each other's
internals.

## See also

[Context Map](context-map.md), [Shared Kernel](shared-kernel.md),
[Anti-Corruption Layer](anti-corruption-layer.md).
