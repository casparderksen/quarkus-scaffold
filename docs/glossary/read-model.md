# Read Model

A read model is a representation of data shaped and optimized for querying
rather than for enforcing business rules.

## Why

A read model is free to denormalize and to match the shape a screen or
report needs, because it never has to uphold invariants. This makes reads
fast and simple, at the cost of duplicating data the write model owns.

## Contrast

A [projection](projection.md) is a specific read-model shape returned from a
query; the read model is the broader idea.

## See also

[Projection](projection.md), [CQRS](cqrs.md).
