# Premature Optimization

Premature optimization is tuning for performance before there is evidence of a
real bottleneck.

## Why it is bad

Optimizing on guesswork adds complexity that makes code harder to read and
change, usually in places that were never the constraint, while the actual
bottleneck goes unaddressed. Correctness and clarity come first; optimization
follows measurement.

## See also

[Golden Hammer](golden-hammer.md), [Technical Debt](technical-debt.md).
