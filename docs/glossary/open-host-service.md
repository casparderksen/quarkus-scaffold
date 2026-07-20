# Open-Host Service (OHS)

An Open-Host Service is a published inbound interface through which a bounded
context offers its capabilities to other contexts.

## Why

It gives consumers one stable front door instead of reaching into a
provider's internals. The mechanism behind that door — an in-process call
today, a network call after the context is extracted — can change without
affecting the consumers that depend on it.

## How

A consumer depends on the provider's published capability contract. While
both live in one deployable, the call is local; once the provider is split
out, a remote adapter fulfils the same contract, unchanged from the
consumer's point of view.

## Rule

An Open-Host Service is the only sanctioned way into another context. A
consumer never touches another context's internal model, its outbound
dependencies, its infrastructure, or its data.

## Contrast

A [shared kernel](shared-kernel.md) shares a model; an Open-Host Service
shares a service. An [anti-corruption layer](anti-corruption-layer.md)
protects the consumer from a foreign model.

## See also

[Published Language](published-language.md), [Context Map](context-map.md),
[Distributed Monolith](distributed-monolith.md),
[Modulith](modulith.md).
