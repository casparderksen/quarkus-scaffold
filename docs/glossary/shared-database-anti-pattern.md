# Shared Database Anti-Pattern

The shared database anti-pattern is several services reading and writing one
common schema.

## Why it is bad

A shared schema couples the services through their data: none can change its
tables without risking the others, and the database becomes a hidden
integration point that no service owns. What looks like independent services
are in fact bound together by the store beneath them, incurring a constant
coordination tax.

## See also

[Distributed Monolith](distributed-monolith.md), [Bounded Context](bounded-context.md),
[Coupling](coupling.md).
