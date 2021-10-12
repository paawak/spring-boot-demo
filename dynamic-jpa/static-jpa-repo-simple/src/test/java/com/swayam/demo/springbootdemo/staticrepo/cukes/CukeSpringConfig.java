package com.swayam.demo.springbootdemo.staticrepo.cukes;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;

import io.cucumber.spring.CucumberContextConfiguration;

@ComponentScan("com.swayam.demo.springbootdemo.staticrepo.cukes")
@CucumberContextConfiguration
@ContextConfiguration(classes = { CukeSpringConfig.class })
public class CukeSpringConfig {

}
