# Structured Logging

Structured logging records log entries as machine-readable data — typically
key/value fields — rather than as free-form lines of prose.

## Why

When each entry carries named fields, logs can be searched, filtered, and
aggregated by those fields instead of parsed with fragile text matching. A
question like "all failures for this correlation id" becomes a precise query
rather than a guess against unstructured text.

## See also

[Observability](observability.md), [Correlation ID](correlation-id.md).
