package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class ComparePage(driver: WebDriver) : Page(driver) {

    private val productTitles = By.xpath("//div[@class='ddl_product_link']")

    fun openComparePage(): ComparePage {
        driver.get("https://megamarket.ru/compare/")
        return this
    }

    fun isVacuumCleanersOnPage(maxAttempts: Int = 2): Boolean {
        repeat(maxAttempts) { attempt ->
            try {
                val elements = waitForElements(productTitles)

                if (elements != null && elements.size == 2 &&
                    elements.all { it!!.text.contains("пылесос", ignoreCase = true) }) {
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