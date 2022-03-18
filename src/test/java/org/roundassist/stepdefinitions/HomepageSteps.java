package org.roundassist.stepdefinitions;

import io.cucumber.java.en.Given;
import org.roundassist.TestBase;

public class HomepageSteps extends TestBase {

    @Given("^I open the homepage$")
    public void iOpenTheHomepage() {
        openHomepage();
    }
}
