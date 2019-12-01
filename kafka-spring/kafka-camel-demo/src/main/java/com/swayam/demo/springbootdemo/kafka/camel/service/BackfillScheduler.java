package com.swayam.demo.springbootdemo.kafka.camel.service;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BackfillScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(BackfillScheduler.class);

    private static final int MIN_INTERVAL_MINUTES = 10;

    private final NamedParameterJdbcOperations jdbcTemplate;
    private final KafkaMessageReplayer kafkaMessageReplayer;

    public BackfillScheduler(NamedParameterJdbcOperations jdbcTemplate,
	    KafkaMessageReplayer kafkaMessageReplayer) {
	this.jdbcTemplate = jdbcTemplate;
	this.kafkaMessageReplayer = kafkaMessageReplayer;
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 60000)
    public void checkForBackill() {
	LOG.info("checking for backfill...");
	String sql =
		"SELECT * FROM message_outbox WHERE TIMESTAMPDIFF(MINUTE, insert_time, NOW()) > :intervalInMinutes";
	Map<String, Object> params = new HashMap<>();
	params.put("intervalInMinutes", MIN_INTERVAL_MINUTES);
	jdbcTemplate.query(sql, params, (ResultSet resultSet) -> {
	    String correlationId = resultSet.getString("correlation_id");
	    String topicName = resultSet.getString("topic_name");
	    int partitionId = resultSet.getInt("partition_id");
	    long offset = resultSet.getLong("offset");
	    LOG.trace("topicName: {}, partitionId: {}, offset: {}, correlationId: {}", topicName,
		    partitionId, offset, correlationId);
	    kafkaMessageReplayer.replayMessages(topicName, partitionId, offset, correlationId);
	});
    }

}
