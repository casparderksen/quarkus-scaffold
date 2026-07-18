# At-least-once Delivery

At-least-once delivery is a messaging guarantee that a message will be
delivered, but possibly more than once.

## Why

Guaranteeing delivery without also guaranteeing exactly one copy is far
simpler and more robust for a broker to provide: when in doubt, it resends.
The cost of that simplicity is pushed to the consumer.

## Implication

Because duplicates are expected, every consumer must be idempotent, so that
processing the same message twice yields the same result as processing it
once.

## See also

[Idempotent Consumer](idempotent-consumer.md), [Transactional Outbox](transactional-outbox.md).
