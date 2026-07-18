# Process Manager

A process manager owns the state of one running saga and advances it,
reacting to participants' outcome events and issuing the next step or a
compensation.

## Why

It centralizes an orchestrated saga's state machine and its failure handling
in a single component, so the flow's logic lives in one place rather than
being inferred from scattered reactions.

## Rule

A process manager owns process state only — never a participant's domain data
or aggregate. It lives in the context that owns the process, or in a
dedicated coordination context when no single participant owns it.

## See also

[Orchestration](orchestration.md), [Saga Pattern](saga-pattern.md),
[Compensating Transaction](compensating-transaction.md).
