# Golden Master Test

A golden master test compares current output against a trusted historical
baseline captured from known-good behavior.

## Why

When a system's behavior is hard to specify but easy to capture — the output
of a legacy component, say — recording a baseline and comparing against it
detects any drift. It is especially useful when refactoring code whose exact
behavior must be preserved but is not otherwise pinned down.

## Contrast

A [snapshot test](snapshot-test.md) is the same idea applied more locally and
routinely; a golden master typically anchors larger, whole-output behavior.

## See also

[Snapshot Test](snapshot-test.md), [Regression Test](regression-test.md).
