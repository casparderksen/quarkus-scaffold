# CloudEvents

CloudEvents is a specification for a standard event envelope — a common set
of metadata fields wrapping whatever payload an event carries.

## Why

Agreeing on envelope metadata lets tools and systems handle events
uniformly regardless of who produced them, describing an event's identity,
its source, its type, when it happened, and how to interpret its data.

## Rule

The envelope is an integration concern. The domain and application work with
their own events and never depend on the envelope format; the messaging
adapter wraps and unwraps it at the edge.

## See also

[Integration Event](integration-event.md), [Event Versioning](event-versioning.md).
