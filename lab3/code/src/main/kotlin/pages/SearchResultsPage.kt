package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class SearchResultsPage(private val driver: WebDriver) {
    private val wait = WebDriverWait(driver, Duration.ofSeconds(10))
    private val resultItems = By.xpath("//*[@id=\"app\"]/div[3]/div/main/div/div[1]/div[3]/div/div[2]/div/div[2]/div[2]/div[2]")

    fun hasResults(): Boolean {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(resultItems)).isDisplayed
    }
}