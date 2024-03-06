package strongroom;

import java.util.ArrayList;
import java.util.Iterator;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import test.Util.TestUtil;

public class Testing extends Base {

	@DataProvider
	public Iterator<Object[]> getTestData() {
		ArrayList<Object[]> testData = TestUtil.transferinImprest();
		return testData.iterator();

	}

	@Test(dataProvider = "getTestData")
	public void automation(String action, String location, String drugname, String transaction_id, String resident,
			String drugqty, String note, String username, String pin, String username1, String pin1)
			throws InterruptedException {

		if ("Destroy imprest".equals(action)) {
			Destroyimprest.destroyimprest(action, location, drugname, transaction_id, resident, drugqty, note, username,
					pin);
		} else if ("Destroy Patient".equals(action)) {
			DestroyPatient.destroyPatient(action, location, drugname, transaction_id, resident, drugqty, note, username,
					pin);
		} else if ("Outgoing imprest".equals(action)) {
			Outgoingimprest.outgoingimprest(action, location, drugname, transaction_id, resident, drugqty, note,
					username, pin);
		} else if ("Outgoing Patient".equals(action)) {
			OutgoingPatient.outgoingPatient(action, location, drugname, transaction_id, resident, drugqty, note,
					username, pin);
		} else if ("Transfer in Imprest".equals(action)) {
			TransferinImprest.transferinImprest(action, location, drugname, transaction_id, resident, drugqty, note,
					username, pin);
		} else if ("Transfer in Patient".equals(action)) {
			TransferinPatient.transferinPatient(action, location, drugname, transaction_id, resident, drugqty, note,
					username, pin);
		} else if ("Transfer out Imprest".equals(action)) {
			TransferoutImprest.transferoutImprest(action, location, drugname, transaction_id, resident, drugqty, note,
					username, pin);
		} else if ("Transfer out Patient".equals(action)) {
			TransferoutPatient.transferoutPatient(action, location, drugname, transaction_id, resident, drugqty, note,
					username, pin);
		} else if ("Adjustment imprest".equals(action)) {
			Adjustmentimprest.adjustmentImprest(action, location, drugname, transaction_id, resident, drugqty, note,
					username, pin, username1, pin1);
		} else if ("Adjustment Patient".equals(action)) {
			AdjustmentPatient.adjustmentPatient(action, location, drugname, transaction_id, resident, drugqty, note,
					username, pin, username1, pin1);
		} else {
			System.out.println("No data");
		}
	}
}
