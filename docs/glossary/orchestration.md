# Orchestration

Orchestration is a way of coordinating a saga in which a central process
manager owns the flow, issuing each step to participants and consuming their
outcome events.

## Why

It puts the whole process — its state, its ordering, and its compensations —
in one visible, testable place, which makes timeouts, retries, and
whole-process auditing straightforward.

## Cost

There is a coordination component to build and run, and the orchestrator
issues commands across a boundary — the one sanctioned case of a command
crossing between contexts.

## Contrast

[Choreography](choreography.md) spreads the flow across peers reacting to
events; orchestration centralizes it.

## See also

[Saga Pattern](saga-pattern.md), [Process Manager](process-manager.md),
[Choreography](choreography.md).
