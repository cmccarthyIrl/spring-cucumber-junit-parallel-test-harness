package com.cmccarthy;

import com.cucumber.listener.Reporter;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.AfterClass;
import org.junit.runner.RunWith;

import java.io.File;
import java.util.Objects;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/cucumber-html-reports",
                "json:target/cucumber-json-report.json",
                "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"
        },
        glue = {"com/cmccarthy/step",
                "com/cmccarthy/utils"
        },
        features = {"classpath:feature/WikipediaTest.feature"}
)
public class WikipediaRunnerTest {
    @AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File(Objects.requireNonNull(WikipediaRunnerTest.class.getClassLoader().getResource("extent.xml")).getPath()));
        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
        Reporter.setSystemInfo("Machine", System.getProperty("os.name") + " - " + System.getProperty("os.arch"));
        Reporter.setSystemInfo("Java Version", System.getProperty("java.version"));
    }
}


