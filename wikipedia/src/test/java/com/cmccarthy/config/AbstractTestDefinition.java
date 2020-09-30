package com.cmccarthy.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {WikipediaContextConfiguration.class})
@SpringBootTest
public class AbstractTestDefinition {
}