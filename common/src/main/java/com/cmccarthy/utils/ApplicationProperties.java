package com.cmccarthy.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {

    @Value("${base.url.value}")
    private String baseUrl;
    @Value("${browser}")
    private String browser;

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getBrowser() {
        return browser;
    }
}