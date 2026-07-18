# Shared Kernel

A shared kernel is a small, explicitly shared subset of a domain model that
two or more bounded contexts agree to hold in common.

## Why

It lets contexts reuse genuinely stable primitives — identifiers, money,
base types — without coupling their business behavior. The shared part is
deliberately tiny and deliberately shared, so both sides know exactly what
they have agreed on.

## Rule

Keep the kernel minimal, and treat any change to it as requiring agreement
from every context that depends on it, since the change affects them all.

## Contrast

An [anti-corruption layer](anti-corruption-layer.md) isolates one context
from another's model rather than sharing anything.

## See also

[Bounded Context](bounded-context.md), [Context Map](context-map.md).
