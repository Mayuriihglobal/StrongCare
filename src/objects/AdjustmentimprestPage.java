package objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdjustmentimprestPage extends ExcelUtils {

	private static final By ADJUSTMENT_BUTTON_LOCATOR = By.xpath("//button[normalize-space()='Adjustment']");
	private static final By TRANSECTIONID_LOCATOR = By
			.xpath("//div[@class='date-entered-field']//input[@placeholder='Transaction ID...']");
	private static final By NOTE_LOCATOR = By.xpath("//textarea[@name='notes' and @id='note-modal']");
	private static final By IMPREST_LOCATOR = By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']");
	private static final By MEDICATION_LOCATOR = By.xpath("//input[@placeholder='Select Medication']");
	private static final By MEDICATION_DROPDOWN_LOCATOR = By.xpath("//li[contains(@class, 'p-dropdown-item')]");
	private static final By MEDICATION_PLACEHOLDER_LOCATOR = By.xpath("//input[@placeholder='Select Medication']");
	private static final By ACTUAL_QTY_LOCATOR = By.xpath("//input[@type='number']");
	private static final By ADD_LOCATOR = By.xpath("//p[@class='submit-button blue-button']");
	private static final By EXPECTEDQTY_LOCATOR = By.xpath("//td/p[contains(@style, 'font-size: 1em;')]");
	private static final By ADJUSTMENT_SUBMIT_LOCATOR = By.xpath("//p[@class='regular-button complete-button']");

	private WebDriverWait wait;
	private WebDriver driver;

	List<String> drugNames = readDrugNamesFromExcel("Agedcare.xlsx");
	List<String> transectionids = readTransectionIDFromExcel("Agedcare.xlsx");

	private static int searchCount = -1; // drug
	private static int searchCoun4 = 0; // TransectionID

	public Stocktakepage stocktakepage;

	public AdjustmentimprestPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.stocktakepage = new Stocktakepage(driver, wait); // Initialize the instance

	}

	public void Adjustment() {

		WebElement Adjustment = wait.until(ExpectedConditions.elementToBeClickable(ADJUSTMENT_BUTTON_LOCATOR));
		Adjustment.click();

		// Wait for the modal to be invisible before proceeding
	}

	public void Transectionid() {

		WebElement Transectionid = wait.until(ExpectedConditions.elementToBeClickable(TRANSECTIONID_LOCATOR));
		Transectionid.click();

		searchCoun4++;

		// String drugqty = innumbers.get(searchCount1);
		String transectionid = transectionids.get(searchCoun4 % transectionids.size());

		Transectionid.sendKeys(transectionid);

	}

	public void writenote() {

		WebElement writenote = wait.until(ExpectedConditions.elementToBeClickable(NOTE_LOCATOR));
		writenote.click();
		writenote.sendKeys("Adjustment imprest");
	}

	public void imprest() throws InterruptedException {

		WebElement imprest = wait.until(ExpectedConditions.elementToBeClickable(IMPREST_LOCATOR));
		imprest.click();

		WebElement medicationInput = wait.until(ExpectedConditions.elementToBeClickable(MEDICATION_LOCATOR));
		medicationInput.click();

		searchCount++;

		// String drugname1 = drugNames.get(searchCount);
		String drugname1 = drugNames.get(searchCount % drugNames.size());

		medicationInput.sendKeys(drugname1);

		Thread.sleep(3000);
		// Locate the dropdown options
		List<WebElement> dropdownOptions1 = driver.findElements(MEDICATION_DROPDOWN_LOCATOR);

		// Iterate through the options to find a match with the entered drug name
		for (WebElement option : dropdownOptions1) {
			String optionText = option.getText().trim();
			if (optionText.contains(drugname1)) {
				// Found a match, click on the option
				Thread.sleep(3000);
				option.click();
				break;
			}
		}

		wait.until(ExpectedConditions.elementToBeClickable(MEDICATION_PLACEHOLDER_LOCATOR));

		WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(ADD_LOCATOR));
		addButton.click();

		// Locate the ExpectedQty element
		WebElement ExpectedQty = driver.findElement(EXPECTEDQTY_LOCATOR);

		// Extract the text content from the ExpectedQty element
		String ExpectedQtyText = ExpectedQty.getText().trim();

		// Convert the extracted text to an integer, add 1 to it
		int originalValue = Integer.parseInt(ExpectedQtyText);
		int updatedValue = originalValue + 1;

		// Click on the quantity field with the specified placeholder
		WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(ACTUAL_QTY_LOCATOR));
		quantityInput.click();

		quantityInput.sendKeys(String.valueOf(updatedValue)); // Send the updated value

		Thread.sleep(3000);

		WebElement completeButton = wait.until(ExpectedConditions.elementToBeClickable(ADJUSTMENT_SUBMIT_LOCATOR));
		completeButton.click();

	}

}
