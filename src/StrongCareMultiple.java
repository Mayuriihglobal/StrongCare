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

public class StrongCareMultiple {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Invoking Browser
		// Chrome - ChromeDrive -> Methods

		// Extent report setup
		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("report.html");
		extent.attachReporter(spark);

		WebDriver driver = new ChromeDriver();
		driver.get("https://staging.strongroom.ai/login");

		// Sample array of combinations
		String[] locations = { "Internal Testing", "Another Location" };
		String[] usernames = { "sam", "anotheruser" };
		String[] passwords = { "strongroompassword", "anotherpassword" };

		for (int i = 0; i < locations.length; i++) {
			// Display status log on the HTML report page
			extent.createTest("Test " + (i + 1)).log(Status.INFO, "Login page open");

			WebElement inputElement = driver.findElement(By.xpath("//input[@placeholder='Location']"));
			inputElement.sendKeys(locations[i]);

			extent.createTest("test " + (i + 2)).log(Status.INFO, "Entered Location");

			WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
			WebElement clickElement = wait1
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='drug-search-result']")));
			clickElement.click();

			extent.createTest("test " + (i + 3)).log(Status.INFO, "Selected location");

			driver.findElement(By.xpath("//input[@placeholder='Username/email']")).sendKeys(usernames[i]);
			extent.createTest("test " + (i + 4)).log(Status.INFO, "Entered Username");

			driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys(passwords[i]);
			extent.createTest("test " + (i + 5)).log(Status.INFO, "Entered Password");

			WebDriverWait wait11 = new WebDriverWait(driver, Duration.ofSeconds(30));
			driver.findElement(By.xpath("//p[@class='blue-button']")).click();
			extent.createTest("test " + (i + 6)).log(Status.INFO, "Clicked Login Button");

			WebDriverWait wait111 = new WebDriverWait(driver, Duration.ofSeconds(30));
			By elementLocator = By.xpath("//span[@aria-label='Select location']");

			try {
				WebElement element = wait111.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
				element.click();
			} catch (org.openqa.selenium.TimeoutException e) {
				System.out.println("Element not found or not visible within 10 seconds.");
			}

			WebElement dropdown = driver.findElement(By.xpath("//li[@id='pv_id_2_4']"));
			dropdown.click();
			extent.createTest("test " + (i + 7)).log(Status.INFO, "Clicked Dropdown");

			driver.findElement(By.xpath("//p[@class='blue-button']")).click();
			extent.createTest("test " + (i + 8)).log(Status.INFO, "Selected Internal facility");

			extent.flush();
			driver.get("https://staging.strongroom.ai/login"); // Go back to the login page for the next combination
		}
		driver.findElement(By.xpath("//p[normalize-space()='SR']")).click();

		driver.findElement(By.xpath("//span[@class='p-menuitem-text']")).click();

	}

}
