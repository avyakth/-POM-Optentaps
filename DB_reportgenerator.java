//**********************************************************
//Date of Creation 	: April 15
//Author          	: Ram Kumar Sastha Lakshmanan
//Purpose          	: Generate Execution Report
//Last Update On 	:
//Updated by       	:
//**********************************************************
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DB_reportgenerator {

	public void report(String Module, boolean freport, String Description, String Teststatus, String errorcriticality, String ErrDesc) throws IOException, RowsExceededException, WriteException, BiffException{
		//DateFormat logTme = new SimpleDateFormat("hh:mm:ss");
		Calendar cal = Calendar.getInstance();
		//String logTime = logTme.format(cal.getTime());		
		DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");		
		String cal1 = dateFormat.format(cal.getTime());
		String filepath = "C:\\Selenium\\Results\\DB_Automation Final Report "+cal1+".xls";
		File ifilepath = new File("C:\\Selenium\\Results\\DB_Automation Final Report "+cal1+".xls");
		String ofilepath = "C:\\Selenium\\Results\\DB_Automation Final Report "+cal1+"_temp.xls";
		File logfile = new File(filepath);//Created object of java File class.
		
		if (!logfile.exists()){			//if1
			WritableWorkbook workbook = Workbook.createWorkbook(new File(filepath));
		    WritableSheet sheet = workbook.createSheet("Report",0);
		    sheet.setName("Report");
		    WritableFont arialfont = new WritableFont(WritableFont.ARIAL, 11, WritableFont.BOLD); 
		    WritableCellFormat cellFormat = new WritableCellFormat (arialfont); 
		    cellFormat.setBackground(Colour.ICE_BLUE);
		    cellFormat.setBorder(Border.ALL,BorderLineStyle.THIN);
		    sheet.addCell(new Label(0, 0, "Date",cellFormat));
		    sheet.addCell(new Label(1, 0, "SheetName",cellFormat));
		    sheet.addCell(new Label(2, 0, "TestName",cellFormat));
		    sheet.addCell(new Label(3, 0, "TestStatus",cellFormat));
		    sheet.addCell(new Label(4, 0, "Comments",cellFormat));
		    sheet.addCell(new Label(5, 0, "Error Type",cellFormat));
		    workbook.write(); 
		    workbook.close();
		} //if1 ends
		
		
		Workbook wb1 = Workbook.getWorkbook(ifilepath);
	    WritableWorkbook wbcopy = Workbook.createWorkbook(new File(ofilepath),wb1); 	
	    WritableSheet sheet1 = wbcopy.getSheet(0);
	    Sheet sheet = wb1.getSheet(0);
	    int newrow = sheet.getRows();
	  
	    sheet1.setName("Report");
	    WritableFont arialfont1 = new WritableFont(WritableFont.ARIAL, 10); 
	    WritableCellFormat cellFormat1 = new WritableCellFormat (arialfont1); 
	    cellFormat1.setBorder(Border.ALL,BorderLineStyle.THIN);
	    
	    WritableCellFormat passcellFormat = new WritableCellFormat (arialfont1); 
	    passcellFormat.setBackground(Colour.LIGHT_GREEN);
	    passcellFormat.setBorder(Border.ALL,BorderLineStyle.THIN);
	    WritableCellFormat failcellFormat = new WritableCellFormat (arialfont1); 
	    failcellFormat.setBackground(Colour.RED);
	    failcellFormat.setBorder(Border.ALL,BorderLineStyle.THIN);
	    
	    int col = 0;
	    int widthInChars = 12;
	    sheet1.setColumnView(col, widthInChars);
	    sheet1.addCell(new Label(col, newrow,cal1,cellFormat1));
	    col = 1;
	    widthInChars = 16;
	    sheet1.setColumnView(col, widthInChars);
	    sheet1.addCell(new Label(col, newrow,Module,cellFormat1));
	    col = 2;
	    widthInChars = 70;
	    sheet1.setColumnView(col, widthInChars);
	    if (freport) {
	    sheet1.addCell(new Label(col, newrow,Module,cellFormat1));
	    freport = false;
	    }
	    else {
	    sheet1.addCell(new Label(col, newrow,Module+" - " +Description,cellFormat1));
	    }
	    col = 3;
	    widthInChars = 15;
	    sheet1.setColumnView(col, widthInChars);
	   
	    if (Teststatus=="PASS") { //if2
	    	sheet1.addCell(new Label(col, newrow,Teststatus,passcellFormat));
	    	col = 5;
	 	    widthInChars = 20;
	 	    sheet1.setColumnView(col, widthInChars);
	    	sheet1.addCell(new Label(col, newrow,"",cellFormat1));
	    }else{
	    	sheet1.addCell(new Label(col, newrow,Teststatus,failcellFormat));	   
	    	col = 5;
	 	    widthInChars = 20;
	 	    sheet1.setColumnView(col, widthInChars);
		    sheet1.addCell(new Label(col, newrow,errorcriticality,cellFormat1));
	    } //if2 ends
	    col = 4;
	    widthInChars = 50;
	    sheet1.setColumnView(col, widthInChars);
	    sheet1.addCell(new Label(col, newrow,ErrDesc,cellFormat1));
	    
	    
	    wb1.close();	 
	    wbcopy.write();
	    wbcopy.close();
	    logfile.delete();
	    Workbook wb2 = Workbook.getWorkbook(new File(ofilepath));
	    WritableWorkbook wbmain = Workbook.createWorkbook(new File(filepath),wb2);
	    WritableSheet sheet2 = wbcopy.getSheet(0);
	    sheet2.setName("Report");
	    wbmain.write(); 
	    wbmain.close();
	    new File(ofilepath).delete();
	    //Teststatus = "PASS";
	    //driverscript.ErrDesc = "";
		
	} //report ends
} //reportgenerator
