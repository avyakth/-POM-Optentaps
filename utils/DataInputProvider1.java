package utils;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;



public class DataInputProvider1 extends wrappers.GenericWrappers {

	public static String[][] getSheet(String dataSheetName) {

		String[][] data = null;

		try {
			FileInputStream fis = new FileInputStream(new File("./data/"+dataSheetName+".xlsx"));
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workbook.getSheetAt(0);	

			// get the number of rows
			int rowCount = sheet.getLastRowNum();

			// get the number of columns
			int columnCount = sheet.getRow(0).getLastCellNum();
			data = new String[rowCount][columnCount];

			for(int i=1; i<rowCount+1; i++)
			{
			XSSFRow row =sheet.getRow(i);
			for(int j=0; j<columnCount;j++)
			{
			XSSFCell cell= row.getCell(j);
				if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC){
			        if (DateUtil.isCellDateFormatted(cell)) {
			        	DateFormat df = new SimpleDateFormat("MM/dd/yy");
			    		java.util.Date today =cell.getDateCellValue();
			    		String reportDate=df.format(today);
			    		data[i-1][j]  = reportDate; 
			    		//System.out.println(reportDate);
			            //System.out.println(cell.getDateCellValue());
			        } 
			        else {
			        	int num =(int) cell.getNumericCellValue();
			        	String s4 = Integer.toString(num); 
			        	data[i-1][j]=s4;
			            //System.out.println(cell.getNumericCellValue());
			        }
			}
				else
				{
					data[i-1][j]=(cell.getStringCellValue());
				}
			}
		}
			// loop through the rows
			/*for(int i=1; i <rowCount+1; i++){
				
					XSSFRow row = sheet.getRow(i);
					for(int j=0; j <columnCount; j++){ // loop through the columns
						
							String cellValue = "";
							
								cellValue = row.getCell(j).getStringCellValue();
						

							data[i-1][j]  = cellValue; // add to the data array
					}

				} 
				}
			}*/
			fis.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;

	}

}
