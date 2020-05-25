package com.qa.plivo.util;

import com.qa.plivo.base.BasePage;
import com.qa.plivo.constants.Constants;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @author Praveen
 */

public class TestUtil extends BasePage {

    public static Workbook book;
    public static Sheet sheet;


    public static void shortWait() {
        try {
            Thread.sleep(Constants.SHORT_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void mediumWait() {
        try {
            Thread.sleep(Constants.MEDIUM_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void longWait() {
        try {
            Thread.sleep(Constants.LONG_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void maxWait() {
        try {
            Thread.sleep(Constants.MAX_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Read data from excel sheet using data provider
     *
     * @param sheetName
     * @throws Exception
     * @Date 25/05/2020
     * @Revision History
     */


    public static Object[][] getTestData(String sheetName) {
        FileInputStream file = null;
        try {
            file = new FileInputStream(Constants.TESTDATA_SHEET_PATH);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            book = WorkbookFactory.create(file);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        sheet = book.getSheet(sheetName);

        Object data[][] = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

        for (int i = 0; i < sheet.getLastRowNum(); i++) {
            for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
                data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
            }
        }

        return data;

    }

    public WebElement presenceOfTheElement(final By elementIdentifier) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        return wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(elementIdentifier);
            }
        });

    }


}
