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
	            
	            // Clicking on the Destroy in
	            WebElement transferIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Destroy']")));
	            transferIn.click();
	            
	         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']")));

	          
	            // Log the pass status
	            System.out.println("Test Passed: Click the Destroy button in the left menu : Destroy modal appears");
	            
	            // Assuming you have navigated to the page and located the textarea element
		        WebElement noteTextArea = driver.findElement(By.xpath("//textarea[@name='notes' and @id='note-modal']"));

		        // Write "Transferrin" in the note box
		        noteTextArea.sendKeys("Destruction");
		        
		        System.out.println("Test Passed: Added text to notes");
		        
		        // Enter a Method of destruction
		        WebElement methodOfDestructionInput = driver.findElement(By.xpath("//input[@placeholder='Method of Destruction']"));
		        methodOfDestructionInput.sendKeys("Incineration");

		        System.out.println("Test Passed: Entered Method of Destruction");
		        // Enter courier name
		        WebElement courierNameInput = driver.findElement(By.xpath("//input[@placeholder='Courier Name']"));
		        courierNameInput.sendKeys("Express Courier");

		        System.out.println("Test Passed: Entered Courier Name");
	         
		     // Enter courier notes
		        WebElement courierNotesInput = driver.findElement(By.xpath("//input[@placeholder='Courier Notes']"));
		        courierNotesInput.sendKeys("Handle with care");

		        System.out.println("Test Passed: Entered Courier Notes");
	 
		        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
	            button.click();
	            
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Select Medication']")));


	                   // Log the pass status
	            System.out.println("Test Passed: Click the Imprest/Emergency Meds/Ward Stock button : select medication input field appears");
	            
	       	 // 7. Enter a medication : Check that the medication dropdown works
	            WebElement medicationInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
	            medicationInput.click();
	                                    
	                     
	                 	 // Add the text "a" to the input field
	            medicationInput.sendKeys("amoxicillin");
	            
	            try {
	                Thread.sleep(5000); // 5000 milliseconds = 5 seconds
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	            
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='p-dropdown-items-wrapper']")));

	            
	            System.out.println("Test Passed: Enter a medication : medication dropdown works");

	            medicationInput.sendKeys(Keys.ENTER);

	            Thread.sleep(2000); // 2000 milliseconds = 2 seconds

	            medicationInput.sendKeys(Keys.ARROW_DOWN);

	            medicationInput.sendKeys(Keys.ENTER);
	            
		        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));

	            
		        // Click on the quantity field with the specified placeholder
		        WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter qty']")));
		        quantityInput.click();

		     // Enter the quantity "1" in the field
		        quantityInput.sendKeys("1");
		        
		        System.out.println("Test Passed: Select a medication and qty");
		        
		        // 9. Click the Add button : Check that the correct medication and qty have been selected
		        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
		        addButton.click();
	            
		        
		        // get xpath for madication
		        WebElement elementmedication = driver.findElement(By.xpath("//p[text()='Amoxicillin 500 mg capsule']"));

		     // Get the text from the medication name 
		        String text1 = elementmedication.getText();
		        
		        // Assuming 'text' contains the text retrieved from the element
		        String expectedText1 = "Amoxicillin 500 mg capsule";
		        
		     // Print medication name 
		        System.out.println("Selected Medication name is : " + text1);

		        // Compare the text with the expected text
		        if (text1.equals(expectedText1)) {
		            System.out.println("Pass: Added Medication name match");
		            
		        
		            
		        } else {
		            System.out.println("Fail: Added Medication name is not match");
		        
		        }
		        
	            //88
		        
		        // get xpath for madication
		        WebElement elementmedication1 = driver.findElement(By.cssSelector("td:nth-child(2) p:nth-child(1)"));

		     // Get the text from the medication name 
		        String text11 = elementmedication1.getText();
		        
		     // Extract the desired part of the text
		        String extractedText = text11.split("\\(")[0].trim();
		        
		        // Assuming 'text' contains the text retrieved from the element
		        String expectedText11 = "1 capsule";
		        
		     // Print medication name 
		        System.out.println("Selected Quantity is : " + extractedText );

		        // Compare the text with the expected text
		        if (extractedText.equals(expectedText11)) {
		            System.out.println("Pass: Selected Quantity is match");
		            
		        
		            
		        } else {
		            System.out.println("Fail: Selected Quantity is not match");
		        
		        }
	     
		        
		        
	}
}





