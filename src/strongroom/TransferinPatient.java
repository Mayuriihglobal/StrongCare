package strongroom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import objects.NotificationPage;
import objects.SignPage;
import objects.Stocktakepage;
import objects.Stocktakepages;
import objects.TransferInPatientPage;

public class TransferinPatient extends Base{
	
	protected WebDriver driver;
	protected WebDriverWait wait;
	private Stocktakepage stocktakepage;
	private TransferInPatientPage transferInPatientPage;
	private SignPage signPage;
	private Stocktakepages stocktakepages;
	private NotificationPage notificationPage;
	private static String inputdata;
	
	@Test
	public void transferInPatient() throws InterruptedException {
		
		SoftAssert softAssert = new SoftAssert();
		
		stocktakepage = new Stocktakepage(driver, wait);
		transferInPatientPage = new TransferInPatientPage(driver, wait);
		signPage = new SignPage(driver, wait);
		stocktakepages = new Stocktakepages(driver, wait);
		notificationPage = new NotificationPage(driver, wait);
		
		notificationPage.clickNotificationIcon();		
		stocktakepage.clickStock();
		stocktakepage.Displayinstock();
		stocktakepage.Displayimprest();
		stocktakepage.enterMedication(0);
		stocktakepage.enterresidentname(0);
		stocktakepage.searching();
		Thread.sleep(1000);
		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);

		transferInPatientPage.transferIn();
		transferInPatientPage.enterLocation();
		transferInPatientPage.writenote();
		transferInPatientPage.Resident();
		String selectdestroyqty = transferInPatientPage.Getselectdestroyqty();
		String result = selectdestroyqty.replaceAll("\\.0$", "");

		String selectMedication = transferInPatientPage.GetselectMedication();
		int selectQty = transferInPatientPage.GetselectQty();
		Thread.sleep(1000);
		signPage.performSignature();
		Thread.sleep(1000);

		// Loop through the test execution
		stocktakepages.enterMedication(0);
		stocktakepages.enterresidentname(0);
		stocktakepages.searching();
		Thread.sleep(1000);

		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);

		int ExpectedQty = actualValue + selectQty;
		System.out.print(ExpectedQty);

		inputdata = "TransferinPatient Quantity From Excel : " + result + "\n" + "Drug Name: " + selectMedication + "\n"
				+ "TransferinPatient in quantity:  " + selectQty + "\n" + "Current Stock: " + actualValue + "\n"
				+ "After TransferinPatient Stock: " + actualValue1 + "\n";

		softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");
		softAssert.assertAll();
	}
}
