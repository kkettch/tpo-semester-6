package org.example.utils

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

object WebDriverFactory {
    fun createDriver(browser: Browser): WebDriver {
        return when (browser) {
            Browser.CHROME -> {
                val options = ChromeOptions()
                options.addArguments("--start-maximized") // максимум
                ChromeDriver(options)
            }
            Browser.FIREFOX -> {
                val options = FirefoxOptions()
                val driver = FirefoxDriver(options)
                driver.manage().window().maximize() // Firefox не имеет --start-maximized
                driver
            }
        }
    }
}