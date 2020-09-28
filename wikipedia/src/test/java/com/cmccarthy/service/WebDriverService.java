package com.cmccarthy.service;

import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Component
@Qualifier("webDriverService")
public class WebDriverService extends EventFiringWebDriver {

    private static final WebDriver REAL_DRIVER = createDriver();

    public WebDriverService() {
        super(REAL_DRIVER);
    }

    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            REAL_DRIVER.close();
        }
    };

    private static WebDriver createDriver() {
        String driverName = "firefox";
        final WebDriver driver;
        switch (driverName.toLowerCase()) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/geckodriver");
                FirefoxOptions capabilities = new FirefoxOptions();
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

    static {
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    @Before
    public void deleteAllCookies() {
        manage().deleteAllCookies();
    }
}