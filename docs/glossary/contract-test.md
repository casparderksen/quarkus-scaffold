# Contract Test

A contract test verifies that two systems which communicate remain compatible
at their shared interface.

## Why

Independent services evolve on their own schedules, and a change to a
provider's interface can silently break a consumer that is tested separately.
A contract test pins the shared expectation so that a breaking change fails a
test rather than production.

## Variants

In the provider-driven style, the provider publishes a specification, the
provider's tests verify that its implementation matches, and consumers verify
that their usage stays within it. In the consumer-driven style, consumers
define the contracts and the provider verifies against them.

## See also

[Bidirectional Contract Testing](bidirectional-contract-testing.md),
[OpenAPI Validation](openapi-validation.md), [Pact](pact.md).
