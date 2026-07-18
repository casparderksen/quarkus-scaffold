# Snapshot Test

A snapshot test compares the current output of some code against a stored
reference captured earlier.

## Why

For output that is large or tedious to assert field by field, recording a
known-good snapshot and comparing against it catches any unexpected change
cheaply. The risk is that a snapshot blessed without scrutiny simply
enshrines whatever the code happened to produce, so changes must be reviewed,
not rubber-stamped.

## See also

[Golden Master Test](golden-master-test.md), [Regression Test](regression-test.md).
