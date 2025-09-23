package com.swayam.demo.springbootdemo.testcontainer.cassandra;

import org.springframework.boot.SpringApplication;

public class TestTestcontainerCassandraDemoApplication {

	public static void main(String[] args) {
		SpringApplication.from(TestcontainerCassandraDemoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
