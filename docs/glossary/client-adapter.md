# Client Adapter

A client adapter is an outbound adapter that integrates an external service
reached over the network, behind a port the core defines.

## Why

The protocol, endpoints, serialization, and failure handling of the remote
call all live in the adapter, so the core depends only on the capability it
asked for, not on how that capability is fetched.

## See also

[Outbound Adapter](outbound-adapter.md), [Anti-Corruption Layer](anti-corruption-layer.md).
