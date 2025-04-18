package tests


import org.example.pages.MainPage
import org.example.pages.SearchResultsPage
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import kotlin.test.Test
import kotlin.test.assertTrue

class SearchTest {

    private val drivers: MutableList<WebDriver> = mutableListOf()
    private val browsers: List<String> = listOf("chrome", "firefox")

    @BeforeEach
    fun setup() {
        browsers.forEach { browser ->
            val driver = when (browser.lowercase()) {
                "chrome" -> ChromeDriver()
                "firefox" -> FirefoxDriver()
                else -> throw IllegalArgumentException("Browser $browser not supported")
            }
            driver.manage().window().maximize()
            drivers.add(driver)
        }
    }

    @AfterEach
    fun teardown() {
        drivers.forEach { it.quit() }
    }

    @Test
    fun testProductSearchShowsResults() {
        drivers.forEach { driver ->
            val mainPage = MainPage(driver)
            val resultsPage = SearchResultsPage(driver)

            mainPage.open()
            mainPage.searchFor("пылесос")

            assertTrue(resultsPage.hasResults())
            driver.quit()
        }
    }
}