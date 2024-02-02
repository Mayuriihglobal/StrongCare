package strongroom;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import objects.DestroyimprestPage;
import objects.SignPage;
import objects.Stocktakepage;
import objects.Stocktakepages;

public class Destroyimprest extends Base{
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	private Stocktakepage stocktakepage;
	private DestroyimprestPage destroyimprestPage;
	private SignPage signPage;
	private Stocktakepages stocktakepages;

	@Test
	public void destoryImprest() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		stocktakepage = new Stocktakepage(driver, wait);
		destroyimprestPage = new DestroyimprestPage(driver, wait);
		signPage = new SignPage(driver, wait);
		stocktakepages = new Stocktakepages(driver, wait);
		
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

		destroyimprestPage.Destroy();
		destroyimprestPage.writenote();
		destroyimprestPage.MethodOFDestruction();
		destroyimprestPage.CourierNameandNotes();
		destroyimprestPage.imprest();
		String selectdestroyqty = destroyimprestPage.Getselectdestroyqty();
		String result = selectdestroyqty.replaceAll("\\.0$", "");

		String selectMedication = destroyimprestPage.GetselectMedication();
		int selectQty = destroyimprestPage.GetselectQty();
		Thread.sleep(1000);
		signPage.performSignature();
		Thread.sleep(1000);

		// Loop through the test execution
		stocktakepages.enterMedication(0);
		Thread.sleep(1000);
		stocktakepages.searching();
		Thread.sleep(1000);
		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);

		int ExpectedQty = actualValue - selectQty;
		System.out.print(ExpectedQty);

		inputdata = "Destory imprest Quantity From Excel : " + result + "\n" + "Drug Name: " + selectMedication + "\n"
				+ "Destory imprest in quantity:  " + selectQty + "\n" + "Current Stock: " + actualValue + "\n"
				+ "After Destory imprest Stock: " + actualValue1 + "\n";

		softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");
		softAssert.assertAll();
	}
}
