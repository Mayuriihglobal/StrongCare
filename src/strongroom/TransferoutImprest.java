package strongroom;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;
import objects.NotificationPage;

public class TransferoutImprest extends Base {
	private static NotificationPage notificationPage;

	public static void transferoutImprest(String action, String location, String drugname, String transaction_id,
			String resident, String drugqty, String note, String username, String pin) throws InterruptedException {

		WebElement medication = null; // Declare medication outside the try block
		String openB = "-";
		int actualValue = 0;

		SoftAssert softAssert = new SoftAssert();

		// Opening
		notificationPage = new NotificationPage(driver, wait);
		notificationPage.clickNotificationIcon();

		WebElement stock = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Stock']")));
		stock.click();

		// New code to read medication name from Excel
		if (drugname == null || drugname.isEmpty()) {
			System.out.println("No more data to process. Exiting the test.");
			// return;
		}

		WebElement clearbutton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button clear-button']")));
		clearbutton.click();

		WebElement Displayinstock = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display In Stock Only']")));
		Displayinstock.click();

		WebElement Displayimprest = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display Imprest Only']")));
		Displayimprest.click();

		try {

			medication = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Medication...']")));
			medication.clear(); // Clear the field before entering a new drug name
			medication.sendKeys(drugname);

		} catch (NoSuchElementException e) {
			System.out.println("Medication input element not found. Exiting the test.");
			// return; // Exit the test method

		}

		String drugName = drugname;
		int closingParenthesisIndex = drugName.indexOf(')');
		String drugNameWithoutBrand = drugName.substring(closingParenthesisIndex + 2);
		String formattedDrugName = drugNameWithoutBrand.substring(0, 1).toUpperCase()
				+ drugNameWithoutBrand.substring(1);
		System.out.println(formattedDrugName);

		WebElement searching = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='button submit-button']")));
		searching.click();
		Thread.sleep(5000);

		// Print the Available Balance and selected Medication
		String MedicationName1 = "0"; // Default value in case element not found
		String stockes = "0"; // Default value in case element not found
		// String PatientName1 = "0"; //Default value in case element not found

		try {
			WebElement SelectedMedication = driver.findElement(By.xpath("//td[1]"));
			MedicationName1 = SelectedMedication.getText();
			System.out.println("Medication Name = " + MedicationName1);
		} catch (Exception e) {
			System.out.println("Entry for this medication is not found");
			System.out.println("Medication Name Not found: 0");
			// Set default values for MedicationName1, stock, and RemainingasString
			MedicationName1 = "-";
			stockes = "-";
			String RemainingasString = "-";

		}

		try {
			WebElement expected = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));

			openB = expected.getText().trim();
			String numericPart = openB.replaceAll("[^0-9]", "");
			int valueToCompare = Integer.parseInt(numericPart);

			Thread.sleep(2000);
			inputdata = "\n" + action + "\n" + "Transfer Out Imprest Location: " + location + "\n" + "Medication Name: "
					+ drugname + "\n" + "\n" + "Medication QTY is found: Zero " + "\n";
			;
			Task_Name = action;

			// softAssert.assertEquals("0", openB, "final stock is not match with Expected
			// stock");
			// softAssert.assertAll();
			// return;

			Thread.sleep(1000);
			actualValue = valueToCompare;
			System.out.println("Before opration Stock Qty: " + actualValue);

		} catch (Exception e) {
			System.out.println("Current Stock not found: 0");
			openB = "-";

		}

		// script

		Thread.sleep(3000);

		WebElement transferIn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer Out']")));
		transferIn.click();

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

		WebElement imprest = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
		imprest.click();

		Thread.sleep(2000);

		WebElement medicationInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
		medicationInput.click();
		medicationInput.sendKeys(drugname);

		Thread.sleep(3000);

		List<WebElement> dropdownOptions1 = driver.findElements(By.xpath("//li[contains(@class, 'p-dropdown-item')]"));
		for (WebElement option : dropdownOptions1) {
			String optionText = option.getText().trim();
			if (optionText.contains(drugname)) {
				// Found a match, click on the option
				Thread.sleep(1000);
				option.click();
				break;
			}
		}

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));

		WebElement quantityInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter qty']")));
		quantityInput.click();
		quantityInput.sendKeys(drugqty);

		WebElement addButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		addButton.click();

		String selectedDrug = driver.findElement(By.xpath("//td[1]/p[1]")).getText();

		String selectedQty = driver.findElement(By.xpath("//p[1]/span[1]")).getText().trim();
		String add1 = selectedQty.replaceAll("\\(.*?\\)", "").trim();
		System.out.println("select qty =  " + add1);
		Thread.sleep(1000);
		String numericAdd = add1.replaceAll("[^0-9]", "");
		int abc = Integer.parseInt(numericAdd);
		double abcAsDouble = (double) abc;

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
		Thread.sleep(5000);

		try {
			WebElement element = driver.findElement(By.xpath("//p[contains(text(),'{')]"));
			String elementText = element.getText();
			System.out.println("Text from element: " + elementText);

			inputdata = "\n" + action + "Error message " + elementText + "\n";

			Task_Name = action;

			softAssert.assertEquals(openB, abcAsDouble);
			softAssert.assertAll();
			return;

		} catch (Exception e) {
			System.out.println("We have stock to Transfer out");

		}
		;

		// Loop through the test execution
		// clearbutton.click();
		// Thread.sleep(2000);

		// Displayinstock.click();
		// Thread.sleep(1000);

		// Displayimprest.click();
		// Thread.sleep(1000);

		// medication.clear();
		// medication.sendKeys(drugname);
		// Thread.sleep(1000);

		searching.click();
		// Thread.sleep(1000);

		WebElement finalstockonstocktakescreen = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));
		
		WebElement medicationname = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[1]")));
		
		String medicationtext = medicationname.getText().trim();

		String closeB = finalstockonstocktakescreen.getText().trim();

		System.out.println("(Final Stock Drug): " + closeB);

		// Extract numeric part from the string (remove non-numeric characters)
		String numericPart1 = closeB.replaceAll("[^0-9]", "");

		int valueToCompare1 = Integer.parseInt(numericPart1);
		System.out.println("(--): " + valueToCompare1);

		int finalstock = valueToCompare1;
		System.out.println("(--): " + finalstock);

		int ExpectedQty = actualValue - abc;
		System.out.print(ExpectedQty);

		inputdata = "\n" + action + "\n" + "Transfer Out imprest Location: " + location + "\n"
				+ "Transferin Imprest Drug Name: " + selectedDrug + "\n" + "Transferin Imprest in quantity:  " + abc
				+ "\n" + "Current Stock: " + actualValue + "\n" + "Final Stock: " + finalstock + "\n";

		Task_Name = action;

		softAssert.assertEquals(finalstock, ExpectedQty, "final stock is not match with Expected stock");

	    // softAssert.assertEquals(selectedLocation, location, "Location Namemismatch");

		softAssert.assertEquals(selectedDrug, formattedDrugName, "Medication Name mismatch");

		softAssert.assertEquals(abcAsDouble, Double.parseDouble(drugqty), "Quantity mismatch");

		softAssert.assertAll();

	}
}