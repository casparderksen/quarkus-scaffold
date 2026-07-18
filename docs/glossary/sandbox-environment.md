# Sandbox Environment

A sandbox environment is a controlled external environment used for
integration testing against a system that cannot be run locally.

## Why

Some dependencies — a third-party payment provider, for instance — cannot be
containerized or embedded. A vendor-provided sandbox offers a safe, isolated
instance to test against, without touching production or moving real money.

## See also

[Integration Test](integration-test.md), [Testcontainers](testcontainers.md),
[WireMock](wiremock.md).
