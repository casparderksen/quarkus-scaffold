# Testcontainers

Testcontainers is an approach to integration testing that runs real
dependencies as disposable containers spun up for the test and torn down
afterward.

## Why

It lets tests exercise the genuine database, broker, or service rather than a
stand-in, giving high-fidelity integration coverage, while the throwaway
lifecycle keeps each run isolated and repeatable without a shared, drifting
test environment.

## See also

[Integration Test](integration-test.md), [Embedded Database](embedded-database.md),
[Sandbox Environment](sandbox-environment.md).
