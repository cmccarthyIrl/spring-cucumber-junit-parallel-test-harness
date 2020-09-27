package com.cmccarthy.page;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class WikipediaPage extends AbstractPage {

    @FindBy(id = "searchbar")
    private WebElement searchBarElement;

    public void open(String url){
        openAt(url);
    }

    public boolean isPageOpened() {
        return waitForElementPresent(searchBarElement);
    }
}

