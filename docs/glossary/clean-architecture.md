# Clean Architecture

Clean architecture organizes code in concentric layers around business
rules, with all dependencies pointing inward toward those rules.

## Rule

Inner layers must not depend on outer ones. Business rules at the center
know nothing of the use cases around them, which in turn know nothing of the
delivery and infrastructure at the edge.

## Why

Keeping dependencies pointed inward makes the innermost rules the most
stable and the most reusable part of the system, untouched when outer
concerns like protocols or databases change.

## See also

[Outside-in Rule](outside-in-rule.md), [Use Case](use-case.md),
[Dependency Direction](dependency-direction.md).
