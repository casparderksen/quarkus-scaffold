# Big Ball of Mud

A big ball of mud is a system with no discernible architecture, where
structure has eroded until everything is tangled with everything else.

## Why it is bad

Without clear boundaries, every change risks unforeseen effects elsewhere,
and no one can hold the system's shape in mind because it has none. It is
usually the end state of many small boundary violations left unchecked.

## Signs

Frameworks leaking into the domain; repositories degenerating into generic
query services; commands and queries mixed together; contexts importing each
other's internals; the shared module growing past primitives; mappers making
business decisions; and integration tests standing in for the unit and domain
tests that should exist.

## See also

[God Service](god-service.md), [Leaky Abstraction](leaky-abstraction.md),
[Boundary](boundary.md).
