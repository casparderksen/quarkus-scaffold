# Read Path

The read path is the route that query handlers take to return projections
for display.

## Routing

The path taken depends on the read. A single-aggregate read whose projection
matches the aggregate shape can load through the repository and map inside
the handler. A list, search, report, or cross-aggregate read goes through a
query port and never hydrates an aggregate.

## Why

Splitting reads this way keeps simple reads simple while ensuring that
broad, display-oriented reads do not pay the cost of loading whole
aggregates.

## See also

[Query Port](query-port.md), [Projection](projection.md),
[Write Path](write-path.md).
