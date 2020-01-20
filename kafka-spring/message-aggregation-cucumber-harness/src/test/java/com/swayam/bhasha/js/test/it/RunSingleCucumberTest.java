package com.swayam.bhasha.js.test.it;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = { "src/test/resources/features/bangla/unsupported/complex-words.feature" },
	plugin = { "pretty", "html:target/cucumber-html-report", "junit:target/cucumber-junit-report/allcukes.xml" }, glue = { "com.swayam.bhasha.js.test.glue" })
public class RunSingleCucumberTest {

}
