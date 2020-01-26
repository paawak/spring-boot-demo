package com.swayam.demo.springbootdemo.kafka.camel.service;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class BackfillScheduler {

    private static final Logger LOG = LoggerFactory.getLogger(BackfillScheduler.class);

    private static final int MIN_INTERVAL_MINUTES = 3;

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
	List<MessageOutBox> outbox =
		jdbcTemplate.query(sql, params, (ResultSet resultSet, int rowNum) -> {
		    String correlationId = resultSet.getString("correlation_id");
		    String topicName = resultSet.getString("topic_name");
		    int partitionId = resultSet.getInt("partition_id");
		    long offset = resultSet.getLong("offset");
		    LOG.trace("topicName: {}, partitionId: {}, offset: {}, correlationId: {}",
			    topicName, partitionId, offset, correlationId);
		    return new MessageOutBox(correlationId, topicName, partitionId, offset);
		});

	if (outbox.isEmpty()) {
	    LOG.info("No backfill found");
	    return;
	} else {
	    LOG.info("{} records found for backfill", outbox.size());
	}

	outbox.forEach(messageOutBox -> kafkaMessageReplayer.replayMessages(messageOutBox.topicName,
		messageOutBox.partitionId, messageOutBox.offset, messageOutBox.correlationId));

    }

    private static class MessageOutBox {
	private final String correlationId;
	private final String topicName;
	private final int partitionId;
	private final long offset;

	MessageOutBox(String correlationId, String topicName, int partitionId, long offset) {
	    this.correlationId = correlationId;
	    this.topicName = topicName;
	    this.partitionId = partitionId;
	    this.offset = offset;
	}
    }

}
