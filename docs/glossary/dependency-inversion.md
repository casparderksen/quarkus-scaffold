# Dependency Inversion

Dependency inversion is the principle that high-level policy depends on
abstractions, and low-level details depend on those same abstractions —
never the reverse.

## Why

It is central to hexagonal architecture. When the core defines the
interfaces it needs and infrastructure implements them, the core can be
tested and re-targeted without touching any concrete technology. Stable
business rules stop depending on volatile technical choices.

## Example

The application defines a repository interface expressing how it wants to
load and store orders; a storage adapter implements that interface. The
application knows the abstraction; it never knows the storage technology.

## See also

[Hexagonal Architecture](hexagonal-architecture.md), [Port](port.md),
[Dependency Direction](dependency-direction.md).
