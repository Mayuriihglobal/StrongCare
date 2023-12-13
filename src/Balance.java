import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

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

public class Balance {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

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

		WebElement notificationIcon = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("i.pi.pi-exclamation-circle")));

		// Click on the notification icon
		notificationIcon.click();

		// Read drug names from the Excel file
		List<String> drugNames = readDrugNamesFromExcel("output.xlsx");

		// Iterate through drug names and perform the search for the second drug
		int searchCount = 0;
		for (String drugName : drugNames) {
			// Increment the search count
			searchCount++;

			// If this is the second name, perform the search
			if (searchCount == 2) {
				// Locate the search field and enter the drug name
				WebElement searchField = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Medication...']")));
				searchField.clear(); // Clear existing text
				searchField.sendKeys(drugName);

				// Locate and click on the search button
				WebElement imprest = wait.until(ExpectedConditions
						.elementToBeClickable(By.xpath("//button[normalize-space()='Imprest Only']")));
				imprest.click();

				// Wait after clicking the search button
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//i[@class='pi pi-angle-right']")));

				// Add a 3-second sleep to wait after clicking
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// Add logic to handle the search results as needed

				// Exit the loop
				break;
			}
		}

		// Assuming you want to print the content of the element with XPath
		// //td[normalize-space()='4']
		WebElement elementWithText4 = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[@style='width: 80px;']")));

		// Get the text content of the element and print it
		String stock = elementWithText4.getText().trim();
		System.out.println("(Stock): " + stock);

		// ... (rest of your code)

		// Convert the text content to an integer
		int valueToCompare = Integer.parseInt(stock);

		// Clicking on the Transfer in
		WebElement transferIn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer In']")));
		transferIn.click();

		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']")));

		// Explicit wait for the location input field
		WebElement enterLocation = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@placeholder='Type in location to receive from']")));
		enterLocation.click();
		enterLocation.sendKeys("101");

		// Check that the location dropdown appears and displays location names
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='p-dropdown-items-wrapper']")));

		String desiredLabel = "101";
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

		// Add the text "a" to the input field
		medicationInput.sendKeys("Soflax (AN) tablet");

		// wait.until(ExpectedConditions
		// .visibilityOfElementLocated(By.xpath("//div[@class='p-dropdown-items-wrapper']")));

		medicationInput.sendKeys(Keys.ENTER);

		Thread.sleep(2000); // 2000 milliseconds = 2 seconds

		medicationInput.sendKeys(Keys.ARROW_DOWN);

		medicationInput.sendKeys(Keys.ENTER);

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));

		// Click on the quantity field with the specified placeholder
		WebElement quantityInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter qty']")));
		quantityInput.click();

		// Enter the quantity "1" in the field
		quantityInput.sendKeys("10");

		WebElement addButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		addButton.click();

		// Assuming you want to print the content of the element with XPath
		WebElement elementWithText1 = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']//span[1]")));

		// Get the text content of the element and print it
		String add = elementWithText1.getText().trim();
		System.out.println("(Transfer): " + add);

		// Balance
		// Convert the text content to an integer
		int valueToCompare1 = Integer.parseInt(add);

		// Perform addition
		int sum = valueToCompare + valueToCompare1;

		// Print the result
		System.out.println("Balance: " + sum);

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
		usernameInput11.sendKeys("valeshan.naidoo@strongroom.ai");

		// Clicking on the input field
		WebElement passwordInput1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='PIN/Password']")));
		passwordInput1.click();

		// Writing text into the input field
		passwordInput1.sendKeys("1111");

		// Clicking on the field
		WebElement greenButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='green-button']")));
		greenButton.click();

		// Wait for 5 seconds (5000 milliseconds)
		Thread.sleep(5000);

		// OpeningBalance("/home/user/Documents/myExcelFile.xlsx", "Table Data", 7,
		// "Pass");
		OpeningBalance("/home/user/Documents/myExcelFile.xlsx", "Table Data", 7, stock, 10, String.valueOf(sum));

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

	public static void OpeningBalance(String filePath, String sheetName, int columnIndex, String status, int i,
			String string) {
		System.out.println(filePath);
		System.out.println(sheetName);
		System.out.println(columnIndex);
		System.out.println(status);
		System.out.println(i);
		System.out.println(string);

		try (Workbook workbook = WorkbookFactory.create(new File(filePath))) {
			Sheet sheet = workbook.getSheet(sheetName); // Use the provided sheetName

			// Iterate through all rows, starting from the 2nd row (index 1)
			for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
				Row row = sheet.getRow(rowIndex);

				// Check if the row exists before trying to create a cell
				if (row == null) {
					row = sheet.createRow(rowIndex);
				}

				
				/*
				Cell cell = row.getCell(2);
				
				String abc = cell.getStringCellValue();
				
              if(abc.contentEquals(stock))
              {
            	  Cell cell1 = row.createCell(columnIndex);
            	  cell1.setCellValue(status);
            	  
            	  Cell cell10 = row.createCell(10);
  				cell10.setCellValue(string);
            	  
              
              }
		*/
				
				
				
				


			}

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

		}

		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
