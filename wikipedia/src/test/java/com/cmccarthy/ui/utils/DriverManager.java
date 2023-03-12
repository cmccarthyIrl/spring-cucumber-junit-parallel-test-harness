package com.cmccarthy.ui.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Locale;

@Component
public class DriverManager {

    private final Logger logger = LoggerFactory.getLogger(DriverManager.class);

    private final ThreadLocal<ChromeDriver> driverThreadLocal = new ThreadLocal<>();

    public void createDriver() {
        if (getDriver() == null) {
            setLocalWebDriver();
        }
    }

    private void setLocalWebDriver() {
        System.setProperty("webdriver.chrome.driver", Constants.DRIVER_DIRECTORY + "/chromedriver" + getExtension());

        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1920,1080");
//        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("--incognito");
//        chromeOptions.addArguments("start-maximized");
//        chromeOptions.addArguments("disable-infobars");
        chromeOptions.addArguments("--disable-extensions");
//        chromeOptions.addArguments("--disable-gpu");
//        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--remote-allow-origins=*");

        ChromeDriver chromeDriver = new ChromeDriver(chromeOptions);
        driverThreadLocal.set(chromeDriver);
    }

    public ChromeDriver getDriver() {
        return driverThreadLocal.get();
    }

    public boolean isDriverExisting() {
        File geckoDriver = new File(Constants.DRIVER_DIRECTORY + "/geckodriver" + getExtension());
        File chromedriver = new File(Constants.DRIVER_DIRECTORY + "/chromedriver" + getExtension());
        return geckoDriver.exists() && chromedriver.exists();
    }

    public void downloadDriver() {
        try {
            Process process;
            if (getOperatingSystem().equals("win")) {
                process = Runtime.getRuntime().exec("cmd.exe /c downloadDriver.sh", null,
                        new File(Constants.COMMON_RESOURCES));
            } else {
                process = Runtime.getRuntime().exec(
                        new String[]{"sh", "-c", Constants.COMMON_RESOURCES + "/downloadDriver.sh"});
            }
            process.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                logger.debug(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getOperatingSystem() {
        String os = System.getProperty("os.name", "generic").toLowerCase(Locale.ROOT);
        if (os.contains("mac") || os.contains("darwin")) {
            return "mac";
        } else if (os.contains("win")) {
            return "win";
        } else {
            return "linux";
        }
    }

    private String getExtension() {
        String extension = "";
        if (getOperatingSystem().contains("win")) {
            return ".exe";
        }
        return extension;
    }
}
