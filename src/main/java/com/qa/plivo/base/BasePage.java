package com.qa.plivo.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.EmailException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @author Praveen
 */


public class BasePage {

    public final Logger logger = LogManager.getLogger(this.getClass().getSimpleName());
    ;

    private ScreenRecorder screenRecorder;
    public static ExtentHtmlReporter htmlReporter1;
    public static ExtentReports extent;
    public static ExtentTest test;
    private static final String OUTPUT_FOLDER = "./build/";
    private static final String FILE_NAME = "PlivoClearTripReport.html";
    File RecordedFile;


    public static WebDriver driver;
    public Actions action;
    public Properties prop;
    public WebDriverWait wait;
    public File f1;
    public FileInputStream file;
    public static ThreadLocal<ExtentTest> test1 = new ThreadLocal<ExtentTest>();

    public static ThreadLocal<WebDriver> tdriver = new ThreadLocal<WebDriver>();

    public WebDriver initialize_driver() {

        String browserName = prop.getProperty("browser");


        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().version("81.0").setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            driver = new ChromeDriver(options);

            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("ie")) {
            WebDriverManager.iedriver().setup();
            driver = new InternetExplorerDriver();
        }

        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        tdriver.set(driver);
        return getDriver();


    }

    /**
     * This method is use for
     * scrolling down the page.
     *
     * @Date 25/05/2020
     * @Revision History
     */
    public void scrollDown() {
        JavascriptExecutor jse;
        jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,250)", "");
    }

    public void scrollUp() {
        JavascriptExecutor jse;
        jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,-2000)", "");
    }


    public static synchronized WebDriver getDriver() {
        return tdriver.get();
    }

    /**
     * Read data from config file using Properties
     *
     * @throws Exception
     * @Date 25/05/2020
     * @Revision History
     */
    public Properties initialize_Properties() {
        //	action= new Actions(driver);
        prop = new Properties();
        try {
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/src/main/java/com/qa/plivo/config/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;
    }


    @BeforeSuite
    public void settingUp() throws Exception {

        logger.info("Recording started");

        htmlReporter1 = new ExtentHtmlReporter(System.getProperty("user.dir") + "/test-output/PlivoClearTripReport.html");

        extent = new ExtentReports();

        extent.attachReporter(htmlReporter1);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Host Name", System.getProperty("user.name"));
        extent.setSystemInfo("URL", "https://www.cleartrip.com/");


        htmlReporter1.config().setChartVisibilityOnOpen(true);
        htmlReporter1.config().setDocumentTitle("AutomationTesting.in Report");
        htmlReporter1.config().setReportName("Plivo Report");
        htmlReporter1.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter1.config().setTheme(Theme.DARK);


        test = extent.createTest(getClass().getSimpleName());


    }


    @BeforeTest
    public void loadProperties() {

        PropertyConfigurator.configure(System.getProperty("user.dir") + "//Config//log4j2.properties");
        Reporter.log("Reporter log started", true);

        Reporter.log("Properties have been initiated. Moving to next step", true);

    }

    public String captureScreenShot(ITestResult result, String status) throws IOException {
        String destDir = "";
        String passfailMethod = result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
        //To capture screenshot.
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
        //If status = fail then set folder name "screenshots/Failures"
        if (status.equalsIgnoreCase("fail")) {
            destDir = System.getProperty("user.dir") + "/screenshots/Failures";
        }
        //If status = pass then set folder name "screenshots/Success"
        else if (status.equalsIgnoreCase("pass")) {
            destDir = System.getProperty("user.dir") + "/screenshots/Success";
        }

        //To create folder to store screenshots
        new File(destDir).mkdirs();
        //Set file name with combination of test class name + date time.
        String destFile = passfailMethod + " - " + dateFormat.format(new Date()) + ".png";

        try {
            //Store file at destination folder location
            if (destDir != null)
                FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destDir + "/" + destFile;
    }

    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
        if (result.getStatus() == ITestResult.FAILURE) {
            String methodName = result.getMethod().getMethodName();
            Throwable errorMessage = result.getThrowable();
            String screenShot = captureScreenShot(result, "fail");
            test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
            test.fail(result.getThrowable());
            test.fail("Screen Shot : " +
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(result, "fail")).build());
            test.fail("Screen Shot : " + test.addScreenCaptureFromPath(screenShot));


        } else if (result.getStatus() == ITestResult.SUCCESS) {

            String screenShot = captureScreenShot(result, "pass");
            test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));

            test.pass("Screen Shot : " + MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(result, "pass")).build());

            test.pass("Screen Shot : " + test.addScreenCaptureFromPath(screenShot));
        } else {
            String methodName = result.getMethod().getMethodName();
            Throwable errorMessage = result.getThrowable();
            test.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.ORANGE));
            test.skip(result.getThrowable());

        }

    }


    @AfterSuite
    public void endTest() throws Exception {
        extent.flush();
        logger.info("Recording Completed");
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    public String tomorrow() {
        return currentDatePlus(1);
    }

    public String dayAfterTomorrow() {
        return currentDatePlus(2);
    }

    private String currentDatePlus(int numberOfDays) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, numberOfDays);
        return new SimpleDateFormat("dd/MM/yyyy").format(c.getTime());
    }

    public void toElement(WebElement element) throws IOException {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        infoLogStatus("Scrolling down to the element...");
    }

    public String getScreenshot() {
        File src = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshots/" + System.currentTimeMillis() + ".png";
        File destination = new File(path);
        try {
            FileUtils.copyFile(src, destination);
        } catch (IOException e) {
            System.out.println("Capture Failed " + e.getMessage());
        }
        return path;
    }


    /**
     * To create a log status for information regarding the test steps
     *
     * @param infomessage
     * @throws IOException
     */
    public void infoLogStatus(String infomessage) throws IOException {
        System.out.println(infomessage);
        logger.info(infomessage);
        test.info(infomessage, MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot()).build());

    }

    /**
     * To create a log status for information regarding the test steps
     *
     * @param passmessage
     * @throws IOException
     */
    public void passLogStatus(String passmessage) throws IOException {
        System.out.println(passmessage);
        logger.info(passmessage);
        test.pass(passmessage, MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot()).build());

    }

    /**
     * To create a log status for Failed/ Skipped cases
     *
     * @param failedmessage
     * @throws IOException
     */
    public void failedLogStatus(String failedmessage) throws IOException {
        System.out.println(failedmessage);
        logger.error(failedmessage);
        test.fail(failedmessage, MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot()).build());

    }


    // This is a generic method for waiting for an elelment to be visible

    public void waitForVisibility(WebElement locator, String element) throws IOException, EmailException {
        WebDriverWait wait = new WebDriverWait(driver, 20);

        infoLogStatus("Waiting for " + element + " to be located....");
        wait.until(ExpectedConditions.visibilityOf(locator));
        try {
            if (locator.isDisplayed()) {
                infoLogStatus("" + element + " located successfully and moving to the next step....");

            }
        } catch (Exception e) {
            failedLogStatus("Unable to find  " + element + " due to " + e.getMessage());
        }
    }


    // This is a generic method for waiting for an elelment to be clickable

    public void waitForClickable(WebElement locator) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        infoLogStatus("Waiting for an element to be located and clickable....");
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        infoLogStatus("Element located and clicked successfully and moving to the next step....");

    }


    // Method to click an element

    public void click(WebElement locator, String element) throws IOException, EmailException {
        try {
            if (locator.isDisplayed()) {
                infoLogStatus("Waiting for " + element + " to be clicked........");
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", locator);
                infoLogStatus("Clicked the expected " + element + " successfully........");
            }
        } catch (Exception e) {
            failedLogStatus("Unable to click on  " + element + " due to " + e.getMessage());

        }
    }


    // Method to fill an element

    public void fill(WebElement locator, String value, String element) throws IOException, EmailException {
        try {
            if (locator.isDisplayed()) {
                infoLogStatus("Waiting for " + element + " to be filled........");
                locator.sendKeys(value);
                infoLogStatus("Enetred the value successfully and moving to the next step........");
            }
        } catch (Exception e) {
            failedLogStatus("Unable to enter the value for  " + element + " due to " + e.getMessage());

        }
    }


    public void elementClickAndException(WebElement locator, String message) throws IOException {
        try {
            if (locator.isDisplayed()) {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", locator);
                passLogStatus(message);
            }
        } catch (Exception e) {
            failedLogStatus("Unable to click on edit icon ");
        }
    }

}
