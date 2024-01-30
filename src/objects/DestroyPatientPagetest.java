package objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.Util.TestUtil;

import java.util.ArrayList;
import java.util.Iterator;

public class DestroyPatientPagetest extends TestUtil {

    private static final By DESTROY_BUTTON_LOCATOR = By.xpath("//button[normalize-space()='Destroy']");
    private static final By NOTE_LOCATOR = By.xpath("//textarea[@name='notes' and @id='note-modal']");
    private static final By METHOD_LOCATOR = By.xpath("//input[@placeholder='Method of Destruction']");
    private static final By COURIER_NAME_LOCATOR = By.xpath("//input[@placeholder='Courier Name']");
    private static final By COURIER_NOTES_LOCATOR = By.xpath("//input[@placeholder='Courier Notes']");
    private static final By RESIDENT_LOCATOR = By.xpath("//p[normalize-space()='Resident Medication']");
    private static final By RESIDENT_SEARCH_LOCATOR = By.xpath("//p[@class='submit-button blue-button']");

    private static WebDriverWait wait;
    private static WebDriver driver;

    public Stocktakepage stocktakepage;

    public DestroyPatientPagetest(WebDriver driver, WebDriverWait wait) {
        super(driver); // Ensure the superclass constructor is called to set the driver
        this.wait = wait;
        }

    public void Destroy() {
        WebElement Destroy = wait.until(ExpectedConditions.elementToBeClickable(DESTROY_BUTTON_LOCATOR));
        Destroy.click();
        // Wait for the modal to be invisible before proceeding
    }

    public void writenote() {
        WebElement writenote = wait.until(ExpectedConditions.elementToBeClickable(NOTE_LOCATOR));
        writenote.click();
        writenote.sendKeys("Destroy Resident");
    }

    public void MethodOFDestruction() {
        WebElement MethodOFDestruction = wait.until(ExpectedConditions.elementToBeClickable(METHOD_LOCATOR));
        MethodOFDestruction.click();
        MethodOFDestruction.sendKeys("MethodOFDestruction");
    }

    public void CourierNameandNotes() {
        WebElement CourierName = wait.until(ExpectedConditions.elementToBeClickable(COURIER_NAME_LOCATOR));
        CourierName.click();
        CourierName.sendKeys("CourierName");

        WebElement CourierNotes = wait.until(ExpectedConditions.elementToBeClickable(COURIER_NOTES_LOCATOR));
        CourierNotes.click();
        CourierNotes.sendKeys("CourierNotes");
    }

    @DataProvider
    public Iterator<Object[]> getTestData() {
        ArrayList<Object[]> testData = TestUtil.getDatafordestorypatient();
        return testData.iterator();
    }

    @Test(dataProvider = "getTestData")
    public static void Resident(String resident) throws InterruptedException {
        WebElement Resident = wait.until(ExpectedConditions.elementToBeClickable(RESIDENT_LOCATOR));
        Resident.click();

        WebElement inputField = driver.findElement(By.xpath("//input[@placeholder='Enter Resident name or Medicare Number']"));
        inputField.sendKeys(resident);

        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(RESIDENT_SEARCH_LOCATOR));
        searchButton.click();

        Thread.sleep(2000);
    }
}
