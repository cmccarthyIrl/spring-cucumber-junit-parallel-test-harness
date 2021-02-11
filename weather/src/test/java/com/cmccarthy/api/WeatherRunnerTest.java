package com.cmccarthy.api;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "json:target/cucumber-report.json"
        },
        glue = {"com/cmccarthy/api/", "com/cmccarthy/common/"
        },
        features = {"classpath:feature/WeatherTest.feature"}
)
public class WeatherRunnerTest {

}
