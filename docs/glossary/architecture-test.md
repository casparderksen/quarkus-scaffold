# Architecture Test

An architecture test enforces structural and dependency rules automatically,
failing the build when code violates the intended shape.

## Why

Architectural rules that live only in documentation drift, because nothing
stops a well-meaning change from crossing a boundary. Encoding a rule as a
test makes it enforced rather than aspirational.

## Rule

Every architectural constraint in the documentation should have a
corresponding test. A rule without a test is aspirational and will erode over
time.

## See also

[Boundary](boundary.md), [Dependency Direction](dependency-direction.md).
