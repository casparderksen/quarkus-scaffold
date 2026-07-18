# Single Responsibility Principle (SRP)

The single responsibility principle holds that a unit should have only one
reason to change — it should answer to a single actor or concern.

## Why

When a unit serves one concern, changes driven by unrelated concerns cannot
collide inside it. This enforces loose coupling and separation of concerns
and keeps each unit small and cohesive. A unit with several reasons to
change becomes a contention point where independent changes interfere.

## See also

[Cohesion](cohesion.md), [Encapsulation](encapsulation.md),
[Separation of Concerns](separation-of-concerns.md).
