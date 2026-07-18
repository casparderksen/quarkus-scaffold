# Transaction Boundary

A transaction boundary is the scope within which a set of changes is
committed atomically — all of them succeed or none do.

## Rule

The boundary lives at the application's use-case entry, on the handler.
Domain objects and adapters carry no transaction control of their own. A
single transaction mutates a single aggregate.

## Why

Anchoring the boundary at the use case gives each operation one clear atomic
scope, and confining it to one aggregate keeps consistency guarantees within
the boundary that owns them.

## See also

[Handler](handler.md), [Aggregate](aggregate.md),
[Consistency Boundary](consistency-boundary.md).
