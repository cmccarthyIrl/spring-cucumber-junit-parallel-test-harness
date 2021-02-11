package com.cmccarthy.api.step;

import com.cmccarthy.api.config.WeatherAbstractTestDefinition;
import com.cmccarthy.common.utils.HookUtil;
import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import org.springframework.beans.factory.annotation.Autowired;

public class Hooks extends WeatherAbstractTestDefinition {

    @Autowired
    private HookUtil hookUtil;

    @After
    public void afterScenario(Scenario scenario) {
        hookUtil.endOfTest(scenario);
    }
}