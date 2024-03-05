package strongroom;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

public class OutgoingPatient extends Base {

	public static void outgoingPatient(String action, String location, String drugname, String transaction_id,
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
		String drugNameWithoutBrand = (closingParenthesisIndex != -1 && closingParenthesisIndex + 2 < drugName.length())
				? drugName.substring(closingParenthesisIndex + 2)
				: drugName;

		String stockValue = null;
		String MediName = null;
		WebElement residentNameElement = null;
		for (int i = 2; i <= 10; i++) {
			try {
				String xpath = "//tr[" + i + "]/td[2]";
				residentNameElement = driver.findElement(By.xpath(xpath));
			} catch (Exception e) {
				//
			}
			if (residentNameElement != null) {
				String residentName = residentNameElement.getText();
				if (residentName.equals(resident)) {
					String stockXpath = "//tr[" + i + "]/td[4]";
					WebElement stockElement = driver.findElement(By.xpath(stockXpath));
					stockValue = stockElement.getText().trim();
					String medXpath = "//tr[" + i + "]/td[1]";
					WebElement MedicationName = driver.findElement(By.xpath(medXpath));
					MediName = MedicationName.getText();
					System.out.println("From Stocktake: " + MediName);

					break;
				} else {
					stockValue = "0";
				}
			} else {
				stockValue = "0";
			}
		}

		String numericPart = stockValue.replaceAll("[^0-9]", "");
		int valueToCompare = Integer.parseInt(numericPart);

		Thread.sleep(2000);
		initialQuantity = valueToCompare;
		System.out.println("Before opration Stock Qty: " + initialQuantity);

		Thread.sleep(3000);

		WebElement Outgoing = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Outgoing']")));
		Outgoing.click();

		WebElement writenote = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='note-modal']")));
		writenote.click();
		writenote.sendKeys("OutGoing Patient");

		WebElement Residentbutton = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Resident Medication']")));
		Residentbutton.click();

		WebElement Residentinput = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']")));
		Residentinput.click();

		Residentinput.sendKeys(resident);

		WebElement searchButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		searchButton.click();

		try {
			WebElement searchresident = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"//*[@id=\"app\"]/div/div[3]/div[1]/div/div[2]/div[2]/div/div/div[2]/form/div/div[2]/div/div/h4"))); /// html/body/div[2]/div/ul/li
			String Search_Result = searchresident.getText();
			if ("No results found.".equals(Search_Result)) {
				inputdata = "\n" + "Message From The QA: Mentioned resident [" + resident + "] not found" + "\n";
				Task_Name = action;
				return;
			}
		} catch (Exception e) {
			//
		}

		WebElement result = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//form/div/div[2]/div/div/div/p[1]")));
		String SelectedResident = result.getText();
		String cleanedResident = SelectedResident.replace("Name: ", "");

		result.click();

		Thread.sleep(2000);

		WebElement medicationbox = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='pom-select']")));
		medicationbox.click();
		Thread.sleep(3000);

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

		System.out.println("**Trimmed Drugname** " + trimmedDrugName);

		// Check if the drugname is present in the dropdown options
		boolean drugFound = false;

		for (WebElement option : dropdownOptions) {
			Thread.sleep(1000);
			if (option.getText().contains(trimmedDrugName)) {
				drugFound = true;
				option.click();
				break;
			} else if (drugname.contains(" ") && option.getText().contains(drugname.split(" ")[1])) {
				drugFound = true;
				option.click();
				break;
			} else if (option.getText().contains(drugname)) { // Check if option contains the entire drugname
				drugFound = true;
				option.click();
				break;
			} else if (option.getText().startsWith(drugname)) {
				// Check if the option starts with the input drugname
				drugFound = true;
				option.click();
				break;

			}
			Thread.sleep(2000);

		}
		if (!drugFound) {
			inputdata = "\n" + "No Medication found" + "\n" + "Entered Medication [" + drugname
					+ "] for Destroy not found" + "\n";
			Task_Name = action;
			return;
		}

		WebElement quantityInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Quantity']")));
		quantityInput.clear();

		quantityInput.sendKeys(drugqty);

		WebElement addButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		addButton.click();

		String selectedDrugtext = driver.findElement(By.xpath("//td[1]/p[1]")).getText();

		System.out.println("Selected drug text" + selectedDrugtext);

		String selectedQtytext = driver.findElement(By.xpath("//tr[1]/td[2]/p[1]")).getText().trim();
		String addedqtytext = selectedQtytext.replaceAll("\\(.*?\\)", "").trim();
		System.out.println("select qty =  " + addedqtytext);
		Thread.sleep(1000);
		String numericAdd = addedqtytext.replaceAll("[^0-9]", "");
		int addedqtyint = Integer.parseInt(numericAdd);
		double abcAsDouble = (double) addedqtyint;

		int drugQtyAsInt = Integer.parseInt(drugqty); // Convert the quantity from the input parameter to an integer
		int add1AsInt = Integer.parseInt(numericAdd); // Convert the quantity from the page to an integer

		int difference = drugQtyAsInt - add1AsInt;

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

			inputdata = "\n" + action + "\n" + "Error message " + elementText + "\n"
					+ "Message From The QA: Entered Medication for Destroy insufficient stock QTY" + "\n"
					+ "Medication Name: " + drugname + "\n" + "Destroy quantity" + add1AsInt;

			Task_Name = action;

			return;

		} catch (Exception e) {
			System.out.println("We have stock to Transfer out");

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

		if (difference == 0) {
			// If difference is 0, print this
			inputdata = "\n" + action + "\n" + "Medication Name: " + selectedDrugtext + "\n" + "Resident Name: "
					+ resident + "\n" + "Outgoing Quantity from Sheet:  " + drugqty + "\n" + "Outgoing Added Quantity: "
					+ add1AsInt + "\n" + "Current Stock: " + initialQuantity + "\n" + "Final Stock: " + expectedquantity
					+ "\n";
		} else {
			// If difference is not 0, print this
			inputdata = "\n" + action + "\n" + "Medication Name: " + selectedDrugtext + "\n" + "Resident Name: "
					+ resident + "\n" + "Outgoing Quantity from Sheet:  " + drugqty + "\n" + "Outgoing Added Quantity: "
					+ add1AsInt + "\n" + "DIFFERENCE BETWEEN SHEET INPUT QUANTITY AND ADDED QUANTITY: " + difference
					+ "\n" + "Current Stock: " + initialQuantity + "\n" + "Final Stock: " + expectedquantity + "\n";
		}

		softAssert.assertEquals(expectedquantity, ExpectedQty, "final stock is not match with Expected stock");
		softAssert.assertEquals(cleanedResident, resident, "Resident Name mismatch");
		softAssert.assertTrue(drugname.equalsIgnoreCase(selectedDrugtext),
				"Medication Name mismatch expected [" + selectedDrugtext + "] but found [" + drugname + "]");
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
