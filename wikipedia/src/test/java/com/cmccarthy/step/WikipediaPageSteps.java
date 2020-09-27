package com.cmccarthy.step;

import com.cmccarthy.config.AbstractTestDefinition;
import com.cmccarthy.page.WikipediaPage;
import com.cmccarthy.service.LogFactoryService;
import cucumber.api.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

public class WikipediaPageSteps extends AbstractTestDefinition{

    @Autowired
    private LogFactoryService logFactoryService;

    @Autowired
    private WikipediaPage wikipediaPage;

    @Given("^The user has open the Wikipedia Homepage : (.*)$")
    public void userIsOpenMainPage(String url) {
       logFactoryService.getLogger().info("Step start: user is open main page");
        wikipediaPage.open(url);
        assertTrue("Main Page should be opened", wikipediaPage.isPageOpened());
    }
}
