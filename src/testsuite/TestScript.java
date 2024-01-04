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
import objects.Stocktakepages;
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
	private Stocktakepages stocktakepages;

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
		stocktakepages = new Stocktakepages(driver, wait);

	}

	@Test(priority = 0)
	public void Login() {
		// Test Scenario
		loginPage.openLoginPage("https://staging.strongroom.ai/login");
		LoginPage.login();
		loginPage.clickLoginButton();

		secondPage.selectLocationFromDropdown();
		secondPage.clickSecondPageButton();
		notificationPage.clickNotificationIcon();

	}

	@Test(priority = 1, enabled = false)

	public void stocktakeOpen() throws InterruptedException {
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();

		// Loop through the test execution
		for (int i = 0; i < 15; i++) {
			stocktakepage.enterMedication(i);
			Thread.sleep(3000);
			stocktakepage.Displayinstock();
			stocktakepage.searching();
			stocktakepage.Displayimprest();
			Thread.sleep(3000);

			int actualValue = stocktakepage.getExpectedValue();
			System.out.println("(Stock): " + actualValue);
		}
	}

	@Test(priority = 2, invocationCount = 15, enabled = false)
	public void Transferin() throws InterruptedException {

		transferInPage.transferIn();
		transferInPage.enterLocation();
		transferInPage.writenote();
		transferInPage.imprest();
		Thread.sleep(3000);
		signPage.performSignature("valeshan.naidoo@strongroom.ai", "1111");
		Thread.sleep(6000);
	}

	@Test(priority = 3, enabled = false)

	public void stocktakeclose() throws InterruptedException {
		stocktakepages.clickStock();
		stocktakepages.clickStockTake();

		// Loop through the test execution
		for (int i = 0; i < 15; i++) {
			stocktakepages.enterMedication(i);
			Thread.sleep(3000);
			stocktakepages.Displayinstock();
			stocktakepages.searching();
			stocktakepages.Displayimprest();
			Thread.sleep(3000);

			int actualValue = stocktakepages.getExpectedValue();
			System.out.println("(Stock): " + actualValue);
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}