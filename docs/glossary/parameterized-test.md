# Parameterized Test

A parameterized test runs the same test logic repeatedly with different sets
of inputs.

## Why

Many cases differ only in their data — the same rule checked against several
examples. A parameterized test expresses the logic once and feeds it each
input set, avoiding a swarm of near-identical copies and making the set of
cases easy to extend.

## Contrast

A parameterized test uses inputs the author chose explicitly; a
[property-based test](property-based-test.md) has inputs generated to probe a
general property.

## See also

[Property-Based Test](property-based-test.md), [Test Case](test-case.md).
