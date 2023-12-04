import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.apache.commons.io.FileUtils;

public class Imprest {

    static WebDriver driver;
    static ExtentReports extent;

    public static void main(String[] args) {
        // Extent report setup
        extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("imprest.html");
        extent.attachReporter(spark);

        // Create a WebDriver instance
        driver = new ChromeDriver();

        // Maximize the Chrome window
        driver.manage().window().maximize();

        // Set implicit wait to 10 seconds
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Initialize WebDriverWait with a longer duration
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        // Open the webpage
        driver.get("https://staging.strongroom.ai/login");
        
        captureScreenshot("URL");


        // Display status log on HTML report page
        extent.createTest("Go to https://staging.strongroom.ai/login").assignCategory("regression testing")
                .assignDevice("Chrome").log(Status.INFO, "Go to https://staging.strongroom.ai/login");

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
            passwordInput.sendKeys("1");

            // Clicking on Login Button
            WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));
            loginButton.click();

            // Capture screenshot after clicking login button
            captureScreenshot("AfterLoginButtonClick");

            // Wait for the warning message element to be present
            WebElement warningMessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//p[normalize-space()='Warning: Invalid login or password.']")));

            

            // Check if the warning message is displayed
            if (warningMessageElement.isDisplayed()) {
                extent.createTest("Enter Incorrect login").assignCategory("regression testing").assignDevice("Chrome")
                        .log(Status.PASS, "Login unsuccessful as expected. Warning message displayed: "
                                + warningMessageElement.getText());
             // Capture screenshot after warning message is displayed
                captureScreenshot("AfterWarningMessage");
                
            } else {
                extent.createTest("Enter Incorrect login").assignCategory("regression testing").assignDevice("Chrome")
                        .log(Status.FAIL, "Login was supposed to be unsuccessful, but warning message not displayed.");
            }

        } catch (Exception e) {
            extent.createTest("Enter Incorrect login").assignCategory("regression testing").assignDevice("Chrome")
                    .log(Status.FAIL, "Exception occurred: " + e.getMessage());
        }

        extent.flush();
        driver.quit();
    }

    private static void captureScreenshot(String screenshotName) {
        try {
            TakesScreenshot screenshot = (TakesScreenshot) driver;
            File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
            Path destinationPath = Paths.get("screenshots", screenshotName + ".png");
            File destinationFile = destinationPath.toFile();
            FileUtils.copyFile(sourceFile, destinationFile);
            extent.createTest("Screenshot - " + screenshotName).addScreenCaptureFromPath(destinationPath.toString());
        } catch (Exception e) {
            System.out.println("Error capturing screenshot: " + e.getMessage());
        }
    }
}
