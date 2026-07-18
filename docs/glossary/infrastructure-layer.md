# Infrastructure Layer

The infrastructure layer holds the technical implementations that connect
the application core to frameworks and external systems.

## Why

Isolating technology here keeps it out of the domain and application. The
core defines what it needs; infrastructure supplies the concrete how, and
can be swapped without disturbing the layers it serves.

## Contents

Adapters for delivery and integration — web entry points, message
consumers and producers, storage, and scheduling — together with wiring and
configuration.

## Rule

Infrastructure depends on the application and the domain, never the reverse,
and it holds no business logic of its own.

## See also

[Adapter](adapter.md), [Outside-in Rule](outside-in-rule.md).
