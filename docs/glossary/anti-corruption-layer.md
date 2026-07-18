# Anti-Corruption Layer (ACL)

An anti-corruption layer is a translation layer that protects a bounded
context from another context's model, converting foreign concepts into local
ones at the boundary.

## Why

Without it, a neighbour's or a legacy system's concepts leak in and corrupt
the local ubiquitous language, so the model slowly starts to mirror
someone else's design. The ACL absorbs the mismatch and keeps the local
model clean.

## Example

A layer that translates a legacy system's notion of an account into the
local notion of a customer, so nothing downstream ever sees the foreign
shape.

## See also

[Bounded Context](bounded-context.md), [Shared Kernel](shared-kernel.md),
[Open-Host Service](open-host-service.md).
