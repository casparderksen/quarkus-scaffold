# Service Discovery

Service discovery is the mechanism by which services locate one another at
runtime instead of relying on fixed, hard-coded addresses.

## Why

In a dynamic environment instances come and go and their addresses change. A
discovery mechanism lets a caller ask for a service by name and be pointed at
a currently healthy instance, so the system keeps working as the underlying
topology shifts.

## See also

[Load Balancing](load-balancing.md), [API Gateway](api-gateway.md).
