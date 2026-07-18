# Dead Letter Queue (DLQ)

A dead letter queue is a separate queue that holds messages which could not
be processed successfully after the normal attempts were exhausted.

## Why

Setting failed messages aside keeps a single poison message from blocking the
consumer and stalling everything behind it. The failures are preserved for
inspection and reprocessing instead of being lost or endlessly retried.

## See also

[Retry Policy](retry-policy.md), [Idempotent Consumer](idempotent-consumer.md).
