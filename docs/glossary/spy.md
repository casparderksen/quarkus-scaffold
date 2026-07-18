# Spy

A spy wraps a real object and records how it was interacted with, so a test
can both let the real behavior run and assert on the calls that occurred.

## Why

A spy is useful when a test needs the genuine behavior but also wants to check
that certain calls happened. That said, reaching for a spy often signals that
the code is hard to isolate, or that only part of an object is being faked —
a smell worth noticing.

## See also

[Mock](mock.md), [Test Double](test-double.md), [Over-Mocking](over-mocking.md).
