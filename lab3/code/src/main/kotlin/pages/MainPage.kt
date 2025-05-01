package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class MainPage(driver: WebDriver) : Page(driver) {

    private val favouriteContainer = By.xpath("/html/body/div[2]/div[1]/div[3]/div/header/div[2]/div/div/div/div[2]/a[2]")
    private val searchContainer = By.xpath("/html/body/div[2]/div[1]/div[3]/div/header/div[2]/div/div/div/div[2]/div[2]")
    private val searchInput = By.xpath("/html/body/div[2]/div[1]/div[3]/div/div[2]/div/div/form/div[1]/div/label/textarea")
    private val searchButton = By.xpath("/html/body/div[2]/div[1]/div[3]/div/div[2]/div/div/form/div[1]/div[2]/div[2]/button")
    private val addToFavouriteProduct = By.xpath("/html/body/div[2]/div[1]/div[3]/div/main/div/section[3]/div/div[2]/div[1]/article[1]/article/div[4]")
    private val productCardOnMainPage = By.xpath("//article[contains(@class, 'product-card product-card_s')][1]")
    private val productCardOnFavouritePage = By.xpath("//div[contains(@class, 'catalog-item-regular-desktop personal-listing-items__item ddl_product catalog-item-desktop')][1]")

    fun openMainPage(): MainPage {
        driver.get("https://megamarket.ru")
        return this
    }

    fun searchFor(keyword: String): SearchResultsPage {
        click(searchContainer)
        type(searchInput, keyword)
        click(searchButton)
        return SearchResultsPage(driver)
    }

    fun isAddingToFavouriteCorrect(): Boolean {
        hoverOverElement(productCardOnMainPage)
        val nameOnMainPage = waitForElement(productCardOnMainPage)
            .findElement(By.xpath(".//a[contains(@class, 'product-card__title product-card__title_show')]"))
            .text

        click(addToFavouriteProduct)
        click(favouriteContainer)

        val nameOnFavouritePage = wait.until {
            driver.findElement(productCardOnFavouritePage)
                .findElement(By.xpath(".//a[contains(@class, 'catalog-item-regular-desktop__title-link ddl_product_link')]"))
                .text
                .trim()
        }

        return nameOnMainPage == nameOnFavouritePage
    }

}