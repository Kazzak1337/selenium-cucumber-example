package org.roundassist.stepdefinitions;

import io.cucumber.java.en.And;
import org.openqa.selenium.support.PageFactory;
import org.roundassist.TestBase;
import org.roundassist.webviews.Header;

public class HeaderSteps extends TestBase {

    private Header header = PageFactory.initElements(driver, Header.class);

    @And("^I search products by \"(.+)\"$") // + -> >= 1 caractere; * -> >= 0 caractere
    public void iSearchProductsBy(String searchKeyword) {
        header.search(searchKeyword);
    }
}
