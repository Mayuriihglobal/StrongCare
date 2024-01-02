package testsuite;

import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import objects.LoginPage;
import objects.NotificationPage;
import objects.SecondPage;
import objects.SignPage;
import objects.Stocktakepage;
import objects.TransferInPage;

public class TestScript {
	private WebDriver driver;
	private WebDriverWait wait;
	private LoginPage loginPage;
	private SecondPage secondPage;
	private NotificationPage notificationPage;
	private Stocktakepage stocktakepage;
	private TransferInPage transferInPage;
	private SignPage signPage;

	@BeforeClass
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		loginPage = new LoginPage(driver, wait);
		secondPage = new SecondPage(driver, wait);
		notificationPage = new NotificationPage(driver, wait);
		stocktakepage = new Stocktakepage(driver, wait);
		transferInPage = new TransferInPage(driver, wait);
		signPage = new SignPage(driver, wait);
	}

	@Test(priority = 0)
	public void Login() {
		// Test Scenario
		loginPage.openLoginPage("https://staging.strongroom.ai/login");
		loginPage.enterLocation("Internal Testing");
		loginPage.enterCredentials("sam", "strongroompassword");
		loginPage.clickLoginButton();
		secondPage.selectLocationFromDropdown();
		secondPage.clickSecondPageButton();
		notificationPage.clickNotificationIcon();

	}

	@Test(priority = 1)
	public void stocktake() throws InterruptedException {
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		String drugname = "YourDrugName"; // You can get this from your data provider or elsewhere
		stocktakepage.enterMedication(drugname);
		Thread.sleep(3000);
		stocktakepage.Displayinstock();
		stocktakepage.searching();
		stocktakepage.Displayimprest();
		Thread.sleep(3000);
		int actualValue = Stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);
		// Your assertion logic
		// int expectedValue = 123; // Set your expected value here
		// Assert.assertEquals(actualValue, expectedValue, "Values do not match");
	}

	@Test(priority = 2, invocationCount = 3)
	public void Transferin() throws InterruptedException {

		transferInPage.transferIn();
		transferInPage.enterLocation();
		transferInPage.writenote();
		transferInPage.imprest();
		Thread.sleep(3000);
		signPage.performSignature("valeshan.naidoo@strongroom.ai", "1111");
		Thread.sleep(6000);
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}