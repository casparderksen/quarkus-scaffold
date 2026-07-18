# Temporal Coupling

Temporal coupling is when components must be invoked in a particular order to
work correctly, and that ordering is implicit rather than enforced.

## Why it is bad

A caller who does not know the required sequence — initialize before use,
configure before start — can produce subtly wrong behavior with no
compile-time warning. The dependency exists in time, not in the type system,
so it is easy to violate and hard to see.

## See also

[Coupling](coupling.md), [Encapsulation](encapsulation.md).
