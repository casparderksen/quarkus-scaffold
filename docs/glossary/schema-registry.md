# Schema Registry

A schema registry is a central store of event schemas that enforces a
compatibility rule whenever a schema changes.

## Why

By checking each new schema against the configured compatibility mode, the
registry rejects a change that would break existing consumers before it ever
reaches production. Compatibility becomes a guarantee rather than a hope.

## See also

[Event Versioning](event-versioning.md), [Backward Compatibility](backward-compatibility.md).
