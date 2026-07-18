# Integration Test

An integration test verifies that a component collaborates correctly with
real infrastructure, such as a database, a broker, or the framework itself.

## Why

Some defects live only in the seams between the code and the technology it
uses — a query that is wrong, a mapping that does not round-trip. A unit test
cannot catch these because it substitutes those seams; an integration test
exercises them against the real thing.

## Tools

A running framework harness together with disposable, containerized
infrastructure stood up for the duration of the test.

## See also

[Unit Test](unit-test.md), [Testcontainers](testcontainers.md),
[Contract Test](contract-test.md).
