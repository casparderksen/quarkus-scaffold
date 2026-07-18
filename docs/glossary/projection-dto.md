# Projection DTO

A projection DTO is the read-side response shape returned by a query handler,
built to match what a reader needs rather than the shape of an aggregate.

## Why

Read needs rarely match write shapes. A projection decouples the read model
from the aggregate and enables read paths optimized for display, without
distorting the aggregate to serve queries.

## See also

[Projection](projection.md), [Read Model](read-model.md), [Query](query.md).
