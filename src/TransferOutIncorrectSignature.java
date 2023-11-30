import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TransferOutIncorrectSignature {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		// Extent report setup
		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("TransferOutIncorrectSignature.html");
		extent.attachReporter(spark);

		// Create a WebDriver instance
		WebDriver driver = new ChromeDriver();

// Maximize the Chrome window

		driver.manage().window().maximize();

		// Set implicit wait to 10 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Initialize WebDriverWait with a longer duration
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		// Open the webpage
		driver.get("https://staging.strongroom.ai/login");

// Display status log on html report page
		extent.createTest("Go to https://staging.strongroom.ai/login").assignCategory("regression testing")
				.assignDevice("Chrome").log(Status.INFO, "Go to https://staging.strongroom.ai/login");

		try {
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

			extent.createTest("Enter correct login").assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.INFO, "Enter correct login");

			// Selecting location on the second page
			WebElement selectLocation = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Select location']")));
			selectLocation.click();

			extent.createTest("user is logged in and the system displays the location select page")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.INFO, "user is logged in and the system displays the location select page");

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

			extent.createTest("Select a location and click the Select Location button")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.INFO, "system displays the drug register for the specific location");

			// Display status log on html report page : Go to /drug-register
			extent.createTest("Go to/drug-register").assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "Go to /drug-register");

			// Clicking on the Transfer out
			WebElement transferIn = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer Out']")));
			transferIn.click();

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']")));

			// Log the pass status
			System.out.println(
					"Test Passed: Click the Transfer out button in the left menu : Transfer out modal appears");

			// Display status log on html report page
			extent.createTest("Click the Transfer In button in the left menu: Transfer In modal appears")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "Click the Transfer In button in the left menu : Transfer In modal appears");

		} catch (Exception e) {
			// Log the fail status and any exception details
			System.out.println(
					"Test Failed: Click the Transfer In button in the left menu : Transfer In modal is not appears. Exception: "
							+ e.getMessage());

			// Display status log on html report page
			extent.createTest("Click the Transfer In button in the left menu : Transfer In modal is not appears")
					.assignCategory("regression testing").assignDevice("Chrome").log(Status.FAIL,
							"Click the Transfer In button in the left menu : Transfer In modal is not appears");

		}

		try {

			// Explicit wait for the location input field
			WebElement enterLocation = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//input[@placeholder='Type in location to send to']")));
			enterLocation.click();
			enterLocation.sendKeys("External facility");

			// Check that the location dropdown appears and displays location names
			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[@class='p-dropdown-items-wrapper']")));

			// Log the pass status
			System.out.println("Test Passed: Enter a location : location dropdown appears and displays location names");

// Display status log on html report page
			extent.createTest("Enter a location : location dropdown appears and displays location names")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "Enter a location : location dropdown appears and displays location names");

		}

		catch (Exception e) {
			// Log the fail status and any exception details
			System.out.println(
					"Test Failed: Enter a location : location dropdown is not appears. Exception: " + e.getMessage());

			// Display status log on html report page
			extent.createTest("Test Failed: Enter a location : location dropdown is not appears")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.FAIL, "Test Failed: Enter a location : location dropdown is not appears");

		}

		String desiredLabel = "External facility";
		WebElement dropdownItem = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@aria-label='" + desiredLabel + "']")));
		dropdownItem.click();

