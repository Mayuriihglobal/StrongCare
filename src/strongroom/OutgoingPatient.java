package strongroom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import objects.OutgoingPatientPage;
import objects.SignPage;
import objects.Stocktakepage;
import objects.Stocktakepages;


public class OutgoingPatient extends Base {
	protected WebDriver driver;
	protected WebDriverWait wait;
	private Stocktakepage stocktakepage;
	private OutgoingPatientPage outgoingPatientPage;
	private SignPage signPage;
	private Stocktakepages stocktakepages;
	private static String inputdata;
	
	@Test
	public void outGoingpatient() throws InterruptedException {
		SoftAssert softAssert = new SoftAssert();
		stocktakepage = new Stocktakepage(driver, wait);
		outgoingPatientPage = new OutgoingPatientPage(driver, wait);
		signPage = new SignPage(driver, wait);
		stocktakepages = new Stocktakepages(driver, wait);
		
		stocktakepage.clickStock();
		stocktakepage.Displayinstock();
		stocktakepage.Displayimprest();

		stocktakepage.enterMedication(0);
		stocktakepage.enterresidentname(0);
		Thread.sleep(1000);
		stocktakepage.searching();
		Thread.sleep(1000);

		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);

		outgoingPatientPage.Outgoing();
		outgoingPatientPage.writenote();
		outgoingPatientPage.Resident();
		String selectdestroyqty = outgoingPatientPage.Getselectdestroyqty();
		String result = selectdestroyqty.replaceAll("\\.0$", "");

		String selectMedication = outgoingPatientPage.GetselectMedication();
		int selectQty = outgoingPatientPage.GetselectQty();

		Thread.sleep(1000);
		signPage.performSignature();
		Thread.sleep(6000);

		stocktakepages.clickStock();
		stocktakepages.enterMedication(0);
		stocktakepages.enterresidentname(0);
		Thread.sleep(1000);
		stocktakepages.searching();
		Thread.sleep(1000);

		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);

		int ExpectedQty = actualValue - selectQty;
		System.out.print(ExpectedQty);

		inputdata = "OutGoing Patient Quantity From Excel : " + result + "\n" + "Drug Name: " + selectMedication + "\n"
				+ "OutGoing Patient in quantity:  " + selectQty + "\n" + "Current Stock: " + actualValue + "\n"
				+ "After Outgoing Patient Stock: " + actualValue1 + "\n";

		softAssert.assertEquals(actualValue1, ExpectedQty, "final stock is not match with Expected stock");
		softAssert.assertAll();

	}
}
