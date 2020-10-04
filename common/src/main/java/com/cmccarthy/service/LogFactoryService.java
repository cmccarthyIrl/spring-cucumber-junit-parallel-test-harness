package com.cmccarthy.service;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.springframework.stereotype.Service;

@Service
public class LogFactoryService {


    private static final ThreadLocal<Logger> logFactory = new ThreadLocal<>();
    private String tempFeatureFileName = "";

    private final static String PATTERN = "%d{yyyy-MM-dd HH-mm-ss} | %-5p | %C{1}:%L | %m%n";
    private final static String MAX_FILE_SIZE = "100MB";
    private final static String MAX_BACKUP_INDEX = "100";

    public void createNewLogger(String featureName) {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        ctx.start(config);
        ctx.updateLoggers(config);
        Logger logger = ctx.getLogger("Thread" + featureName);
        logger.addAppender(addFileAppender(config, "fileAppender", featureName));
        logger.addAppender(addConsoleAppender("consoleAppender"));
        Configurator.setLevel(logger.getName(), Level.INFO);

        logFactory.set(logger);
    }

    private Appender addFileAppender(Configuration config, String appenderName, String featureName) {
        final Layout layout = PatternLayout.newBuilder()
                .withConfiguration(config)
                .withPattern(PATTERN).build();
        RollingFileAppender appender = RollingFileAppender.newBuilder()
                .withFileName("logs/" + featureName + ".log")
                .setName(appenderName)
                .withFilePattern(featureName + "%i.log")
                .setLayout(layout)
                .withPolicy(SizeBasedTriggeringPolicy.createPolicy(MAX_FILE_SIZE))
                .withStrategy(DefaultRolloverStrategy.newBuilder().withMax(MAX_BACKUP_INDEX).build())
                .withAppend(false)
                .build();

        appender.start();
        return appender;
    }

    private Appender addConsoleAppender(String appenderName) {
        ConsoleAppender appender = ConsoleAppender.newBuilder()
                .setName(appenderName)
                .withImmediateFlush(true)
                .build();

        appender.start();
        return appender;
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