package com.cmccarthy.ui.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverManager {

    private final Logger logger = LoggerFactory.getLogger(DriverManager.class);

    private final ThreadLocal<ChromeDriver> driverThreadLocal = new ThreadLocal<>();

    private ChromeDriver chromeDriver;

    private final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            logger.info("Quitting driver");
            chromeDriver.quit();
        }
    };

    @Autowired
    private void setLocalWebDriver() {
        System.setProperty("webdriver.chrome.driver", "../common/src/main/resources/chromedriver");

        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1920,1080");
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("-incognito");
        chromeOptions.addArguments("start-maximized");
        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--disable-extensions");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--no-sandbox");

        chromeDriver = new ChromeDriver(chromeOptions);
        driverThreadLocal.set(chromeDriver);
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    public ChromeDriver getDriver() {
        return driverThreadLocal.get();
    }
}
