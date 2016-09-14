package apachepoi;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class UpdateExcel {
	public void updateXLSFile() {

		FileInputStream inputFile = null;
		HSSFWorkbook workbook = null;
		String filePath = "C:\\testExcelUpdate.xls";

		try {
			inputFile = new FileInputStream(new File(filePath));
			workbook = new HSSFWorkbook(inputFile);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFCell cell = null;

		// Updating value of cell with integer
		cell = sheet.getRow(1).getCell(1);
		cell.setCellValue(123456);

		// Updating value of cell with String
		cell = sheet.getRow(1).getCell(0);
		cell.setCellValue("test666");

		// Updating value of cell with boolean
		cell = sheet.getRow(2).getCell(1);
		cell.setCellValue(true);

		try {
			FileOutputStream outFile = null;
			outFile = new FileOutputStream(new File(filePath));
			workbook.write(outFile);
			outFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public static void main(String args[]) {
		UpdateExcel updateExcel = new UpdateExcel();
		updateExcel.updateXLSFile();
	}
}
