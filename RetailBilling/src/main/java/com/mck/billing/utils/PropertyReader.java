package com.mck.billing.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * The base property reader class responsible for reading data from various
 * properties files
 * 
 */
abstract class PropertyReader {

    private final Properties properties;

    /**
     * Loads the properties file
     * 
     * @param propertyFileName
     */
    PropertyReader(String propertyFileName) {
        properties = new Properties();
        try {
            properties.load(this.getClass().getResourceAsStream("/" + propertyFileName));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error occurred while loading the property file: " + propertyFileName);
        }
    }

    /**
     * Returns the property value for the provided property name
     * 
     * @param propertyName
     * @return
     */
    public String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }

}
