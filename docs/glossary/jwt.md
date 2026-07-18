# JWT (JSON Web Token)

A JSON Web Token is a compact, self-contained token that carries
authentication and authorization claims and is signed so its contents can be
trusted.

## Why

Because the token itself holds the claims and a signature that proves they
were not tampered with, a service can verify a caller without a round-trip to
a central session store. This makes it well suited to stateless, distributed
systems.

## See also

[Authentication](authentication.md), [OAuth 2.0](oauth-2.md),
[OpenID Connect](openid-connect.md), [Digital Signature](digital-signature.md).
