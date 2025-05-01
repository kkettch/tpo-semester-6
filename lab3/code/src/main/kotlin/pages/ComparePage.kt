package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class ComparePage(driver: WebDriver) : Page(driver) {

    private val productTitle1 = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div/div[4]/div/div/div/div/div[1]/div[4]/div[1]/div[2]/a[1]/div")
    private val productTitle2 = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div/div[4]/div/div/div/div/div[2]/div[3]/div[1]/div[2]/a[1]/div")

    fun openComparePage(): ComparePage {
        driver.get("https://megamarket.ru/compare/")
        return this
    }

    fun isVacuumCleanersOnPage(maxAttempts: Int = 2): Boolean {
        repeat(maxAttempts) { attempt ->
            try {
                val element1 = waitForElement(productTitle1)
                val element2 = waitForElement(productTitle2)

                if (element1.text.contains("пылесос", ignoreCase = true) &&
                    element2.text.contains("пылесос", ignoreCase = true)) {
                    return true
                }
            } catch (e: Exception) {
                if (attempt == maxAttempts - 1) return false
            }
            driver.navigate().refresh()
        }
        return false
    }
}