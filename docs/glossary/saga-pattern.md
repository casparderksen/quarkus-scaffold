# Saga Pattern

A saga is a pattern for coordinating a business process that spans several
local transactions, driving them forward through events or commands.

## Why

A single distributed transaction across multiple services or contexts is
costly and brittle. A saga replaces it with a sequence of local
transactions, each committing on its own, and handles failure by
compensating rather than by rolling everything back.

## Variants

An orchestration-based saga has a central coordinator that issues steps; a
choreography-based saga has each participant react to events and emit its
own, with no coordinator.

## See also

[Orchestration](orchestration.md), [Choreography](choreography.md),
[Process Manager](process-manager.md), [Compensating Transaction](compensating-transaction.md).
