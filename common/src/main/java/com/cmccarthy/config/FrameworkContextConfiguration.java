package com.cmccarthy.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@Configuration
@ComponentScan({
        "com.cmccarthy"
})
@PropertySource("application.properties")
public class FrameworkContextConfiguration {
}
