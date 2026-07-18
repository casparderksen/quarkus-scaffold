# Transactional Outbox

The transactional outbox is a reliability pattern that makes a database
change and the publication of its events atomic.

## Why

Writing to the database and publishing to a broker are two systems that can
fail independently. Doing them naively risks a committed change whose event
was never sent, or an event sent for a change that rolled back. The outbox
removes that gap.

## How

Events are written to an outbox table in the same transaction as the
aggregate change, so they commit together or not at all. A separate
publisher then reads the outbox and forwards the events to the broker,
retrying until each is sent.

## See also

[At-least-once Delivery](at-least-once-delivery.md),
[Idempotent Consumer](idempotent-consumer.md),
[Integration Event](integration-event.md).
