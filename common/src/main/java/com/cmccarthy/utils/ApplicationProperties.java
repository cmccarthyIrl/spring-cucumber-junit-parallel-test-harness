package com.cmccarthy.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class ApplicationProperties {

    @Value("${apiPassword.value}")
    private String apiPassword;
    @Value("${username.value}")
    private String username;
    @Value("${password.value}")
    private String password;
    @Value("${base.url.value}")
    private String baseUrl;
    @Value("${browser.active}")
    private String browser;

    public String getApiPassword() {
        return apiPassword;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setApiPassword(String apiPassword) {
        this.apiPassword = apiPassword;
    }

    public String getBrowser() {
        return browser;
    }
}