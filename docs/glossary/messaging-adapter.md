# Messaging Adapter

A messaging adapter integrates asynchronous messaging, either producing
messages for others or consuming messages the system reacts to.

## Why

It isolates the broker, the envelope format, and the delivery semantics from
the core, which deals only in events and capabilities. A producing adapter
translates an internal event into a published contract; a consuming adapter
translates an incoming message into a capability invocation.

## See also

[Message Broker](message-broker.md), [Integration Event](integration-event.md),
[Outbound Adapter](outbound-adapter.md).
