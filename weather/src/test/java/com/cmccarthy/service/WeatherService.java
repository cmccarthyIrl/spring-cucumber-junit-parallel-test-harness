package com.cmccarthy.service;

import com.cmccarthy.model.response.LocationWeatherRootResponse;
import com.cmccarthy.utils.ApplicationProperties;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class WeatherService {

    @Autowired
    private RestService restService;
    @Autowired
    private ResponseManagerService responseManagerService;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private LogFactoryService logFactoryService;

    public void getWeatherForLocation(String location) {
        final Map<String, Object> managedResponses = new HashMap<>();
        final Response response = restService.getRequestSpecification()
                .param("q", location)
                .param("appid", "0a1b11f110d4b6cd43181d23d724cb94")
                .get(applicationProperties.getWeatherAppUrl());

        managedResponses.put("class", response.as(LocationWeatherRootResponse.class));
        responseManagerService.setGetResponseThread(managedResponses);

        if (response.statusCode() != HttpURLConnection.HTTP_OK) {
            logFactoryService.getLogger().info("Could not retrieve the weather forecast from the Response");
            throw new NoSuchElementException("Could not retrieve the weather forecast from the Response");
        }
    }
}
