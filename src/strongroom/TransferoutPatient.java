package strongroom;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;
import objects.NotificationPage;

public class TransferoutPatient extends Base {
	private static NotificationPage notificationPage;

	public static void transferoutPatient(String action, String location, String drugname, String transaction_id,
			String resident, String drugqty, String note, String username, String pin) throws InterruptedException {

		WebElement medication = null; // Declare medication outside the try block
		String Initialstock = "-";
		int InitialValue = 0;

		if ("Transfer out Patient".equals(action)) {

			SoftAssert softAssert = new SoftAssert();
			Task_Name = action;

			// Opening
			// notificationPage = new NotificationPage(driver, wait);

			// notificationPage.clickNotificationIcon();

			Thread.sleep(3000);

			WebElement stock = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Stock']")));
			stock.click();

			Thread.sleep(3000);

			// New code to read medication name from Excel
			if (drugname == null || drugname.isEmpty()) {
				System.out.println("No more data to process. Exiting the test.");
				return;
			}

			medication = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Medication...']")));
			medication.clear(); // Clear the field before entering a new drug name
			medication.sendKeys(drugname);

			WebElement Resident = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Resident...']")));
			Resident.clear(); // Clear the field before entering a new drug name
			Resident.sendKeys(resident);

			WebElement Displayinstock = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display In Stock Only']")));
			Displayinstock.click();
			// New code to read medication name from Excel

			WebElement searching = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='button submit-button']")));
			searching.click();
			Thread.sleep(5000);

			String drugName = drugname;
			int closingParenthesisIndex = drugName.indexOf(')');
			String drugNameWithoutBrand = drugName.substring(closingParenthesisIndex + 2);
			String formattedDrugName = drugNameWithoutBrand.substring(0, 1).toUpperCase()
					+ drugNameWithoutBrand.substring(1);
			System.out.println(formattedDrugName);

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
				return;

			}
			Thread.sleep(2000);

			WebElement SelectedPatient = driver.findElement(By.xpath("//td[2]"));
			String Patientname = SelectedPatient.getText();
			System.out.println("Patient name " + Patientname);
			Thread.sleep(2000);

			try {
				WebElement stock1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));

				Initialstock = stock1.getText().trim();
				String numericPart = Initialstock.replaceAll("[^0-9]", "");
				int valueToCompare = Integer.parseInt(numericPart);

				if (valueToCompare == 0) {
					Thread.sleep(2000);
					inputdata = "\n" + "Transfer In Imprest Location: " + location + "\n" + "Medication Name: "
							+ drugname + "\n" + "\n" + "Medication QTY is found: Zero " + "\n";
					;

					return;

				} else {
					System.out.println("COntinue");
				}
				System.out.println("Current Stock = " + Initialstock);

				Thread.sleep(1000);
				InitialValue = valueToCompare;
				System.out.println("Before opration Stock Qty: " + InitialValue);

			} catch (Exception e) {
				System.out.println("Current Stock not found: 0");
				Initialstock = "-";

			}

			// script

			Thread.sleep(3000);

			WebElement transferIn = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer Out']")));
			transferIn.click();

			WebElement enterLocation = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//input[@placeholder='Type in location to send to']")));
			enterLocation.click();
			enterLocation.sendKeys(location);
			Thread.sleep(5000);
			WebElement selectlocation = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class, 'p-dropdown-item')]")));
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

			WebElement Residentinput = wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']")));
			Residentinput.click();

			Residentinput.sendKeys(resident);
			Thread.sleep(2000);

			WebElement searchButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
			searchButton.click();
			Thread.sleep(2000);

			WebElement result = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div/div[2]/div/div/div/p[1]")));
			String SelectedResident = result.getText();
			String cleanedResident = SelectedResident.replace("Name: ", "");

			result.click();

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
				inputdata = "\n" + "Transfer In Imprest Location: " + location + "\n" + "Medication Name: " + drugname
						+ "\n" + "Drug Drop down: No available options" + "\n" + "\n" + "Medication QTY is found: Zero "
						+ "\n";
				Task_Name = action;
				return;

			}

			WebElement quantityInput = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Quantity']")));
			quantityInput.clear();

			quantityInput.sendKeys(drugqty);
			Thread.sleep(2000);

			WebElement addButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
			addButton.click();
			Thread.sleep(2000);

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
			Thread.sleep(3000);

			// Loop through the test execution

			searching.click();
			Thread.sleep(1000);

			WebElement expected1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));
			String finalstock = expected1.getText().trim();

			System.out.println("(Final Stock Drug): " + finalstock);

			// Extract numeric part from the string (remove non-numeric characters)
			String numericPart1 = finalstock.replaceAll("[^0-9]", "");

			int valueToCompare1 = Integer.parseInt(numericPart1);
			System.out.println("(--): " + valueToCompare1);

			int actualValue1 = valueToCompare1;
			System.out.println("(--): " + actualValue1);

			int ExpectedQty = InitialValue - abc;
			System.out.print(ExpectedQty);

			inputdata = "\n" + "Transfer In Imprest Location: " + enteredLocation + "\n"
					+ "Transferin Imprest Drug Name: " + selectedDrug + "\n" + "Transferin Imprest in quantity:  " + abc
					+ "\n" + "Current Stock: " + InitialValue + "\n" + "Final Stock: " + actualValue1 + "\n";

			Task_Name = action;
			softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");
			softAssert.assertEquals(cleanedResident, resident, "Resident Name mismatch");
			softAssert.assertEquals(selectedDrug, formattedDrugName, "Medication Name mismatch");
			softAssert.assertAll();
		} else {
			//
		}
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