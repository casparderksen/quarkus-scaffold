# Detached Entity

A detached entity is an object that was once managed but has since left the
persistence context, so the mapper no longer tracks it.

## Risk

Its lazily loaded associations throw when accessed, because there is no open
session to fetch them, and getting it managed again requires an explicit
re-attach. Passing detached objects around invites errors far from where
they were loaded.

## See also

[Managed Entity](managed-entity.md), [Persistence Context](persistence-context.md).
