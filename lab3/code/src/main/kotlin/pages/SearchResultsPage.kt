package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver

class SearchResultsPage(driver: WebDriver) : Page(driver) {

    private val addToComparisonButtons = By.xpath("//div[contains(@class, 'catalog-item-buttons__button catalog-item-buttons__button-compare')]")
    private val openFilterChoice = By.xpath("//div[contains(@class, 'select field sm')]")
    private val cheapFirstButton = By.xpath("//li[contains(@class, 'option') and contains(text(), 'Сначала дешевле')]")
    private val expensiveFirstButton = By.xpath("//li[contains(@class, 'option') and contains(text(), 'Сначала дороже')]")
    private val prices = By.xpath("//div[contains(@class, 'catalog-item-regular-desktop__price') and contains(@data-test, 'product-price')]")
    private val productTitleOnSearchPage = By.xpath("//a[contains(@class, 'catalog-item-regular-desktop__title-link ddl_product_link')]")
    private val addToCartButton = By.xpath("//button[contains(@class, 'catalog-buy-button__button btn sm')][1]")
    private val openCartButton = By.xpath("//div[contains(@class, 'multicart-tab navigation-tabs__multicart-button')]")
    private val productTitleOnCartPage = By.xpath("//span[contains(@class, 'title')]")
    private val goToComparePageButton = By.xpath("//div[contains(@class, 'btn') and contains(text(), 'Сравнить')]")
    private val secondCard = By.xpath("//div[@class='catalog-item-regular-desktop ddl_product catalog-item-desktop'][2]")

    fun openVacuumCleanerSearchPage(): SearchResultsPage {
        driver.get("https://megamarket.ru/catalog/pylesosy/#?related_search=пылесос")
        return this
    }

    fun isPageLoaded(): Boolean {
        return isElementDisplayed(addToCartButton)
                && isElementDisplayed(addToComparisonButtons)
    }

    fun clickSecondCard(): CardPage {
        click(secondCard)
        return CardPage(driver)
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
        val buttons = waitForElements(addToComparisonButtons)

        buttons?.take(2)
            ?.forEach {
            it!!.click()
        }
        click(goToComparePageButton)

        val originalWindow = driver.windowHandle
        val allWindows = driver.windowHandles

        for (window in allWindows) {
            if (window != originalWindow) {
                driver.switchTo().window(window)
                break
            }
        }

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
            try {
                val currentPrices = getCurrentPrices()
                currentPrices != initialPrices && currentPrices.size >= 4 && currentPrices.all { it > 0 }
            } catch (e: StaleElementReferenceException) {
                false
            }
        }
    }

    private fun getCurrentPrices(): List<Int> {
        return waitForElements(prices)!!
            .take(4)
            .mapNotNull { element ->
                try {
                    parsePriceText(element!!.text)
                } catch (e: Exception) {
                    null
                }
            }
    }

    private fun parsePriceText(priceText: String): Int {
        val cleanPrice = priceText.replace("[^0-9]".toRegex(), "")
        return cleanPrice.toIntOrNull() ?: throw IllegalArgumentException(
            "Не удалось извлечь цену из текста: '$priceText'"
        )
    }

    fun isAddingToCartCorrect(): Boolean {
        val nameOnMainPage = waitForElement(productTitleOnSearchPage)
            .findElement(productTitleOnSearchPage)
            .text

        click(addToCartButton)
        click(openCartButton)

        val nameOnCartPage = waitForElement(productTitleOnCartPage)
            .findElement(productTitleOnCartPage)
            .text

        return nameOnMainPage == nameOnCartPage
    }
}