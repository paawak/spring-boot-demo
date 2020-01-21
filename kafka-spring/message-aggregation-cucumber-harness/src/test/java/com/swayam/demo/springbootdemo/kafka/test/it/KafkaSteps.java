package com.swayam.demo.springbootdemo.kafka.test.it;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java8.En;

public class KafkaSteps implements En {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaSteps.class);

    public KafkaSteps() {

	Given("Bank details messages are published on Kafka {string}", (String kafkaBrokers) -> {
	    LOG.info("kafkaBrokers: {}", kafkaBrokers);
	});

	When("I wait for {int} mins", (Integer mins) -> {
	    LOG.info("waiting for {} mins...", mins);
	});

	Then("Aggregation results are published", () -> {
	    LOG.info("verifying results published...");
	});
    }

}
