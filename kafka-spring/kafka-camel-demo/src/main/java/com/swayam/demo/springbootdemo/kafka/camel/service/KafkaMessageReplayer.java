package com.swayam.demo.springbootdemo.kafka.camel.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.kafka.camel.route.RouteConstants;

@Service
public class KafkaMessageReplayer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageReplayer.class);

    private final ProducerTemplate producerTemplate;

    public KafkaMessageReplayer(ProducerTemplate producerTemplate) {
	this.producerTemplate = producerTemplate;
    }

    public void replayMessages(String topicName, int partitionId, long offset,
	    String correlationId) {
	KafkaConsumer<String, String> kafkaConsumer = getKafkaConsumer();
	TopicPartition topicPartition = new TopicPartition(topicName, partitionId);
	kafkaConsumer.assign(Arrays.asList(topicPartition));
	kafkaConsumer.seek(topicPartition, offset);
	while (true) {
	    ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(500));
	    for (ConsumerRecord<String, String> record : records) {
		String key = record.key();
		if (!key.equals(correlationId)) {
		    return;
		}
		String value = record.value();
		LOG.info("correlationId: {}, value: {}", correlationId, value);
		Map<String, Object> headers = new HashMap<>();
		headers.put(KafkaConstants.KEY, correlationId);
		headers.putAll(StreamSupport.stream(record.headers().spliterator(), false)
			.collect(Collectors.toMap(header -> header.key(),
				header -> new String(header.value()))));
		producerTemplate.sendBodyAndHeaders(RouteConstants.AGGREGATION_CHANNEL, value,
			headers);
	    }
	}
    }

    private KafkaConsumer<String, String> getKafkaConsumer() {
	Properties props = new Properties();
	props.put("bootstrap.servers", "localhost:9092");
	props.put("group.id", "bank-detail-backfill");
	props.put("key.deserializer", StringDeserializer.class);
	props.put("value.deserializer", StringDeserializer.class);
	return new KafkaConsumer<>(props);
    }

}
