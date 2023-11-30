import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class TransferInImprest {

	public static void main(String[] args) throws InterruptedException {

		// Extent report setup
		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("TRIN-imprest.html");
		extent.attachReporter(spark);

		WebDriver driver = new ChromeDriver();

		// Set implicit wait to 10 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Initialize WebDriverWait with a longer duration
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		driver.get("https://staging.strongroom.ai/login");

		// Display status log on html report page
		extent.createTest("Verify login page").assignCategory("regression testing").assignDevice("Chrome")
				.log(Status.INFO, "Login page open");

		// Entering Location
		WebElement locationInput = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Location']")));
		locationInput.sendKeys("Internal Testing");

		WebElement clickElement = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//p[@class='drug-search-result' and text()='Internal Testing']")));
		clickElement.click();

		// Entering Username
		WebElement usernameInput = driver.findElement(By.xpath("//input[@placeholder='Username/email']"));
		usernameInput.sendKeys("sam");

		// Entering Password
		WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Password']"));
		passwordInput.sendKeys("strongroompassword");

		// Clicking on Login Button
		WebElement loginButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));
		loginButton.click();

		// Selecting location on the second page
		WebElement selectLocation = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Select location']")));
		selectLocation.click();

		// Choosing a location from the dropdown
		WebElement dropdownOption = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='pv_id_2_4']")));
		dropdownOption.click();

		// Explicit wait for the "blue-button" with a 60-second duration
		WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(90));
		WebElement secondPageButton = extendedWait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));

		// Click the "blue-button"
		secondPageButton.click();

		// Clicking on the Transfer in
		WebElement transferIn = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer In']")));
		transferIn.click();

		// Explicit wait for the location input field
		WebElement enterLocation = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//input[@placeholder='Type in location to receive from']")));
		enterLocation.click();
		enterLocation.sendKeys("101");

		String desiredLabel = "101";
		WebElement dropdownItem = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@aria-label='" + desiredLabel + "']")));
		dropdownItem.click();

		// Assuming you have navigated to the page and located the textarea element
		WebElement noteTextArea = driver.findElement(By.xpath("//textarea[@name='notes' and @id='note-modal']"));

		// Write "Transferrin" in the note box
		noteTextArea.sendKeys("Transferr in");

		// Click on the button with the specified text using XPath
		WebElement button = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
		button.click();

		// Click on the input field with the specified placeholder
		WebElement medicationInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
		medicationInput.click();

		// Add the text "a" to the input field
		medicationInput.sendKeys("a");
		medicationInput.sendKeys(Keys.ENTER);

		Thread.sleep(2000); // 2000 milliseconds = 2 seconds

		medicationInput.sendKeys(Keys.ARROW_DOWN);

		medicationInput.sendKeys(Keys.ENTER);

		// Click on the quantity field with the specified placeholder
		WebElement quantityInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter qty']")));
		quantityInput.click();

		// Enter the quantity "1" in the field
		quantityInput.sendKeys("1");

		// Click on the "Add" button with the specified class
		WebElement addButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		addButton.click();

		// Click on the button with the specified class
		WebElement completeButton = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='regular-button complete-button']")));
		completeButton.click();

		// Find the input field with the specified placeholder
		WebElement usernameInput1 = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));

		usernameInput1.click();

		// Clear the text from the input field
		usernameInput1.clear();

		// Writing text into the input field

		WebElement usernameInput11 = wait
				.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));
		usernameInput11.sendKeys("valeshan.naidoo@strongroom.ai");

		// Clicking on the input field
		WebElement passwordInput1 = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='PIN/Password']")));
		passwordInput1.click();

		// Writing text into the input field
		passwordInput1.sendKeys("1111");

		// Clicking on the field
		WebElement greenButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='green-button']")));
		greenButton.click();

		// Wait for 5 seconds (5000 milliseconds)
		Thread.sleep(5000);

		// Assuming you have a WebDriver instance named 'driver'
		WebElement elementToClick = driver.findElement(By.xpath("//tbody[1]/tr[1]/td[1]/i[1]"));
		elementToClick.click();

		// Assuming you have a WebDriver instance named 'driver'
		WebElement stockButton = driver.findElement(By.xpath("//p[normalize-space()='Stock']"));
		stockButton.click();

		// Assuming you have a WebDriver instance named 'driver'
		WebElement transfersField = driver.findElement(By.xpath("//p[normalize-space()='Transfers']"));
		transfersField.click();

		// Assuming you have a WebDriver instance named 'driver'
		WebElement transferRow = driver.findElement(By.xpath("//tbody[1]/tr[1]/td[1]/i[1]"));
		transferRow.click();

		// Assuming you have a WebDriver instance named 'driver'
		WebElement stockTakeMenuItem = driver.findElement(By.xpath("//p[normalize-space()='Stock Take']"));
		stockTakeMenuItem.click();

		// Assuming you have a WebDriver instance named 'driver'
		WebElement searchField = driver.findElement(By.xpath("//input[@placeholder='Medication...']"));
		searchField.click();

		extent.flush();

		// Your additional steps...

//        driver.quit(); // Make sure to close the WebDriver when done

	}
}
