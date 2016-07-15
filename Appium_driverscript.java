//**********************************************************
//Date of Creation 	: April 15
//Author          	: Ram Kumar Sastha Lakshmanan
//Purpose          	: Main Driver to handle execution
//Last Update On 	:
//Updated by       	:
//**********************************************************


import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Appium_driverscript {
	public String TestName;
	public static String Teststatus = "PASS";
	public static String crntSheet;
	public static String crntStep = "";
	static boolean freport = false;
	public static String stepNo;
	public static String ErrDesc = "";	
	public static String errorcriticality = "NON-CRITICAL";
	public static String FunctionalityToExecute = "";
	public static String StepsToExecute = "";
	public static String Browsername;
	public static String deviceName = "", platformVersion = "", platformName = "", appPackage = "", appActivity = "", ipAddress = "", port ="" ;
	public static String[] FunctionalityArray = new String[50] ;
	public static String[] StepsArray = new String[50];
	public static String[] DummyArr = new String[50];	
	public static String[] StoreVariable = new String[100];
	public static String[] StoreVariableValue = new String[100];
	//public static String StoreVariableName = "";
	static int StoreVariableCount=0;
	static int[] step_start = new int[50];
	static int[] step_end = new int[50];
	static String[] sheetCount;
	static int NoOfSheets;
	//public static WebDriver dr;		
	public static FileInputStream fis;
	public static Workbook wb;
	public static Sheet s;
	static boolean Exist;
	static boolean varExist=false;
	static String appFilename,txtareastep;
	static String stepinfo = "";
	static String[] arg = new String[5];
	static Graphics g;
	//static boolean startexec=false;
	public static String Module,ElementName,ElementProperty,ElementPropValue,elemPropAttr;
	public static String ElementAction,ElementInputValue,ElementChkProperty,ElementChkPropertyValue,needReport, actionifnotfound,errortype;
	public static String capValue = "";	
	public static Hashtable store = new Hashtable();
	public static String status = "";
	public static String childWindow = "", ParentWindow = "";
	static File pathToBinary = null;
	
	public static AndroidDriver driver;
	public static JFrame mainFrame;
	public static JPanel controlPanel;
	public static String Activation_code = "";
	
	public static void main(String[] args) throws IOException, AWTException, RowsExceededException, WriteException, BiffException, InterruptedException {
		Appium_errorchecker ec = new Appium_errorchecker();
		Appium_reportgenerator rg = new Appium_reportgenerator();
		Appium_eventlogger el = new Appium_eventlogger();
		Appium_fnlibrary fl = new Appium_fnlibrary();
		Robot robot = new Robot();
		
		try { //try1
			//System.out.println(args.length);
			int i,j,keypress;
					
			String[] elemPropArray = null;
			String[] elemPropValArray = null;
			String elemPropxpathAttr = "";
			String elemPropindexAttr = "";
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
			if(args.length != 0){
				varExist = true;
				for(int ar = 0;ar<args.length;ar++) {
					arg[ar]=args[ar].toUpperCase();
				}			
			}
			Appium_appselection.appdisplay();
			if (args.length==0){
				while(!Appium_appselection.StartExec) { //while1
					Thread.sleep(500);
				} //while1 ends				
			} 
			stepinfo = "Creating base logs";
			Appium_appselection.TxtAreaStep.setText(stepinfo);
			el.baselog();
			fis = new FileInputStream("C:\\Appium\\Datasheet\\"+Appium_appselection.compFilename);
	        wb = Workbook.getWorkbook(fis);        
	        final Sheet[] sheets = wb.getSheets(); 	
	        s = wb.getSheet(0);
	        crntSheet = s.getName();	

	        el.log("config", "Gathering info from App Config");
	        stepinfo = "Gathering info from App Config";
	        Appium_appselection.TxtAreaStep.setText(stepinfo);
	        el.func_log();
	       
	        String urlToExec = s.getCell(0,1).getContents();
	        Browsername = s.getCell(3,1).getContents();
	        FunctionalityToExecute = s.getCell(1,1).getContents();
	        StepsToExecute = s.getCell(2,1).getContents();
	        
	        deviceName = s.getCell(6,1).getContents();
	        platformVersion= s.getCell(7,1).getContents();
	        platformName= s.getCell(8,1).getContents();
	        appPackage= s.getCell(9,1).getContents();
	        appActivity = s.getCell(10,1).getContents();
	        ipAddress = s.getCell(11,1).getContents();
	        port = s.getCell(12,1).getContents();
	        if (FunctionalityToExecute.contains(","))
	        { //if1
	        	FunctionalityArray = FunctionalityToExecute.split(",");
	        	StepsArray = StepsToExecute.split(",");
	        	sheetCount = StepsToExecute.split(",");
	        	if (FunctionalityArray.length != StepsArray.length) 
	        	{ //if2
    				el.log("error","Functionality & Steps to Execute count mismatch");
    				stepinfo = "Error: Functionality & Steps to Execute count mismatch";
    				Appium_appselection.TxtAreaStep.setText(stepinfo);
    				 
    				 wb.close();
    			     fis.close();	
    			     System.exit(0);
    			} //if2 ends
	        	NoOfSheets = FunctionalityArray.length;	    
	        	 
	        	for (int stp_array = 0;stp_array<FunctionalityArray.length;stp_array++)
	        	{ //for1
	        		while(!Appium_appselection.StartExec) 
	        		{ //while
	        			Appium_appselection.TxtAreaStep.setText("Execution Paused...");
	        			Appium_appselection.brunps.setText("Resume");
	    				Thread.sleep(500);
	    			} //while ends
	        		if (StepsArray[stp_array].toUpperCase().equals("ALL")) 
	        		{ //if3
	        			step_start[stp_array]=1;
	        			try
	        			{
		        			int num = Integer.parseInt(FunctionalityArray[stp_array]);	
		        			step_end[stp_array]= wb.getSheet(sheets[num-1].getName()).getRows()-1;		        			  				  
	      				} 
	        			catch (NumberFormatException e) 
	        			{
	      					step_end[stp_array]= wb.getSheet(FunctionalityArray[stp_array]).getRows()-1;	    
	      				}
	        			
	        		} 
	        		else 
	        		{
	        			DummyArr = StepsArray[stp_array].split("-");
		        		step_start[stp_array] = Integer.valueOf(DummyArr[0])+1;
		        		step_end[stp_array] = Integer.valueOf(DummyArr[1])+1;
	        		} //if3 ends
	        		
	        	} //for1 ends 
	        	
	        }
	        else 
	        {	 
	        		if(!FunctionalityToExecute.toUpperCase().equals("ALL"))
		        	{
		        			NoOfSheets = 1;
		        		try
		        		{
		        			int num = Integer.parseInt(FunctionalityToExecute);	        			
		        			FunctionalityArray[0] = sheets[num-1].getName();      				  
	      				} 
		        		catch (NumberFormatException e) 
		        		{
		        			FunctionalityArray[0]=FunctionalityToExecute;	    
	      				}
		        			s = wb.getSheet(FunctionalityArray[0]);
		        			if (StepsToExecute.contains("-"))
		        			{
		        				DummyArr = StepsToExecute.split("-");
				        		step_start[0] = Integer.valueOf(DummyArr[0])+1;
				        		step_end[0] = Integer.valueOf(DummyArr[1])+1;
		        			} 
		        			else 
		        			{
		        				step_start[0] = 1;
		        				step_end[0] = s.getRows()-1;
		        			}
			        		
		        	} 
	        		else 
	        		{
	        			NoOfSheets = wb.getNumberOfSheets()-1;
						for(int k = 1;k<=NoOfSheets;k++) 
						{ //for2								
							s = wb.getSheet(k);									
							FunctionalityArray[k-1] = s.getName();
							step_start[k-1] = 1;							
			        		step_end[k-1] = s.getRows()-1;	
						} //for2 ends	
	        		}	        		
	        		
	        } //if1 ends
	       
	        while(!Appium_appselection.StartExec) { //while
	        	Appium_appselection.TxtAreaStep.setText("Execution Paused...");
	        	Appium_appselection.brunps.setText("Resume");
	        	Thread.sleep(500);
			} //while ends
	        DesiredCapabilities capabilities = new DesiredCapabilities();
			if (Browsername.toUpperCase().equals("CHROME")){				
				capabilities.setCapability("chromedriverExecutable", "C:\\Appium\\chromedriver\\chromedriver.exe");		
				capabilities.setCapability("browserName","Chrome");
				stepinfo = "Launching Chrome Browser";				
				Appium_appselection.TxtAreaStep.setText(stepinfo);
			} else if (Browsername.toUpperCase().equals("BROWSER")){
				capabilities.setCapability("browserName","Browser");
				stepinfo = "Launching Native Browser";				
				Appium_appselection.TxtAreaStep.setText(stepinfo);
			}
			else{
				stepinfo = "Launching Android App";				
				Appium_appselection.TxtAreaStep.setText(stepinfo);
				capabilities.setCapability(MobileCapabilityType.APP_PACKAGE,appPackage);
				capabilities.setCapability("appActivity",appActivity);
				capabilities.setCapability(MobileCapabilityType.SUPPORTS_APPLICATION_CACHE, "true");
			}
			capabilities.setCapability("newCommandTimeout", "60000");
	        capabilities.setCapability("deviceName", deviceName);
	        capabilities.setCapability("platformVersion", platformVersion);
	        capabilities.setCapability("platformName", platformName); 
	       
			driver = new AndroidDriver(new URL("http://"+ipAddress+":"+port+"/wd/hub"),capabilities);
			driver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
			if (Browsername.toUpperCase().equals("CHROME") || Browsername.toUpperCase().equals("BROWSER")){	
				driver.get(urlToExec);
				new WebDriverWait(driver, 30);
			}
			/*else
				Thread.sleep(45000);*/
			
			stepinfo = "Launching URL: "+urlToExec;
			Appium_appselection.TxtAreaStep.setText(stepinfo);
	        el.log("config", "App: launched");
	        while(!Appium_appselection.StartExec) { //while
	        	Appium_appselection.TxtAreaStep.setText("Execution Paused...");
	        	Appium_appselection.brunps.setText("Resume");
				Thread.sleep(500);
			} //while ends
			for(j=0;j<NoOfSheets;j++){ //for3
				try{
        			int num = Integer.parseInt(FunctionalityArray[j]);	        			
        			s = wb.getSheet(sheets[num-1].getName());
  				} catch (NumberFormatException e) {
  					s = wb.getSheet(FunctionalityArray[j]);     
  				}
	        	       	
	        	crntSheet = s.getName();
	        	el.func_log();
		        for (i=step_start[j];i<=step_end[j];i++) {  //for4	    
				        	while(!Appium_appselection.StartExec) { //while
				        		Appium_appselection.TxtAreaStep.setText("Execution Paused...");
				        		Appium_appselection.brunps.setText("Resume");
			    				Thread.sleep(500);
			    			} //while ends
				        	elemPropAttr = "";
				        	elemPropxpathAttr = "";
				        	stepNo = s.getCell(0,i).getContents();		
				        	Module = s.getCell(1,i).getContents();	
				        	ElementName = s.getCell(2,i).getContents();					        	
				        	ElementProperty = s.getCell(3,i).getContents();	
				        	ElementPropValue = s.getCell(4,i).getContents();	
				        	ElementAction = s.getCell(5,i).getContents();	
				        	ElementInputValue = s.getCell(6,i).getContents();	
				        	ElementChkProperty = s.getCell(7,i).getContents();	
				        	ElementChkPropertyValue = s.getCell(8,i).getContents();				        	
				        	needReport = s.getCell(9,i).getContents();
				        	actionifnotfound = s.getCell(10,i).getContents();
				        	errortype = s.getCell(11,i).getContents();
				        	//StoreVariableName = s.getCell(10,i).getContents();
				        	crntStep = ElementName+" ("+ElementProperty+"="+ElementPropValue+") value "+ElementInputValue + " Action - "+ ElementAction + " ElementInputValue - "+ ElementInputValue;
				        	errorcriticality = errortype;
			        		if(ElementInputValue.toUpperCase().contains("VAR(")){
			        			String VarName = ElementInputValue.substring(ElementInputValue.indexOf("(")+1, ElementInputValue.lastIndexOf(")")).toUpperCase();
			        			if(Arrays.asList(StoreVariable).contains(VarName)){
			        				ElementInputValue = StoreVariableValue[Arrays.asList(StoreVariable).indexOf(VarName)];
			        			}
			        		}
			        		boolean multiProp = ElementProperty.contains(",,");
			        		
			        		if (multiProp==true) 
			        		{ //if4
			        			elemPropArray = ElementProperty.split(",,");
			        			elemPropValArray = ElementPropValue.split(",,");
			        			
			        			if (elemPropArray.length != elemPropValArray.length) 
			        			{ //if5
			        				el.log("error","Multiple Element property count mismatch");
			        				stepinfo = "Multiple Element property count mismatch";
			        				Appium_appselection.TxtAreaStep.setText(stepinfo);
			        				
			        				 wb.close();
			        			     fis.close();	
			        			     if (Browsername.toUpperCase().equals("CHROME") || Browsername.toUpperCase().equals("BROWSER")){	
			        			    	 driver.close();
				        			     driver.quit();
			        			     }
			        			     System.exit(0);
			        			} //if5
			        			
			        			for (int elp = 0;elp<elemPropArray.length;elp++)
			        			{	//for5	
			        				if (elemPropArray[elp].toUpperCase().equals("INDEX"))
			        				{		
			        					elemPropindexAttr = elemPropValArray[elp];
			        				}
			        				else 
			        				{
			        					if(elemPropArray[elp].toUpperCase().equals("XPATH"))
			        					{
			        						elemPropxpathAttr = elemPropValArray[elp];			        						
			        					}
			        					else 
			        					{
				        					if (elp == 0) 
				        					{
				        						//if6	
													if (elemPropArray[elp].toUpperCase().equals("TAGTEXT"))
													{
														elemPropAttr = elemPropAttr + "contains(.,'"+elemPropValArray[elp]+"')";
													} 
													else 
													{
														elemPropAttr = elemPropAttr + "@"+elemPropArray[elp]+"='"+elemPropValArray[elp]+"'";
													}
				        					} 
				        					else 
				        					{
				        						if (elemPropArray[elp].toUpperCase().equals("TAGTEXT"))
				        						{
				        							elemPropAttr = elemPropAttr + " and contains(.,'"+elemPropValArray[elp]+"')";
				        						} 
				        						else 
				        						{
				        							elemPropAttr = elemPropAttr + " and @"+elemPropArray[elp]+"='"+elemPropValArray[elp]+"'";
				        						}
				        					} //if6 ends											
			        					}
			        				}
			        			} //for5 ends
			        			if (!elemPropindexAttr.equals("")) 
			        			{
			        				elemPropAttr = elemPropAttr + "][" + elemPropindexAttr;
			        			}
			        			
			        		} 
			        		else 
			        		{			        				
				        			if (ElementProperty.toUpperCase().equals("TAGTEXT"))
				        			{ //if7
										elemPropAttr = "contains(.,'"+ElementPropValue+"')";
									} 
				        			else 
				        			{
										if(ElementProperty.toUpperCase().equals("XPATH"))
										{
											elemPropxpathAttr = ElementPropValue;	
										}
										elemPropAttr = "@"+ElementProperty+"='"+ElementPropValue+"'";
									} //if7 ends
			        		} //if4
			        		if (!elemPropxpathAttr.equals(""))
			        		{
			        			if (elemPropxpathAttr.contains("//"))
			        			{
			        				elemPropAttr = elemPropxpathAttr; 			        				
			        			} 
			        			else 
			        			{
			        				elemPropAttr = "//"+elemPropxpathAttr;
			        			}
			        		} 
			        		else 
			        		{
			        			elemPropAttr = "//*["+elemPropAttr+"]";
			        		}
			        		switch(ElementAction.toUpperCase()){
			        			case "DRAW":
			        				status = "info";
				        			Teststatus = "PASS";
				        			TouchAction line = (new TouchAction(driver));
				        			int x1 = Integer.parseInt(ElementProperty);
				        			int y1 = Integer.parseInt(ElementPropValue);
				        			int x2 = Integer.parseInt(ElementInputValue);
				        			int y2 = Integer.parseInt(ElementChkProperty);
		        					line.press(x1, y1);
		        					line.moveTo(x2, y2);
		        					line.release(); 
		        					line.perform();
				        			stepinfo = "DRAW is completed successfully";
			        				ErrDesc = stepinfo;
			        				Appium_appselection.TxtAreaStep.setText(stepinfo);
			        			break;
			        			case "CLICKBYXY":
			        				try {
				        				status = "info";
				        				Teststatus = "PASS";
					        			Appium_appselection.TxtAreaStep.setText("Executing Sheet '"+Appium_driverscript.crntSheet+"' - Waiting for - Step "+Appium_driverscript.stepNo+": "+ElementName+" ("+elemPropAttr+")");
					        			
					        			stepinfo = "Executing Sheet '"+crntSheet+"' - Step "+stepNo+": "+ElementName+" (X="+ElementProperty+" Y="+ElementPropValue+" "+ " clicked";					        			
					        			Appium_appselection.TxtAreaStep.setText(stepinfo);					        			
					        			TouchAction line1 = (new TouchAction(driver));
					        			int x = Integer.parseInt(ElementProperty);
					        			int y = Integer.parseInt(ElementPropValue);
			        					line1.press(x, y);
			        					line1.release(); 
			        					line1.perform();
					        			
				        			} catch (Exception e) {
				        				if(!ElementAction.toUpperCase().equals("CLICKIFEXIST")){
				        					status = "error";
				        					Teststatus = "FAIL";
				        					stepinfo = ElementName+" (X="+ElementProperty+" Y="+ElementPropValue+") has not been selected " + e.getMessage();
					        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				}else {
				        					Teststatus = "PASS";
				        					status = "warning";
					        				stepinfo = "info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
							        		Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				}
				        			 }
				        			Thread.sleep(500);
				        			ErrDesc = stepinfo;
			        			break;
			        			case "SWIPE":
			        				status = "info";
				        			Teststatus = "PASS";
			        				Dimension size = driver.manage().window().getSize(); 
			        				float startx_value = Float.parseFloat(ElementProperty);
			        				float starty_value = Float.parseFloat(ElementPropValue);
			        				float endx_value = Float.parseFloat(ElementInputValue);
			        				float endy_value = Float.parseFloat(ElementChkProperty);
			        				int startx = (int) (size.width * startx_value); 
			        				int starty = (int) (size.height * starty_value); 
			        				int endx = (int) (size.width * endx_value); 
			        				int endy = (int) (size.height * endy_value); 
			        				driver.swipe(startx, starty, endx, endy, 5000);
			        				stepinfo = "SWIPE is completed successfully";
			        				ErrDesc = stepinfo;
			        				Appium_appselection.TxtAreaStep.setText(stepinfo);
			        			break;
			        			case "ACTIVATION_CODE":
			        				try{
			        					  mainFrame = new JFrame("CTC - One-Time Activation code");
			        					  mainFrame.setSize(350, 150); 
			        					  mainFrame.setLayout(new GridLayout(3, 1)); 
			        					  controlPanel = new JPanel(); 
			        					  mainFrame.add(controlPanel);
			        					  mainFrame.setVisible(true); 
			        					  final JTextField userText = new JTextField(6); 
			        					  controlPanel.add(userText); 
			        					  mainFrame.setVisible(true);
			        					  Thread.sleep(20000);
			        					  Activation_code = userText.getText();
        								  driver.findElement(By.xpath(elemPropAttr)).sendKeys(Activation_code);
        								  stepinfo ="CTC - One-Time Activation code";
        								  ErrDesc = stepinfo;
        								  Appium_appselection.TxtAreaStep.setText(stepinfo);
			        				   }catch(Exception e){}
			        			break;
			        			case "DRAGANDDROP":
			        				try {
				        				status = "info";
				        				Teststatus = "PASS";
				        				Appium_appselection.TxtAreaStep.setText("Executing Sheet '"+Appium_driverscript.crntSheet+"' - Waiting for - Step "+Appium_driverscript.stepNo+": "+ElementName+" ("+elemPropAttr+")");
					        			stepinfo = "Executing Sheet '"+crntSheet+"' - Step "+stepNo+": "+ElementName+" (From Source "+ElementProperty+"="+ElementPropValue+") "+ElementAction+" to Target "+ElementInputValue ;					        			
					        			Appium_appselection.TxtAreaStep.setText(stepinfo);					        			
					        			Actions actions = new Actions(driver);
					        			WebElement source = driver.findElement(By.xpath(elemPropAttr));
					        			WebElement target = driver.findElement(By.xpath(ElementInputValue));
					        			actions.dragAndDrop(source, target).build().perform();
				        			} catch (Exception e) {
				        				if(!ElementAction.toUpperCase().equals("DRAGANDDROPIFEXIST")){
				        					status = "error";
				        					Teststatus = "FAIL";
				        					stepinfo = ElementName+" (From Source "+ElementProperty+"="+ElementPropValue+") "+ElementAction+" to Target "+ElementInputValue+" has not been  DRAGANDDROP" + e.getMessage();
				        					Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				}else {
				        					Teststatus = "PASS";
				        					status = "warning";
					        				stepinfo = "info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
					        				Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				}
				        			 }
				        			Thread.sleep(500);
				        			ErrDesc = stepinfo;
				        			break;
				        		case "SET": case "SETIFEXIST":
				        			try { 
				        				status = "info";
				        				Teststatus = "PASS";
				        				Appium_appselection.TxtAreaStep.setText("Executing Sheet '"+crntSheet+"' - Waiting for - Step "+stepNo+": "+ElementName+"("+elemPropAttr+")");
					        			fl.waitforelement(elemPropAttr);	
					        			if(ElementPropValue.toUpperCase().contains("PWD") | ElementPropValue.toUpperCase().contains("PASSWORD")) {
					        				stepinfo = "Executing Sheet '"+crntSheet+"' - Step "+stepNo+": Entering password";
					        			} else {
					        				stepinfo = "Executing Sheet '"+crntSheet+"' - Step "+stepNo+": "+ElementName+"("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue;
					        			}
					        			
						        		Appium_appselection.TxtAreaStep.setText(stepinfo);
						        		if (driver.findElement(By.xpath(elemPropAttr)).getTagName().toUpperCase().equals("TEXTAREA")) {						        			
						        			WebElement element = driver.findElement((By.xpath(elemPropAttr)));
						        			((JavascriptExecutor)driver).executeScript("arguments[0].value = arguments[1];", element, ElementInputValue);
						        			stepinfo = " ("+ElementProperty+"="+ElementPropValue+") value entered with given XML";
						        			//el.log("info","Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") value entered with given XML");
						        		} else {						        			
						        			driver.findElement(By.xpath(elemPropAttr)).sendKeys();				   
						        			Thread.sleep(500);
						        			driver.findElement(By.xpath(elemPropAttr)).clear();
						        			Thread.sleep(500);
						        			driver.findElement(By.xpath(elemPropAttr)).sendKeys(ElementInputValue);	
						        			stepinfo = " ("+ElementProperty+"="+ElementPropValue+") value entered as "+ElementInputValue+")";
						        			//el.log("info","Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") value entered as "+ElementInputValue);						        			
						        			}						        		
				        			} catch (Exception e) {
				        				if(!ElementAction.toUpperCase().equals("SETIFEXIST")){
				        					status = "error";
				        					Teststatus = "FAIL";
				        					stepinfo = " ("+ElementProperty+"="+ElementPropValue+") to set value as "+ElementInputValue + " "+ e.getMessage();
					        				 //el.log("error","Error Executing Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") to set value as "+ElementInputValue);
					        				 //el.log("error","Error Desc: "+e.getMessage());
					        				 stepinfo = "Step "+stepNo+": error : " + e.getMessage();
						        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				}else {
				        					Teststatus = "PASS";
				        					status = "warning";
				        					//el.log("warning","Element not found Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") to set value as "+ElementInputValue+".Error IGNORED as its "+ElementAction.toUpperCase()+" action");					        				
					        				stepinfo = ": info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
						        			Appium_appselection.TxtAreaStep.setText(stepinfo);
					        			}
				        			 }
				        			Thread.sleep(500);
				        			ErrDesc = stepinfo;
				        			break;
				        		case "CLICK": case "CLICKIFEXIST":
				        			try {
				        				status = "info";
				        				Teststatus = "PASS";
					        			Appium_appselection.TxtAreaStep.setText("Executing Sheet '"+Appium_driverscript.crntSheet+"' - Waiting for - Step "+Appium_driverscript.stepNo+": "+ElementName+" ("+elemPropAttr+")");
					        			fl.waitforelement(elemPropAttr);	
					        			stepinfo = "Executing Sheet '"+crntSheet+"' - Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue + " clicked";					        			
					        			Appium_appselection.TxtAreaStep.setText(stepinfo);					        			
					        			driver.findElement(By.xpath(elemPropAttr)).click();
					        			//el.log("info","Step "+stepNo+": "+ElementName+" "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") has been selected.");
				        			} catch (Exception e) {
				        				if(!ElementAction.toUpperCase().equals("CLICKIFEXIST")){
				        					status = "error";
				        					Teststatus = "FAIL";
				        					stepinfo = ElementName+" ("+ElementProperty+"="+ElementPropValue+") has not been selected " + e.getMessage();
				        				 //el.log("error","Error Executing Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") has not been selected.");
				        				 //el.log("error","Error Desc: "+e.getMessage());
				        				 //stepinfo = "Step "+stepNo+": error : " + e.getMessage();
					        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				}else {
				        					Teststatus = "PASS";
				        					status = "warning";
				        					//el.log("warning","Element not found "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") has not been selected.Error IGNORED as its "+ElementAction.toUpperCase()+" action");						        				
					        				stepinfo = "info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
							        		Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				}
				        			 }
				        			Thread.sleep(500);
				        			ErrDesc = stepinfo;
					            	break;
				        		case "DBLCLICK": case "DBLCLICKIFEXIST":
				        			try { 
				        				status = "info";
				        				Teststatus = "PASS";
					        			Appium_appselection.TxtAreaStep.setText("Executing Sheet '"+Appium_driverscript.crntSheet+"' - Waiting for - Step "+Appium_driverscript.stepNo+": "+ElementName+"("+elemPropAttr+")");
					        			fl.waitforelement(elemPropAttr);	
					        			stepinfo = "Executing Sheet '"+crntSheet+"' - Step "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue;					        			
					        			Appium_appselection.TxtAreaStep.setText(stepinfo);					        			
					        			Actions doubleClick = new Actions(driver).doubleClick(driver.findElement(By.xpath(elemPropAttr)));
				        				doubleClick.build().perform();
					        			//el.log("info","Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") has been selected.");
				        			} catch (Exception e) {
				        				if(!ElementAction.toUpperCase().equals("DBLCLICKIFEXIST")){
				        					status = "error";
				        					Teststatus = "FAIL";
				        				 //el.log("error","Error Executing Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") has not been selected.");
				        				 //el.log("error","Error Desc: "+e.getMessage());
				        				 stepinfo = "error : Error Executing Step "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") has not been selected." + e.getMessage();
					        			 Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				} else {
				        					Teststatus = "PASS";
				        					status = "warning";
				        					 el.log("warning","Element not found Step "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") has not been selected.Error IGNORED as its "+ElementAction.toUpperCase()+" action");
				        					 stepinfo = "Element not found Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") has not been selected.Error IGNORED as its "+ElementAction.toUpperCase()+" action info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
						        			 Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				}
				        			 }
				        			Thread.sleep(500);
				        			ErrDesc = stepinfo;
					            	break;
				        		case "SELECT": case "INDEX": case "SELECTIFEXIST": case "INDEXIFEXIST":
				        			status = "info";
				        			Teststatus = "PASS";
				        			Appium_appselection.TxtAreaStep.setText("Executing Sheet '"+Appium_driverscript.crntSheet+"' - Waiting for - Step "+Appium_driverscript.stepNo+": "+ElementName+"("+elemPropAttr+")");
				        			fl.waitforelement(elemPropAttr);	
				        			//stepinfo = "Executing Sheet '"+crntSheet+"' - Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue;
				        			Appium_appselection.TxtAreaStep.setText(stepinfo);				        			
				        			try { 			
				        				Select DrpDown = new Select(driver.findElement(By.xpath(elemPropAttr)));
					        			 if (ElementAction.toUpperCase().equals("INDEX")) 
					        			 {
					        				 DrpDown.selectByIndex(Integer.valueOf(ElementInputValue));
					        			 }
					        			 else if(ElementAction.toUpperCase().equals("SELECTBYVISIBLETEXT")) 
					        			 {
					        				 DrpDown.selectByVisibleText(ElementInputValue);
					        			 }
					        			 else
					        			 {
					        				 DrpDown.selectByValue(String.valueOf(ElementInputValue));
					        			 }		
					        			 stepinfo = ElementName+" ("+ElementProperty+"="+ElementPropValue+") selected with value '"+ElementInputValue+"'";
						            	 //el.log("info","Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") selected with value '"+ElementInputValue+"'");
				        			 } catch (Exception e) {
				        				 if(!ElementAction.toUpperCase().contains("IFEXIST")){
				        					 status = "error";
				        					 Teststatus = "FAIL";
				        					 stepinfo = "Error Executing Step "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") to select value as '"+ElementInputValue+"' "+e.getMessage();
					        				 //el.log("error","Error Executing Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") to select value as '"+ElementInputValue+"'");
					        				 //el.log("error","Error Desc: "+e.getMessage());
					        				 //stepinfo = "Step "+stepNo+": error : " + e.getMessage();
					        				Appium_appselection.TxtAreaStep.setText(stepinfo);
				        			 } else {
				        				 Teststatus = "PASS";
				        				 status = "warning";
				        				 stepinfo = "Element not found Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") to select value as '"+ElementInputValue+"'.Error IGNORED as its "+ElementAction.toUpperCase()+" action"+ e.getMessage();
				        				 //el.log("warning","Element not found Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") to select value as '"+ElementInputValue+"'.Error IGNORED as its "+ElementAction.toUpperCase()+" action");
			        					 //stepinfo = "Step "+stepNo+": info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
					        			 Appium_appselection.TxtAreaStep.setText(stepinfo);
				        			 }
				        			 }
					            	 Thread.sleep(500);
					            	 ErrDesc = stepinfo;
					            	 break;
				        		case "FRAMECHANGE": case "FRAMECHANGEIFEXIST":
				        			 try {
				        				 status = "info";
				        				 Teststatus = "PASS";
				        				 if (ElementPropValue.toUpperCase().equals("DEFAULT")) {
				        					 driver.switchTo().defaultContent();
				        				 }else {
				        				 driver.switchTo().frame(ElementPropValue);	  
				        				 Thread.sleep(1000);
				        				 }
				        				 stepinfo = "Frame pointer changed to "+ElementPropValue;
				        				 //el.log("config","Step "+stepNo+": Frame pointer changed to "+ElementPropValue);
				        			 }catch (Exception e) {
				        				 if(!ElementAction.toUpperCase().equals("FRAMECHANGEIFEXIST")){
				        					 status = "error";
				        					 Teststatus = "FAIL";
				        					// el.log("error","Error Executing Step "+stepNo+": Frame pointer not changed to "+ElementPropValue);
				        					 //el.log("error","Error Desc: "+e.getMessage());
				        					 stepinfo = "Error Executing Step "+stepNo+": Frame pointer not changed to "+ElementPropValue+" error : " + e.getMessage();
					        				Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				 } else {
				        					 Teststatus = "PASS";
				        					 status = "warning";
				        					 //el.log("warning","Element not found Step "+stepNo+": Frame pointer not changed to "+ElementPropValue+" Error IGNORED as its "+ElementAction.toUpperCase()+" action");
				        					 stepinfo = "Step "+stepNo+": info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
						        			 Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				 }
				        			 }
				        			 ErrDesc = stepinfo;
				        			 break;
				        		case "CHKBOX": case "CHKBOXIFEXIST":
				        			try {
				        				status = "info";
				        				Teststatus = "PASS";
					        			 Appium_appselection.TxtAreaStep.setText("Executing Sheet '"+Appium_driverscript.crntSheet+"' - Waiting for - Step "+Appium_driverscript.stepNo+": "+ElementName+"("+elemPropAttr+")");
					        			 fl.waitforelement(elemPropAttr);	
						        		//stepinfo = "Executing Sheet '"+crntSheet+"'"+ElementName+" ("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue;
						        		Appium_appselection.TxtAreaStep.setText(stepinfo);
					        			if (ElementInputValue.toUpperCase().equals("ON")){
					        				if (!driver.findElement(By.xpath(elemPropAttr)).isSelected()) {
					        					driver.findElement(By.xpath(elemPropAttr)).click();
						        			}
					        			} else {
				        					if (driver.findElement(By.xpath(elemPropAttr)).isSelected()) {
				        						driver.findElement(By.xpath(elemPropAttr)).click();
						        			}					        					
					        			}	
					        			stepinfo = ElementName+" ("+ElementProperty+"="+ElementPropValue+") value set to '"+ElementInputValue+"'";
					            	 	//el.log("info","Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") value set to '"+ElementInputValue+"'");
				        			 }catch (Exception e) {
				        				 if(!ElementAction.toUpperCase().equals("CHECKIFEXIST")){
				        					 status = "error";
				        					 Teststatus = "FAIL";
				        					 stepinfo = "Error Executing Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") to set value as '"+ElementInputValue+"'. Error IGNORED as its "+ElementAction.toUpperCase()+" action" +  e.getMessage();
					        				 //el.log("error","Error Executing Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") to set value as '"+ElementInputValue+"'. Error IGNORED as its "+ElementAction.toUpperCase()+" action");
					        				 //el.log("error","Error Desc: "+e.getMessage());
					        				 //stepinfo = "Step "+stepNo+": error : " + e.getMessage();
						        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				 } else {
				        					 status = "warning";
				        					 Teststatus = "PASS";
				        					 stepinfo = "Element not found. Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") to set value as '"+ElementInputValue+"' "+ e.getMessage(); 
				        					 //el.log("error","Element not found. Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") to set value as '"+ElementInputValue+"'");
					        				 
					        				 //stepinfo = "Step "+stepNo+": error : " + e.getMessage();
						        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				 }
				        			 }
				            	 	Thread.sleep(500);
				            	 	ErrDesc = stepinfo;
				        			break;
				        		case "VERIFY": case "VERIFYIFEXIST":
				        				status = "info";
				        				Teststatus = "PASS";
				        				ErrDesc ="Element: "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") Expected: "+ElementChkProperty+" = "+ElementChkPropertyValue;
					        			stepinfo = "Executing Sheet '"+crntSheet+"' - "+ ErrDesc;
				        				Appium_appselection.TxtAreaStep.setText(stepinfo);
						        		//el.log("info",ErrDesc);
						        		if(ElementChkProperty.toUpperCase().equals("TAGTEXT")) 
						        		{
						        			try 
						        			{
							        			if (!driver.getPageSource().contains(">"+ElementChkPropertyValue.toUpperCase()+"<") && !driver.getPageSource().contains(">"+ElementChkPropertyValue.toLowerCase()+"<") && !driver.getPageSource().contains(">"+ElementChkPropertyValue+"<")) 
							        			{	
							        				status = "error";
							        				Teststatus = "FAIL";	
							        				//errorcriticality = ElementInputValue.toUpperCase();
								        			ErrDesc = ">"+ElementChkPropertyValue.toUpperCase()+"<" + " not found";
								        			stepinfo = ErrDesc;
								        			//el.log("error",ErrDesc);
							        			}
							        			else if (driver.getPageSource().contains(">"+ElementChkPropertyValue.toUpperCase()+"<") && driver.getPageSource().contains(">"+ElementChkPropertyValue.toLowerCase()+"<") && driver.getPageSource().contains(">"+ElementChkPropertyValue+"<"))
							        			{
							        				status = "info";
							        				Teststatus = "PASS";
							        				ErrDesc = ">"+ElementChkPropertyValue.toUpperCase()+"<"+" Expected value found";
							        				stepinfo = ErrDesc;
							        				//el.log("info",ErrDesc);
							        			}
						        			}
						        			catch (NoSuchElementException e) 
						        			{
						        				if(!ElementAction.toUpperCase().equals("VERIFYIFEXIST"))
						        				{
						        					status = "error";
							        				Teststatus = "FAIL";	  
								        			ErrDesc = "Expected value not found";
								        			//errorcriticality = ElementInputValue.toUpperCase();
								        			ErrDesc = "Step "+stepNo+">"+ElementChkPropertyValue.toUpperCase()+"<" + " not found";
								        			//el.log("error","Error Executing Step "+stepNo+": Expected : "+ElementChkProperty+" = "+ElementChkPropertyValue+" Expected value not found");
								        			//el.log("error","Error Desc: "+e.getMessage());
								        			stepinfo = "Error Executing Step "+stepNo+": Expected : "+ElementChkProperty+" = "+ElementChkPropertyValue+" Expected value not found"+": error : " + e.getMessage();
							        				Appium_appselection.TxtAreaStep.setText(stepinfo);
						        				} 
						        				else 
						        				{
						        					Teststatus = "PASS";
						        					status = "warning";
						        					//el.log("warning","Element not found Step "+stepNo+": Frame pointer not changed to "+ElementPropValue+" Error IGNORED as its "+ElementAction.toUpperCase()+" action");
						        					stepinfo = "Step "+stepNo+": info : Element not found.Ignoring step as its IFEXIST action Element: "+ElementPropValue+ e.getMessage();
								        			Appium_appselection.TxtAreaStep.setText(stepinfo);
						        				}
							        		}
						        		}
						        		else if(ElementChkProperty.toUpperCase().equals("SPECIFICTAGTEXT")) 
						        		{
						        			try 
						        			{
						        				if (!driver.getPageSource().contains("<"+ElementInputValue+">"+ElementChkPropertyValue.toUpperCase()+"</"+ElementInputValue+">") && !driver.getPageSource().contains("<"+ElementInputValue+">"+ElementChkPropertyValue.toLowerCase()+"</"+ElementInputValue+">") && !driver.getPageSource().contains("<"+ElementInputValue+">"+ElementChkPropertyValue+"</"+ElementInputValue+">")) 
							        			{
						        					status = "error";
						        					Teststatus = "FAIL";	
						        					ErrDesc = "Expected : "+"<"+ElementInputValue+">"+ElementChkPropertyValue.toUpperCase()+"</"+ElementInputValue+">"+" is not present";
						        					ErrDesc = ErrDesc;
						        					stepinfo = ErrDesc;
						        					//el.log("error",ErrDesc);
							        			}
						        				else if (driver.getPageSource().contains("<"+ElementInputValue+">"+ElementChkPropertyValue.toUpperCase()+"</"+ElementInputValue+">") && driver.getPageSource().contains("<"+ElementInputValue+">"+ElementChkPropertyValue.toLowerCase()+"</"+ElementInputValue+">") && driver.getPageSource().contains("<"+ElementInputValue+">"+ElementChkPropertyValue+"</"+ElementInputValue+">"))
						        				{
						        					Teststatus = "PASS";
						        					status = "info";
							        				ErrDesc = "Step "+stepNo+": Expected : "+"<"+ElementInputValue+">"+ElementChkPropertyValue.toUpperCase()+"</"+ElementInputValue+">"+" value found";
							        				stepinfo = ErrDesc;
							        				//el.log("info",ErrDesc);
						        				}
						        			}
						        			catch(Exception e)
						        			{
						        				if(!ElementAction.toUpperCase().equals("VERIFYIFEXIST"))
						        				{
						        					status = "error";
						        					Teststatus = "FAIL";	  
						        					ErrDesc = "Expected : "+"<"+ElementInputValue+">"+ElementChkPropertyValue.toUpperCase()+"</"+ElementInputValue+">"+" is not present";
						        					ErrDesc = "Step "+stepNo+" "+ErrDesc;
						        					stepinfo = ErrDesc;
						        					//el.log("error",ErrDesc);
						        				}
						        				else
						        				{
						        					Teststatus = "PASS";
						        					status = "warning";
						        					//el.log("warning","Element not found Step "+stepNo+"<"+ElementInputValue+">"+ElementChkPropertyValue.toUpperCase()+"</"+ElementInputValue+">"+" Error IGNORED as its "+ElementAction.toUpperCase()+" action");
						        					stepinfo =": info : Element not found.Ignoring step as its IFEXIST action : " + "<"+ElementInputValue+">"+ElementChkPropertyValue.toUpperCase()+"</"+ElementInputValue+">"+" Error IGNORED as its "+ElementAction.toUpperCase()+" action"+e.getMessage();
						        					stepinfo = ErrDesc;
								        			Appium_appselection.TxtAreaStep.setText(stepinfo);
						        				}
						        			}
						        		}
						        		else if(ElementChkProperty.toUpperCase().equals("GETTEXT")) 
						        		{
						        			try
						        			{
							        			//if (!driver.findElement(By.xpath("//*[@"+ElementProperty+"='"+ElementPropValue+"']")).getText().trim().toUpperCase().equals(ElementChkPropertyValue.toUpperCase()))
						        				if (!driver.findElement(By.xpath(elemPropAttr)).getText().trim().toUpperCase().equals(ElementChkPropertyValue.toUpperCase()))
							        			{
							        				status = "error";
								        			Teststatus = "FAIL";	  
								        			//errorcriticality = ElementInputValue.toUpperCase();
								        			ErrDesc = "Expected : "+ElementChkPropertyValue+" & Actual : "+driver.findElement(By.xpath(elemPropAttr)).getText();
								        			ErrDesc = "Element: "+elemPropAttr+"Expected : "+ElementChkPropertyValue+" & Actual : "+driver.findElement(By.xpath(elemPropAttr)).getText();
								        			stepinfo = ErrDesc;
								        			//el.log("error","Element not found "+ErrDesc);
								        		}
							        			//else if(driver.findElement(By.xpath("//*[@"+ElementProperty+"='"+ElementPropValue+"']")).getText().trim().toUpperCase().equals(ElementChkPropertyValue.toUpperCase()))
						        				else if(driver.findElement(By.xpath(elemPropAttr)).getText().trim().toUpperCase().equals(ElementChkPropertyValue.toUpperCase()))
							        			{
							        				status = "info";
							        				Teststatus = "PASS";
							        				ErrDesc = "Element: "+elemPropAttr+"Expected : "+ElementChkPropertyValue+" & Actual : "+driver.findElement(By.xpath(elemPropAttr)).getText();
							        				
							        				stepinfo = ErrDesc;
							        				//el.log("info","Element found "+ErrDesc);
							        			}
						        			}
						        			catch(Exception e)
						        			{
						        				if(!ElementAction.toUpperCase().equals("VERIFYIFEXIST"))
						        				{
						        					status = "error";
						        					Teststatus = "FAIL";
						        					ErrDesc = "Expected : "+"//*[@"+ElementProperty+"='"+ElementPropValue+"']"+" ERROR: "+e.getMessage();
						        					
						        					stepinfo = ErrDesc;
						        					//el.log("error","Element not found "+ErrDesc);
						        				}
						        				else
						        				{
						        					status = "warning";
						        					Teststatus = "PASS";
						        					ErrDesc = "Expected : "+"//*[@"+ElementProperty+"='"+ElementPropValue+"']"+" ERROR: "+e.getMessage();
						        					ErrDesc= "Step "+stepNo+ErrDesc;
						        					stepinfo = "Step "+stepNo+": info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
						        					//el.log("warning","Element not found "+stepinfo);
						        				}
						        			}
						        		}
						        		else 
						        		{
						        			try
						        			{
							        			//if (!driver.findElement(By.xpath("//*[@"+ElementProperty+"='"+ElementPropValue+"']")).getAttribute(ElementChkProperty).toUpperCase().equals(ElementChkPropertyValue.toUpperCase()))
						        				if (!driver.findElement(By.xpath(elemPropAttr)).getAttribute(ElementChkProperty).toUpperCase().equals(ElementChkPropertyValue.toUpperCase()))
							        			{
							        				status = "error";
								        			Teststatus = "FAIL";	  
								        			//errorcriticality = ElementInputValue.toUpperCase();
								        			ErrDesc = "Expected : "+ElementChkPropertyValue+" & Actual : "+driver.findElement(By.xpath(elemPropAttr)).getAttribute(ElementChkProperty);
								        			ErrDesc = "Element: "+elemPropAttr+"Expected : "+ElementChkProperty+" = "+ElementChkPropertyValue+" & Actual : "+ElementChkProperty+" = "+driver.findElement(By.id(ElementPropValue)).getAttribute(ElementChkProperty);
								        			stepinfo = ErrDesc;
								        			//el.log("error","Element not found "+ErrDesc);
								        		}
							        			//else if (driver.findElement(By.xpath("//*[@"+ElementProperty+"='"+ElementPropValue+"']")).getAttribute(ElementChkProperty).toUpperCase().equals(ElementChkPropertyValue.toUpperCase()))
						        				else if (driver.findElement(By.xpath(elemPropAttr)).getAttribute(ElementChkProperty).toUpperCase().equals(ElementChkPropertyValue.toUpperCase()))
							        			{
							        				status = "info";
							        				Teststatus = "PASS";
							        				ErrDesc = "Element: "+elemPropAttr+"Expected : "+ElementChkPropertyValue+" & Actual : "+driver.findElement(By.xpath(elemPropAttr)).getAttribute(ElementChkProperty);
							        				
							        				stepinfo = ErrDesc;
							        				//el.log("info","Element found "+ErrDesc);
							        			}
						        			}
						        			catch(Exception e)
						        			{
						        				if(!ElementAction.toUpperCase().equals("VERIFYIFEXIST"))
						        				{
						        					status = "error";
						        					Teststatus = "FAIL";
						        					ErrDesc = "Expected : "+"//*[@"+ElementProperty+"='"+ElementPropValue+"']"+" Error: "+e.getMessage();
						        					
						        					stepinfo = ErrDesc;
						        					//el.log("error","Element not found "+ErrDesc);
						        				}
						        				else
						        				{
						        					status = "warning";
						        					Teststatus = "PASS";
						        					ErrDesc = "Expected : "+"//*[@"+ElementProperty+"='"+ElementPropValue+"']"+" error: "+e.getMessage();
						        					ErrDesc= "Step "+stepNo+ErrDesc;
						        					stepinfo = "info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
						        					//el.log("warning","Element not found "+stepinfo);
						        				}
						        			}
						        		}
						        		el.log("validation",ErrDesc);
						        		ErrDesc = stepinfo;
				        			break;
				        		case "CHKEXIST":
				        			status = "info";
				        			Teststatus = "PASS";
				        			ErrDesc = "Step "+stepNo+": Expected : "+elemPropAttr+" & Actual : Should Exist";
				        			stepinfo = "Executing Sheet '"+crntSheet+"' - "+ ErrDesc;
			        				Appium_appselection.TxtAreaStep.setText(stepinfo);
					        		//el.log("validation",ErrDesc);
					        		Exist = true;
				        			try {		        				
				        				driver.findElement(By.xpath(elemPropAttr));
				 	        		   Exist = true;
				 	        		} catch (NoSuchElementException e) {
				 	        		   Exist = false;
				 	        		}
				 	        		if (!Exist)
				 	        		{
				 	        			status = "error";
					        			Teststatus = "FAIL";	
					        			ErrDesc = "Expected : "+elemPropAttr+" should exist & Actual : Not Exist";
					        			stepinfo = ErrDesc;
					        			//el.log("error","Error Found : "+ErrDesc);
					        		}
				 	        		else
				 	        		{
				 	        			Teststatus = "PASS";
				 	        			status = "info";
				 	        			stepinfo = "Expected : "+elemPropAttr+" exist";
				 	        		}
				 	        		ErrDesc = stepinfo;
				        			break;
				        		case "ACCEPT": case "ACCEPTIFEXIST":
				        			status = "info";
				        			Teststatus = "PASS";
				        			stepinfo = "Executing Sheet '"+crntSheet+"' - Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue;
				        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        			try {	
					        			Thread.sleep(1000);
					        			Alert al = driver.switchTo().alert();		        			
					        			al.accept(); 
					        			stepinfo = ElementName+" Accepted";
					        			//el.log("info","Step "+stepNo+":"+ElementName+" Accepted");
				        			}catch (Exception e) {
				        				if(!ElementAction.toUpperCase().equals("ACCEPTIFEXIST")){
				        					status = "error";
				        					Teststatus = "FAIL";
					        				//el.log("error","Error Executing Step "+stepNo+":"+ElementName+" not accept");
					        				//el.log("error","Error Desc: "+e.getMessage());
					        				stepinfo = "Error Executing Step "+stepNo+":"+ElementName+" not accept"+"error : " + e.getMessage();
					        				Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				} else {
				        					Teststatus = "PASS";
				        					status = "warning";
				        					//el.log("warning","Element not found Step "+stepNo+":"+ElementName+" not accept. Error IGNORED as its "+ElementAction.toUpperCase()+" action");					        				
				        					stepinfo = ": info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
						        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        				}
				        			 }
				        			ErrDesc = stepinfo;
				        			break;
				        		case "STOREVARIABLE": case "STOREVARIABLEIFEXIST":
				        			try {
				        					status = "info";
				        					Teststatus = "PASS";
					        				Appium_appselection.TxtAreaStep.setText("Executing Sheet '"+Appium_driverscript.crntSheet+"' - Waiting for - Step "+Appium_driverscript.stepNo+": "+ElementName+" ("+elemPropAttr+")");
						        			fl.waitforelement(elemPropAttr);	
						        			stepinfo = "Executing Sheet '"+crntSheet+"'  "+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue;
						        			Appium_appselection.TxtAreaStep.setText(stepinfo);
						        			String tagname;
											if (ElementInputValue.toUpperCase().equals("TAGTEXT")) 
											{
						        				tagname = driver.findElement(By.xpath(elemPropAttr)).getTagName().toUpperCase();					        				
						        				String[] sarray;
						        				sarray = driver.getPageSource().split(ElementProperty+"="+ElementPropValue+"");
						        				int sarraycounter = 1;
						        				try{
						        				sarraycounter = Integer.parseInt(ElementChkProperty);
						        				}catch(Exception e){
						        					System.out.println(e.getMessage());
						        					}
						        				if(sarraycounter>1)
						        					capValue = sarray[sarraycounter].substring(sarray[sarraycounter].indexOf(">")+1,sarray[sarraycounter].indexOf("</"+tagname+">"));
						        				else
						        					capValue = sarray[1].substring(sarray[1].indexOf(">")+1,sarray[1].indexOf("</"+tagname+">"));
						        				
											}
											else 
											{
							        			//capValue = driver.findElement(By.xpath(elemPropAttr)).getAttribute(ElementInputValue);
												capValue = driver.findElement(By.xpath(elemPropAttr)).getText();
											}
						        			/*if(!StoreVariableName.equals(""))
						        			{		
						        				StoreVariable[StoreVariableCount] = StoreVariableName.toUpperCase();
						        				StoreVariableValue[StoreVariableCount]=capValue;
						        				StoreVariableCount = StoreVariableCount+1;
						        			}		*/	
											capValue = capValue.replace(" PRIORITY", "");
											stepinfo = "TAGTEXT - " + ElementProperty+" = "+ElementPropValue + "Variable - "+ElementChkPropertyValue+" = "+capValue;
											store.put(ElementChkPropertyValue, capValue);
						            	 	//el.log("info","Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") Captured '"+ElementInputValue+"' property is "+capValue);
					        		}
				        			catch (Exception e) 
				        			{
					        			if(!ElementAction.toUpperCase().equals("STOREVARIABLEIFEXIST"))
					        			{
					        				Teststatus = "FAIL";
					        				status = "error";
						        			//el.log("info","Error Executing Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") not Captured '"+ElementInputValue+"'");
						        			//el.log("error","Error Desc: "+e.getMessage());
						        			stepinfo = "Error Executing Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") not Captured '"+ElementInputValue+"'" + ": error : " + e.getMessage();
					        				Appium_appselection.TxtAreaStep.setText(stepinfo);
					        			} 
					        			else 
					        			{
					        				Teststatus = "PASS";
					        				status = "warning";
					        				//el.log("info","Error Executing Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+ElementPropValue+") capture '"+ElementInputValue+"'. Error IGNORED as its "+ElementAction.toUpperCase()+" action");					        				 				        				
				        					stepinfo = "Step "+stepNo+": info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
						        			Appium_appselection.TxtAreaStep.setText(stepinfo);
					        			}
				        			 }
				            	 	Thread.sleep(500);
				            	 	ErrDesc = stepinfo;
				        		break;
				        		case "VERIFYSTOREVARIABLE": case "VERIFYSTOREVARIABLEIFEXIST":
				        			try{
				        			Teststatus = "PASS";
				        			status = "info";
				        			String store_var = store.get(ElementChkPropertyValue) + ElementInputValue;
				        			if(driver.getPageSource().contains(store_var))
				        			{
				        				//el.log("info","Step "+stepNo+" " +store_var+"  is present in the screen"+capValue);
				        				stepinfo = "variable -  "+ElementChkPropertyValue+" = " +store_var+"  is present in the screen"+capValue;
				        			}
				        			else
				        			{
				        				Teststatus = "FAIL";
				        				status = "error";
				        				//el.log("error","Step "+stepNo+" " +store_var+"  is not present in the screen"+capValue);
				        				stepinfo = "Step "+stepNo+" " +store_var+"  is not present in the screen"+capValue;
				        			}
				        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        			capValue = "";}catch(Exception e){
					        				if(ElementAction.toUpperCase().contentEquals("VERIFYSTOREVARIABLEIFEXIST"))
					        				{
					        					//el.log("info", e.getMessage());
					        					Teststatus = "FAIL";
					        					status = "error";
					        					stepinfo = "variable -  "+ElementChkPropertyValue+" = " +store.get(ElementChkPropertyValue) + ElementInputValue+"  is not present in the screen"+capValue;
					        				}
					        				else
					        				{
					        					//el.log("error", e.getMessage());
					        					Teststatus = "PASS";
					        					status = "warning";
					        					stepinfo = "variable -  "+ElementChkPropertyValue+" = " +store.get(ElementChkPropertyValue) + ElementInputValue+"  is not present in the screen"+capValue;
					        				}
				        				}
				        			ErrDesc = stepinfo;
				        		break;
				        		case "SCRNCAPTURE": case "SCREENCAPTURE":
				        			status = "info";
				        			Teststatus = "PASS";
				        			stepinfo = "Executing Sheet '"+crntSheet+"'  "+": "+ElementName+"("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue;
				        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        			Appium_fnlibrary.screencapture();
				        			ErrDesc = stepinfo;
				        			break;
				        		case "ERRORCHECK":
				        			status = "info";
				        			Teststatus = "PASS";
				        			stepinfo = "Executing Sheet '"+crntSheet+"' - "+": Checking for errors";
				        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        			ec.errorcheck();
				        			ErrDesc = stepinfo;
				        			break;
				        		case "WAIT":
				        			int waitfor;
				        			status = "info";
				        			Teststatus = "PASS";
						        	waitfor = Integer.parseInt(ElementInputValue)*1000;
						        	stepinfo = "Executing Sheet '"+crntSheet+"' -  "+ElementInputValue+" Execution wait for "+ElementInputValue+" seconds";
			        				Appium_appselection.TxtAreaStep.setText(stepinfo);
						        	//el.log("info","Step "+stepNo+": Execution wait for "+ElementInputValue+" seconds");
						        	Thread.sleep(waitfor);
						        	ErrDesc = stepinfo;
						        	break;
				        		case "SCROLL":
				        			Teststatus = "PASS";
				        			status = "info";
				        			stepinfo = "Executing Sheet '"+crntSheet+"' -  "+": "+ElementName+"("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue;
				        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        			 JavascriptExecutor executor = (JavascriptExecutor)driver;
				        		    executor.executeScript("window.focus()");
				        		    Thread.sleep(2000);
				        			Robot mouse = new Robot(); 
				        			switch (ElementInputValue.toUpperCase()){
					        			case "UP":
					        				mouse.mouseWheel(-10);
//					        				for (keypress = 1;keypress<=4;keypress++) {
//						        				 robot.keyPress(KeyEvent.VK_UP);
//						        				 Thread.sleep(100);
//						        			 }
					        				break;
					        			case "LEFT":					        				
					        				for (keypress = 1;keypress<=4;keypress++) {
						        				 robot.keyPress(KeyEvent.VK_LEFT);
						        				 Thread.sleep(100);
						        			 }
					        				break;
					        			case "RIGHT":
					        				for (keypress = 1;keypress<=4;keypress++) {
						        				 robot.keyPress(KeyEvent.VK_RIGHT);
						        				 Thread.sleep(100);
						        			 }
					        				break;
					        			case "DOWN":
					        				mouse.mouseWheel(10);
//					        				for (keypress = 1;keypress<=4;keypress++) {
//						        				 robot.keyPress(KeyEvent.VK_DOWN);
//						        				 Thread.sleep(100);
//						        			 }
					        				break;				        				
				        			}
				        			ErrDesc = stepinfo;
				        		break;
				        		case "TYPE":
				        			status = "info";
				        			Teststatus = "PASS";
				        			stepinfo = "Executing Sheet '"+crntSheet+"' -  "+": "+ElementName+"("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue;
				        			Appium_appselection.TxtAreaStep.setText(stepinfo);
			        				String inpvalue = ElementInputValue.toUpperCase();			        				
			        				Appium_fnlibrary.wordtype(inpvalue);	
			        				ErrDesc = stepinfo;
				        			break;
				        		case "PRESS":
				        			status = "info";
				        			Teststatus = "PASS";
				        			stepinfo = "Executing Sheet '"+crntSheet+"' -  "+": "+ElementName+"("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue;
				        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        			String presskey = ElementInputValue.toUpperCase();			        				
			        				Appium_fnlibrary.keyboard(presskey);
			        				ErrDesc = stepinfo;
				        		break;
				        		case "MOUSE":
				        			status = "info";
				        			Teststatus = "PASS";
				        			stepinfo = "Executing Sheet '"+crntSheet+"' -  "+": "+ElementName+"("+ElementProperty+"="+ElementPropValue+") "+ElementAction+" "+ElementInputValue;
				        			Appium_appselection.TxtAreaStep.setText(stepinfo);
				        			String mousekey = ElementInputValue.toUpperCase();			        				
			        				Appium_fnlibrary.mouseclick(mousekey);
			        				ErrDesc = stepinfo;
				        		break;
				        		case "SWITCHNEWWINDOW":
				        			ParentWindow = driver.getWindowHandle();
				        			//ParentWindow
				        			// Switch to new window opened
				        			for(String winHandle : driver.getWindowHandles())
				        			{
				        				if(!ParentWindow.contentEquals(winHandle))
				        				{
				        					childWindow = winHandle;
				        					driver.switchTo().window(childWindow);
				        				}
				        			}
				        		break;
				        		case "SWITCHOLDWINDOW":
				        			driver.switchTo().window(ParentWindow);
				        		break;
			        		}//Switch1 ends
		        		
			        if (needReport.toUpperCase().equals("Y")) {
			        	//stepinfo = "Executing Sheet '"+crntSheet+"' - Step "+stepNo+": Starting Report Generator";
	        			//appselection.TxtAreaStep.setText(stepinfo);
			        	el.log(status,"Step "+stepNo+stepinfo);
			        	rg.report();
			        	//el.log("info","Report Generated");
			        } 
			        if(Teststatus.toUpperCase().contentEquals("FAIL") && errortype.toUpperCase().contentEquals("CRITICAL") && actionifnotfound.toUpperCase().contentEquals("TERMINATE"))
			        {
			        	el.log("error","Step "+stepNo+" Execution terminated");
			        	Appium_appselection.TxtAreaStep.setText("Step "+stepNo+" Execution terminated");
			        	crntStep = "Step "+stepNo+" Execution terminated";
			        	errorcriticality =errortype;
			        	Teststatus = "FAIL";
			        	ErrDesc = " Execution termeniated due to failed test step";
			        	rg.report();
			        	wb.close();
			  	      Thread.sleep(1000);
			  	      fis.close();	
			  	      stepinfo = "Closing "+Browsername+" Driver";
			  		  Appium_appselection.TxtAreaStep.setText(stepinfo);	
			  		  Thread.sleep(1000);
			  		 if (Browsername.toUpperCase().equals("CHROME") || Browsername.toUpperCase().equals("BROWSER")){
			  			 driver.close();
				  	     driver.quit();
			  		 }
			  	      stepinfo = "Composing report mail...";
			  		  Appium_appselection.TxtAreaStep.setText(stepinfo);	
			  		  Runtime.getRuntime().exec("WScript C:/Appium/library/mail.vbs "+"\""+Appium_appselection.selectedApp+"\"");
			         stepinfo = "Report mail has been sent...";
			        }
			        else if(Teststatus.toUpperCase().contentEquals("FAIL")  && actionifnotfound.toUpperCase().contentEquals("NEXTSHEET"))
			        {
			        	el.log("info","======================== Functionality : '"+crntSheet+"' TERMINATED ========================");
			        	stepinfo = "Functionality : '"+crntSheet+"' Terminated";
			        	Appium_appselection.TxtAreaStep.setText(stepinfo);
			        	crntStep = "Step "+stepNo+" Execution terminated";
			        	errorcriticality =errortype;
			        	if(errortype.toUpperCase().contentEquals("CRITICAL"))
			        		Teststatus = "FAIL";
			        	else
			        		Teststatus = "PASS";
			        	ErrDesc = stepinfo;
			        	rg.report();
			        	break;
			        }
		        } //for4 ends
		        
		        el.log("info","======================== Functionality : '"+crntSheet+"' Completed ========================");
		        stepinfo = "Functionality : '"+crntSheet+"' Completed";
				Appium_appselection.TxtAreaStep.setText(stepinfo);
		        ErrDesc = crntSheet+" Functionality";
		        freport = true;
		        //rg.report();
		        Thread.sleep(1000);     
		        
			} //for3 ends
				stepinfo = "Execution Completed";
				Appium_appselection.TxtAreaStep.setText(stepinfo);
				Thread.sleep(1000);    
				stepinfo = "Closing input sheet";
				Appium_appselection.TxtAreaStep.setText(stepinfo);		
			
		      wb.close();
		      Thread.sleep(1000);
		      
		      fis.close();	
		      stepinfo = "Closing "+Browsername+" Driver";
			  Appium_appselection.TxtAreaStep.setText(stepinfo);	
			  Thread.sleep(1000);
			  if (Browsername.toUpperCase().equals("CHROME") || Browsername.toUpperCase().equals("BROWSER")){	
				  driver.close();
			      driver.quit();  
			  }
		      stepinfo = "Composing report mail...";
			  Appium_appselection.TxtAreaStep.setText(stepinfo);	
		      try {
		         Runtime.getRuntime().exec("WScript C:/Appium/library/mail.vbs "+"\""+Appium_appselection.selectedApp+"\"");
		         stepinfo = "Report mail has been sent...";
				 Appium_appselection.TxtAreaStep.setText(stepinfo);
		      }
		      catch( IOException e ) {
		    	 stepinfo = "Error sending Report mail...";
				 Appium_appselection.TxtAreaStep.setText(stepinfo);
				 el.log("error",e.getMessage());	
		         e.printStackTrace();
		      }

		      stepinfo = "Exiting...";
			  Appium_appselection.TxtAreaStep.setText(stepinfo);	
			  Thread.sleep(1000);
		      System.exit(0);
		} catch(IOException e) {	
			el.log("error",e.getMessage());	
			stepinfo = e.getMessage();
			Appium_appselection.TxtAreaStep.setText(stepinfo);
			el.log("error",stepinfo);
			Appium_fnlibrary.screencapture();
			Appium_driverscript.Teststatus = "FAIL";
			Appium_driverscript.ErrDesc = e.getMessage();
			Appium_driverscript.errorcriticality = "CRITICAL";
			rg.report();
			
			stepinfo = "Execution Interupted";
			Appium_appselection.TxtAreaStep.setText(stepinfo);
			Thread.sleep(1000);    
			stepinfo = "Closing input sheet";
			Appium_appselection.TxtAreaStep.setText(stepinfo);				  
	      wb.close();
	      Thread.sleep(1000);
	      fis.close();	
	      stepinfo = "Closing "+Browsername+" Driver";
		  Appium_appselection.TxtAreaStep.setText(stepinfo);	
		  Thread.sleep(1000);
		  if (Browsername.toUpperCase().equals("CHROME") || Browsername.toUpperCase().equals("BROWSER")){	
	      driver.close();
	      driver.quit();
		  }
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
			
//			driverscript.wb.close();
//		    driverscript.fis.close();		     
//		    driverscript.driver.close();
//		    driverscript.driver.quit();
//		    System.exit(0);
			
		} catch(Exception e1){
			el.log("error",e1.getMessage());
			stepinfo = e1.getMessage();
			Appium_appselection.TxtAreaStep.setText(stepinfo);
			el.log("error",stepinfo);
			Appium_fnlibrary.screencapture();
			Appium_driverscript.Teststatus = "FAIL";
			Appium_driverscript.ErrDesc = e1.getMessage();
			Appium_driverscript.errorcriticality = "CRITICAL";
			rg.report();
			stepinfo = "Execution Interupted";
			Appium_appselection.TxtAreaStep.setText(stepinfo);
			Thread.sleep(1000);    
			stepinfo = "Closing input sheet";
			Appium_appselection.TxtAreaStep.setText(stepinfo);		
			
	      wb.close();
	      Thread.sleep(1000);	      
	      fis.close();	
	      stepinfo = "Closing "+Browsername+" Driver";
		  Appium_appselection.TxtAreaStep.setText(stepinfo);	
		  Thread.sleep(1000);
		  if (Browsername.toUpperCase().equals("CHROME") || Browsername.toUpperCase().equals("BROWSER")){	
	      driver.close();
	      driver.quit();
		  }
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
		
		} //try1 catch ends
	
	} //main ends
	
} //class ends