//Add a sleep of 5 seconds
		Thread.sleep(5000);

		// Display status log on html report page
		extent.createTest("Select a location").assignCategory("regression testing").assignDevice("Chrome")
				.log(Status.PASS, "Selected a location");

		System.out.println("Test Passed: Selected a location");

		// Assuming you have navigated to the page and located the textarea element
		WebElement noteTextArea = driver.findElement(By.xpath("//textarea[@name='notes' and @id='note-modal']"));

		// Write "Transferrin" in the note box
		noteTextArea.sendKeys("Transferr out");

		System.out.println("Test Passed: Added text to notes");

		// Display status log on html report page
		extent.createTest("Add text to notes").assignCategory("regression testing").assignDevice("Chrome")
				.log(Status.PASS, "Added text to notes");

		try {

			// Click the Imprest/Emergency Meds/Ward Stock button
			WebElement button = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Resident Medication']")));
			button.click();

			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']")));

			// Log the pass status
			System.out
					.println("Test Passed: Click the Patient Medication button :patient medication modal is displayed");

// Display status log on html report page
			extent.createTest("Click the Patient Medication button :patient medication modal is displayed")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "Click the Patient Medication button :patient medication modal is displayed");

		} catch (Exception e) {

			// Log the fail status and any exception details
			System.out.println(
					"Test Failed: Click the Patient Medication button :patient medication modal is not displayed. Exception: "
							+ e.getMessage());

			// Display status log on html report page
			extent.createTest("Click the Patient Medication button :patient medication modal is not displayed")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.FAIL, "Click the Patient Medication button :patient medication modal is not displayed");

		}

		try {
			// Click on the search field
			WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']")));
			searchField.click();

			// Enter text in the search field
			searchField.sendKeys("Arvind Nath");

			// Click on the search button
			WebElement searchButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
			searchButton.click();

			// Make sure the name is in the search result
			WebElement searchResult = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='patient-result-info']//p[1]")));
			String resultText = searchResult.getText();

			// Verify that the name in the search result matches the expected name
			if (resultText.contains("Arvind Nath")) {
				System.out.println("Search successful. Name found in the search result.");

				// Display status log on html report page
				extent.createTest("Enter a patient name in the search field and click search : patient is displayed")
						.assignCategory("regression testing").assignDevice("Chrome").log(Status.PASS,
								"Enter a patient name in the search field and click search : patient is displayed");

			} else {
				System.out.println("Search failed. Name not found in the search result.");
				// Display status log on html report page
				extent.createTest(
						"Enter a patient name in the search field and click search : patient is not displayed")
						.assignCategory("regression testing").assignDevice("Chrome").log(Status.FAIL,
								"Enter a patient name in the search field and click search : patient is not displayed");
			}

		} catch (Exception e) {
			// Handle exceptions if any
			e.printStackTrace();

		}

		try {

			WebElement searchResult1 = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='patient-result-info']//p[1]")));

			// Click on the name in the search result
			searchResult1.click();

			// Assuming you have clicked on the search result and navigated to the transfer
			// in page

			// Wait for the element to be present and visible
			WebElement individualPatientName = wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[@class='individual-patient-container']//p[1]")));

			// Get the text from the element
			String individualPatientNameText = individualPatientName.getText();

			// Verify that the expected text is displayed on the page
			if (individualPatientNameText.contains("Arvind Nath")) {
				System.out.println("Text is displayed on the page: " + individualPatientNameText);

				// Display status log on html report page
				extent.createTest("Select the patient : patient is displayed in the Transfer In modal")
						.assignCategory("regression testing").assignDevice("Chrome")
						.log(Status.PASS, "Select the patient : patient is displayed in the Transfer In modal");

			} else {
				System.out.println("Text is not displayed on the page: " + individualPatientNameText);
				// Handle the case where the text is not displayed as expected
				// Display status log on html report page
				extent.createTest("Select the patient : patient is not displayed in the Transfer In modal")
						.assignCategory("regression testing").assignDevice("Chrome")
						.log(Status.FAIL, "Select the patient : patient is not displayed in the Transfer In modal");

			}

		} catch (Exception e) {

			// Handle exceptions if any
			e.printStackTrace();

		}
