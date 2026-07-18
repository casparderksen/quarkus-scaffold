# Unit Test

A unit test verifies a single unit of behavior in isolation from the rest of
the system.

## Rule

A unit test touches no external systems and boots no framework. Its
collaborators are real or substituted in memory, and it runs fast enough to
be run constantly.

## Use

It is the natural home for domain logic and for application orchestration
exercised with its ports substituted, where the behavior under test is pure
decision-making rather than integration.

## See also

[Integration Test](integration-test.md), [Test Double](test-double.md),
[Fake](fake.md).
