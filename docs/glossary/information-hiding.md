# Information Hiding

Information hiding is the practice of keeping a component's internal
implementation details private, exposing only a deliberate interface to the
outside world.

## Why

When callers cannot see internals, they cannot depend on them, so the
internals stay free to change. Hidden details keep interfaces small and
coupling low. The opposite — details bleeding through an interface — is a
leaky abstraction that quietly ties consumers to decisions they were never
meant to know about.

## See also

[Encapsulation](encapsulation.md), [Leaky Abstraction](leaky-abstraction.md),
[Coupling](coupling.md).
