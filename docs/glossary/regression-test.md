# Regression Test

A regression test guards previously working behavior against being broken by
later changes.

## Why

As a system changes, a fix or a feature can silently undo something that used
to work. Capturing known-good behavior as a test — especially the behavior
around a bug once fixed — ensures that a reintroduced fault fails a test
rather than reaching users again.

## See also

[Unit Test](unit-test.md), [Snapshot Test](snapshot-test.md).
