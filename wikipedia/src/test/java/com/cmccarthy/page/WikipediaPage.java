package com.cmccarthy.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

@Component
public class WikipediaPage extends AbstractPage {

    @FindBy(className = "central-featured-logo")
    private WebElement centerLogo;

    public void open(String url) {
        openAt(url);
    }

    public boolean isPageOpened() {
        return waitForElementPresent(centerLogo);
    }
}

