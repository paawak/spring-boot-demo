package com.swayam.demo.springbootdemo.cucumber;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import io.cucumber.core.options.Constants;

@Suite
@SelectClasspathResource("/cukes") // location of the feature files
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "com.swayam.demo.springbootdemo.cucumber.cukes")
@ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "@book or @author")
public class RunCucumberTest {

}
