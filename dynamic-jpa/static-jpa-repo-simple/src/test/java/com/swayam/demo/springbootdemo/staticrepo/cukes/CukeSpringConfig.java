package com.swayam.demo.springbootdemo.staticrepo.cukes;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import io.cucumber.spring.CucumberContextConfiguration;

@ComponentScan("com.swayam.demo.springbootdemo.staticrepo.cukes")
@CucumberContextConfiguration
@SpringBootTest
public class CukeSpringConfig {

}
