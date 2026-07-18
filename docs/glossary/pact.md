# Pact

Pact is a contract-testing framework for verifying that services which
communicate stay compatible, and for gating deployments on that compatibility.

## Why

It lets a consumer and a provider be checked against a shared contract and
records the results centrally, so a tool can answer whether a given pair is
safe to deploy together. It can operate in a bidirectional mode where both
sides verify against a published specification.

## See also

[Contract Test](contract-test.md),
[Bidirectional Contract Testing](bidirectional-contract-testing.md),
[Consumer-Driven Contract](consumer-driven-contract.md).
