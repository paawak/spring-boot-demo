package com.swayam.demo.springbootdemo.kafka.test.glue;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UserSteps {

    @Given("Bank details messages are published on Kafka {string}")
    public void bankDetailsMessagesPublishedOnKafka(String kafkaBrokers) {

    }

    @When("I wait for {int} mins")
    public void iWaitFor(int waitTimeInMinutes) {

    }

    @Then("Aggregation results are published")
    public void aggregationResultsPublished() {

    }

}
