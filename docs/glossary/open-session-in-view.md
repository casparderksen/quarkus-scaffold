# Open-Session-In-View (OSIV)

Open-session-in-view is an anti-pattern that keeps the persistence session
open all the way through rendering the response, so lazy loads can happen
during serialization.

## Why it is avoided

It hides expensive query patterns by letting associations load silently
during rendering, runs reads outside the transaction's consistency, and
couples the response format to the persistence model. The convenience masks
real problems until they show up under load.

## Rule

The session closes when the handler returns. Turning an aggregate into a
response shape happens inside the handler, while the session is still open,
not during rendering.

## See also

[Handler Return Contract](handler-return-contract.md),
[N+1 Query](n-plus-one-query.md).
