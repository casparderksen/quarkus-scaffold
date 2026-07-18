# Bidirectional Contract Testing

Bidirectional contract testing is a mode in which consumer and provider each
verify independently against a published specification, and their results are
then matched by a broker.

## Why

It combines the authority of a schema-first specification with the
deployment-gating of contract testing: because both sides are checked against
the same published contract and reconciled centrally, a tool can answer
whether a given consumer and provider are safe to deploy together.

## Contrast

Classic consumer-driven contract testing has the provider verify live
consumer-authored contracts; the bidirectional mode has both verify against
the published specification instead.

## See also

[Contract Test](contract-test.md), [Pact](pact.md),
[OpenAPI Validation](openapi-validation.md).
