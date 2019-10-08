package com.swayam.demo.springbootdemo.kafka.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class BankDetailRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("kafka:bank-details?brokers=localhost:9092").log("${headers[__TypeId__]} : ${body}");
    }

}
