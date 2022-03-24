package org.roundassist.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@CucumberOptions(
        plugin = {"html:target/cucumber.html", "json:target/cucumber/reports.json"},
        glue = {"org.roundassist"},
        features = {"src/test/resources/features/product-sorting.feature"}
)
@RunWith(Cucumber.class)
public class ProductSortingRunner {
}
