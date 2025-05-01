package tests


import com.sun.tools.javac.Main
import org.example.pages.CardPage
import org.example.pages.CategoryFoundPage
import org.example.pages.MainPage
import org.example.pages.SearchResultsPage
import org.example.utils.Browser
import org.example.utils.WebDriverFactory
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver;
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchTest {

    // Use-Case #1 - Search For Product
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun searchTest(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val keyword = "пылесос"
            val hasResults = MainPage(driver)
                .openMainPage()
                .searchFor(keyword)
                .hasRelatedSearch(keyword)

            assertTrue(hasResults, "Search results do not contain expected query: $keyword")
        } finally {
            driver.quit()
        }
    }

    // Use-Case #2 - check that both products are added for comparison
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun testAddingToComparison(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val hasBothObjects = SearchResultsPage(driver)
                .openVacuumCleanerSearchPage()
                .addTwoItemsToCompareFromSearchForVacuumCleaner()
                .openComparePage()
                .isVacuumCleanersOnPage()

            assertTrue(hasBothObjects, "Search results are not on the page")
        } finally {
            driver.quit()
        }
    }

    // Use-Case #3 - checking that sorting by price (cheaper first) is correct
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun testPriceFilterCheapFirst(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val isResultSortedCorrectly = SearchResultsPage(driver)
                .openVacuumCleanerSearchPage()
                .isFilteredByPriceCheapFirst()

            assertTrue(isResultSortedCorrectly, "Objects are not sorted by cheaper first rule")
        } finally {
            driver.quit()
        }
    }

    // Use-Case #4 - checking that sorting by price (expensive first) is correct
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun testPriceFilterExpensiveFirst(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val isResultSortedCorrectly = SearchResultsPage(driver)
                .openVacuumCleanerSearchPage()
                .isFilteredByPriceExpensiveFirst()

            assertTrue(isResultSortedCorrectly, "Objects are not sorted by expensive first rule")
        } finally {
            driver.quit()
        }
    }

    // Use-Case #5 - adding product to favourites
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun testAddingProductToFavourites(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val isProductAdded = MainPage(driver)
                .openMainPage()
                .isAddingToFavouriteCorrect()

            assertTrue(isProductAdded, "Products are not correct after adding to favourites")
        } finally {
            driver.quit()
        }
    }

    // Use-Case #6 - adding product to cart
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun testAddingProductToCart(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val isProductAdded = SearchResultsPage(driver)
                .openVacuumCleanerSearchPage()
                .isAddingToCartCorrect()

            assertTrue(isProductAdded, "Products are not correct after adding to cart")

        } finally {
            driver.quit()
        }
    }

    // Use-Case #7 - select category
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun testSelectingCategory(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val nameOfCategory = MainPage(driver)
                .openMainPage()
                .getNameOfCategory()

            val nameOfCategoryPage = CategoryFoundPage(driver)
                .getNameOfCategoryPage()

            assertEquals(nameOfCategory, nameOfCategoryPage, "Category name not found")

        } finally {
            driver.quit()
        }
    }

    // Use-Case #8 - product card check
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun testProductCard(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val nameOfCardOnMainPage = MainPage(driver)
                .openMainPage()
                .getNameOfCard()

            val nameOfCardOnCardPage = CardPage(driver)
                .getNameOfProductOnCardPage()

            assertEquals(nameOfCardOnMainPage, nameOfCardOnCardPage, "Card name not found")
        } finally {
            driver.quit()
        }
    }
}