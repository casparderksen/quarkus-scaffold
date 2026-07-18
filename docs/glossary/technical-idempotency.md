# Technical Idempotency

Technical idempotency detects duplicates using a client-supplied key or a
message identifier rather than a domain value.

## When

It is used where no natural business key exists — payment-style APIs, or
message consumers whose messages carry no domain-unique field.

## How

The supplied key or message identifier is recorded in a dedicated store
along with the outcome of first processing it. A later arrival with the same
key returns the stored outcome instead of doing the work again.

## See also

[Business-key Idempotency](business-key-idempotency.md),
[Idempotent Consumer](idempotent-consumer.md).
