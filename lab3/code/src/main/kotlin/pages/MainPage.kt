package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class MainPage(driver: WebDriver) : Page(driver) {

    private val favouriteContainer = By.xpath("//a[contains(@class, 'navigation-tabs__item') and contains(@href, '/personal/favorites')]")
    private val searchContainer = By.xpath("//div[contains(@class, 'search-tab navigation-tabs__item navigation-tabs__item_search')]")
    private val searchInput = By.xpath("//textarea[contains(@class, 'search-input__textarea search-input__textarea_header')]")
    private val searchButton = By.xpath("//button[contains(@class, 'pui-button-element pui-button-element_variant_primary pui-button-element_size_lg')]")
    private val addToFavouriteProduct = By.xpath("//div[@class='to-favorite-button product-card__favorite-button product-card__favorite-button_over']")
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

        val nameOnFavouritePage = waitForElement(productCardOnFavouritePage)
            .findElement(By.xpath(".//a[contains(@class, 'catalog-item-regular-desktop__title-link ddl_product_link')]"))
            .text

        return nameOnMainPage == nameOnFavouritePage
    }
}