package com.swayam.demo.springbootdemo.kafka.camel.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Service;

import com.swayam.demo.springbootdemo.kafka.camel.route.BankDetailAggregatorByJob;

@Service
public class KafkaMessageReplayer {

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
		System.out.println("********** " + value);
		producerTemplate.sendBodyAndHeader(BankDetailAggregatorByJob.AGGREGATION_CHANNEL,
			value, KafkaConstants.KEY, correlationId);
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
