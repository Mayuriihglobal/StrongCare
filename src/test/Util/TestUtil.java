package test.Util;

import java.util.ArrayList;

import com.excel.reader.Xls_Reader;

public class TestUtil {
	public static Xls_Reader reader;

	public static ArrayList<Object[]> transferinImprest() {
		ArrayList<Object[]> myData = new ArrayList<Object[]>();
		try {
			reader = new Xls_Reader("/home/user/Videos/StrongCare-parthdev/Agedcare.xlsx");
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int rownum = 2; rownum <= reader.getRowCount("Data"); rownum++) {
			String action = reader.getCellData("Data", "Action", rownum);
			String location = reader.getCellData("Data", "Location", rownum);
			String drugname = reader.getCellData("Data", "Drug", rownum);
			String transaction_id = reader.getCellData("Data", "Transaction ID", rownum);
			String resident = reader.getCellData("Data", "Resident", rownum);
			String drugqty = reader.getCellData("Data", "QTY", rownum);
			String note = reader.getCellData("Data", "Note", rownum);
			String username = reader.getCellData("Data", "Username", rownum);
			String pin = reader.getCellData("Data", "PIN/Password", rownum);
			String username1 = reader.getCellData("Data", "Username1", rownum);
			String pin1 = reader.getCellData("Data", "PIN/Password1", rownum);
			String AdminRound = reader.getCellData("Data", "AdministrationRound", rownum);
			Object ab[] = { action, location, drugname, transaction_id, resident, drugqty, note, username, pin,
					username1, pin1, AdminRound };
			myData.add(ab);
		}
		return myData;
	}
}
