package com.qa.plivo.testpages;

import com.qa.plivo.base.BasePage;
import com.qa.plivo.listener.ExtentReportListener;
import com.qa.plivo.pages.BookingPage;
import com.qa.plivo.pages.FlightsSearchPage;
import com.qa.plivo.pages.SearchResultsPage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.Properties;


@Listeners({ExtentReportListener.class})
public class BookingTwoWayFlightsTest extends BasePage {

    public final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());


    public BasePage basePage;
    public WebDriver driver;
    public Properties prop;
    public FlightsSearchPage flightsSearchPage;
    public SearchResultsPage searchresultTest;
    public BookingPage bookingPage;


    @BeforeClass // this method will be executed before every @test method
    public void setUp() {
        basePage = new BasePage();
        prop = basePage.initialize_Properties();
        driver = basePage.initialize_driver();
        driver.get(prop.getProperty("url"));
        flightsSearchPage = new FlightsSearchPage(driver);
        searchresultTest = new SearchResultsPage(driver);
        bookingPage = new BookingPage(driver);


    }


    @Test(priority = 1, description = "Verifying the booking for flight functionality")
    public void testThatResultsAppearForAReturnJourney() throws Exception {

        flightsSearchPage.searchForAReturnJourneyWith(prop.getProperty("origin"), prop.getProperty("destination"));
        searchresultTest.selectTheFlights();
        bookingPage.bookingPageDetails();
    }

    @Test(dependsOnMethods = {"testThatResultsAppearForAReturnJourney"}, description = "Total expenses for two way journey")
    public void totalExpensesForTwoWayJourneyReport() throws Exception {

        bookingPage.totalExpensesForTwoWayJourney();
    }
}



