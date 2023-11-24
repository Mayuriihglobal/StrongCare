import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

public class imprest {

	public static void main(String[] args) throws InterruptedException {
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
	            
	      
	            
	            
	            // Clicking on the Transfer out
	            WebElement transferIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer Out']")));
	            transferIn.click();
	            
	         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']")));

	            
	         // Explicit wait for the location input field
	         WebElement enterLocation = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Type in location to send to']")));
	         enterLocation.click();
	         enterLocation.sendKeys("External facility");
	         
	                 
	         // Check that the location dropdown appears and displays location names
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='p-dropdown-items-wrapper']")));
	        
	        String desiredLabel = "External facility";
	        WebElement dropdownItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@aria-label='" + desiredLabel + "']")));
	        dropdownItem.click();
	        
	        Thread.sleep(5000);


	        
	        // Assuming you have navigated to the page and located the textarea element
	        WebElement noteTextArea = driver.findElement(By.xpath("//textarea[@name='notes' and @id='note-modal']"));

	        // Write "Transferrin" in the note box
	        noteTextArea.sendKeys("Transferr out");
	        
	        // Click the Imprest/Emergency Meds/Ward Stock button
	        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Resident Medication']")));
	        button.click();
	        
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']")));

	   
	        WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']")));
		    searchField.click();

		    // Enter text in the search field
		    searchField.sendKeys("Arvind Nath");

		    // Click on the search button
		    WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		    searchButton.click();
		    
		    WebElement searchResult1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='patient-result-info']//p[1]")));

			// Click on the name in the search result
			searchResult1.click();
			
			 // 7. Enter a medication : Check that the medication dropdown works
		    WebElement medicationInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='pom-select']")));
		    medicationInput.click();
		    
		   
		    
		    
		    
	         
	 
	 
	            
	           
	}
}





