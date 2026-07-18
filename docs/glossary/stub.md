# Stub

A stub is a test double that returns predefined answers to the calls a test
makes.

## Why

When code under test needs data from a collaborator, a stub supplies canned
responses so the test can drive the code down a chosen path without a real
dependency behind it.

## Contrast

A stub makes no assertions about how it was called; it is purely a source of
inputs. A [mock](mock.md), by contrast, verifies the interactions it received.

## See also

[Mock](mock.md), [Fake](fake.md), [Test Double](test-double.md).
