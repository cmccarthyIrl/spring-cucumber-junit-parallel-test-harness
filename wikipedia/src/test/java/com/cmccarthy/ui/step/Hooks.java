package com.cmccarthy.ui.step;

import com.cmccarthy.common.utils.HookUtil;
import com.cmccarthy.ui.config.WikipediaAbstractTestDefinition;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

@CucumberContextConfiguration
public class Hooks extends WikipediaAbstractTestDefinition {

    @Autowired
    private HookUtil hookUtil;

    @After
    public void afterScenario(Scenario scenario) {
        hookUtil.endOfTest(scenario);
    }
}