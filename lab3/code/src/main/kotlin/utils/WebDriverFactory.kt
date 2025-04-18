package org.example.utils

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

object WebDriverFactory {
    fun createDriver(browser: String): WebDriver {
        return when (browser.lowercase()) {
            "chrome" -> ChromeDriver()
            "firefox" -> FirefoxDriver()
            else -> throw IllegalArgumentException("Browser $browser not supported")
        }
    }
}