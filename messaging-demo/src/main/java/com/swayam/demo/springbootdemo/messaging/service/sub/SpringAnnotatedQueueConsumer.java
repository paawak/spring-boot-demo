package com.swayam.demo.springbootdemo.messaging.service.sub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SpringAnnotatedQueueConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(SpringAnnotatedQueueConsumer.class);

	@RabbitListener(queues = "bank-details")
	public void processBankDetails(String data) {
		LOGGER.info("--------------------------- recieved message: {}", data);
	}

}
