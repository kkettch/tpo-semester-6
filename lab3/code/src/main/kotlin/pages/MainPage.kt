package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration

class MainPage(private val driver: WebDriver) {
    private val wait = WebDriverWait(driver, Duration.ofSeconds(10))
    private val searchContainer = By.xpath("//*[@id=\"app\"]/div[3]/div/header/div[2]/div/div/div/div[2]/div[2]")
    private val searchInput = By.xpath("//*[@id=\"app\"]/div[3]/div/div[2]/div/div/form/div[1]/div/label/textarea")
    private val searchButton = By.xpath("//*[@id=\"app\"]/div[3]/div/div[2]/div/div/form/div[1]/div[2]/div[2]/button")

    fun open() {
        driver.get("https://megamarket.ru")
    }

    fun searchFor(keyword: String) {
        // Шаг 1: Кликаем на контейнер поиска
        wait.until(ExpectedConditions.elementToBeClickable(searchContainer)).click()

        // Шаг 2: Ждём появления input и вводим текст
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInput)).sendKeys(keyword)

        // Шаг 3: Нажимаем кнопку "Поиск"
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click()
    }
}