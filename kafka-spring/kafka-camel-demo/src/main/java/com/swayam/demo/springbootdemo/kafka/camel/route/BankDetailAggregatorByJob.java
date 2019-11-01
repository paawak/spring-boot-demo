package com.swayam.demo.springbootdemo.kafka.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class BankDetailAggregatorByJob extends RouteBuilder {

    @Override
    public void configure() {
	from("kafka:bank-details?brokers=localhost:9092" + "&autoOffsetReset=earliest"
		+ "&autoCommitEnable=true" + "&groupId=bank-detail-camel-consumer")
			.routeId(BankDetailAggregatorByJob.class.getSimpleName())
			.log("${headers[__TypeId__]} : ${body}");
    }

}
