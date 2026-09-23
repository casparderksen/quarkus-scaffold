# Message Broker

A message broker is infrastructure that routes messages between services,
decoupling senders from receivers in space and in time.

## Why

By sitting between producers and consumers, a broker lets them run
independently: a sender does not need the receiver to be available at the
moment it sends, and many receivers can consume the same stream. It absorbs
bursts and provides delivery guarantees the participants would otherwise have
to build themselves.

## See also

[Channel](channel.md), [Event Bus](event-bus.md),
[Event-Driven Architecture](event-driven-architecture.md),
[At-least-once Delivery](at-least-once-delivery.md).
