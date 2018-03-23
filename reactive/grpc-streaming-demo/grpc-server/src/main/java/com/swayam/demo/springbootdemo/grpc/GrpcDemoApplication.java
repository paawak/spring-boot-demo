package com.swayam.demo.springbootdemo.grpc;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GrpcDemoApplication {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(GrpcDemoApplication.class, args);
	}
}
