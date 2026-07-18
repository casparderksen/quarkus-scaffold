# Handler Return Contract

The handler return contract is the rule that handlers never return live,
storage-managed objects or aggregates across the transaction boundary.

## Why

A managed object handed to a caller outside the transaction exposes a
mutation surface and lazily loaded associations that are no longer safe to
touch — the connection that backed them has closed. The caller can then
trigger errors or unexpected reads far from where the data was loaded.

## Rule

Return a response or projection built while the transaction is still open,
an identity value, or nothing at all — anything fully detached from the
storage session.

## See also

[Handler](handler.md), [Open-Session-In-View](open-session-in-view.md),
[Managed Entity](managed-entity.md).
