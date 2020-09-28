package com.cmccarthy.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * Get property value from file
 */
public class PropertyLoader {

	private static final String PROP_FILE = "/application.properties";

	public static String loadProperty(String name) {
		Properties props = new Properties();
		try {
			props.load(PropertyLoader.class.getResourceAsStream(PROP_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String value = "";

		if (name != null) {
			value = props.getProperty(name);
		}
		return value;
	}
}