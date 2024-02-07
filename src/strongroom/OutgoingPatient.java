package strongroom;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;
import objects.NotificationPage;


public class OutgoingPatient extends Base {
	private static NotificationPage notificationPage;


	public static void outgoingPatient(String action, String location, String drugname, String transaction_id, String resident,
			String drugqty, String note, String username, String pin) throws InterruptedException {

		if ("Outgoing Patient".equals(action)) {

			SoftAssert softAssert = new SoftAssert();

			// Opening
			notificationPage = new NotificationPage(driver, wait);

			notificationPage.clickNotificationIcon();

			WebElement stock = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Stock']")));
			stock.click();
			
			WebElement clearbutton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button clear-button']")));
			clearbutton.click();

			WebElement medication = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Medication...']")));
			medication.clear(); // Clear the field before entering a new drug name
			medication.sendKeys(drugname);

			WebElement Resident = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Resident...']")));
			Resident.clear(); // Clear the field before entering a new drug name
			Resident.sendKeys(resident);

			WebElement searching = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='button submit-button']")));
			searching.click();
			Thread.sleep(5000);

			WebElement expected = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));
			String openB = expected.getText().trim();

			System.out.println("(Open stock Drug): " + openB);
			String numericPart = openB.replaceAll("[^0-9]", "");

			int valueToCompare = Integer.parseInt(numericPart);
			System.out.println("(--): " + valueToCompare);

			Thread.sleep(1000);
			int actualValue = valueToCompare;
			System.out.println("(--): " + actualValue);

			// script

			Thread.sleep(3000);

			WebElement transferIn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Outgoing']")));
			transferIn.click();

			WebElement writenote = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='note-modal']")));
			writenote.click();
			writenote.sendKeys("Transferr in imprest");

			WebElement imprest = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Resident Medication']")));
			imprest.click();

			WebElement Residentinput = wait.until(ExpectedConditions.presenceOfElementLocated(
					By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']")));
			Residentinput.click();

			Residentinput.sendKeys(resident);

			WebElement searchButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
			searchButton.click();

			WebElement result = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/form[1]/div[1]/div[2]/div[1]/div[1]/div[1]/p[1]/b[1]")));
			result.click();

			Thread.sleep(2000);

			WebElement medicationbox = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='pom-select']")));
			medicationbox.click();

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

			Thread.sleep(1000);

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

			Thread.sleep(1000);

			if (!drugFound) {
				System.out.println("Drug name '" + trimmedDrugName + "' not found in the dropdown options.");
				System.out.println("Available Options in Dropdown:");
				for (WebElement option : dropdownOptions) {
					System.out.println("- " + option.getText());
				}
			}

			WebElement quantityInput = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Quantity']")));
			quantityInput.clear();

			quantityInput.sendKeys(drugqty);

			WebElement addButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
			addButton.click();

			String selectedDrug = driver.findElement(By.xpath("//td[1]/p[1]")).getText();

			String selectedQty = driver.findElement(By.xpath("//tr[1]/td[2]/p[1]")).getText().trim();
			String add1 = selectedQty.replaceAll("\\(.*?\\)", "").trim();
			System.out.println("select qty =  " + add1);
			Thread.sleep(1000);
			String numericAdd = add1.replaceAll("[^0-9]", "");
			int abc = Integer.parseInt(numericAdd);
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
			clearbutton.click();
			Thread.sleep(2000);

			medication.clear();
			medication.sendKeys(drugname);
			Thread.sleep(1000);

			Resident.sendKeys(resident);
			Thread.sleep(1000);

			searching.click();
			Thread.sleep(1000);

			WebElement expected1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));
			String closeB = expected1.getText().trim();

			System.out.println("(Final Stock Drug): " + closeB);

			// Extract numeric part from the string (remove non-numeric characters)
			String numericPart1 = closeB.replaceAll("[^0-9]", "");

			int valueToCompare1 = Integer.parseInt(numericPart1);
			System.out.println("(--): " + valueToCompare1);

			int actualValue1 = valueToCompare1;
			System.out.println("(--): " + actualValue1);

			int ExpectedQty = actualValue - abc;
			System.out.print(ExpectedQty);

			inputdata = "\n" + "Transfer In Imprest Location: " + enteredLocation + "\n"
					+ "Transferin Imprest Drug Name: " + selectedDrug + "\n" + "Transferin Imprest in quantity:  " + abc
					+ "\n" + "Current Stock: " + actualValue + "\n" + "Final Stock: " + actualValue1 + "\n";

			Task_Name = action;
			
			softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");
			softAssert.assertAll();
		} else {
			System.out.println("Not found this testcase data");
			System.out.println(action);

			inputdata = "null";
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
