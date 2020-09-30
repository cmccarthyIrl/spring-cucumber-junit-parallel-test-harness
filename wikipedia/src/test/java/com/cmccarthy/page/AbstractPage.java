package com.cmccarthy.page;

import com.cmccarthy.utils.ApplicationProperties;
import com.cmccarthy.utils.InvisibilityOfElement;
import com.cmccarthy.utils.VisibilityOfElement;
import com.cmccarthy.utils.VisibilityOfElementLocated;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.NoSuchElementException;

@Component
public abstract class AbstractPage {

    @Autowired
    private ApplicationProperties applicationProperties;

    private WebDriver driver;
    private Wait<WebDriver> wait;
    private final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            driver.close();
        }
    };

    @PostConstruct
    public void init() {
        driver = createDriver();
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 10, 500);
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    private WebDriver createDriver() {
        final WebDriver driver;
        switch (applicationProperties.getBrowser().toLowerCase()) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/geckodriver");
                FirefoxOptions capabilities = new FirefoxOptions();
                capabilities.setHeadless(true);
                driver = new FirefoxDriver(capabilities);
                break;
            case "chrome":
                driver = new ChromeDriver();
                break;
            default:
                throw new NoSuchElementException("NoSuchElementException");
        }
        return driver;
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    /**
     * Open inner path of site
     */
    protected void openAt(String url) {
        getWebDriver().get(url);
    }

    /**
     * Wait until element is present
     *
     * @param element element
     * @return boolean
     */
    protected boolean waitForElementPresent(WebElement element) {
        boolean result = true;
        try {
            wait.until(new VisibilityOfElement(element));
        } catch (TimeoutException e) {
            result = false;
        } catch (Throwable t) {
            throw new Error(t);
        }
        return result;
    }

    /**
     * Wait until element not present
     *
     * @param element element
     * @return boolean
     */
    protected boolean waitForElementNotPresent(WebElement element) {
        boolean result = true;
        try {
            wait.until(new InvisibilityOfElement(element));
        } catch (TimeoutException e) {
            result = false;
        } catch (Throwable t) {
            throw new Error(t);
        }
        return result;
    }

    /**
     * Wait until element present by locator
     *
     * @param locator locator
     * @return boolean
     */
    protected boolean waitForElementPresent(By locator) {
        boolean result = true;
        try {
            wait.until(new VisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            result = false;
        } catch (Throwable t) {
            throw new Error(t);
        }
        return result;
    }

    /**
     * Type string into element
     *
     * @param element element
     * @param s       string
     */
    protected void typeInto(WebElement element, String s) {
        element.clear();
        element.sendKeys(s);
    }

    /**
     * Get attribute "value" of the element
     *
     * @param element element
     * @return value
     */
    protected String getValue(WebElement element) {
        return element.getAttribute("value");
    }
}
