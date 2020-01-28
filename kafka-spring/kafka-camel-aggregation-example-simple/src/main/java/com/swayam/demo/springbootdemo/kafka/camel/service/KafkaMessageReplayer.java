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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.kafka.camel.route.RouteConstants;

@Service
public class KafkaMessageReplayer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageReplayer.class);

    private final ProducerTemplate producerTemplate;
    private final String kafkaBrokers;

    public KafkaMessageReplayer(ProducerTemplate producerTemplate,
	    @Value("${spring.kafka.bootstrap-servers}") String kafkaBrokers) {
	this.producerTemplate = producerTemplate;
	this.kafkaBrokers = kafkaBrokers;
    }

    public void replayMessages(String topicName, int partitionId, long offset,
	    String correlationId) {
	LOG.info(
		"Kafka message replay in progress: topicName: {}, partitionId: {}, offset: {}, correlationId: {}",
		topicName, partitionId, offset, correlationId);
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
		LOG.trace("correlationId: {}, value: {}", correlationId, value);
		Map<String, Object> headers = new HashMap<>();
		headers.put(RouteConstants.BACKFILL_IN_PROGRESS, Boolean.TRUE);
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
	props.put("bootstrap.servers", kafkaBrokers);
	props.put("group.id", "bank-detail-backfill");
	props.put("key.deserializer", StringDeserializer.class);
	props.put("value.deserializer", StringDeserializer.class);
	return new KafkaConsumer<>(props);
    }

}
