package com.qa.plivo.pages;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.qa.plivo.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Properties;

public class BookingPage extends BasePage {

    public WebDriverWait wait;
  //  public Properties prop;
    public BasePage basePage;

    public String total_Fare;



    @FindBy(xpath="(//*[@value='Continue to payment'])[1]")
    private WebElement BookingPageDisplayIdentifier;

    @FindBy(id="itineraryBtn")
    private WebElement continueToPaymentBtn;

    @FindBy(id="username")
    private WebElement emailAddress;

    @FindBy(id="LoginContinueBtn_1")
    private WebElement continueBtnOnEmailPage;

    @FindBy(id="AdultTitle1")
    private WebElement adultTitle;

    @FindBy(id="AdultFname1")
    private WebElement adultFName;

    @FindBy(id="AdultLname1")
    private WebElement adultLName;

    @FindBy(id="ChildTitle1")
    private WebElement childTitle;

    @FindBy(id="ChildFname1")
    private WebElement childFName;

    @FindBy(id="ChildLname1")
    private WebElement childLName;

    @FindBy(id="ChildDobDay1")
    private WebElement childDOBDay;

    @FindBy(id="ChildDobMonth1")
    private WebElement childDOBMonth;

    @FindBy(id="ChildDobYear1")
    private WebElement getChildDOBYear;

    @FindBy(id="mobileNumber")
    private WebElement mobileNumber;

    @FindBy(id="travellerBtn")
    private WebElement paymentBtn;

    @FindBy(xpath="(//span[@id='counter'])[7]")
    private WebElement totalFare;


    public BookingPage(WebDriver driver) {
        BasePage.driver = driver;
        PageFactory.initElements(driver, this);
        initialize_Properties();

    }

    public void selectClass(WebElement element){
        Select select= new Select(element);
        select.selectByIndex(1);
    }

    public void identifierOfBookingPage() {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(BookingPageDisplayIdentifier));
            if (BookingPageDisplayIdentifier.isDisplayed()) {
                System.out.println("We are on the booking page and moving to next step");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickOnContinueToPaymentButton() {
        Actions action = new Actions(driver);
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(continueToPaymentBtn));
            if (continueToPaymentBtn.isDisplayed()) {
                action.click(continueToPaymentBtn).build().perform();
                System.out.println("Clicked on continue to payment button and moving to next step");

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void fillTravellerEmailAddress() {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(emailAddress));
            if (emailAddress.isDisplayed()) {
                emailAddress.clear();
                emailAddress.sendKeys(prop.getProperty("emailAddress"));
                waitForVisibility(continueBtnOnEmailPage,"Continue button");
                click(continueBtnOnEmailPage, "Continue button");
                System.out.println("Filled the mobile number and moving to next step");
                test.pass("Filled the mobile number", MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot()).build());

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }







    /**
     * This method is to select the title of the travellers
     * @param element
     */
    public void fillTravellerTitle(WebElement element) {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            if (element.isDisplayed()) {
                selectClass(element);
                System.out.println("Filled the Adult title and moving to next step");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This method is to fill the details of the travellers
     * like First name and Last name
     * @param adultFirstName
     * @param adultLastName
     */

    public void fillTravellerDetails(WebElement firstName, WebElement lastName, String adultFirstName, String adultLastName){
        try {
            wait.until(ExpectedConditions.visibilityOf(firstName));
            if (firstName.isDisplayed()) {
                firstName.clear();
                firstName.sendKeys(adultFirstName);
                System.out.println("Filled the Adult first name and moving to next step");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            wait.until(ExpectedConditions.visibilityOf(lastName));
            if (lastName.isDisplayed()) {
                lastName.clear();
                lastName.sendKeys(adultLastName);
                System.out.println("Filled the Adult last name and moving to next step");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    public void fillChildDOBDetail(WebElement dobDayAndMonth) {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(dobDayAndMonth));
            if (dobDayAndMonth.isDisplayed()) {
                selectClass(dobDayAndMonth);
                System.out.println("Selected the Day of DOB of child and moving to next step");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void fillChildDOBDetail() {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(getChildDOBYear));
            if (getChildDOBYear.isDisplayed()) {
               Select select= new Select(getChildDOBYear);
               select.selectByValue("2010");
                System.out.println("Selected the Day of DOB of child and moving to next step");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void fillMobileNumber() {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(mobileNumber));
            if (mobileNumber.isDisplayed()) {
                mobileNumber.clear();
                mobileNumber.sendKeys(prop.getProperty("mobile"));
                System.out.println("Filled the mobile number and moving to next step");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void clickOnPaymentButton() {
        wait = new WebDriverWait(driver, 15);
        try {
            wait.until(ExpectedConditions.visibilityOf(paymentBtn));
            if (paymentBtn.isDisplayed()) {
                paymentBtn.click();
                System.out.println("Clicked on continue to Payment button and moving to next step");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void getTotalFare(){
        wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOf(totalFare));
        total_Fare= totalFare.getText().replace(",","");
        System.out.println(total_Fare);
    }



    /**
     * This method is to consolidate the events of the booking page details
     *
     */

    public void bookingPageDetails() throws InterruptedException {
        identifierOfBookingPage();
        clickOnContinueToPaymentButton();
        fillTravellerEmailAddress();
        Thread.sleep(2000);
        fillTravellerTitle(adultTitle);
        fillTravellerDetails(adultFName, adultLName, prop.getProperty("firstName"), prop.getProperty("lastName"));
        fillTravellerTitle(childTitle);
        fillTravellerDetails(childFName,childLName,  prop.getProperty("childFirstName"), prop.getProperty("childLastName"));
        fillChildDOBDetail(childDOBDay);
        fillChildDOBDetail(childDOBMonth);
        fillChildDOBDetail();
        fillMobileNumber();
        clickOnPaymentButton();
        getTotalFare();
    }






}
