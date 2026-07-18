# Outbound Port

An outbound port is a contract describing a dependency the application core
needs from the outside world, expressed in the core's own language.

## Why

The core states its needs as abstractions and lets infrastructure satisfy
them. This inverts the dependency: the technology depends on the core's
contract, not the other way around.

## Categories

Persistence (loading and storing aggregates), messaging (publishing
events), external clients (calling another service), read queries
(fetching projections), and caching.

## See also

[Adapter](adapter.md), [Dependency Inversion](dependency-inversion.md).
