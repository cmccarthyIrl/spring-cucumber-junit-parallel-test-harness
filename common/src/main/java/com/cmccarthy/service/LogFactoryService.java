package com.cmccarthy.service;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class LogFactoryService {

    private static final ThreadLocal<Logger> logFactory = new ThreadLocal<>();
    private String tempFeatureFileName = "";

    public void createNewLogger(String featureName) {
        Logger log = Logger.getLogger("Thread" + featureName);
        Properties props = new Properties();
        props.setProperty("log4j.rootLogger", "info");
        props.setProperty("log4j.appender.file", "org.apache.log4j.RollingFileAppender");
        props.setProperty("log4j.appender.file.maxFileSize", "100MB");
        props.setProperty("log4j.appender.file.Append", "false");
        props.setProperty("log4j.appender.file.maxBackupIndex", "100");
        props.setProperty("log4j.appender.file.File", "logs/" + featureName + ".log");
        props.setProperty("log4j.appender.file.threshold", "info");
        props.setProperty("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
        props.setProperty("log4j.appender.file.layout.ConversionPattern", "%d{yyyy-MM-dd HH-mm-ss} | %-5p | %C{1}:%L | %m%n");
        props.setProperty("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
        props.setProperty("log4j.appender.stdout.Target", "System.out");
        props.setProperty("log4j.logger." + "Thread" + featureName, "INFO, file");
        PropertyConfigurator.configure(props);
        logFactory.set(log);
    }

    public Logger getLogger() {
        return logFactory.get();
    }

    public String getFeatureFileName() {
        return tempFeatureFileName;
    }

    public void setFeatureFileName(String tempFeatureFileName) {
        this.tempFeatureFileName = tempFeatureFileName;
    }
}