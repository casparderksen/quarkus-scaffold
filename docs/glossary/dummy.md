# Dummy

A dummy is a placeholder passed only to satisfy a required parameter, never
actually used during the test.

## Why

Sometimes a method signature demands an argument that a particular test does
not care about. A dummy fills that slot with something inert, making the
intent clear: this value is present only because it must be, and plays no part
in what the test checks.

## See also

[Test Double](test-double.md), [Stub](stub.md).
