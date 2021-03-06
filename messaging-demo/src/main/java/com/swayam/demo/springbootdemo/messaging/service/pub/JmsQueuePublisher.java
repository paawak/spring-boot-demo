package com.swayam.demo.springbootdemo.messaging.service.pub;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.swayam.demo.springbootdemo.messaging.model.BankDetail;

public class JmsQueuePublisher implements QueuePublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmsQueuePublisher.class);

    private final ObjectMapper mapper = new ObjectMapper();
    private final String queueName;
    private final JmsTemplate jmsTemplate;

    public JmsQueuePublisher(String queueName, JmsTemplate jmsTemplate) {
	this.queueName = queueName;
	this.jmsTemplate = jmsTemplate;
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

	jmsTemplate.convertAndSend(queueName, jsonString);

    }

}
