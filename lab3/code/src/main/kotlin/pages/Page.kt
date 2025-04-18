package org.example.pages

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

abstract class Page(protected val driver: WebDriver) {
    protected val wait: WebDriverWait = WebDriverWait(driver, Duration.ofSeconds(10))

    fun initPage(driver: WebDriver) {
        PageFactory.initElements(driver, this)
    }

    fun open(url: String){
        return driver.get(url)
    }

    fun sendText(text: String, element: WebElement){
        wait.until(ExpectedConditions.visibilityOf(element)).apply {
            clear()
            sendKeys(text)
        }
    }

    fun click(element: WebElement){
        wait.until(ExpectedConditions.elementToBeClickable(element)).click()
    }

    fun submitQuery(element: WebElement){
        wait.until(ExpectedConditions.elementToBeClickable(element)).submit()
    }

}