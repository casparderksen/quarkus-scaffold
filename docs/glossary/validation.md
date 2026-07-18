# Validation

Validation is the verification that incoming data satisfies structural or
business requirements before the system acts on it.

## Layers

Validation happens at distinct depths. Wire validation checks that a request
is well-formed and parseable at the edge. Structural validation checks that
a command carries its required fields and legal values. Business validation
— invariants, eligibility, legal state transitions — happens inside the
aggregate, policy, or domain service, where the rules actually live.

## Rule

Business validation is not hoisted into a separate validation stage; the
domain enforces its own rules, and only lightweight pre-flight checks sit in
the handler.

## See also

[Command](command.md), [Invariant](invariant.md), [Policy](policy.md).
