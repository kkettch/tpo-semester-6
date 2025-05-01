package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
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

    protected fun waitForElements(locator: By): List<WebElement?>? {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator))
    }

    protected fun hoverOverElement(locator: By) {
        val element = wait.until {
            driver.findElement(locator)
        }
        Actions(driver).moveToElement(element).perform()
    }

    fun hoverOverWebElement(element: WebElement) {
        Actions(driver).moveToElement(element).perform()
    }

}