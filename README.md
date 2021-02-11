# Spring Cucumber Test Harness

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/544aae36f927441ebe786fd5ce7a092b)](https://app.codacy.com/gh/cmccarthyIrl/spring-cucumber-junit-test-harness?utm_source=github.com&utm_medium=referral&utm_content=cmccarthyIrl/spring-cucumber-junit-test-harness&utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.com/cmccarthyIrl/spring-cucumber-junit-test-harness.svg?branch=master)](https://travis-ci.com/cmccarthyIrl/spring-cucumber-junit-test-harness) [![Codacy Badge](https://app.codacy.com/project/badge/Grade/6e4bd0bef1b5467aaaed6bc70a8ca2ae)](https://www.codacy.com/gh/cmccarthyIrl/spring-cucumber-test-harness/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cmccarthyIrl/spring-cucumber-test-harness&amp;utm_campaign=Badge_Grade)

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

The Framework uses [Spring Boot Test](https://spring.io/guides/gs/testing-web/)
, [Cucumber](https://cucumber.io/)
, [Rest Assured](https://rest-assured.io/) and [Selenium](https://www.selenium.dev/) client implementations.

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
        <artifactId>cucumber-java8</artifactId>
        <groupId>io.cucumber</groupId>
        <version>${cucumber.version}</version>
    </dependency>
    <dependency>
        <artifactId>cucumber-spring</artifactId>
        <groupId>io.cucumber</groupId>
        <version>${cucumber.version}</version>
    </dependency>
    <dependency>
        <artifactId>cucumber-junit</artifactId>
        <groupId>io.cucumber</groupId>
        <version>${cucumber.version}</version>
    </dependency>
    <dependency>
        <artifactId>extentreports-cucumber4-adapter</artifactId>
        <groupId>com.aventstack</groupId>
        <version>1.2.1</version>
        <exclusions>
            <exclusion>
                <artifactId>cucumber-java</artifactId>
                <groupId>io.cucumber</groupId>
            </exclusion>
            <exclusion>
                <artifactId>cucumber-core</artifactId>
                <groupId>io.cucumber</groupId>
            </exclusion>
        </exclusions>
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

By using the [JUnit Framework](https://junit.org/junit4/) `@RunWith` Annotation Type we can utilize
the [Cucumber Framework](https://cucumber.io/) and the `@CucumberOptions` Annotation Type to execute the `*.feature`
file tests

> Right click the `WeatherTest` class and select `Run`

```java

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"
        },
        glue = {"com/cmccarthy/step",
                "com/cmccarthy/utils"
        },
        features = {"classpath:feature/GoogleMapsTest.feature"}
)
public class GoogleMapsRunnerTest {
}
```

# Command Line

Normally you will use your IDE to run a `*.feature` file directly or via the `*Test.java` class. With the `Test` class,
we can run tests from the command-line as well.

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

The `AbstractTestDefinition` class is responsible for specifying each Step class as `@SpringBootTest` and
its `@ContextConfiguration`

> All the `Step Classes` in the Framework should `extend` the `AbstractTestDefinition` class

```java

@ContextConfiguration(classes = {FrameworkContextConfiguration.class})
@SpringBootTest
public class AbstractTestDefinition {
}
```

The `FrameworkContextConfiguration` class is responsible for specifying the Spring `@Configuration`, modules to scan,
properties to use etc

```java

@EnableRetry
@Configuration
@ComponentScan({
        "com.cmccarthy"
})
@PropertySource("application.properties")
public class FrameworkContextConfiguration {
}
```

# Environment Switching

There is only one thing you need to do to switch the environment - which is to set `<activeByDefault>` property in the
Master POM.

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

# Extent Spark Reports

The Framework uses [Spark Reports Framework](http://www.extentreports.com/docs/versions/4/java/spark-reporter.html) to
generate the HTML Test Reports

The example below is a report generated by Extent Reports open-source library.

<img src="https://github.com/cmccarthyIrl/spring-cucumber-junit-test-harness/blob/master/common/src/main/resources/demo/demo.png" height="400px"/>

# Logging

The Framework uses [Log4j2](https://logging.apache.org/log4j/2.x/) You can instantiate the logging service in any Class
like so

```java
        private final Logger logger=LoggerFactory.getLogger(WikipediaPageSteps.class);
```

you can then use the logger like so :

```java
        logger.info("This is a info message");
        logger.warn("This is a warning message");
        logger.debug("This is a info message");
        logger.error("This is a error message");
```

# Before / After Hooks

The [Log4j2](https://logging.apache.org/log4j/2.x/) logging service is initialized from the `Hooks.class`

```java
public class Hooks {

    @Autowired
    private HookUtils hookUtil;

    @After
    public void afterScenario(Scenario scenario) {
        hookUtil.endOfTest(scenario);
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


