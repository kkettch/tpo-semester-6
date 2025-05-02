package tests

import org.example.pages.CardPage
import org.example.pages.CategoryFoundPage
import org.example.pages.MainPage
import org.example.utils.Browser
import org.example.utils.WebDriverFactory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class SearchTest {

    // check if MainPage is Loaded
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun testHomePageOpened(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val hasResults = MainPage(driver)
                .openMainPage()
            assertTrue(hasResults.isPageLoaded(), "MainPage should be loaded")
        } finally {
            driver.quit()
        }
    }

    // Use-Case #1 - Search For Product
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun searchTest(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val keyword = "пылесос"

            val hasResults = MainPage(driver)
                .openMainPage()
            assertTrue(hasResults.isPageLoaded(), "MainPage should be loaded")

            val searchResultsPage = hasResults
                .searchFor(keyword)
            assertTrue(searchResultsPage.isPageLoaded(), "searchResultsPage should be loaded")

            val result = searchResultsPage
                .hasRelatedSearch(keyword)
            assertTrue(result, "Search results do not contain expected query: $keyword")
        } finally {
            driver.quit()
        }
    }

    // Use-Case #2 - check that both products are added for comparison
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun testAddToComparison(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val keyword = "пылесос"

            val mainPageResult = MainPage(driver)
                .openMainPage()
            assertTrue(mainPageResult.isPageLoaded(), "MainPage should be loaded")

            val vacuumCleanerSearchPage = mainPageResult
                .searchFor(keyword)
            assertTrue(vacuumCleanerSearchPage.isPageLoaded(), "Vacuum Cleaner search should be loaded")

            val comparePage = vacuumCleanerSearchPage
                .addTwoItemsToCompareFromSearchForVacuumCleaner()
            assertTrue(comparePage.isPageLoaded(), "Compare page should be loaded")

            val result = comparePage
                .isVacuumCleanersOnPage()
            assertTrue(result, "Search results are not on the page")
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
            val keyword = "пылесос"

            val mainPageResult = MainPage(driver)
                .openMainPage()
            assertTrue(mainPageResult.isPageLoaded(), "MainPage should be loaded")

            val vacuumCleanerSearchPage = mainPageResult.searchFor(keyword)
            assertTrue(vacuumCleanerSearchPage.isPageLoaded(), "Vacuum Cleaner search should be loaded")

            val result = vacuumCleanerSearchPage
                .isFilteredByPriceCheapFirst()
            assertTrue(result, "Objects are not sorted by cheaper first rule")
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
            val keyword = "пылесос"

            val mainPageResult = MainPage(driver)
                .openMainPage()
            assertTrue(mainPageResult.isPageLoaded(), "MainPage should be loaded")

            val vacuumCleanerSearchPage = mainPageResult.searchFor(keyword)
            assertTrue(vacuumCleanerSearchPage.isPageLoaded(), "Vacuum Cleaner search should be loaded")

            val result = vacuumCleanerSearchPage
                .isFilteredByPriceExpensiveFirst()
            assertTrue(result, "Objects are not sorted by expensive first rule")
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
            val mainPageResult = MainPage(driver)
                .openMainPage()
            assertTrue(mainPageResult.isPageLoaded(), "MainPage should be loaded")

            val result = mainPageResult
                .isAddingToFavouriteCorrect()
            assertTrue(result, "Products are not correct after adding to favourites")
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
            val keyword = "пылесос"

            val mainPageResult = MainPage(driver)
                .openMainPage()
            assertTrue(mainPageResult.isPageLoaded(), "MainPage should be loaded")

            val vacuumCleanerSearchPage = mainPageResult.searchFor(keyword)
            assertTrue(vacuumCleanerSearchPage.isPageLoaded(), "Vacuum Cleaner search should be loaded")

            val result = vacuumCleanerSearchPage
                .isAddingToCartCorrect()
            assertTrue(result, "Products are not correct after adding to cart")

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
            val mainPageResult = MainPage(driver)
                .openMainPage()
            assertTrue(mainPageResult.isPageLoaded(), "MainPage should be loaded")

            val nameOfCategory = mainPageResult
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
            val mainPageResult = MainPage(driver)
                .openMainPage()
            assertTrue(mainPageResult.isPageLoaded(), "MainPage should be loaded")

            val nameOfCardOnMainPage = mainPageResult
                .getNameOfCard()

            val nameOfCardOnCardPage = CardPage(driver)
                .getNameOfProductOnCardPage()
            assertEquals(nameOfCardOnMainPage, nameOfCardOnCardPage, "Card name not found")
        } finally {
            driver.quit()
        }
    }

    // Use-Case #9 - reviews sorted in ascending order
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun testReviewsInAscendingOrder(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val keyword = "защитная пленка samsung для galaxy a34 (ef-ua346ctegru) (2шт)"
            val mainPageResult = MainPage(driver)
                .openMainPage()
            assertTrue(mainPageResult.isPageLoaded(), "MainPage should be loaded")

            val searchResultsPage = mainPageResult
                .searchFor(keyword)
            assertTrue(searchResultsPage.isPageLoaded(), "searchResultsPage should be loaded")

            val cardPage = searchResultsPage
                .clickSecondCard()
            assertTrue(cardPage.isPageLoaded(), "cardPage should be loaded")

            val isSortedByAscendingOrder = cardPage
                .isSortedHighestRatedFirst()
            assertTrue(isSortedByAscendingOrder, "Reviews are not sorted by ascending order")
        } finally {
            driver.quit()
        }
    }

    // Use-Case #10 - reviews sorted in descending order
    @ParameterizedTest
    @EnumSource(Browser::class)
    fun testReviewsInDescendingOrder(browser: Browser) {
        val driver = WebDriverFactory.createDriver(browser)
        try {
            val keyword = "защитная пленка samsung для galaxy a34 (ef-ua346ctegru) (2шт)"
            val mainPageResult = MainPage(driver)
                .openMainPage()
            assertTrue(mainPageResult.isPageLoaded(), "MainPage should be loaded")

            val searchResultsPage = mainPageResult
                .searchFor(keyword)
            assertTrue(searchResultsPage.isPageLoaded(), "searchResultsPage should be loaded")

            val cardPage = searchResultsPage
                .clickSecondCard()
            assertTrue(cardPage.isPageLoaded(), "cardPage should be loaded")

            val isSortedByDescendingOrder = CardPage(driver)
                .isSortedLowestRatedFirst()
            assertTrue(isSortedByDescendingOrder, "Reviews are not sorted by descending order")
        } finally {
            driver.quit()
        }
    }
}