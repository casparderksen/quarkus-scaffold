# Domain Service

A domain service holds domain logic that does not belong naturally to any
single aggregate because it spans several.

## Why

Some behavior coordinates across aggregates and would distort any one of
them if forced inside. A domain service captures that behavior without
inflating one aggregate into a catch-all that reaches into others.

## Example

A pricing service that computes a price from a customer's segment, the
order, and current promotions — inputs owned by different aggregates.

## Contrast

A [policy](policy.md) is a stateless decision or computation; a domain
service coordinates across aggregates.

## See also

[Aggregate](aggregate.md), [Policy](policy.md), [Specification](specification.md).
