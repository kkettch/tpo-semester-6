package org.example

import org.example.pages.CardPage
import org.example.utils.Browser
import org.example.utils.WebDriverFactory

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val driver = WebDriverFactory.createDriver(Browser.FIREFOX)
    print(CardPage(driver)
        .openCardPage()
        .isSortedLowestRatedFirst()
    )
}