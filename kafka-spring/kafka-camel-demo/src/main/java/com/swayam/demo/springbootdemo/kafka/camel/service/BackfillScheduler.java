package com.swayam.demo.springbootdemo.kafka.camel.service;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BackfillScheduler {

    private static final int MIN_INTERVAL_MINUTES = 10;

    private final NamedParameterJdbcOperations jdbcTemplate;
    private final KafkaMessageReplayer kafkaMessageReplayer;

    public BackfillScheduler(NamedParameterJdbcOperations jdbcTemplate,
	    KafkaMessageReplayer kafkaMessageReplayer) {
	this.jdbcTemplate = jdbcTemplate;
	this.kafkaMessageReplayer = kafkaMessageReplayer;
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 1_000)
    public void checkForBackill() {
	String sql =
		"SELECT * FROM message_outbox WHERE TIMESTAMPDIFF(MINUTE, insert_time, NOW()) > :intervalInMinutes";
	Map<String, Object> params = new HashMap<>();
	params.put("intervalInMinutes", MIN_INTERVAL_MINUTES);
	jdbcTemplate.query(sql, params, (ResultSet resultSet) -> {
	    String correlationId = resultSet.getString("correlation_id");
	    String topicName = resultSet.getString("topic_name");
	    int partitionId = resultSet.getInt("partition_id");
	    long offset = resultSet.getLong("offset");
	    kafkaMessageReplayer.replayMessages(topicName, partitionId, offset, correlationId);
	});
    }

}
