package com.swayam.demo.springbootdemo.kafka.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RouteExceptionHandler extends RouteBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(RouteExceptionHandler.class);

    @Override
    public void configure() {
	onException(Throwable.class).maximumRedeliveries(1).process(exchange -> {
	    LOG.error("Exception occured in message with body: {} and headers: {}",
		    exchange.getIn().getBody(), exchange.getIn().getHeaders());
	}).handled(true);
    }

}
