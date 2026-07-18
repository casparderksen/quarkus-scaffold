# Persistence Context

A persistence context is the mapper's session-scoped cache of the objects it
is currently managing, tracking their identity and their changes for the
life of a unit of work.

## Why

By holding managed objects, the context can guarantee that the same stored
record maps to one object within the session and can detect what changed so
it knows what to write.

## Rule

The context is bound to the transaction. It opens for the unit of work and
closes when the handler returns, after which its objects are no longer
managed.

## See also

[Managed Entity](managed-entity.md), [Dirty Checking](dirty-checking.md),
[Transaction Boundary](transaction-boundary.md).
