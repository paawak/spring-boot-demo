package com.swayam.demo.springbootdemo.liquibase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.swayam.demo.springbootdemo.liquibase.service.BatchedDBExporter;

@SpringBootApplication
public class LiquibaseDemoApplication implements CommandLineRunner {

    @Autowired
    private BatchedDBExporter batchedDBExporter;

    public static void main(String[] args) {
	SpringApplication.run(LiquibaseDemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
	batchedDBExporter.export();
    }

}
