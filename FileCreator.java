import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;


public class FileCreator {
	String datasheetPath = "src/AppName-CXM_HL_Grid.xls";
	static String Teststatus = null;

	public static void main(String args[]) throws BiffException, IOException{
		FileCreator f = new FileCreator();
		f.testCreateSrc();
	}

	//@Test
	public void testCreateSrc() throws IOException, BiffException 
	{
		File sourceFile   = new File("src/AutomaticAutomation.java");
		if(!sourceFile.exists())
		{
			sourceFile.createNewFile();
		}
		if(!sourceFile.exists())
		{
			sourceFile.createNewFile();
		}
		FileWriter writer = new FileWriter(sourceFile);


		String code = "import java.awt.AWTException;\n"+
				"import java.awt.Dimension;\n"+
				"import java.awt.Graphics;\n"+
				"import java.awt.Rectangle;\n"+
				"import java.awt.Robot;\n"+
				"import java.awt.Toolkit;\n"+
				"import java.awt.event.InputEvent;\n"+
				"import java.awt.event.KeyEvent;\n"+
				"import java.awt.image.BufferedImage;\n"+
				"import java.io.BufferedReader;\n"+
				"import java.io.BufferedWriter;\n"+
				"import java.io.File;\n"+
				"import java.io.FileInputStream;\n"+
				"import java.io.FileWriter;\n"+
				"import java.io.IOException;\n"+
				"import java.io.InputStream;\n"+
				"import java.io.InputStreamReader;\n"+
				"import java.io.OutputStream;\n"+
				"import java.net.HttpURLConnection;\n"+
				"import java.net.URL;\n"+
				"import java.text.DateFormat;\n"+
				"import java.text.SimpleDateFormat;\n"+
				"import java.util.Calendar;\n"+
				"import java.util.Hashtable;\n"+
				"import java.util.concurrent.TimeUnit;\n"+

	    "import javax.imageio.ImageIO;\n"+

	    "import jxl.Sheet;\n"+
	    "import jxl.Workbook;\n"+
	    "import jxl.format.Border;\n"+
	    "import jxl.format.BorderLineStyle;\n"+
	    "import jxl.format.Colour;\n"+
	    "import jxl.read.biff.BiffException;\n"+
	    "import jxl.write.Label;\n"+
	    "import jxl.write.WritableCellFormat;\n"+
	    "import jxl.write.WritableFont;\n"+
	    "import jxl.write.WritableSheet;\n"+
	    "import jxl.write.WritableWorkbook;\n"+
	    "import jxl.write.WriteException;\n"+
	    "import jxl.write.biff.RowsExceededException;\n"+

	    "import org.openqa.selenium.Alert;\n"+
	    "import org.openqa.selenium.By;\n"+
	    "import org.openqa.selenium.JavascriptExecutor;\n"+
	    "import org.openqa.selenium.NoSuchElementException;\n"+
	    "import org.openqa.selenium.Point;\n"+
	    "import org.openqa.selenium.UnexpectedAlertBehaviour;\n"+
	    "import org.openqa.selenium.WebDriver;\n"+
	    "import org.openqa.selenium.WebElement;\n"+
	    "import org.openqa.selenium.ie.InternetExplorerDriver;\n"+
	    "import org.openqa.selenium.interactions.Actions;\n"+
	    "import org.openqa.selenium.remote.CapabilityType;\n"+
	    "import org.openqa.selenium.remote.DesiredCapabilities;\n"+
	    "import org.openqa.selenium.remote.RemoteWebDriver;\n"+
	    "import org.openqa.selenium.support.ui.ExpectedConditions;\n"+
	    "import org.openqa.selenium.support.ui.Select;\n"+
	    "import org.openqa.selenium.support.ui.WebDriverWait;\n"+
	    "import org.testng.annotations.BeforeTest;\n"+
	    "import org.testng.annotations.Parameters;\n"+
	    "import org.testng.annotations.Test;\n"+
	    "public class AutomaticAutomation\n"+
	    "{\n"+
	    "DesiredCapabilities caps;\n"+
	    "public WebDriver driver;\n"+
	    "static String Teststatus = null;\n"+
	    "static String status = \"\", stepinfo = \"\", ErrDesc = \"\";\n"+
	    "static Graphics g;\n"+
	    "static boolean Exist;\n"+
	    "public static String capValue = \"\";\n"+
	    "public static Hashtable store = new Hashtable();\n"+
	    "public static String childWindow = \"\", ParentWindow = \"\";\n"+
	    "static Calendar cal;\n"+
	    "static String reprtTime;\n"+
	    "public enum logType\n"+
	    "{\n"+
	    "warning, info, config, error, validation;\n"+
	    "}\n"+
	    "static String color;\n"+
	    "static DateFormat rptTme;\n"+
	    "public static String errorcriticality = \"NON-CRITICAL\";\n"+
	    "JavascriptExecutor js, executor;\n"+
	    "WebElement ele, source, target, element;\n"+
	    "String mouseOverScript=\"\", tagname=\"\", store_var = \"\", inpvalue=\"\", errorchk_Filepath = \"\", presskey = \"\", xmlInput =\"\", line=\"\", res=\"\", Response=\"\", Response2 =\"\", ParentWindow=\"\", childWindow=\"\", soap_res=\"\";\n"+
	    "Actions actions,doubleClick;\n"+
	    "Select combobox;\n"+
	    "Alert al;\n"+
	    "String[] sarray;\n"+
	    "int sarraycounter = 1, wait;\n"+
	    "Robot robot = new Robot();\n"+
	    "URL URL;\n"+
	    "HttpURLConnection connection;\n"+
	    "OutputStream out;\n"+
	    "InputStream is;\n"+
	    "BufferedReader in;\n"+

		"@Parameters({\"hubUrl\",\"datasheetPath\"})\n"+
		"@BeforeTest\n"+
		"public void setUp(String hubUrl, String datasheetPath) throws Exception\n"+
		"{\n"+
		"try{\n"+
		"File  fis = new File(datasheetPath);\n"+
		"Workbook wb = Workbook.getWorkbook(fis);\n"+
		"Sheet s = wb.getSheet(\"AppConfig\");\n"+
		"Hashtable AppConfig = new Hashtable();\n"+

		    "AppConfig.put(s.getCell(0,0).getContents(), s.getCell(0,1).getContents());\n"+
		    "System.out.println(\"App_URL \"+AppConfig.get(\"App_URL\"));\n"+
		    "AppConfig.put(s.getCell(3,0).getContents(), s.getCell(3,1).getContents());\n"+
		    "System.out.println(\"BrowserName \"+AppConfig.get(\"BrowserName\"));\n"+

	        "if(AppConfig.get(\"App_URL\").toString().contains(\"http\"))\n"+
	        "{\n"+
	        "if(AppConfig.get(\"BrowserName\").toString().toUpperCase().contentEquals(\"FIREFOX\"))\n"+
	        "{\n"+
	        "System.out.println(hubUrl);\n"+
	        "System.out.println(\"setUp\");\n"+
	        "ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<RemoteWebDriver>();\n"+
	        "System.out.println(\"0\");\n"+
	        "DesiredCapabilities caps = DesiredCapabilities.firefox(); \n"+
	        "System.out.println(\"1\");\n"+
	        "caps.setBrowserName(DesiredCapabilities.firefox().getBrowserName());\n"+
	        "System.out.println(\"2\");\n"+
	        "caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);\n"+
	        "System.out.println(\"3\");\n"+
	        "threadDriver.set(new RemoteWebDriver(new URL(hubUrl), caps));\n"+
	        "System.out.println(\"4\");\n"+
	        "driver = threadDriver.get();\n"+
	        "System.out.println(\"5\");\n"+
	        "driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);\n"+
	        "System.out.println(\"6\");\n"+
	        "driver.manage().deleteAllCookies();\n"+
	        "System.out.println(\"7\");\n"+
	        "driver.manage().window().maximize();\n"+
	        "System.out.println(\"beforetest\");\n"+
	        "}\n"+
	        "else if(AppConfig.get(\"BrowserName\").toString().toUpperCase().contentEquals(\"INTERNETEXPLORER\"))\n"+
	        "{\n"+
	        "System.out.println(hubUrl);\n"+
	        "System.out.println(\"setUp\");\n"+
	        "ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<RemoteWebDriver>();\n"+
	        "System.out.println(\"0\");\n"+
	        "DesiredCapabilities caps = DesiredCapabilities.internetExplorer();\n"+ 
	        "System.out.println(\"1\");\n"+
	        "caps.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());\n"+
	        "System.out.println(\"2\");\n"+
	        "caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);\n"+
	        "System.out.println(\"3\");\n"+
	        "caps.setCapability(\"ignoreZoomSetting\", true);\n"+
	        "System.out.println(\"4\");\n"+
	        "caps.setCapability(\"EnableNativeEvents\", true);\n"+
	        "System.out.println(\"5\");\n"+
	        "caps.setCapability(\"enablePersistentHover\", true);\n"+
	        "System.out.println(\"6\");\n"+
	        "threadDriver.set(new RemoteWebDriver(new URL(hubUrl), caps));\n"+
	        "System.out.println(\"7\");\n"+
	        "driver = threadDriver.get();\n"+
	        "System.out.println(\"8\");\n"+
	        "driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);\n"+
	        "System.out.println(\"9\");\n"+
	        "driver.manage().deleteAllCookies();\n"+
	        "System.out.println(\"10\");\n"+
	        "driver.manage().window().maximize();\n"+
	        "System.out.println(\"beforetest\");\n"+
	        "}\n"+
	        "System.out.println(\"@BeforeTest Ends\");\n"+
	        "}\n"+
	        "}catch(Exception e){\n"+
	        "System.out.println(e.getMessage());\n"+
	        "}\n"+
	        "}\n";

		System.out.println("datasheetPath ");
		File  fis = new File(datasheetPath);
		Workbook wb = Workbook.getWorkbook(fis);
		Sheet s = wb.getSheet("AppConfig");
		Hashtable AppConfig = new Hashtable();

		AppConfig.put(s.getCell(0,0).getContents(), s.getCell(0,1).getContents());
		System.out.println(AppConfig.get(s.getCell(0,0).getContents()));

		AppConfig.put(s.getCell(1,0).getContents(), s.getCell(1,1).getContents());
		System.out.println(AppConfig.get(s.getCell(1,0).getContents()));

		AppConfig.put(s.getCell(2,0).getContents(), s.getCell(2,1).getContents());
		System.out.println(AppConfig.get(s.getCell(2,0).getContents()));

		AppConfig.put(s.getCell(3,0).getContents(), s.getCell(3,1).getContents());
		System.out.println(AppConfig.get(s.getCell(3,0).getContents()));

		int sheet_counter = 0;
		if(AppConfig.get("App_URL").toString().contains("http"))
		{
			code = code + "@Parameters({\"datasheetPath\",\"selectedApp\",\"baseUrl\",\"hubUrl\"})\n"+
					"@Test\n"+
					"public void testLogin(String Sheet_Name, String datasheetPath, String selectedApp, String baseUrl, String mail_app, String hubUrl) throws BiffException, IOException, RowsExceededException, WriteException {\n"+
					"try{\n"+
					"if(baseUrl.contains(\"http\"))\n"+
					"driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);\n";

			code = code + "if(baseUrl.contains(\"http\")){\n"+
					"driver.get(baseUrl);\n"+
					"System.out.println(baseUrl+\" Launched\");\n"+
					"}\n";

			System.out.println("Login sheet is being executed");
			Sheet sheet_Login= wb.getSheet("Login");
			Teststatus = "PASS";

			for(int i=1;i<sheet_Login.getRows();i++) 
			{
				try{
					code = code + StepsProvider.generateSteps("Login", sheet_Login.getCell(0,i).getContents(),sheet_Login.getCell(1,i).getContents(),sheet_Login.getCell(2,i).getContents(),sheet_Login.getCell(3,i).getContents(), sheet_Login.getCell(4,i).getContents(), sheet_Login.getCell(5,i).getContents(), sheet_Login.getCell(6,i).getContents(), sheet_Login.getCell(7,i).getContents(), sheet_Login.getCell(8,i).getContents(), sheet_Login.getCell(9,i).getContents(), sheet_Login.getCell(10,i).getContents(),sheet_Login.getCell(11,i).getContents());

				}catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
			}
			code = code +"}\n";
			sheet_counter = 2;
		}
		else
		{
			sheet_counter = 1;
		}
		String Sheet_Name = "" ;
		for(int j=sheet_counter; j<wb.getNumberOfSheets(); j++){
			Sheet_Name = wb.getSheet(j).getName();
			System.out.println(Sheet_Name+" is being executed");
			Sheet s3= wb.getSheet(Sheet_Name);
			code = code + "@Parameters({\"datasheetPath\",\"selectedApp\",\"baseUrl\",\"hubUrl\"})\n"+
					"@Test(dependsOnMethods = {\"testLogin\"})\n"+
					"public void test"+Sheet_Name+"(String datasheetPath, String selectedApp, String baseUrl, String hubUrl) throws BiffException, IOException, RowsExceededException, WriteException {\n"+
					"try{\n"+
					"if(baseUrl.contains(\"http\"))\n"+
					"driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);\n";
			String TC_ID = null;
			for(int i=1;i<s3.getRows();i++) 
			{
				try{
					if(TC_ID==null)
						TC_ID = s3.getCell(1, i).getContents().toString();
					else if((TC_ID.contentEquals(s3.getCell(1, i).getContents().toString())))
					{
						code = code + StepsProvider.generateSteps(Sheet_Name, s3.getCell(0,i).getContents(),s3.getCell(1,i).getContents(),s3.getCell(2,i).getContents(),s3.getCell(3,i).getContents(), s3.getCell(4,i).getContents(), s3.getCell(5,i).getContents(), s3.getCell(6,i).getContents(), s3.getCell(7,i).getContents(), s3.getCell(8,i).getContents(), s3.getCell(9,i).getContents(), s3.getCell(10,i).getContents(),s3.getCell(11,i).getContents());
						code = code + "if(baseUrl.contains(\"http\")){\n"+
								"driver.close();\n"+
								"driver.quit();\n"+
								"}\n";
						code = code +"}\n";

					}
				}catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
				code = code + StepsProvider.generateSteps(Sheet_Name, s3.getCell(0,i).getContents(),s3.getCell(1,i).getContents(),s3.getCell(2,i).getContents(),s3.getCell(3,i).getContents(), s3.getCell(4,i).getContents(), s3.getCell(5,i).getContents(), s3.getCell(6,i).getContents(), s3.getCell(7,i).getContents(), s3.getCell(8,i).getContents(), s3.getCell(9,i).getContents(), s3.getCell(10,i).getContents(),s3.getCell(11,i).getContents());
			}
		}

		code = code + "public static void switchNewWindow(String code){\n";
		code = code + "ParentWindow = driver.getWindowHandle();\n";
		code = code + "for(String winHandle : driver.getWindowHandles())\n";
		code = code + "{\n";
		code = code + "if(!ParentWindow.contentEquals(winHandle))\n";
		code = code + "{\n";
		code = code + "childWindow = winHandle;\n";
		code = code + "driver.switchTo().window(childWindow);\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void scroll(String code, String Element_Input_Value){\n";
		code = code + "executor = (JavascriptExecutor)driver;\n";
		code = code + "executor.executeScript(\"window.focus()\");\n";
		code = code + "Thread.sleep(2000);\n";
		code = code + "switch (Element_Input_Value.toUpperCase())\n";
		code = code + "{\n";
		code = code + "case \"UP\":\n";
		code = code + "robot.mouseWheel(-10);\n";
		code = code + "break;\n";
		code = code + "case \"LEFT\":\n";
		code = code + "for (int keypress = 1;keypress<=4;keypress++) {\n";
		code = code + "robot.keyPress(KeyEvent.VK_LEFT);\n";
		code = code + "Thread.sleep(100);\n";
		code = code + "}\n";
		code = code + "break;\n";
		code = code + "case \"RIGHT\":\n";
		code = code + "for (int keypress = 1;keypress<=4;keypress++) {\n";
		code = code + "robot.keyPress(KeyEvent.VK_RIGHT);\n";
		code = code + "Thread.sleep(100);\n";
		code = code + "}\n";
		code = code + "break;\n";
		code = code + "case \"DOWN\":\n";
		code = code + "robot.mouseWheel(10);\n";
		code = code + "break;\n";		
		code = code + "}\n";		  	
		code = code + "}\n";

		code = code + "public static void verifyStoreVariable(String code, String ChkElement_Property_Value, String Element_Input_Value, String Action){\n";
		code = code + "try{\n";
		code = code + "store_var = store.get(\"ChkElement_Property_Value) + Element_Input_Value\";\n";
		code = code + "if(driver.getPageSource().contains(store_var))\n";
		code = code + "{\n";
		code = code + "}\n";
		code = code + "else\n";
		code = code + "{\n";
		code = code + "}\n";
		code = code + "capValue = \"\";}catch(Exception e){\n";
		code = code + "if(Action.toUpperCase().contentEquals(\"VERIFYSTOREVARIABLEIFEXIST\"))\n";
		code = code + "{\n";
		code = code + "}\n";
		code = code + "else\n";
		code = code + "{\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void storeVariable(String code, String xPath, String Action, String Element_Property, String Element_Property_Value, String Element_Input_Value, String ChkElement_Property, String ChkElement_Property_Value){\n";
		code = code + "try {\n";
		code = code + "waitUntilElementPresent(xPath, Action);\n";
		code = code + "if (Element_Input_Value.toUpperCase().equals(\"TAGTEXT\"))\n"; 
		code = code + "{\n";
		code = code + "tagname = driver.findElement(By.xpath(xPath)).getTagName().toUpperCase();\n";					        				
		code = code + "sarray = driver.getPageSource().split(Element_Property+\"=\"+Element_Property_Value);\n";
		code = code + "try{\n";
		code = code + "sarraycounter = Integer.parseInt(\"ChkElement_Property\");\n";
		code = code + "}catch(Exception e){\n";
		code = code + "System.out.println(e.getMessage());\n";
		code = code + "}\n";
		code = code + "if(sarraycounter>1)\n";
		code = code + "capValue = sarray[sarraycounter].substring(sarray[sarraycounter].indexOf(\">\")+1,sarray[sarraycounter].indexOf(\"</+tagname+>\"));\n";
		code = code + "else\n";
		code = code + "capValue = sarray[1].substring(sarray[1].indexOf(\">\")+1,sarray[1].indexOf(\"</+tagname+>\"));\n";
		code = code + "}\n";
		code = code + "else\n"; 
		code = code + "{\n";
		code = code + "capValue = driver.findElement(By.xpath(xPath)).getText();\n";
		code = code + "}\n";	
		code = code + "capValue = capValue.replace(\" PRIORITY\", \"\");\n";
		code = code + "store.put(ChkElement_Property_Value, capValue);\n";
		code = code + "}\n";
		code = code + "catch (Exception e)\n"; 
		code = code + "{\n";
		code = code + "if(!Action.toUpperCase().equals(\"STOREVARIABLEIFEXIST\"))\n";
		code = code + "{\n";
		code = code + "}\n"; 
		code = code + "else\n"; 
		code = code + "{\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "Thread.sleep(500);\n";
		code = code + "ErrDesc = stepinfo;\n";
		code = code + "}\n";

		code = code + "public static void accept(String code, String Action){\n";
		code = code + "try {\n";	
		code = code + "Thread.sleep(1000);\n";
		code = code + "al = driver.switchTo().alert();\n";		        			
		code = code + "al.accept();\n"; 
		code = code + "}catch (Exception e) {\n";
		code = code + "if(!Action.toUpperCase().equals(\"ACCEPTIFEXIST\"))\n";
		code = code + "{\n";
		code = code + "} else {\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void chkExist(String code, String xPath){\n";
		code = code + "try {\n";		        				
		code = code + "driver.findElement(By.xpath(xPath));\n";
		code = code + "Exist = true;\n";
		code = code + "}\n"; 
		code = code + "catch (NoSuchElementException e)\n"; 
		code = code + "{\n";
		code = code + "Exist = false;\n";
		code = code + "}\n";
		code = code + "if (!Exist)\n";
		code = code + "{\n";
		code = code + "}\n";
		code = code + "else\n";
		code = code + "{\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void verify(String code, String ChkElement_Property, String ChkElement_Property_Value, String Action, String Element_Input_Value, String xPath){\n";
		code = code + "if (ChkElement_Property.toUpperCase().equals(\"TAGTEXT\"))\n";
		code = code + "{\n";
		code = code + "try\n";
		code = code + "{\n";
		code = code + "if(!driver.getPageSource().contains(\">\"ChkElement_Property_Value.toUpperCase()\"<\") && !driver.getPageSource().contains(\">\"ChkElement_Property_Value.toLowerCase()\"<\") && !driver.getPageSource().contains(\">\"ChkElement_Property_Value\"<\"))\n"; 
		code = code + "{\n";

		code = code +"}\n";
		code = code +"else if (driver.getPageSource().contains(\">\"+ChkElement_Property_Value.toUpperCase()+\"<\") && driver.getPageSource().contains(\">\"+ChkElement_Property_Value.toLowerCase()+\"<\") && driver.getPageSource().contains(\">\"+ChkElement_Property_Value+\"<\"))\n";
		code = code +"{\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "catch (NoSuchElementException e)\n"; 
		code = code +"{\n";
		code = code +"if(!Action.toUpperCase().equals(\"VERIFYIFEXIST\"))\n";
		code = code +"{\n";

		code = code +"}\n"; 
		code = code +"else\n"; 
		code = code +"{\n";

		code = code +"}\n";
		code = code +"}\n";
		code = code +"}\n";
		code = code +"else if(ChkElement_Property.toUpperCase().equals(\"SPECIFICTAGTEXT\"))\n"; 
		code = code +"{\n";
		code = code +"try\n"; 
		code = code +"{\n";
		code = code +"if (!driver.getPageSource().contains(\"<\"+Element_Input_Value+\">\"+ChkElement_Property_Value.toUpperCase()+\"</\"+Element_Input_Value+\">\") && !driver.getPageSource().contains(\"<\"+Element_Input_Value+\">\"+ChkElement_Property_Value.toLowerCase()+\"</\"+Element_Input_Value+\">\") && !driver.getPageSource().contains(\"<\"+Element_Input_Value+\">\"+ChkElement_Property_Value+\"<\"+Element_Input_Value+\">\"))\n"; 
		code = code +"{\n";

		code = code + "}\n";
		code = code + "else if (driver.getPageSource().contains(\"<\"+Element_Input_Value+\">\"+ChkElement_Property_Value.toUpperCase()+\"</\"+Element_Input_Value+\">\") && driver.getPageSource().contains(\"<\"+Element_Input_Value+\">\"+ChkElement_Property_Value.toLowerCase()+\"</\"+Element_Input_Value+\">\") && driver.getPageSource().contains(\"<\"+Element_Input_Value+\">\"+ChkElement_Property_Value+\"</\"+Element_Input_Value+\">\"))\n";
		code = code + "{\n";

		code = code + "}\n";
		code = code + "}\n";
		code = code + "catch(Exception e)\n";
		code = code + "{\n";
		code = code + "if(!+Action.toUpperCase().equals(\"VERIFYIFEXIST\"))\n";
		code = code + "{\n";

		code = code + "}\n";
		code = code + "else\n";
		code = code + "{\n";

		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "else if(ChkElement_Property.toUpperCase().equals(\"GETTEXT\"))\n"; 
		code = code + "{\n";
		code = code + "try\n";
		code = code + "{\n";
		code = code + "if (!driver.findElement(By.xpath(xPath)).getText().trim().toUpperCase().equals(ChkElement_Property_Value.toUpperCase()))\n";
		code = code + "{\n";

		code = code + "}\n";
		code = code + "else if(driver.findElement(By.xpath(xPath)).getText().trim().toUpperCase().equals(ChkElement_Property_Value.toUpperCase()))\n";
		code = code + "{\n";

		code = code + "}\n";
		code = code + "}\n";
		code = code + "catch(Exception e)\n";
		code = code + "{\n";
		code = code + "if(!Action.toUpperCase().equals(\"VERIFYIFEXIST\"))\n";
		code = code + "{\n";

		code = code + "}\n";
		code = code + "else\n";
		code = code + "{\n";

		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "else\n"; 
		code = code + "{\n";
		code = code + "try\n";
		code = code + "{\n";
		code = code + "if(!driver.findElement(By.xpath(xPath)).getAttribute(ChkElement_Property).toUpperCase().equals(ChkElement_Property_Value.toUpperCase()))\n";
		code = code + "{\n";

		code = code + "}\n";
		code = code + "else if(driver.findElement(By.xpath(xPath)).getAttribute(ChkElement_Property).toUpperCase().equals(ChkElement_Property_Value.toUpperCase()))\n";
		code = code + "{\n";

		code = code + "}\n";
		code = code + "}\n";
		code = code + "catch(Exception e)\n";
		code = code + "{\n";
		code = code + "if(!Action.toUpperCase().equals(\"VERIFYIFEXIST\"))\n";
		code = code + "{\n";

		code = code + "}\n";
		code = code + "else\n";
		code = code + "{\n";

		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "}\n";

		code = code + "public static void checkBox(String code, String xPath, String Action, String Element_Input_Value ){\n";
		code = code + "try\n";
		code = code + "{\n";	
		code = code + "waitUntilElementPresent(xPath, Action);\n";
		code = code + "if (Element_Input_Value.toUpperCase().equals(\"ON\"))\n";
		code = code + "{\n";
		code = code + "if (!driver.findElement(By.xpath(xPath)).isSelected())\n";
		code = code + "{\n";
		code = code + "driver.findElement(By.xpath(xPath)).click();\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "else\n"; 
		code = code + "{\n";
		code = code + "if (driver.findElement(By.xpath(xPath)).isSelected())\n";
		code = code + "{\n";
		code = code + "driver.findElement(By.xpath(xPath)).click();\n";
		code = code + "}\n";	
		code = code + "}\n";
		code = code + "}\n";
		code = code + "catch(Exception e)\n";
		code = code + "{\n";
		code = code + "if(!Action.toUpperCase().equals(\"CHECKIFEXIST\"))\n";
		code = code + "{\n";
		code = code + "}\n"; 
		code = code + "else\n"; 
		code = code + "{\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void frameChange(String code, String Element_Property_Value, String Action){\n";
		code = code + "try\n";
		code = code + "{\n";
		code = code + "if (Element_Property_Value.toUpperCase().equals(\"DEFAULT\"))\n";
		code = code + "{\n";
		code = code + "driver.switchTo().defaultContent();\n";
		code = code + "}\n";
		code = code + "else\n";
		code = code + "{\n";
		code = code + "driver.switchTo().frame(Element_Property_Value);\n";
		code = code + "Thread.sleep(1000);\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "catch(Exception e)\n";
		code = code + "{\n";
		code = code + "if(!Action.toUpperCase().equals(\"FRAMECHANGEIFEXIST\")){\n";
		code = code + "} else {\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void select(String code, String xPath, String Action, String Element_Input_Value, String Element_Name, String Element_Property, String Element_Property_Value, String Step_No){\n";
		code = code + "try\n";
		code = code + "{\n";
		code = code + "waitUntilElementPresent(xPath, Action);\n";
		code = code + "combobox = new Select(driver.findElement(By.xpath(xPath)));\n";
		code = code + "if (Action.toUpperCase().equals(\"INDEX\"))\n";
		code = code + "{\n";
		code = code + "combobox.selectByIndex(Integer.parseInt(Element_Input_Value));\n";
		code = code + "}\n";
		code = code + "else if(Action.toUpperCase().equals(\"SELECTBYVISIBLETEXT\"))\n";
		code = code + "{\n";
		code = code + "combobox.selectByVisibleText(Element_Input_Value);\n";
		code = code + "}\n";
		code = code + "else\n";
		code = code + "{\n";
		code = code + "combobox.selectByValue(Element_Input_Value.toString());\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "catch(Exception e)\n";
		code = code + "{\n";
		code = code + "if(!Action.toUpperCase().contains(\"IFEXIST\"))\n";
		code = code + "{\n";
		code = code + "}\n"; 
		code = code + "else\n"; 
		code = code + "{\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void dblClick(String code, String xPath, String Action, String Element_Name, String Element_Property, String Element_Property_Value){\n";
		code = code + "try\n";
		code = code + "{\n";
		code = code + "waitUntilElementPresent(xPath, Action);\n";
		code = code + "doubleClick = new Actions(driver).doubleClick(driver.findElement(By.xpath(xPath)));\n";
		code = code + "doubleClick.build().perform();\n";
		code = code + "}\n";
		code = code + "catch(Exception e)\n";
		code = code + "{\n";
		code = code + "if(!Action.toUpperCase().equals(\"DBLCLICKIFEXIST\")){\n";
		code = code + "} else {\n";
		code = code + "log(\"warning\",\"Element not found Step \"+Element_Name+\" (\"+Element_Property+\"=\"+Element_Property_Value+\") has not been selected.Error IGNORED as its \"+Action.toUpperCase()+\");\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void submit(String code, String xPath, String Action){\n";
		code = code + "try\n";
		code = code + "{\n";
		code = code + "waitUntilElementPresent(xPath,Action);\n";
		code = code + "element = driver.findElement((By.xpath(xPath)));\n";
		code = code + "element.submit();\n";
		code = code + "Teststatus = \"PASS\";\n";
		code = code + "}\n";
		code = code + "catch(Exception e)\n";
		code = code +"{\n";
		code = code + "Teststatus = \"FAIL\";\n";
		code = code + "log(\"error\",e.getMessage());\n";
		code = code + "if(!Action.toUpperCase().contains(\"IFEXIST\"))\n";
		code = code + "{\n";
		code = code + "break;\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void click(String code, String xPath, String Action){\n";
		code = code + "try\n";
		code = code + "{\n"; 
		code = code + "waitUntilElementPresent(xPath, Action);\n";
		code = code + "element = driver.findElement((By.xpath(xPath)));\n";
		code = code + "element.click();\n";
		code = code + "}\n";
		code = code + "catch(Exception e)\n";
		code = code + "{\n";
		code = code + "if(!Action.toUpperCase().equals(\"CLICKIFEXIST\")){\n";
		code = code + "}else {\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void set(String code, String xPath, String Element_Input_Value, String Action){\n";
		code = code + "try\n";
		code = code + "{\n";
		code = code + "waitUntilElementPresent(xPath, Action);\n";
		code = code + "if (driver.findElement(By.xpath(xPath)).getTagName().toUpperCase().equals(\"TEXTAREA\"))\n"; 
		code = code + "{";					        			
		code = code + "element = driver.findElement((By.xpath(xPath)));\n";
		code = code + "element.clear();\n";
		code = code + "((JavascriptExecutor)driver).executeScript(\"arguments[0].value = arguments[1];\", element, Element_Input_Value);\n";
		code = code + "}\n";
		code = code + "else\n";
		code = code + "{\n";
		code = code + "element = driver.findElement((By.xpath(xPath)));\n";
		code = code + "element.clear();\n";
		code = code + "element.sendKeys(Element_Input_Value);\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "catch(Exception e)\n";
		code = code + "{\n";
		code = code + "if(!Action.toUpperCase().equals(\"SETIFEXIST\")){\n";
		code = code + "}else {\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void dragAndDrop(String code, String xPath, String Element_Input_Value, String Action){\n";
		code = code + "try {\n";
		code = code + "actions = new Actions(driver);\n";
		code = code + "source = driver.findElement(By.xpath(xPath));\n";
		code = code + "target = driver.findElement(By.xpath(Element_Input_Value));\n";
		code = code + "actions.dragAndDrop(source, target).build().perform();\n";
		code = code + "} catch (Exception e) {\n";
		code = code + "if(!Action.toUpperCase().equals(\"DRAGANDDROPIFEXIST\")){\n";
		code = code + "}else {\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void dragAndDrop(String code, String xPath, String Element_Input_Value, String Action){\n";
		code = code + "try {\n";
		code = code + "actions = new Actions(driver);\n";
		code = code + "source = driver.findElement(By.xpath(xPath));\n";
		code = code + "target = driver.findElement(By.xpath(Element_Input_Value));\n";
		code = code + "actions.dragAndDrop(source, target).build().perform();\n";
		code = code + "} catch (Exception e) {\n";
		code = code + "if(!Action.toUpperCase().equals(\"DRAGANDDROPIFEXIST\")){\n";
		code = code + "}else {\n";
		code = code + "}\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void moveTo(String code, String xPath){\n";
		code = code + "js = (JavascriptExecutor) driver;\n"; 
		code = code + "ele = driver.findElement(By.xpath(xPath));\n";
		code = code + "js.executeScript(\"arguments[0].click();\", ele);\n";
		code = code + "mouseOverScript = \"if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}\";\n";
		code = code + "js.executeScript(mouseOverScript, ele);\n";
		code = code + "driver.findElement(By.xpath(xPath)).click();\n";
		code = code + "}\n";

		code = code + "public static void soap_verify(String code, String ChkElement_Property_Value){\n";
		code = code + "System.out.println(\"SOAP_VERIFY\");\n";
		code = code + "soap_res = (String) store.get(\"Response\");\n";
		code = code + "String[] soapArr, soapArr1;\n";
		code = code + "soapArr = soap_res.split(\"<\"+Element_Property_Value+\">\");\n";
		code = code + "soapArr1 = soapArr[1].split(\"<\");\n";
		code = code + "System.out.println(soapArr1[0]);\n";
		code = code + "if(soapArr1[0].contentEquals(ChkElement_Property_Value)){\n";
		code = code + "System.out.println(\"PASS\");\n";
		code = code + "}\n";
		code = code + "else{\n";
		code = code + "System.out.println(\"FAIL\");\n";
		code = code + "}\n";
		code = code + "}\n";

		code = code + "public static void soap(String code, String Element_Property_Value){\n";
		code = code + "System.out.println(\"SOAP\");\n";
		code = code + "URL = new URL(Element_Property_Value);\n";
		code = code + "connection = (HttpURLConnection) URL.openConnection();\n";
		code = code + "connection.setRequestMethod(\"POST\");\n";
		code = code + "connection.setDoOutput(true);\n";
		code = code + "connection.setRequestProperty(\"Content-type\", \"text/xml; charset=utf-8\");\n";
		code = code + "connection.setRequestProperty(\"SOAPAction\", \"\");\n";
		code = code + "xmlInput = Element_Input_Value;\n";
		code = code + "out = connection.getOutputStream();\n";
		code = code + "out.write(xmlInput.getBytes());\n";       
		code = code + "is = connection.getInputStream();\n";
		code = code + "in = new BufferedReader(new InputStreamReader(is));\n";
		code = code + "StringBuffer response = new StringBuffer();\n";
		code = code + "while ((line = in.readLine()) != null) {\n";
		code = code + "response.append(line);\n";
		code = code + "}\n";
		code = code + "in.close();\n";
		code = code + "res = response.toString();\n";
		code = code + "Response = res.replaceAll(\"<\", \"<\");\n";
		code = code + "Response2 = Response.replaceAll(\">\", \">\");\n";
		code = code + "Response=Response2.replaceAll(\"&quot;\", \"\"\");\n";
		code = code + "Response2=Response.replace(\"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\", \"\");\n";
		code = code + "Response=Response2.replace(\"<?xml version=\"1.0\" encoding=\"utf-8\"?>\",\"\");\n";
		code = code + "Response2 = Response.replaceAll(\"&lt;\", \"<\");\n";
		code = code + "Response = Response2.replaceAll(\"&gt;\", \">\");\n";
		code = code + "store.put(\"Response\", Response);\n";
		code = code + "}\n";

		code = code + "public void waitUntilElementPresent(String selectedApp, String node_Name, String xPath, String Action) throws IOException\n"+
				"{\n"+
				"try\n"+
				"{\n"+ 
				"WebDriverWait wait = new WebDriverWait(driver,20);\n"+
				"wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));\n"+
				"}\n"+
				"catch(Exception e)\n"+
				"{\n"+
				"if(!Action.toUpperCase().contains(\"IFEXIST\"))\n"+
				"{\n"+
				"System.exit(0);\n"+
				"}\n"+
				"}\n"+
				"}\n";

		code = code + "public static void mouseclick(String inpkey, String xPath, WebDriver driver) throws AWTException, InterruptedException {\n"+
				"Robot robot = new Robot();\n"+  
				"Thread.sleep(50);\n"+
				"if (!inpkey.contains(\"(\"))\n"+
				"{\n"+
				"switch (inpkey)\n"+ 
				"{\n"+       
				"case \"CLICK\":\n"+ 
				"robot.mousePress(InputEvent.BUTTON1_MASK);\n"+
				"robot.mouseRelease(InputEvent.BUTTON1_MASK);\n"+
				"break;\n"+
				"case \"DBLCLICK\": case \"DOUBLECLICK\":\n"+ 
				"robot.mousePress(InputEvent.BUTTON1_MASK);\n"+
				"robot.mouseRelease(InputEvent.BUTTON1_MASK);\n"+
				"robot.mousePress(InputEvent.BUTTON1_MASK);\n"+
				"robot.mouseRelease(InputEvent.BUTTON1_MASK); break;\n"+
				"case \"RHTCLICK\": case \"RIGHTCLICK\":\n"+ 
				"robot.mousePress(InputEvent.BUTTON3_MASK);\n"+
				"robot.mouseRelease(InputEvent.BUTTON3_MASK);\n"+
				"break;\n"+		     
				"case \"MOVE\":\n"+
				"driver.manage().window().setPosition(new Point(0, 0));\n"+
				"Robot robot1 = new Robot();\n"+ 
				"Point coordinates = driver.findElement(By.xpath(xPath)).getLocation();\n"+
				"((JavascriptExecutor) driver).executeScript(\"window.focus();\");\n"+
				"robot1.mouseMove(coordinates.getX()+20,coordinates.getY()+160);\n"+
				"Thread.sleep(1000);\n"+
				"driver.findElement(By.xpath(xPath)).click();\n"+
				"Thread.sleep(100);\n"+
				"break;\n"+

			        "default:\n"+
			        "throw new IllegalArgumentException(\"Cannot PRESS key  + inpkey+\");\n"+
			        "}\n"+
			        "}\n"+ 
			        "else\n"+ 
			        "{\n"+
			        "Robot robot2 = new Robot();\n"+ 
			        "String mloc = inpkey.substring(inpkey.indexOf(\"(\")+1, inpkey.lastIndexOf(\")\"));\n"+
			        "String[] mlocArray = mloc.split(\",\");\n"+
			        "robot2.mouseMove(Integer.parseInt(mlocArray[0]),Integer.parseInt(mlocArray[1]));\n"+
			        "}\n"+
			        "Thread.sleep(300);\n"+
			        "}\n";

		code = code + "public static void errorchecker(String selectedApp, String node_Name, String DatasheetPath, String SheetName, WebDriver driver) throws BiffException, IOException, AWTException\n"+
				"{\n"+
				"FileInputStream  fis1 = new FileInputStream(DatasheetPath);\n"+
				"Workbook wb1 = Workbook.getWorkbook(fis1);\n"+
				"Sheet s4 = wb1.getSheet(SheetName);\n"+
				"String Error_No = null, Library = null, Error_Name = null, Element_Type = null, Element_Property = null, Element_Property_Value = null, Action_IfFound = null, Error_Type = null;\n"+
				"String xPath = null;\n"+
				"for (int i=1;i<s4.getRows();i++) \n"+
				"{\n"+
				"Error_No = s4.getCell(0,i).getContents();\n"+
				"Library = s4.getCell(1,i).getContents();\n"+
				"Error_Name = s4.getCell(2,i).getContents();\n"+
				"Element_Property = s4.getCell(3,i).getContents();\n"+
				"Element_Property_Value = s4.getCell(4,i).getContents();\n"+
				"Action_IfFound = s4.getCell(5,i).getContents();\n"+
				"Error_Type = s4.getCell(6,i).getContents();\n"+
				"String[] PropArray, PropValArray;\n"+
				"if(Element_Property.toUpperCase().contentEquals(\"XPATH\"))\n"+
				"{\n"+
				"xPath = Element_Property_Value;\n"+
				"}\n"+
				"else\n"+
				"{\n"+
				"if(Element_Property.contains(\",,\"))\n"+
				"{\n"+
				"PropArray = Element_Property.split(\",,\");\n"+
				"PropValArray = Element_Property_Value.split(\",,\");\n"+
				"xPath = \"//*[\";\n"+
				"for(int j = 0; j < PropArray.length; j++)\n"+
				"{\n"+
				"if(j>0)\n"+
				"xPath = xPath + \" and \";\n"+
				"xPath = xPath + \"@\"+PropArray[j] + \"='\" + PropValArray[j] + \"'\";\n"+
				"}\n"+
				"xPath = xPath + \"]\";\n"+
				"}\n"+
				"else\n"+
				"xPath = \"//*[@\"+Element_Property+\"='\"+Element_Property_Value+\"']\";\n"+
				"}\n"+
				"try \n"+
				"{\n"+
				"if(driver.findElement(By.xpath(xPath)).isDisplayed())\n"+
				"{\n"+
				"screencapture(selectedApp, SheetName);\n"+
				"if(Action_IfFound.toUpperCase().contentEquals(\"TERMINATE\"))\n"+
				"{\n"+
				"System.exit(0);\n"+
				"}\n"+
				"}\n"+
				"}\n"+
				"catch (NoSuchElementException e)\n"+
				"{\n"+
				"}\n"+
				"}\n"+
				"}\n";

		code = code + "public static void screencapture(String selectedApp, String SheetName) throws IOException, AWTException\n"+
				"{\n"+		
				"File resDir = new File(\"C:\\Selenium\\Results\\Screenshots\");\n"+
				"if (!resDir.exists())\n"+
				"{\n"+ 
				"resDir.mkdir();\n"+
				"}\n"+ 
				"File resDir1 = new File(\"C:\\Selenium\\Results\\Screenshots\\\"+selectedApp+\");\n"+
				"if (!resDir1.exists())\n"+
				"{\n"+
				"resDir1.mkdir();\n"+
				"}\n"+ 
				"DateFormat scrcpttme = new SimpleDateFormat(\"yyyy_MM_dd hh_mm_ss a\");\n"+
				"Calendar cal = Calendar.getInstance();\n"+
				"String scrcptTime = scrcpttme.format(cal.getTime());\n"+
				"Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();\n"+
				"Rectangle screenRectangle = new Rectangle(screenSize);\n"+	
				"Robot robot = new Robot();\n"+
				"BufferedImage image = robot.createScreenCapture(screenRectangle);\n"+
				"ImageIO.write(image, \"png\", new File(resDir1+\"\\\"+selectedApp+\"-\"+\" \"+SheetName+scrcptTime+\".jpg\"));\n"+	
				"}\n";

		code = code + "public static void keypress(String inpkey) throws AWTException, InterruptedException {\n"+
				"Robot robot = new Robot();\n"+  
				"Thread.sleep(50);\n"+
				"switch (inpkey) {\n"+       
				"case \"ENTER\": robot.keyPress(KeyEvent.VK_ENTER); break;\n"+
				"case \"ESC\": robot.keyPress(KeyEvent.VK_ESCAPE); break;\n"+
				"case \"TAB\": robot.keyPress(KeyEvent.VK_TAB); break;\n"+
				"case \"SPACE\": robot.keyPress(KeyEvent.VK_SPACE); break;\n"+
				"case \"PAGEUP\": robot.keyPress(KeyEvent.VK_PAGE_UP); break;\n"+
				"case \"PAGEDOWN\": robot.keyPress(KeyEvent.VK_PAGE_DOWN); break;\n"+
				"case \"BACKSPACE\": robot.keyPress(KeyEvent.VK_BACK_SPACE); break;\n"+
				"case \"UP\": robot.keyPress(KeyEvent.VK_UP); break;\n"+
				"case \"DOWN\": robot.keyPress(KeyEvent.VK_DOWN); break;\n"+
				"case \"LEFT\": robot.keyPress(KeyEvent.VK_LEFT); break;\n"+
				"case \"RIGHT\": robot.keyPress(KeyEvent.VK_RIGHT); break;\n"+
				"case \"END\": robot.keyPress(KeyEvent.VK_END); break;\n"+
				"case \"HOME\": robot.keyPress(KeyEvent.VK_HOME); break;\n"+
				"case \"INSERT\": robot.keyPress(KeyEvent.VK_INSERT); break;\n"+
				"case \"DELETE\": robot.keyPress(KeyEvent.VK_DELETE); break;\n"+
				"case \"SHIFT\": robot.keyPress(KeyEvent.VK_SHIFT); break;\n"+
				"case \"CTRL\": robot.keyPress(KeyEvent.VK_CONTROL); break;\n"+
				"case \"ALT\": robot.keyPress(KeyEvent.VK_ALT); break;\n"+
				"case \"COPY\": keypress(\"CTRL\");keypress(\"C\");keyrelease(\"C\");keyrelease(\"CTRL\"); break;\n"+
				"case \"PASTE\": keypress(\"CTRL\");keypress(\"V\");keyrelease(\"V\");keyrelease(\"CTRL\");break;\n"+
				"case \"CUT\":keypress(\"CTRL\");keypress(\"X\");keyrelease(\"X\");keyrelease(\"CTRL\"); break;\n"+
				"case \"A\": robot.keyPress(KeyEvent.VK_A); break;\n"+
				"case \"B\": robot.keyPress(KeyEvent.VK_B); break;\n"+
				"case \"C\": robot.keyPress(KeyEvent.VK_C); break;\n"+
				"case \"D\": robot.keyPress(KeyEvent.VK_D); break;\n"+
				"case \"E\": robot.keyPress(KeyEvent.VK_E); break;\n"+
				"case \"F\": robot.keyPress(KeyEvent.VK_F); break;\n"+
				"case \"G\": robot.keyPress(KeyEvent.VK_G); break;\n"+
				"case \"H\": robot.keyPress(KeyEvent.VK_H); break;\n"+
				"case \"I\": robot.keyPress(KeyEvent.VK_I); break;\n"+
				"case \"J\": robot.keyPress(KeyEvent.VK_J); break;\n"+
				"case \"K\": robot.keyPress(KeyEvent.VK_K); break;\n"+
				"case \"L\": robot.keyPress(KeyEvent.VK_L); break;\n"+
				"case \"M\": robot.keyPress(KeyEvent.VK_M); break;\n"+
				"case \"N\": robot.keyPress(KeyEvent.VK_N); break;\n"+
				"case \"O\": robot.keyPress(KeyEvent.VK_O); break;\n"+
				"case \"P\": robot.keyPress(KeyEvent.VK_P); break;\n"+
				"case \"Q\": robot.keyPress(KeyEvent.VK_Q); break;\n"+
				"case \"R\": robot.keyPress(KeyEvent.VK_R); break;\n"+
				"case \"S\": robot.keyPress(KeyEvent.VK_S); break;\n"+
				"case \"T\": robot.keyPress(KeyEvent.VK_T); break;\n"+
				"case \"U\": robot.keyPress(KeyEvent.VK_U); break;\n"+
				"case \"V\": robot.keyPress(KeyEvent.VK_V); break;\n"+
				"case \"W\": robot.keyPress(KeyEvent.VK_W); break;\n"+
				"case \"X\": robot.keyPress(KeyEvent.VK_X); break;\n"+
				"case \"Y\": robot.keyPress(KeyEvent.VK_Y); break;\n"+
				"case \"Z\": robot.keyPress(KeyEvent.VK_Z); break;\n"+       
				"case \"0\": robot.keyPress(KeyEvent.VK_0); break;\n"+
				"case \"1\": robot.keyPress(KeyEvent.VK_1); break;\n"+
				"case \"2\": robot.keyPress(KeyEvent.VK_2); break;\n"+
				"case \"3\": robot.keyPress(KeyEvent.VK_3); break;\n"+
				"case \"4\": robot.keyPress(KeyEvent.VK_4); break;\n"+
				"case \"5\": robot.keyPress(KeyEvent.VK_5); break;\n"+
				"case \"6\": robot.keyPress(KeyEvent.VK_6); break;\n"+
				"case \"7\": robot.keyPress(KeyEvent.VK_7); break;\n"+
				"case \"8\": robot.keyPress(KeyEvent.VK_8); break;\n"+
				"case \"9\": robot.keyPress(KeyEvent.VK_9); break;\n"+
				"case \"`\": robot.keyPress(KeyEvent.VK_BACK_QUOTE); break;\n"+
				"case \"-\": robot.keyPress(KeyEvent.VK_MINUS); break;\n"+
				"case \"=\": robot.keyPress(KeyEvent.VK_EQUALS); break;\n"+
				"case \"~\": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_BACK_QUOTE); break;\n"+
				"case \"!\": robot.keyPress(KeyEvent.VK_EXCLAMATION_MARK); break;\n"+
				"case \"@\": robot.keyPress(KeyEvent.VK_AT); break;\n"+
				"case \"#\": robot.keyPress(KeyEvent.VK_NUMBER_SIGN); break;\n"+
				"case \"$\": robot.keyPress(KeyEvent.VK_DOLLAR); break;\n"+
				"case \"%\": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_5); break;\n"+
				"case \"^\": robot.keyPress(KeyEvent.VK_CIRCUMFLEX); break;\n"+
				"case \"&\": robot.keyPress(KeyEvent.VK_AMPERSAND); break;\n"+
				"case \"*\": robot.keyPress(KeyEvent.VK_ASTERISK); break;\n"+
				"case \"(\": robot.keyPress(KeyEvent.VK_LEFT_PARENTHESIS); break;\n"+
				"case \")\": robot.keyPress(KeyEvent.VK_RIGHT_PARENTHESIS); break;\n"+
				"case \"_\": robot.keyPress(KeyEvent.VK_UNDERSCORE); break;\n"+
				"case \"+\": robot.keyPress(KeyEvent.VK_PLUS); break;\n"+
				"case \"\t\": robot.keyPress(KeyEvent.VK_TAB); break;\n"+
				"case \"\n\": robot.keyPress(KeyEvent.VK_ENTER); break;\n"+
				"case \"[\": robot.keyPress(KeyEvent.VK_OPEN_BRACKET); break;\n"+
				"case \"]\": robot.keyPress(KeyEvent.VK_CLOSE_BRACKET); break;\n"+
				"case \"\\\": robot.keyPress(KeyEvent.VK_BACK_SLASH); break;\n"+
				"case \"{\": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_OPEN_BRACKET); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \"}\": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_CLOSE_BRACKET); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \"|\": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_BACK_SLASH); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \";\": robot.keyPress(KeyEvent.VK_SEMICOLON); break;\n"+
				"case \":\": robot.keyPress(KeyEvent.VK_COLON); break;\n"+
				"case \"'\": robot.keyPress(KeyEvent.VK_QUOTE); break;\n"+
				"case \"\"\": robot.keyPress(KeyEvent.VK_QUOTEDBL); break;\n"+
				"case \",\": robot.keyPress(KeyEvent.VK_COMMA); break;\n"+
				"case \"<\": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_COMMA); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \".\": robot.keyPress(KeyEvent.VK_PERIOD); break;\n"+
				"case \">\": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_PERIOD); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \"/\": robot.keyPress(KeyEvent.VK_SLASH); break;\n"+
				"case \"?\": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_SLASH); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \" \": robot.keyPress(KeyEvent.VK_SPACE); break;\n"+
				"case \"F1\": robot.keyPress(KeyEvent.VK_F1); break;\n"+
				"case \"F2\": robot.keyPress(KeyEvent.VK_F2); break;\n"+
				"case \"F3\": robot.keyPress(KeyEvent.VK_F3); break;\n"+
				"case \"F4\": robot.keyPress(KeyEvent.VK_F4); break;\n"+
				"case \"F5\": robot.keyPress(KeyEvent.VK_F5); break;\n"+
				"case \"F6\": robot.keyPress(KeyEvent.VK_F6); break;\n"+
				"case \"F7\": robot.keyPress(KeyEvent.VK_F7); break;\n"+
				"case \"F8\": robot.keyPress(KeyEvent.VK_F8); break;\n"+
				"case \"F9\": robot.keyPress(KeyEvent.VK_F9); break;\n"+
				"case \"F10\": robot.keyPress(KeyEvent.VK_F10); break;\n"+
				"case \"F11\": robot.keyPress(KeyEvent.VK_F11); break;\n"+
				"case \"F12\": robot.keyPress(KeyEvent.VK_F12); break;\n"+
				"case \"CAPSLOCK\": robot.keyPress(KeyEvent.VK_CAPS_LOCK); break;\n"+
				"case \"SCROLLLOCK\": robot.keyPress(KeyEvent.VK_SCROLL_LOCK); break;\n"+
				"case \"NUMLOCK\": robot.keyPress(KeyEvent.VK_NUM_LOCK); break;\n"+
				"default:\n"+
				"throw new IllegalArgumentException(\"Cannot PRESS key \" + inpkey);\n"+
				"}\n"+
				"Thread.sleep(50);\n"+

	    	"}\n";

		code = code + "public static void keyrelease(String inpkey) throws AWTException, InterruptedException {\n"+
				"Robot robot = new Robot();\n"+  
				"Thread.sleep(50);\n"+
				"switch (inpkey) {\n"+       
				"case \"ENTER\": robot.keyRelease(KeyEvent.VK_ENTER); break;\n"+
				"case \"ESC\": robot.keyRelease(KeyEvent.VK_ESCAPE); break;\n"+
				"case \"TAB\": robot.keyRelease(KeyEvent.VK_TAB); break;\n"+
				"case \"SPACE\": robot.keyRelease(KeyEvent.VK_SPACE); break;\n"+
				"case \"PAGEUP\": robot.keyRelease(KeyEvent.VK_PAGE_UP); break;\n"+
				"case \"PAGEDOWN\": robot.keyRelease(KeyEvent.VK_PAGE_DOWN); break;\n"+
				"case \"BACKSPACE\": robot.keyRelease(KeyEvent.VK_BACK_SPACE); break;\n"+
				"case \"UP\": robot.keyRelease(KeyEvent.VK_UP); break;\n"+
				"case \"DOWN\": robot.keyRelease(KeyEvent.VK_DOWN); break;\n"+
				"case \"LEFT\": robot.keyRelease(KeyEvent.VK_LEFT); break;\n"+
				"case \"RIGHT\": robot.keyRelease(KeyEvent.VK_RIGHT); break;\n"+
				"case \"END\": robot.keyRelease(KeyEvent.VK_END); break;\n"+
				"case \"HOME\": robot.keyRelease(KeyEvent.VK_HOME); break;\n"+
				"case \"INSERT\": robot.keyRelease(KeyEvent.VK_INSERT); break;\n"+
				"case \"DELETE\": robot.keyRelease(KeyEvent.VK_DELETE); break;\n"+
				"case \"SHIFT\": robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \"CTRL\": robot.keyRelease(KeyEvent.VK_CONTROL); break;\n"+
				"case \"ALT\": robot.keyRelease(KeyEvent.VK_ALT); break;\n"+
				"case \"COPY\": keyRelease(\"CTRL\");keyRelease(\"C\");keyrelease(\"C\");keyrelease(\"CTRL\"); break;\n"+
				"case \"PASTE\": keyRelease(\"CTRL\");keyRelease(\"V\");keyrelease(\"V\");keyrelease(\"CTRL\");break;\n"+
				"case \"CUT\":keyRelease(\"CTRL\");keyRelease(\"X\");keyrelease(\"X\");keyrelease(\"CTRL\"); break;\n"+
				"case \"A\": robot.keyRelease(KeyEvent.VK_A); break;\n"+
				"case \"B\": robot.keyRelease(KeyEvent.VK_B); break;\n"+
				"case \"C\": robot.keyRelease(KeyEvent.VK_C); break;\n"+
				"case \"D\": robot.keyRelease(KeyEvent.VK_D); break;\n"+
				"case \"E\": robot.keyRelease(KeyEvent.VK_E); break;\n"+
				"case \"F\": robot.keyRelease(KeyEvent.VK_F); break;\n"+
				"case \"G\": robot.keyRelease(KeyEvent.VK_G); break;\n"+
				"case \"H\": robot.keyRelease(KeyEvent.VK_H); break;\n"+
				"case \"I\": robot.keyRelease(KeyEvent.VK_I); break;\n"+
				"case \"J\": robot.keyRelease(KeyEvent.VK_J); break;\n"+
				"case \"K\": robot.keyRelease(KeyEvent.VK_K); break;\n"+
				"case \"L\": robot.keyRelease(KeyEvent.VK_L); break;\n"+
				"case \"M\": robot.keyRelease(KeyEvent.VK_M); break;\n"+
				"case \"N\": robot.keyRelease(KeyEvent.VK_N); break;\n"+
				"case \"O\": robot.keyRelease(KeyEvent.VK_O); break;\n"+
				"case \"P\": robot.keyRelease(KeyEvent.VK_P); break;\n"+
				"case \"Q\": robot.keyRelease(KeyEvent.VK_Q); break;\n"+
				"case \"R\": robot.keyRelease(KeyEvent.VK_R); break;\n"+
				"case \"S\": robot.keyRelease(KeyEvent.VK_S); break;\n"+
				"case \"T\": robot.keyRelease(KeyEvent.VK_T); break;\n"+
				"case \"U\": robot.keyRelease(KeyEvent.VK_U); break;\n"+
				"case \"V\": robot.keyRelease(KeyEvent.VK_V); break;\n"+
				"case \"W\": robot.keyRelease(KeyEvent.VK_W); break;\n"+
				"case \"X\": robot.keyRelease(KeyEvent.VK_X); break;\n"+
				"case \"Y\": robot.keyRelease(KeyEvent.VK_Y); break;\n"+
				"case \"Z\": robot.keyRelease(KeyEvent.VK_Z); break;\n"+       
				"case \"0\": robot.keyRelease(KeyEvent.VK_0); break;\n"+
				"case \"1\": robot.keyRelease(KeyEvent.VK_1); break;\n"+
				"case \"2\": robot.keyRelease(KeyEvent.VK_2); break;\n"+
				"case \"3\": robot.keyRelease(KeyEvent.VK_3); break;\n"+
				"case \"4\": robot.keyRelease(KeyEvent.VK_4); break;\n"+
				"case \"5\": robot.keyRelease(KeyEvent.VK_5); break;\n"+
				"case \"6\": robot.keyRelease(KeyEvent.VK_6); break;\n"+
				"case \"7\": robot.keyRelease(KeyEvent.VK_7); break;\n"+
				"case \"8\": robot.keyRelease(KeyEvent.VK_8); break;\n"+
				"case \"9\": robot.keyRelease(KeyEvent.VK_9); break;\n"+
				"case \"`\": robot.keyRelease(KeyEvent.VK_BACK_QUOTE); break;\n"+
				"case \"-\": robot.keyRelease(KeyEvent.VK_MINUS); break;\n"+
				"case \"=\": robot.keyRelease(KeyEvent.VK_EQUALS); break;\n"+
				"case \"~\": robot.keyRelease(KeyEvent.VK_SHIFT); robot.keyRelease(KeyEvent.VK_BACK_QUOTE); break;\n"+
				"case \"!\": robot.keyRelease(KeyEvent.VK_EXCLAMATION_MARK); break;\n"+
				"case \"@\": robot.keyRelease(KeyEvent.VK_AT); break;\n"+
				"case \"#\": robot.keyRelease(KeyEvent.VK_NUMBER_SIGN); break;\n"+
				"case \"$\": robot.keyRelease(KeyEvent.VK_DOLLAR); break;\n"+
				"case \"%\": robot.keyRelease(KeyEvent.VK_SHIFT); robot.keyRelease(KeyEvent.VK_5); break;\n"+
				"case \"^\": robot.keyRelease(KeyEvent.VK_CIRCUMFLEX); break;\n"+
				"case \"&\": robot.keyRelease(KeyEvent.VK_AMPERSAND); break;\n"+
				"case \"*\": robot.keyRelease(KeyEvent.VK_ASTERISK); break;\n"+
				"case \"(\": robot.keyRelease(KeyEvent.VK_LEFT_PARENTHESIS); break;\n"+
				"case \")\": robot.keyRelease(KeyEvent.VK_RIGHT_PARENTHESIS); break;\n"+
				"case \"_\": robot.keyRelease(KeyEvent.VK_UNDERSCORE); break;\n"+
				"case \"+\": robot.keyRelease(KeyEvent.VK_PLUS); break;\n"+
				"case \"\t\": robot.keyRelease(KeyEvent.VK_TAB); break;\n"+
				"case \"\n\": robot.keyRelease(KeyEvent.VK_ENTER); break;\n"+
				"case \"[\": robot.keyRelease(KeyEvent.VK_OPEN_BRACKET); break;\n"+
				"case \"]\": robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET); break;\n"+
				"case \"\\\": robot.keyRelease(KeyEvent.VK_BACK_SLASH); break;\n"+
				"case \"{\": robot.keyRelease(KeyEvent.VK_SHIFT); robot.keyRelease(KeyEvent.VK_OPEN_BRACKET); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \"}\": robot.keyRelease(KeyEvent.VK_SHIFT); robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \"|\": robot.keyRelease(KeyEvent.VK_SHIFT); robot.keyRelease(KeyEvent.VK_BACK_SLASH); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \";\": robot.keyRelease(KeyEvent.VK_SEMICOLON); break;\n"+
				"case \":\": robot.keyRelease(KeyEvent.VK_COLON); break;\n"+
				"case \"'\": robot.keyRelease(KeyEvent.VK_QUOTE); break;\n"+
				"case \"\"\": robot.keyRelease(KeyEvent.VK_QUOTEDBL); break;\n"+
				"case \",\": robot.keyRelease(KeyEvent.VK_COMMA); break;\n"+
				"case \"<\": robot.keyRelease(KeyEvent.VK_SHIFT); robot.keyRelease(KeyEvent.VK_COMMA); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \".\": robot.keyRelease(KeyEvent.VK_PERIOD); break;\n"+
				"case \">\": robot.keyRelease(KeyEvent.VK_SHIFT); robot.keyRelease(KeyEvent.VK_PERIOD); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \"/\": robot.keyRelease(KeyEvent.VK_SLASH); break;\n"+
				"case \"?\": robot.keyRelease(KeyEvent.VK_SHIFT); robot.keyRelease(KeyEvent.VK_SLASH); robot.keyRelease(KeyEvent.VK_SHIFT); break;\n"+
				"case \" \": robot.keyRelease(KeyEvent.VK_SPACE); break;\n"+
				"case \"F1\": robot.keyRelease(KeyEvent.VK_F1); break;\n"+
				"case \"F2\": robot.keyRelease(KeyEvent.VK_F2); break;\n"+
				"case \"F3\": robot.keyRelease(KeyEvent.VK_F3); break;\n"+
				"case \"F4\": robot.keyRelease(KeyEvent.VK_F4); break;\n"+
				"case \"F5\": robot.keyRelease(KeyEvent.VK_F5); break;\n"+
				"case \"F6\": robot.keyRelease(KeyEvent.VK_F6); break;\n"+
				"case \"F7\": robot.keyRelease(KeyEvent.VK_F7); break;\n"+
				"case \"F8\": robot.keyRelease(KeyEvent.VK_F8); break;\n"+
				"case \"F9\": robot.keyRelease(KeyEvent.VK_F9); break;\n"+
				"case \"F10\": robot.keyRelease(KeyEvent.VK_F10); break;\n"+
				"case \"F11\": robot.keyRelease(KeyEvent.VK_F11); break;\n"+
				"case \"F12\": robot.keyRelease(KeyEvent.VK_F12); break;\n"+
				"case \"CAPSLOCK\": robot.keyRelease(KeyEvent.VK_CAPS_LOCK); break;\n"+
				"case \"SCROLLLOCK\": robot.keyRelease(KeyEvent.VK_SCROLL_LOCK); break;\n"+
				"case \"NUMLOCK\": robot.keyRelease(KeyEvent.VK_NUM_LOCK); break;\n"+
				"default:\n"+
				"throw new IllegalArgumentException(\"Cannot Release key \" + inpkey);\n"+
				"}\n"+
				"Thread.sleep(50);\n"+

		    	"}\n";


		code = code + "public static void keyboard(String inpstring) throws AWTException, InterruptedException {\n"+    	
				"int l;\n"+
				"String[] keybsplit;\n"+
				"if (inpstring.contains(\"+\")) {\n"+
				"keybsplit = inpstring.split(\"\\+\");\n"+
				"for(l=0;l<keybsplit.length;l++) {\n"+
				"keypress(keybsplit[l]);\n"+
				"}\n"+
				"for(l=keybsplit.length-1;l>=0;l--){\n"+
				"keyrelease(keybsplit[l]);\n"+
				"}\n"+
				"} else {\n"+
				"keypress(inpstring);\n"+
				"keyrelease(inpstring);\n"+
				"}\n"+
				"}\n";

		code = code + "public static void wordtype(String inpstring) throws AWTException, InterruptedException {\n"+    	
				"int l;\n"+
				"for (l = 0;l<inpstring.length();l++) {\n"+
				"char invalue = inpstring.charAt(l);\n"+
				"String inpvalue = Character.toString(invalue);\n"+
				"keypress(inpvalue);\n"+
				"}\n"+
				"for (l=inpstring.length()-1;l>=0;l--){\n"+
				"char invalue = inpstring.charAt(l);\n"+ 
				"String inpvalue = Character.toString(invalue);\n"+    		
				"keyrelease(inpvalue);\n"+
				"}\n"+
				"}\n";



		code = code +"}\n";
		wb.close();

		writer.write(code);
		writer.close();

		JavaCompiler compiler    = ToolProvider.getSystemJavaCompiler();
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

		fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Arrays.asList(new File("bin")));
		// Compile the file
		compiler.getTask(null,fileManager,null,null,null,fileManager.getJavaFileObjectsFromFiles(Arrays.asList(sourceFile))).call();
		fileManager.close();
	}
}
