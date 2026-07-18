# Mapper

A mapper is a component that translates between models — a wire shape and a
command or projection, or an aggregate and a projection.

## Why

Concentrating translation in a mapper keeps adapters and handlers focused on
their real work instead of on field-copying, and gives every translation one
obvious home.

## Rule

A mapper performs pure transformation only. It makes no business decision and
does no conditional enrichment; anything that decides or computes belongs in
a policy or a factory, not in a mapper.

## See also

[Wire DTO](wire-dto.md), [Projection DTO](projection-dto.md), [Policy](policy.md).
