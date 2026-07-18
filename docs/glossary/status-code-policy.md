# Status Code Policy

A status code policy is a consistent mapping from kinds of failure to HTTP
status codes, applied the same way across every bounded context.

## Why

When every context maps the same kind of failure to the same status, clients
learn one set of rules instead of a different dialect per endpoint. The
policy makes the error surface predictable.

## Mapping

A missing resource returns not found; a broken invariant returns conflict;
an authorization failure returns forbidden and an authentication failure
unauthorized; a malformed request returns bad request; and anything
unexpected returns a server error.

## See also

[Problem Detail Catalog](problem-detail-catalog.md), [Domain Exception](domain-exception.md).
