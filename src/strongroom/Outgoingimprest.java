package strongroom;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

public class Outgoingimprest extends Base {

	public static void outgoingimprest(String action, String location, String drugname, String transaction_id,
			String resident, String drugqty, String note, String username, String pin) throws InterruptedException {
		Thread.sleep(5000);

		WebElement medicationinput = null;

		int initialQuantity = 0;
		Task_Name = action;

		SoftAssert softAssert = new SoftAssert();

		WebElement stock = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Stock']")));
		stock.click();

		if (drugname == null || drugname.isEmpty()) {
			System.out.println("No more data to process. Exiting the test.");
			inputdata = "\n" + action + "\n" + "Medication Name: is Empty" + "\n";
			return;
		}

		WebElement Displayinstock = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display In Stock Only']")));
		Displayinstock.click();

		WebElement Displayimprest = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display Imprest Only']")));
		Displayimprest.click();

		medicationinput = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Medication...']")));
		medicationinput.sendKeys(drugname);

		WebElement searchbutton = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='button submit-button']")));
		searchbutton.click();
		Thread.sleep(5000);

		String drugName = drugname;
		int closingParenthesisIndex = drugName.indexOf(')');
		String drugNameWithoutBrand = (closingParenthesisIndex != -1 && closingParenthesisIndex + 2 < drugName.length())
				? drugName.substring(closingParenthesisIndex + 2)
				: drugName;

		System.out.println("From Excel: " + drugNameWithoutBrand);
		String stockValue = null;
		String MediName = null;

		try {
			String medXpath = "//td[1]";
			WebElement MedicationName = driver.findElement(By.xpath(medXpath));
			MediName = MedicationName.getText();
			System.out.println("From Stocktake: " + MediName);
		} catch (Exception e) {

		}

		try {
			String stockXpath = "//td[4]";
			WebElement stockElement = driver.findElement(By.xpath(stockXpath));
			stockValue = stockElement.getText().trim();

			String numericPart = stockValue.replaceAll("[^0-9]", "");
			int valueToCompare = Integer.parseInt(numericPart);

			Thread.sleep(2000);
			initialQuantity = valueToCompare;
			System.out.println("Before Stock: " + initialQuantity);
			Thread.sleep(3000);

		} catch (Exception e) {

		}

		WebElement Outgoing = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Outgoing']")));
		Outgoing.click();

		WebElement writenote = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='note-modal']")));
		writenote.click();
		writenote.sendKeys("Outgoing imprest");

		WebElement imprest = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
		imprest.click();

		Thread.sleep(2000);

		try {
			WebElement medicationInput = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
			medicationInput.click();
			medicationInput.sendKeys(drugname);

			Thread.sleep(3000);

			WebElement optionElement = driver.findElement(By.xpath("//li[@role='option']"));
			String optionText = optionElement.getText();

			if ("No available options".equals(optionText)) {

				inputdata = "\n" + "Medication Name: " + drugname + "\n" + "Drug Dropdown: " + optionText + "\n" + "\n"
						+ "No Medication found" + "\n"
						+ "Message From The QA: Entered Medication for Outgoing imprest not found" + "\n";
				Task_Name = action;

				return;
			} else {

				List<WebElement> dropdownOptions = driver
						.findElements(By.xpath("//li[contains(@class, 'p-dropdown-item')]"));
				for (WebElement option : dropdownOptions) {
					String optionText1 = option.getText().trim();
					if (optionText1.contains(drugname)) {
						// Found a match, click on the option
						Thread.sleep(1000);
						option.click();
						break;
					}
				}
			}
		} catch (Exception e) {
		}

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));

		WebElement quantityInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter quantity']")));
		quantityInput.click();
		quantityInput.sendKeys(drugqty);

		WebElement addButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		addButton.click();

		String selectedDrugtext = driver.findElement(By.xpath("//td[1]/p[1]")).getText();

		String selectedQtytext = driver.findElement(By.xpath("//tr[1]/td[2]/p[1]")).getText().trim();
		String addedqtytext = selectedQtytext.replaceAll("\\(.*?\\)", "").trim();
		System.out.println("select qty =  " + addedqtytext);
		Thread.sleep(1000);
		String numericAdd = addedqtytext.replaceAll("[^0-9]", "");
		int addedqtyint = Integer.parseInt(numericAdd);
		double abcAsDouble = (double) addedqtyint;

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

		try {
			WebElement wrongCred = wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//*[@id=\"app\"]/div/div[3]/div[1]/div/div[2]/div[2]/div/div/div[2]/p[2]")));
			String ErrorMessage = wrongCred.getText();
			if ("Invalid login or password/pin code.".equals(ErrorMessage)) {
				inputdata = "\n" + ErrorMessage + "\n" + "Message From The QA: Entered Credentials are Incorrect" + "\n"
						+ "User Name : " + username + "\n" + "Password: " + pin + "\n";
				Task_Name = action;
				return;
			}
		} catch (Exception e) {
			//
		}

		try {
			WebElement element = driver.findElement(By.xpath("//p[contains(text(),'{')]"));
			String elementText = element.getText();
			System.out.println("Text from element: " + elementText);

			inputdata = "\n" + action + "\n" + "Error message: " + elementText + "\n"
					+ "Message From The QA: Entered Medication for Outgong imprest is insufficient stock QTY" + "\n"
					+ "Medication Name: " + drugname + "\n" + "Outgoing imprest quantity: " + addedqtyint + "\n";
			return;

		} catch (Exception e) {
			System.out.println("We have stock to Destroy imprest");

		}

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

		inputdata = "\n" + "Transaction Type: " + action + "\n" + "Drug Name: " + selectedDrugtext + "\n"
				+ "Outgoing Quantity:  " + addedqtyint + "\n" + "Current Stock: " + initialQuantity + "\n"
				+ "Final Stock: " + expectedquantity + "\n";

		Task_Name = action;

		softAssert.assertEquals(expectedquantity, ExpectedQty, "final stock is not match with Expected stock");
		softAssert.assertTrue(drugname.equalsIgnoreCase(selectedDrugtext),
				"Medication Name mismatch expected [" + selectedDrugtext + "] but found [" + drugname + "]");
		softAssert.assertEquals(abcAsDouble, Double.parseDouble(drugqty), "Quantity mismatch");
		softAssert.assertAll();

	}
}
