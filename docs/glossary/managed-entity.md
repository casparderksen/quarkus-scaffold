# Managed Entity

A managed entity is an object currently attached to an active persistence
context, which the mapper watches for changes.

## Why

While an object is managed, its field changes are tracked and written out
automatically when the unit of work commits, so no explicit save call is
needed for a modification.

## Rule

A managed entity is never returned across the transaction boundary. Once the
context closes it is no longer managed, and handing it outward exposes an
object whose backing session has gone.

## See also

[Handler Return Contract](handler-return-contract.md),
[Detached Entity](detached-entity.md),
[Open-Session-In-View](open-session-in-view.md).
