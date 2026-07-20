# Modulith

A modulith — also called a *modular monolith* — is a single deployable unit
composed of independently designed modules — bounded contexts — separated by
explicit boundaries.

## Why

It captures most of the benefits of microservices, such as clear boundaries
and ownership, without the operational cost of many separately deployed
services. Because the boundaries are real, a module can later be extracted
into its own service when there is a reason to pay that cost.

## Contrast

[Microservices](microservices.md) separate modules into independently
deployable services; a modulith keeps them in one deployment.

## See also

[Bounded Context](bounded-context.md), [Distributed Monolith](distributed-monolith.md).
