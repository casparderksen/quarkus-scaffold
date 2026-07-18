# Use Case

A use case is a single business operation the application layer exposes —
one named capability with one input and one outcome.

## Why

Framing capabilities as discrete use cases keeps each one focused and
independently testable, and it names what the system can do in the
business's own terms rather than in terms of screens or endpoints.

## Example

"Create order" or "list order history" — each a complete operation a caller
can request.

## Implementation

A use case is expressed as a command or a query contract and carried out by
a single handler.

## See also

[Command](command.md), [Query](query.md), [Handler](handler.md).
