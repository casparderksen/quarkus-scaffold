# Claim-Check Pattern

The claim-check pattern stores a large payload externally and puts only a
reference to it inside the message.

## Why

Brokers impose limits on message size, and moving big payloads through them is
wasteful. Storing the payload elsewhere and passing a reference — the claim
check — keeps messages small while still letting the consumer retrieve the
full content when it needs it.

## See also

[Message Broker](message-broker.md), [Integration Pattern](integration-pattern.md).
