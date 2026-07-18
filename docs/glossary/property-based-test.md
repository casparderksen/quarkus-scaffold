# Property-Based Test

A property-based test verifies that an invariant holds across many
automatically generated inputs, rather than against a few chosen examples.

## Why

Instead of asserting specific outputs for specific inputs, it states a
property that should always be true — reversing a list twice yields the
original, an encoded then decoded value is unchanged — and lets a generator
attack it with many cases, often finding edge cases a person would never pick.

## See also

[Generator](generator.md), [Parameterized Test](parameterized-test.md).
