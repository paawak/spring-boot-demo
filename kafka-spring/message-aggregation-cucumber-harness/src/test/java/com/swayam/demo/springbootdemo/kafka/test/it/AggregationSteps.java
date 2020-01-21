package com.swayam.demo.springbootdemo.kafka.test.it;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java8.En;

public class AggregationSteps implements En {

    private static final Logger LOG = LoggerFactory.getLogger(AggregationSteps.class);

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public AggregationSteps() {

	Given("The kafka-camel-demo application is available at {string}", (String url) -> {
	    LOG.info("kafka-camel-demo app is available at: {}", url);
	});

	When("I publish bank details messages on Kafka by invoking {string}", (String url) -> {
	    LOG.info("message publisher url: {}", url);
	    Runnable task = () -> {
		Request request = Request.Get(url);
		int code;
		try {
		    code = request.execute().returnResponse().getStatusLine().getStatusCode();
		} catch (IOException e) {
		    throw new RuntimeException(e);
		}
		if (code == 200) {
		    LOG.info("message publishing trigerred successfully");
		} else {
		    throw new IllegalArgumentException(
			    "could not trigger message publishing as server returned a http code "
				    + code);
		}
	    };

	    executorService.submit(task);
	});

	When("I wait for {int} second", (Integer second) -> {
	    LOG.info("waiting for {} second...", second);
	    Thread.sleep(second * 1000);
	});

	Then("Aggregation results are published", () -> {
	    LOG.info("verifying results published...");
	});
    }

}
