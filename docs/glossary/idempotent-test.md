# Idempotent Test

An idempotent test can be run repeatedly without its earlier runs affecting
its later ones.

## Why

A test that leaves state behind may pass the first time and fail the next, or
poison other tests. An idempotent test cleans up after itself and does not
depend on residue from a prior run, so the suite behaves the same whether run
once or many times, in any order.

## See also

[Isolation](isolation.md), [Setup / Teardown](setup-teardown.md).
