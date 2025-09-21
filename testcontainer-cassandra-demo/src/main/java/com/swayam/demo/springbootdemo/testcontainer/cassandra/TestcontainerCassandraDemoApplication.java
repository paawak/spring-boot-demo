package com.swayam.demo.springbootdemo.testcontainer.cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import com.swayam.demo.springbootdemo.testcontainer.cassandra.model.BankDetail;

@SpringBootApplication
@EnableCassandraRepositories
@EntityScan(basePackageClasses = BankDetail.class)
public class TestcontainerCassandraDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestcontainerCassandraDemoApplication.class, args);
	}

}
