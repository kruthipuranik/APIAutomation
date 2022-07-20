package org.techArk.utils;

import org.testng.annotations.BeforeSuite;

public class Base {
    @BeforeSuite
    public void beforeSuite() {
        EnvironmentDetails.loadProperties();
        TestDataUtils.loadProperties();
    }
}
