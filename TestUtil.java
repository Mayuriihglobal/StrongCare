package test.Util;
import java.util.ArrayList;

import com.excel.reader.Xls_Reader;

public class TestUtil {
	
	static Xls_Reader reader;
	
	public static ArrayList<Object[]> getDatafordestorypatient(){
		ArrayList<Object[]> myData = new  ArrayList<Object[]>();
		 try {
			 reader = new Xls_Reader("C:/Users/bhako/Downloads/file.xlsx");
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 
		 for(int rownum=2; rownum<= reader.getRowCount("New"); rownum++) {
			 String First = reader.getCellData("New", "First", rownum);
			 String Second = reader.getCellData("New", "Second", rownum);	 
			 Object ab[]= {First, Second};
			 myData.add(ab);
			 
		 }
		 return myData;
	}
}
