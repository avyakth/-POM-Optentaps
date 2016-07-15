//**********************************************************
//Date of Creation 	: April 15
//Author          	: Ram Kumar Sastha Lakshmanan
//Purpose          	: Check error elements existance in the browser page
//Last Update On 	:
//Updated by       	:
//**********************************************************
import java.awt.AWTException;
import java.io.FileInputStream;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class Appium_errorchecker { //errorchecker start
	public void errorcheck() throws IOException, BiffException, RowsExceededException, WriteException, AWTException, InterruptedException {
		int i;
		boolean present = false;
		
		Appium_reportgenerator rg = new Appium_reportgenerator();
		String sheetname = Appium_driverscript.crntSheet;
		Appium_eventlogger el = new Appium_eventlogger();
		el.log("info","Error Check for "+sheetname+" page");
		String stepinfo = "Error Check for "+sheetname+" page";
		Appium_appselection.TxtAreaStep.setText(stepinfo);
		
		new WebDriverWait(Appium_driverscript.driver, 5);
		FileInputStream fis1 = new FileInputStream("C:\\Appium\\Datasheet\\"+Appium_appselection.selectedApp+"-Error Sheet.xls");
        Workbook wb1 = Workbook.getWorkbook(fis1);		
        	Sheet s1 = wb1.getSheet(sheetname);   
        String ErrModules,ErrName,ErrElementProperty,ErrElementPropValue,ErrelemPropAttr = "",ErrActioniffound,ErrType;
        String[] errelemPropArray,errelemPropValArray;
        String ErrelemPropindexAttr = "",ErrelemPropxpathAttr = "";
        	
       
        for (i=1;i<s1.getRows();i++) { //for1
        	ErrModules = s1.getCell(1,i).getContents();
        	ErrName = s1.getCell(2,i).getContents();
        	ErrElementProperty = s1.getCell(4,i).getContents();
        	ErrElementPropValue = s1.getCell(5,i).getContents();      	
        	ErrActioniffound = s1.getCell(6,i).getContents();
        	ErrType = s1.getCell(7,i).getContents();
        	boolean multiProp = ErrElementProperty.contains(",,");
        	
    		if (multiProp==true) { //if1
    			errelemPropArray = ErrElementProperty.split(",,");
    			errelemPropValArray = ErrElementPropValue.split(",,");
    			
    			if (errelemPropArray.length != errelemPropValArray.length) { //if5
    				el.log("error","Multiple Error Element property count mismatch");
    				stepinfo = "Multiple Error Element property count mismatch";
    				Appium_appselection.TxtAreaStep.setText(stepinfo);
    				 Appium_driverscript.wb.close();
    				 Appium_driverscript.fis.close();		     
    				 Appium_driverscript.driver.close();
    				 Appium_driverscript.driver.quit();
    			     System.exit(0);
    			} //if1
    			
    			for (int elp = 0;elp<errelemPropArray.length;elp++){	//for2
    				if (errelemPropArray[elp].toUpperCase().equals("INDEX")){	//if3	
    					ErrelemPropindexAttr = errelemPropValArray[elp];
    				} else {
    					if(errelemPropArray[elp].toUpperCase().equals("XPATH")){ //if4
    						ErrelemPropxpathAttr = errelemPropValArray[elp];			        						
    				} else {
        					if (elp == 0) { //if5
									if (errelemPropArray[elp].toUpperCase().equals("TAGTEXT")){ //if6
										ErrelemPropAttr = ErrelemPropAttr + "contains(.,'"+errelemPropValArray[elp]+"')";
									} else {
										ErrelemPropAttr = ErrelemPropAttr + "@"+errelemPropArray[elp]+"='"+errelemPropValArray[elp]+"'";
									} //if6

						} else {												
							if (errelemPropArray[elp].toUpperCase().equals("TAGTEXT")){ //if7
								ErrelemPropAttr = ErrelemPropAttr + " and contains(.,'"+errelemPropValArray[elp]+"')";
							} else {
								ErrelemPropAttr = ErrelemPropAttr + " and @"+errelemPropArray[elp]+"='"+errelemPropValArray[elp]+"'";
							} //if7
				
	        			} //if5 ends											
    				}//if4
    				} //if3
    			} //for2 ends
    			if (!ErrelemPropindexAttr.equals("")) { //if8
    				ErrelemPropAttr = ErrelemPropAttr + "][" + ErrelemPropindexAttr;
    			}//if8
    			
    		} else {			        				
        			if (ErrElementProperty.toUpperCase().equals("TAGTEXT")){ //if9
        				ErrelemPropAttr = "contains(.,'"+ErrElementPropValue+"')";
					} else {
						if(ErrElementProperty.toUpperCase().equals("XPATH")) { //if10
							ErrelemPropxpathAttr = ErrElementPropValue;	
					}
						ErrelemPropAttr = "@"+ErrElementProperty+"='"+ErrElementPropValue+"'";
					} //if9 ends
    		} //if1
    		if (!ErrelemPropxpathAttr.equals("")){ //if11
    			if (ErrelemPropxpathAttr.contains("//")){ //if12
    				ErrelemPropAttr = ErrelemPropxpathAttr; 			        				
    			} else {
    				ErrelemPropAttr = "//"+ErrelemPropxpathAttr;
    			} //if112
    		} else {
    			
    			ErrelemPropAttr = "//*["+ErrelemPropAttr+"]";
    		}//if11
	        	if (ErrElementProperty.length()> 1){	 //if13	
	        		try {
	        			stepinfo = "Error Check for page: "+sheetname+" Module: "+ErrModules+" - checking for Errorname "+ErrName+"("+ErrElementProperty+"='"+ErrElementPropValue+"')";
	        			Appium_appselection.TxtAreaStep.setText(stepinfo);
	        		   if (Appium_driverscript.driver.findElement(By.xpath(ErrelemPropAttr)).isDisplayed()) { //if14
	        			   Appium_fnlibrary.screencapture();
		        		   present = true;
	        		   }
	        		   
	        		} catch (NoSuchElementException e) {
	        		   present = false;
	        		}
	        		if (present){ //if15
	        			Appium_driverscript.Teststatus = "FAIL";	  
	        			Appium_driverscript.ErrDesc = ErrName+"("+ErrElementProperty+"='"+ErrElementPropValue+"')";
	        			el.log("error","Error Found : "+Appium_driverscript.ErrDesc);
	        			stepinfo = "Error Check for page: "+sheetname+" Module: "+ErrModules+" - Error Found : "+Appium_driverscript.ErrDesc;
	        			Appium_appselection.TxtAreaStep.setText(stepinfo);
	        			if (Appium_driverscript.needReport.equals("Y")) { //if16
				        	el.log("info","Step "+Appium_driverscript.stepNo+": Starting Report Generator");
		        			stepinfo = "Step "+Appium_driverscript.stepNo+": Starting Report Generator";
		        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        	rg.report();
				        	el.log("info","Report Generated");
				        	stepinfo = "Report Generated";
		        			Appium_appselection.TxtAreaStep.setText(stepinfo);
		        			Appium_driverscript.errorcriticality = ErrType.toUpperCase();
				        } //if16
	        			if (Appium_driverscript.ElementInputValue.toUpperCase().equals("TERMINATE") || ErrActioniffound.toUpperCase().equals("TERMINATE")){ //if17	        				
	        				el.log("info","Step "+Appium_driverscript.stepNo+": Crtical error");
		        			stepinfo = "Step "+Appium_driverscript.stepNo+": Crtical error";
		        			Appium_driverscript.errorcriticality = "CRITICAL";
		        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        	rg.report();
				        	
				        	stepinfo = "Execution Interupted";
		    				Appium_appselection.TxtAreaStep.setText(stepinfo);
		    				Thread.sleep(1000);    
		    				stepinfo = "Closing input sheet";
		    				Appium_appselection.TxtAreaStep.setText(stepinfo);	
		    				wb1.close();
		    				Appium_driverscript.wb.close();
		    		      Thread.sleep(1000);
		    		      fis1.close();
		    		      Appium_driverscript.fis.close();	
		    		      stepinfo = "Closing "+Appium_driverscript.Browsername+" Driver";
		    			  Appium_appselection.TxtAreaStep.setText(stepinfo);	
		    			  Thread.sleep(1000);
		    			  Appium_driverscript.driver.close();
		    			  Appium_driverscript.driver.quit();
		    		      stepinfo = "Composing report mail...";
		    			  Appium_appselection.TxtAreaStep.setText(stepinfo);	
		    		      try {
		    		         Runtime.getRuntime().exec("WScript C:/Appium/library/mail.vbs "+"\""+Appium_appselection.selectedApp+"\"");
		    		         stepinfo = "Report mail has been sent...";
		    				 Appium_appselection.TxtAreaStep.setText(stepinfo);
		    		      }
		    		      catch( IOException em ) {
		    		    	 stepinfo = "Error sending Report mail...";
		    				 Appium_appselection.TxtAreaStep.setText(stepinfo);
		    				 el.log("error",em.getMessage());	
		    		         em.printStackTrace();
		    		      }

		    		      stepinfo = "Exiting...";
		    			  Appium_appselection.TxtAreaStep.setText(stepinfo);	
		    			  Thread.sleep(1000);
		    		      System.exit(0);

	        			} //if17 ends
	        		} //if15 ends
	        	} //if13 ends	        		
	        	} //for1 ends
	        if (Appium_driverscript.Teststatus.equals("PASS")) {  //if18
	        	el.log("info","No Error Found");
	        	stepinfo = "Functionality :"+sheetname+" - No Error Found";
    			Appium_appselection.TxtAreaStep.setText(stepinfo);
	        } //if18        
	        		
       wb1.close();
      fis1.close();

	new WebDriverWait(Appium_driverscript.driver, 20);
	} //errorcheck
} //errorchecker ends
