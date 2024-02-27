package strongroom;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

public class TransferoutPatient extends Base {

	public static void transferoutPatient(String action, String location, String drugname, String transaction_id,
			String resident, String drugqty, String note, String username, String pin) throws InterruptedException {

		Thread.sleep(5000);

		WebElement medicationinput = null;
		String ExpectedQuantity = "-";
		int initialQuantity = 0;

		SoftAssert softAssert = new SoftAssert();
		Task_Name = action;

		WebElement stockpage = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Stock']")));
		stockpage.click();

		if (drugname == null || drugname.isEmpty()) {
			System.out.println("No more data to process. Exiting the test.");
			inputdata = "\n" + action + "\n" + "Medication Name: is Empty" + "\n";
			return;
		}

		medicationinput = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Medication...']")));
		medicationinput.sendKeys(drugname);

		WebElement Resident = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Resident...']")));
		Resident.sendKeys(resident);

		WebElement Displayinstockfilter = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display In Stock Only']")));
		Displayinstockfilter.click();

		WebElement searchbutton = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='button submit-button']")));
		searchbutton.click();
		Thread.sleep(5000);

		String drugName = drugname;
		int closingParenthesisIndex = drugName.indexOf(')');
		String drugNameWithoutBrand = drugName.substring(closingParenthesisIndex + 2);
		String formattedDrugName = drugNameWithoutBrand.substring(0, 1).toUpperCase()
				+ drugNameWithoutBrand.substring(1);
		System.out.println(formattedDrugName);

		String MedicationNametext = "0";

		try {
			WebElement SelectedMedication = driver.findElement(By.xpath("//td[1]"));
			MedicationNametext = SelectedMedication.getText();
			System.out.println("Medication Name = " + MedicationNametext);
		} catch (Exception e) {
			System.out.println("Entry for this medication is not found");

			inputdata = "\n" + action + "\n" + "Medication Name: " + drugname + "\n" + "Resident Name: " + resident
					+ "\n" + "Entry for this Medication Not Found" + "\n";

			return;

		}
		Thread.sleep(2000);

		WebElement SelectedPatient = driver.findElement(By.xpath("//td[2]"));
		String Patientname = SelectedPatient.getText();
		System.out.println("Patient name " + Patientname);
		Thread.sleep(2000);

