package com.mck.billing.utils;

/**
 * Loads and reads the configuration property file : configuration.properties
 * 
 */
public class ConfigurationPropertyReader extends PropertyReader {

    private static final String CONFIGURATION_FILE = "configuration.properties";
    private static ConfigurationPropertyReader instance;

    static {
        if (null != instance) {
            throw new AssertionError("Found an already created ConfigurationPropertyReader instance!!");
        }
    }

    /**
     * Returns an instance of ConfigurationPropertyReader
     * 
     * @return
     */
    public static ConfigurationPropertyReader getInstance() {
        if (instance == null) {
            instance = new ConfigurationPropertyReader();
        }
        return instance;
    }

    private ConfigurationPropertyReader() {
        super(CONFIGURATION_FILE);
    }

}
