# Backward Compatibility

Backward compatibility means a new version of a producer can still be
understood by an old, unchanged consumer.

## Why

It lets a producer evolve without forcing every consumer to be updated and
redeployed first. Old consumers keep working against the new output, so
change can roll out gradually.

## Contrast

Forward compatibility is the reverse — an old producer understood by a new
consumer — and full compatibility holds in both directions at once.

## See also

[Event Versioning](event-versioning.md), [Schema Registry](schema-registry.md).
