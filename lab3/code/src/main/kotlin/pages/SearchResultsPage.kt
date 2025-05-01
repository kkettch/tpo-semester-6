package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class SearchResultsPage(driver: WebDriver) : Page(driver) {

    private val addToComparisonButton1 = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div[1]/div[3]/div/div[2]/div/div[2]/div[2]/div[2]/div/div/div[1]/div[1]/div[2]/div[2]")
    private val addToComparisonButton2 = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div[1]/div[3]/div/div[2]/div/div[2]/div[2]/div[2]/div/div/div[1]/div[2]/div[2]/div[2]")
    private val openFilterChoice = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div[1]/div[3]/div/div[2]/div/div[2]/div[2]/div[1]/div/div[1]/div")
    private val cheapFirstButton = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div[1]/div[3]/div/div[2]/div/div[2]/div[2]/div[1]/div/div[1]/div/div[2]/ul/li[2]")
    private val expensiveFirstButton = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div[1]/div[3]/div/div[2]/div/div[2]/div[2]/div[1]/div/div[1]/div/div[2]/ul/li[3]")
    private val firstPrice = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div[1]/div[3]/div/div[2]/div/div[2]/div[2]/div[2]/div/div/div[1]/div[1]/div[4]/div[1]/div")
    private val secondPrice = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div[1]/div[3]/div/div[2]/div/div[2]/div[2]/div[2]/div/div/div[1]/div[2]/div[4]/div/div")
    private val thirdPrice = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/div[1]/div[3]/div/div[2]/div/div[2]/div[2]/div[2]/div/div/div[1]/div[3]/div[4]/div/div")

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
        click(addToComparisonButton1)
        click(addToComparisonButton2)
        return ComparePage(driver)
    }

    fun isFilteredByPriceCheapFirst() : Boolean {
        val initialPrices = getCurrentPrices()

        click(openFilterChoice)
        click(cheapFirstButton)

        waitForPricesToChange(initialPrices)
        val sortedPrices = getCurrentPrices()

        return isSortedAscending(sortedPrices)
    }

    fun isFilteredByPriceExpensiveFirst() : Boolean {
        val initialPrices = getCurrentPrices()

        click(openFilterChoice)
        click(expensiveFirstButton)

        waitForPricesToChange(initialPrices)
        val sortedPrices = getCurrentPrices()

        return isSortedDescending(sortedPrices)
    }

    private fun isSortedAscending(prices: List<Int>): Boolean {
        return prices.zipWithNext().all { (a, b) -> a <= b }
    }

    private fun isSortedDescending(prices: List<Int>): Boolean {
        return prices.zipWithNext().all { (a, b) -> a >= b }
    }

    private fun waitForPricesToChange(initialPrices: List<Int>) {
        wait.until {
            val currentPrices = getCurrentPrices()
            currentPrices != initialPrices &&
                    currentPrices.all { it > 0 }
        }
    }

    private fun getCurrentPrices(): List<Int> {
        return listOf(
            getPriceValue(firstPrice),
            getPriceValue(secondPrice),
            getPriceValue(thirdPrice)
        )
    }

    fun getPriceValue(priceElementLocator: By): Int {
        val priceElement = waitForElement(priceElementLocator)
        val priceText = priceElement.text

        return parsePriceText(priceText)
    }

    private fun parsePriceText(priceText: String): Int {
        val cleanPrice = priceText.replace("[^0-9]".toRegex(), "")

        return cleanPrice.toIntOrNull() ?: throw IllegalArgumentException(
            "Не удалось извлечь цену из текста: '$priceText'"
        )
    }
}