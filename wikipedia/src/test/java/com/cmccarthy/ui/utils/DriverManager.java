package com.cmccarthy.ui.utils;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Locale;

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
        System.setProperty("webdriver.chrome.driver", Constants.DRIVER_DIRECTORY + "/chromedriver" + getOSExtension());

        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("window-size=1920,1080");
//        chromeOptions.addArguments("--headless");
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

    public boolean checkIfDriverExists() {
        File geckoDriver = new File(Constants.DRIVER_DIRECTORY + "/geckodriver" + getOSExtension());
        File chromedriver = new File(Constants.DRIVER_DIRECTORY + "/geckodriver" + getOSExtension());
        return geckoDriver.exists() && chromedriver.exists();
    }

    public void downloadDriver() {
        System.out.println(" inside download driver ");
        try {
            Process process;
            if (getOperatingSystem().equals("win")) {
                process = Runtime.getRuntime().exec("cmd.exe /c downloadDriver.sh", null,
                        new File(Constants.COMMON_RESOURCES));
            } else {
                String filePath = Constants.COMMON_RESOURCES + "/downloadDriver.sh";
                process = Runtime.getRuntime().exec(new String[]{"/bin/bash", "-c", filePath}, null);
            }
            process.waitFor();
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

    private String getOSExtension() {
        String extension = "";
        if (getOperatingSystem().contains("win")) {
            return ".exe.";
        }
        return extension;
    }
}
