package strongroom;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;
import objects.NotificationPage;

public class TransferinPatient extends Base {
	private static NotificationPage notificationPage;

	public static void transferinPatient(String action, String location, String drugname, String transaction_id,
			String resident, String drugqty, String note, String username, String pin) throws InterruptedException {

		WebElement medication = null; // Declare medication outside the try block
		int actualValue = 0;

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

		WebElement Resident = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Resident...']")));
		Resident.clear(); // Clear the field before entering a new drug name
		Resident.sendKeys(resident);

		WebElement active = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//p[@class='active-select-filter select-filter-item']")));
		Actions actions = new Actions(driver);
		actions.moveToElement(active).click().perform();

		WebElement searching = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='button submit-button']")));
		searching.click();
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
		actualValue = valueToCompare;
		System.out.println("Before Transfer in Stock: " + actualValue);
		
		WebElement transferIn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer In']")));
		transferIn.click();

		WebElement enterLocation = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@placeholder='Type in location to receive from']")));
		enterLocation.click();
		enterLocation.sendKeys(location);
		Thread.sleep(2000);
		
		WebElement Location = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"pv_id_5_list\"]/li")));  ///html/body/div[2]/div/ul/li
		String Loc = Location.getText();
		if ("No available options".equals(Loc)) {
			inputdata = "\n" + "Message From The QA: Entered Location for Transfer In not found -> " + location + "\n";
			Task_Name = action;
			return;
		}
		
		Thread.sleep(5000);
		WebElement selectlocation = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class, 'p-dropdown-item')]")));
		String selectedLocation = selectlocation.getText();

		selectlocation.click();

		WebElement writenote = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='note-modal']")));
		writenote.click();
		writenote.sendKeys(note);

		WebElement imprest = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Resident Medication']")));
		imprest.click();

		WebElement Residentinput = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']")));
		Residentinput.click();
		Residentinput.sendKeys(resident);
		
		WebElement searchButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		searchButton.click();
		Thread.sleep(2000);	
		try {
			WebElement searchresident = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"app\"]/div/div[3]/div[1]/div/div[2]/div[2]/div/div/div[2]/form/div/div[2]/div/div/h4")));  ///html/body/div[2]/div/ul/li
			String Search_Result = searchresident.getText();
			if ("No results found.".equals(Search_Result)) {
				inputdata = "\n" + "Message From The QA: Mentioned resident [" + resident +"] not found" + "\n";
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

		try {
			WebElement medicationInput = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
			medicationInput.click();

			medicationInput.sendKeys(drugname);

			Thread.sleep(3000);
			WebElement optionElement = driver.findElement(By.xpath("//li[@role='option']"));
			String optionText = optionElement.getText();
			if ("No available options".equals(optionText)) {
				// System.out.println("Printing data into ClickUp: " + optionText);

				inputdata = "\n" + "Transfer In Imprest Location: " + location + "\n" + "Medication Name: " + drugname
						+ "\n" + "Drug Drop down: " + optionText + "\n" + "\n" + "No Medication found" + "\n" + "Entered Medication [" + drugname + "] for Transfer In not found"
						+ "\n";
				;
				Task_Name = action;

				return;
			} else {

				List<WebElement> dropdownOptions1 = driver
						.findElements(By.xpath("//li[contains(@class, 'p-dropdown-item')]"));
				for (WebElement option : dropdownOptions1) {
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
			//
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
		Thread.sleep(6000);
		try {
			WebElement wrongCred = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/p[2]")));
			String ErrorMessage = wrongCred.getText();
			if ("Invalid login or password/pin code.".equals(ErrorMessage)) {
				inputdata = "\n" + ErrorMessage + "\n" +"Message From The QA: Entered Credentials are Incorrect" + "\n" + "User Name : " + username
						+ "\n" + "Password: " + pin + "\n";
				Task_Name = action;
				return;
			}
		}catch (Exception e) {
			//
		}
		
		
		clearbutton.click();
		Thread.sleep(2000);

		medication.clear();
		medication.sendKeys(drugname);
		Thread.sleep(1000);

		Resident.sendKeys(resident);
		Thread.sleep(1000);

		searching.click();
		Thread.sleep(3000);
		

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
		String numericPart1 = newstockValue.replaceAll("[^0-9]", "");

		int valueToCompare1 = Integer.parseInt(numericPart1);
		int actualValue1 = valueToCompare1;
		System.out.println("After Transfer in Stock: " + actualValue1);
		
		int ExpectedQty = actualValue + abc;

		inputdata = "\n" + "Transfer In Imprest Location: " + enteredLocation + "\n" + "Transferin Imprest Drug Name: "
				+ selectedDrug + "\n" + "Transferin Imprest in quantity:  " + abc + "\n" + "Current Stock: "
				+ actualValue + "\n" + "Final Stock: " + actualValue1 + "\n";

		Task_Name = action;

		softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");

		softAssert.assertEquals(selectedLocation, location, "Location Name mismatch");

		softAssert.assertEquals(cleanedResident, resident, "Resident Name mismatch");

		softAssert.assertEquals(selectedDrug, formattedDrugName, "Medication Name mismatch");

		softAssert.assertEquals(abcAsDouble, Double.parseDouble(drugqty), "Quantity mismatch");

		softAssert.assertAll();

	}
}