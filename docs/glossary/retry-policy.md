# Retry Policy

A retry policy is the strategy governing how an operation is repeated after a
transient failure.

## Why

Many failures are momentary — a brief network blip, a short-lived overload —
and simply trying again succeeds. A policy makes retrying deliberate rather
than ad hoc, and prevents naive retries from amplifying the very problem they
are reacting to.

## Includes

A backoff that spaces attempts out, a limit on how many attempts to make, and
jitter to keep many clients from retrying in lockstep.

## See also

[Dead Letter Queue](dead-letter-queue.md), [Circuit Breaker](circuit-breaker.md).
