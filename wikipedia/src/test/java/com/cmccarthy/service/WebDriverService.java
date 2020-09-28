package com.cmccarthy.service;

import com.cmccarthy.utils.ApplicationProperties;
import com.cmccarthy.utils.PropertyLoader;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class WebDriverService extends EventFiringWebDriver {

    @Autowired
    ApplicationProperties applicationProperties;

    private static final WebDriver REAL_DRIVER = createDriver();
    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            REAL_DRIVER.close();
        }
    };

    private static WebDriver createDriver() {
        String driverName = System.getProperties().getProperty("spring.profiles.active");
        System.out.println("driverName = " + driverName);
        String driverName2 = System.getProperties().getProperty("browser");
        System.out.println("browser = " + driverName2);
        String driverName3 = PropertyLoader.loadProperty("browser");
        System.out.println("browser = " + driverName3);
        driverName = "firefox";
        final WebDriver driver;
        switch (driverName.toLowerCase()) {
            case "firefox":
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"/src/test/resources/geckodriver");
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

    public WebDriverService() {
        super(REAL_DRIVER);
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