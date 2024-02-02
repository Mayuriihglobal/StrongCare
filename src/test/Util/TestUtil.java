package test.Util;

import java.util.ArrayList;

import com.excel.reader.Xls_Reader;

public class TestUtil {
public static Xls_Reader reader;

	public static ArrayList<Object[]> transferinImprest() {
		ArrayList<Object[]> myData = new  ArrayList<Object[]>();
		 try {
			 reader = new Xls_Reader("C:/Users/bhako/eclipse-workspace/Pharmacy/lib/TestData.xlsx");
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 
		 for(int rownum=2; rownum<= reader.getRowCount("Data"); rownum++) {
			 String location = reader.getCellData("Data", "Location", rownum);
			 String drugname1 = reader.getCellData("Data", "Drug", rownum);
			 String drugqty = reader.getCellData("Data", "QTY", rownum);
			 Object ab[]= {location, drugname1, drugqty};
			 myData.add(ab);	 
		 }
		 return myData;
	}
}
