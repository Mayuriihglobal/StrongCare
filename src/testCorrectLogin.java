import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class testCorrectLogin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Extent report setup
		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("testCorrectLogin.html");
		extent.attachReporter(spark);

// Create a WebDriver instance
		WebDriver driver = new ChromeDriver();

// Maximize the Chrome window

		driver.manage().window().maximize();

// Set implicit wait to 10 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

// Initialize WebDriverWait with a longer duration
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

// Open the webpage
		driver.get("https://staging.strongroom.ai/login");

// Display status log on html report page
		extent.createTest("Go to https://staging.strongroom.ai/login").assignCategory("regression testing")
				.assignDevice("Chrome").log(Status.INFO, "Go to https://staging.strongroom.ai/login");

		try {
// Entering Location
			WebElement locationInput = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Location']")));
			locationInput.sendKeys("Internal Testing");

// Clicking on a location
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

			extent.createTest("Enter correct login").assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.INFO, "Enter correct login");

// Selecting location on the second page
			WebElement selectLocation = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Select location']")));
			selectLocation.click();

			extent.createTest("user is logged in and the system displays the location select page")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.INFO, "user is logged in and the system displays the location select page");

// Choosing a location from the dropdown
			WebElement dropdownOption = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='pv_id_2_4']")));
			dropdownOption.click();

// Get text from the first location element

			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='pv_id_2_4']")));
			String dropdownOptionText = dropdownOption.getText();

//Extract "West Ward" from the full text
			String desiredText = dropdownOptionText.substring(dropdownOptionText.lastIndexOf("-") + 1).trim();

// Explicit wait for the "blue-button" with a 90-second duration
			WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(60));

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			WebElement secondPageButton = extendedWait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));

// Click the "blue-button"
			secondPageButton.click();

			extent.createTest("Select a location and click the Select Location button")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.INFO, "system displays the drug register for the specific location");

//--
//Get text from the drug register element
			WebElement newPatientButton = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//p[@class='new-patient-button data-v-tooltip']")));
			String newPatientButtonText = newPatientButton.getText();

//Compare the text
			if (desiredText.equals(newPatientButtonText)) {
				System.out.println("Pass: Texts match");
			} else {
				System.out.println("Fail: Texts do not match");
			}

//Print the values
			System.out.println("Dropdown Option Text: " + desiredText);
			System.out.println("New Patient Button Text: " + newPatientButtonText);

		} catch (Exception e) {
			// Log the fail status and any exception details
			System.out.println(
					"Test Failed:system is not displays the drug register for the specific location. Exception: "
							+ e.getMessage());

			// Display status log on html report page
			extent.createTest("system is not displays the drug register for the specific location")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.FAIL, "system is not displays the drug register for the specific location");

		}

		extent.flush();

	}

}
