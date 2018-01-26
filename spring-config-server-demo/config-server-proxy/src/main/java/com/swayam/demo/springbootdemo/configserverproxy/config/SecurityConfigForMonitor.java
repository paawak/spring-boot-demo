package com.swayam.demo.springbootdemo.configserverproxy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfigForMonitor {

	@Bean
	public WebSecurityConfigurer<WebSecurity> customSecurityForMonitor() {
		return new WebSecurityConfigurerAdapter() {
			@Override
			public void configure(WebSecurity webSecurity) {
				webSecurity.ignoring().antMatchers("/monitor");
			}
		};
	}

}
