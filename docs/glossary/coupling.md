# Coupling

Coupling is the degree of interdependence between components — how much one
must know about, or change with, another.

## Why

Lower coupling improves maintainability, testability, and reuse, and it
limits the ripple effect of a change. Tightly coupled components must be
understood, changed, and deployed together; loosely coupled ones can evolve
on their own schedule.

## How

Coupling is reduced by hiding information behind stable interfaces, by
depending on abstractions rather than concrete implementations, and by
inverting dependencies so volatile details point inward toward stable
policy.

## See also

[Cohesion](cohesion.md), [Dependency Inversion](dependency-inversion.md),
[Information Hiding](information-hiding.md).
