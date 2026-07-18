# GraphQL

GraphQL is a query language for APIs that lets a client specify exactly which
fields it wants in a response.

## Why

Instead of the server dictating fixed response shapes, the client asks for
precisely the data it needs in one request, avoiding both over-fetching
unwanted fields and under-fetching that forces multiple round-trips. The cost
is a more complex server and caching story than fixed endpoints.

## Contrast

[REST](rest.md) exposes fixed resource representations; GraphQL lets the
client shape the response.

## See also

[REST](rest.md), [HTTP](http.md).
