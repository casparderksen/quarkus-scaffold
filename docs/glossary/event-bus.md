# Event Bus

An event bus is a publish/subscribe channel over which events are broadcast to
any interested subscribers.

## Why

Publishers put events onto the bus without knowing who consumes them, and
subscribers receive the events they care about without knowing who produced
them. This keeps the two sides decoupled and lets subscribers come and go
without disturbing publishers.

## See also

[Message Broker](message-broker.md), [Event-Driven Architecture](event-driven-architecture.md).
