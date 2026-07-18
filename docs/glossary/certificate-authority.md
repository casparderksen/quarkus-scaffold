# Certificate Authority (CA)

A certificate authority is a trusted entity that issues digital certificates,
vouching for the binding between a public key and an identity.

## Why

The whole trust model of public key infrastructure rests on authorities that
relying parties already trust. When a CA signs a certificate, anyone who
trusts the CA can, by extension, trust the identity in that certificate
without having verified it themselves.

## See also

[Certificate](certificate.md), [PKI](pki.md).
