# ABAC (Attribute-Based Access Control)

Attribute-based access control decides access from attributes of the subject,
the resource, and the surrounding context, evaluated by policy.

## Why

Some access rules cannot be captured by roles alone — they depend on where a
request comes from, the time, the resource's owner, or the subject's
clearance. ABAC expresses these as policies over attributes, giving
finer-grained control at the cost of more complex rules.

## Contrast

[RBAC](rbac.md) grants access through roles only; ABAC weighs arbitrary
attributes.

## See also

[Authorization](authorization.md), [RBAC](rbac.md).
