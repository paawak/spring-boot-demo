package com.swayam.demo.springbootdemo.cucumber.cukes;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@ComponentScan
@Configuration
public class SpringTestConfig {

    @Bean
    public RestTemplate restTemplate() {
	return new RestTemplate();
    }

}
