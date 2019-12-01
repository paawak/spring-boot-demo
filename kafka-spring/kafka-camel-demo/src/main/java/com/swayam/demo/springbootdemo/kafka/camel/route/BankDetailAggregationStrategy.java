package com.swayam.demo.springbootdemo.kafka.camel.route;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.apache.camel.processor.aggregate.CompletionAwareAggregationStrategy;
import org.apache.camel.processor.aggregate.TimeoutAwareAggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import com.swayam.demo.springbootdemo.kafkadto.JobCount;

public class BankDetailAggregationStrategy implements AggregationStrategy, Predicate,
	CompletionAwareAggregationStrategy, TimeoutAwareAggregationStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(BankDetailAggregationStrategy.class);

    private static final String PRECOMPLETE_DIRTY_AGGREGATION = "PRECOMPLETE_DIRTY_AGGREGATION";

    private final NamedParameterJdbcOperations jdbcTemplate;

    public BankDetailAggregationStrategy(NamedParameterJdbcOperations jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
	if (oldExchange == null) {
	    // insert only the first message in the group
	    if (checkforDirtyAggregation(newExchange)) {
		newExchange.getIn().setHeader(PRECOMPLETE_DIRTY_AGGREGATION, Boolean.TRUE);
		return newExchange;
	    }
	    persistMessageOffsetDetails(newExchange);
	    return newExchange;
	}

	// any header that is used by the predicate should be set in the message
	// returned from this method
	Boolean shouldComplete = newExchange.getIn()
		.getHeader(RouteConstants.COMPLETE_JOB_AGGREGATION_COMMAND, Boolean.class);
	LOG.trace("+++++shouldComplete: {}", shouldComplete);
	if (shouldComplete != null) {
	    oldExchange.getIn().setHeader(RouteConstants.COMPLETE_JOB_AGGREGATION_COMMAND,
		    shouldComplete);
	}

	JobCount partialResults = oldExchange.getIn().getBody(JobCount.class);
	JobCount newMessage = newExchange.getIn().getBody(JobCount.class);

	oldExchange.getIn().setBody(doAggregation(newMessage, partialResults), JobCount.class);
	return oldExchange;
    }

    @Override
    public boolean matches(Exchange oldExchange) {
	Boolean shouldComplete = oldExchange.getIn()
		.getHeader(RouteConstants.COMPLETE_JOB_AGGREGATION_COMMAND, Boolean.class);
	LOG.trace("******shouldComplete: {}", shouldComplete);
	return shouldComplete == null ? false : shouldComplete;
    }

    @Override
    public void onCompletion(Exchange exchange) {
	String correlationId = getCorrelationId(exchange);
	LOG.info("########### Aggregation is complete for {}", correlationId);
	String sql = "DELETE FROM message_outbox WHERE correlation_id = :correlationId";
	Map<String, Object> params = new HashMap<>();
	params.put("correlationId", correlationId);
	jdbcTemplate.update(sql, params);
	LOG.info("Record from *message_outbox* table deleted for {}", correlationId);
    }

    @Override
    public void timeout(Exchange oldExchange, int index, int total, long timeout) {
	LOG.info("############# timed out");
    }

    private JobCount doAggregation(JobCount newMessage, JobCount partialResults) {
	JobCount aggregate = new JobCount();

	aggregate.setAdminCount(newMessage.getAdminCount() + partialResults.getAdminCount());
	aggregate.setBlueCollarCount(
		newMessage.getBlueCollarCount() + partialResults.getBlueCollarCount());
	aggregate.setEntrepreneurCount(
		newMessage.getEntrepreneurCount() + partialResults.getEntrepreneurCount());
	aggregate.setHouseMaidCount(
		newMessage.getHouseMaidCount() + partialResults.getHouseMaidCount());
	aggregate.setManagementCount(
		newMessage.getManagementCount() + partialResults.getManagementCount());
	aggregate.setRetiredCount(newMessage.getRetiredCount() + partialResults.getRetiredCount());
	aggregate.setSelfEmployedCount(
		newMessage.getSelfEmployedCount() + partialResults.getSelfEmployedCount());
	aggregate.setServicesCount(
		newMessage.getServicesCount() + partialResults.getServicesCount());
	aggregate.setStudentCount(newMessage.getStudentCount() + partialResults.getStudentCount());
	aggregate.setTechnicianCount(
		newMessage.getTechnicianCount() + partialResults.getTechnicianCount());
	aggregate.setUnemployedCount(
		newMessage.getUnemployedCount() + partialResults.getUnemployedCount());
	aggregate.setUnknownCount(newMessage.getUnknownCount() + partialResults.getUnknownCount());

	return aggregate;
    }

    private void persistMessageOffsetDetails(Exchange message) {
	if (Boolean.TRUE.equals(
		message.getIn().getHeader(RouteConstants.BACKFILL_IN_PROGRESS, Boolean.class))) {
	    return;
	}
	String sql =
		"INSERT INTO message_outbox (correlation_id, processor_id, topic_name, partition_id, offset) VALUES (:correlationId, :processorId, :topicName, :partitionId, :offset)";
	Map<String, Object> params = new HashMap<>();
	params.put("correlationId", getCorrelationId(message));
	String topicName = message.getIn().getHeader(KafkaConstants.TOPIC, String.class);
	params.put("topicName", topicName);
	params.put("partitionId",
		message.getIn().getHeader(KafkaConstants.PARTITION, Integer.class));
	params.put("offset", message.getIn().getHeader(KafkaConstants.OFFSET, Long.class));
	params.put("processorId", System.getProperty("processorId"));

	jdbcTemplate.update(sql, params);

    }

    private String getCorrelationId(Exchange message) {
	return message.getIn().getHeader(KafkaConstants.KEY, String.class);
    }

    private boolean checkforDirtyAggregation(Exchange message) {
	String sql = "SELECT COUNT(*) FROM message_outbox WHERE correlation_id = :correlationId";
	Map<String, Object> params = new HashMap<>();
	params.put("correlationId", getCorrelationId(message));
	int count = jdbcTemplate.queryForObject(sql, params, Integer.class);
	boolean dirtyRecordFound = count > 0;
	LOG.info("Dirty records found: {}", dirtyRecordFound);
	return dirtyRecordFound;
    }

}
