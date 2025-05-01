package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

abstract class Page(protected val driver: WebDriver) {

    protected val wait = WebDriverWait(driver, Duration.ofSeconds(10))

    protected fun click(locator: By) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator))
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click()
    }

    protected fun type(locator: By, text: String) {
        val inputElement = waitForElement(locator)
        inputElement.sendKeys(text)
    }

    protected fun waitForElement(locator: By): WebElement {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
    }
}