package com.swayam.demo.springboot.aws.reactive.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CorsGlobalConfiguration implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
	corsRegistry.addMapping("/**").allowedOrigins("http://localhost:8100").allowedMethods(HttpMethod.GET.name())
		.maxAge(3600);
    }

}
