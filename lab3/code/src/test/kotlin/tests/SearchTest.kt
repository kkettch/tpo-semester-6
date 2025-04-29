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

    @BeforeEach
    fun setup() {
        val chromeDriver = ChromeDriver().apply { manage().window().maximize() }
        val firefoxDriver = FirefoxDriver().apply { manage().window().maximize() }

        drivers.add(chromeDriver)
        drivers.add(firefoxDriver)
    }

    @AfterEach
    fun teardown() {
        drivers.forEach { it.quit() }
    }

    @Test
    fun testProductSearchShowsResults() {
        val keyword = "пылесос"
        drivers.forEach { driver ->
            val hasResults = MainPage(driver)
                .open()
                .searchFor(keyword)
                .hasRelatedSearch(keyword)

            assertTrue(hasResults, "Search results do not contain expected query: $keyword")
        }
    }
}