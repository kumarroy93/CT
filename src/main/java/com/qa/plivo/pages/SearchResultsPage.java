package com.qa.plivo.pages;

import com.qa.plivo.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class SearchResultsPage extends BasePage {

    public WebDriverWait wait;


    @FindAll({
            @FindBy(xpath = "(//*[@class='totalAmount'])[6]"),
            @FindBy(xpath = "//form[@id='flightForm']/section[2]/div[3]/div/h2")
    })
    private WebElement searchResultDisplayedIdentifier;

    @FindBy(xpath = "(//a[contains(text(),'Depart')])[2]")
    private WebElement returnDepartureBtn;

    @FindBy(xpath = "(//button[@type='submit'])[4]")
    private WebElement bookBtn;


    public SearchResultsPage(WebDriver driver) {
        BasePage.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void identifier() {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(searchResultDisplayedIdentifier));
            if (searchResultDisplayedIdentifier.isDisplayed()) {
                System.out.println("We are on the search result page and moving to next step");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sortReturnDepartureByLast() {
        Actions action = new Actions(driver);
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(returnDepartureBtn));
            if (returnDepartureBtn.isDisplayed()) {
                action.doubleClick(returnDepartureBtn).build().perform();
                System.out.println("Clicked on return departure to sort in descending order and moving to next step");

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public BookingPage goToBooking() {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(bookBtn));
            if (bookBtn.isDisplayed()) {
                bookBtn.click();
                System.out.println("Clicked on search button and waiting for the booking page ");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return new BookingPage(driver);

    }

    public BookingPage selectTheFlights() throws InterruptedException {

        identifier();
        sortReturnDepartureByLast();

        BookingPage bookingPage = this.goToBooking();
        return bookingPage;
    }


}
