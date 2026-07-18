# Seed Data

Seed data is the initial data inserted before a test runs, establishing the
baseline the test depends on.

## Why

Tests that read or modify existing records need those records to exist first.
Seeding puts a known baseline in place beforehand, so the test operates on
predictable data rather than on whatever happens to be present.

## See also

[Fixture](fixture.md), [Fixture File](fixture-file.md).
