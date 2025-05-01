package tests


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
}