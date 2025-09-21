package com.swayam.demo.springbootdemo.testcontainer.cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

@SpringBootApplication
@EnableCassandraRepositories
public class TestcontainerCassandraDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestcontainerCassandraDemoApplication.class, args);
	}

}
