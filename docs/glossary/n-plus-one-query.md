# N+1 Query

An N+1 query is the pattern where loading a list of items issues one query
for the list and then one more query per item.

## Why it is bad

The number of database round-trips grows linearly with the size of the
result, so a page that looks fine with ten items degrades badly with ten
thousand. The cost is invisible in the code and only appears at scale.

## Cause

Lazily loaded associations accessed one at a time — in a loop, or during
serialization of each item.

## Fix

Fetch the needed associations up front in a single query, or take the read
path and return a projection built by one query instead of hydrating each
aggregate.

## See also

[Open-Session-In-View](open-session-in-view.md), [Read Path](read-path.md).
