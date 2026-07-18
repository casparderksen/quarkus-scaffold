# Configuration Mapping

Configuration mapping is the binding of external configuration values into
typed objects the application can use directly.

## Why

Reading raw settings scattered through the code is error-prone and untyped.
Mapping configuration into a typed object gives one validated, well-named
place for a group of related settings, and fails early when a value is
missing or malformed.

## See also

[Infrastructure Layer](infrastructure-layer.md).
