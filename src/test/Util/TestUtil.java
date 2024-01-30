package test.Util;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;

import com.excel.reader.Xls_Reader;

public class TestUtil {
	
	static Xls_Reader reader;
	private static WebDriver driver;
	
	public TestUtil(WebDriver driver) {
        this.driver = driver;
    }

	public static ArrayList<Object[]> getDatafordestorypatient() {
		ArrayList<Object[]> myData = new  ArrayList<Object[]>();
		 try {
			 reader = new Xls_Reader("/home/user/eclipse-workspace/StrongCare/output.xlsx");
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 
		 for(int rownum=2; rownum<= reader.getRowCount("Table Data"); rownum++) {
			 String Transaction_ID = reader.getCellData("Table Data", "Transaction ID", rownum); 
			 String Drug = reader.getCellData("Table Data", "Drug", rownum); 
			 String Action = reader.getCellData("Table Data", "Action (Static)", rownum); 
			 String To_From = reader.getCellData("Table Data", "To/From (Location)", rownum); 
			 String resident = reader.getCellData("Table Data", "Resident", rownum); 
			 String Entered_BY = reader.getCellData("Table Data", "Entered By (Static)", rownum); 
			 String In = reader.getCellData("Table Data", "In", rownum); 
			 Object ab[]= {Transaction_ID, Drug, Action, To_From, resident, Entered_BY, In};
			 myData.add(ab);
			 
		 }
		 return myData;
	}

}
