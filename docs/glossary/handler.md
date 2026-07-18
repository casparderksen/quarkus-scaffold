# Handler

A handler is the application component that carries out exactly one use case.

## Why

Binding one command or query to one handler prevents the drift toward a
sprawling service class that accumulates many unrelated operations. Each
handler stays small and named after the single thing it does.

## Rules

A handler owns the transaction boundary for its operation. It does not
invoke other handlers, so use cases never chain into one another. It returns
a response shape, a projection, an identity value, or nothing — never a
live, storage-managed object that would expose internals beyond the
transaction.

## Contrast

A handler that starts calling other handlers has become an orchestration
layer, which the "one use case, one handler" rule exists to prevent.

## See also

[Use Case](use-case.md), [Transaction Boundary](transaction-boundary.md),
[Handler Return Contract](handler-return-contract.md).
