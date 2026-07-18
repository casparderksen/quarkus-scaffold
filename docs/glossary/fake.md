# Fake

A fake is a lightweight but genuinely working implementation of a dependency,
used in place of the real one.

## Why

Unlike a stub with canned answers, a fake actually behaves — an in-memory
store that really saves and retrieves, for instance — so it can stand in
across many test scenarios without each one scripting responses. It gives
realistic behavior without the cost of the real infrastructure.

## Example

An in-memory repository that implements the same port as the real storage
adapter.

## See also

[Stub](stub.md), [Test Double](test-double.md), [Repository](repository.md).
