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
import objects.SignPage;
import test.Util.TestUtil;

public class TransferinImprest extends Base {
	private NotificationPage notificationPage;
	private SignPage signPage;
	private static String inputdata;
	
	@DataProvider
	public Iterator<Object[]> getTestData() {
		ArrayList<Object[]> testData =TestUtil.transferinImprest();
		return testData.iterator();
		
	}

	@Test(dataProvider="getTestData")
	public void transferinImprest(String location, String drugname1, String drugqty) throws InterruptedException {
		
		SoftAssert softAssert = new SoftAssert();
		
		// Opening
		signPage = new SignPage(driver, wait);
		notificationPage = new NotificationPage(driver, wait);
		
		notificationPage.clickNotificationIcon();
		
		WebElement stock = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Stock']")));
		stock.click();
		
		WebElement stockTake = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Stock Take']")));
		stockTake.click();
		
		WebElement Displayinstock = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[@class='active-select-filter select-filter-item']")));
		Displayinstock.click();
		
		WebElement Displayimprest = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//p[normalize-space()='Display Imprest Only']")));
		Displayimprest.click();
		
		WebElement medication = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Medication...']")));
		medication.clear(); // Clear the field before entering a new drug name
		medication.sendKeys(drugname1);
		
		WebElement searching = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='button submit-button']")));
		searching.click();
		
		WebElement expected = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));
		String openB = expected.getText().trim();
		
		System.out.println("(Stock Drug): " + openB);
		String numericPart = openB.replaceAll("[^0-9]", "");

		int valueToCompare = Integer.parseInt(numericPart);
		System.out.println("(Stock): " + valueToCompare);
		Thread.sleep(1000);
		int actualValue = valueToCompare;
		System.out.println("(Stock): " + actualValue);


		
		
		
		
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
		writenote.sendKeys("Transferr in imprest");
		
		WebElement imprest = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
		imprest.click();

		WebElement medicationInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
		medicationInput.click();
		medicationInput.sendKeys(drugname1);	
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
		
		String selectdestroyqty =drugqty;
		String selectMedication = selectedDrug; 
		int selectQty = abc;
		int enteredLocation = abc ;
		Thread.sleep(3000);
		
		WebElement completeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='regular-button complete-button']")));
		completeButton.click();
		
		
		signPage.performSignature();
		Thread.sleep(6000);

		
		
		
		
		
		// Loop through the test execution
		stock.click();
		Thread.sleep(1000);
		
		stockTake.click();
		Thread.sleep(1000);
		
		Displayinstock.click();
		Thread.sleep(1000);
		
		Displayimprest.click();
		Thread.sleep(1000);
		
		medication.clear();
		medication.sendKeys(drugname1);
		Thread.sleep(1000);
		
		searching.click();
		Thread.sleep(1000);
		
		WebElement expected1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//td)[4]")));
		String closeB = expected1.getText().trim();

		System.out.println("(Stock Drug): " + closeB);

		// Extract numeric part from the string (remove non-numeric characters)
		String numericPart1 = closeB.replaceAll("[^0-9]", "");

		int valueToCompare1 = Integer.parseInt(numericPart1);
		System.out.println("(Stock): " + valueToCompare1);
		
		int actualValue1 = valueToCompare1;
		System.out.println("(Stock): " + actualValue1);

		int ExpectedQty = actualValue + selectQty;
		System.out.print(ExpectedQty);

		
		
		inputdata = "\n" + "Transfer In Imprest Location: " + enteredLocation + "\n" + "Transferin Imprest Drug Name: "
				+ selectMedication + "\n" + "Transferin Imprest in quantity:  "  + "\n" + "Current Stock: "
				+ actualValue + "\n" + "Final Stock: " + actualValue1 + "\n";

		softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");
		softAssert.assertAll();
	}
}