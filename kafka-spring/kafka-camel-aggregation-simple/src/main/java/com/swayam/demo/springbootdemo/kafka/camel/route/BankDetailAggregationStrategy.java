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

import com.swayam.demo.springbootdemo.kafkadto.JobCount;

public class BankDetailAggregationStrategy implements AggregationStrategy, Predicate,
	CompletionAwareAggregationStrategy, TimeoutAwareAggregationStrategy {

    private static final Logger LOG = LoggerFactory.getLogger(BankDetailAggregationStrategy.class);

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
	if (oldExchange == null) {
	    LOG.info("First message recieved for the correlationID: {}",
		    getCorrelationId(newExchange));
	    return newExchange;
	}

	JobCount partialResults = oldExchange.getIn().getBody(JobCount.class);
	JobCount newMessage = newExchange.getIn().getBody(JobCount.class);

	oldExchange.getIn().setBody(doAggregation(newMessage, partialResults), JobCount.class);

	// any header that is used by the predicate should be set in the message
	// returned from this method
	Map<String, Object> headers = new HashMap<>();
	headers.putAll(oldExchange.getIn().getHeaders());
	headers.putAll(newExchange.getIn().getHeaders());
	oldExchange.getIn().setHeaders(headers);

	return oldExchange;
    }

    @Override
    public boolean matches(Exchange oldExchange) {
	Boolean completionCommand = oldExchange.getIn()
		.getHeader(RouteConstants.COMPLETE_JOB_AGGREGATION_COMMAND, Boolean.class);
	if (completionCommand == null) {
	    return false;
	}

	LOG.debug("******shouldComplete: {}", completionCommand);
	return completionCommand;
    }

    @Override
    public void onCompletion(Exchange exchange) {
	String correlationId = getCorrelationId(exchange);
	LOG.info("########### Aggregation is complete for {}", correlationId);
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

    private String getCorrelationId(Exchange message) {
	return message.getIn().getHeader(KafkaConstants.KEY, String.class);
    }

}
