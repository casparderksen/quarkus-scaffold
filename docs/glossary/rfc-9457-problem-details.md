# RFC 9457 Problem Details

RFC 9457 Problem Details is a standard format for describing errors in HTTP
responses as a structured, machine-readable payload.

## Why

A standard error shape lets clients handle failures programmatically instead
of parsing prose. It carries a stable type identifier, a human-readable
title and detail, a status, the affected instance, and room for extension
fields specific to the error.

## Replaces

It supersedes the earlier problem-details standard, RFC 7807, refining the
same idea.

## See also

[Domain Exception](domain-exception.md), [Problem Detail Catalog](problem-detail-catalog.md),
[Status Code Policy](status-code-policy.md).
