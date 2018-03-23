package com.swayam.demo.springbootdemo.grpc.server;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcServerApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(GrpcServerApplication.class, args);
	}
}
