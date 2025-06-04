package com.cmccarthy.ui.utils;

import com.cmccarthy.common.utils.HookUtil;
import com.cmccarthy.ui.config.WikipediaAbstractTestDefinition;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@CucumberContextConfiguration
@SuppressWarnings("unused")
public class Hooks extends WikipediaAbstractTestDefinition {

    private static final Object lock = new Object();
    @Autowired
    private HookUtil hookUtil;
    @Autowired
    private DriverManager driverManager;

    @Before
    public void beforeScenario(Scenario scenario) throws IOException {
        driverManager.createDriver();
    }

    @After
    public void afterScenario(Scenario scenario) {
        driverManager.getDriver().quit();
        hookUtil.endOfTest(scenario);
    }
}