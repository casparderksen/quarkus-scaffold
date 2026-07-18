# Concurrency Test

A concurrency test validates a system's behavior when work runs in parallel.

## Why

Bugs like race conditions, deadlocks, and lost updates appear only under
simultaneous access and stay invisible to sequential tests. A concurrency
test deliberately exercises parallel execution to surface them, verifying that
invariants still hold when many operations overlap.

## See also

[Performance Test](performance-test.md), [Consistency Boundary](consistency-boundary.md).
