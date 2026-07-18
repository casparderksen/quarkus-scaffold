# Factory (Test Factory)

A test factory is a helper that constructs test objects with default values
filled in.

## Why

It removes the boilerplate of building a valid object in every test by
supplying defaults, so a test can obtain a usable instance in one call. It is
the simplest of the test-data helpers, without the fluent customization of a
builder or the named scenarios of an object mother.

## See also

[Test Data Builder](test-data-builder.md), [Object Mother](object-mother.md),
[Fixture](fixture.md).
