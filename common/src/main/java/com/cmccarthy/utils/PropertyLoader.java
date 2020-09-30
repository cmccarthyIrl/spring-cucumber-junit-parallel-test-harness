package com.cmccarthy.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {

    public static String loadProperty(String name) {
        Properties props = new Properties();
        try {
            props.load(PropertyLoader.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (name != null) {
            return props.getProperty(name);
        }
        return "";
    }
}