import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class StrongCare {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Invoking Browser 
		// Chrome - ChromeDrive -> Methods
	
       // Extent report setup
		ExtentReports extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("report.html");
        extent.attachReporter(spark);
        
		
		WebDriver driver = new ChromeDriver ();
		driver.get("https://staging.strongroom.ai/login");
		

		// Display status log on html report page
        extent.createTest("test 1")
        .log(Status.INFO, "Login page open");
        
		
		WebElement inputElement = driver.findElement(By.xpath("//input[@placeholder='Location']")); // Replace with the appropriate locator method (e.g., By.id, By.name, By.xpath) and value
		inputElement.sendKeys("Internal Testing"); // Replace "Text to send" with the text you want to send

		 extent.createTest("test 2")
	        .log(Status.INFO, "Entered Location");
		 
		// Create a WebDriverWait instance with a specified timeout
		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(30));
        // Timeout in seconds

        // Wait for a specific condition before clicking
        WebElement clickElement = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='drug-search-result']")));

        // Perform the click action
        clickElement.click();
        extent.createTest("test 3")
        .log(Status.INFO, "Selected location");
       
			
		driver.findElement(By.xpath("//input[@placeholder='Username/email']")).sendKeys("sam");
		  extent.createTest("test 4")
	        .log(Status.INFO, "Entered Username");
		  
		driver.findElement(By.xpath("//input[@placeholder='Password']")).sendKeys("strongroompassword");
		 extent.createTest("test 5")
	        .log(Status.INFO, "Entered Password");
		 
			WebDriverWait wait11 = new WebDriverWait(driver, Duration.ofSeconds(30));

		 
		driver.findElement(By.xpath("//p[@class='blue-button']")).click();
		
		extent.createTest("test 6")
        .log(Status.INFO, "Clicked Login Button");
		// 2nd page 
		
		WebDriverWait wait111 = new WebDriverWait(driver, Duration.ofSeconds(30));

		By elementLocator = By.xpath("//span[@aria-label='Select location']");

		try {
		    WebElement element = wait111.until(ExpectedConditions.visibilityOfElementLocated(elementLocator));
		    element.click(); // Now click the element
	} catch (org.openqa.selenium.TimeoutException e) {
		    System.out.println("Element not found or not visible within 10 seconds.");
	}

	
		WebElement dropdown = driver.findElement(By.xpath("//li[@id='pv_id_2_4']"));
		dropdown.click();
		extent.createTest("test 7")
        .log(Status.INFO, "Clicked Dropdown");
		
		driver.findElement(By.xpath("//p[@class='blue-button']")).click();
		extent.createTest("test 8")
        .log(Status.INFO, "Slected Internal facility");
		

        
        
        extent.flush();




	}

}
