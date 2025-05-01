package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class CategoryFoundPage(driver: WebDriver) : Page(driver) {

    private val titleOfPage = By.xpath("//h1[@class='catalog-header__title']")

    fun openCategoryPage(): CategoryFoundPage {
        driver.get("https://megamarket.ru/catalog/dacha-sezonnye-tovary/")
        return this
    }

    fun getNameOfCategoryPage(): String {
        val titleElement = waitForElement(titleOfPage)
            .findElement(titleOfPage)
            .text
            .trim()

        return titleElement
    }
}