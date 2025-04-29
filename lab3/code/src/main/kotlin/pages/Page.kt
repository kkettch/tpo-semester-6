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

    // Метод для клика по элементу
    protected fun click(locator: By) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click()
    }

    // Метод для ввода текста в поле
    protected fun type(locator: By, text: String) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).sendKeys(text)
//        val inputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
//        inputElement.sendKeys(text)
    }

    // Метод для ожидания видимости элемента
    protected fun waitForElement(locator: By): WebElement {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
    }
}