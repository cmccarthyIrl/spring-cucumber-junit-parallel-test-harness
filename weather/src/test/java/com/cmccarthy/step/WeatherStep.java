package com.cmccarthy.step;

import com.cmccarthy.config.AbstractTestDefinition;
import com.cmccarthy.model.response.LocationWeatherRootResponse;
import com.cmccarthy.service.LogFactoryService;
import com.cmccarthy.service.ResponseManagerService;
import com.cmccarthy.service.WeatherService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.assertj.core.api.SoftAssertions;
import org.springframework.beans.factory.annotation.Autowired;

public class WeatherStep extends AbstractTestDefinition {

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private ResponseManagerService responseManagerService;
    @Autowired
    private LogFactoryService logFactoryService;

    @Then("^The weather for (.*) should be returned$")
    public void theWeatherForDublinShouldBeReturned(String location) {
        final SoftAssertions softAssertions = new SoftAssertions();
        final LocationWeatherRootResponse locationWeatherRootResponse = (LocationWeatherRootResponse) responseManagerService.getGetResponse().get("class");
        logFactoryService.getLogger().info("Verifying the Response location : " + locationWeatherRootResponse.getName() + ", is equal to the expected location : " + location);

        softAssertions.assertThat(locationWeatherRootResponse.getName()).as("Expected the weather forecast to be for : " + location).withFailMessage("But it was for : " + locationWeatherRootResponse.getName()).isEqualToIgnoringCase(location);
        softAssertions.assertAll();
    }

    @Given("^The user has requested the weather for (.*)$")
    public void theUserHasRequestedTheWeatherForDublin(String location) {
        logFactoryService.getLogger().info("The user makes an request for the weather in : " + location);
        weatherService.getWeatherForLocation(location);
    }
}