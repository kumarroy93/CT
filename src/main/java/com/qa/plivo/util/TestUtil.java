package com.qa.plivo.util;

import com.opencsv.CSVWriter;
import com.qa.plivo.base.BasePage;
import com.qa.plivo.constants.Constants;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
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
            file = new FileInputStream("");
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

    public static void writeInExcel(String totalFare) throws IOException {
        FileInputStream fis = new FileInputStream("");
        FileOutputStream fos = null;
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheet("");
        XSSFRow row = null;
        XSSFCell cell = null;
        XSSFFont font = workbook.createFont();
        XSSFCellStyle style = workbook.createCellStyle();

        int col_Num = -1;
        row = sheet.getRow(0);
        for(int i = 0; i < row.getLastCellNum(); i++)
        {
            if(row.getCell(i).getStringCellValue().trim().equals(""))
            {
                col_Num = i;
            }
        }

        row = sheet.getRow(1);
        if(row == null)
            row = sheet.createRow(1);

        cell = row.getCell(col_Num);
        if(cell == null)
            cell = row.createCell(col_Num);

        font.setFontName("Comic Sans MS");
        font.setFontHeight(14.0);
        font.setBold(true);
        font.setColor(HSSFColor.WHITE.index);

        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.GREEN.index);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);
        cell.setCellValue(totalFare);

        fos = new FileOutputStream("");
        workbook.write(fos);
        fos.close();
    }


    public static void writeIntoCSV(String filePath, String cab, String gift, String food, String mis, String air){
        File file = new File(filePath);

        try {
            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile, '|',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] { "Cab Expenses", "Gift Expenses", "Food Expenses" , "Miscellaneous Expenses "," Air Fare"});
            data.add(new String[] { cab, gift, food ,mis, air});
            writer.writeAll(data);

            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


}
