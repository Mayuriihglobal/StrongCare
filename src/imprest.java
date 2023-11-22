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
	            
	      
	              
	            // Clicking on the Transfer in
	            WebElement transferIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer In']")));
	            transferIn.click();
	            
	         // Check if the Transfer In modal appears based on a specific element within the modal
	            WebElement transferInModal = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']")));

	   
	        
	       

	  
	        	
	        	 // Click the Imprest/Emergency Meds/Ward Stock button
	            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
	            button.click();
	            
	            // 7. Enter a medication : Check that the medication dropdown works
	            WebElement medicationInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
	            medicationInput.click();
	                                    
	            medicationInput.sendKeys("amoxicillin");
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='p-dropdown-items-wrapper']")));
	            medicationInput.sendKeys(Keys.ENTER);
	            Thread.sleep(2000); // 2000 milliseconds = 2 seconds
	            medicationInput.sendKeys(Keys.ARROW_DOWN);

	            medicationInput.sendKeys(Keys.ENTER);
	            WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter qty']")));
	            quantityInput.click();
	            quantityInput.sendKeys("1");
	            
	            Thread.sleep(2000);

	            
	            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
	            addButton.click();
	            

	            WebElement elementmedication = driver.findElement(By.xpath("//p[text()='Amoxicillin 500 mg capsule']"));
	            	           
	            WebElement elementqty = driver.findElement(By.xpath("//div[@class='right-form-section-drug-container']//span[1]"));
	            
	            WebElement elementqtytype = driver.findElement(By.xpath("//span[normalize-space()='capsule']"));

	            
	            
	            
	         // Get the text from the element
	         String text1 = elementmedication.getText();
	      // Get the text from the element
	         String text2 = elementqty.getText().trim();
	         
	         String text3 = elementqtytype.getText();

	         
	         try {
	        	    int intValue = Integer.parseInt(text2);
	        	    System.out.println("Integer value from the element: " + intValue);
	        	    
	        	} catch (NumberFormatException e) {
	        	    System.out.println("Failed to convert the text to an integer. The text is not a valid integer.");
	        	}
	         
	         

	      // Assuming 'text' contains the text retrieved from the element

	        String expectedText1 = "Amoxicillin 500 mg capsule";
	        
	        String expectedText2 = "1";
	        
	        String expectedText3 = "capsule"; 
	        

	        
	        

	         // Print medication name 
	         System.out.println("Selected Medication name is : " + text1);

	         // Compare the text with the expected text
	         if (text1.equals(expectedText1)) {
	             System.out.println("Pass: Added Medication name match");
	         } else {
	             System.out.println("Fail: Added Medication name is not match");
	         }

	         
	         
	         int result = text2.compareTo(expectedText2);

	         if (result == 0) {
	        	   
	        	    System.out.println("qty matched");
	        	    
	        	} else {
	        	    System.out.println("qty mismatched");

	        	}
	         
	         

	       
	        
	         
	      // Print Capsule 
	         System.out.println("Selected Medication name is : " + text3);

	         // Compare the text with the expected text
	         if (text3.equals(expectedText3)) {
	             System.out.println("Pass: Added Medication Qtytype match");
	         } else {
	             System.out.println("Fail: Added Medication Qtytype is not match");
	         }

	         
	         
	         
	         
	         
	    
	            
	            
	            
	            
	            
	       

	            
	            
	           
	            
	            
	            
	            
	            
	            
	            
	            

	            
	            
	            
	            
	            
	            
	            

	            
	            
	            
	            
	            
	            
	            
	            
	            
	       
	            
	            
	            
	            

	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	            
	          
	            
	            
	        
	            


	            
	           
	}
}





