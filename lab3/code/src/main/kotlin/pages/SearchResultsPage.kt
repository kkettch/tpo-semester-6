package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class SearchResultsPage(driver: WebDriver) : Page(driver) {

    private val button1 = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div[1]/div[3]/div/div[2]/div/div[2]/div[2]/div[2]/div/div/div[1]/div[1]/div[2]/div[2]")
    private val button2 = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div[1]/div[3]/div/div[2]/div/div[2]/div[2]/div[2]/div/div/div[1]/div[2]/div[2]/div[2]")

    fun openVacuumCleanerSearchPage(): SearchResultsPage {
        driver.get("https://megamarket.ru/catalog/pylesosy/#?related_search=пылесос")
        return this
    }

    fun hasRelatedSearch(searchedString: String): Boolean {
        return try {
            val xpath = """
            //a[
                contains(@class, 'ddl_product_link') and 
                contains(@href, 'related_search=$searchedString')
            ]
        """.trimIndent()
            val linkElement = wait.until {
                driver.findElement(By.xpath(xpath))
            }
            linkElement.getDomAttribute("href")?.contains("related_search=$searchedString") ?: false
        } catch (e: Exception) {
            false
        }
    }

    fun addTwoItemsToCompareFromSearchForVacuumCleaner(): ComparePage {
        click(button1)
        click(button2)
        return ComparePage(driver)
    }
}