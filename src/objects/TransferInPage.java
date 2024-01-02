package objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TransferInPage extends ExcelUtils {

	private static final By TRANSFERIN_BUTTON_LOCATOR = By.xpath("//button[normalize-space()='Transfer In']");
	private static final By LOCATION_BUTTON_LOCATOR = By
			.xpath("//input[@placeholder='Type in location to receive from']");
	private static final By LOCATION_DROPDOWN_LOCATOR = By.xpath("//li[contains(@class, 'p-dropdown-item')]");
	private static final By NOTE_LOCATOR = By.xpath("//textarea[@name='notes' and @id='note-modal']");
	private static final By IMPREST_LOCATOR = By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']");
	private static final By MEDICATION_LOCATOR = By.xpath("//input[@placeholder='Select Medication']");
	private static final By MEDICATION_DROPDOWN_LOCATOR = By.xpath("//li[contains(@class, 'p-dropdown-item')]");
	private static final By MEDICATION_PLACEHOLDER_LOCATOR = By.xpath("//input[@placeholder='Select Medication']");
	private static final By QTY_LOCATOR = By.xpath("//input[@placeholder='Enter qty']");
	private static final By ADD_LOCATOR = By.xpath("//p[@class='submit-button blue-button']");
	private static final By COMPLATE_LOCATOR = By.xpath("//p[@class='regular-button complete-button']");

	private WebDriverWait wait;
	private WebDriver driver;

	List<String> drugNames = readDrugNamesFromExcel("output.xlsx");
	List<String> innumbers = readInnumbersFromExcel("output.xlsx");
	List<String> location = readLocationFromExcel("output.xlsx");

	public TransferInPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
	}

	public void transferIn() {
		WebElement transferIn = wait.until(ExpectedConditions.elementToBeClickable(TRANSFERIN_BUTTON_LOCATOR));
		transferIn.click();
	}

	public void enterLocation() throws InterruptedException {
		WebElement enterLocation = wait.until(ExpectedConditions.elementToBeClickable(LOCATION_BUTTON_LOCATOR));
		enterLocation.click();

		int locationIndex = (searchCoun2 - 1) % location.size();

		enterLocation.sendKeys(location.get(locationIndex));

		Thread.sleep(3000);
		// Locate the location options
		List<WebElement> dropdownlocation = driver.findElements(LOCATION_DROPDOWN_LOCATOR);
		for (WebElement option : dropdownlocation) {

			// String loc2 = location.get(searchCoun2);
			String loc2 = location.get(searchCoun2 % location.size());

			if (option.getText().trim().equals(loc2)) {
				// Found a match, click on the option
				Thread.sleep(3000);

				searchCoun2++;

				Thread.sleep(3000);

				option.click();

				break;
			}

		}

	}

	public void writenote() {
		WebElement writenote = wait.until(ExpectedConditions.elementToBeClickable(NOTE_LOCATOR));
		writenote.click();
		writenote.sendKeys("Transferr in");
	}

	public void imprest() throws InterruptedException {
		WebElement imprest = wait.until(ExpectedConditions.elementToBeClickable(IMPREST_LOCATOR));
		imprest.click();

		WebElement medicationInput = wait.until(ExpectedConditions.elementToBeClickable(MEDICATION_LOCATOR));
		medicationInput.click();

		// String drugname1 = drugNames.get(searchCount);
		String drugname1 = drugNames.get(searchCount % drugNames.size());

		medicationInput.sendKeys(drugname1);

		Thread.sleep(3000);
		// Locate the dropdown options
		List<WebElement> dropdownOptions1 = driver.findElements(MEDICATION_DROPDOWN_LOCATOR);

		// Iterate through the options to find a match with the entered drug name
		for (WebElement option : dropdownOptions1) {
			if (option.getText().trim().equals(drugname1)) {
				// Found a match, click on the option
				Thread.sleep(3000);
				option.click();
				break;
			}
		}

		wait.until(ExpectedConditions.elementToBeClickable(MEDICATION_PLACEHOLDER_LOCATOR));

		// Click on the quantity field with the specified placeholder
		WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(QTY_LOCATOR));
		quantityInput.click();

		// searchCount++;
		searchCount1++;

		// String drugqty = innumbers.get(searchCount1);
		String drugqty = innumbers.get(searchCount1 % innumbers.size());

		quantityInput.sendKeys(drugqty);

		WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(ADD_LOCATOR));
		addButton.click();

		// Click on the button with the specified class
		WebElement completeButton = wait.until(ExpectedConditions.elementToBeClickable(COMPLATE_LOCATOR));
		completeButton.click();

	}

}
