package com.swayam.demo.springbootdemo.messaging.config;

import javax.jms.ConnectionFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.rabbitmq.jms.admin.RMQConnectionFactory;

@Configuration
@Profile("rabbitmq-jms")
public class RabbitMQJmsConfig {

	@Autowired
	private Environment environment;

	@Bean
	public ConnectionFactory connectionFactory() {
		RMQConnectionFactory connectionFactory = new RMQConnectionFactory();
		connectionFactory.setHost(environment.getProperty("app.config.rabbitmq.host"));
		connectionFactory.setPort(Integer.parseInt(environment.getProperty("app.config.rabbitmq.port")));
		connectionFactory.setUsername(environment.getProperty("app.config.rabbitmq.username"));
		connectionFactory.setPassword(environment.getProperty("app.config.rabbitmq.password"));
		return connectionFactory;
	}

}
