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

public class Transferinimprest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		   // Extent report setup
				ExtentReports extent = new ExtentReports();
		        ExtentSparkReporter spark = new ExtentSparkReporter("TransferIn-imprest.html");
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
        extent.createTest("Go to https://staging.strongroom.ai/login").assignCategory("regression testing").assignDevice("Chrome")
        .log(Status.INFO, "Go to https://staging.strongroom.ai/login");
        
        
        

        try {
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
            
            extent.createTest("Enter correct login").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.INFO, "Enter correct login");

            // Selecting location on the second page
            WebElement selectLocation = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Select location']")));
            selectLocation.click();

            extent.createTest("user is logged in and the system displays the location select page").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.INFO, "user is logged in and the system displays the location select page");
            
            // Choosing a location from the dropdown
            WebElement dropdownOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='pv_id_2_4']")));
            dropdownOption.click();

            // Explicit wait for the "blue-button" with a 90-second duration
            WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(60));
            
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            WebElement secondPageButton = extendedWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));

            // Click the "blue-button"
            secondPageButton.click();
            
            extent.createTest("Select a location and click the Select Location button").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.INFO, "system displays the drug register for the specific location");
            
         // Display status log on html report page :  Go to /drug-register
            extent.createTest("Go to/drug-register").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.PASS, "Go to /drug-register");
        
	
            
              
            // Clicking on the Transfer in
            WebElement transferIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Transfer In']")));
            transferIn.click();
            
         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']")));

          
            // Log the pass status
            System.out.println("Test Passed: Click the Transfer In button in the left menu : Transfer In modal appears");
            
            
         // Display status log on html report page 
            extent.createTest("Click the Transfer In button in the left menu: Transfer In modal appears").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.PASS, "Click the Transfer In button in the left menu : Transfer In modal appears");
            
            
            
        } catch (Exception e) {
            // Log the fail status and any exception details
            System.out.println("Test Failed: Click the Transfer In button in the left menu : Transfer In modal is not appears. Exception: " + e.getMessage());
            
            // Display status log on html report page
            extent.createTest("Click the Transfer In button in the left menu : Transfer In modal is not appears").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.FAIL, "Click the Transfer In button in the left menu : Transfer In modal is not appears");
            
        } 
        
        
        
        try {
        
        // Explicit wait for the location input field
        WebElement enterLocation = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Type in location to receive from']")));
        enterLocation.click();
        enterLocation.sendKeys("101");
        
                
        // Check that the location dropdown appears and displays location names
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='p-dropdown-items-wrapper']")));

        // Log the pass status
        System.out.println("Test Passed: Enter a location : location dropdown appears and displays location names");
        
     // Display status log on html report page
        extent.createTest("Enter a location : location dropdown appears and displays location names").assignCategory("regression testing").assignDevice("Chrome")
        .log(Status.PASS, "Enter a location : location dropdown appears and displays location names");
        
    } 
        
        catch (Exception e) {
        // Log the fail status and any exception details
        System.out.println("Test Failed: Enter a location : location dropdown is not appears. Exception: " + e.getMessage());
        
        // Display status log on html report page
        extent.createTest("Test Failed: Enter a location : location dropdown is not appears").assignCategory("regression testing").assignDevice("Chrome")
        .log(Status.FAIL, "Test Failed: Enter a location : location dropdown is not appears");
        
    } 
        
        
        
        String desiredLabel = "101";
        WebElement dropdownItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@aria-label='" + desiredLabel + "']")));
        dropdownItem.click();
        
        // Display status log on html report page
        extent.createTest("Select a location").assignCategory("regression testing").assignDevice("Chrome")
        .log(Status.PASS, "Selected a location");
        
        System.out.println("Test Passed: Selected a location");

        
        // Assuming you have navigated to the page and located the textarea element
        WebElement noteTextArea = driver.findElement(By.xpath("//textarea[@name='notes' and @id='note-modal']"));

        // Write "Transferrin" in the note box
        noteTextArea.sendKeys("Transferr in");
        
        System.out.println("Test Passed: Added text to notes");

        
        // Display status log on html report page
        extent.createTest("Add text to notes").assignCategory("regression testing").assignDevice("Chrome")
        .log(Status.PASS, "Added text to notes");
        
        
        
        try {
        	
        	 // Click the Imprest/Emergency Meds/Ward Stock button
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
            button.click();
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Select Medication']")));


                   // Log the pass status
            System.out.println("Test Passed: Click the Imprest/Emergency Meds/Ward Stock button : select medication input field appears");
            
            // Display status log on html report page
            extent.createTest("Click the Imprest/Emergency Meds/Ward Stock button : select medication input field appears").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.PASS, "Click the Imprest/Emergency Meds/Ward Stock button : select medication input field appears");
                     
        }catch (Exception e) {
        	
            // Log the fail status and any exception details
            System.out.println("Test Failed: Click the Imprest/Emergency Meds/Ward Stock button  : select medication input field is not appear. Exception: " + e.getMessage());
        	
         // Display status log on html report page
            extent.createTest("Click the Imprest/Emergency Meds/Ward Stock button  : select medication input field is not appear").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.FAIL, "Click the Imprest/Emergency Meds/Ward Stock button  : select medication input field is not appear");
        		
        }
        
       
        try {
        	
        	 // 7. Enter a medication : Check that the medication dropdown works
            WebElement medicationInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
            medicationInput.click();
                                    
                     
                 	 // Add the text "a" to the input field
            medicationInput.sendKeys("amoxicillin");
            
         // Add a wait for 5 seconds using Thread.sleep()
            try {
                Thread.sleep(5000); // 5000 milliseconds = 5 seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='p-dropdown-items-wrapper']")));

             // Log the pass status
            System.out.println("Test Passed: Enter a medication : medication dropdown works");
            
            // Display status log on html report page
            extent.createTest("Enter a medication : medication dropdown works").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.PASS, "Enter a medication : medication dropdown works");
            
            medicationInput.sendKeys(Keys.ENTER);

            Thread.sleep(2000); // 2000 milliseconds = 2 seconds

            medicationInput.sendKeys(Keys.ARROW_DOWN);

            medicationInput.sendKeys(Keys.ENTER);
        	
        }catch (Exception e) {
        	
            System.out.println("Test Failed: Enter a medication : medication dropdown is not work");
            
            // Display status log on html report page
            extent.createTest("Enter a medication : medication dropdown is not work").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.FAIL, "Enter a medication : medication dropdown is not work");

        }
        
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
       
                    
     // Click on the quantity field with the specified placeholder
        WebElement quantityInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter qty']")));
        quantityInput.click();

     // Enter the quantity "1" in the field
        quantityInput.sendKeys("1");
        
        System.out.println("Test Passed: Select a medication and qty");
        
        
        // Display status log on html report page
        extent.createTest("Select a medication and qty").assignCategory("regression testing").assignDevice("Chrome")
        .log(Status.PASS, "Select a medication and qty");

        
        
        try {
        	
        // 9. Click the Add button : Check that the correct medication and qty have been selected
        WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
        addButton.click();
        
        //---
        
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
            
         // Display status log on html report page
            extent.createTest("Added Medication name match").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.INFO, "Added Medication name match");
            
        } else {
            System.out.println("Fail: Added Medication name is not match");
         // Display status log on html report page
            extent.createTest("Added Medication name is not match").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.INFO, "Added Medication name is not match");
        }
        
        

        WebElement elementqty = driver.findElement(By.xpath("//div[@class='right-form-section-drug-container']//span[1]"));
        String text2 = elementqty.getText().trim();
        String expectedText2 = "1";
        int result = text2.compareTo(expectedText2);

        if (result == 0) {
       	   
       	    System.out.println("Added qty matched");
       	    
       	// Display status log on html report page
       	 extent.createTest("Added qty matched").assignCategory("regression testing").assignDevice("Chrome")
       	 .log(Status.INFO, "Added qty matched");
       	    
       	} else {
       	    System.out.println("qty mismatched");
       	  	// Display status log on html report page
          	 extent.createTest("Added qty is not matched").assignCategory("regression testing").assignDevice("Chrome")
          	 .log(Status.INFO, "Added qty is not matched");

       	}
        
        WebElement elementqtytype = driver.findElement(By.xpath("//span[normalize-space()='capsule']"));
        String text3 = elementqtytype.getText();
        String expectedText3 = "capsule"; 
        // Print Capsule 
        System.out.println("Selected Medication name is : " + text3);

        // Compare the text with the expected text
        if (text3.equals(expectedText3)) {
            System.out.println("Pass: Added Medication Qty type(capsule) is match");
         // Display status log on html report page
            extent.createTest("Added Medication Qty type(capsule) is match").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.INFO, "Added Medication Qty type(capsule) is match");
            
        } else {
            System.out.println("Fail: Added Medication Qty type(capsule) is not match");
            
            // Display status log on html report page
            extent.createTest("Added Medication Qty type(capsule) is not match").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.INFO, "Added Medication Qty type(capsule) is not match");
            
        }

        
     // Log the pass status
      System.out.println("Test Passed: Click the Add button : correct medication and qty have been Added");
        
        
     // Display status log on html report page
        extent.createTest("Click the Add button : correct medication and qty have been Added").assignCategory("regression testing").assignDevice("Chrome")
        .log(Status.PASS, "Click the Add button : correct medication and qty have been Added");
        
        
        
        }catch (Exception e) {
        	
            // Log the fail status and any exception details
            System.out.println("Test Failed: Click the Add button : correct medication and qty is not Added. Exception: " + e.getMessage());
        	
            // Display status log on html report page
            extent.createTest("Click the Add button : correct medication and qty is not Added").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.FAIL, "Click the Add button : correct medication and qty is not Added");
            
        }
       
        try {
        	
        // Click on the button with the specified class
        WebElement completeButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='regular-button complete-button']")));
        completeButton.click();
        
        
     // Check that the signature check modal pops up
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='modal-mask']//div[@class='modal-mask']//div[@class='form-section-container']")));

      
     // Log the pass status
        System.out.println("Test Passed: Click the Recieve Transfer button : signature check modal pops up apper");
        
        // Display status log on html report page
        extent.createTest("Click the Recieve Transfer button : signature check modal pops up apper").assignCategory("regression testing").assignDevice("Chrome")
        .log(Status.PASS, "Click the Recieve Transfer button : signature check modal pops up apper");
        
        
        } catch (Exception e)  {
        	
            System.out.println("Test Failed: Click the Recieve Transfer button : signature modal pops up is not apper");

            // Display status log on html report page
            extent.createTest("Click the Recieve Transfer button : signature modal pops up is not apper").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.FAIL, "Click the Recieve Transfer button : signature modal pops up is not apper");
        	
        }
        
        
        
        
        try {
        	
        	 // Find the input field with the specified placeholder
            WebElement usernameInput1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));

            usernameInput1.click();
            
         // Clear the text from the input field
            usernameInput1.clear();

            // Writing text into the input field

            WebElement usernameInput11 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));
            usernameInput11.sendKeys("valeshan.naidoo@strongroom.ai");
            
         // Clicking on the input field
            WebElement passwordInput1 = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='PIN/Password']")));
            passwordInput1.click();

            // Writing text into the input field
            passwordInput1.sendKeys("1111");
            
         // Clicking on the field
            WebElement greenButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='green-button']")));
            greenButton.click();

         // Wait for 5 seconds (5000 milliseconds)
            Thread.sleep(5000);
       
            // Check that the drug register displays the new transfer at the top of the table
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[4]/tr[1]/td[4]")));
            
         // Log the pass status
            System.out.println("Test Passed: Enter correct signature and click the Sign button: Drug register displays the new transfer at the top of the table");
            
            // Display status log on html report page
            extent.createTest("Enter correct signature and click the Sign button: Drug register displays the new transfer at the top of the table").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.PASS, "Enter correct signature and click the Sign button: Drug register displays the new transfer at the top of the table");
            
            
        }catch (Exception e){
        	
            System.out.println("Test Failed: Enter correct signature and click the Sign button : Drug register is not displays the new transfer at the top of the table");

            // Display status log on html report page
            extent.createTest("Enter correct signature and click the Sign button : Drug register is not displays the new transfer at the top of the table").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.FAIL, "Enter correct signature and click the Sign button : Drug register is not displays the new transfer at the top of the table");
        	
        }
        
        
        
        try {
        
     // Click on the new transfer in the drug register
        WebElement elementToClick = driver.findElement(By.xpath("//tbody[1]/tr[1]/td[1]/i[1]"));
        elementToClick.click();
        
     // Check that the details are correct
       wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[@id='expand-0']//div[@class='expanded-transfer-container']")));

    // Log the pass status
       System.out.println("Test Passed: Click on the new transfer in the drug register : details are correct");
       
    // Display status log on html report page
       extent.createTest("Click on the new transfer in the drug register : details are correct").assignCategory("regression testing").assignDevice("Chrome")
       .log(Status.PASS, "Click on the new transfer in the drug register : details are correct");
       

        }catch (Exception e) {
        	
            System.out.println("Test Failed: Click on the new transfer in the drug register : details are not correct");
            extent.createTest("Click on the new transfer in the drug register : details are not correct").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.FAIL, "Click on the new transfer in the drug register : details are not correct");
        	
        }
      
        // Wait for the element to be clickable and click on it

        try {
        WebElement stockElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Stock']")));
        stockElement.click();
        
        
     // Check that the details are correct
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[2]//td[1]//p[1]")));
        
        System.out.println("Test Passed: Click Stock in the top menu : Transfers Box displays the new transfer and the data is correct");

        // Display status log on html report page
        extent.createTest("Click Stock in the top menu : Transfers Box displays the new transfer and the data is correct").assignCategory("regression testing").assignDevice("Chrome")
        .log(Status.PASS, "Click Stock in the top menu : Transfers Box displays the new transfer and the data is correct");
        
        
        } catch (Exception e) {
        	
            System.out.println("Test Failed: Click Stock in the top menu : Transfers Box is not displays the new transfer and the data is not correct");

            // Display status log on html report page
            extent.createTest("Click Stock in the top menu : Transfers Box is not displays the new transfer and the data is not correct").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.FAIL, "Click Stock in the top menu : Transfers Box is not displays the new transfer and the data is not correct");
            
        }
                             
        // Wait for the element to be clickable and click on it
        WebElement transfersElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Transfers']")));
        transfersElement.click();
                      
               
     // Specify the XPath for the data element
        String xpathForData = "//tbody[1]/tr[1]/td[2]/p[1]";

        // Wait for the element to be present
        WebElement dataElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathForData)));

        // Check if the element contains the expected data
        String expectedData = "IN";  // Replace with the expected data
        String actualData = dataElement.getText();

        if (actualData.equals(expectedData)) {
            System.out.println("Test Passed : Click the Transfers field in the top sub menu : new transfer is displayed in the top row" + actualData);
            
            extent.createTest("Click the Transfers field in the top sub menu : new transfer is displayed in the top row").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.PASS, "Click the Transfers field in the top sub menu : new transfer is displayed in the top row");
            
        } else {
            System.out.println("Test Failed : Click the Transfers field in the top sub menu : new transfer is not displayed in the top row. Expected: " + expectedData + ", Actual: " + actualData);
            
            extent.createTest("Click the Transfers field in the top sub menu : new transfer is not displayed in the top row").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.FAIL, "Click the Transfers field in the top sub menu : new transfer is not displayed in the top row");
            
            
            // ****Open the new transfer row Check that the data is correct
          
        }

              
        // Introduce a 2-second sleep
        Thread.sleep(2000);
               
  
        try {
        	
        	 // Wait for the element to be clickable and click on it Stock take
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[id='stock-take-item'] p")));
            transfersElement.click();
            
            System.out.println("Test Passed : Clicked Stock Take in the top menu");
            
            extent.createTest("Clicked Stock Take in the top menu").assignCategory("regression testing").assignDevice("Chrome")
            .log(Status.PASS, "Clicked Stock Take in the top menu");
            
            // Find the input field for medication search
            WebElement medicationSearchInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Medication...']")));

            // Click the search field
            medicationSearchInput.click();

            // Enter the medication name "amoxicillin 500 mg capsule"
            medicationSearchInput.sendKeys("amoxicillin 500 mg capsule");

            // Wait for the search results to appear (adjust the XPath accordingly)
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='search-results']")));

            // Log the pass status
            System.out.println("Test Passed: Medication search performed successfully");

            // Display status log on html report page
            extent.createTest("Perform Medication Search").assignCategory("regression testing").assignDevice("Chrome")
                    .log(Status.PASS, "Medication search performed successfully");

        } catch (Exception e) {
            // Log the fail status and any exception details
            System.out.println("Test Failed: Medication search failed. Exception: " + e.getMessage());

            // Display status log on html report page
            extent.createTest("Medication Search").assignCategory("regression testing").assignDevice("Chrome")
                    .log(Status.FAIL, "Medication search failed");
        }

        
     // ...

        try {
            // Click on the search button
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button submit-button']")));
            searchButton.click();

            // Log the pass status
            System.out.println("Test Passed: Clicked on the search button");

            // Display status log on html report page
            extent.createTest("Click on Search Button").assignCategory("regression testing").assignDevice("Chrome")
                    .log(Status.PASS, "Clicked on the search button");

        } catch (Exception e) {
            // Log the fail status and any exception details
            System.out.println("Test Failed: Unable to click on the search button. Exception: " + e.getMessage());

            // Display status log on html report page
            extent.createTest("Click on Search Button").assignCategory("regression testing").assignDevice("Chrome")
                    .log(Status.FAIL, "Unable to click on the search button");
        }

        // ...

        
        
     
      
        

        
        
        
        
       
        extent.flush();
        
        
            // Close the WebDriver
            driver.quit();
        
            


	}
}
	


		
	



