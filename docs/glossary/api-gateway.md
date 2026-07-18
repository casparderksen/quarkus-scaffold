# API Gateway

An API gateway is a single entry point that sits in front of a system's
services and mediates external traffic to them.

## Why

Concentrating the edge in one place gives a natural home for concerns that
would otherwise be repeated in every service — authenticating callers, rate
limiting, and routing requests to the right service — and gives external
clients one stable address rather than many.

## Responsibilities

Authentication, rate limiting, and routing, among other cross-cutting edge
concerns.

## See also

[Load Balancing](load-balancing.md), [Service Discovery](service-discovery.md).
