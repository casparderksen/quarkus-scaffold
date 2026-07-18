# Test Double

A test double is any object that stands in for a real dependency during a
test.

## Why

Real dependencies can be slow, non-deterministic, or hard to steer into the
state a test needs. A double substitutes for one so the test can run fast and
control the conditions it exercises. Stubs, mocks, fakes, spies, and dummies
are all kinds of test double, differing in how much they do and what they
assert.

## See also

[Stub](stub.md), [Mock](mock.md), [Fake](fake.md), [Spy](spy.md), [Dummy](dummy.md).
