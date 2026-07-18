# Deterministic Test

A deterministic test produces the same result on every run, given the same
code.

## Why

Determinism is what makes a test trustworthy: a pass means pass and a failure
means a real problem, every time. It is achieved by controlling the sources of
variation — time, randomness, ordering, external state — often by abstracting
things like the clock so a test can fix them.

## Contrast

A [flaky test](flaky-test.md) is the opposite: nondeterministic and unreliable.

## See also

[Flaky Test](flaky-test.md), [Isolation](isolation.md).
