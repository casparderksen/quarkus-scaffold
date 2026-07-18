# Idempotent Consumer

An idempotent consumer is one that can process the same message more than
once without changing the outcome beyond the first time.

## Why

Under at-least-once delivery, duplicates are not an anomaly but a normal
condition. A consumer that is not idempotent will double-count, double-charge,
or double-create when a message is redelivered, so idempotency is essential
rather than optional.

## See also

[At-least-once Delivery](at-least-once-delivery.md), [Idempotency](idempotency.md).
