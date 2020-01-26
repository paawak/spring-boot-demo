package com.swayam.demo.springbootdemo.kafka.test.it;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaMessageReader {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageReader.class);

    public void readMessage(String kafkaBrokers, String topicName, int timeToWaitSeconds) {
	Consumer<String, String> kafkaConsumer = getKafkaConsumer(kafkaBrokers);

	kafkaConsumer.subscribe(Collections.singletonList(topicName));

	ConsumerRecords<String, String> records =
		kafkaConsumer.poll(Duration.ofSeconds(timeToWaitSeconds));
	for (ConsumerRecord<String, String> record : records) {
	    String key = record.key();
	    String value = record.value();
	    LOG.info("Found message with key: {} and value: {}", key, value);
	}
    }

    private KafkaConsumer<String, String> getKafkaConsumer(String kafkaBrokers) {
	Properties props = new Properties();
	props.put("bootstrap.servers", kafkaBrokers);
	props.put("group.id", "cucumber-aggregation-test-group");
	props.put("key.deserializer", StringDeserializer.class);
	props.put("value.deserializer", StringDeserializer.class);
	return new KafkaConsumer<>(props);
    }

}
