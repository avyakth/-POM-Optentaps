package day9;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

public class ReadDataFromExcel {

	@Test
	public void readData() throws IOException {
		FileInputStream fis = new FileInputStream(new File("./data/login.xlsx"));
		XSSFWorkbook workBook = new XSSFWorkbook(fis);
		XSSFSheet sheet = workBook.getSheet("Sheet2");
		int rowcount = sheet.getLastRowNum();
		System.out.println("RowCOunt "+rowcount
				);
		for (int i = 1; i <= rowcount; i++) {
			XSSFRow row = sheet.getRow(i);
			XSSFCell username = row.getCell(0);
			System.out.println(username.getStringCellValue());
			XSSFCell password = row.getCell(1);
			System.out.println(password.getStringCellValue());
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
