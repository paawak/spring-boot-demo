package com.swayam.demo.springbootdemo.kafka.test.it;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java8.En;

public class AggregationSteps implements En {

    private static final Logger LOG = LoggerFactory.getLogger(AggregationSteps.class);

    public AggregationSteps() {

	Given("The kafka-camel-demo application is available at {string}", (String url) -> {
	    LOG.info("kafka-camel-demo app is available at: {}", url);
	});

	When("I publish bank details messages on Kafka by invoking {string}", (String url) -> {
	    LOG.info("message publisher url: {}", url);
	});

	When("I wait for {int} second", (Integer second) -> {
	    LOG.info("waiting for {} second...", second);
	});

	Then("Aggregation results are published", () -> {
	    LOG.info("verifying results published...");
	});
    }

}
