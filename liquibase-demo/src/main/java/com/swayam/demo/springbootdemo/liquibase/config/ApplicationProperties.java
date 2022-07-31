package com.swayam.demo.springbootdemo.liquibase.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app.config", ignoreUnknownFields = false)
@Data
public class ApplicationProperties {

    private int batchSize;

}
