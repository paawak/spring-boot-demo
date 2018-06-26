package com.swayam.demo.springbootdemo.messaging.config;

import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;

import com.swayam.demo.springbootdemo.messaging.service.pub.JmsQueuePublisher;
import com.swayam.demo.springbootdemo.messaging.service.pub.QueuePublisher;
import com.swayam.demo.springbootdemo.messaging.service.sub.JmsMessageConsumer;

@Configuration
@Profile("activemq")
public class ActiveMQConfig {

    private final String queueName;

    public ActiveMQConfig(@Value("${app.config.message.queue.bank-data}") String queueName) {
	this.queueName = queueName;
    }

    @Bean
    public MessageListener messageListener() {
	return new JmsMessageConsumer();
    }

    @Bean
    public QueuePublisher jmsQueuePublisher(JmsTemplate jmsTemplate) {
	return new JmsQueuePublisher(queueName, jmsTemplate);
    }

}