		try {
			WebElement expectedstock = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));

			ExpectedQuantity = expectedstock.getText().trim();
			String numericPart = ExpectedQuantity.replaceAll("[^0-9]", "");
			int valueToCompare = Integer.parseInt(numericPart);

			if (valueToCompare == 0) {
				Thread.sleep(2000);
				inputdata = "\n" + action + "\n" + "Medication Name: " + drugname + "\n" + "Resident Name: " + resident
						+ "\n" + "Medication Quantity is found: Zero " + "\n";
				;

				return;

			} else {
				System.out.println("COntinue");
			}
			System.out.println("Current Stock = " + ExpectedQuantity);

			Thread.sleep(1000);
			initialQuantity = valueToCompare;
			System.out.println("Before opration Stock Qty: " + initialQuantity);

		} catch (Exception e) {
			System.out.println("Current Stock not found: 0");
			ExpectedQuantity = "-";

		}

		Thread.sleep(3000);

		WebElement transferout = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer Out']")));
		transferout.click();

		WebElement enterLocation = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@placeholder='Type in location to send to']")));
		enterLocation.click();
		enterLocation.sendKeys(location);
		Thread.sleep(5000);
		WebElement selectlocation = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class, 'p-dropdown-item')]")));
		String selectedLocation = selectlocation.getText();

		selectlocation.click();

		WebElement writenote = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='note-modal']")));
		writenote.click();
		writenote.sendKeys("Transferr in imprest");

		WebElement Patient = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Resident Medication']")));
		Patient.click();
		Thread.sleep(2000);

		WebElement Residentinput = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']")));
		Residentinput.click();

		Residentinput.sendKeys(resident);
		Thread.sleep(2000);

		WebElement searchButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		searchButton.click();
		Thread.sleep(3000);

		List<WebElement> residentdropdown = driver.findElements(By.xpath("//p[starts-with(b, 'Name:')]"));

		for (WebElement option : residentdropdown) {
			System.out.println("Residentdropdown: " + option.getText());

			if (option.getText().trim().equals("Name: " + resident) || option.getText().trim().equals(resident)) {
				Thread.sleep(2000);
				option.click();
				break;
			}

		}

		Thread.sleep(3000);

		WebElement result = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@class='individual-patient-container']//p[1]")));
		String SelectedResident = result.getText();

		String cleanedResident = SelectedResident.replace("Name: ", "");

		Thread.sleep(2000);

		WebElement medicationbox = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='pom-select']")));
		medicationbox.click();
		Thread.sleep(2000);

		// Define the timeout (in milliseconds) for which you want to keep the dropdown
		// open
		long timeout = System.currentTimeMillis() + 1000; // 1 seconds

		while (System.currentTimeMillis() < timeout) {
			// Click on the dropdown repeatedly to keep it open
			medicationbox.click();

			// Add a small delay to control the loop speed
			Thread.sleep(1000);

			// Check if the dropdown is still open
			if (!isDropdownOpen()) {
				// Break the loop if the dropdown is closed
				break;
			}
		}
		Thread.sleep(2000);

		List<WebElement> dropdownOptions = driver.findElements(By.xpath("//select[@id='pom-select']/option"));

		// Extract only the drug name without additional text
		String trimmedDrugName = getTrimmedDrugName(drugname);

		System.out.println(trimmedDrugName);

		// Check if the drugname is present in the dropdown options
		boolean drugFound = false;
		for (WebElement option : dropdownOptions) {
			System.out.println("Dropdown Option: " + option.getText());

			if (option.getText().contains(trimmedDrugName)) {
				drugFound = true;
				Thread.sleep(2000);

				option.click();
				break;
			}
		}

		Thread.sleep(2000);

		if (!drugFound) {
			// If an exact match is not found, try to find an option that contains the
			// partial drug name
			for (WebElement option : dropdownOptions) {
				if (option.getText().contains(drugname.split(" ")[1])) {
					drugFound = true;
					option.click();
					break;
				}
			}
		}

		Thread.sleep(2000);

		if (!drugFound) {
			System.out.println("Drug name '" + trimmedDrugName + "' not found in the dropdown options.");
			System.out.println("Available Options in Dropdown:");
			for (WebElement option : dropdownOptions) {
				System.out.println("- " + option.getText());
				Thread.sleep(2000);

			}

			// Print data into ClickUp
			inputdata = "\n" + "Location Name: " + location + "\n" + "Medication Name: " + drugname + "\n"
					+ "Drug Drop down: No available options" + "\n" + "\n" + "Medication QTY is found: Zero " + "\n";
			Task_Name = action;
			return;

		}

		WebElement quantityInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Quantity']")));
		quantityInput.clear();

		quantityInput.sendKeys(drugqty);
		Thread.sleep(2000);

		WebElement addButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		addButton.click();
		Thread.sleep(2000);

		String selectedDrugtext = driver.findElement(By.xpath("//td[1]/p[1]")).getText();

		String selectedQtytext = driver.findElement(By.xpath("//p[1]/span[1]")).getText().trim();
		String addedqtytext = selectedQtytext.replaceAll("\\(.*?\\)", "").trim();
		System.out.println("select qty =  " + addedqtytext);

		// Convert strings to integers
		int drugQtyAsInt = Integer.parseInt(drugqty);
		int add1AsInt = Integer.parseInt(addedqtytext);

		// Perform subtraction
		int difference = drugQtyAsInt - add1AsInt;

		Thread.sleep(1000);
		String numericAdd = addedqtytext.replaceAll("[^0-9]", "");
		int addedqtyint = Integer.parseInt(numericAdd);
		double addedqtydouble = (double) addedqtyint;

		String enteredLocation = location;
		Thread.sleep(3000);

		WebElement completeButton = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='regular-button complete-button']")));
		completeButton.click();

		// Sign off
		WebElement usernameInput = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));
		usernameInput.click();
		usernameInput.clear();
		usernameInput.sendKeys(username);

		WebElement passwordInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='PIN/Password']")));
		passwordInput.click();
		passwordInput.sendKeys(pin);

		WebElement greenButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='green-button']")));
		greenButton.click();
		Thread.sleep(3000);

		// Loop through the test execution

		searchbutton.click();
		Thread.sleep(1000);

		WebElement Expectedquantity = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));
		String finalstock = Expectedquantity.getText().trim();

		System.out.println("(Final Stock Drug): " + finalstock);

		// Extract numeric part from the string (remove non-numeric characters)
		String numericpartExpected = finalstock.replaceAll("[^0-9]", "");

		int Expectedint = Integer.parseInt(numericpartExpected);
		System.out.println("(--): " + Expectedint);

		int expectedquantity = Expectedint;
		System.out.println("(--): " + expectedquantity);

		int ExpectedQty = initialQuantity - addedqtyint;
		System.out.print(ExpectedQty);

		// Check the value of difference
		if (difference == 0) {
			// If difference is 0, print this
			inputdata = "\n" + action + "\n" + "Location Name: " + enteredLocation + "\n" + "Medication Name: "
					+ selectedDrugtext + "\n" + "Resident Name: " + resident + "\n"
					+ "Transfer Out Quantity from Sheet:  " + drugqty + "\n" + "Transfer Out Added Quantity: "
					+ addedqtyint + "\n" + "Current Stock: " + initialQuantity + "\n" + "Final Stock: "
					+ expectedquantity + "\n";
		} else {
			// If difference is not 0, print this
			inputdata = "\n" + action + "\n" + "Location Name: " + enteredLocation + "\n" + "Medication Name: "
					+ selectedDrugtext + "\n" + "Resident Name: " + resident + "\n"
					+ "Transfer Out Quantity from Sheet:  " + drugqty + "\n" + "Transfer Out Added Quantity: "
					+ addedqtyint + "\n" + "QA NOTE: DIFFERENCE BETWEEN SHEET INPUT QUANTITY AND ADDED QUANTITY: "
					+ difference + "\n" + "Current Stock: " + initialQuantity + "\n" + "Final Stock: "
					+ expectedquantity + "\n";
		}

		softAssert.assertEquals(expectedquantity, ExpectedQty, "final stock is not match with Expected stock");
		softAssert.assertEquals(cleanedResident, resident, "Resident Name mismatch");
		softAssert.assertEquals(selectedDrugtext, formattedDrugName, "Medication Name mismatch");
		softAssert.assertAll();

	}

	private static boolean isDropdownOpen() {
		// You can modify this method based on how you determine if the dropdown is open
		try {
			WebElement dropdown = driver.findElement(By.xpath("//select[@id='pom-select']"));
			return dropdown.isDisplayed();
		} catch (Exception e) {
			return false;
		}

	}

	private static String getTrimmedDrugName(String drugName) {
		// Find the last set of parentheses and extract the content between them
		int lastOpenParenthesisIndex = drugName.lastIndexOf("(");
		int lastCloseParenthesisIndex = drugName.lastIndexOf(")");

		if (lastOpenParenthesisIndex != -1 && lastCloseParenthesisIndex != -1
				&& lastOpenParenthesisIndex < lastCloseParenthesisIndex) {
			return drugName.substring(lastOpenParenthesisIndex + 1, lastCloseParenthesisIndex).trim();
		} else {
			// If no parentheses found or the format is not as expected, return the original
			// drug name
			return drugName.trim();
		}
	}

}