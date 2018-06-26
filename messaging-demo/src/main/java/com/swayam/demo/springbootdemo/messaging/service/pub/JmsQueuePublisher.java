package com.swayam.demo.springbootdemo.messaging.service.pub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.springbootdemo.messaging.model.BankDetail;

@Service
public class JmsQueuePublisher implements QueuePublisher {

	private static final Logger LOGGER = LoggerFactory.getLogger(JmsQueuePublisher.class);

	private final ObjectMapper mapper = new ObjectMapper();
	private final AmqpTemplate amqpTemplate;

	@Autowired
	public JmsQueuePublisher(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

	@Override
	public void publish(BankDetail bankDetail) {
		String jsonString;
		try {
			jsonString = mapper.writeValueAsString(bankDetail);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

		LOGGER.info("trying to publish message `{}` to queue...", jsonString);

		amqpTemplate.convertAndSend("bank-details", jsonString);

	}

}
