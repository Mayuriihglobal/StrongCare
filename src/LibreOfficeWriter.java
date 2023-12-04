import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LibreOfficeWriter {

    public static void main(String[] args) {
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
        WebElement locationInput = wait
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Location']")));
        locationInput.sendKeys("Internal Testing");

        // Clicking on a location
        WebElement clickElement = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//p[@class='drug-search-result' and text()='Internal Testing']")));
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
        WebElement selectLocation = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Select location']")));
        selectLocation.click();

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

        WebElement secondPageButton = extendedWait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));

        // Click the "blue-button"
        secondPageButton.click();

        // Wait for the table to be present
        WebElement dataTable = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"app\"]/div/div[3]/div[2]/div/div[3]/table")));

        // Capture table data
        List<WebElement> rows = dataTable.findElements(By.tagName("tr"));

        // Create a new workbook
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Table Data");

        int rowNum = 0;
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            Row excelRow = sheet.createRow(rowNum++);

            int cellNum = 0;
            for (WebElement cell : cells) {
                Cell excelCell = excelRow.createCell(cellNum++);
                excelCell.setCellValue(cell.getText());
            }
        }

        // Write the workbook to an ODS file
        try (FileOutputStream outputStream = new FileOutputStream("output.ods")) {
            workbook.write(outputStream);
            System.out.println("Table data written to output.ods successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Close the WebDriver
        driver.quit();
    }
}
