# Domain Layer

The domain layer is the core of the system: it holds the business concepts,
rules, and logic, and it depends on nothing outside itself.

## Why

Because it is free of frameworks and infrastructure, the domain layer stays
stable across technology changes and can be exercised in tests without
booting any framework. It is the part of the system that expresses what the
business actually does.

## Contents

Aggregates, entities, and value objects; domain services and policies;
repository interfaces; domain events; and domain-specific exceptions.

## See also

[Application Layer](application-layer.md),
[Infrastructure Layer](infrastructure-layer.md),
[Domain-Driven Design](domain-driven-design.md).
