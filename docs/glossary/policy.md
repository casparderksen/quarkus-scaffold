# Policy

A policy is a stateless business decision or computation rule — a captured
answer to "how do we decide or calculate this".

## Why

Isolating a decision from the aggregate's state means the rule can evolve on
its own and survive changes to how the aggregate is shaped. The aggregate
holds state; the policy holds the reasoning applied to it.

## Example

A shipping-cost rule that computes a charge from weight, destination, and
carrier.

## Contrast

A [specification](specification.md) returns a boolean; a
[domain service](domain-service.md) coordinates several aggregates, whereas
a policy decides or computes.

## See also

[Specification](specification.md), [Domain Service](domain-service.md).
