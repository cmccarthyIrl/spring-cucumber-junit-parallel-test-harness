package com.cmccarthy.step;

import com.cmccarthy.config.AbstractTestDefinition;
import com.cmccarthy.service.HooksService;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks extends AbstractTestDefinition {

    @Autowired
    private HooksService hooksService;

    @Before
    public void beforeScenario(Scenario scenario) {
        hooksService.createLogger(hooksService.getFeatureFileName(scenario));
    }

    @After
    public void afterScenario() {
        hooksService.endOfTest();
    }



}