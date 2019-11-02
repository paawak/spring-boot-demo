package com.swayam.demo.springbootdemo.kafka.camel.service;

import java.sql.ResultSet;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BackfillScheduler {

    private final NamedParameterJdbcOperations jdbcTemplate;
    private final KafkaMessageReplayer kafkaMessageReplayer;

    public BackfillScheduler(NamedParameterJdbcOperations jdbcTemplate,
	    KafkaMessageReplayer kafkaMessageReplayer) {
	this.jdbcTemplate = jdbcTemplate;
	this.kafkaMessageReplayer = kafkaMessageReplayer;
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 10_000_000)
    public void checkForBackill() {
	String sql = "SELECT * FROM message_outbox";
	jdbcTemplate.query(sql, (ResultSet resultSet) -> {
	    String correlationId = resultSet.getString("correlation_id");
	    String topicName = resultSet.getString("topic_name");
	    int partitionId = resultSet.getInt("partition_id");
	    long offset = resultSet.getLong("offset");
	    kafkaMessageReplayer.replayMessages(topicName, partitionId, offset, correlationId);
	});
    }

}