//***
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='pom-select']")));

			// Log the pass status
			System.out.println("Test Passed: Select Medication input field is present");

			// Display status log on html report page
			extent.createTest("Check that Select Medication input field appears").assignCategory("regression testing")
					.assignDevice("Chrome").log(Status.PASS, "Select Medication input field is present");

			// 7. Enter a medication : Check that the medication dropdown works
			WebElement medicationInput = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='pom-select']")));
			medicationInput.click();

			// Create a Select object
			Select medicationDropdown = new Select(medicationInput);

			medicationDropdown.selectByValue("2");
			medicationInput.sendKeys(Keys.ESCAPE);

			// Locate the quantity (QTY) input box
			WebElement qtyInput = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Quantity']")));

			qtyInput.click();

			// Clear the quantity input box
			qtyInput.clear();

			// Add "1" to the quantity input box
			qtyInput.sendKeys("1");

			// Click on the "Add" button
			WebElement addButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
			addButton.click();

			// You can perform further actions if needed

		} catch (Exception e) {
			// Log the fail status and any exception details
			System.out
					.println("Test Failed: Select Medication input field is not present. Exception: " + e.getMessage());

			// Display status log on html report page
			extent.createTest("Check that Select Medication input field appears").assignCategory("regression testing")
					.assignDevice("Chrome").log(Status.FAIL, "Select Medication input field is not present");

			// Handle the case where the input field is not present as expected
		}

		// Click on the name in the search result

		// ------&&

		try {

			// get xpath for madication
			WebElement elementmedication = driver
					.findElement(By.xpath("//p[normalize-space()='Amoxicillin 500 mg capsule']"));

			// Get the text from the medication name
			String text1 = elementmedication.getText();

			// Assuming 'text' contains the text retrieved from the element
			String expectedText1 = "Amoxicillin 500 mg capsule";

			// Print medication name
			System.out.println("Selected Medication name is : " + text1);

			// Compare the text with the expected text
			if (text1.equals(expectedText1)) {
				System.out.println("Pass: Added Medication name match");

				// Display status log on html report page
				extent.createTest("Added Medication name match").assignCategory("regression testing")
						.assignDevice("Chrome").log(Status.INFO, "Added Medication name match");

			} else {
				System.out.println("Fail: Added Medication name is not match");
				// Display status log on html report page
				extent.createTest("Added Medication name is not match").assignCategory("regression testing")
						.assignDevice("Chrome").log(Status.INFO, "Added Medication name is not match");
			}

			WebElement elementqty = driver.findElement(By.xpath("//div[@class='right-form-section-panel']//span[1]"));
			String text2 = elementqty.getText().trim();
			String expectedText2 = "1";
			int result = text2.compareTo(expectedText2);

			if (result == 0) {

				System.out.println("Added qty matched");

				// Display status log on html report page
				extent.createTest("Added qty matched").assignCategory("regression testing").assignDevice("Chrome")
						.log(Status.INFO, "Added qty matched");

			} else {
				System.out.println("qty mismatched");
				// Display status log on html report page
				extent.createTest("Added qty is not matched").assignCategory("regression testing")
						.assignDevice("Chrome").log(Status.INFO, "Added qty is not matched");

			}

			WebElement elementqtytype = driver.findElement(By.xpath("//span[normalize-space()='capsule']"));
			String text3 = elementqtytype.getText();
			String expectedText3 = "capsule";
			// Print Capsule
			System.out.println("Selected Medication name is : " + text3);

			// Compare the text with the expected text
			if (text3.equals(expectedText3)) {
				System.out.println("Pass: Added Medication Qty type(capsule) is match");
				// Display status log on html report page
				extent.createTest("Added Medication Qty type(capsule) is match").assignCategory("regression testing")
						.assignDevice("Chrome").log(Status.INFO, "Added Medication Qty type(capsule) is match");

			} else {
				System.out.println("Fail: Added Medication Qty type(capsule) is not match");

				// Display status log on html report page
				extent.createTest("Added Medication Qty type(capsule) is not match")
						.assignCategory("regression testing").assignDevice("Chrome")
						.log(Status.INFO, "Added Medication Qty type(capsule) is not match");

			}

			// Log the pass status
			System.out.println("Test Passed: Click the Add button : correct medication and qty have been Added");

			// Display status log on html report page
			extent.createTest("Click the Add button : correct medication and qty have been Added")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "Click the Add button : correct medication and qty have been Added");

		} catch (Exception e) {

			// Log the fail status and any exception details
			System.out
					.println("Test Failed: Click the Add button : correct medication and qty is not Added. Exception: "
							+ e.getMessage());

			// Display status log on html report page
			extent.createTest("Click the Add button : correct medication and qty is not Added")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.FAIL, "Click the Add button : correct medication and qty is not Added");

		}

		try {

			// Click on the button with the specified class
			WebElement completeButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='regular-button complete-button']")));
			completeButton.click();

			// Check that the signature check modal pops up
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//div[@class='modal-mask']//div[@class='modal-mask']//div[@class='form-section-container']")));

			// Log the pass status
			System.out.println("Test Passed: Click the Recieve Transfer button : signature check modal pops up apper");

			// Display status log on html report page
			extent.createTest("Click the Recieve Transfer button : signature check modal pops up apper")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "Click the Recieve Transfer button : signature check modal pops up apper");

		} catch (Exception e) {

			System.out.println("Test Failed: Click the Recieve Transfer button : signature modal pops up is not apper");

			// Display status log on html report page
			extent.createTest("Click the Recieve Transfer button : signature modal pops up is not apper")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.FAIL, "Click the Recieve Transfer button : signature modal pops up is not apper");

		}

		// &&

		var test = extent.createTest("Enter an incorrect signature");

		try {

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
			passwordInput1.sendKeys("****");

			// Clicking on the field
			WebElement greenButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='green-button']")));
			greenButton.click();

			// Wait for the validation message element
			WebElement validationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//p[normalize-space()='Invalid login or password/pin code.']")));

			// Get text of the validation message
			String validationMessageText = validationMessage.getText();

			// Create a test in the Extent report
			// Log the validation message to the Extent report
			test.log(Status.PASS, "Validation message displayed: " + validationMessageText);

		} catch (Exception e) {

			// Log failure and exception details to the Extent report
			test.log(Status.FAIL, "Validation message not displayed.");
			test.log(Status.FAIL, e.getMessage());

		}

		extent.flush();

	}

}
