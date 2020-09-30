package com.cmccarthy;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        WeatherRunnerTest.class,
        WikipediaRunnerTest.class
})
public class JunitSuiteTest {

}