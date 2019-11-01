package com.swayam.demo.springbootdemo.kafka.camel.route;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class BankDetailAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
	if (oldExchange == null) {
	    return newExchange;
	}

	String oldBody = oldExchange.getIn().getBody(String.class);
	String newBody = newExchange.getIn().getBody(String.class);
	oldExchange.getIn().setBody(oldBody + "+" + newBody);
	return oldExchange;
    }

}
