package com.qa.plivo.testpages;

import com.qa.plivo.base.BasePage;
import com.qa.plivo.listner.ExtentReportListener;
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
public class SearchPageTest extends BasePage {

    public final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());


    public BasePage basePage;
    public WebDriver driver;
    public Properties prop;
    public FlightsSearchPage flightsSearchPage;
    public SearchResultsPage searchresultTest;


    @BeforeClass // this method will be executed before every @test method
    public void setUp() {
        basePage = new BasePage();
        prop = basePage.initialize_Properties();
        driver = basePage.initialize_driver();
        driver.get(prop.getProperty("url"));
        flightsSearchPage = new FlightsSearchPage(driver);
        searchresultTest = new SearchResultsPage(driver);


    }


    @Test(priority = 1, description = "Verifying the searching for flight functinality")
    public void testThatResultsAppearForAReturnJourney() throws Exception {

        flightsSearchPage.searchForAReturnJourneyWith(prop.getProperty("origin"), prop.getProperty("destination"));
        searchresultTest.selectTheFlights();


    }
}



