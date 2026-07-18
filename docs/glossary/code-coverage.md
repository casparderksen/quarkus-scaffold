# Code Coverage

Code coverage is a metric of how much of the code was executed by the tests —
by line, by branch, or by path.

## Why

It reveals code that no test touches at all, which is useful for finding
blind spots. Its limitation is that executing code is not the same as
checking it: a high coverage number says the tests ran the code, not that
they asserted the right things about it.

## Caveat

High coverage does not guarantee quality; code can be fully covered by tests
that assert almost nothing.

## See also

[Mutation Testing](mutation-testing.md), [Assertion](assertion.md).
