# Dependency Direction

Dependency direction is the rule governing which layers are allowed to
depend on which others at build time.

## Why

Controlling the direction of dependencies prevents the domain or the
application from depending on transport or persistence concerns. The
business core stays ignorant of the technologies that surround it, which is
what keeps it stable and testable.

## Rule

Dependencies run outside-in only: infrastructure may depend on the
application, and the application on the domain — never the reverse. The
domain depends on nothing outside itself.

## See also

[Clean Architecture](clean-architecture.md), [Outside-in Rule](outside-in-rule.md),
[Dependency Inversion](dependency-inversion.md).
