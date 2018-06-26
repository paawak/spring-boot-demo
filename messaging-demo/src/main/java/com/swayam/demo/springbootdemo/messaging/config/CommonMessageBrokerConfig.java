package com.swayam.demo.springbootdemo.messaging.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonMessageBrokerConfig {

    static final String BANK_DATA_QUEUE_NAME = "${app.config.message.queue.bank-data}";

    private final String queueName;

    public CommonMessageBrokerConfig(@Value(BANK_DATA_QUEUE_NAME) String queueName) {
	this.queueName = queueName;
    }

    @Bean
    public Queue queue() {
	return new Queue(queueName, false);
    }

}
