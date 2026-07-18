# Mutation Testing

Mutation testing evaluates the quality of a test suite by deliberately
introducing small faults — mutants — into the code and checking whether the
tests catch them.

## Why

It answers a question coverage cannot: not whether tests ran the code, but
whether they would notice if the code were wrong. A mutant that survives —
a change no test detects — points to a weak or missing assertion, making
mutation testing a far stronger signal of test effectiveness than coverage.

## See also

[Code Coverage](code-coverage.md), [Assertion](assertion.md).
