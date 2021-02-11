package com.cmccarthy.api.service;

import com.cmccarthy.api.model.response.LocationWeatherRootResponse;
import com.cmccarthy.common.service.RestService;
import com.cmccarthy.common.service.StepDefinitionDataManager;
import com.cmccarthy.common.utils.ApplicationProperties;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class WeatherService {

    private final Logger logger = LoggerFactory.getLogger(WeatherService.class);
    @Autowired
    private RestService restService;
    @Autowired
    private StepDefinitionDataManager stepDefinitionDataManager;
    @Autowired
    private ApplicationProperties applicationProperties;

    public void getWeatherForLocation(String location) {
        final Map<String, Object> managedResponses = new HashMap<>();
        final Response response = restService.getRequestSpecification()
                .param("q", location)
                .param("appid", "0a1b11f110d4b6cd43181d23d724cb94")
                .get(applicationProperties.getWeatherAppUrl());

        managedResponses.put("class", response.as(LocationWeatherRootResponse.class));
        stepDefinitionDataManager.setStoredObjectMap(managedResponses);

        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            logger.info("Could not retrieve the weather forecast from the Response");
            throw new NoSuchElementException("Could not retrieve the weather forecast from the Response");
        }
    }
}
