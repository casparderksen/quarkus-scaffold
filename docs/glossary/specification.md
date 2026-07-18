# Specification

A specification is a reusable business predicate — a named rule that answers
yes or no about whether something satisfies a condition.

## Why

Wrapping a boolean rule in a named object lets the same rule be reused for
selecting objects and for validating them, instead of being copied as an ad
hoc condition in several places.

## Example

An "eligible for free shipping" rule that can be asked of any order and
answers true or false.

## Contrast

A [policy](policy.md) returns a decision or a computed value; a
specification returns a boolean.

## See also

[Policy](policy.md), [Domain Service](domain-service.md).
