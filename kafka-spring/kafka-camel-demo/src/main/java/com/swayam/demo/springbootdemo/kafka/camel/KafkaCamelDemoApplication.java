package com.swayam.demo.springbootdemo.kafka.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaCamelDemoApplication {

    public static void main(String[] args) {
	SpringApplication.run(KafkaCamelDemoApplication.class, args);
    }

}
