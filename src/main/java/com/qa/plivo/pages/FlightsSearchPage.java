package com.qa.plivo.pages;


import com.google.common.base.Function;
import com.qa.plivo.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

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

    @FindBy(id = "Childrens")
    private WebElement childern;


    public FlightsSearchPage(WebDriver driver) {
        BasePage.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void enterDepartureDateAs(String date) {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(departureDateField));
            if (departureDateField.isDisplayed()) {
                departureDateField.clear();
                departureDateField.sendKeys(date);
                System.out.println("Selected the departure date and moving to next step");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void enterReturnDateAs(String date) {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(returnDateField));
            if (returnDateField.isDisplayed()) {
                returnDateField.clear();
                returnDateField.sendKeys(date, Keys.TAB);
                System.out.println("Selected the origin date and moving to next step");

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void enterDestinationAs(String destination) {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(destinationField));
            if (destinationField.isDisplayed()) {
                destinationField.clear();
                destinationField.sendKeys(destination);
                System.out.println("Filled the destination and moving to next step");

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void enterOriginAs(String origin) {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(originField));
            if (originField.isDisplayed()) {
                originField.clear();
                originField.sendKeys(origin);
                System.out.println("Filled the origin and moving to next step");

            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    public void chooseToHaveAReturnJourney() {

        WebDriverWait wait = new WebDriverWait(driver, 15);

        try {
            wait.until(ExpectedConditions.visibilityOf(returnTripJourneySelection));
            if (returnTripJourneySelection.isDisplayed()) {
                returnTripJourneySelection.click();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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

    public void noOfChildren(WebElement childern) {
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(childern));
        Select select = new Select(childern);
        select.selectByIndex(1);
        System.out.println("Selected number of childern as 1 and moving to next step");

    }


    public SearchResultsPage searchForTheJourney() {
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(searchButton));
        searchButton.click();
        System.out.println("Clicked on search button and waiting for the search result");
        return new SearchResultsPage(driver);
    }


    public SearchResultsPage searchForAReturnJourneyWith(String origin, String destination) throws InterruptedException {
        chooseToHaveAReturnJourney();

        enterOriginAs(origin);
        selectTheFirstAvailableAutoCompleteOption(originAuto);

        enterDestinationAs(destination);
        selectTheFirstAvailableAutoCompleteOption(destinationAuto);

        enterDepartureDateAs(after60Days());
        enterReturnDateAs(after61Days());

        noOfChildren(childern);
        SearchResultsPage searchResultsPage = this.searchForTheJourney();
        return searchResultsPage;
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

