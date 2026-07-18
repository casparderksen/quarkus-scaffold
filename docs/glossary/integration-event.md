# Integration Event

An integration event is an event published for consumption by other bounded
contexts or external systems, as a stable, versioned contract.

## Why

Crossing a boundary means committing to a contract others depend on. Unlike
an internal domain event, an integration event is deliberately shaped and
versioned so consumers can rely on it and evolve at their own pace.

## Rule

An integration event is produced by the outbound messaging adapter, which
translates an internal domain event into the published, versioned form. The
domain does not publish it directly.

## Contrast

A [domain event](domain-event.md) is internal and unversioned; an
integration event is an external, versioned contract.

## See also

[Domain Event](domain-event.md), [Event Versioning](event-versioning.md),
[Published Language](published-language.md).
