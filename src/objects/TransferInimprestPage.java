package objects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TransferInimprestPage extends ExcelUtils {

	private static final By TRANSFERIN_BUTTON_LOCATOR = By.xpath("//button[normalize-space()='Transfer In']");
	private static final By LOCATION_BUTTON_LOCATOR = By.xpath("//input[@placeholder='Type in location to receive from']");
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

	List<String> drugNames = readDrugNamesFromExcel("Agedcare.xlsx");
	List<String> innumbers = readInnumbersFromExcel("Agedcare.xlsx");
	List<String> location = readLocationFromExcel("Agedcare.xlsx");

	private static int searchCount = -1; // drug
	private static int searchCount1 = -1; // quantity
	private static int searchCoun2 = 0; // location

	public Stocktakepage stocktakepage;

	private String enteredLocation; // Class variable to store the entered location
	private String enteredDrug; // Class variable to store the entered drug
	private String enteredQuantity; // Class variable to store the entered quantity
	private String transferInPageURL; // Class variable to store the Transfer In Page URL
	private String selectedLocation; // Class variable to store the selected location
	private String note; // Class variable to store the note
	private String QtyFromExcel;

	public TransferInimprestPage(WebDriver driver, WebDriverWait wait) {
		this.driver = driver;
		this.wait = wait;
		this.stocktakepage = new Stocktakepage(driver, wait); // Initialize the instance

	}

	public void transferIn() {

		WebElement transferIn = wait.until(ExpectedConditions.elementToBeClickable(TRANSFERIN_BUTTON_LOCATOR));
		transferIn.click();

		// Wait for the modal to be invisible before proceeding
	}

	public void enterLocation() throws InterruptedException {

		WebElement enterLocation = wait.until(ExpectedConditions.elementToBeClickable(LOCATION_BUTTON_LOCATOR));
		enterLocation.click();

		// searchCoun2++;
		int locationIndex = searchCoun2 % location.size();
		enteredLocation = location.get(locationIndex); // Store the entered location
		enterLocation.sendKeys(enteredLocation);

		enterLocation.sendKeys(location.get(locationIndex));

		Thread.sleep(3000);
		// Locate the location options
		List<WebElement> dropdownlocation = driver.findElements(LOCATION_DROPDOWN_LOCATOR);
		for (WebElement option : dropdownlocation) {

			// String loc2 = location.get(searchCoun2);
			String loc2 = location.get(searchCoun2 % location.size());

			if (option.getText().trim().equals(loc2)) {
				// Found a match, click on the option
				Thread.sleep(1000);

				searchCoun2++;

				Thread.sleep(1000);

				option.click();

				break;
			}

		}

	}

	public void writenote() {

		WebElement writenote = wait.until(ExpectedConditions.elementToBeClickable(NOTE_LOCATOR));
		writenote.click();
		writenote.sendKeys("Transferr in imprest");
		note = "Transfer in imprest";

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
			if (option.getText().trim().equals(drugname1)) {
				// Found a match, click on the option
				Thread.sleep(1000);
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
		enteredDrug = drugname1; // Store the entered drug
		enteredQuantity = drugqty; // Store the entered quantity

		QtyFromExcel = drugqty;
		WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(ADD_LOCATOR));
		addButton.click();

		Thread.sleep(3000);

		WebElement completeButton = wait.until(ExpectedConditions.elementToBeClickable(COMPLATE_LOCATOR));
		completeButton.click();

	}

	public String getEnteredLocation() {
		return enteredLocation;
	}

	public String getEnteredDrug() {
		return enteredDrug;
	}

	public String getEnteredQuantity() {
		return enteredQuantity;
	}

	public String getTransferInPageURL() {
		transferInPageURL = driver.getCurrentUrl();
		return transferInPageURL;
	}

	public String getSelectedLocation() {
		WebElement selectedLocationElement = driver.findElement(LOCATION_BUTTON_LOCATOR);
		selectedLocation = selectedLocationElement.getText().trim();
		return selectedLocation;
	}

	public String getNote() {
		return note;
	}

	public String Getselectdestroyqty() {
		// String QtyFromExcel
		// =wait.until(ExpectedConditions.elementToBeClickable(QTY_LOCATOR)).getText();
		return QtyFromExcel;
	}

	public String GetselectMedication() {
		String selectedDrug = driver.findElement(By.xpath("//td[1]/p[1]")).getText();
		return selectedDrug;
	}

	public int GetselectQty() throws InterruptedException {
		String selectedQty = driver.findElement(By.xpath("//p[1]/span[1]")).getText().trim();
		String add1 = selectedQty.replaceAll("\\(.*?\\)", "").trim();
		System.out.println("select qty =  " + add1);
		Thread.sleep(1000);
		String numericAdd = add1.replaceAll("[^0-9]", "");
		int abc = Integer.parseInt(numericAdd);
		return abc;
	}

}
