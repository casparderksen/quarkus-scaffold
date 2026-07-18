# DTO (Data Transfer Object)

A DTO is a simple structure used to carry data between layers or systems,
holding values with no behavior of its own.

## Why

A DTO keeps domain models from leaking outward and decouples an external
wire format from the internal model, so each side can change without
dragging the other along.

## Variants

A wire DTO is coupled to transport and lives at the edge; a projection DTO
is a read-side response shape; commands and queries are the input shapes of
use cases. Each is a DTO specialized to one role.

## See also

[Wire DTO](wire-dto.md), [Projection DTO](projection-dto.md),
[Command](command.md), [Query](query.md).
