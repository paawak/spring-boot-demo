package com.swayam.demo.opentracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.swayam.demo.opentracing" })
public class OpenTracingExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenTracingExampleApplication.class, args);
    }
}
