package com.swayam.demo.opentracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.opentracing.contrib.spring.tracer.configuration.TracerRegisterAutoConfiguration;
import io.opentracing.contrib.spring.web.starter.ServerTracingAutoConfiguration;

@SpringBootApplication(scanBasePackages = { "com.swayam.demo.opentracing" },
        exclude = { TracerRegisterAutoConfiguration.class, ServerTracingAutoConfiguration.class })
public class OpenTracingExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenTracingExampleApplication.class, args);
    }
}
