# Object Mother

An object mother is a test helper that produces predefined, valid domain
objects for named scenarios.

## Why

Recurring test scenarios need the same well-formed objects again and again.
An object mother names each scenario and hands back a ready-made, valid
object for it, keeping tests readable and their setup consistent.

## Example

A helper returning a paid order or an expired subscription — each a named,
valid starting point.

## Contrast

A [test data builder](test-data-builder.md) offers flexible, field-by-field
customization; an object mother offers fixed, named scenarios.

## See also

[Test Data Builder](test-data-builder.md), [Fixture](fixture.md).
