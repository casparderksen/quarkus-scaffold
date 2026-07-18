# Layered Architecture

Layered architecture organizes a system into logical layers — typically
domain, application, and infrastructure — each with a defined role and a
defined set of layers it may depend on.

## Why

Layers give a system a predictable shape: a reader knows where a given kind
of logic lives before opening a file. Combined with the outside-in
dependency rule, layering keeps business logic isolated from delivery and
storage concerns.

## See also

[Hexagonal Architecture](hexagonal-architecture.md),
[Clean Architecture](clean-architecture.md),
[Dependency Direction](dependency-direction.md).
