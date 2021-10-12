package com.swayam.demo.springbootdemo.staticrepo;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import io.cucumber.core.options.Constants;

@Suite
@SelectClasspathResource("cukes")
@ConfigurationParameter(key = Constants.PLUGIN_PUBLISH_QUIET_PROPERTY_NAME, value = "true")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "@book")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "@author")
public class RunCucumberTest {

}
