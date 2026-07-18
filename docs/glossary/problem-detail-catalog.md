# Problem Detail Catalog

A problem detail catalog is a per-context registry that maps each domain
exception to its HTTP status, its stable error-type identifier, and the
extension fields it is allowed to expose.

## Why

Centralizing the mapping puts the entire public error contract in one
reviewable place, rather than scattering status codes and error shapes
across the edge. Anyone can see, and vet, exactly how failures appear to
clients.

## Where

It lives at the delivery edge, alongside the other translation from internal
concepts to the wire.

## See also

[Domain Exception](domain-exception.md),
[RFC 9457 Problem Details](rfc-9457-problem-details.md),
[Status Code Policy](status-code-policy.md).
