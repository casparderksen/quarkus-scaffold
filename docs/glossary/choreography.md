# Choreography

Choreography is a way of coordinating a saga in which each participant reacts
to events and emits its own, with no central coordinator directing the flow.

## Why

It maximizes participant autonomy: only facts cross boundaries, and a new
participant can join simply by subscribing to the events it cares about,
without changing anyone else.

## Cost

The process definition and its compensation logic end up spread across all
the participants. No single place shows the whole flow or its current state,
which makes the process harder to see and to reason about.

## Contrast

[Orchestration](orchestration.md) concentrates the flow in one coordinator;
choreography distributes it among peers.

## See also

[Saga Pattern](saga-pattern.md), [Orchestration](orchestration.md).
