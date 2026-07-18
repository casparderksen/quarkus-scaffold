# Command

A command is a request that changes system state, naming the intent of the
change and carrying the values it needs.

## Why

A command names what the caller wants to happen, in the domain's own terms,
rather than describing a transport payload. This keeps intent explicit and
keeps the use case free of delivery concerns.

## Example

A "create order" request carrying a customer and a set of lines.

## Rule

A command is built from primitives or domain values and is free of transport
types; it is the same whatever protocol delivered it.

## Contrast

A [query](query.md) retrieves data and changes nothing.

## See also

[Use Case](use-case.md), [Handler](handler.md).
