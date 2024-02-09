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
import org.openqa.selenium.StaleElementReferenceException;
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
	public static List<String> loginUrls;

	private static String enteredUsername; // Variable to store entered username
	private static String enteredPassword; // Variable to store entered password
	private static String enteredLocation;

	public static void data() {

		locations = readLocationFromExcel("Agedcare.xlsx");
		usernames = UsernameFromExcel("Agedcare.xlsx");
		passwords = PasswordFromExcel("Agedcare.xlsx");
		selectlocations = SelectlocationFromExcell("Agedcare.xlsx");
		loginUrls = readLoginUrlFromExcel("Agedcare.xlsx");

	}

	public LoginPage(WebDriver driver, WebDriverWait wait) {
		LoginPage.driver = driver;
		LoginPage.wait = wait;

	}

	public void openLoginPage() {
		data();
		String loginUrl = loginUrls.get(0);
		driver.get(loginUrl);
	}

	public static void login() {
		data();
		String location = locations.get(0);
		String username = usernames.get(0);
		String password = passwords.get(0);

		LoginPage loginPage = new LoginPage(driver, wait);

		loginPage.enterLocation(location);
		loginPage.enterCredentials(username, password);

		// Store entered username and password
		enteredUsername = username;
		enteredPassword = password;
	}

	public void enterLocation(String location) {

		int attempts = 0;
		while (attempts < 3) {

			try {
				WebElement locationInput = wait
						.until(ExpectedConditions.presenceOfElementLocated(By.xpath(LOCATION_INPUT_XPATH)));
				locationInput.sendKeys(location);

				WebElement locationResultElement = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath(LOCATION_RESULT_XPATH + "[text()='" + location + "']")));
				locationResultElement.click();

				// Set the entered location
				enteredLocation = location;
				break;
			} catch (StaleElementReferenceException e) {
				e.printStackTrace();

			}
			attempts++;
		}
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
			Sheet sheet = workbook.getSheet("Configuration");
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
	public static List<String> readLoginUrlFromExcel(String filePath) {
		int loginUrlCellIndex = 0; // Assuming URL names are in the first column (index 0)
		return readValuesFromExcel(filePath, filePath, loginUrlCellIndex);
	}

	public static List<String> readLocationFromExcel(String filePath) {
		int locationCellIndex = 1; // Assuming Location names are in the second column (index 1)
		return readValuesFromExcel(filePath, filePath, locationCellIndex);
	}

	public static List<String> UsernameFromExcel(String filePath) {
		int UsernameCellIndex = 2; // Assuming username are in the third column (index 2)
		return readValuesFromExcel(filePath, filePath, UsernameCellIndex);
	}

	public static List<String> PasswordFromExcel(String filePath) {
		int passwordCellIndex = 3; // Assuming password are in the fourth column (index 3)
		return readValuesFromExcel(filePath, filePath, passwordCellIndex);
	}

	public static List<String> SelectlocationFromExcell(String filePath) {
		int SelectlocationCellIndex = 4; // Assuming selectlocation names are in the fifth column (index 4)
		return readValuesFromExcel(filePath, filePath, SelectlocationCellIndex);
	}

	public static String getEnteredUsername() {
		// TODO Auto-generated method stub
		return enteredUsername;
	}

	public static String getEnteredPassword() {
		// TODO Auto-generated method stub
		return enteredPassword;
	}

	public static String getEnteredLocation() {
		return enteredLocation;
	}

}
