# Mock

A mock is a test double that verifies the interactions the code under test
had with it — which calls were made, with what arguments.

## Why

Some behavior is defined not by a return value but by an effect on a
collaborator: that a message was published, that a record was saved. A mock
asserts those interactions happened as expected.

## Risk

Leaning on mocks too heavily couples tests to the implementation's exact call
sequence, so a behavior-preserving refactor breaks them.

## See also

[Stub](stub.md), [Over-Mocking](over-mocking.md), [Test Double](test-double.md).
