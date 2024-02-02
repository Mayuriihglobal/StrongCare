package strongroom;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import objects.OutgoingimprestPage;
import objects.SignPage;
import objects.Stocktakepage;
import objects.Stocktakepages;

public class Outgoingimprest extends Base {
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	private Stocktakepage stocktakepage;
	private OutgoingimprestPage outgoingimprestPage;
	private SignPage signPage;
	private Stocktakepages stocktakepages;
	private static String inputdata;

	
	@Test
	public void outGoingIprest() throws InterruptedException {
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		stocktakepage = new Stocktakepage(driver, wait);
		outgoingimprestPage = new OutgoingimprestPage(driver, wait);
		signPage = new SignPage(driver, wait);
		stocktakepages = new Stocktakepages(driver, wait);
		
		SoftAssert softAssert = new SoftAssert();

		// stocktakepage.clickStock();
		// stocktakepage.Displayinstock();

		driver.navigate().refresh();
		stocktakepage.Displayinstock();

		stocktakepage.Displayimprest();
		stocktakepage.enterMedication(0);
		Thread.sleep(1000);
		stocktakepage.searching();
		Thread.sleep(1000);
		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);

		outgoingimprestPage.Outgoing();
		outgoingimprestPage.writenote();
		outgoingimprestPage.imprest();
		String selectdestroyqty = outgoingimprestPage.Getselectdestroyqty();
		String result = selectdestroyqty.replaceAll("\\.0$", "");

		String selectMedication = outgoingimprestPage.GetselectMedication();
		int selectQty = outgoingimprestPage.GetselectQty();

		Thread.sleep(3000);
		signPage.performSignature();
		Thread.sleep(6000);

		// Loop through the test execution
		stocktakepages.enterMedication(0);
		Thread.sleep(1000);
		stocktakepages.searching();
		Thread.sleep(1000);
		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);

		int ExpectedQty = actualValue - selectQty;
		System.out.print(ExpectedQty);

		inputdata = "Outgoing imprest Quantity From Excel : " + result + "\n" + "Drug Name: " + selectMedication + "\n"
				+ "Outgoing imprest in quantity:  " + selectQty + "\n" + "Current Stock: " + actualValue + "\n"
				+ "After Outgoing imprest Stock: " + actualValue1 + "\n";

		softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");
		softAssert.assertAll();
	}
}