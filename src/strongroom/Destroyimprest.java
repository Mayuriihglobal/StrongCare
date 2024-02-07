package strongroom;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.SoftAssert;
import objects.NotificationPage;

public class Destroyimprest extends Base {
	private static NotificationPage notificationPage;

	
	public static void destroyimprest(String action, String location, String drugname, String transaction_id, String resident,
			String drugqty, String note, String username, String pin) throws InterruptedException {

		if ("Destroy imprest".equals(action)) {
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
			
			WebElement Displayinstock = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display In Stock Only']")));
			Displayinstock.click();

			WebElement Displayimprest = wait.until(ExpectedConditions
					.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display Imprest Only']")));
			Displayimprest.click();

			WebElement medication = wait.until(
					ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Medication...']")));
			medication.clear(); // Clear the field before entering a new drug name
			medication.sendKeys(drugname);

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
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Destroy']")));
			transferIn.click();

			WebElement writenote = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//textarea[@id='note-modal']")));
			writenote.click();
			writenote.sendKeys("Transferr in imprest");

			WebElement MethodOFDestruction = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Method of Destruction']")));
			MethodOFDestruction.click();
			MethodOFDestruction.sendKeys("MethodOFDestruction");

			WebElement CourierName = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Courier Name']")));
			CourierName.click();
			CourierName.sendKeys("CourierName");

			WebElement CourierNotes = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Courier Notes']")));
			CourierNotes.click();
			CourierNotes.sendKeys("CourierNotes");

			WebElement imprest = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
			imprest.click();

			Thread.sleep(2000);

			WebElement medicationInput = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
			medicationInput.click();
			medicationInput.sendKeys(drugname);

			Thread.sleep(3000);

			List<WebElement> dropdownOptions1 = driver
					.findElements(By.xpath("//li[contains(@class, 'p-dropdown-item')]"));
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

			WebElement addButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
			addButton.click();

			String selectedDrug = driver.findElement(By.xpath("//td[1]/p[1]")).getText();

			String selectedQty = driver.findElement(By.xpath("//td[2]/p[1]")).getText().trim();
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

			Displayinstock.click();
			Thread.sleep(1000);

			Displayimprest.click();
			Thread.sleep(1000);

			medication.clear();
			medication.sendKeys(drugname);
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
}
