package com.swayam.demo.springbootdemo.dynamicrepo.cukes;

import org.springframework.test.context.ContextConfiguration;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@ContextConfiguration(classes = { SpringTestConfig.class })
public class CucumberSpringContextConfig {

}
