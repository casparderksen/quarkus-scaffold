# Separation of Concerns

Separation of concerns is the division of a system's responsibilities into
distinct components or layers, each addressing one concern and nothing else.

## Why

When each part owns a single concern, a change to one concern touches one
place. This lowers coupling, keeps individual components small enough to
reason about, and isolates the blast radius of change. A system without it
tends to smear one decision — a validation rule, a storage format — across
many unrelated places.

## See also

[Coupling](coupling.md), [Cohesion](cohesion.md),
[Single Responsibility Principle](single-responsibility-principle.md).
