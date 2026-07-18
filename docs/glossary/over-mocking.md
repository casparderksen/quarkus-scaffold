# Over-Mocking

Over-mocking is relying so heavily on mocks that the tests end up coupled to
how the code is implemented rather than to what it does.

## Why it is bad

When nearly every collaborator is a mock, the test asserts a specific sequence
of internal calls, so any refactor that preserves behavior still breaks the
test. Such tests resist change and give little confidence that the real
pieces work together.

## See also

[Mock](mock.md), [Fake](fake.md), [Test Double](test-double.md).
