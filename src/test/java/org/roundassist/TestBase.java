package org.roundassist;

import org.openqa.selenium.WebDriver;

public class TestBase {

    protected WebDriver driver = DriverFactory.getDriver();

    public void openHomepage() {
        driver.get(AppConfig.getSiteUrl());
    }
}
