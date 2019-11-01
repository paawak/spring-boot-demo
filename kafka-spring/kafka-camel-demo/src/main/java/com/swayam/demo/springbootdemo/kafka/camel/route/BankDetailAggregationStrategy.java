package com.swayam.demo.springbootdemo.kafka.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.swayam.demo.springbootdemo.kafkadto.JobCount;

public class BankDetailAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
	if (oldExchange == null) {
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

}
