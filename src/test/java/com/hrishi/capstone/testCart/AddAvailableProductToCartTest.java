package com.hrishi.capstone.testCart;

import com.hrishi.capstone.util.BaseTest;
import com.hrishi.capstone.actions.SearchContent;
import com.hrishi.capstone.modals.CartModal;
import com.hrishi.capstone.pages.HomePage;
import com.hrishi.capstone.pages.ProductDetailsPage;
import com.hrishi.capstone.pages.SearchResultPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("E-commerce Platform")
@Feature("Shopping Cart Management")
@Story("User adds products to cart")
public class AddAvailableProductToCartTest extends BaseTest {

    // Logger for general test logging
    private static final Logger logger = LogManager.getLogger(AddAvailableProductToCartTest.class);
    // Logger specifically for search-related actions
    private static final Logger searchLogger = LogManager.getLogger("SearchLogger");
    // Logger specifically for cart-related actions
    private static final Logger cartLogger = LogManager.getLogger("CartLogger");

    /**
     * Test Name: testUserCanAddProductToCartWhenInStock
     * Description: Verifies that a user is able to successfully add a product to the cart when it is available for purchase.
     */
    @Test(testName = "testUserCanAddProductToCartWhenInStock", description = "Verifies that a user is able to successfully add a product to the cart when it is available for purchase.")
    public void testUserCanAddProductToCartWhenInStock() {
        try {
            logger.info("Executing testUserCanAddProductToCartWhenInStock: Verifying that a user can add a product to the cart when it is in stock");

            // Create a search content object for the product (bell dress in this case)
            SearchContent searchContent = SearchContent.builder().build().bellDress();
            searchLogger.info("Search Content: {}", searchContent.getInput());

            // Navigate to the homepage and perform a search for the product
            HomePage homePage = new HomePage(getWebDriver());
            homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

            // Navigate to the search results page and select the product to view its details
            SearchResultPage searchResultPage = new SearchResultPage(getWebDriver());
            ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
            cartLogger.debug("Navigated to Product Details Page");

            // Initialize the cart modal
            CartModal cartModal = new CartModal(getWebDriver());

            // Check if the product is in stock
            if (!productDetailsPage.isProductSoldOut()) {
                // Add the product to the cart
                productDetailsPage.clickAddToCart();
                // Verify that the success message is displayed
                Assert.assertTrue(cartModal.getSuccessMessage().contains("Item added to your cart"));
                cartLogger.info("Product Added to the Cart");
            } else {
                // If the product is out of stock, log an error and fail the test
                Assert.fail("Product Out of Stock");
                cartLogger.error("Product is Out of Stock");
            }
        } catch (Exception e) {
            logger.error("Error occurred during test execution: testUserCanAddProductToCartWhenInStock", e);
            throw e; // Rethrow the exception to mark the test as failed
        }
    }

    /**
     * Test Name: testCartCountIncreasesAfterAddingProduct
     * Description: Verifies that the cart count is increased by one after adding a product to the cart.
     */
    @Test(testName = "testCartCountIncreasesAfterAddingProduct", description = "Verifies that the cart count is increased by one after adding a product to the cart.")
    public void testCartCountIncreasesAfterAddingProduct() {
        try {
            logger.info("Executing testCartCountIncreasesAfterAddingProduct: Verifying that the cart count is increased after adding a product to the cart");

            // Create a search content object for the product (bell dress in this case)
            SearchContent searchContent = SearchContent.builder().build().bellDress();
            searchLogger.info("Search content: {}", searchContent.getInput());

            // Navigate to the homepage and perform a search for the product
            HomePage homePage = new HomePage(getWebDriver());
            homePage.getHeader().clickSearchBtn().searchProduct(searchContent.getInput());

            // Navigate to the search results page and select the product to view its details
            SearchResultPage searchResultPage = new SearchResultPage(getWebDriver());
            ProductDetailsPage productDetailsPage = searchResultPage.clickToViewProductByName();
            cartLogger.debug("Navigated to product details page.");

            // Initialize the cart modal
            CartModal cartModal = new CartModal(getWebDriver());

            // Check if the product is in stock
            if (!productDetailsPage.isProductSoldOut()) {
                // Get the initial cart count
                int initialCount = homePage.getHeader().getCartCount();
                // Add the product to the cart
                productDetailsPage.clickAddToCart();
                // Verify that the success message is displayed
                Assert.assertTrue(cartModal.getSuccessMessage().contains("Item added to your cart"));
                // Get the new cart count
                int newCount = homePage.getHeader().getCartCount();
                // Verify that the cart count has increased by one
                Assert.assertEquals(newCount, initialCount + 1);
                cartLogger.info("Cart count increased successfully.");
            } else {
                // If the product is out of stock, log an error and fail the test
                Assert.fail("Product Out of Stock");
                cartLogger.error("Product is out of stock.");
            }
        } catch (Exception e) {
            logger.error("Error occurred during test execution: testCartCountIncreasesAfterAddingProduct", e);
            throw e; // Rethrow the exception to mark the test as failed
        }
    }
}
