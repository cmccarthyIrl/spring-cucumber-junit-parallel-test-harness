package com.cmccarthy.suites;

import com.cmccarthy.api.WeatherRunnerTest;
import com.cmccarthy.ui.WikipediaRunnerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        WeatherRunnerTest.class,
        WikipediaRunnerTest.class
})
public class JunitSuiteTest {

}