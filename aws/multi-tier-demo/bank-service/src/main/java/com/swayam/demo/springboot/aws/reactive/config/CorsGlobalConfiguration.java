package com.swayam.demo.springboot.aws.reactive.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    private final Environment env;

    public CorsGlobalConfiguration(Environment env) {
	this.env = env;
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
	corsRegistry.addMapping("/**").allowedOrigins(env.getProperty("bank-service.cors-allow-host"))
		.allowedMethods(HttpMethod.GET.name()).maxAge(3600);
    }

}
