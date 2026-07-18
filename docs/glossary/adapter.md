# Adapter

An adapter is an implementation that connects a port to a concrete external
system, translating between the outside representation and the core's
internal model.

## Why

Keeping translation in adapters means the core speaks only its own language.
Each adapter absorbs the quirks of one technology so those quirks never
reach business logic.

## Contrast

An [inbound adapter](inbound-adapter.md) drives the application from
outside; an [outbound adapter](outbound-adapter.md) is driven by the
application to reach something outside.

## See also

[Port](port.md), [Hexagonal Architecture](hexagonal-architecture.md).
