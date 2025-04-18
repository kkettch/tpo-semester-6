package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class SearchResultsPage(private val driver: WebDriver) {
    private val wait = WebDriverWait(driver, Duration.ofSeconds(10))

    fun hasResults(searchedString: String): Boolean {
        return try {
            val linkElement = wait.until {
                driver.findElement(
                    By.cssSelector("a[href*='related_search=$searchedString']")
                )
            }
            val href = linkElement.getDomAttribute("href")
            href?.contains("related_search=$searchedString") ?: false
        } catch (e: Exception) {
            false
        }
    }
}