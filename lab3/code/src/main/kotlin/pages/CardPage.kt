package org.example.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class CardPage(driver: WebDriver) : Page(driver) {

    private val titleOfProduct = By.xpath("//h1[@class='pdp-header__title pdp-header__title_only-title']")
    private val openRatingButton = By.xpath("//a[contains(@class, 'tab-selector-link') and contains(@href, 'reviews')]")
    private val changeSortingFilterButton = By.xpath("//div[@class='select field xs']")
    private val sortButtonHighestRatedFirst = By.xpath("//li[contains(@class, 'option') and contains(text(), 'С высоким рейтингом')]")
    private val sortButtonLowestRatedFirst = By.xpath("//li[contains(@class, 'option') and contains(text(), 'С низким рейтингом')]")
    private val listOfRatings = By.xpath("//div[@class='pui-rating-display pui-rating-display_size_sm pui-rating-display_variant_primary pui-rating-display_box-type_wide']")

    fun openCardPage(): CardPage {
        driver.get("https://megamarket.ru/catalog/details/zashitnaya-plenka-samsung-dlya-samsung-galaxy-a34-ef-ua346ctegru-2sht-prozrachnaya-100057161839_9110/")
        return this
    }

    fun getNameOfProductOnCardPage(): String {
        val titleElement = waitForElement(titleOfProduct)
            .findElement(titleOfProduct)
            .text
            .trim()

        return titleElement
    }

    fun isSortedHighestRatedFirst(): Boolean {
        click(openRatingButton)
        repeat(2) { attempt ->
            click(changeSortingFilterButton)
            click(sortButtonHighestRatedFirst)
        }

        val listOfRating = getListOfRating(listOfRatings)
        return listOfRating == listOfRating.sortedDescending()
    }

    fun isSortedLowestRatedFirst(): Boolean {
        click(openRatingButton)
        repeat(2) { attempt ->
            click(changeSortingFilterButton)
            click(sortButtonLowestRatedFirst)
        }

        val listOfRating = getListOfRating(listOfRatings)
        return listOfRating != listOfRating.sortedDescending()
    }


    fun getListOfRating(listOfRatings: By): List<Int> {
        val listOfStars = waitForElements(listOfRatings)
        val selectedStarsCounts = listOfStars!!
            .take(listOfStars.size - 5)
            .map { starElement ->
            val selectedStars = starElement!!.findElements(
                By.cssSelector(".pui-rating-display__item.pui-rating-item.pui-rating-item_variant_primary.pui-rating-item_selected")
            )
            selectedStars.size
        }

        return selectedStarsCounts
    }
}