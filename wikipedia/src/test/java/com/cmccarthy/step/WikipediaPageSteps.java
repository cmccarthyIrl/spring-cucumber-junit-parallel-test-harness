package com.cmccarthy.step;

import com.cmccarthy.config.AbstractTestDefinition;
import com.cmccarthy.page.WikipediaPage;
import com.cmccarthy.service.LogFactoryService;
import com.cmccarthy.utils.ApplicationProperties;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

public class WikipediaPageSteps extends AbstractTestDefinition {

    @Autowired
    private LogFactoryService logFactoryService;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private WikipediaPage wikipediaPage;

    @Given("^The user has opened the Wikipedia Homepage$")
    public void userIsOpenMainPage() {
        logFactoryService.getLogger().info("Step start: The user has opened the Wikipedia Homepage");
        wikipediaPage.open(applicationProperties.getWikipediaUrl());
        assertTrue("Wikipedia Homepage should be opened", wikipediaPage.isPageOpened());
    }
}
