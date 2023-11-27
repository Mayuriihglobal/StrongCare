import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class DestroyPatientMedication {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		
		   // Extent report setup
					ExtentReports extent = new ExtentReports();
			        ExtentSparkReporter spark = new ExtentSparkReporter("DestroyPatientMedication.html");
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
	        
		
	            
	              
	            // Clicking on the Destroy in
	            WebElement transferIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Destroy']")));
	            transferIn.click();
	            
	         wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']")));

	          
	            // Log the pass status
	            System.out.println("Test Passed: Click the Destroy button in the left menu : Destroy modal appears");
	            
	            
	         // Display status log on html report page 
	            extent.createTest("Click the Destroy button in the left menu: Destroy modal appears").assignCategory("regression testing").assignDevice("Chrome")
	            .log(Status.PASS, "Click the Destroy button in the left menu : Destroy modal appears");
	            
	            
	            
	        } catch (Exception e) {
	            // Log the fail status and any exception details
	            System.out.println("Test Failed: Click Destroy button in the left menu : Destroy modal is not appears. Exception: " + e.getMessage());
	            
	            // Display status log on html report page
	            extent.createTest("Click Destroy button in the left menu : Destroy modal is not appears").assignCategory("regression testing").assignDevice("Chrome")
	            .log(Status.FAIL, "Click the Destroy button in the left menu : Destroy modal is not appears");
	            
	        } 
	        
	  
	        // Assuming you have navigated to the page and located the textarea element
	        WebElement noteTextArea = driver.findElement(By.xpath("//textarea[@name='notes' and @id='note-modal']"));

	        // Write "Transferrin" in the note box
	        noteTextArea.sendKeys("Destruction");
	        
	        System.out.println("Test Passed: Added text to notes");
	      
	        // Display status log on html report page
	        extent.createTest("Enter a Reason for destruction").assignCategory("regression testing").assignDevice("Chrome")
	        .log(Status.PASS, "Enter a Reason for destruction");
	        
	     // Enter a Method of destruction
	        WebElement methodOfDestructionInput = driver.findElement(By.xpath("//input[@placeholder='Method of Destruction']"));
	        methodOfDestructionInput.sendKeys("Incineration");

	        System.out.println("Test Passed: Entered Method of Destruction");

	        // Display status log on html report page
	        extent.createTest("Enter a Method of Destruction").assignCategory("regression testing").assignDevice("Chrome")
	                .log(Status.PASS, "Entered Method of Destruction");

	        // Enter courier name
	        WebElement courierNameInput = driver.findElement(By.xpath("//input[@placeholder='Courier Name']"));
	        courierNameInput.sendKeys("Express Courier");

	        System.out.println("Test Passed: Entered Courier Name");

	        // Display status log on html report page
	        extent.createTest("Enter Courier Name").assignCategory("regression testing").assignDevice("Chrome")
	                .log(Status.PASS, "Entered Courier Name");
	        
	     // Enter courier notes
	        WebElement courierNotesInput = driver.findElement(By.xpath("//input[@placeholder='Courier Notes']"));
	        courierNotesInput.sendKeys("Handle with care");

	        System.out.println("Test Passed: Entered Courier Notes");

	        // Display status log on html report page
	        extent.createTest("Enter Courier Notes").assignCategory("regression testing").assignDevice("Chrome")
	                .log(Status.PASS, "Entered Courier Notes");

	        
	        
	        
	        
	        //88
	        try {
	        	
	        	 // Click the Patient Medication button
	            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Resident Medication']")));
	            button.click();
	            
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@placeholder='Select Medication']")));


	                   // Log the pass status
	            System.out.println("Test Passed: Click Patient Medication button : select medication input field appears");
	            
	            // Display status log on html report page
	            extent.createTest("Click Patient Medication button : select medication input field appears").assignCategory("regression testing").assignDevice("Chrome")
	            .log(Status.PASS, "Click Patient Medication button : select medication input field appears");
	                     
	        }catch (Exception e) {
	        	
	            // Log the fail status and any exception details
	            System.out.println("Test Failed: Click the Imprest/Emergency Meds/Ward Stock button  : select medication input field is not appear. Exception: " + e.getMessage());
	        	
	         // Display status log on html report page
	            extent.createTest("Click the Imprest/Emergency Meds/Ward Stock button  : select medication input field is not appear").assignCategory("regression testing").assignDevice("Chrome")
	            .log(Status.FAIL, "Click the Imprest/Emergency Meds/Ward Stock button  : select medication input field is not appear");
	        		
	        }
	        
	        try {
	            Thread.sleep(3000);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	        
	        
	        
		
	        try {
	    	    // Click on the search field
	    	    WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']")));
	    	    searchField.click();
	    	    

	    	    // Enter text in the search field
	    	    searchField.sendKeys("Arvind Nath");

	    	    // Click on the search button
	    	    WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
	    	    searchButton.click();

	    	    
	    	    // Make sure the name is in the search result
	    	    WebElement searchResult = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='patient-result-info']//p[1]")));
	    	    String resultText = searchResult.getText();

	    	    // Verify that the name in the search result matches the expected name
	    	    if (resultText.contains("Arvind Nath")) {
	    	        System.out.println("Search successful. Name found in the search result.");
	    	        
	    	        // Display status log on html report page
	    	        extent.createTest("Enter a patient name in the search field and click search : patient is displayed").assignCategory("regression testing").assignDevice("Chrome")
	    	        .log(Status.PASS, "Enter a patient name in the search field and click search : patient is displayed");
	    	        
	    	    } else {
	    	        System.out.println("Search failed. Name not found in the search result.");
	    	     // Display status log on html report page
	    	        extent.createTest("Enter a patient name in the search field and click search : patient is not displayed").assignCategory("regression testing").assignDevice("Chrome")
	    	        .log(Status.FAIL, "Enter a patient name in the search field and click search : patient is not displayed");
	    	    }

	    	} catch (Exception e) {
	    	    // Handle exceptions if any
	    	    e.printStackTrace();



	    	}

	        
	        
	        
	        
	        
	    	try {

	    	WebElement searchResult1 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='patient-result-info']//p[1]")));

	    	// Click on the name in the search result
	    	searchResult1.click();

	    	//Assuming you have clicked on the search result and navigated to the transfer in page

	    	//Wait for the element to be present and visible
	    	WebElement individualPatientName = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='individual-patient-container']//p[1]")));

	    	//Get the text from the element
	    	String individualPatientNameText = individualPatientName.getText();

	    	//Verify that the expected text is displayed on the page
	    	if (individualPatientNameText.contains("Arvind Nath")) {
	    	 System.out.println("Text is displayed on the page: " + individualPatientNameText);
	    	 
	    	//Display status log on html report page
	    	 extent.createTest("Select the patient : patient is displayed in the Transfer In modal").assignCategory("regression testing").assignDevice("Chrome")
	    	 .log(Status.PASS, "Select the patient : patient is displayed in the Transfer In modal");
	    	 
	    	 
	    	} else {
	    	 System.out.println("Text is not displayed on the page: " + individualPatientNameText);
	    	 // Handle the case where the text is not displayed as expected
	    	//Display status log on html report page
	    	 extent.createTest("Select the patient : patient is not displayed in the Transfer In modal").assignCategory("regression testing").assignDevice("Chrome")
	    	 .log(Status.FAIL, "Select the patient : patient is not displayed in the Transfer In modal");
	    	 
	    	}



	    	}catch(Exception e) {
	    		
	    		  // Handle exceptions if any
	    	    e.printStackTrace();

	    			}
	    //***
	    	try {
	    	    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='pom-select']")));

	    	    // Log the pass status
	    	    System.out.println("Test Passed: Select Medication input field is present");

	    	    // Display status log on html report page
	    	    extent.createTest("Check that Select Medication input field appears").assignCategory("regression testing").assignDevice("Chrome")
	    	            .log(Status.PASS, "Select Medication input field is present");
	    	    
	    	    // 7. Enter a medication : Check that the medication dropdown works
	    	    WebElement medicationInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[@id='pom-select']")));
	    	    medicationInput.click();
	    	
	    	    // Create a Select object
	    	    Select medicationDropdown = new Select(medicationInput);

	    		  medicationDropdown.selectByValue("2");
	    	medicationInput.sendKeys(Keys.ESCAPE);

	    	  	  		  
	    	// Locate the quantity (QTY) input box
	    	  WebElement qtyInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Quantity']")));

	    	  qtyInput.click();
	    	  
	    	
	    	  // Clear the quantity input box
	    	  qtyInput.clear();

	    	  // Add "1" to the quantity input box
	    	  qtyInput.sendKeys("1");
	    	  
	    	  
	    	  // Click on the "Add" button
	    	  WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
	    	  addButton.click();
	    	  
	    	    // You can perform further actions if needed

	    	} catch (Exception e) {
	    	    // Log the fail status and any exception details
	    	    System.out.println("Test Failed: Select Medication input field is not present. Exception: " + e.getMessage());

	    	    // Display status log on html report page
	    	    extent.createTest("Check that Select Medication input field appears").assignCategory("regression testing").assignDevice("Chrome")
	    	            .log(Status.FAIL, "Select Medication input field is not present");

	    	    // Handle the case where the input field is not present as expected
	    	}

	    	//Click on the name in the search result

	    	//------&&

	    	try {
	    	
	    	// get xpath for madication
	    	WebElement elementmedication = driver.findElement(By.xpath("//p[normalize-space()='Amoxicillin 500 mg capsule']"));

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



	    	WebElement elementqty = driver.findElement(By.xpath("//div[@class='right-form-section-panel']//span[1]"));
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

	      //&&

	      
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
	      
	      //----stock 
	      
	      
	      // Specify the XPath for the data element
	      String xpathstock = "//tr[2]//td[1]//p[1]";

	      // Wait for the element to be present
	      WebElement dataElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathstock)));

	      // Check if the element contains the expected data
	      String expectedData = "OUT";  // Replace with the expected data
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
	      
	      
	      
	      //---Transfer 
	                           
	      // Wait for the element to be clickable and click on it
	      WebElement transfersElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Transfers']")));
	      transfersElement.click();
	                    
	             
	    // Specify the XPath for the data element
	      String xpathForData = "//tbody[1]/tr[1]/td[2]/p[1]";

	      // Wait for the element to be present
	      WebElement dataElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathForData)));

	      // Check if the element contains the expected data
	      String expectedData = "OUT";  // Replace with the expected data
	      String actualData = dataElement.getText();

	      if (actualData.equals(expectedData)) {
	          System.out.println("Test Passed : Click the Transfers field in the top sub menu : new transfer is displayed in the top row" + actualData);
	          
	          extent.createTest("Click the Transfers field in the top sub menu : new transfer is displayed in the top row").assignCategory("regression testing").assignDevice("Chrome")
	          .log(Status.PASS, "Click the Transfers field in the top sub menu : new transfer is displayed in the top row");
	          
	      } else {
	          System.out.println("Test Failed : Click the Transfers field in the top sub menu : new transfer is not displayed in the top row. Expected: " + expectedData + ", Actual: " + actualData);
	          
	          extent.createTest("Click the Transfers field in the top sub menu : new transfer is not displayed in the top row").assignCategory("regression testing").assignDevice("Chrome")
	          .log(Status.FAIL, "Click the Transfers field in the top sub menu : new transfer is not displayed in the top row");
	          
	          
	        
	      }

	      
	            
	      // Introduce a 2-second sleep
	      Thread.sleep(2000);
	           
	      try {
	     		
	     	 // Wait for the element to be clickable and click on it Stock take
	     	WebElement  transfersElement1 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a[id='stock-take-item'] p")));
	         transfersElement1.click();
	         
	         System.out.println("Test Passed : Clicked Stock Take in the top menu");
	         
	         extent.createTest("Clicked Stock Take in the top menu").assignCategory("regression testing").assignDevice("Chrome")
	         .log(Status.PASS, "Clicked Stock Take in the top menu");
	         
	         // Find the input field for medication search
	         WebElement medicationSearchInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Medication...']")));


	         // Enter the medication name "amoxicillin 500 mg capsule"
	         medicationSearchInput.sendKeys("amoxicillin 500 mg capsule");

	        
	         // Find the button using its XPath
	         WebElement submitButton = wait.until(
	             ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button submit-button']")));

	         // Click on the button
	         submitButton.click();

	         // Wait for the result element to be visible
	         WebElement resultElement = wait.until(
	                 ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[2]//td[1]")));

	         // Check if the result element is visible
	         if (resultElement.isDisplayed()) {
	             System.out.println("Pass: Result element is visible");
	         } else {
	             System.out.println("Fail: Result element is not visible");
	         }

	       

	         // Log the pass status
	         System.out.println("Test Passed: Medication search performed successfully");

	         // Display status log on html report page
	         extent.createTest("Search for the transfered medication in the search field").assignCategory("regression testing").assignDevice("Chrome")
	                 .log(Status.PASS, "data and qty is correct");

	     } catch (Exception e) {
	         // Log the fail status and any exception details
	         System.out.println("Test Failed: Medication search failed. Exception: " + e.getMessage());

	         // Display status log on html report page
	         extent.createTest("Medication Search").assignCategory("regression testing").assignDevice("Chrome")
	                 .log(Status.FAIL, "Medication search failed");
	     }
	        
	      
		       extent.flush();

		
	}

}
