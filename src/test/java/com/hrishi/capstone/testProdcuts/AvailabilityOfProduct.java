package com.hrishi.capstone.testProducts;

import com.hrishi.capstone.util.BaseTest;
import com.hrishi.capstone.actions.SearchContent;
import com.hrishi.capstone.pages.HomePage;
import com.hrishi.capstone.pages.ProductDetailsPage;
import com.hrishi.capstone.pages.SearchResultPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Annotations for Epic, Feature, and Story
@Epic("Product Availability")
@Feature("Search and View Products")
public class AvailabilityOfProduct extends BaseTest {

    // Logger for logging in this class
    private static final Logger logger = LogManager.getLogger(AvailabilityOfProduct.class);

    /**
     * Test for verifying that the product is available when the user searches for it
     */
    @Test(testName = "testVerifyProductAvailability", description = "Verify that the product is available when the user searches for it.")
    @Story("User should see available products")
    public void testVerifyProductAvailability() {
        try {
            // Arrange
            logger.info("Creating a search content object for the product (bell dress in this case).");
            SearchContent searchContent = SearchContent.builder().build().bellDress();

            // Act
            logger.info("Navigating to the homepage and performing a search for the product.");
            HomePage homePage = new HomePage(getWebDriver());
            homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

            // Navigate to the search results page and select the product to view its details
            logger.info("Navigating to the search results page and selecting the product to view its details.");
            SearchResultPage searchResultPage = new SearchResultPage(getWebDriver());
            ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByIndex(0);

            // Assert
            logger.info("Verifying that the product is available (not sold out).");
            Assert.assertFalse(productDetailsPage.isProductSoldOut(), "Product is available");
        } catch (Exception e) {
            logger.error("Error occurred during test execution: testVerifyProductAvailability", e);
            throw e; // Rethrow the exception to mark the test as failed
        }
    }

    /**
     * Test for verifying that the product is marked as out of stock when the user searches for it
     */
    @Test(testName = "testVerifyProductOutOfStock", description = "Verify that the product is marked as out of stock when the user searches for it.")
    @Story("User should see out of stock products")
    public void testVerifyProductOutOfStock() {
        try {
            // Arrange
            logger.info("Creating a search content object for a sold-out product.");
            SearchContent searchContent = SearchContent.builder().build().soldOutProduct();

            // Act
            logger.info("Navigating to the homepage and performing a search for the product.");
            HomePage homePage = new HomePage(getWebDriver());
            homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

            // Navigate to the search results page and select the product to view its details
            logger.info("Navigating to the search results page and selecting the product to view its details.");
            SearchResultPage searchResultPage = new SearchResultPage(getWebDriver());
            ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByIndex(0);

            // Assert
            logger.info("Verifying that the product is marked as out of stock.");
            Assert.assertTrue(productDetailsPage.isProductSoldOut(), "The product is marked Out of Stock as expected.");
        } catch (Exception e) {
            logger.error("Error occurred during test execution: testVerifyProductOutOfStock", e);
            throw e; // Rethrow the exception to mark the test as failed
        }
    }
}
