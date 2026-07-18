# Test Data Builder

A test data builder is a fluent helper for constructing customized test
objects, starting from sensible defaults and overriding only what a test
cares about.

## Why

Tests usually care about one or two attributes of an object and want the rest
to be valid but unremarkable. A builder lets a test state just those
attributes, keeping the intent visible and the setup free of irrelevant
detail.

## Contrast

An [object mother](object-mother.md) returns fixed named scenarios; a builder
customizes freely per test.

## See also

[Object Mother](object-mother.md), [Factory (Test Factory)](test-factory.md).
