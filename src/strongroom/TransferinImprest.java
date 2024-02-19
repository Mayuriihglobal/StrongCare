package strongroom;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;
import objects.NotificationPage;

public class TransferinImprest extends Base {
	private static NotificationPage notificationPage;

	public static void transferinImprest(String action, String location, String drugname, String transaction_id,
			String resident, String drugqty, String note, String username, String pin) throws InterruptedException {

		WebElement medication = null;
		String openB = "-";
		int actualValue = 0;

		SoftAssert softAssert = new SoftAssert();
		notificationPage = new NotificationPage(driver, wait);
		notificationPage.clickNotificationIcon();

		WebElement stock = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Stock']")));
		stock.click();

		WebElement stockTake = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Stock Take']")));
		stockTake.click();

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

		// New code to read medication name from Excel

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
		Thread.sleep(3000);

		String MedicationName1 = "0"; // Default value in case element not found
		String stockes = "0"; // Default value in case element not found

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

		WebElement expected = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));

		openB = expected.getText().trim();
		String numericPart = openB.replaceAll("[^0-9]", "");
		int valueToCompare = Integer.parseInt(numericPart);

		actualValue = valueToCompare;

		Thread.sleep(2000);
		inputdata = "\n" + "Transfer In Imprest Location: " + location + "\n" + "Medication Name: " + drugname + "\n"
				+ "\n" + "Medication QTY is found: Zero " + "\n";
		;
		Task_Name = action;

		openB = "-";

		WebElement transferIn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer In']")));
		transferIn.click();

		WebElement enterLocation = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@placeholder='Type in location to receive from']")));
		enterLocation.click();
		enterLocation.sendKeys(location);
		Thread.sleep(5000);
		WebElement selectlocation = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class, 'p-dropdown-item')]")));

		// String selectedLocation = "MismatchedLocation"; // Set a mismatched location

		String selectedLocation = selectlocation.getText();

		selectlocation.click();

		WebElement writenote = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='note-modal']")));
		writenote.click();
		writenote.sendKeys(note);

		WebElement imprest = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
		imprest.click();

		WebElement medicationInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
		medicationInput.click();
		medicationInput.sendKeys(drugname);

		Thread.sleep(3000);

		List<WebElement> dropdownOptions1 = driver.findElements(By.xpath("//li[contains(@class, 'p-dropdown-item')]"));
		for (WebElement option : dropdownOptions1) {
			String optionText = option.getText().trim();
			if (optionText.contains(drugname)) {
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

		Task_Name = action;

		System.out.println("Excel sheet QTY " + drugqty);

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

		System.out.println("selected Converted QTY  " + abcAsDouble);

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
		Thread.sleep(6000);

		// Loop through the test execution

		clearbutton.click();
		Thread.sleep(2000);

		Displayinstock.click();
		Thread.sleep(1000);

		Displayimprest.click();
		Thread.sleep(1000);

		medication.clear();
		medication.sendKeys(drugname);
		Thread.sleep(1000);

		searching.click();
		Thread.sleep(1000);

		WebElement actual = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));
		String closeB = actual.getText().trim();
		String numericPart1 = closeB.replaceAll("[^0-9]", "");
		int actualValue1 = Integer.parseInt(numericPart1);
		System.out.println("After opration Stock Qty: " + actualValue1);

		int ExpectedQty = actualValue + abc;

		inputdata = "\n" + "Transfer In Imprest Location: " + location + "\n" + "Transferin Imprest Drug Name: "
				+ selectedDrug + "\n" + "Transferin Imprest in quantity:  " + abc + "\n" + "Current Stock: "
				+ actualValue + "\n" + "Final Stock: " + actualValue1 + "\n";

		Task_Name = action;

		softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");

		softAssert.assertEquals(selectedLocation, location, "Location Name mismatch");

		softAssert.assertEquals(selectedDrug, formattedDrugName, "Medication Name mismatch");

		softAssert.assertEquals(abcAsDouble, Double.parseDouble(drugqty), "Quantity mismatch");

		softAssert.assertAll();

	}
}