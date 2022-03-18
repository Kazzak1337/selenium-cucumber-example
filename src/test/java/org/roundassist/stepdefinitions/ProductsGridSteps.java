package org.roundassist.stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.roundassist.TestBase;
import org.roundassist.webviews.ProductsGrid;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.number.OrderingComparison.greaterThan;

public class ProductsGridSteps extends TestBase {

    private ProductsGrid productsGrid = PageFactory.initElements(driver, ProductsGrid.class);

    @When("^I select the option \"(.+)\" in the Sort By list$")
    public void iSelectTheOptionInTheSortByList(String sortCriteria) {
        productsGrid.getSortByList().selectByVisibleText(sortCriteria);
    }

    @And("^I sort the products in (.+) direction$")
    public void iSortTheProducts(String sortDirection) {
        String classAttribute;

        if (sortDirection.equals("descending")) {
            classAttribute = "--asc";
        } else {
            classAttribute = "--desc";
        }

        if (productsGrid.getSortDirectionButton().getAttribute("class").contains(classAttribute)) {
            productsGrid.getSortDirectionButton().click();
        }
    }

    @Then("^the products are ordered by (.+) in (.+) order$")
    public void theProductsAreOrderedByPriceInDescendingOrder(String sortCriteria, String sortDirection) {

        switch (sortCriteria) {
            case "Price":
                List<Double> prices = new ArrayList<>();

                for (WebElement priceContainer : productsGrid.getProductPrices()) {
                    String priceText = priceContainer.getText();

                    ///////////varianta mai frumoasa
                    Pattern pattern = Pattern.compile("(\\d+,\\d+).*");
                    Matcher matcher = pattern.matcher(priceText);

                    if (matcher.find()) {
                        //group(0) e tot textul extras, group(1) e textul extras dupa aplicarea regex
                        double price = Double.parseDouble(matcher.group(1).replace(",", "."));
                        prices.add(price);
                    }

                    //////////varianta mai urata
//            double price = Double.parseDouble(priceText.split(" ")[0].replace(",", "."));
//            prices.add(price);
                }

                assertThat("No products prices could be extracted.",
                        prices,
                        hasSize(greaterThan(0)));

                List<Double> sortedPrices = new ArrayList<>(prices);

                Comparator<Double> comparatorPrices;

                if (sortDirection.equals("ascending")) {
                    comparatorPrices = Comparator.naturalOrder();
                } else {
                    comparatorPrices = Comparator.reverseOrder();
                }

                sortedPrices.sort(comparatorPrices);

                assertThat("Product prices are not ordered correctly",
                        prices,
                        equalTo(sortedPrices));

                break;

            case "Name":
                List<String> names = new ArrayList<>();

                for (WebElement namesContainer : productsGrid.getProductNames()) {
                    names.add(namesContainer.getText());
                }

                assertThat("No products names could be extracted.",
                        names,
                        hasSize(greaterThan(0)));

                List<String> sortedNames = new ArrayList<>(names);

                Comparator<String> comparatorNames;

                if (sortDirection.equals("ascending")) {
                    comparatorNames = Comparator.naturalOrder();
                } else {
                    comparatorNames = Comparator.reverseOrder();
                }

                sortedNames.sort(comparatorNames);

                assertThat("Product names are not ordered correctly",
                        names,
                        equalTo(sortedNames));

                break;

            default:
                System.out.println("Wrong input");
        }
    }
}
