# Inbound Adapter

An inbound adapter, also called a driving adapter, drives the application
from the outside by turning an external trigger into a call on an inbound
port.

## Examples

A web endpoint handling an incoming request, a consumer reacting to a
message, or a scheduled job firing on a timer — each translates its trigger
into an application capability invocation.

## Rule

An inbound adapter is an entry point only. It carries no business logic and
opens no transaction; it parses input, invokes a capability, and renders the
result.

## See also

[Driving Adapter](driving-adapter.md), [Adapter](adapter.md),
[Inbound Port](inbound-port.md).
