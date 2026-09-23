# Channel

A channel is a named, virtual destination inside the application that messages
transit through. A messaging adapter declares the channel it reads from or
writes to; configuration maps that channel to a connector and a physical
destination on a broker.

## Why

The channel is the seam between the application and the transport. Because the
adapter names only the channel, it holds no broker type and no broker
vocabulary. Replacing the connector — a different broker, or an in-memory one
for tests — is then a configuration change that moves no code.

## Rule

A channel name is not a topic name. The two are configured independently and
need not match, so a consumer is named after the event it consumes, never after
a topic. Code that genuinely needs broker types — serializers, rebalance
listeners, offset handling — is not channel-bound and belongs in the one
package dedicated to the transport.

## Contrast

A channel is internal and logical; a [topic](message-broker.md) or queue is
external and physical. A [message broker](message-broker.md) routes between
systems; a channel routes between the application and whichever connector is
configured.

## See also

[Messaging Adapter](messaging-adapter.md), [Message Broker](message-broker.md),
[Event Bus](event-bus.md), [Integration Event](integration-event.md).
