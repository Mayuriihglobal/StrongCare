package objects;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

public class LoginPage {
	private static WebDriver driver;
	private static WebDriverWait wait;

	// Constants for XPath expressions
	private static final String LOCATION_INPUT_XPATH = "//input[@placeholder='Location']";
	private static final String LOCATION_RESULT_XPATH = "//p[@class='drug-search-result']";
	private static final String USERNAME_INPUT_XPATH = "//input[@placeholder='Username/email']";
	private static final String PASSWORD_INPUT_XPATH = "//input[@placeholder='Password']";
	private static final String LOGIN_BUTTON_XPATH = "//p[@class='blue-button']";

	public static List<String> locations;
	public static List<String> usernames;
	public static List<String> passwords;
	public static List<String> selectlocations;

	public static void data() {

		locations = readLocationFromExcel("output.xlsx");
		usernames = UsernameFromExcel("output.xlsx");
		passwords = PasswordFromExcel("output.xlsx");
		selectlocations = SelectlocationFromExcell("output.xlsx");

	}

	public LoginPage(WebDriver driver, WebDriverWait wait) {
		LoginPage.driver = driver;
		LoginPage.wait = wait;

	}

	public void openLoginPage(String url) {
		driver.get(url);
	}

	public static void login() {
		data();
		String location = locations.get(0);
		String username = usernames.get(0);
		String password = passwords.get(0);

		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.enterLocation(location);
		loginPage.enterCredentials(username, password);
	}

	public void enterLocation(String location) {
		WebElement locationInput = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(LOCATION_INPUT_XPATH)));
		locationInput.sendKeys(location);

		WebElement locationResultElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath(LOCATION_RESULT_XPATH + "[text()='" + location + "']")));
		locationResultElement.click();
	}

	public void enterCredentials(String username, String password) {
		WebElement usernameInput = driver.findElement(By.xpath(USERNAME_INPUT_XPATH));
		usernameInput.sendKeys(username);

		WebElement passwordInput = driver.findElement(By.xpath(PASSWORD_INPUT_XPATH));
		passwordInput.sendKeys(password);
	}

	public void clickLoginButton() {
		WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LOGIN_BUTTON_XPATH)));
		loginButton.click();
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
	public static List<String> readLocationFromExcel(String filePath) {
		int locationCellIndex = 1; // Assuming drug names are in the third column (index 2)
		return readValuesFromExcel(filePath, filePath, locationCellIndex);
	}

	public static List<String> UsernameFromExcel(String filePath) {
		int UsernameCellIndex = 2; // Assuming innumbers are in the eighth column (index 7)
		return readValuesFromExcel(filePath, filePath, UsernameCellIndex);
	}

	public static List<String> PasswordFromExcel(String filePath) {
		int locationCellIndex = 3; // Assuming locations are in the fifth column (index 4)
		return readValuesFromExcel(filePath, filePath, locationCellIndex);
	}

	// Usage of single method
	public static List<String> SelectlocationFromExcell(String filePath) {
		int SelectlocationCellIndex = 4; // Assuming drug names are in the third column (index 2)
		return readValuesFromExcel(filePath, filePath, SelectlocationCellIndex);
	}

}
