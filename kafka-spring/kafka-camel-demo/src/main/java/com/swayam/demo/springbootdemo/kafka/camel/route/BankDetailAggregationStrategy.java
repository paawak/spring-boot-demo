package com.swayam.demo.springbootdemo.kafka.camel.route;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.component.kafka.KafkaConstants;
import org.apache.camel.processor.aggregate.AggregationStrategy;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

import com.swayam.demo.springbootdemo.kafkadto.JobCount;

public class BankDetailAggregationStrategy implements AggregationStrategy {

    private final NamedParameterJdbcOperations jdbcTemplate;

    public BankDetailAggregationStrategy(NamedParameterJdbcOperations jdbcTemplate) {
	this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
	if (oldExchange == null) {
	    // insert only the first message in the group
	    persistMessageOffsetDetails(newExchange);
	    return newExchange;
	}

	JobCount partialResults = oldExchange.getIn().getBody(JobCount.class);
	JobCount newMessage = newExchange.getIn().getBody(JobCount.class);

	oldExchange.getIn().setBody(doAggregation(newMessage, partialResults), JobCount.class);
	return oldExchange;
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
	String sql =
		"INSERT INTO message_outbox (correlation_id, topic_name, partition_id, offset) VALUES (:correlationId, :topicName, :partitionId, :offset)";
	Map<String, Object> params = new HashMap<>();
	params.put("correlationId", message.getIn().getHeader(KafkaConstants.KEY, String.class));
	params.put("topicName", message.getIn().getHeader(KafkaConstants.TOPIC, String.class));
	params.put("partitionId",
		message.getIn().getHeader(KafkaConstants.PARTITION, Integer.class));
	params.put("offset", message.getIn().getHeader(KafkaConstants.OFFSET, Long.class));

	jdbcTemplate.update(sql, params);

    }

}
