package com.qa.plivo.pages;


import com.google.common.base.Function;
import com.qa.plivo.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class FlightsSearchPage extends BasePage {

    public WebDriverWait wait;


    @FindBy(xpath = "//*[text()='Round trip']")
    private WebElement returnTripJourneySelection;

    @FindBy(id = "DepartDate")
    private WebElement departureDateField;

    @FindBy(id = "ReturnDate")
    private WebElement returnDateField;

    @FindBy(id = "FromTag")
    private WebElement originField;

    @FindBy(id = "ToTag")
    private WebElement destinationField;

    @FindBy(id = "SearchBtn")
    private WebElement searchButton;

    @FindBy(id = "ui-id-1")
    private WebElement originAuto;

    @FindBy(id = "ui-id-2")
    private WebElement destinationAuto;


    public FlightsSearchPage(WebDriver driver) {
        BasePage.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void enterDepartureDateAs(String date) {
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(departureDateField));
        departureDateField.clear();
        departureDateField.sendKeys(date);
        System.out.println("Selected the departure date and moving to next step");
    }

    public void enterReturnDateAs(String date) {
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(returnDateField));
        returnDateField.clear();
        returnDateField.sendKeys(date, Keys.TAB);
        System.out.println("Selected the origin date and moving to next step");

    }


    public void enterDestinationAs(String destination) {
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(destinationField));
        destinationField.clear();
        destinationField.sendKeys(destination);
        System.out.println("Filled the destination and moving to next step");

    }


    public void enterOriginAs(String origin) {
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(originField));
        originField.clear();
        originField.sendKeys(origin);
        System.out.println("Filled the origin and moving to next step");

    }


    public void chooseToHaveAReturnJourney() {

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(returnTripJourneySelection));
        returnTripJourneySelection.click();

    }


    public void selectTheFirstAvailableAutoCompleteOption(WebElement locator) throws InterruptedException {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement optionListElement = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return locator;
            }
        });

        //select the first item from the auto complete list
        WebElement originOptionsElement = optionListElement;
        Thread.sleep(5000);
        List<WebElement> originOptions = originOptionsElement.findElements(By.tagName("li"));
        originOptions.get(0).click();
    }

    public void searchForTheJourney() {
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(searchButton));
        searchButton.click();
        System.out.println("Clicked on search button and waiting for the search result");
    }


    public void searchForAReturnJourneyWith(String origin, String destination) throws InterruptedException {
        //  wait= new WebDriverWait(driver,15);
        chooseToHaveAReturnJourney();

        enterOriginAs(origin);
        selectTheFirstAvailableAutoCompleteOption(originAuto);

        enterDestinationAs(destination);
        selectTheFirstAvailableAutoCompleteOption(destinationAuto);

        enterDepartureDateAs(after60Days());
        enterReturnDateAs(after61Days());
        searchForTheJourney();
    }


    public String after60Days() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 60);
        return new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
    }

    public String after61Days() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 61);
        return new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
    }


}

