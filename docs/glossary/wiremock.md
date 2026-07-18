# WireMock

WireMock is a tool for stubbing HTTP services, standing in for a real remote
API during tests.

## Why

Testing code that calls an external HTTP service against the real thing is
slow, flaky, and sometimes impossible to steer into specific responses. A
stubbed HTTP service lets a test define exactly what the remote returns —
including errors and timeouts — so the calling code's handling of each case
can be verified deterministically.

## See also

[Sandbox Environment](sandbox-environment.md), [Stub](stub.md),
[Integration Test](integration-test.md).
