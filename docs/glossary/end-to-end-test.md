# End-to-End (E2E) Test

An end-to-end test exercises a complete workflow through all the system's
layers as a black box, from the outside in.

## Why

It verifies that the parts actually work together to deliver a user-visible
outcome, catching failures of integration and configuration that
layer-by-layer tests miss. That realism comes at a cost: end-to-end tests are
slower, more brittle, and harder to diagnose, so they are used sparingly for
the most important flows.

## See also

[Integration Test](integration-test.md), [Acceptance Test](acceptance-test.md),
[Smoke Test](smoke-test.md).
