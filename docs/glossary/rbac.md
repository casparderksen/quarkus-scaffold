# RBAC (Role-Based Access Control)

Role-based access control grants permissions to roles and assigns roles to
subjects, so a subject's access derives from the roles it holds.

## Why

Managing permissions per individual does not scale. Grouping permissions into
roles that map to real job functions makes access easy to grant, audit, and
revoke — a person changes roles, and their permissions follow.

## Contrast

[ABAC](abac.md) decides from attributes of the subject, resource, and
context, allowing finer-grained policy than roles alone.

## See also

[Authorization](authorization.md), [ABAC](abac.md).
