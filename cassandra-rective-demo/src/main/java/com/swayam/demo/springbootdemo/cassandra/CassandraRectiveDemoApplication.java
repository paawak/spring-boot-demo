package com.swayam.demo.springbootdemo.cassandra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@EnableReactiveCassandraRepositories
@SpringBootApplication
public class CassandraRectiveDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CassandraRectiveDemoApplication.class, args);
    }

}
