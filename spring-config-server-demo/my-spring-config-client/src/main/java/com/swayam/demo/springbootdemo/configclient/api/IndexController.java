package com.swayam.demo.springbootdemo.configclient.api;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	private final Environment environment;

	public IndexController(Environment environment) {
		this.environment = environment;
	}

	@RequestMapping("/")
	public String handleIndex() {
		String key = "app.code.secret";
		return key + ":" + environment.getProperty(key);
	}

}
