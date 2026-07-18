# CQRS (Command Query Responsibility Segregation)

CQRS is the separation of write operations from read operations, so that
changing state and querying state travel different paths.

## Why

Reads and writes have different shapes, different scaling profiles, and
different consistency needs. Splitting them lets each path be modeled and
optimized for what it actually does, instead of forcing one model to serve
both.

## See also

[CQRS-lite](cqrs-lite.md), [Command](command.md), [Query](query.md).
