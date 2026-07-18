# Outside-in Rule

The outside-in rule states that dependencies always point inward, toward the
domain core, and never outward toward transport or infrastructure.

## Why

Keeping every reference pointed inward leaves the inner layers free of any
framework or delivery concern. Those inner layers become the most stable
part of the system, because nothing they depend on changes when a protocol
or a database does.

## Example

A REST entry point depends on an application capability; the application
capability has no idea that REST exists. Influence flows in; knowledge does
not flow out.

## See also

[Dependency Direction](dependency-direction.md),
[Clean Architecture](clean-architecture.md),
[Hexagonal Architecture](hexagonal-architecture.md).
