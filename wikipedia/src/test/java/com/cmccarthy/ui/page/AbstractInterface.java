package com.cmccarthy.ui.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public interface AbstractInterface {

    void openAt(String url);

    void clickAction(WebElement element);

    boolean isElementDisplayed(WebElement element);

    void click(WebElement element) throws NoSuchFieldException;

    void click(By locator) throws NoSuchFieldException;

    void clickAction(By locator) throws NoSuchFieldException;

    void sendKeys(WebElement element, String value);
}
