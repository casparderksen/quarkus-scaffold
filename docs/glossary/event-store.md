# Event Store

An event store is a persistent repository of domain events, holding the
sequence of things that happened rather than a snapshot of current state.

## When

An event store is used with event sourcing, where the ordered stream of
events is the system of record and current state is derived by replaying it.

## See also

[Event Sourcing](event-sourcing.md), [Domain Event](domain-event.md).
