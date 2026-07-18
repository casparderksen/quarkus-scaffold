# Idempotency

Idempotency is the property that performing an operation repeatedly has the
same effect as performing it once.

## Why

In any system where a request or message can arrive more than once — through
retries, redelivery, or an impatient user — idempotency is what keeps
repetition safe. It turns "did this already run?" from a correctness worry
into a non-issue.

## Approaches

Duplicates can be caught by a natural business key when one exists, or by a
technical key supplied with the request when no business key does.

## See also

[Business-key Idempotency](business-key-idempotency.md),
[Technical Idempotency](technical-idempotency.md).
