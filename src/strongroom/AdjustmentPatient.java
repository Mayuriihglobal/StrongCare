package strongroom;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

public class AdjustmentPatient extends Base {

	public static void adjustmentPatient(String action, String location, String drugname, String transaction_id,
			String resident, String drugqty, String note, String username, String pin, String username1, String pin1)
			throws InterruptedException {

		Thread.sleep(5000);
		Task_Name = action;

		WebElement medicationinput = null;
		int initialQuantity = 0;

		SoftAssert softAssert = new SoftAssert();

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

		String drugName = drugname;
		int closingParenthesisIndex = drugName.indexOf(')');
		String drugNameWithoutBrand = drugName.substring(closingParenthesisIndex + 2);
		String formattedDrugName = drugNameWithoutBrand.substring(0, 1).toUpperCase()
				+ drugNameWithoutBrand.substring(1);
		System.out.println(formattedDrugName);

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

		String stockValue = null;
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

		WebElement Adjustment = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Adjustment']")));
		Adjustment.click();

		WebElement Transectionid = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Transaction ID...']")));
		Transectionid.click();
		Transectionid.sendKeys(transaction_id);
		Thread.sleep(2000);

		WebElement writenote = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='note-modal']")));
		writenote.click();
		writenote.sendKeys("Adjustment Patient");

		WebElement Residentmedication = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Resident Medication']")));
		Residentmedication.click();

		WebElement Residentinput = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']")));
		Residentinput.click();

		Residentinput.sendKeys(resident);

		WebElement searchButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		searchButton.click();

		Thread.sleep(3000);

		List<WebElement> residentdropdown = driver.findElements(
				By.xpath("//*[@id=\"app\"]/div/div[3]/div[1]/div/div[2]/div[2]/div/div/div[2]/form/div/div[2]/div"));
		WebElement individual = driver.findElement(By.xpath("//div[@class='patient-result-info']//p[1]"));
		// Check if there is at least one element
		if (!residentdropdown.isEmpty()) {
			// Click on the first element
			for (int i = 0; i < residentdropdown.size(); i++) {

				String Name = individual.getText();
				System.out.println(Name);

				Thread.sleep(1000);
			}
			individual.click();

		} else {
			System.out.println("No matching elements found in residentdropdown.");
		}

		Thread.sleep(3000);

		WebElement result = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@class='individual-patient-container']//p[1]")));

		String SelectedResident = result.getText();

		String cleanedResident = SelectedResident.replace("Name: ", "");

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
					+ "] for Adjustment not found" + "\n";
			Task_Name = action;
			return;
		}

		WebElement addButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		addButton.click();
		Thread.sleep(1000);

		WebElement quantityInput = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//tr[@class='drug-entry-transfer']//input[@type='number']")));
		quantityInput.click();
		quantityInput.sendKeys(drugqty);

		WebElement selectedDrugtextElement = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//td[1]/p[1]")));
		String selectedDrugtext = selectedDrugtextElement.getText();

		String selectedQtytext = driver.findElement(By.xpath("//td[2]/p[1]")).getText().trim();
		String addedqtytext = selectedQtytext.replaceAll("\\(.*?\\)", "").trim();
		System.out.println("select qty =  " + addedqtytext);
		Thread.sleep(1000);
		String numericqty = addedqtytext.replaceAll("[^0-9]", "");
		int addedqtyint = Integer.parseInt(numericqty);
		// double addedqtydouble = (double) addedqtyint;

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
		Thread.sleep(2000);

		try {

			WebElement usernameInput1 = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));
			usernameInput1.click();
			usernameInput1.clear();
			usernameInput1.sendKeys(username1);

			WebElement passwordInput1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='PIN/Password']")));
			passwordInput1.click();
			passwordInput1.sendKeys(pin1);

			WebElement greenButton1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='green-button']")));
			greenButton1.click();

		} catch (Exception e) {

		}

		try {
			WebElement wrongCred = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
					"/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/p[2]")));
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

		Thread.sleep(5000);

		searchbutton.click();
		Thread.sleep(5000);

		String newstockValue = null;
		for (int i = 2; i <= 10; i++) {
			String xpath = "//tr[" + i + "]/td[2]";
			WebElement residentNameElement1 = driver.findElement(By.xpath(xpath));
			if (residentNameElement1 != null) {
				String residentName = residentNameElement1.getText();
				if (residentName.equals(resident)) {
					String stockXpath = "//tr[" + i + "]/td[4]";
					WebElement stockElement = driver.findElement(By.xpath(stockXpath));
					newstockValue = stockElement.getText().trim();
					break;
				}
			} else {
				System.out.println("Resident Name element not found at row " + i);
				break;
			}
		}

		// Extract numeric part from the string (remove non-numeric characters)
		String numericpartExpected = newstockValue.replaceAll("[^0-9]", "");

		int Expectedint = Integer.parseInt(numericpartExpected);
		System.out.println("(--): " + Expectedint);

		inputdata = "\n" + action + "\n" + "Medication Name: " + selectedDrugtext + "\n" + "Resident Name: " + resident
				+ "\n" + "Adjustment Patient Quantity:  " + addedqtyint + "\n" + "Current Stock: " + initialQuantity
				+ "\n" + "Final Stock: " + Expectedint + "\n";

		softAssert.assertEquals(Expectedint, Integer.parseInt(drugqty), "final stock is not match with Expected stock");
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