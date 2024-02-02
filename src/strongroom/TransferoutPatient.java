package strongroom;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import objects.SignPage;
import objects.Stocktakepage;
import objects.Stocktakepages;
import objects.TransferoutPatientPage;

public class TransferoutPatient extends Base {
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	private Stocktakepage stocktakepage;
	private TransferoutPatientPage transferoutPatientPage;
	private SignPage signPage;
	private Stocktakepages stocktakepages;

	
	@Test
	public void transferInPatient() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(30));
		stocktakepage = new Stocktakepage(driver, wait);
		transferoutPatientPage = new TransferoutPatientPage(driver, wait);
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

		transferoutPatientPage.transferout();
		transferoutPatientPage.enterLocation();
		transferoutPatientPage.writenote();
		transferoutPatientPage.Resident();
		String selectdestroyqty = transferoutPatientPage.Getselectdestroyqty();
		String result = selectdestroyqty.replaceAll("\\.0$", "");

		String selectMedication = transferoutPatientPage.GetselectMedication();
		String selectresident = transferoutPatientPage.Getselectresident();
		int selectQty = transferoutPatientPage.GetselectQty();
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

		inputdata = "\n" + "Transfer-out Patient Quantity From Excel: " + result + "\n" + "Transfer-out Drug Name: "
				+ selectMedication + "\n" + "Transfer-out Patient Drug Quantity:  " + selectQty + "\n"
				+ "Current Drug Stock: " + actualValue + "\n" + "Slected Patient :" + selectresident + "\n"
				+ "After Transferout Patient Drug Stock: " + actualValue1 + "\n";

		softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");
		softAssert.assertAll();
	}
}
