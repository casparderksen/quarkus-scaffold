# OpenID Connect

OpenID Connect is an identity layer built on top of OAuth 2.0, adding a
standard way to verify who a user is.

## Why

OAuth 2.0 on its own handles delegated authorization — granting access — but
not authentication. OpenID Connect fills that gap with a standard identity
token and user-info flow, so applications get a consistent way to sign users
in rather than each inventing its own.

## Contrast

[OAuth 2.0](oauth-2.md) delegates access; OpenID Connect establishes
identity on top of it.

## See also

[OAuth 2.0](oauth-2.md), [Authentication](authentication.md), [JWT](jwt.md).
