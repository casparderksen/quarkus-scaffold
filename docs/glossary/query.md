# Query

A query is a request that retrieves data without modifying any state.

## Why

Naming reads as queries separates them cleanly from commands, so a reader can
tell at a glance whether an operation changes anything. A query carries only
the criteria of what to fetch.

## Example

An "order history" request carrying a customer and a page size.

## Contrast

A [command](command.md) changes state; a query never does.

## See also

[Query Port](query-port.md), [Projection](projection.md).
