package com.swayam.demo.springbootdemo.kafka.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class RouteExceptionHandler extends RouteBuilder {

    @Override
    public void configure() {
	onException(Throwable.class).maximumRedeliveries(1).log("Exception occured").handled(true);
    }

}
