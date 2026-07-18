# Dirty Checking

Dirty checking is the mapper's ability to detect field changes on managed
objects and write them out automatically when the unit of work flushes.

## Why

It removes the need for an explicit update call after modifying an object:
change the object, and the mapper works out what to persist. This keeps
mutation code focused on the domain change rather than on storage
bookkeeping.

## Requires

An active persistence context for the whole duration of the mutation; a
change made after the context has closed is not tracked.

## See also

[Persistence Context](persistence-context.md), [Managed Entity](managed-entity.md).
