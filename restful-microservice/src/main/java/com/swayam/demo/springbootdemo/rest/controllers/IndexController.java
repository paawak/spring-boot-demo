package com.swayam.demo.springbootdemo.rest.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

	@RequestMapping("/")
	public String handleIndex() {
		return "This is a spring boot application";
	}

}
