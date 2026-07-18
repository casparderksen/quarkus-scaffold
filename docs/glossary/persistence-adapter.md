# Persistence Adapter

A persistence adapter is an outbound adapter that implements durable storage
for aggregates behind a repository port.

## Why

It confines all knowledge of the storage engine and its query language to
one place, so the domain works only with repository operations expressed in
its own terms.

## See also

[Outbound Adapter](outbound-adapter.md), [Repository](repository.md).
