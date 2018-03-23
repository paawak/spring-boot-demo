package com.swayam.demo.springbootdemo.grpc.server.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.swayam.demo.springbootdemo.grpc.server.controller.BankDetailStreamer;

import io.grpc.Server;
import io.grpc.ServerBuilder;

@Configuration
public class GrpcServerConfig {

	private static final Logger logger = LoggerFactory.getLogger(GrpcServerConfig.class);

	@Bean(destroyMethod = "shutdown")
	public Server server(@Value("${grpc.server.port}") int port, BankDetailStreamer bankDetailStreamer)
			throws IOException {
		Server server = ServerBuilder.forPort(port).addService(bankDetailStreamer).build().start();
		logger.info("Grpc Server started, listening on " + port);
		return server;
	}

}
