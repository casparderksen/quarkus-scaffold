# Boundary

A boundary is a separation point between architectural responsibilities, the
line across which one part of the system is not allowed to reach into
another.

## How

Boundaries are made real through ports, through the way code is organized
into modules, and through automated architecture tests that fail when a
dependency crosses a line it should not. A boundary that is only described,
never enforced, tends to erode.

## See also

[Bounded Context](bounded-context.md),
[Architecture Test](architecture-test.md), [Port](port.md).
