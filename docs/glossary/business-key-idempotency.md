# Business-key Idempotency

Business-key idempotency detects duplicates using a unique identifier that
already exists in the domain.

## How

Before creating something, the system looks it up by its business key; if it
already exists, the existing result is returned instead of creating a second
one. A uniqueness constraint in storage backs this up as a last line of
defense against a race.

## Example

An externally supplied order identifier, or a payment reference — values the
business already treats as unique.

## Default

When a natural deduplication key exists, this is preferred over a technical
key, because it relies on real domain meaning rather than an added mechanism.

## See also

[Technical Idempotency](technical-idempotency.md), [Idempotency](idempotency.md).
