package com.swayam.demo.springbootdemo.kafka.test.it;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class KafkaSteps {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaSteps.class);

    @Given("Bank details messages are published on Kafka {string}")
    public void bankDetailsMessagesPublishedOnKafka(String kafkaBrokers) {
	LOG.info("11111");
    }

    @When("I wait for {int} mins")
    public void iWaitFor(int waitTimeInMinutes) {
	LOG.info("22222");
    }

    @Then("Aggregation results are published")
    public void aggregationResultsPublished() {
	LOG.info("33333");
    }

}
