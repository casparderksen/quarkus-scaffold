# Embedded Database

An embedded database is a lightweight or in-memory database run inside the
test process for the duration of a test.

## Why

It is fast to start and needs no external setup, which makes it convenient for
tests. The trade-off is fidelity: an embedded engine may not behave exactly
like the production database, so tests that pass against it can still hide
differences a containerized real database would expose.

## Contrast

[Testcontainers](testcontainers.md) runs the real engine in a container for
higher fidelity; an embedded database trades some fidelity for speed and
simplicity.

## See also

[Testcontainers](testcontainers.md), [Integration Test](integration-test.md).
