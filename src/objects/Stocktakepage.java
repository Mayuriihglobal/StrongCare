package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Stocktakepage extends ExcelUtils {
	private static WebDriverWait wait;

	// Constants for XPaths
	private static final String STOCK_XPATH = "//p[normalize-space()='Stock']";
	private static final String STOCK_TAKE_XPATH = "//p[normalize-space()='Stock Take']";
	private static final String MEDICATION_XPATH = "//input[@placeholder='Medication...']";
	private static final String EXPECTED_VALUE_XPATH = "(//td)[4]";
	private static final String DISPLAYIN_STOCK_XPATH = "//p[@class='active-select-filter select-filter-item']";
	private static final String SEARCHING_XPATH = "//button[@class='button submit-button']";
	private static final String DISPLAYIMPREST_XPATH = "//p[normalize-space()='Display Imprest Only']";

	public Stocktakepage(WebDriver driver, WebDriverWait wait) {
		Stocktakepage.wait = wait;
	}

	public void clickStock() {
		WebElement stock = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(STOCK_XPATH)));
		stock.click();
	}

	public void clickStockTake() {
		WebElement stockTake = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(STOCK_TAKE_XPATH)));
		stockTake.click();
	}

	public void enterMedication(String drugName) {
		List<String> drugNames = readDrugNamesFromExcel("output.xlsx");
		String drugname = drugNames.get(searchCount % drugNames.size());

		WebElement medication = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(MEDICATION_XPATH)));
		medication.sendKeys(drugname);
	}

	public void Displayinstock() {
		WebElement Displayinstock = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(DISPLAYIN_STOCK_XPATH)));
		Displayinstock.click();
	}

	public void searching() {
		WebElement searching = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(SEARCHING_XPATH)));
		searching.click();
	}

	public void Displayimprest() {
		WebElement Displayimprest = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath(DISPLAYIMPREST_XPATH)));
		Displayimprest.click();
	}

	public static int getExpectedValue() {
		WebElement expected = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(EXPECTED_VALUE_XPATH)));
		String openB = expected.getText().trim();

		// Extract numeric part from the string (remove non-numeric characters)
		String numericPart = openB.replaceAll("[^0-9]", "");

		// Parse the numeric part into an integer
		return Integer.parseInt(numericPart);

	}

}
