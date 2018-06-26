package com.swayam.demo.springbootdemo.messaging.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.swayam.demo.springbootdemo.messaging.service.pub.AmqpQueuePublisher;
import com.swayam.demo.springbootdemo.messaging.service.pub.QueuePublisher;
import com.swayam.demo.springbootdemo.messaging.service.sub.AmqpMessageConsumer;

@Configuration
@Profile("rabbitmq")
public class RabbitMQConfig {

    private final String queueName;

    public RabbitMQConfig(@Value(CommonMessageBrokerConfig.BANK_DATA_QUEUE_NAME) String queueName) {
	this.queueName = queueName;
    }

    @Bean
    public MessageListener messageListener() {
	return new AmqpMessageConsumer();
    }

    @Bean
    public QueuePublisher amqpQueuePublisher(AmqpTemplate amqpTemplate) {
	return new AmqpQueuePublisher(queueName, amqpTemplate);
    }

}
