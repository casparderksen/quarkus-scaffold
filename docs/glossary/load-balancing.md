# Load Balancing

Load balancing distributes incoming requests across several instances of a
service so that no one instance carries the whole load.

## Why

Spreading traffic lets a system scale horizontally, tolerate the loss of an
instance, and keep response times steady under load. A balancer directs each
request to a healthy instance according to some strategy, such as round-robin
or least-busy.

## See also

[Service Discovery](service-discovery.md), [API Gateway](api-gateway.md).
