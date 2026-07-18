# Aggregate Root

The aggregate root is the single entity that controls all access to an
aggregate — the only door into it.

## Why

Routing every mutation through one entry point guarantees the aggregate's
invariants are checked on every change. Nothing can reach inside and leave
the aggregate in an invalid state, because there is no way in except through
the root.

## Example

An order is the root over its order lines; outside code changes lines only
by asking the order, never by touching a line directly.

## See also

[Aggregate](aggregate.md), [Invariant](invariant.md).
