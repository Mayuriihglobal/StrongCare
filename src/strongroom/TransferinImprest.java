package strongroom;

import java.util.ArrayList;
import java.util.Iterator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import objects.NotificationPage;
import test.Util.TestUtil;

public class TransferinImprest extends Base {
	private NotificationPage notificationPage;
	
	@DataProvider
	public Iterator<Object[]> getTestData() {
		ArrayList<Object[]> testData = TestUtil.transferinImprest();
		return testData.iterator();

	}

	@Test(dataProvider="getTestData")
	public void transferinImprest(String action, String location, String drugname, String transaction_id, String resident, 
			String drugqty, String note, String username, String pin) throws InterruptedException {
		
		if (action == "Transfer in Imprest") {
			// Opening
			SoftAssert softAssert = new SoftAssert();
			notificationPage = new NotificationPage(driver, wait);
			notificationPage.clickNotificationIcon();
			
			WebElement stock = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Stock']")));
			stock.click();
			
			WebElement stockTake = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Stock Take']")));
			stockTake.click();
			
			WebElement Displayinstock = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display In Stock Only']")));
			Displayinstock.click();
			
			
			WebElement Displayimprest = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display Imprest Only']")));
			Displayimprest.click();
			
			WebElement medication = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Medication...']")));
			medication.clear(); // Clear the field before entering a new drug name
			medication.sendKeys(drugname);
			
			WebElement searching = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='button submit-button']")));
			searching.click();
			Thread.sleep(3000);
			
			
			WebElement expected = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));
			String openB = expected.getText().trim();
			String numericPart = openB.replaceAll("[^0-9]", "");
			int valueToCompare = Integer.parseInt(numericPart);
			Thread.sleep(1000);
			int actualValue = valueToCompare;
			System.out.println("Before opration Stock Qty: " + actualValue);
			
			
			// script
			
			WebElement transferIn = wait.until(ExpectedConditions.elementToBeClickable( By.xpath("//button[normalize-space()='Transfer In']")));
			transferIn.click();
			
			WebElement enterLocation = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Type in location to receive from']")));
			enterLocation.click();
			enterLocation.sendKeys(location);
			Thread.sleep(5000);
			WebElement selectlocation = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[contains(@class, 'p-dropdown-item')]")));
			selectlocation.click(); 
			
			WebElement writenote = wait.until(ExpectedConditions.elementToBeClickable(By.xpath( "//textarea[@id='note-modal']")));
			writenote.click(); 
			writenote.sendKeys(note);
			
			WebElement imprest = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
			imprest.click();

			WebElement medicationInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
			medicationInput.click();
			medicationInput.sendKeys(drugname);	
			Thread.sleep(5000);
			
			driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/ul[1]/li[1]")).click();
			Thread.sleep(2000);
			
			WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter qty']")));
			quantityInput.click();
			quantityInput.sendKeys(drugqty);
			
			WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
			addButton.click();
			
			String selectedDrug = driver.findElement(By.xpath("//td[1]/p[1]")).getText();
			
			String selectedQty = driver.findElement(By.xpath("//p[1]/span[1]")).getText().trim();
			String add1 = selectedQty.replaceAll("\\(.*?\\)", "").trim();
			System.out.println("select qty =  " + add1);
			Thread.sleep(1000);
			String numericAdd = add1.replaceAll("[^0-9]", "");
			int abc = Integer.parseInt(numericAdd);
			Thread.sleep(3000);
			
			
			
			WebElement completeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='regular-button complete-button']")));
			completeButton.click();
			
			// Sign off
			WebElement usernameInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(username);
			
			WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='PIN/Password']")));
			passwordInput.click();
			passwordInput.sendKeys(pin);
			
			WebElement greenButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='green-button']")));
			greenButton.click();
			Thread.sleep(6000);
			
			
			
			
			// Loop through the test execution
			
			WebElement clearbutton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button clear-button']")));
			clearbutton.click();
			Thread.sleep(1000);
			
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

			inputdata = "\n" + "Transfer In Imprest Location: " + location + 
					"\n" + "Transferin Imprest Drug Name: "+ selectedDrug + 
					"\n" + "Transferin Imprest in quantity:  " + abc  + 
					"\n" + "Current Stock: "+ actualValue + 
					"\n" + "Final Stock: " + actualValue1 + "\n";

			softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");
			softAssert.assertAll();
		} else {
			System.out.println("Not found this testcase data");
			System.out.println(action);
		}
		
	}
}
