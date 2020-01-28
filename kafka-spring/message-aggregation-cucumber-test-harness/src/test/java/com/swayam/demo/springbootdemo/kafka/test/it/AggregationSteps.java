package com.swayam.demo.springbootdemo.kafka.test.it;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java8.En;

public class AggregationSteps implements En {

    private static final Logger LOG = LoggerFactory.getLogger(AggregationSteps.class);

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    public AggregationSteps() {

	Given("The kafka-camel-aggregation-example-simple application is available at {string}",
		(String url) -> {
		    LOG.info("kafka-camel-aggregation-example-simple app is available at: {}", url);
		});

	When("I publish bank details messages on Kafka by invoking {string}", (String url) -> {
	    LOG.info("message publisher url: {}", url);
	    Runnable task = () -> {
		LOG.info("Trying to trigger message publishing...");
		Instant start = Instant.now();
		Request request = Request.Get(url);
		int code;
		try {
		    code = request.execute().returnResponse().getStatusLine().getStatusCode();
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}

		if (code == 200) {
		    LOG.info("Messages published successfully, total time take: {} seconds",
			    Duration.between(start, Instant.now()).toSeconds());
		} else {
		    throw new IllegalArgumentException(
			    "Could not trigger message publishing as server returned a http code "
				    + code);
		}
	    };

	    executorService.submit(task);
	});

	When("I wait for {int} second", (Integer second) -> {
	    LOG.info("waiting for {} second...", second);
	    Thread.sleep(second * 1000);
	});

	Then("Aggregation results are published on the topic {string} of Kafka broker {string} in {int} second",
		(String topic, String brokers, Integer timeToWaitInSeconds) -> {
		    LOG.info(
			    "verifying results published for brokers: {}, topic: {}, timeToWaitInSeconds: {}",
			    brokers, topic, timeToWaitInSeconds);
		    String expected =
			    "{\"adminCount\":478,\"blueCollarCount\":946,\"entrepreneurCount\":168,\"houseMaidCount\":112,\"managementCount\":969,\"retiredCount\":230,\"selfEmployedCount\":183,\"servicesCount\":417,\"studentCount\":84,\"technicianCount\":768,\"unemployedCount\":128,\"unknownCount\":38}";
		    CompletableFuture<String> completableFuture = new KafkaMessageReader()
			    .readSingleMessage(brokers, topic, timeToWaitInSeconds);
		    assertEquals(expected, completableFuture.get());
		});
    }

}
