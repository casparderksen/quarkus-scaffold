# Setup / Teardown

Setup and teardown are the initialization and cleanup steps that run around a
test, preparing its starting state and restoring things afterward.

## Why

Tests need a known state to begin and should leave nothing behind that could
affect others. Setup establishes that state; teardown releases resources and
undoes side effects, which is central to keeping tests isolated from one
another.

## See also

[Fixture](fixture.md), [Isolation](isolation.md).
