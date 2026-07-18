# Cache Adapter

A cache adapter is an outbound adapter that integrates a caching system
behind a port, letting the core request cached reads or writes without
knowing the caching technology.

## Why

Whether the cache is remote or in-process, and how entries expire or evict,
stays inside the adapter. The core sees only a port for storing and
retrieving values.

## See also

[Outbound Adapter](outbound-adapter.md), [Read Model](read-model.md).
