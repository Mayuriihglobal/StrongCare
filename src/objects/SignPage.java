package objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
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

public class SignPage {
	private static final By USERNAME_INPUT_LOCATOR = By.xpath("//input[@placeholder='Username']");
	private static final By PASSWORD_INPUT_LOCATOR = By.xpath("//input[@placeholder='PIN/Password']");
	private static final By GREEN_BUTTON_LOCATOR = By.xpath("//div[@class='green-button']");

	public static List<String> usernames;
	public static List<String> pins;

	public static void data() {

		usernames = readValuesFromExcel("Agedcare.xlsx", "Signature", 1); // Assuming usernames are in the second column
																			// (index 1)
		pins = readValuesFromExcel("Agedcare.xlsx", "Signature", 2); // Assuming pins are in the third column (index 2)

	}

	private WebDriverWait wait;

	public SignPage(WebDriver driver, WebDriverWait wait) {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		this.wait = wait;
	}

	public void performSignature() {
		data();
		if (usernames.size() > 0 && pins.size() > 0) {
			String enteredUsername = enterUsername(usernames.get(0)); // Using the first username from the list
			System.out.println("Entered Username: " + enteredUsername);

			String enteredPassword = enterPassword(pins.get(0)); // Using the first pin from the list
			System.out.println("Entered Password: " + enteredPassword);

			clickGreenButton();
		} else {
			System.out.println("No usernames or pins available in the Excel sheet.");
		}
	}

	private String enterUsername(String username) {
		WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(USERNAME_INPUT_LOCATOR));
		usernameInput.click();
		usernameInput.clear();
		usernameInput.sendKeys(username);
		return username;
	}

	private String enterPassword(String password) {
		WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(PASSWORD_INPUT_LOCATOR));
		passwordInput.click();
		passwordInput.sendKeys(password);
		return password;
	}

	private void clickGreenButton() {
		WebElement greenButton = wait.until(ExpectedConditions.elementToBeClickable(GREEN_BUTTON_LOCATOR));
		greenButton.click();

	}

	// single method
	public static List<String> readValuesFromExcel(String filePath, String sheetName, int cellIndex) {
		List<String> values = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
			Sheet sheet = workbook.getSheet(sheetName);
			int rowIndex = 0;

			for (Row row : sheet) {
				Cell cell = row.getCell(cellIndex);
				if (cell != null && rowIndex > 0) {
					if (cell.getCellType() == CellType.STRING) {
						values.add(cell.getStringCellValue());
					} else if (cell.getCellType() == CellType.NUMERIC) {
						if (DateUtil.isCellDateFormatted(cell)) {
							values.add(cell.getDateCellValue().toString());
						} else {
							values.add(String.valueOf((int) cell.getNumericCellValue()));
						}
					}
				}
				rowIndex++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return values;
	}

}
