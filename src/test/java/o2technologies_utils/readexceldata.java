package o2technologies_utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class readexceldata {
	
	 public static Map<String, String> readExcelData(String filePath) throws IOException {
	       FileInputStream fis = new FileInputStream(filePath);
	       Workbook workbook = new XSSFWorkbook(fis);
	       Sheet sheet = workbook.getSheetAt(0);

	       Map<String, String> data = new HashMap<>();
	       Row headerRow = sheet.getRow(0);
	       Row dataRow = sheet.getRow(1);
	       
	      
	       for (Cell cell : headerRow) {
	           int columnIndex = cell.getColumnIndex();
	           String header = getCellValueAsString(cell);
	           String value =  getCellValueAsString(dataRow.getCell(columnIndex));
	           
	           data.put(header, value);
	       }
	       workbook.close();
	       fis.close();
		  return data;
		  
	    }

	    private static String getCellValueAsString(Cell cell) {
	        if (cell == null) {
	            return ""; // Handle null cells gracefully
	        }

	        switch (cell.getCellType()) {
	            case STRING:
	                return cell.getStringCellValue();
	            case NUMERIC:
	            	  // Check if the numeric value is an integer or has a decimal
	                double numericValue = cell.getNumericCellValue();
	                long longValue = (long) numericValue;
	                if (numericValue == longValue) {
	                    return String.valueOf(longValue); // Convert to string without decimal if it's an integer
	                } else {
	                    return String.valueOf(numericValue); // Convert to string with decimal if it's not an integer
	                }
	            case BOOLEAN:
	                return String.valueOf(cell.getBooleanCellValue());
	            default:
	                return ""; // Handle other cell types as needed
	        }
	        
	    }

}
