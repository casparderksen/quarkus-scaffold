# Port

A port is an abstraction that defines a point of communication with the
application core, described in the core's own terms rather than any
technology's.

## Why

A port separates what the core offers or needs from how that is technically
carried out, so implementations can change while the contract stays fixed.

## Contrast

An [inbound port](inbound-port.md) describes a capability the application
offers to the outside; an [outbound port](outbound-port.md) describes a
dependency the application needs from the outside.

## See also

[Adapter](adapter.md), [Hexagonal Architecture](hexagonal-architecture.md).
