package com.qa.plivo.listner;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.qa.plivo.base.BasePage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.IClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;


/**
 * @author Praveen
 */

public class ExtentReportListener extends BasePage implements ITestListener {


    public ExtentHtmlReporter htmlReporter;
    Logger log = Logger.getLogger(ExtentReportListener.class);

    private static final String OUTPUT_FOLDER = "./build/";
    private static final String FILE_NAME = "PlivoClearTripReport.html";

    private static ExtentReports extent = init();
    private static ExtentTest test2;
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

    private static ExtentReports init() {

        Path path = Paths.get(OUTPUT_FOLDER);
        // if directory exists?
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                // fail to create directory
                e.printStackTrace();
            }
        }
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER + FILE_NAME);


        htmlReporter.config().setDocumentTitle("Automation Test Results");
        htmlReporter.config().setReportName("Automation Test Results");
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();

        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Host Name", System.getProperty("user.name"));
        extent.setSystemInfo("URL", "https://www.cleartrip.com/");


        extent.attachReporter(htmlReporter);
        extent.setReportUsesManualConfiguration(true);

        return extent;
    }

    public synchronized void onStart(ITestContext context) {
        System.out.println("Test Suite started!");
        log("Test Suite Started....");


    }

    public synchronized void onFinish(ITestContext context) {
        System.out.println(("Test Suite is ending!"));
        extent.flush();
        test.remove();

    }

    public synchronized void onTestStart(ITestResult result) {

        String methodName = result.getMethod().getMethodName();
        String qualifiedName = result.getMethod().getQualifiedName();
        int last = qualifiedName.lastIndexOf(".");
        int mid = qualifiedName.substring(0, last).lastIndexOf(".");
        String className = qualifiedName.substring(mid + 1, last);

        System.out.println(methodName + " started!");
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName(),
                result.getMethod().getDescription());

        extentTest.assignCategory(result.getTestContext().getSuite().getName());
        /*
         * methodName = StringUtils.capitalize(StringUtils.join(StringUtils.
         * splitByCharacterTypeCamelCase(methodName), StringUtils.SPACE));
         */
        extentTest.assignCategory(className);
        test.set(extentTest);
        test.get().getModel().setStartTime(getTime(result.getStartMillis()));

        log("Test '" + result.getName() + "' Started");


    }


    public synchronized void onTestSuccess(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " passed!"));

        try {
            test.get().pass(result.getMethod().getMethodName() + " passed!",
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(result, "pass")).build());

            test.get().log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    public synchronized void onTestFailure(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " failed!"));
        String methodName = result.getMethod().getMethodName();
        Throwable errorMessage = result.getThrowable();
        try {
            test.get().fail(result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromPath(captureScreenShot(result, "fail")).build());
        } catch (IOException e) {
            System.err
                    .println("Exception thrown while updating test fail status " + Arrays.toString(e.getStackTrace()));
            test.get().log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test Case Failed", ExtentColor.RED));


        }
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));

    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        System.out.println((result.getMethod().getMethodName() + " skipped!"));
        try {
            test.get().skip(result.getThrowable(),
                    MediaEntityBuilder.createScreenCaptureFromPath(getScreenshot()).build());
        } catch (IOException e) {
            System.err
                    .println("Exception thrown while updating test skip status " + Arrays.toString(e.getStackTrace()));
        }
        test.get().getModel().setEndTime(getTime(result.getEndMillis()));
    }

    @Override
    public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    private void log(String methodName) {
        System.out.println(methodName);
    }

    private void log(IClass testClass) {
        System.out.println(testClass);
    }

    private void log1(WebElement element) {
        System.out.println(element);

    }


}
