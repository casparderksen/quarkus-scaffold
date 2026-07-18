# Application Layer

The application layer orchestrates use cases: it coordinates the domain and
the system's external dependencies to carry out a single business operation.

## Why

It owns transactions and workflow, which keeps that coordination noise out
of the domain. The domain stays focused on rules; the application decides
when to apply them and in what order.

## Rule

The application layer holds no core business rules of its own. It is pure
coordination — loading, invoking, and saving domain objects, and driving
outbound dependencies through abstractions.

## Contents

Use-case contracts (commands and queries), their handlers, the outbound
port interfaces the use cases depend on, and read-side response shapes.

## See also

[Use Case](use-case.md), [Handler](handler.md),
[Transaction Boundary](transaction-boundary.md).
