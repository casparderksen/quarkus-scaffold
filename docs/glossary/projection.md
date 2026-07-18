# Projection

A projection is a transformed view of domain data, shaped for reading and
returned directly from a query.

## Why

A projection avoids hydrating an aggregate for a display-only read. It pulls
just the fields a view needs, in the shape the view wants, so reads stay
cheap and decoupled from the write model.

## Example

An order-history view returned straight from a read query, never touching
the order aggregate.

## Contrast

A [read model](read-model.md) is the broader concept; a projection is a
specific shape returned from a query.

## See also

[Read Model](read-model.md), [Query Port](query-port.md).
