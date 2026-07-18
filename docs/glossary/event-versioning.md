# Event Versioning

Event versioning is the policy for evolving event contracts that cross a
context or service boundary without breaking the consumers that depend on
them.

## Default

Changes are kept backward compatible: adding a new optional field is
non-breaking, while removing a field, renaming one, or changing its type
requires a new major version.

## Convention

The major version is carried in the event's type, so a breaking change
produces a distinctly typed event. Minor, compatible changes leave the type
alone, and a schema reference points at the current definition.

## Coexistence

During a transition a producer can publish both the old and the new
versions, letting consumers migrate independently rather than in lockstep.

## See also

[Schema Registry](schema-registry.md), [Backward Compatibility](backward-compatibility.md),
[Integration Event](integration-event.md).
