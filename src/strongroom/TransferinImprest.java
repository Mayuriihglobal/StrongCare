package strongroom;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;

public class TransferinImprest extends Base {

	public static void transferinImprest(String action, String location, String drugname, String transaction_id,
			String resident, String drugqty, String note, String username, String pin) throws InterruptedException {

		Thread.sleep(5000);
		Task_Name = action;

		WebElement medicationinput = null;
		String ExpectedQuantity = "-";
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

		WebElement Displayinstockfilter = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display In Stock Only']")));
		Displayinstockfilter.click();

		WebElement Displayimprestfilter = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display Imprest Only']")));
		Displayimprestfilter.click();

		medicationinput = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Medication...']")));
		medicationinput.sendKeys(drugname);

		WebElement searchbutton = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='button submit-button']")));
		searchbutton.click();
		Thread.sleep(3000);

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

		}
		try {
			WebElement Expectedcolumn = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));

			ExpectedQuantity = Expectedcolumn.getText().trim();
			String numericPart = ExpectedQuantity.replaceAll("[^0-9]", "");
			int valueToCompare = Integer.parseInt(numericPart);

			initialQuantity = valueToCompare;

		} catch (Exception e) {

		}

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

		String selectedLocation = selectlocation.getText();

		selectlocation.click();

		WebElement writenote = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='note-modal']")));
		writenote.click();
		writenote.sendKeys(note);

		WebElement imprest = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
		imprest.click();

		try {
			WebElement medicationInput = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
			medicationInput.click();
			medicationInput.sendKeys(drugname);

			Thread.sleep(3000);

			WebElement optionElement = driver.findElement(By.xpath("//li[@role='option']"));
			String optionText = optionElement.getText();
			if ("No available options".equals(optionText)) {

				inputdata = "\n" + action + "\n" + "Location Name: " + location + "\n" + "Medication Name: " + drugname
						+ "\n" + "Select Medication Dropdown: " + optionText + "\n" + "\n" + "No Medication found"
						+ "\n";
				;
				return;

			} else {
				List<WebElement> dropdownOptions1 = driver
						.findElements(By.xpath("//li[contains(@class, 'p-dropdown-item')]"));
				for (WebElement option : dropdownOptions1) {
					String optionText1 = option.getText().trim();
					if (optionText1.contains(drugname)) {
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
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter qty']")));
		quantityInput.click();
		quantityInput.sendKeys(drugqty);

		WebElement addButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		addButton.click();

		String selectedDrugtext = driver.findElement(By.xpath("//td[1]/p[1]")).getText();
		String selectedQtytext = driver.findElement(By.xpath("//p[1]/span[1]")).getText().trim();
		String addedqtytext = selectedQtytext.replaceAll("\\(.*?\\)", "").trim();
		System.out.println("select qty =  " + addedqtytext);
		Thread.sleep(1000);
		String numericqty = addedqtytext.replaceAll("[^0-9]", "");
		int addedqtyint = Integer.parseInt(numericqty);
		double addedqtydouble = (double) addedqtyint;

		System.out.println("selected Converted QTY  " + addedqtydouble);

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

		searchbutton.click();
		Thread.sleep(1000);

		WebElement Expectedquantity = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));
		String Expectedtext = Expectedquantity.getText().trim();
		String numericpartExpected = Expectedtext.replaceAll("[^0-9]", "");
		int Expectedint = Integer.parseInt(numericpartExpected);
		System.out.println("After opration Stock Qty: " + Expectedint);

		int ExpectedQty = initialQuantity + addedqtyint;

		inputdata = "\n" + action + "\n" + "Location Name: " + location + "\n" + "Medication Name: " + selectedDrugtext
				+ "\n" + "Transferin Imprest Quantity:  " + addedqtyint + "\n" + "Current Stock: " + initialQuantity
				+ "\n" + "Final Stock: " + Expectedint + "\n";

		softAssert.assertEquals(Expectedint, ExpectedQty, "final stock is not match with Expected stock");
		softAssert.assertEquals(selectedLocation, location, "Location Name mismatch");
		softAssert.assertEquals(selectedDrugtext, formattedDrugName, "Medication Name mismatch");
		softAssert.assertEquals(addedqtydouble, Double.parseDouble(drugqty), "Quantity mismatch");
		softAssert.assertAll();

	}
}