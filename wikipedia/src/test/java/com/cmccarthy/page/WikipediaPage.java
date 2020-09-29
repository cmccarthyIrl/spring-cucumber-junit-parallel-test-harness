package com.cmccarthy.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;

@Component
public class WikipediaPage extends AbstractPage {

    @FindBy(id = "p-logo")
    private WebElement sideBarLogo;

    public void open(String url) {
        openAt(url);
    }

    public boolean isPageOpened() {
        return waitForElementPresent(sideBarLogo);
    }
}

