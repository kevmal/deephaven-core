/**
 * Copyright (c) 2016-2022 Deephaven Data Labs and Patent Pending
 */
package io.deephaven.kafka.ingest;

import io.deephaven.stream.StreamFailureConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

/**
 * Consumer for lists of ConsumerRecords coming from Kafka. The StreamFailureConsumer is extended so that we can report
 * errors emanating from our consumer thread.
 */
public interface KafkaStreamConsumer extends StreamFailureConsumer {
    /**
     * Consume a list of ConsumerRecords coming from Kafka.
     *
     * @param records the records to consume
     * @return the total number of message bytes processed, according whether any key and/or value fields were
     *         processed, and the corresponding values for
     *         {@code org.apache.kafka.clients.consumer.ConsumerRecord.serializedKeySize}
     *         {@code org.apache.kafka.clients.consumer.ConsumerRecord.serializedValueSize}
     */
    long consume(List<? extends ConsumerRecord<?, ?>> records);
}
