package com.swayam.demo.springbootdemo.messaging;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MessagingDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagingDemoApplication.class, args);
	}

	@Bean
	Queue queue() {
		return new Queue("bank-details", false);
	}
}
