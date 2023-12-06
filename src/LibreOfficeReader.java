import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;


public class LibreOfficeReader {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();

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
		locationInput.sendKeys("Internal Testing");

		// Clicking on a location
		WebElement clickElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//p[@class='drug-search-result' and text()='Internal Testing']")));
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
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='pv_id_2_4']")));
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

		// Assuming there is a notification icon with the class 'pi
		// pi-exclamation-circle'
		WebElement notificationIcon = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("i.pi.pi-exclamation-circle")));

		// Click on the notification icon
		notificationIcon.click();

		// Read drug names from the Excel file
		
		
		List<String> drugNames = readDrugNamesFromExcel("output.xlsx");
		
		

		// Iterate through drug names and perform the search
		for (String drugName : drugNames) {
			// Locate the search field and enter the drug name
			WebElement searchField = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Medication...']")));
			searchField.clear(); // Clear existing text
			searchField.sendKeys(drugName);

			// Locate and click on the search button
			WebElement searchButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button submit-button']")));
			searchButton.click();

			// Wait after clicking the search button
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[@class='pi pi-angle-right']")));

			// wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody[2]/tr[1]/td[2]")));

			// Add a 3-second sleep to wait after clicking
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// Add logic to handle the search results as needed

			// Optionally, navigate back to the search page for the next drug search
			// driver.navigate().back();
		}

		// Close the WebDriver
		// driver.quit();
	}

	
	// Method to read drug names from the Excel file
	private static List<String> readDrugNamesFromExcel(String filePath) {
		List<String> drugNames = new ArrayList<>();
		
		try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
			Sheet sheet = workbook.getSheet("Table Data"); // Replace with your sheet name
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

	

	
	
	
}
