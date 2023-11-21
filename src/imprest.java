import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

public class imprest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
	    WebDriver driver = new ChromeDriver();
	     // Maximize the Chrome window
	        driver.manage().window().maximize();

	        // Set implicit wait to 10 seconds
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

	        // Initialize WebDriverWait with a longer duration
	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

	        // Open the webpage
	        driver.get("https://staging.strongroom.ai/login");
	   
	        
	            // Entering Location
	            WebElement locationInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Location']")));
	            locationInput.sendKeys("Internal Testing");

	            // Clicking on a location
	            WebElement clickElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='drug-search-result' and text()='Internal Testing']")));
	            clickElement.click();

	            // Entering Username
	            WebElement usernameInput = driver.findElement(By.xpath("//input[@placeholder='Username/email']"));
	            usernameInput.sendKeys("sam");

	            // Entering Password
	            WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Password']"));
	            passwordInput.sendKeys("strongroompassword");

	            // Clicking on Login Button
	            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));
	            loginButton.click();

	            // Selecting location on the second page
	            WebElement selectLocation = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Select location']")));
	            selectLocation.click();

	            // Choosing a location from the dropdown
	            WebElement dropdownOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='pv_id_2_4']")));
	            dropdownOption.click();

	            // Explicit wait for the "blue-button" with a 90-second duration
	            WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(20));
	            
	            try {
	                Thread.sleep(2000);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            
	            WebElement secondPageButton = extendedWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));

	            // Click the "blue-button"
	            secondPageButton.click();
	            
	      
	              
	            // Clicking on the Transfer in
	            WebElement transferIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer In']")));
	            transferIn.click();
	            
	         // Check if the Transfer In modal appears based on a specific element within the modal
	            WebElement transferInModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']")));

	   
	        
	        // Assuming you have navigated to the page and located the textarea element
	        WebElement noteTextArea = driver.findElement(By.xpath("//textarea[@name='notes' and @id='note-modal']"));

	  
	        	
	        	 // Click the Imprest/Emergency Meds/Ward Stock button
	            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
	            button.click();
	            
	          


	            
	           
	}
}





