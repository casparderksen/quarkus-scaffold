# Domain Exception

A domain exception is an error that carries business context as typed fields
and is free of any transport concern.

## Why

Because it holds no HTTP status, no error-format identifier, and no
protocol coupling, the same exception is meaningful whether the operation
was reached over the web, over messaging, or from a test. The mapping to a
protocol-specific response happens later, at the edge.

## Example

An "order not found" exception carrying the identity that was looked up, and
nothing about how the caller arrived.

## Rule

A domain exception names a business failure and its data only; translating
it into a status code and an error payload is the edge's job.

## See also

[Problem Detail Catalog](problem-detail-catalog.md),
[Status Code Policy](status-code-policy.md).
