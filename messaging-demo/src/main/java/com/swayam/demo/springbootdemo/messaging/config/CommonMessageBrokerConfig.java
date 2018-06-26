package com.swayam.demo.springbootdemo.messaging.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonMessageBrokerConfig {

    private final String queueName;

    public CommonMessageBrokerConfig(@Value("${app.config.message.queue.bank-data}") String queueName) {
	this.queueName = queueName;
    }

    @Bean
    public Queue queue() {
	return new Queue(queueName, false);
    }

}
