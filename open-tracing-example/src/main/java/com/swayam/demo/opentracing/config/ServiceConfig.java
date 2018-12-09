package com.swayam.demo.opentracing.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

	@Bean
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(20);
	}

}
