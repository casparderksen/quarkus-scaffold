# ORM in the Domain Model

Placing object-relational mapping metadata directly on aggregates and
entities is a pragmatic exception to keeping the domain free of technical
concerns.

## Why

Letting the mapper manage domain objects directly preserves its ability to
detect changes automatically, to batch writes, and to avoid the extra reads
that come from reconstructing objects the mapper no longer tracks. A fully
separate persistence model would give a purer domain but at a real cost.

## Tradeoff

The domain is not perfectly framework-free, but for systems dominated by
straightforward create-read-update-delete work, keeping one model is
cheaper than maintaining a domain model and a parallel persistence model
that must be mapped back and forth.

## Boundary

The exception applies only to the write model. Read paths bypass mapped
objects entirely and return projections built directly by read queries.

## See also

[Domain Layer](domain-layer.md), [Persistence Context](persistence-context.md),
[Read Path](read-path.md).
