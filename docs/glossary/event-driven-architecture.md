# Event-Driven Architecture

Event-driven architecture organizes a system around the publishing of, and
reaction to, events rather than around direct calls between components.

## Why

When components communicate by announcing facts instead of invoking each
other, they stay loosely coupled: a producer need not know who reacts, and
new reactors can be added without touching it. This naturally supports
asynchronous workflows that do not block on one another.

## See also

[Domain Event](domain-event.md), [Event Bus](event-bus.md),
[Message Broker](message-broker.md).
