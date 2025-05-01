package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class MainPage(driver: WebDriver) : Page(driver) {

    private val searchContainer = By.xpath("/html/body/div[2]/div[1]/div[3]/div/header/div[2]/div/div/div/div[2]/div[2]")
    private val searchInput = By.xpath("/html/body/div[2]/div[1]/div[3]/div/div[2]/div/div/form/div[1]/div/label/textarea")
    private val searchButton = By.xpath("/html/body/div[2]/div[1]/div[3]/div/div[2]/div/div/form/div[1]/div[2]/div[2]/button")

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
}