# Hexagonal Architecture

Hexagonal architecture, also called ports and adapters, is an architectural
style that isolates business logic from external systems by placing a ring
of ports around the core and connecting each port to the outside world
through an adapter.

## Why

The core can be exercised in tests without any real infrastructure, because
every interaction with the outside crosses a port it fully controls.
Technologies live behind those ports, so a storage engine, a protocol, or a
message broker can be swapped without touching business logic.

## See also

[Port](port.md), [Adapter](adapter.md),
[Dependency Inversion](dependency-inversion.md).
