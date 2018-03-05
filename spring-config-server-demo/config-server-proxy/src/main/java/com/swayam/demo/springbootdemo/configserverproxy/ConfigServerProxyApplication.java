package com.swayam.demo.springbootdemo.configserverproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class ConfigServerProxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConfigServerProxyApplication.class, args);
	}
}
