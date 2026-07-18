# Flaky Test

A flaky test is one that passes or fails nondeterministically without any
change to the code under test.

## Why it is bad

A test that fails at random erodes trust in the whole suite: people start
ignoring failures, and a real regression hidden among the noise slips
through. Flakiness turns a safety net into background static.

## Causes

Dependence on timing, shared state bleeding between tests, and reliance on
external systems that are not fully controlled.

## See also

[Deterministic Test](deterministic-test.md), [Isolation](isolation.md).
