# Write Path

The write path is the route that command handlers take to load and mutate
aggregates through their repositories.

## Why

Keeping all state changes on one path, through aggregate-shaped repository
operations, ensures every mutation passes through the aggregate root and its
invariants. The write path is where consistency is enforced.

## Rule

Repositories on the write path expose aggregate-shaped operations only; they
are not query services and do not return display projections.

## See also

[Repository](repository.md), [Command](command.md), [Read Path](read-path.md).
