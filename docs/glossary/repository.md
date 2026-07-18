# Repository

A repository is an abstraction for loading and persisting aggregates,
letting the domain work with whole aggregates without knowing how they are
stored.

## Why

The domain deals in aggregates, not rows or documents. A repository hides
the storage mechanism behind operations shaped like the domain's own needs.

## Rules

A repository exists only for aggregate roots, never for child entities. It
exposes aggregate-shaped operations — save, delete, look up by identity or
by business key — and returns whole aggregates. It does not return read-side
projections, and it does not take query-shaped parameters such as filters,
sorts, or pagination meant for display.

## Contrast

A [query port](query-port.md) handles list, search, and report shapes that
do not match the aggregate.

## See also

[Aggregate](aggregate.md), [Query Port](query-port.md).
