package com.cmccarthy.utils;

import com.cmccarthy.service.LogFactoryService;
import cucumber.api.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HookUtils {

    @Autowired
    private LogFactoryService logFactoryService;

    public void createLogger(String featureName) {
        if (!logFactoryService.getFeatureFileName().equals(featureName)) {
            logFactoryService.setFeatureFileName(featureName);
            logFactoryService.createNewLogger(featureName);
        }
    }

    public void endOfTest() {
        logFactoryService.getLogger().info("");
        logFactoryService.getLogger().info("==========================================================================");
        logFactoryService.getLogger().info("==============================TEST COMPLETED==============================");
        logFactoryService.getLogger().info("==========================================================================");
        logFactoryService.getLogger().info("");
    }

    public String getFeatureFileName(Scenario scenario) {
        final String rawFeatureName = scenario.getId().split(";")[0].replace("-", " ");
        return rawFeatureName.substring(0, 1).toUpperCase() + rawFeatureName.substring(1);
    }
}