# Inbound Port

An inbound port is a contract that exposes an application capability to
external actors — the front through which the outside world asks the core
to do something.

## Why

By stating each capability as an explicit contract, the core defines exactly
what it can be asked to do, independent of who is asking or over what
protocol.

## Example

A "create order" capability, invoked by a web entry point but equally
callable by a scheduled job or a test, without any of them knowing the
others exist.

## Contrast

An [outbound port](outbound-port.md) describes a dependency the core needs;
an inbound port describes a capability it provides.

## See also

[Use Case](use-case.md), [Command](command.md), [Query](query.md).
