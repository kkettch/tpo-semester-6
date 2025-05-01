package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class CardPage(driver: WebDriver) : Page(driver) {

    private val titleOfProduct = By.xpath("//h1[@class='pdp-header__title pdp-header__title_only-title']")

    fun getNameOfProductOnCardPage(): String {
        val titleElement = waitForElement(titleOfProduct)
            .findElement(titleOfProduct)
            .text
            .trim()

        return titleElement
    }
}