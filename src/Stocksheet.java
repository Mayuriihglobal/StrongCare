import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.JavascriptExecutor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Stocksheet {

	// private static final String Return = null;
	public WebDriver driver;
	public WebDriverWait wait;
	public static List<String> drugNames;
	public static List<String> Innumbers;
	int searchCount = 0;

	public Stocksheet(WebDriver driver) {
		// Initialize the WebDriver and WebDriverWait in the constructor
		this.driver = driver;
		this.driver.manage().window().maximize();
		this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(60));

	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// Use FILE_PATH in the constructor and methods
		// drugNames = readDrugNamesFromExcel("output.xlsx");
		// Innumbers = readInnumbersFromExcel("output.xlsx");

		WebDriver driver = new ChromeDriver();
		Stocksheet stocksheet = new Stocksheet(driver);
		// Maximize the Chrome window
		driver.manage().window().maximize();

		// Set implicit wait to 10 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Initialize WebDriverWait with a longer duration
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		// Open the webpage
		driver.get("https://staging.strongroom.ai/login");

		// Entering Location
		WebElement locationInput = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Location']")));
		locationInput.sendKeys("Internal Testing\"");

		// Add a sleep before clicking "Transfer In"
		Thread.sleep(2000); // Adjust the sleep time as per your requirement

		// Clicking on a location
		WebElement clickElement = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='drug-search-result' and text()='Internal Testing']")));
		clickElement.click();

		// Entering Username
		WebElement usernameInput = driver.findElement(By.xpath("//input[@placeholder='Username/email']"));
		usernameInput.sendKeys("sam");

		// Entering Password
		WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Password']"));
		passwordInput.sendKeys("strongroompassword");

		// Clicking on Login Button
		WebElement loginButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));
		loginButton.click();

		// Selecting location on the second page
		WebElement selectLocation = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Select location']")));
		selectLocation.click();

		// Choosing a location from the dropdown
		WebElement dropdownOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='pv_id_2_2']")));
		dropdownOption.click();

		// Explicit wait for the "blue-button" with a 90-second duration
		WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(60));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement secondPageButton = extendedWait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));

		// Click the "blue-button"
		secondPageButton.click();

		WebElement notificationIcon = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("i.pi.pi-exclamation-circle")));

		// Click on the notification icon
		notificationIcon.click();

		stocksheet.run();

	}

	public void run() throws InterruptedException {

		// WebDriver driver = new ChromeDriver();

		// Set implicit wait to 10 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Initialize WebDriverWait with a longer duration
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		// Read drug names from the Excel file
		List<String> drugNames = readDrugNamesFromExcel("output.xlsx");

		// Read In numbers qty from the Excel file (7) cell
		List<String> Innumbers = readInnumbersFromExcel("output.xlsx");

		WebElement transferIn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer In']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", transferIn);

		// Infinite loop to repeatedly click the "Transfer In" button

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']")));

		// Explicit wait for the location input field
		WebElement enterLocation = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@placeholder='Type in location to receive from']")));
		enterLocation.click();
		enterLocation.sendKeys("ward");

		String desiredLabel = "Ward 1";
		WebElement dropdownItem = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@aria-label='" + desiredLabel + "']")));
		dropdownItem.click();

		// Assuming you have navigated to the page and located the textarea element
		WebElement noteTextArea = driver.findElement(By.xpath("//textarea[@name='notes' and @id='note-modal']"));

		// Write "Transferrin" in the note box
		noteTextArea.sendKeys("Transferr in");

		// Click the Imprest/Emergency Meds/Ward Stock button
		WebElement button = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
		button.click();

		// 7. Enter a medication : Check that the medication dropdown works
		WebElement medicationInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
		medicationInput.click();

		// Click on the quantity field with the specified placeholder
		WebElement quantityInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter qty']")));
		quantityInput.click();

		// Iterate through drug names and perform the search for the second drug

		// Increment the search count
		searchCount++;

		// If this is the second name, perform the search

		// Locate the search field and enter the drug name
		WebElement SelectMedication = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Select Medication']")));
		String drugname = drugNames.get(searchCount);

		SelectMedication.sendKeys(drugname);
		SelectMedication.sendKeys(Keys.ENTER);

		Thread.sleep(2000); // 2000 milliseconds = 2 seconds

		SelectMedication.sendKeys(Keys.ARROW_DOWN);

		SelectMedication.sendKeys(Keys.ENTER);

		// Add a 3-second sleep to wait after clicking
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Add logic to handle the search results as needed

		// Exit the loop
		// break;

		// Iterate through qty
		int searchCount1 = 0;
		for (String Innumber : Innumbers) {
			// Increment the search count
			searchCount1++;

			// If this is the second name, perform the search
			if (searchCount1 == 2) {
				// Locate the search field and enter the drug name
				WebElement Selectqty = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Enter qty']")));
				Selectqty.sendKeys(Innumber);
			}
		}

		WebElement addButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		addButton.click();

		// Click on the button with the specified class
		WebElement completeButton = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='regular-button complete-button']")));
		completeButton.click();

		// Check that the signature check modal pops up
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.xpath("//div[@class='modal-mask']//div[@class='modal-mask']//div[@class='form-section-container']")));

		// Find the input field with the specified placeholder
		WebElement usernameInput1 = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));

		usernameInput1.click();

		// Clear the text from the input field
		usernameInput1.clear();

		// Writing text into the input field

		WebElement usernameInput11 = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));
		usernameInput11.sendKeys("nurse");

		// Clicking on the input field
		WebElement passwordInput1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='PIN/Password']")));
		passwordInput1.click();

		// Writing text into the input field
		passwordInput1.sendKeys("strongroompassword");

		// Clicking on the field
		WebElement greenButton = wait

				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='green-button']")));
		greenButton.click();

		Thread.sleep(3000);
		
		if (searchCount<16)
		{
			run();

		}

	}

	// Method to read drug names from the Excel file (2) for all rows
	public static List<String> readDrugNamesFromExcel(String filePath) {
		List<String> drugNames = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {

			Sheet sheet = workbook.getSheet("Table Data"); // Replace with your sheet name

			// int cellIndexDrugNames = 2; // Assuming drug names are in the second column
			// (index 1)

			// int rowIndex = 0; // Initialize row index counter

			for (Row row : sheet) {
				Cell cell = row.getCell(2); // Assuming drug names are in the second column (index 1)
				if (cell != null) {

					drugNames.add(cell.getStringCellValue());

				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return drugNames;
	}

	// Method to qty from the Excel file (7) for all rows
	public static List<String> readInnumbersFromExcel(String filePath) {
		List<String> Innumbers = new ArrayList<>();

		try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
			Sheet sheet = workbook.getSheet("Table Data"); // Replace with your sheet name
			int cellIndexQuantities = 7; // Assuming quantities are in the seventh column (index 6)

			int rowIndex = 0; // Initialize row index counter

			for (Row row : sheet) {
				Cell cell = row.getCell(cellIndexQuantities);
				if (cell != null) {
					if (rowIndex > 0) {
						Innumbers.add(cell.getStringCellValue());
					}
					rowIndex++;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return Innumbers;
	}

}
