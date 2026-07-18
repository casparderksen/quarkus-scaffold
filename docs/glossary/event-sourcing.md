# Event Sourcing

Event sourcing stores and replays domain events as the source of truth,
reconstructing current state by applying the recorded sequence of changes.

## Why

Keeping the full history of changes, rather than only the latest state,
preserves how the system arrived at where it is — valuable for audit,
debugging, and deriving new read models after the fact. The cost is added
complexity in reconstructing and evolving state.

## Contrast

Standard persistence stores the current state and overwrites it; event
sourcing stores the sequence of changes and derives state from them.

## See also

[Event Store](event-store.md), [Domain Event](domain-event.md).
