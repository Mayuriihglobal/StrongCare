package objects;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Stocktakeresultpage {

    public static void main(String[] args) {
        processExcelFile("/home/user/Documents/Agedcare.xlsx", "Table Data");
    }

    public static void processExcelFile(String filePath, String sheetName) {
        try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
            Sheet sheet = workbook.getSheet(sheetName);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                // Read values from 7th, 8th, and 10th index
                double openingBalance = getNumericCellValue(row, 7);
                double numericValue = getNumericCellValue(row, 8);
                double closingBalance = getNumericCellValue(row, 10);

                // Perform addition
                double sum = openingBalance + numericValue;

                // Write the sum to the 9th index
                writeNumericValue(row, 9, sum);

                // Check if the values at 9th and 10th index match
                String result = (sum == closingBalance) ? "Pass" : "Fail";

                // Write the result to the 12th index
                writeCellValue(row, 12, result);
            }

            // Save the changes back to the Excel file
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double getNumericCellValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        return (cell != null && cell.getCellType() == CellType.NUMERIC) ? cell.getNumericCellValue() : 0;
    }

    private static void writeNumericValue(Row row, int cellIndex, double value) {
        Cell cell = row.createCell(cellIndex, CellType.NUMERIC);
        cell.setCellValue(value);
    }

    private static void writeCellValue(Row row, int cellIndex, String value) {
        Cell cell = row.createCell(cellIndex, CellType.STRING);
        cell.setCellValue(value);
    }
}
