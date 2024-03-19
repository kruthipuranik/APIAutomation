package com.tekarch.base;

import org.techArk.utils.EnvironmentDetails;
import org.techArk.utils.TestDataUtils;
import org.testng.annotations.BeforeSuite;

public class BaseTest {
    @BeforeSuite
    public void beforeSuite() {
        EnvironmentDetails.loadProperties();
        TestDataUtils.loadProperties();
    }
}
