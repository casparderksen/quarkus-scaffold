# Leaky Abstraction

A leaky abstraction is one that exposes details of its underlying
implementation to the consumers it was meant to shield.

## Why it is bad

When implementation details show through, consumers start depending on them,
so the abstraction can no longer change freely — the very coupling it was
supposed to prevent creeps back in through the leak. The interface promises
independence it does not deliver.

## See also

[Information Hiding](information-hiding.md), [Coupling](coupling.md).
