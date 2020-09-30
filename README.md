# Spring Cucumber Test Harness

[![Build Status](https://travis-ci.org/cmccarthyIrl/spring-cucumber-test-harness.svg?branch=master)](https://travis-ci.org/cmccarthyIrl/spring-cucumber-test-harness)
 
 <!-- [![codecov](https://codecov.io/gh/cmccarthyIrl/spring-cucumber-test-harness/branch/master/graph/badge.svg)](https://codecov.io/gh/cmccarthyIrl/spring-cucumber-test-harness) [![Release](https://img.shields.io/github/release/jenkinsci/jacoco-plugin.svg)](https://github.com/jenkinsci/jacoco-plugin/releases) -->
# Index

<table> 
<tr>
  <th>Start</th>
  <td>
    | <a href="#maven">Maven</a> 
    | <a href="#quickstart">Quickstart</a> | 
  </td>
</tr>
<tr>
  <th>Run</th>
  <td>
     | <a href="#junit">JUnit</a>
     | <a href="#junit-suites">JUnit Suites</a>
    | <a href="#command-line">Command Line</a>
    | <a href="#ide-support">IDE Support</a>    
    | <a href="#java-jdk">Java JDK</a>    
    | <a href="#troubleshooting">Troubleshooting</a>    |
  </td>
</tr>
<tr>
  <th>Report</th> 
  <td>
     | <a href="#configuration">Configuration</a> 
    | <a href="#environment-switching">Environment Switching</a>
    | <a href="#extent-reports">Extent HTML Reports</a>
    | <a href="#logging">Logging</a> |
  </td>
</tr>
<tr>
  <th>Advanced</th>
  <td>
    | <a href="#hooks">Before / After Hooks</a>
    | <a href="#json-transforms">JSON Transforms</a>
    | <a href="#contributing">Contributing</a> |
    </td>
</tr>
</table>
    
# Maven

The Framework uses [Spring Boot Test](https://spring.io/guides/gs/testing-web/), [Cucumber](https://cucumber.io/), [Rest Assured](https://rest-assured.io/) and [Selenium](https://www.selenium.dev/) client implementations.

Spring `<dependencies>`:

```xml
<dependecies>
    ...
        <dependency>
            <groupId>org.springframework.amqp</groupId>
            <artifactId>spring-rabbit</artifactId>
            <version>${spring-rabbit.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-test</artifactId>
        </dependency>
    ...
</dependecies>
```

Cucumber & Rest Assured `<dependencies>`:

```xml
<dependecies>
    ...
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>${restassured.version}</version>
        </dependency>
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>${cucumber.version}</version>
        </dependency>
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-spring</artifactId>
            <version>${cucumber.version}</version>
        </dependency>
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>cucumber-junit</artifactId>
            <version>${cucumber.version}</version>
        </dependency>
        <dependency>
            <groupId>info.cukes</groupId>
            <artifactId>gherkin</artifactId>
            <version>2.12.2</version>
            <scope>provided</scope>
        </dependency>
    ...
</dependecies>
```

Selenium `<dependencies>`:

```xml
<dependecies>
    ...
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>${selenium-version}</version>
        </dependency>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-server</artifactId>
            <version>${selenium-version}</version>
        </dependency>
    ...
</dependecies>
```
# Quickstart

- [Intellij IDE](https://www.jetbrains.com/idea/) - `Recommended`
- [Java JDK 8](https://www.oracle.com/ie/java/technologies/javase/javase-jdk8-downloads.html)
- [Apache Maven 3.6.3](https://maven.apache.org/docs/3.6.3/release-notes.html) 

# JUnit

By using the [JUnit Framework](https://junit.org/junit4/) `@RunWith` Annotation Type we can utilize the [Cucumber Framework](https://cucumber.io/) and the `@CucumberOptions` Annotation Type to execute the `*.feature` file tests

> Right click the `WeatherTest` class and select `Run`

```java
@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/cucumber-html-reports",
                "json:target/cucumber-json-report.json",
                "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"
        },
        glue = {"com/cmccarthy/step",
                "com/cmccarthy/utils"
        },
        features = {"classpath:feature/WeatherTest.feature"}
)
public class WeatherTest {
    @AfterClass
    public static void writeExtentReport() {
        Reporter.loadXMLConfig(new File(Objects.requireNonNull(WeatherTest.class.getClassLoader().getResource("extent.xml")).getPath()));
        Reporter.setSystemInfo("User Name", System.getProperty("user.name"));
        Reporter.setSystemInfo("Time Zone", System.getProperty("user.timezone"));
        Reporter.setSystemInfo("Machine", System.getProperty("os.name") + " - " + System.getProperty("os.arch"));
        Reporter.setSystemInfo("Java Version", System.getProperty("java.version"));
    }
}
```

# JUnit Suites

By using the [JUnit Framework](https://junit.org/junit4/) `@Suites` Annotation Type, we can execute multiple Modules in a single test run 

> Right click the `JunitSuiteTest` class and select `Run`
>
```java
@RunWith(Suite.class)
@Suite.SuiteClasses({
        WeatherRunnerTest.class,
        WikipediaRunnerTest.class
})
public class JunitSuiteTest {
}
```

# Command Line 

Normally you will use your IDE to run a `*.feature` file directly or via the `*Test.java` class. With the `Test` class, we can run tests from the command-line as well.

Note that the `mvn test` command only runs test classes that follow the `*Test.java` naming convention. 

You can run a single test or a suite or tests like so :

```
mvn test -Dtest=WeatherTest
```
```
mvn test -Dtest=JunitSuiteTest
```

Note that the `mvn clean install` command runs all test Classes that follow the `*Test.java` naming convention

```
mvn clean install
```

# IDE Support 

To minimize the discrepancies between IDE versions and Locales the `<sourceEncoding>` is set to `UTF-8`

```xml
<properties>
    ...
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
    ...
 </properties>
```

# Java JDK

The Java version to use is defined in the `maven-compiler-plugin` 

```xml
    <build>
        ...
        <pluginManagement>
            <plugins>
                    ...
                    <plugin>
                        <groupId>org.apache.maven.plugins</groupId>
                        <artifactId>maven-compiler-plugin</artifactId>
                        <configuration>
                            <source>1.8</source>
                            <target>1.8</target>
                        </configuration>
                    </plugin>
                    ...
            </plugins>
        </pluginManagement>
        ...
    </build>
```

# Configuration 

The `AbstractTestDefinition` class is responsible for specifying each Step class as `@SpringBootTest` and its `@ContextConfiguration`

> All the `Step Classes` in the Framework should `extend` the `AbstractTestDefinition` class

```java
@ContextConfiguration(classes = {FrameworkContextConfiguration.class})
@SpringBootTest
public class AbstractTestDefinition {}
```

The `FrameworkContextConfiguration` class is responsible for specifying the Spring `@Configuration`, modules to scan, properties to use etc

```java
@EnableRetry
@Configuration
@ComponentScan({
        "com.cmccarthy"
})
@PropertySource("application.properties")
public class FrameworkContextConfiguration {}
```

# Environment Switching

There is only one thing you need to do to switch the environment - which is to set `<activeByDefault>` property in the Master POM.

> By default, the value of `spring.profiles.active` is defined in the `application.properties` file which inherits its value from the Master POM property `<activeByDefault>`

```xml
 <profiles>
        ...
        <profile>
            <id>prod</id>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
            <properties>
                <activatedProperties>prod</activatedProperties>
            </properties>
        </profile>
        ...
</profiles>
```

You can then specify the profile to use when running Maven from the command line like so:
```
mvn clean install -P dev
```

Below is an example of the `application.properties` file.

```properties
spring.profiles.active=@activatedProperties@
```


# Extent Reports

The Framework uses [Extent Reports Framework](https://extentreports.com/) to generate the HTML Test Reports

The example below is a report generated by Extent Reports open-source library.

<img src="https://github.com/cmccarthyIrl/spring-cucumber-test-harness/blob/master/common/src/main/resources/demo/extent-report.jpg" height="500px"/>


# Logging

The Framework uses [Log4j](https://logging.apache.org/log4j/2.x/) and [Spring](https://docs.spring.io/spring-Framework/docs/current/javadoc-api/org/springFramework/stereotype/Service.html) to leverage the power of the `@Component` - Annotation Type to implement the Logging Service.

```java
@Component
public class LogFactoryService {

    private static final ThreadLocal<Logger> logFactory = new ThreadLocal<>();
    private String tempFeatureFileName = "";

    public void createNewLogger(String featureName) {
        Logger log = Logger.getLogger("Thread" + featureName);
        Properties props = new Properties();
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
```

# Before / After Hooks

The [Log4j](https://logging.apache.org/log4j/2.x/) logging `@Service` is initialized from the `Hooks.class` 

```java
public class Hooks extends AbstractTestDefinition {

    @Autowired
    private LogFactoryService logFactoryService;

    @Before
    public void beforeScenario(Scenario scenario) {
        final String rawFeatureName = scenario.getId().split(";")[0].replace("-", " ");
        final String featureName = rawFeatureName.substring(0, 1).toUpperCase() + rawFeatureName.substring(1);
        if (!logFactoryService.getFeatureFileName().equals(featureName)) {
            logFactoryService.setFeatureFileName(featureName);
            logFactoryService.createNewLogger(featureName);
            long id = Thread.currentThread().getId();
            logFactoryService.getLogger().info("New log created " + featureName);
        }
    }

    @After
    public void afterScenario() {
        logFactoryService.getLogger().info("");
        logFactoryService.getLogger().info("==========================================================================");
        logFactoryService.getLogger().info("==============================TEST COMPLETED==============================");
        logFactoryService.getLogger().info("==========================================================================");
        logFactoryService.getLogger().info("");
    }
}
```
# JSON Transforms

[Rest Assured IO](https://rest-assured.io/) is used to map the `Response` Objects to their respective `POJO` Classes

```xml
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>3.0.0</version>
    </dependency>
```

# Troubleshooting

- Execute the following commands to resolve any dependency issues
    1. `cd ~/install directory path/spring-cucumber-test-harness`
    2. `mvn clean install -DskipTests` 
    
# Contributing

Spotted a mistake? Questions? Suggestions?

[Open an Issue](https://github.com/cmccarthyIrl/spring-cucumber-test-harness/issues)


