package com.swayam.demo.spring.springbootdemo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@RequestMapping("/")
	public String handleIndex() {
		return "Hello " + System.getProperty("user.name");
	}

}
