# Distributed Monolith

A distributed monolith is a set of services that are deployed separately yet
so tightly coupled at runtime that they cannot be released independently.

## Why it is bad

It combines the operational cost of microservices with the coupling of a
monolith — the worst of both. Because the services must change and deploy
together, one service's outage or change cascades into the others, so nothing
is truly independent.

## Cause

Synchronous cross-service calls placed on the request path where an
asynchronous local projection would serve.

## Fix

After extraction, prefer a local projection fed by the provider's integration
events over a synchronous call across the network for every read.

## See also

[Chatty Service Communication](chatty-service-communication.md),
[Open-Host Service](open-host-service.md), [Eventual Consistency](eventual-consistency.md).
