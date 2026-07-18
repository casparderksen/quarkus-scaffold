# Domain Event

A domain event represents something meaningful that happened in the domain,
stated as a fact about the past.

## Why

Events capture business-significant state changes and decouple whatever
caused a change from whatever needs to react to it. The emitter announces
what happened; reactors decide what to do, without the emitter knowing they
exist.

## Example

An "order placed" event carrying the order's identity, its customer, and its
total.

## Rule

Domain events are emitted from inside aggregates, not from the coordinating
layer. They are internal and unversioned; publishing one to the outside
world goes through a messaging adapter that maps it to a versioned
integration event.

## See also

[Integration Event](integration-event.md),
[Transactional Outbox](transactional-outbox.md).
