package day9;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class WriteDataToExcel {
	@Test
	public void writeData() throws IOException{
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("output");
		XSSFRow row = sheet.createRow(0);
		XSSFCell cell =row.createCell(0);
		cell.setCellValue("S.No");
		XSSFCell cell1 =row.createCell(1);
		cell1.setCellValue("Description");
		FileOutputStream ops = new FileOutputStream(new File("./data/output.xlsx"));
		workbook.write(ops);
		workbook.close();
		ops.close();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
