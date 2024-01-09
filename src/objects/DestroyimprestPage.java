package objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DestroyimprestPage extends ExcelUtils {

	private static final By DESTROY_BUTTON_LOCATOR = By.xpath("//button[normalize-space()='Destroy']");
	private static final By NOTE_LOCATOR = By.xpath("//textarea[@name='notes' and @id='note-modal']");
	private static final By METHOD_LOCATOR = By.xpath("//input[@placeholder='Method of Destruction']");
	private static final By COURIER_NAME_LOCATOR = By.xpath("//input[@placeholder='Courier Name']");
	private static final By COURIER_NOTES_LOCATOR = By.xpath("//input[@placeholder='Courier Notes']");
	private static final By IMPREST_LOCATOR = By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']");
	private static final By MEDICATION_LOCATOR = By.xpath("//input[@placeholder='Select Medication']");
	private static final By MEDICATION_DROPDOWN_LOCATOR = By.xpath("//li[contains(@class, 'p-dropdown-item')]");
	private static final By MEDICATION_PLACEHOLDER_LOCATOR = By.xpath("//input[@placeholder='Select Medication']");
	private static final By QTY_LOCATOR = By.xpath("//input[@placeholder='Enter qty']");
	private static final By ADD_LOCATOR = By.xpath("//p[@class='submit-button blue-button']");
	private static final By DESTROY_COMPLATE_LOCATOR = By.xpath("//p[@class='regular-button complete-button']");

	private WebDriverWait wait;
	private WebDriver driver;

	List<String> drugNames = readDrugNamesFromExcel("output.xlsx");
	List<String> innumbers = readInnumbersFromExcel("output.xlsx");

	private static int searchCount = -1; // drug
	private static int searchCount1 = -1; // quantity

	public Stocktakepage stocktakepage;

	public DestroyimprestPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.stocktakepage = new Stocktakepage(driver, wait); // Initialize the instance

	}

	public void Destroy() {

		WebElement Destroy = wait.until(ExpectedConditions.elementToBeClickable(DESTROY_BUTTON_LOCATOR));
		Destroy.click();

		// Wait for the modal to be invisible before proceeding
	}

	public void writenote() {

		WebElement writenote = wait.until(ExpectedConditions.elementToBeClickable(NOTE_LOCATOR));
		writenote.click();
		writenote.sendKeys("Destroy imprest");
	}

	public void MethodOFDestruction() {

		WebElement MethodOFDestruction = wait.until(ExpectedConditions.elementToBeClickable(METHOD_LOCATOR));
		MethodOFDestruction.click();
		MethodOFDestruction.sendKeys("MethodOFDestruction");
	}

	public void CourierNameandNotes() {

		WebElement CourierName = wait.until(ExpectedConditions.elementToBeClickable(COURIER_NAME_LOCATOR));
		CourierName.click();
		CourierName.sendKeys("CourierName");

		WebElement CourierNotes = wait.until(ExpectedConditions.elementToBeClickable(COURIER_NOTES_LOCATOR));
		CourierNotes.click();
		CourierNotes.sendKeys("CourierNotes");

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

		// Click on the quantity field with the specified placeholder
		WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(QTY_LOCATOR));
		quantityInput.click();

		searchCount1++;

		// String drugqty = innumbers.get(searchCount1);
		String drugqty = innumbers.get(searchCount1 % innumbers.size());

		quantityInput.sendKeys(drugqty);

		WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(ADD_LOCATOR));
		addButton.click();

		Thread.sleep(3000);

		WebElement completeButton = wait.until(ExpectedConditions.elementToBeClickable(DESTROY_COMPLATE_LOCATOR));
		completeButton.click();

	}

}
