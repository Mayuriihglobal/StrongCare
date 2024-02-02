package strongroom;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import objects.DestroyPatientPage;
import objects.SignPage;
import objects.Stocktakepage;
import objects.Stocktakepages;

public class DestroyPatient extends Base {
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	private Stocktakepage stocktakepage;
	private DestroyPatientPage destroyPatientPage;
	private SignPage signPage;
	private Stocktakepages stocktakepages;
	private static String inputdata;
	
	@Test
	public void destoryPatient() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		stocktakepage = new Stocktakepage(driver, wait);
		destroyPatientPage = new DestroyPatientPage(driver, wait);
		signPage = new SignPage(driver, wait);
		stocktakepages = new Stocktakepages(driver, wait);

		// stocktakepage.clickStock();
		// stocktakepage.Displayinstock();
		stocktakepage.Displayimprest();

		stocktakepage.enterMedication(0);
		stocktakepage.enterresidentname(0);
		Thread.sleep(1000);
		stocktakepage.searching();
		Thread.sleep(1000);

		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);

		destroyPatientPage.Destroy();
		destroyPatientPage.writenote();
		destroyPatientPage.MethodOFDestruction();
		destroyPatientPage.CourierNameandNotes();
		destroyPatientPage.Resident();
		String selectdestroyqty = destroyPatientPage.Getselectdestroyqty();
		String result = selectdestroyqty.replaceAll("\\.0$", "");

		String selectMedication = destroyPatientPage.GetselectMedication();
		int selectQty = destroyPatientPage.GetselectQty();
		Thread.sleep(1000);
		signPage.performSignature();
		Thread.sleep(1000);

		stocktakepages.enterMedication(0);
		stocktakepages.enterresidentname(0);
		Thread.sleep(1000);
		stocktakepages.searching();
		Thread.sleep(1000);

		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);

		int ExpectedQty = actualValue - selectQty;
		System.out.print(ExpectedQty);

		inputdata = "Destory Patient Quantity From Excel : " + result + "\n" + "Drug Name: " + selectMedication + "\n"
				+ "Destory Patient in quantity:  " + selectQty + "\n" + "Current Stock: " + actualValue + "\n"
				+ "After Destory Patient Stock: " + actualValue1 + "\n";

		softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");
		softAssert.assertAll();
	}
}
