# Chatty Service Communication

Chatty service communication is a pattern of excessive synchronous calls
between services to accomplish a single piece of work.

## Why it is bad

Each network hop adds latency and a chance of failure, so a flow that fans out
into many fine-grained calls becomes slow and fragile, and its success depends
on every callee being available at once. It often signals boundaries drawn in
the wrong place, splitting work that belonged together.

## See also

[Distributed Monolith](distributed-monolith.md), [Eventual Consistency](eventual-consistency.md).
