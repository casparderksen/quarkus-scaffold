# Transaction Script

A transaction script is a procedural application service that carries the
branching, validation, and orchestration that should have lived in the
domain.

## Why it is bad

By doing the domain's thinking in a procedure, it bypasses domain modeling
entirely. Invariants end up hidden inside long chains of handler logic, where
they are hard to find, easy to contradict, and impossible for the model to
guarantee.

## Contrast

It is the procedural counterpart of the [anemic domain model](anemic-domain-model.md):
one drains behavior from the objects, the other pools it in a script.

## See also

[Anemic Domain Model](anemic-domain-model.md), [Domain Service](domain-service.md).
