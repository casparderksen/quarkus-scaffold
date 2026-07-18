# Smart Controller / Thin Domain

Smart controller, thin domain is the anti-pattern of putting business logic in
controllers or application services while the domain model stays passive.

## Why it is bad

Logic at the edge cannot be reused across other entry points and cannot be
tested without the delivery machinery around it, and the domain — the place
that should guarantee the rules — guarantees nothing. It is how an anemic
domain model comes about in practice.

## See also

[Anemic Domain Model](anemic-domain-model.md), [Inbound Adapter](inbound-adapter.md).
