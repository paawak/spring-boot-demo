package com.swayam.demo.springbootdemo.kafka.test.it;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMessageReader {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageReader.class);

    public CompletableFuture<String> readSingleMessage(String kafkaBrokers, String topicName,
	    int timeToWaitSeconds) {
	Consumer<String, String> kafkaConsumer = getKafkaConsumer(kafkaBrokers);

	kafkaConsumer.subscribe(Collections.singletonList(topicName));

	ConsumerRecords<String, String> records =
		kafkaConsumer.poll(Duration.ofSeconds(timeToWaitSeconds));

	List<String> messages = StreamSupport.stream(records.spliterator(), false).map(record -> {
	    String key = record.key();
	    String value = record.value();
	    LOG.info("Found message with key: {} and value: {}", key, value);
	    return value;
	}).collect(Collectors.toList());

	if (messages.isEmpty()) {
	    return CompletableFuture.failedFuture(new RuntimeException("No messages found"));
	}

	if (messages.size() > 1) {
	    return CompletableFuture.failedFuture(
		    new RuntimeException(messages.size() + " messages found, instead of 1"));
	}

	return CompletableFuture.completedFuture(messages.get(0));

    }

    private KafkaConsumer<String, String> getKafkaConsumer(String kafkaBrokers) {
	Properties props = new Properties();
	props.put("bootstrap.servers", kafkaBrokers);
	props.put("group.id", "cucumber-aggregation-test-group-" + System.currentTimeMillis());
	props.put("key.deserializer", StringDeserializer.class);
	props.put("value.deserializer", StringDeserializer.class);
	return new KafkaConsumer<>(props);
    }

}
