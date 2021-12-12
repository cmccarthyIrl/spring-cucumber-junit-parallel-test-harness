package com.cmccarthy.api.step;

import com.cmccarthy.api.model.response.LocationWeatherRootResponse;
import com.cmccarthy.api.service.WeatherService;
import com.cmccarthy.common.service.StepDefinitionDataManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.assertj.core.api.SoftAssertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class WeatherStep {

    private final Logger logger = LoggerFactory.getLogger(WeatherStep.class);
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private StepDefinitionDataManager stepDefinitionDataManager;

    @Then("^The weather for (.*) should be returned$")
    public void theWeatherForDublinShouldBeReturned(String location) {
        final SoftAssertions softAssertions = new SoftAssertions();
        final LocationWeatherRootResponse locationWeatherRootResponse = (LocationWeatherRootResponse) stepDefinitionDataManager.getStoredObjectMap().get("class");
        logger.info("Verifying the Response location : " + locationWeatherRootResponse.getName() + ", is equal to the expected location : " + location);

        softAssertions.assertThat(locationWeatherRootResponse.getName())
                .as("Expected the weather forecast to be for : " + location)
                .withFailMessage("But it was for : " + locationWeatherRootResponse.getName())
                .isEqualToIgnoringCase(location);
        softAssertions.assertAll();
    }

    @Given("^The user has requested the weather for (.*)$")
    public void theUserHasRequestedTheWeatherForDublin(String location) {
        logger.info("The user makes an request for the weather in : " + location);
        weatherService.getWeatherForLocation(location);
    }
}