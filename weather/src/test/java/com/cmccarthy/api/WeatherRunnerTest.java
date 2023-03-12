package com.cmccarthy.api;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.core.options.Constants.GLUE_PROPERTY_NAME;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com/cmccarthy/api")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.cmccarthy.api")
public class WeatherRunnerTest {
}
