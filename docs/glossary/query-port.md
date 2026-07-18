# Query Port

A query port is an outbound port for read-side queries that bypass
aggregates entirely.

## Why

List, search, report, and cross-aggregate reads should not load and hydrate
whole aggregates just to show a few fields. A query port lets the read path
fetch exactly the projection it needs, straight from storage, without going
through the write model.

## Example

An "order history" query port that returns a list of history projections,
implemented by a read query against storage.

## Contrast

A [repository](repository.md) loads whole aggregates for mutation; a query
port returns projections for reading.

## See also

[Read Path](read-path.md), [Projection](projection.md).
