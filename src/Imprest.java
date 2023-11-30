import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Imprest {

    public static void main(String[] args) {
        // Create a WebDriver instance
        WebDriver driver = new ChromeDriver();

        // Initialize WebDriverWait with a 60-second duration
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        try {
            // Maximize the Chrome window
            driver.manage().window().maximize();

            // Set implicit wait to 10 seconds
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

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

            // Explicit wait for the "blue-button" with a 60-second duration
            WebElement secondPageButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));

            // Click the "blue-button"
            secondPageButton.click();

            // Clicking on the Destroy in
            WebElement transferIn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Destroy']")));
            transferIn.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']")));

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

            try {
                // Click the Patient Medication button
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Resident Medication']")));
                button.click();

                // Log the pass status
                System.out.println("Test Passed: Click Resident Medication button");

            } catch (Exception e) {
                // Log the fail status and any exception details
                System.out.println("Test Failed: Not Clicked Resident Medication button. Exception: " + e.getMessage());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Click on the search field
            WebElement searchField = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[placeholder='Enter Resident name or Medicare Number']")));

            // Enter text in the search field
            searchField.sendKeys("Arvind Nath");

            // Click on the search button
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
            searchButton.click();

        } catch (Exception e) {
            // Log the fail status and any exception details
            e.printStackTrace();
            System.out.println("Test Failed: " + e.getMessage());
        } finally {
            // Close the WebDriver
            //driver.quit();
        }
    }
}
