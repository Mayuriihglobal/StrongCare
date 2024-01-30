package objects;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtils {
	static int searchCount = 1; // drug
	static int searchCount1 = -1; // Qty
	static int searchCoun2 = 1; // location
	static int searchCoun4 = 0; // TransectionID
	static int rowIndex = 1; // external file

	public static List<String> drugNames;
	public static List<String> Innumbers;
	public static List<String> location;
	public static List<String> resident;
	public static List<String> transectionid;

	public static void data() {

		drugNames = readDrugNamesFromExcel("Agedcare.xlsx");
		Innumbers = readInnumbersFromExcel("Agedcare.xlsx");
		location = readLocationFromExcel("Agedcare.xlsx");
		resident = readResidentFromExcel("Agedcare.xlsx");
		transectionid = readTransectionIDFromExcel("Agedcare.xlsx");

	}

	public static void OpeningBalance(String filePath, String sheetName, int columnIndex7, String beforestockcount) {
		try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
			Sheet sheet = workbook.getSheet(sheetName);

			Row row = sheet.getRow(rowIndex);

			// Check if the row exists before trying to create a cell
			if (row == null) {
				row = sheet.createRow(rowIndex);
			}

			// Create a cell only for the specified columnIndex
			processCell(rowIndex, columnIndex7, row, beforestockcount);
			// processCell(rowIndex, columnindex10, row, sum);

			// Create a new temporary file
			File tempFile = new File(filePath + ".tmp");

			try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
				workbook.write(outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Rename the temporary file to the original file
			if (tempFile.renameTo(new File(filePath))) {
				System.out.println("File updated successfully.");
			} else {
				System.err.println("Error updating file.");
			}

			rowIndex++;

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static void processCell(int rowIndex, int columnIndex, Row row, String value) {
		Cell cell = row.createCell(columnIndex);
		cell.setCellValue(value);
	}

	// single method
	public static List<String> readValuesFromExcel(String filePath, String sheetName, int cellIndex) {
		List<String> values = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
			Sheet sheet = workbook.getSheet("Table Data");
			int rowIndex = 0;

			for (Row row : sheet) {
				Cell cell = row.getCell(cellIndex);
				if (cell != null && rowIndex > 0) {
					if (cell.getCellType() == CellType.STRING) {
						values.add(cell.getStringCellValue());
					} else if (cell.getCellType() == CellType.NUMERIC) {
						values.add(String.valueOf(cell.getNumericCellValue()));
					}
				}
				rowIndex++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return values;
	}

	// Usage of single method
	public static List<String> readDrugNamesFromExcel(String filePath) {
		int drugNameCellIndex = 2; // Assuming drug names are in the third column (index 2)
		return readValuesFromExcel(filePath, filePath, drugNameCellIndex);
	}

	public static List<String> readInnumbersFromExcel(String filePath) {
		int innumbersCellIndex = 7; // Assuming innumbers are in the eighth column (index 7)
		return readValuesFromExcel(filePath, filePath, innumbersCellIndex);
	}

	public static List<String> readLocationFromExcel(String filePath) {
		int locationCellIndex = 4; // Assuming locations are in the fifth column (index 4)
		return readValuesFromExcel(filePath, filePath, locationCellIndex);
	}

	public static List<String> readResidentFromExcel(String filePath) {
		int residentCellIndex = 5; // Assuming locations are in the fifth column (index 4)
		return readValuesFromExcel(filePath, filePath, residentCellIndex);
	}

	public static List<String> readTransectionIDFromExcel(String filePath) {
		int TransectionIDCellIndex = 1; // Assuming locations are in the fifth column (index 4)
		return readValuesFromExcel(filePath, filePath, TransectionIDCellIndex);
	}
}