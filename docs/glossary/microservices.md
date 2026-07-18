# Microservices

Microservices are independently deployable, fine-grained services that
communicate over APIs and message brokers.

## Why

Splitting a system into separately deployable services lets teams own, scale,
and release their piece independently. The benefit comes with an operational
cost — distribution, networking, and data consistency across services — that
must be worth paying.

## Contrast

A [modular monolith](modular-monolith.md) separates modules by clear
boundaries without deploying them separately; a
[distributed monolith](distributed-monolith.md) pays the operational cost of
microservices while keeping the coupling of a monolith.

## See also

[Modular Monolith](modular-monolith.md), [Distributed Monolith](distributed-monolith.md).
