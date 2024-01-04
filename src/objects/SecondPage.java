package objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SecondPage {
	private final WebDriver driver;
	private final WebDriverWait wait;

	// Constants for locators
	private static final By SELECT_LOCATION_BUTTON = By.xpath("//span[@aria-label='Select location']");
	private static final By DROPDOWN_OPTION = By.xpath("//li[@class='p-dropdown-item']");
	private static final By SECOND_PAGE_BUTTON = By.xpath("//p[@class='blue-button']");

	public static List<String> selectlocations;

	public SecondPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public static void data() {

		selectlocations = SelectlocationFromExcell("output.xlsx");

	}

	// Selects a location from the dropdown.
	public void selectLocationFromDropdown() {
		WebElement selectLocation = wait.until(ExpectedConditions.elementToBeClickable(SELECT_LOCATION_BUTTON));
		selectLocation.click();

		data();
		// Get the list of dropdown options
		List<WebElement> dropdownOptions = wait
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(DROPDOWN_OPTION));

		// Match sheet location name with dropdown options and click on the matching
		// option
		String sheetLocationName = selectlocations.get(0); // Assuming you want to select the first location
		for (WebElement option : dropdownOptions) {
			if (option.getText().equals(sheetLocationName)) {
				option.click();
				return; // Exit the loop once a match is found and clicked
			}
		}

		// If no match is found
		System.out.println("No matching option found for location: " + sheetLocationName);

	}

	// Clicks the button on the second page.
	public void clickSecondPageButton() {
		// Instead of Thread.sleep, use an explicit wait
		WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(90));
		WebElement secondPageButton = extendedWait.until(ExpectedConditions.elementToBeClickable(SECOND_PAGE_BUTTON));
		secondPageButton.click();
	}

	// single method
	public static List<String> readValuesFromExcel(String filePath, String sheetName, int cellIndex) {
		List<String> values = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
			Sheet sheet = workbook.getSheet("Login");
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
	public static List<String> SelectlocationFromExcell(String filePath) {
		int SelectlocationCellIndex = 4; // Assuming drug names are in the third column (index 2)
		return readValuesFromExcel(filePath, filePath, SelectlocationCellIndex);
	}

}
