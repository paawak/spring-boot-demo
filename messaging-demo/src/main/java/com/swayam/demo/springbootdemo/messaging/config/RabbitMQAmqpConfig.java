package com.swayam.demo.springbootdemo.messaging.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.swayam.demo.springbootdemo.messaging.service.pub.AmqpQueuePublisher;
import com.swayam.demo.springbootdemo.messaging.service.pub.QueuePublisher;
import com.swayam.demo.springbootdemo.messaging.service.sub.AmqpMessageConsumer;

@Configuration
@Profile("rabbitmq-amqp")
public class RabbitMQAmqpConfig {

	@Autowired
	private Environment environment;

	@Bean
	public MessageListener messageListener() {
		return new AmqpMessageConsumer();
	}

	@Bean
	public QueuePublisher amqpQueuePublisher(AmqpTemplate amqpTemplate) {
		return new AmqpQueuePublisher(environment.getProperty(CommonJMSConfig.BANK_DATA_QUEUE_NAME),
				amqpTemplate);
	}

}
