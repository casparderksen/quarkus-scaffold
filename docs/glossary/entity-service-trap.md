# Entity Service Trap

The entity service trap is organizing services around individual data
entities and their create-read-update-delete operations rather than around
business capabilities.

## Why it is bad

Real business operations usually span several entities, so entity-shaped
services force a single capability to be assembled from many chatty calls,
and no service owns a meaningful behavior. The system ends up structured
around its tables instead of around what the business actually does.

## See also

[Chatty Service Communication](chatty-service-communication.md),
[Bounded Context](bounded-context.md).
