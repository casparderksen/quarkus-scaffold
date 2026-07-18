# Compensating Transaction

A compensating transaction is a local transaction that semantically undoes
the effect of a saga step that has already committed.

## Why

Once a local transaction has committed it cannot be rolled back. When a later
step fails, the saga recovers by applying an inverse action — releasing a
reservation, refunding a charge — rather than reverting the original.

## Note

A compensation must itself be idempotent, since it may be retried under
at-least-once delivery. A step that cannot be compensated is ordered last, so
nothing after it can fail and require undoing it.

## See also

[Saga Pattern](saga-pattern.md), [Eventual Consistency](eventual-consistency.md).
