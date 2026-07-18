# CQRS-lite

CQRS-lite is a pragmatic form of CQRS that separates the command and query
paths in code without introducing separate write and read databases or
eventually-consistent projections between them.

## Why

It captures the design benefit of distinct read and write models while
avoiding the operational cost of running and synchronizing two stores. One
database still serves both, but the code paths to it are kept apart.

## When

It fits read-heavy screens and reports where the natural read shape does not
match the aggregate shape, but where full CQRS with separate stores would be
more machinery than the problem warrants.

## See also

[CQRS](cqrs.md), [Projection](projection.md), [Read Path](read-path.md).
