package com.mck.billing.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConfigurationPropertyReaderTest {

    ConfigurationPropertyReader configurationPropertyReader;

    @Before
    public void init() {
        configurationPropertyReader = ConfigurationPropertyReader.getInstance();
    }

    @Test
    public void testShouldNotThrowErrorIfEntryNotFound() {
        String property = configurationPropertyReader.getProperty("doesNotExist");
        Assert.assertNull(property);
    }

}
