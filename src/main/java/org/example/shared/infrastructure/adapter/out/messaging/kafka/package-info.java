/**
 * Kafka connector wiring: the only messaging code coupled to the broker.
 *
 * <p>A class belongs here if and only if it imports {@code org.apache.kafka.*} or
 * {@code io.smallrye.reactive.messaging.kafka.*}. Everything else in the messaging adapters binds
 * to a channel name and is transport-neutral, so it stays in
 * {@code infrastructure.adapter.in.messaging} or {@code infrastructure.adapter.out.messaging}.
 *
 * <p>Qualifying examples:
 *
 * <ul>
 *   <li>Serializers, deserializers, and {@code Serde} wiring
 *   <li>{@code KafkaConsumerRebalanceListener} implementations
 *   <li>{@code DeserializationFailureHandler} / {@code SerializationFailureHandler}
 *   <li>Checkpoint state stores and other code-level commit or offset handling
 *   <li>Producer wiring behind the outbox publisher that needs record keys, partitions, or headers
 * </ul>
 *
 * <p>Not here: anything expressible in {@code application.properties}. Connector selection, topic
 * names, commit strategy, and the {@code dead-letter-queue} failure strategy are configuration, not
 * code.
 *
 * <p>If the broker changes, this package and the {@code mp.messaging.*} configuration are what get
 * replaced; the channel-bound adapters are untouched. Swap the package name accordingly.
 *
 * <p>Naming convention: {@code Kafka<Concept>}. Example: {@code KafkaRebalanceListener}.
 */
package org.example.shared.infrastructure.adapter.out.messaging.kafka;
