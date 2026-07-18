# Isolation

Isolation is the property that tests do not affect one another — each runs as
if it were the only one.

## Why

When tests share mutable state, one can leave the world in a way that makes
another pass or fail for the wrong reason, and the order they run in starts to
matter. Isolation removes those hidden dependencies, so a failure points at
the test that failed and not at some earlier one.

## See also

[Setup / Teardown](setup-teardown.md), [Flaky Test](flaky-test.md),
[Idempotent Test](idempotent-test.md).
