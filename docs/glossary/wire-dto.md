# Wire DTO

A wire DTO is a transport-coupled request or response model that lives at the
system's edge, shaped to the protocol rather than to the application.

## Why

It carries the serialization and documentation concerns of the transport, so
those concerns never reach the application contract. The edge speaks the
protocol's language; the application speaks its own.

## Example

The request and response shapes of a web endpoint, matched to the external
API's fields.

## Rule

A wire DTO belongs to the delivery adapter and never reaches the application
or the domain; it is translated at the boundary into a command, query, or
domain value.

## See also

[Command](command.md), [Projection DTO](projection-dto.md), [Mapper](mapper.md).
