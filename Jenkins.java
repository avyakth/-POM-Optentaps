
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JEditorPane;

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

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;


public class Jenkins {
	
	static String Teststatus = null;
	static String status = "", stepinfo = "", ErrDesc = "";
	static Graphics g;
	static boolean Exist;
	public static String capValue = "";
	public static Hashtable<String, String> store = new Hashtable<String, String>();
	public static String childWindow = "", ParentWindow = "";
	static Calendar cal;
	static String reprtTime;
	 public enum logType
	 {
	     warning, info, config, error, validation; 
	 }
	static String color;
	static DateFormat rptTme;
	public static String errorcriticality = "NON-CRITICAL";
	WebDriver driver = null;
	static Connection conn = null, conn1 = null;
	static Statement stmt = null, stmt1 = null;
	static ResultSet rs = null;
	static String sql, PK_ID;
	static String urlToExec="";
	
	@Parameters({"hubUrl","mail_app","baseUrl"})
	@Test
	public void sendMail(String hubUrl,String mail_app,String baseUrl) throws BiffException{
		ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<RemoteWebDriver>();
		 System.out.println("0");
		 DesiredCapabilities caps = DesiredCapabilities.firefox(); 
		 System.out.println("1");
		 caps.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
		 System.out.println("2");
		 caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
		 System.out.println("3");
		 try {
			threadDriver.set(new RemoteWebDriver(new URL(hubUrl), caps));
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 System.out.println("4");
		 driver = threadDriver.get();
		 System.out.println("5");
		 driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		 System.out.println("6");
		 driver.manage().deleteAllCookies();
		 System.out.println("7");
		 driver.manage().window().maximize();
		 System.out.println("beforetest"); 
		String host = "smtp.verizon.com";
		final String user = "svc-dpm-devops";// change accordingly
		final String password = "nS5$mK8nS5$mK8nS";// change accordingly
		String to = "avyakth.kumar@one.verizon.com";// change accordingly

		// Email message content
		System.out.println("Excel report calling");
		String bdymsg = ExcelReport(mail_app, baseUrl);
		System.out.println("excelreport called");
		

		// Get the session object
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		System.out.println("added host");
		props.put("mail.smtp.auth", "true");
		System.out.println("added auth");
		props.put("mail.smtp.port", "25");
		System.out.println("added port");
		props.put("mail.smtp.starttls.enable", "true");
		System.out.println("added enable");
	
		

		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			
			protected PasswordAuthentication getPasswordAuthentication() {
				System.out.println("Authenticating");
				return new PasswordAuthentication(user, password);
			}
		});

		// Compose the message
		try {
			MimeMessage message = new MimeMessage(session);
			System.out.println("created message object");
			message.setFrom(new InternetAddress(user));
			System.out.println("set address");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			System.out.println("added recipient");
			message.setSubject("trial");
			System.out.println("set subject");
			message.setText(bdymsg);
			System.out.println("set text");
			// send the message

			Transport.send(message);
			System.out.println("message sent successfully...");

		} catch (MessagingException e) {
			System.out.println("In catch block");
			e.printStackTrace();
		}
	}
	
	public String ExcelReport(String mail_app,String baseUrl) throws BiffException {
		
		int Critical_Error_Counter = 0;
		int  Critical_Counter = 0;
		int Failed_Case_Counter = 0;
		int Passed_Case_Counter = 0;
		String appname=mail_app;
		String appurl = null;
		String html=null;
		String UsrNme;
		String CertifyingAs;
		String color;
		int Total_Cases;
		String System_User_Name = "Avyakth Kumar";
		try

		{

		// Code to find the System's User name
		

		// Date
			
			 System.out.println("8");
		Calendar cal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");
		String cal1 = dateFormat.format(cal.getTime());
		DateFormat dateFormat1 = new SimpleDateFormat("MM/dd/yyyy");
		String cal2 = dateFormat1.format(cal.getTime());

		// WorkBook
		 System.out.println("9");
		/*//Checking Directory
		 System.out.println("Checking Directory");
			String dir_path = "C:\\Jenkins\\Results\\";
			File actualdir = new File(dir_path);
			System.out.println(actualdir.exists());
			if (!actualdir.exists()) { 
				actualdir.mkdir();
		}
			//Checking File
			 System.out.println("Checking File");
			String file_path = "C:\\Jenkins\\Results\\" + appname + "Final Report" + cal1 + ".xls";
			File actualFile = new File(file_path);
			
			
			String currentfilepath = ".\\" + appname + "Final Report" + cal1 + ".xls";
			File file_path2 = new File(currentfilepath);
			
			System.out.println("File path exists or not : "+actualFile.exists());
			if (!actualFile.exists()) { 
				actualFile.createNewFile();
				FileUtils.copyFile(file_path2,actualFile);
				} */
		 System.out.println("App anme is : "+appname);
		 
		 String file_path = ".\\Results\\"+appname+"Final Report.xls";
		 System.out.println("10 - File present");
			 System.out.println("Creating WorkBook");
		Workbook wb = Workbook.getWorkbook(new File(file_path));
		Sheet sh = wb.getSheet(0);
		int totalNoOfRows = sh.getRows();
		int totalNoOfCols = sh.getColumns();
		Total_Cases = totalNoOfRows - 1;
		System.out.println("Total Rows and cols "+totalNoOfRows+" "+totalNoOfCols);
		

		// Failed, Passed and Critical_Error_Counter and Critical_Counter
		 System.out.println("10");
		for (int i = 1; i < totalNoOfRows; i++) {
			for (int j = 0; j < totalNoOfCols; j++) {
				if (sh.getCell(j, i).getContents().equalsIgnoreCase("PASS")) {
					Passed_Case_Counter = Passed_Case_Counter + 1;
				} else if (sh.getCell(j, i).getContents().equalsIgnoreCase("FAIL")) {
					Failed_Case_Counter = Failed_Case_Counter + 1;
				} else if (sh.getCell(j, i).getContents().equalsIgnoreCase("CRITICAL")) {
					Critical_Error_Counter = Critical_Error_Counter + 1;
				}
				if(j==3){
					if (sh.getCell(j, i).getContents().equalsIgnoreCase("FAIL")&& sh.getCell(j+1, i).getContents().equalsIgnoreCase("CRITICAL") ) {
						Critical_Counter = Critical_Counter + 1;
				}
			}

		}
		}
		System.out.println("Failed, Passed and Critical_Error_Counter and Critical_Counter "+Failed_Case_Counter+" "+Passed_Case_Counter+" "+Critical_Error_Counter+" "+Critical_Counter);
	
		 System.out.println("11");
		//CertifyingAs
		if (Critical_Counter > 0) {
			CertifyingAs = "<font face='Calibri' size='12px' color='Red'><b>No-Go</b></font>";
		} else {
			CertifyingAs = "<font face='Calibri' size='12px' color='Green'><b>Go</b></font>";
		}
		 System.out.println("12");
		// Body Content
		html = "<HTML><BODY> <font face='Calibri' size='12px'>Hello Team, " + "<br><br>" + "Please find the "
				+ appname + " Automated Regression Test Report below" + " <br> <br>" + "Certifying as "
				+ CertifyingAs + "<br><br> URL: " + appurl + "<br> Date: " + cal2 + " </font><br><br>";
		
		 System.out.println("13");
		// Summary Table
		html += "<font face='Calibri' size='10px'><b>Summary</b></font><br><TABLE border='1' style='border-collapse:collapse' cellpadding '5' cellspacing='0'>";
		html += "<tr><TH bgcolor='#396992' align='left'>&nbsp<font face='calibri' size='3' color='White'>&nbsp<b>Total Cases Executed</b>&nbsp</font></th>";
		html += "<TH bgcolor='#396992' align='left'>&nbsp<font face='calibri' size='3' color='White'>&nbsp<b>Total Cases Passed</b>&nbsp</font></th>";
		html += "<TH bgcolor='#396992' align='left'>&nbsp<font face='calibri' size='3' color='White'>&nbsp<b>Total Cases Failed</b>&nbsp</font></th>";
		html += "<TH bgcolor='#396992' align='left'>&nbsp<font face='calibri' size='3' color='White'>&nbsp<b>Total Critical Errors</b>&nbsp</font></th></tr>";
		html += "<tr><Td bgcolor='Whitesmoke'>&nbsp<font face='calibri' size='2.5'>&nbsp " + Total_Cases
				+ " &nbsp</font></td>";
		html += "<Td bgcolor='Whitesmoke'>&nbsp<font face='calibri' size='2.5'>&nbsp " + Passed_Case_Counter
				+ " &nbsp</font></td>";
		html += "<Td bgcolor='Whitesmoke'>&nbsp<font face='calibri' size='2.5'>&nbsp " + Failed_Case_Counter
				+ " &nbsp</font></td>";
		html += "<Td bgcolor='Whitesmoke'>&nbsp<font face='calibri' size='2.5'>&nbsp " + Critical_Error_Counter
				+ " &nbsp</font></td>";
		html += "</tr></Table><br><br>";
		 System.out.println("14");
		// Split-Up Table
		html += "<font face='Calibri' size='10px'><b>Split-up</b></font><br><TABLE border='1' bordercolorlight='DimGray' cellpadding'5' cellspacing='0'>";
		html += "<TR>";
		for (int i = 0; i < totalNoOfRows; i++) {
			

			for (int j = 0; j < totalNoOfCols; j++) {
				if (i == 0) {
					color = "#396992";
					html += "<TH bgcolor=" + color
							+ " align='left'>&nbsp<font face='calibri' size='3' color='White'>&nbsp<b>"
							+ sh.getCell(j, i).getContents().toString() + "</b>&nbsp</font></th>";
				} else {

					if (sh.getCell(j, i).getContents().equalsIgnoreCase("PASS")) {
						color = "#81F781";
					} else if (sh.getCell(j, i).getContents().equalsIgnoreCase("FAIL")) {
						color = "#F78181";
					} else {
						color = "Whitesmoke";

					}
					html += "<Td bgcolor=" + color + ">&nbsp<font face='calibri' size='2.5'>&nbsp "
							+ sh.getCell(j, i).getContents().toString() + " &nbsp</font></td>";
				}
			}
			html += "</TR>";
		}
		 System.out.println("15");
		//Tail
		html +="</TABLE> <br><br><font face='Calibri' size='10px'><b>Note:</b><i>  Don't reply to all. This is a auto-generated report.</i></b><br> <br> Thanks & Regards <br>"+ System_User_Name +"<br></Font></BODY></HTML>";     
		
		 System.out.println("16");
		 wb.close();
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("Some problem has occured" + e.getMessage());

	}
		System.out.println("returned html");
	return html;
}


	
	
	
	
	/*@Parameters({"hubUrl","datasheetPath","Sheet_Name","selectedApp","baseUrl","mail_app","errorSheet"})
	@BeforeTest()
	public void setUp(String hubUrl, String datasheetPath, String Sheet_Name, String selectedApp, String baseUrl, String mail_app, String errorSheet) throws Exception
	{
		try{
		System.out.println("@BeforeTest Started");
		File  fis = new File(datasheetPath);
		Workbook wb = Workbook.getWorkbook(fis);
	    Sheet s = wb.getSheet("AppConfig");
	    Hashtable<String, String> AppConfig = new Hashtable<String, String>();
	    
	    AppConfig.put(s.getCell(0,0).getContents(), s.getCell(0,1).getContents());
        System.out.println("App_URL "+AppConfig.get("App_URL"));
        AppConfig.put(s.getCell(3,0).getContents(), s.getCell(3,1).getContents());
        System.out.println("BrowserName "+AppConfig.get("BrowserName"));
       
        AppConfig.put(s.getCell(1,0).getContents(), s.getCell(1,1).getContents());
        System.out.println(AppConfig.get(s.getCell(1,0).getContents()));
        
        AppConfig.put(s.getCell(2,0).getContents(), s.getCell(2,1).getContents());
        System.out.println(AppConfig.get(s.getCell(2,0).getContents()));
	        
        if(baseUrl.contains("http"))
        {
        	 if(AppConfig.get("BrowserName").toString().toUpperCase().contentEquals("FIREFOX"))
			    {
	        		 System.out.println(hubUrl);
	        		 System.out.println("setUp");
	        		 ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<RemoteWebDriver>();
	        		 System.out.println("0");
	        		 DesiredCapabilities caps = DesiredCapabilities.firefox(); 
	        		 System.out.println("1");
	        		 caps.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
	        		 System.out.println("2");
	        		 caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
	        		 System.out.println("3");
	        		 threadDriver.set(new RemoteWebDriver(new URL(hubUrl), caps));
	        		 System.out.println("4");
	        		 driver = threadDriver.get();
	        		 System.out.println("5");
	        		 driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	        		 System.out.println("6");
	        		 driver.manage().deleteAllCookies();
	        		 System.out.println("7");
	        		 driver.manage().window().maximize();
	        		 System.out.println("beforetest");
			    }
        	 else if(AppConfig.get("BrowserName").toString().toUpperCase().contentEquals("INTERNETEXPLORER"))
			    {
        		 	 Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
	        		 System.out.println(hubUrl);
	        		 System.out.println("setUp");
	        		 ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<RemoteWebDriver>();
	        		 System.out.println("0");
	        		 DesiredCapabilities caps = DesiredCapabilities.internetExplorer(); 
	        		 System.out.println("1");
	        		 caps.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
	        		 System.out.println("2");
	        		caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
	        		
	        		caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
	        		 System.out.println("3");
	        		 caps.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
	        		 System.out.println("4");
	        		 //caps.setCapability(InternetExplorerDriver.NATIVE_EVENTS, true);
	        		 System.out.println("5");
	        		 caps.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
	        		 System.out.println("6");
	        		 //caps.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS , false);
	        		 //caps.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR , UnexpectedAlertBehaviour.ACCEPT);
	        		 System.out.println("8");
	        		 threadDriver.set(new RemoteWebDriver(new URL(hubUrl), caps));
	        		 System.out.println("9");
	        		 driver = threadDriver.get();
	        		 System.out.println("10");
	        		 driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
	        		 System.out.println("11");
	        		 driver.manage().deleteAllCookies();
	        		 System.out.println("12");
	        		 driver.manage().window().maximize();
			    }
        }
        if(baseUrl.contains("http")){
 				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
 				System.out.println("datasheetPath ");
 				File  fis1 = new File(datasheetPath);
 				Workbook wb1 = Workbook.getWorkbook(fis1);
 				Sheet s1 = wb1.getSheet("AppConfig");
 	        	driver.get(AppConfig.get("App_URL"));
 	        	System.out.println(AppConfig.get("App_URL")+" Launched");
 	        }
        	 System.out.println("@BeforeTest Ends");
        
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
*/	
	@AfterSuite
	public void tearDown() throws IOException{
		try{
			System.out.println("In tearDown");
		  driver.quit();
		  Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
/*	@Parameters({"Sheet_Name","datasheetPath","selectedApp","baseUrl","mail_app","hubUrl", "errorSheet"})
	@Test
	public void testActionSimulator(String Sheet_Name, String datasheetPath, String selectedApp, String baseUrl, String mail_app, String hubUrl, String errorSheet) throws BiffException, IOException, RowsExceededException, WriteException {
		try{
			
			if(baseUrl.contains("http"))
				driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			System.out.println("datasheetPath ");
			File  fis = new File(datasheetPath);
			Workbook wb = Workbook.getWorkbook(fis);
		    Sheet s = wb.getSheet("AppConfig");
		    Hashtable<String, String> AppConfig = new Hashtable<String, String>();
		  
		    AppConfig.put(s.getCell(0,0).getContents(), s.getCell(0,1).getContents());
	        System.out.println(AppConfig.get(s.getCell(0,0).getContents()));
	        
	        AppConfig.put(s.getCell(1,0).getContents(), s.getCell(1,1).getContents());
	        System.out.println(AppConfig.get(s.getCell(1,0).getContents()));
	        
	        AppConfig.put(s.getCell(2,0).getContents(), s.getCell(2,1).getContents());
	        System.out.println(AppConfig.get(s.getCell(2,0).getContents()));
	        
	        AppConfig.put(s.getCell(3,0).getContents(), s.getCell(3,1).getContents());
	        System.out.println(AppConfig.get(s.getCell(3,0).getContents()));
		    
	        if(baseUrl.contains("http")){
	        	driver.get(baseUrl);
	        	System.out.println(baseUrl+" Launched");
	        }
	        
		  if(AppConfig.get("App_URL").toString().contains("http"))
		  {
			  System.out.println("Login sheet is being executed");
			  Sheet sheet_Login= wb.getSheet("Login");
			  Teststatus = "PASS";
			  for(int i=1;i<sheet_Login.getRows();i++) 
			  {
				  try{
				  fnSimulator(errorSheet, AppConfig.get("App_URL").toString(),selectedApp, "Node0", "Login", sheet_Login.getCell(0,i).getContents(),sheet_Login.getCell(1,i).getContents(),sheet_Login.getCell(2,i).getContents(),sheet_Login.getCell(3,i).getContents(), sheet_Login.getCell(4,i).getContents(), sheet_Login.getCell(5,i).getContents(), sheet_Login.getCell(6,i).getContents(), sheet_Login.getCell(7,i).getContents(), sheet_Login.getCell(8,i).getContents(), sheet_Login.getCell(9,i).getContents(), sheet_Login.getCell(10,i).getContents(),sheet_Login.getCell(11,i).getContents());
				  }catch(Exception e)
				  {
					  System.out.println(e.getMessage());
				  }
			  }
		  }
			 System.out.println(Sheet_Name+" is being executed");
		   Sheet s3= wb.getSheet(Sheet_Name);
		  for(int i=1;i<s3.getRows();i++) 
		  {
			  try{
			  fnSimulator(errorSheet, baseUrl, selectedApp, "Node0",Sheet_Name, s3.getCell(0,i).getContents(),s3.getCell(1,i).getContents(),s3.getCell(2,i).getContents(),s3.getCell(3,i).getContents(), s3.getCell(4,i).getContents(), s3.getCell(5,i).getContents(), s3.getCell(6,i).getContents(), s3.getCell(7,i).getContents(), s3.getCell(8,i).getContents(), s3.getCell(9,i).getContents().toString(), s3.getCell(10,i).getContents(),s3.getCell(11,i).getContents());
			  
			  
			  }catch(Exception e)
			  {
				  System.out.println(e.getMessage());
			  }
		  }
		  wb.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	

	public void fnSimulator(String errorSheet, String App_URL, String selectedApp, String Node_Name, String SheetName, String Step_No, String TC_Name, String Element_Name, String Element_Property, String Element_Property_Value, String Action, String Element_Input_Value, String ChkElement_Property, String ChkElement_Property_Value, String Report, String ActionIfNotFound, String ErrorType) throws IOException, AWTException, InterruptedException, BiffException, RowsExceededException, WriteException, SQLException, ClassNotFoundException
	  {
		  try {
			
			System.out.println("Step_No"+Step_No);
			  if(App_URL.contains("http"))
				  driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			  String xPath = null;
			  String[] PropArray, PropValArray;
			  if(Element_Property.toUpperCase().contentEquals("XPATH"))
			  {
				  xPath = Element_Property_Value;
			  }
			  else
			  {
				  if(Element_Property.contains(",,"))
				  {
					  PropArray = Element_Property.split(",,");
					  PropValArray = Element_Property_Value.split(",,");
					  xPath = "//*[";
					  for(int i = 0; i < PropArray.length; i++)
					  {
						  if(i>0)
							  xPath = xPath + " and ";
						  xPath = xPath + "@"+PropArray[i] + "='" + PropValArray[i] + "'";
					  }
					  xPath = xPath + "]";
				  }
				  else
					  xPath = "//*[@"+Element_Property+"='"+Element_Property_Value+"']";
			  }
			  boolean TC_Exec_Status;
			  switch(Action.toUpperCase())
			  {
			  case "UPDATE_QUERY":
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
				conn1 = (Connection) DriverManager.getConnection("jdbc:sqlserver://"+Element_Property_Value);
				stmt1 = (Statement) ((java.sql.Connection) conn1).createStatement();
				stmt1.executeUpdate(Element_Input_Value);
				conn1.commit();
				status = "info";
					Teststatus = "PASS";
					stepinfo = TC_Name+" Update query executed successfully "+Element_Input_Value;
					conn1.close();
					stmt1.close();
			break;
			case "REPORT_265":
					TC_Exec_Status = false;
					Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
					conn = (Connection) DriverManager.getConnection("jdbc:sqlserver://"+Element_Property_Value);
					stmt = (Statement) ((java.sql.Connection) conn).createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					System.out.println(store.get(ChkElement_Property_Value));
					String query1 = "CREATE TABLE #tmpBus (COL1 VARCHAR(MAX))";
					String query2 = "INSERT INTO #tmpBus exec dexsp_assignment_results "+store.get("PK")+", 1";
					String sql = "SELECT * FROM #tmpBus";
					stmt.addBatch(query1);
					stmt.addBatch(query2);
					stmt.executeBatch();
					conn.commit();
					rs = stmt.executeQuery(sql);
					int counter = 0;
					if(Element_Input_Value.trim().toUpperCase().contentEquals("ASSIGNED"))
					{
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("#Unassigned Jobs: 0"))
							{
								TC_Exec_Status = true;
							}
						}
					}
					else if(Element_Input_Value.trim().toUpperCase().contentEquals("UNASSIGNED"))
					{
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("#Unassigned Jobs: 1"))
							{
								TC_Exec_Status = true;
							}
						}
					}
					else if(Element_Input_Value.trim().toUpperCase().contentEquals("SAMEBUILDING_STEP_VALIDATION"))
					{
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("-- -- Job:") && val.contains("Step Asgnd: Same Building"))
							{
								counter++;
							}
						}
						rs.beforeFirst();
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("#Jobs: "+counter))
							{
								TC_Exec_Status = true;
							}
						}
					}
					else if(Element_Input_Value.trim().toUpperCase().contentEquals("DAA_LIST_RULE_RESTRICT_PRIMARY_DAAS_STEP_VALIDATION"))
					{
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("DAA List Rule (Restrict to Primary DAAs)"))
							{
								TC_Exec_Status = true;
							}
						}
					}
					else if(Element_Input_Value.trim().toUpperCase().contentEquals("GENERAL_TABU_STEP_VALIDATION"))
					{
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("Step Asgnd: General Tabu"))
							{
								TC_Exec_Status = true;
							}
						}
					}
					else if(Element_Input_Value.trim().toUpperCase().contentEquals("DAA_LIST_RULE_RESTRICT_PRIMARY_DAAS_WHEN_SPECIFIED_STEP_VALIDATION"))
					{
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("DAA List Rule (Restrict to Primary DAAs when specified)"))
							{
								TC_Exec_Status = true;
							}
						}
					}
					else if(Element_Input_Value.trim().toUpperCase().contentEquals("POLYGON_LIST_RULE_RESTRICT_PRIMARY_POLYGONS_STEP_VALIDATION"))
					{
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("Polygon List Rule (Restrict to Primary Polygons)"))
							{
								TC_Exec_Status = true;
							}
						}
					}
					else if(Element_Input_Value.trim().toUpperCase().contentEquals("APPT_WINDOW_VERIFICATION_STRICT_STEP_VALIDATION"))
					{
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("Appt Window verification (Strict)"))
							{
								TC_Exec_Status = true;
							}
						}
					}
					else if(Element_Input_Value.trim().toUpperCase().contentEquals("DAA_LIST_RULE_RESTRICT_PRIMARY_DAAS_STEP_VALIDATION"))
					{
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("DAA List Rule (Restrict to Primary DAAs)"))
							{
								TC_Exec_Status = true;
							}
						}
					}
					else if(Element_Input_Value.trim().toUpperCase().contentEquals("DAA_LIST_RULE_RESTRICT_PRIMARY_DAAS_WHEN_SPECIFIED_STEP_VALIDATION"))
					{
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("DAA List Rule (Restrict to Primary DAAs when specified)"))
							{
								TC_Exec_Status = true;
							}
						}
					}
					else if(Element_Input_Value.trim().toUpperCase().contentEquals("POLYGON_LIST_RULE_RESTRICT_PRIMARY_POLYGONS_STEP_VALIDATION"))
					{
						while(rs.next())
						{
							String val = rs.getString(1);
							System.out.println(val);
							if(val.contains("Polygon List Rule (Restrict to Primary Polygons)"))
							{
								TC_Exec_Status = true;
							}
						}
					}
					if(TC_Exec_Status){
						status = "info";
					Teststatus = "PASS";
					stepinfo = TC_Name+" Test case has Passed. Plan PK ID # "+store.get("PK");
						
					}else{
						status = "error";
						Teststatus = "FAIL";
						stepinfo = TC_Name+" Test case has failed. Plan PK ID # "+store.get("PK");
						
					}
					sql = "DROP TABLE #tmpBus";
					stmt.executeUpdate(sql);
					conn.commit();
					conn.close();
					stmt.close();
				break;
				case "SOAP":
					System.out.println("SOAP");
					URL URL = new URL(ChkElement_Property);
					HttpURLConnection connection = (HttpURLConnection) URL.openConnection();
					connection.setRequestMethod("POST");
					connection.setDoOutput(true);
					connection.setRequestProperty("Content-type", "text/xml; charset=utf-8");
					connection.setRequestProperty("SOAPAction", "");
					 String xmlInput = Element_Input_Value;
					 System.out.println(xmlInput);
					 
						OutputStream out = connection.getOutputStream();
						out.write(xmlInput.getBytes());       
						InputStream is = connection.getInputStream();
						BufferedReader in = new BufferedReader(new InputStreamReader(is));
						String line;
						StringBuffer response = new StringBuffer();
						while ((line = in.readLine()) != null) {
							response.append(line);
						//	response.append('\r');
						}
						in.close();
						String res = response.toString();
						//System.out.println(res);
						String Response = res.replaceAll("<", "<");
						String Response2 = Response.replaceAll(">", ">");
					    Response=Response2.replaceAll("&quot;", "\"");
				        Response2=Response.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
				        Response=Response2.replace("<?xml version=\"1.0\" encoding=\"utf-8\"?>","");
				        Response2 = Response.replaceAll("&lt;", "<");
				        Response = Response2.replaceAll("&gt;", ">");
						System.out.println(Response);
						String[] pk = Response.split("<pk>");
						String[] pk1 = pk[1].split("</pk>");
						System.out.println("pk value is "+pk1[0]);
						store.put("PK", pk1[0]);
						status = "info";
					Teststatus = "PASS";
					stepinfo = TC_Name+" SOAP Response is received. Plan PK ID # "+store.get("PK");
				break;
			    case "SOAP_VERIFY":
			    	 System.out.println("SOAP_VERIFY");
			    	String soap_res = store.get("Response");
			    	String[] soapArr, soapArr1;
			    	soapArr = soap_res.split("<"+Element_Property_Value+">");
			    	soapArr1 = soapArr[1].split("<");
			    	System.out.println(soapArr1[0]);
			    	if(soapArr1[0].contentEquals(ChkElement_Property_Value)){
			    		status = "info";
						Teststatus = "PASS";
						stepinfo = SheetName+" SOAP Response is received.  "+Element_Property_Value + " = "+ ChkElement_Property_Value;
						System.out.println("PASS");
			    	}
			    	else{
			    		status = "error";
						Teststatus = "FAIL";
						stepinfo = SheetName+" SOAP Response is received.  "+Element_Property_Value + " NOT EQUALS "+ ChkElement_Property_Value;
						System.out.println("FAIL");
			    	}
			    	System.out.println(stepinfo);
			    break;
			  	case "MOVETO":
			  		Actions actions1 = new Actions(driver);
			  		WebElement ele = driver.findElement(By.xpath(xPath));
			  		actions1.contextClick(ele).build().perform();
			  		
			  		JavascriptExecutor js = (JavascriptExecutor) driver; 
			  		WebElement ele = driver.findElement(By.xpath(xPath));
			  		js.executeScript("arguments[0].click();", ele);
			  		
			  		WebElement ele = driver.findElement(By.xpath(xPath));
			  		JavascriptExecutor js = (JavascriptExecutor) driver; 
			  		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}"; 
			  		js.executeScript(mouseOverScript, ele);
			  		System.out.println("MOVETO");
					//driver.findElement(By.xpath(Element_Input_Value)).click();
					
					WebElement ele = driver.findElement(By.xpath(xPath));
					Actions actions1 = new Actions(driver);
					actions1.moveToElement(ele).perform();
					
					Robot robot1 = new Robot(); 
			    	Point coordinates = driver.findElement(By.xpath(xPath)).getLocation();
			    	((JavascriptExecutor) driver).executeScript("window.focus();");
			    	robot1.mouseMove(coordinates.getX()+20,coordinates.getY()+160);
			  		Robot r = new Robot();
			  		r.mouseMove(1,1); 
			  		System.out.println("xPath "+xPath);
			  		WebElement element_move = driver.findElement(By.xpath(xPath));
			  		Locatable hoverItem = (Locatable) element_move;
			  		Mouse mouse = ((RemoteWebDriver) driver).getMouse();
			  		mouse.mouseMove(hoverItem.getCoordinates());
			  	break;
			  	case "RIGHT_CLICK":
			  		Actions actions_RightClick = new Actions(driver);
			  		WebElement ele_RightClick = driver.findElement(By.xpath(xPath));
			  		actions_RightClick.contextClick(ele_RightClick).build().perform();
			  		System.out.println("RIGHT_CLICK");
			  	break;
			  	case "MINIMIZE_WINDOW":
			  		driver.manage().window().setPosition(new Point(-2000, 0));
				break;
			  	case "LAUNCH_URL":
			  		driver.get(Element_Input_Value);	
					//dr.manage().window().maximize();
					new WebDriverWait(driver, 30);
					break;
				case "DRAGANDDROP":
					try {
					status = "info";
					Teststatus = "PASS";
					//appselection.TxtAreaStep.setText("Executing Sheet '"+SheetName+"' - Waiting for - Step "+Step_No+": "+Element_Name+" ("+xPath+")");
					stepinfo = "Executing Sheet '"+SheetName+"' - Step "+Step_No+": "+Element_Name+" (From Source "+Element_Property+"="+Element_Property_Value+") "+Action+" to Target "+Element_Input_Value ;					        			
					//appselection.TxtAreaStep.setText(stepinfo);	
					Actions actions = new Actions(driver);
					WebElement source = driver.findElement(By.xpath(xPath));
					WebElement target = driver.findElement(By.xpath(Element_Input_Value));
					actions.dragAndDrop(source, target).build().perform();
				} catch (Exception e) {
					if(!Action.toUpperCase().equals("DRAGANDDROPIFEXIST")){
						status = "error";
						Teststatus = "FAIL";
						stepinfo = Element_Name+" (From Source "+Element_Property+"="+Element_Property_Value+") "+Action+" to Target "+Element_Input_Value+" has not been  DRAGANDDROP" + e.getMessage();
					//appselection.TxtAreaStep.setText(stepinfo);
					}else {
						Teststatus = "PASS";
						status = "warning";
						stepinfo = "info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
			        		//appselection.TxtAreaStep.setText(stepinfo);
					}
				 }
				Thread.sleep(500);
				ErrDesc = stepinfo;
				System.out.println(stepinfo);
				break;
			  	case "SET": case "SETIFEXIST":
			  		try
			  		{
			  			waitUntilElementPresent(selectedApp, Node_Name, xPath, Action);
			  			if (driver.findElement(By.xpath(xPath)).getTagName().toUpperCase().equals("TEXTAREA")) 
			  			{					        			
			    			WebElement element = driver.findElement((By.xpath(xPath)));
			    			element.clear();
			    			((JavascriptExecutor)driver).executeScript("arguments[0].value = arguments[1];", element, Element_Input_Value);
			    			stepinfo = " ("+Element_Property+"="+Element_Property_Value+") value entered with given XML";
			    		}
			  			else
			  			{
			  				WebElement element = driver.findElement((By.xpath(xPath)));
					  		element.clear();
					  		element.sendKeys(Element_Input_Value);
					  		stepinfo = " ("+Element_Property+"="+Element_Property_Value+") value entered as "+Element_Input_Value+")";
			  			}
			  			status = "info";
				  		Teststatus = "PASS";
			  		}
			  		catch(Exception e)
			  		{
			  			if(!Action.toUpperCase().equals("SETIFEXIST")){
						status = "error";
						Teststatus = "FAIL";
						stepinfo = " ("+Element_Property+"="+Element_Property_Value+") to set value as "+Element_Input_Value + " "+ e.getMessage();
						stepinfo = "Step "+Step_No+": error : " + e.getMessage();
			    			//appselection.TxtAreaStep.setText(stepinfo);
					}else {
						Teststatus = "PASS";
						status = "warning";
						stepinfo = ": info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
			    			//appselection.TxtAreaStep.setText(stepinfo);
					}
			  		}
			  		finally{
			  			System.out.println(stepinfo);
			  		}
			  		Thread.sleep(500);
				ErrDesc = stepinfo;
			  		break;
			  	case "CLICK": case "CLICKIFEXIST":
				try
				{ 
					status = "info";
					Teststatus = "PASS";
					//waitUntilElementPresent(selectedApp, Node_Name, xPath, Action);
					stepinfo = "Executing Sheet '"+SheetName+"' - Step "+Step_No+": "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") "+Action+" "+Element_Input_Value + " clicked";
				  		WebElement element = driver.findElement((By.xpath(xPath)));
				  		element.click();
				  		Teststatus = "PASS";
				}
				catch(Exception e)
			  		{
					if(!Action.toUpperCase().equals("CLICKIFEXIST")){
						status = "error";
						Teststatus = "FAIL";
						stepinfo = Element_Name+" ("+Element_Property+"="+Element_Property_Value+") has not been selected " + e.getMessage();
						//appselection.TxtAreaStep.setText(stepinfo);
					}else {
						Teststatus = "PASS";
						status = "warning";
						stepinfo = "info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
			        		//appselection.TxtAreaStep.setText(stepinfo);
					}
				 }
				finally{
					System.out.println(stepinfo);
				}
				Thread.sleep(500);
				ErrDesc = stepinfo;
			  	break;
			  	case "DRAW":
				status = "info";
				Teststatus = "PASS";
				g.drawString(Element_Input_Value, 15, 15);
				stepinfo = "Draw is done for " + Element_Input_Value;
				ErrDesc = stepinfo;
				break;
			  	case "SUBMIT": case "SUBMITIFEXIST":
				try
				{
					waitUntilElementPresent(selectedApp, Node_Name, xPath, Action);
				  		WebElement element = driver.findElement((By.xpath(xPath)));
				  		element.submit();
				  		Teststatus = "PASS";
				}
				catch(Exception e)
			  		{
					Teststatus = "FAIL";
					log(selectedApp, Node_Name, "error",e.getMessage());
			  			if(!Action.toUpperCase().contains("IFEXIST"))
			  			{
			  				break;
			  			}
			  		}
				finally{
					System.out.println(stepinfo);
				}
			  		break;
			  	case "DBLCLICK": case "DBLCLICKIFEXIST":
				try 
				{
					status = "info";
					Teststatus = "PASS";
					waitUntilElementPresent(selectedApp, Node_Name, xPath, Action);
					stepinfo = "Executing Sheet '"+SheetName+"' - Step "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") "+Action+" "+Element_Input_Value;
					Actions doubleClick = new Actions(driver).doubleClick(driver.findElement(By.xpath(xPath)));
					doubleClick.build().perform();
				}
				catch(Exception e)
			  		{
					if(!Action.toUpperCase().equals("DBLCLICKIFEXIST")){
						status = "error";
						Teststatus = "FAIL";
					 //el.log("error","Error Executing Step "+stepNo+": "+ElementName+" ("+ElementProperty+"="+Element_Property_Value+") has not been selected.");
					 //el.log("error","Error Desc: "+e.getMessage());
					 stepinfo = "error : Error Executing Step "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") has not been selected." + e.getMessage();
					 //appselection.TxtAreaStep.setText(stepinfo);
					} else {
						Teststatus = "PASS";
						status = "warning";
						 log(selectedApp, Node_Name, "warning","Element not found Step "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") has not been selected.Error IGNORED as its "+Action.toUpperCase()+" action");
						 stepinfo = "Element not found Step "+Step_No+": "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") has not been selected.Error IGNORED as its "+Action.toUpperCase()+" action info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
			    			 //appselection.TxtAreaStep.setText(stepinfo);
					}
				 }
				finally{
					System.out.println(stepinfo);
				}
				Thread.sleep(500);
				ErrDesc = stepinfo;
			  		break;
			  	case "SELECT": case "INDEX": case "SELECTIFEXIST": case "INDEXIFEXIST": case "SELECTBYVISIBLETEXT":
			  		status = "info";
			  		Teststatus = "PASS";
			  		try
			  		{
			  			//waitUntilElementPresent(selectedApp,Node_Name, xPath, Action);
			  			Select combobox = new Select(driver.findElement(By.xpath(xPath)));
			  			if (Action.toUpperCase().equals("INDEX"))
			  			{
			  				combobox.selectByIndex(Integer.parseInt(Element_Input_Value));
			  			}
			  			else if(Action.toUpperCase().equals("SELECTBYVISIBLETEXT")) 
			  			{
			  				combobox.selectByVisibleText(Element_Input_Value);
			  			}
			  			else
			  			{
			  				combobox.selectByValue(Element_Input_Value.toString());
			  			}
			  			stepinfo = Element_Name+" ("+Element_Property+"="+Element_Property_Value+") selected with value '"+Element_Input_Value+"'";
			  		}
			  		catch(Exception e)
			  		{
						 if(!Action.toUpperCase().contains("IFEXIST"))
						 {
							 status = "error";
							 Teststatus = "FAIL";
							 stepinfo = "Error Executing Step "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") to select value as '"+Element_Input_Value+"' "+e.getMessage();
			   				//appselection.TxtAreaStep.setText(stepinfo);
						 } 
						 else 
						 {
						 Teststatus = "PASS";
						 status = "warning";
						 stepinfo = "Element not found Step "+Step_No+": "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") to select value as '"+Element_Input_Value+"'.Error IGNORED as its "+Action.toUpperCase()+" action"+ e.getMessage();
			   			 //appselection.TxtAreaStep.setText(stepinfo);
						 }
			  		}
			  		finally{
			  			System.out.println(stepinfo);
			  		}
			  		Thread.sleep(500);
			  		ErrDesc = stepinfo;
			  		break;
			  	case "FRAMECHANGE": case "FRAMECHANGEIFEXIST":
				 try
				 {
					 status = "info";
					 Teststatus = "PASS";
					 if (Element_Property_Value.toUpperCase().equals("DEFAULT"))
					 {
						 driver.switchTo().defaultContent();
					 }
					 else
					 {
						driver.switchTo().frame(Element_Property_Value);
						Thread.sleep(1000);
					 }
					stepinfo = "Frame pointer changed to "+Element_Property_Value;
				 }
				catch(Exception e)
				{
					 if(!Action.toUpperCase().equals("FRAMECHANGEIFEXIST")){
						 status = "error";
						 Teststatus = "FAIL";
						 stepinfo = "Error Executing Step "+Step_No+": Frame pointer not changed to "+Element_Property_Value+" error : " + e.getMessage();
					//appselection.TxtAreaStep.setText(stepinfo);
					 } else {
						 Teststatus = "PASS";
						 status = "warning";
						 stepinfo = "Step "+Step_No+": info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
					 //appselection.TxtAreaStep.setText(stepinfo);
					 }
				}
				finally{
					System.out.println(stepinfo);
				}
				ErrDesc = stepinfo;
				break;
			  	case "CHKBOX": case "CHKBOXIFEXIST":
				try
				{ 	
					status = "info";
					Teststatus = "PASS";
					waitUntilElementPresent(selectedApp,Node_Name, xPath, Action);
					if (Element_Input_Value.toUpperCase().equals("ON"))
					{
						if (!driver.findElement(By.xpath(xPath)).isSelected())
						{
							driver.findElement(By.xpath(xPath)).click();
			    			}
					} 
					else 
					{
						if (driver.findElement(By.xpath(xPath)).isSelected())
						{
							driver.findElement(By.xpath(xPath)).click();
			    			}	
					}
					stepinfo = Element_Name+" ("+Element_Property+"="+Element_Property_Value+") value set to '"+Element_Input_Value+"'";
				}
				catch(Exception e)
				{
						 if(!Action.toUpperCase().equals("CHECKIFEXIST"))
						 {
							 status = "error";
							 Teststatus = "FAIL";
							 stepinfo = "Error Executing Step "+Step_No+": "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") to set value as '"+Element_Input_Value+"'. Error IGNORED as its "+Action.toUpperCase()+" action" +  e.getMessage();
			        		 //appselection.TxtAreaStep.setText(stepinfo);
						 } 
						 else 
						 {
							 status = "warning";
							 Teststatus = "PASS";
							 stepinfo = "Element not found. Step "+Step_No+": "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") to set value as '"+Element_Input_Value+"' "+ e.getMessage(); 
							 //appselection.TxtAreaStep.setText(stepinfo);
						 }
				}
				finally{
					System.out.println(stepinfo);
				}
				Thread.sleep(500);
			 	ErrDesc = stepinfo;
				break;
			  	case "VERIFY": case "VERIFYIFEXIST":
			  		status = "info";
					Teststatus = "PASS";
					ErrDesc ="Element: "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") Expected: "+ChkElement_Property+" = "+ChkElement_Property_Value;
				stepinfo = "Executing Sheet '"+SheetName+"' - "+ ErrDesc;
			  		if (ChkElement_Property.toUpperCase().equals("TAGTEXT"))
			  		{
					try 
					{
			    			if (!driver.getPageSource().contains(">"+ChkElement_Property_Value.toUpperCase()+"<") && !driver.getPageSource().contains(">"+ChkElement_Property_Value.toLowerCase()+"<") && !driver.getPageSource().contains(">"+ChkElement_Property_Value+"<")) 
			    			{	
			    				status = "error";
			    				Teststatus = "FAIL";
			        			ErrDesc = ">"+ChkElement_Property_Value.toUpperCase()+"<" + " not found";
			        			stepinfo = ErrDesc;
			    			}
			    			else if (driver.getPageSource().contains(">"+ChkElement_Property_Value.toUpperCase()+"<") && driver.getPageSource().contains(">"+ChkElement_Property_Value.toLowerCase()+"<") && driver.getPageSource().contains(">"+ChkElement_Property_Value+"<"))
			    			{
			    				status = "info";
			    				Teststatus = "PASS";
			    				ErrDesc = ">"+ChkElement_Property_Value.toUpperCase()+"<"+" Expected value found";
			    				stepinfo = ErrDesc;
			    				//el.log("info",ErrDesc);
			    			}
					}
					catch (NoSuchElementException e) 
					{
						if(!Action.toUpperCase().equals("VERIFYIFEXIST"))
						{
							status = "error";
			    				Teststatus = "FAIL";	  
			        			ErrDesc = "Expected value not found";
			        			ErrDesc = "Step "+Step_No+">"+ChkElement_Property_Value.toUpperCase()+"<" + " not found";
			        			stepinfo = "Error Executing Step "+Step_No+": Expected : "+ChkElement_Property+" = "+ChkElement_Property_Value+" Expected value not found"+": error : " + e.getMessage();
			    				//appselection.TxtAreaStep.setText(stepinfo);
						} 
						else 
						{
							Teststatus = "PASS";
							status = "warning";
							stepinfo = "Step "+Step_No+": info : Element not found.Ignoring step as its IFEXIST action Element: "+Element_Property_Value+ e.getMessage();
			        			//appselection.TxtAreaStep.setText(stepinfo);
						}
			    		}finally{
				  			System.out.println(stepinfo);
				  		}
			  		}
			  		
			  		else if(ChkElement_Property.toUpperCase().equals("SPECIFICTAGTEXT")) 
				{
					try 
					{
						if (!driver.getPageSource().contains("<"+Element_Input_Value+">"+ChkElement_Property_Value.toUpperCase()+"</"+Element_Input_Value+">") && !driver.getPageSource().contains("<"+Element_Input_Value+">"+ChkElement_Property_Value.toLowerCase()+"</"+Element_Input_Value+">") && !driver.getPageSource().contains("<"+Element_Input_Value+">"+ChkElement_Property_Value+"</"+Element_Input_Value+">")) 
			    			{
							status = "error";
							Teststatus = "FAIL";	
							ErrDesc = "Expected : "+"<"+Element_Input_Value+">"+ChkElement_Property_Value.toUpperCase()+"</"+Element_Input_Value+">"+" is not present";
							stepinfo = ErrDesc;
			    			}
						else if (driver.getPageSource().contains("<"+Element_Input_Value+">"+ChkElement_Property_Value.toUpperCase()+"</"+Element_Input_Value+">") && driver.getPageSource().contains("<"+Element_Input_Value+">"+ChkElement_Property_Value.toLowerCase()+"</"+Element_Input_Value+">") && driver.getPageSource().contains("<"+Element_Input_Value+">"+ChkElement_Property_Value+"</"+Element_Input_Value+">"))
						{
							Teststatus = "PASS";
							status = "info";
			    				ErrDesc = "Step "+Step_No+": Expected : "+"<"+Element_Input_Value+">"+ChkElement_Property_Value.toUpperCase()+"</"+Element_Input_Value+">"+" value found";
			    				stepinfo = ErrDesc;
						}
					}
					catch(Exception e)
					{
						if(!Action.toUpperCase().equals("VERIFYIFEXIST"))
						{
							status = "error";
							Teststatus = "FAIL";	  
							ErrDesc = "Expected : "+"<"+Element_Input_Value+">"+ChkElement_Property_Value.toUpperCase()+"</"+Element_Input_Value+">"+" is not present";
							ErrDesc = "Step "+ Step_No+" "+ErrDesc;
							stepinfo = ErrDesc;
						}
						else
						{
							Teststatus = "PASS";
							status = "warning";
							stepinfo =": info : Element not found.Ignoring step as its IFEXIST action : " + "<"+Element_Input_Value+">"+ChkElement_Property_Value.toUpperCase()+"</"+Element_Input_Value+">"+" Error IGNORED as its "+ Action.toUpperCase()+" action"+e.getMessage();
							stepinfo = ErrDesc;
			        			//appselection.TxtAreaStep.setText(stepinfo);
						}
					}finally{
			  			System.out.println(stepinfo);
			  		}
				}
				else if(ChkElement_Property.toUpperCase().equals("GETTEXT")) 
				{
					try
					{
						if (!driver.findElement(By.xpath(xPath)).getText().trim().toUpperCase().equals(ChkElement_Property_Value.toUpperCase()))
			    			{
			    				status = "error";
			        			Teststatus = "FAIL";
			        			ErrDesc = "Expected : "+ChkElement_Property_Value+" & Actual : "+driver.findElement(By.xpath(xPath)).getText();
			        			ErrDesc = "Element: "+xPath+"Expected : "+ChkElement_Property_Value+" & Actual : "+driver.findElement(By.xpath(xPath)).getText();
			        			stepinfo = ErrDesc;
			        		}
						else if(driver.findElement(By.xpath(xPath)).getText().trim().toUpperCase().equals(ChkElement_Property_Value.toUpperCase()))
			    			{
			    				status = "info";
			    				Teststatus = "PASS";
			    				ErrDesc = "Element: "+xPath+"Expected : "+ChkElement_Property_Value+" & Actual : "+driver.findElement(By.xpath(xPath)).getText();
			    				stepinfo = ErrDesc;
			    			}
					}
					catch(Exception e)
					{
						if(!Action.toUpperCase().equals("VERIFYIFEXIST"))
						{
							status = "error";
							Teststatus = "FAIL";
							ErrDesc = "Expected : "+"//*[@"+ChkElement_Property+"='"+ChkElement_Property_Value+"']"+" ERROR: "+e.getMessage();
							stepinfo = ErrDesc;
						}
						else
						{
							status = "warning";
							Teststatus = "PASS";
							ErrDesc = "Expected : "+"//*[@"+Element_Property+"='"+Element_Property_Value+"']"+" ERROR: "+e.getMessage();
							ErrDesc= "Step "+Step_No+ErrDesc;
							stepinfo = "Step "+Step_No+": info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
						}
					}finally{
			  			System.out.println(stepinfo);
			  		}
				}
				else 
				{
					try
					{
						if(!driver.findElement(By.xpath(xPath)).getAttribute(ChkElement_Property).toUpperCase().equals(ChkElement_Property_Value.toUpperCase()))
			    			{
			    				status = "error";
			        			Teststatus = "FAIL";
			        			ErrDesc = "Expected : "+ChkElement_Property_Value+" & Actual : "+driver.findElement(By.xpath(xPath)).getAttribute(ChkElement_Property);
			        			ErrDesc = "Element: "+xPath+"Expected : "+ChkElement_Property+" = "+ChkElement_Property_Value+" & Actual : "+ChkElement_Property+" = "+driver.findElement(By.id(Element_Property_Value)).getAttribute(ChkElement_Property);
			        			stepinfo = ErrDesc;
			        		}
						else if(driver.findElement(By.xpath(xPath)).getAttribute(ChkElement_Property).toUpperCase().equals(ChkElement_Property_Value.toUpperCase()))
			    			{
			    				status = "info";
			    				Teststatus = "PASS";
			    				ErrDesc = "Element: "+xPath+"Expected : "+ChkElement_Property_Value+" & Actual : "+driver.findElement(By.xpath(xPath)).getAttribute(ChkElement_Property);
			    				stepinfo = ErrDesc;
			    			}
					}
					catch(Exception e)
					{
						if(!Action.toUpperCase().equals("VERIFYIFEXIST"))
						{
							status = "error";
							Teststatus = "FAIL";
							ErrDesc = "Expected : "+"//*[@"+Element_Property+"='"+Element_Property_Value+"']"+" Error: "+e.getMessage();
							
							stepinfo = ErrDesc;
							//el.log("error","Element not found "+ErrDesc);
						}
						else
						{
							status = "warning";
							Teststatus = "PASS";
							ErrDesc = "Expected : "+"//*[@"+Element_Property+"='"+Element_Property_Value+"']"+" error: "+e.getMessage();
							ErrDesc= "Step "+Step_No+ErrDesc;
							stepinfo = "info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
						}
					}finally{
			  			System.out.println(stepinfo);
			  		}
				}
				log(selectedApp, Node_Name, "validation",ErrDesc);
				ErrDesc = stepinfo;
			  		break;
			  	case "CHKEXIST":
			  		status = "info";
				Teststatus = "PASS";
				ErrDesc = "Step "+Step_No+": Expected : "+xPath+" & Actual : Should Exist";
				stepinfo = "Executing Sheet '"+SheetName+"' - "+ ErrDesc;
				try {		        				
					   driver.findElement(By.xpath(xPath));
			    		   Exist = true;
			    		} 
					catch (NoSuchElementException e) 
			    		{
			    		   Exist = false;
			    		}finally{
				  			System.out.println(stepinfo);
				  		}
			    		if (!Exist)
			    		{
			    			status = "error";
					Teststatus = "FAIL";	
					ErrDesc = "Expected : "+xPath+" should exist & Actual : Not Exist";
					stepinfo = ErrDesc;
					//el.log("error","Error Found : "+ErrDesc);
			    		}
			    		else
			    		{
			    			Teststatus = "PASS";
			    			status = "info";
			    			stepinfo = "Expected : "+xPath+" exist";
			    		}
			    		ErrDesc = stepinfo;
			  		break;
			  	case "ACCEPT": case "ACCEPTIFEXIST":
			  		status = "info";
				Teststatus = "PASS";
				stepinfo = "Executing Sheet '"+SheetName+"' - Step "+Step_No+": "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") "+Action+" "+Element_Input_Value;
				//appselection.TxtAreaStep.setText(stepinfo);
				try {	
					Thread.sleep(1000);
					Alert al = driver.switchTo().alert();		        			
					al.accept(); 
					stepinfo = Element_Name+" Accepted";
				}catch (Exception e) {
					if(!Action.toUpperCase().equals("ACCEPTIFEXIST"))
					{
						status = "error";
						Teststatus = "FAIL";
						stepinfo = "Error Executing Step "+Step_No+":"+Element_Name+" not accept"+"error : " + e.getMessage();
						//appselection.TxtAreaStep.setText(stepinfo);
					} else {
						Teststatus = "PASS";
						status = "warning";
						stepinfo = ": info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
			    			//appselection.TxtAreaStep.setText(stepinfo);
					}
				 }finally{
			  			System.out.println(stepinfo);
			  		}
				ErrDesc = stepinfo;
			  	break;
			  	case "STOREVARIABLE": case "STOREVARIABLEIFEXIST":
				try {
						status = "info";
						Teststatus = "PASS";
						//appselection.TxtAreaStep.setText("Executing Sheet '"+SheetName+"' - Waiting for - Step "+Step_No+": "+Element_Name+" ("+xPath+")");
						waitUntilElementPresent(selectedApp,Node_Name, xPath, Action);
			    			stepinfo = "Executing Sheet '"+SheetName+"'  "+": "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") "+Action+" "+Element_Input_Value;
			    			//appselection.TxtAreaStep.setText(stepinfo);
			    			String tagname;
							if (Element_Input_Value.toUpperCase().equals("TAGTEXT")) 
							{
			    				tagname = driver.findElement(By.xpath(xPath)).getTagName().toUpperCase();					        				
			    				String[] sarray;
			    				sarray = driver.getPageSource().split(Element_Property+"="+Element_Property_Value+"");
			    				int sarraycounter = 1;
			    				try{
			    				sarraycounter = Integer.parseInt(ChkElement_Property);
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
								capValue = driver.findElement(By.xpath(xPath)).getText();
							}	
							capValue = capValue.replace(" PRIORITY", "");
							stepinfo = "TAGTEXT - " + Element_Property+" = "+Element_Property_Value + "Variable - "+ChkElement_Property_Value+" = "+capValue;
							store.put(ChkElement_Property_Value, capValue);
				}
				catch (Exception e) 
				{
					if(!Action.toUpperCase().equals("STOREVARIABLEIFEXIST"))
					{
						Teststatus = "FAIL";
						status = "error";
			    			stepinfo = "Error Executing Step "+Step_No+": "+Element_Name+" ("+Element_Property+"="+Element_Property_Value+") not Captured '"+Element_Input_Value+"'" + ": error : " + e.getMessage();
						//appselection.TxtAreaStep.setText(stepinfo);
					} 
					else 
					{
						Teststatus = "PASS";
						status = "warning";
						stepinfo = "Step "+ Step_No+": info : Element not found.Ignoring step as its IFEXIST action : " + e.getMessage();
			    			//appselection.TxtAreaStep.setText(stepinfo);
					}
				 }finally{
			  			System.out.println(stepinfo);
			  		}
			 	Thread.sleep(500);
			 	ErrDesc = stepinfo;
			break;
			  	case "VERIFYSTOREVARIABLE": case "VERIFYSTOREVARIABLEIFEXIST":
				try{
				Teststatus = "PASS";
				status = "info";
				String store_var = store.get(ChkElement_Property_Value) + Element_Input_Value;
				if(driver.getPageSource().contains(store_var))
				{
					stepinfo = "variable -  "+ChkElement_Property_Value+" = " +store_var+"  is present in the screen"+capValue;
				}
				else
				{
					Teststatus = "FAIL";
					status = "error";
					stepinfo = "Step "+Step_No+" " +store_var+"  is not present in the screen"+capValue;
				}
				//appselection.TxtAreaStep.setText(stepinfo);
				capValue = "";}catch(Exception e){
						if(Action.toUpperCase().contentEquals("VERIFYSTOREVARIABLEIFEXIST"))
						{
							Teststatus = "FAIL";
							status = "error";
							stepinfo = "variable -  "+ChkElement_Property_Value+" = " +store.get(ChkElement_Property_Value) + Element_Input_Value+"  is not present in the screen"+capValue;
						}
						else
						{
							//el.log("error", e.getMessage());
							Teststatus = "PASS";
							status = "warning";
							stepinfo = "variable -  "+ChkElement_Property_Value+" = " +store.get(ChkElement_Property_Value) + Element_Input_Value+"  is not present in the screen"+capValue;
						}
					}finally{
			  			System.out.println(stepinfo);
			  		}
				ErrDesc = stepinfo;
			break;
			  	case "SCRNCAPTURE": case "SCREENCAPTURE":
			  			screencapture(selectedApp, SheetName);
			  			status = "info";
					Teststatus = "PASS";
					stepinfo = "Executing Sheet '"+SheetName+"'  "+": "+Element_Name+"("+Element_Property+"="+Element_Property_Value+") "+Action+" "+Element_Input_Value;
					ErrDesc = stepinfo;
			  	break;
			  	case "ERRORCHECK":
			  		String errorchk_Filepath = "src/"+selectedApp+"-Error Sheet.xls";
			  		errorchecker(selectedApp, Node_Name, errorchk_Filepath, SheetName, driver);
			  		status = "info";
				Teststatus = "PASS";
				stepinfo = "Executing Sheet '"+SheetName+"' - "+": Checking for errors";
				ErrDesc = stepinfo;
			  	break;
			  	case "WAIT":
			  		int wait = Integer.parseInt(Element_Input_Value)*1000;
			  		status = "info";
			  		Teststatus = "PASS";
			  		Thread.sleep(wait);
			  		ErrDesc = stepinfo;
			  		System.out.println("WAIT");
			    break;
			  	case "SCROLL":
			  		Teststatus = "PASS";
				status = "info";
				stepinfo = "Executing Sheet '"+SheetName+"' -  "+": "+Element_Name+"("+Element_Property+"="+Element_Property_Value+") "+Action+" "+Element_Input_Value;
				JavascriptExecutor executor = (JavascriptExecutor)driver;
			    executor.executeScript("window.focus()");
			    Thread.sleep(2000);
			  		Robot robot = new Robot();
			  		switch (Element_Input_Value.toUpperCase())
			  		{
			  			case "UP":
			  				robot.mouseWheel(-10);
			  			break;
			  			case "LEFT":
			  				for (int keypress = 1;keypress<=4;keypress++) {
			    				 robot.keyPress(KeyEvent.VK_LEFT);
			    				 Thread.sleep(100);
			    			 }
						break;
			  			case "RIGHT":
						for (int keypress = 1;keypress<=4;keypress++) {
			    				 robot.keyPress(KeyEvent.VK_RIGHT);
			    				 Thread.sleep(100);
			    			 }
						break;
			  			case "DOWN":
			  				robot.mouseWheel(10);
						break;		
			  		}	
			  			System.out.println(stepinfo);
			  		
			  		break;
			  	case "TYPE":
			  		status = "info";
				Teststatus = "PASS";
				stepinfo = "Executing Sheet '"+SheetName+"' -  "+": "+Element_Name+"("+Element_Property+"="+Element_Property_Value+") "+Action+" "+Element_Input_Value;
				String inpvalue = Element_Input_Value.toUpperCase();			        				
					wordtype(inpvalue);	
					ErrDesc = stepinfo;
					System.out.println(stepinfo);
			  		break;
			  	case "PRESS":
			  		status = "info";
				Teststatus = "PASS";
				stepinfo = "Executing Sheet '"+SheetName+"' -  "+": "+Element_Name+"("+Element_Property+"="+Element_Property_Value+") "+Action+" "+Element_Input_Value;
				//appselection.TxtAreaStep.setText(stepinfo);
				String presskey = Element_Input_Value.toUpperCase();			        				
					keyboard(presskey);
					ErrDesc = stepinfo;
					System.out.println(stepinfo);
			break;
			  	case "MOUSE":
			  		status = "info";
				Teststatus = "PASS";
				stepinfo = "Executing Sheet '"+SheetName+"' -  "+": "+Element_Name+"("+Element_Property+"="+Element_Property_Value+") "+Action+" "+Element_Input_Value;
			  		mouseclick(Element_Input_Value, xPath, driver);
			  		Teststatus = "PASS";
			  		System.out.println(stepinfo);
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
				System.out.println("SWITCHNEWWINDOW");
			break;
			case "SWITCHOLDWINDOW":
				driver.switchTo().window(ParentWindow);
				System.out.println("SWITCHOLDWINDOW");
			break;
			  }
			 
			  
			  if (Report.toUpperCase().contentEquals("Y")) {
				 
			    	report(selectedApp, SheetName,TC_Name," "+Element_Name+"-"+Element_Property+"="+Element_Property_Value);
			    	//log(selectedApp, Node_Name, status,"Step "+Step_No+stepinfo);
			    }else{
			    	//log(selectedApp,Node_Name, status,"Step "+Step_No+stepinfo);
			    }
			  
			  stepinfo = "Element Name: "+Element_Name+ "Element Property: "+Element_Property+"Element Property Value: "+Element_Property_Value+ " is executed";
			  //appselection.TxtAreaStep.setText(JUNITDriverEngine_0.stepinfo);
			  
			  
			  
			  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
					
		}finally {
			if (Report.toUpperCase().contentEquals("Y")) {
				 
		    	report(selectedApp, SheetName,TC_Name," "+Element_Name+"-"+Element_Property+"="+Element_Property_Value);
		    	//log(selectedApp, Node_Name, status,"Step "+Step_No+stepinfo);
		    }else{
		    	//log(selectedApp,Node_Name, status,"Step "+Step_No+stepinfo);
		    }
		}
		 
	  }
	  */
	  public void waitUntilElementPresent(String selectedApp, String node_Name, String xPath, String Action) throws IOException
	  {
		  try
	  		{ 
	  			WebDriverWait wait = new WebDriverWait(driver,20);
	  			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xPath)));
	  		}
	  		catch(Exception e)
	  		{
	  			if(!Action.toUpperCase().contains("IFEXIST"))
	  			{
	  				log(selectedApp,node_Name, "error",e.getMessage());
	  				System.exit(0);
	  			}
	  		}
	  }
	  
	  public static void mouseclick(String inpkey, String xPath, WebDriver driver) throws AWTException, InterruptedException {
	    	Robot robot = new Robot();  
	    		Thread.sleep(50);
	    		if (!inpkey.contains("("))
	    		{
			        switch (inpkey) 
			        {       
			        case "CLICK": 
			        	robot.mousePress(InputEvent.BUTTON1_MASK);
			        	robot.mouseRelease(InputEvent.BUTTON1_MASK);
		        	break;
			        case "DBLCLICK": case "DOUBLECLICK": 
			        	robot.mousePress(InputEvent.BUTTON1_MASK);
			        	robot.mouseRelease(InputEvent.BUTTON1_MASK);
			        	robot.mousePress(InputEvent.BUTTON1_MASK);
		        		robot.mouseRelease(InputEvent.BUTTON1_MASK); break;
			        case "RHTCLICK": case "RIGHTCLICK": 
			        	robot.mousePress(InputEvent.BUTTON3_MASK);
			        	robot.mouseRelease(InputEvent.BUTTON3_MASK);
			        break;		     
			        case "MOVE":
			        	driver.manage().window().setPosition(new Point(0, 0));
			        	Robot robot1 = new Robot(); 
			        	Point coordinates = driver.findElement(By.xpath(xPath)).getLocation();
			        	((JavascriptExecutor) driver).executeScript("window.focus();");
			        	robot1.mouseMove(coordinates.getX()+20,coordinates.getY()+160);
			        	Thread.sleep(1000);
			        	driver.findElement(By.xpath(xPath)).click();
			        	//driver.manage().window().setPosition(new Point(-2000, 0));
			        	Thread.sleep(100);
			        break;
			        
			        default:
			            throw new IllegalArgumentException("Cannot PRESS key " + inpkey);
			        }
		    	} 
	    		else 
	    		{
		    		Robot robot2 = new Robot(); 
		    		String mloc = inpkey.substring(inpkey.indexOf("(")+1, inpkey.lastIndexOf(")"));
		    		String[] mlocArray = mloc.split(",");
		    		robot2.mouseMove(Integer.parseInt(mlocArray[0]),Integer.parseInt(mlocArray[1]));
		    	}
		        Thread.sleep(300);

	    	}
	  
	  public static void errorchecker(String selectedApp, String node_Name, String DatasheetPath, String SheetName, WebDriver driver) throws BiffException, IOException, AWTException
	  {
		  FileInputStream  fis1 = new FileInputStream(DatasheetPath);
		  Workbook wb1 = Workbook.getWorkbook(fis1);
		  Sheet s4 = wb1.getSheet(SheetName);
		  String Error_No = null, Library = null, Error_Name = null, Element_Type = null, Element_Property = null, Element_Property_Value = null, Action_IfFound = null, Error_Type = null;
		  String xPath = null;
		  for (int i=1;i<s4.getRows();i++) 
		  {
			  Error_No = s4.getCell(0,i).getContents();
			  Library = s4.getCell(1,i).getContents();
			  Error_Name = s4.getCell(2,i).getContents();
			  Element_Property = s4.getCell(3,i).getContents();
			  Element_Property_Value = s4.getCell(4,i).getContents();
			  Action_IfFound = s4.getCell(5,i).getContents();
			  Error_Type = s4.getCell(6,i).getContents();
			  String[] PropArray, PropValArray;
			  if(Element_Property.toUpperCase().contentEquals("XPATH"))
			  {
				  xPath = Element_Property_Value;
			  }
			  else
			  {
				  if(Element_Property.contains(",,"))
				  {
					  PropArray = Element_Property.split(",,");
					  PropValArray = Element_Property_Value.split(",,");
					  xPath = "//*[";
					  for(int j = 0; j < PropArray.length; j++)
					  {
						  if(j>0)
							  xPath = xPath + " and ";
						  xPath = xPath + "@"+PropArray[j] + "='" + PropValArray[j] + "'";
					  }
					  xPath = xPath + "]";
				  }
				  else
					  xPath = "//*[@"+Element_Property+"='"+Element_Property_Value+"']";
			  }
			  try 
			  {
				  if(driver.findElement(By.xpath(xPath)).isDisplayed())
				  {
					  screencapture(selectedApp, SheetName);
					  if(Action_IfFound.toUpperCase().contentEquals("TERMINATE"))
					  {
						  System.exit(0);
					  }
				  }
			  }
			  catch (NoSuchElementException e)
			  {
				 // log(Node_Name, "error",e.getMessage());
     		  }
		  }
	  }
	  
	  public static void screencapture(String selectedApp, String SheetName) throws IOException, AWTException
	  {		
			File resDir = new File("C:\\Selenium\\Results\\Screenshots");
			if (!resDir.exists())
			{ 
				resDir.mkdir();
			} 
			File resDir1 = new File("C:\\Selenium\\Results\\Screenshots\\"+selectedApp);
			if (!resDir1.exists())
			{
				resDir1.mkdir();
			} 
			DateFormat scrcpttme = new SimpleDateFormat("yyyy_MM_dd hh_mm_ss a");
			Calendar cal = Calendar.getInstance();
			String scrcptTime = scrcpttme.format(cal.getTime());
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Rectangle screenRectangle = new Rectangle(screenSize);	
			Robot robot = new Robot();
	        BufferedImage image = robot.createScreenCapture(screenRectangle);
	        ImageIO.write(image, "png", new File(resDir1+"\\"+selectedApp+"-"+" "+SheetName+scrcptTime+".jpg"));	
		}
	  
	  public static void keypress(String inpkey) throws AWTException, InterruptedException {
	    	Robot robot = new Robot();  
	    		Thread.sleep(50);
		        switch (inpkey) {       
			        case "ENTER": robot.keyPress(KeyEvent.VK_ENTER); break;
			        case "ESC": robot.keyPress(KeyEvent.VK_ESCAPE); break;
			        case "TAB": robot.keyPress(KeyEvent.VK_TAB); break;
			        case "SPACE": robot.keyPress(KeyEvent.VK_SPACE); break;
			        case "PAGEUP": robot.keyPress(KeyEvent.VK_PAGE_UP); break;
			        case "PAGEDOWN": robot.keyPress(KeyEvent.VK_PAGE_DOWN); break;
			        case "BACKSPACE": robot.keyPress(KeyEvent.VK_BACK_SPACE); break;
			        case "UP": robot.keyPress(KeyEvent.VK_UP); break;
			        case "DOWN": robot.keyPress(KeyEvent.VK_DOWN); break;
			        case "LEFT": robot.keyPress(KeyEvent.VK_LEFT); break;
			        case "RIGHT": robot.keyPress(KeyEvent.VK_RIGHT); break;
			        case "END": robot.keyPress(KeyEvent.VK_END); break;
			        case "HOME": robot.keyPress(KeyEvent.VK_HOME); break;
			        case "INSERT": robot.keyPress(KeyEvent.VK_INSERT); break;
			        case "DELETE": robot.keyPress(KeyEvent.VK_DELETE); break;
			        case "SHIFT": robot.keyPress(KeyEvent.VK_SHIFT); break;
			        case "CTRL": robot.keyPress(KeyEvent.VK_CONTROL); break;
			        case "ALT": robot.keyPress(KeyEvent.VK_ALT); break;
			        case "COPY": keypress("CTRL");keypress("C");keyrelease("C");keyrelease("CTRL"); break;
			        case "PASTE": keypress("CTRL");keypress("V");keyrelease("V");keyrelease("CTRL");break;
			        case "CUT":keypress("CTRL");keypress("X");keyrelease("X");keyrelease("CTRL"); break;
			        case "A": robot.keyPress(KeyEvent.VK_A); break;
			        case "B": robot.keyPress(KeyEvent.VK_B); break;
			        case "C": robot.keyPress(KeyEvent.VK_C); break;
			        case "D": robot.keyPress(KeyEvent.VK_D); break;
			        case "E": robot.keyPress(KeyEvent.VK_E); break;
			        case "F": robot.keyPress(KeyEvent.VK_F); break;
			        case "G": robot.keyPress(KeyEvent.VK_G); break;
			        case "H": robot.keyPress(KeyEvent.VK_H); break;
			        case "I": robot.keyPress(KeyEvent.VK_I); break;
			        case "J": robot.keyPress(KeyEvent.VK_J); break;
			        case "K": robot.keyPress(KeyEvent.VK_K); break;
			        case "L": robot.keyPress(KeyEvent.VK_L); break;
			        case "M": robot.keyPress(KeyEvent.VK_M); break;
			        case "N": robot.keyPress(KeyEvent.VK_N); break;
			        case "O": robot.keyPress(KeyEvent.VK_O); break;
			        case "P": robot.keyPress(KeyEvent.VK_P); break;
			        case "Q": robot.keyPress(KeyEvent.VK_Q); break;
			        case "R": robot.keyPress(KeyEvent.VK_R); break;
			        case "S": robot.keyPress(KeyEvent.VK_S); break;
			        case "T": robot.keyPress(KeyEvent.VK_T); break;
			        case "U": robot.keyPress(KeyEvent.VK_U); break;
			        case "V": robot.keyPress(KeyEvent.VK_V); break;
			        case "W": robot.keyPress(KeyEvent.VK_W); break;
			        case "X": robot.keyPress(KeyEvent.VK_X); break;
			        case "Y": robot.keyPress(KeyEvent.VK_Y); break;
			        case "Z": robot.keyPress(KeyEvent.VK_Z); break;	       
			        case "0": robot.keyPress(KeyEvent.VK_0); break;
			        case "1": robot.keyPress(KeyEvent.VK_1); break;
			        case "2": robot.keyPress(KeyEvent.VK_2); break;
			        case "3": robot.keyPress(KeyEvent.VK_3); break;
			        case "4": robot.keyPress(KeyEvent.VK_4); break;
			        case "5": robot.keyPress(KeyEvent.VK_5); break;
			        case "6": robot.keyPress(KeyEvent.VK_6); break;
			        case "7": robot.keyPress(KeyEvent.VK_7); break;
			        case "8": robot.keyPress(KeyEvent.VK_8); break;
			        case "9": robot.keyPress(KeyEvent.VK_9); break;
			        case "`": robot.keyPress(KeyEvent.VK_BACK_QUOTE); break;
			        case "-": robot.keyPress(KeyEvent.VK_MINUS); break;
			        case "=": robot.keyPress(KeyEvent.VK_EQUALS); break;
			        case "~": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_BACK_QUOTE); break;
			        case "!": robot.keyPress(KeyEvent.VK_EXCLAMATION_MARK); break;
			        case "@": robot.keyPress(KeyEvent.VK_AT); break;
			        case "#": robot.keyPress(KeyEvent.VK_NUMBER_SIGN); break;
			        case "$": robot.keyPress(KeyEvent.VK_DOLLAR); break;
			        case "%": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_5); break;
			        case "^": robot.keyPress(KeyEvent.VK_CIRCUMFLEX); break;
			        case "&": robot.keyPress(KeyEvent.VK_AMPERSAND); break;
			        case "*": robot.keyPress(KeyEvent.VK_ASTERISK); break;
			        case "(": robot.keyPress(KeyEvent.VK_LEFT_PARENTHESIS); break;
			        case ")": robot.keyPress(KeyEvent.VK_RIGHT_PARENTHESIS); break;
			        case "_": robot.keyPress(KeyEvent.VK_UNDERSCORE); break;
			        case "+": robot.keyPress(KeyEvent.VK_PLUS); break;
			        case "\t": robot.keyPress(KeyEvent.VK_TAB); break;
			        case "\n": robot.keyPress(KeyEvent.VK_ENTER); break;
			        case "[": robot.keyPress(KeyEvent.VK_OPEN_BRACKET); break;
			        case "]": robot.keyPress(KeyEvent.VK_CLOSE_BRACKET); break;
			        case "\\": robot.keyPress(KeyEvent.VK_BACK_SLASH); break;
			        case "{": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_OPEN_BRACKET); robot.keyRelease(KeyEvent.VK_SHIFT); break;
			        case "}": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_CLOSE_BRACKET); robot.keyRelease(KeyEvent.VK_SHIFT); break;
			        case "|": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_BACK_SLASH); robot.keyRelease(KeyEvent.VK_SHIFT); break;
			        case ";": robot.keyPress(KeyEvent.VK_SEMICOLON); break;
			        case ":": robot.keyPress(KeyEvent.VK_COLON); break;
			        case "'": robot.keyPress(KeyEvent.VK_QUOTE); break;
			        case "\"": robot.keyPress(KeyEvent.VK_QUOTEDBL); break;
			        case ",": robot.keyPress(KeyEvent.VK_COMMA); break;
			        case "<": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_COMMA); robot.keyRelease(KeyEvent.VK_SHIFT); break;
			        case ".": robot.keyPress(KeyEvent.VK_PERIOD); break;
			        case ">": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_PERIOD); robot.keyRelease(KeyEvent.VK_SHIFT); break;
			        case "/": robot.keyPress(KeyEvent.VK_SLASH); break;
			        case "?": robot.keyPress(KeyEvent.VK_SHIFT); robot.keyPress(KeyEvent.VK_SLASH); robot.keyRelease(KeyEvent.VK_SHIFT); break;
			        case " ": robot.keyPress(KeyEvent.VK_SPACE); break;
			        case "F1": robot.keyPress(KeyEvent.VK_F1); break;
			        case "F2": robot.keyPress(KeyEvent.VK_F2); break;
			        case "F3": robot.keyPress(KeyEvent.VK_F3); break;
			        case "F4": robot.keyPress(KeyEvent.VK_F4); break;
			        case "F5": robot.keyPress(KeyEvent.VK_F5); break;
			        case "F6": robot.keyPress(KeyEvent.VK_F6); break;
			        case "F7": robot.keyPress(KeyEvent.VK_F7); break;
			        case "F8": robot.keyPress(KeyEvent.VK_F8); break;
			        case "F9": robot.keyPress(KeyEvent.VK_F9); break;
			        case "F10": robot.keyPress(KeyEvent.VK_F10); break;
			        case "F11": robot.keyPress(KeyEvent.VK_F11); break;
			        case "F12": robot.keyPress(KeyEvent.VK_F12); break;
			        case "CAPSLOCK": robot.keyPress(KeyEvent.VK_CAPS_LOCK); break;
			        case "SCROLLLOCK": robot.keyPress(KeyEvent.VK_SCROLL_LOCK); break;
			        case "NUMLOCK": robot.keyPress(KeyEvent.VK_NUM_LOCK); break;
		        default:
		            throw new IllegalArgumentException("Cannot PRESS key " + inpkey);
		        }
		        Thread.sleep(50);

	    	}
	  
	  public static void keyrelease(String inpkey) throws AWTException, InterruptedException {
	    	Robot robot = new Robot();  
	    		Thread.sleep(50);
		        switch (inpkey) {       
			        case "ENTER": robot.keyRelease(KeyEvent.VK_ENTER); break;
			        case "ESC": robot.keyRelease(KeyEvent.VK_ESCAPE); break;
			        case "TAB": robot.keyRelease(KeyEvent.VK_TAB); break;
			        case "SPACE": robot.keyRelease(KeyEvent.VK_SPACE); break;
			        case "PAGEUP": robot.keyRelease(KeyEvent.VK_PAGE_UP); break;
			        case "PAGEDOWN": robot.keyRelease(KeyEvent.VK_PAGE_DOWN); break;
			        case "BACKSPACE": robot.keyRelease(KeyEvent.VK_BACK_SPACE); break;
			        case "UP": robot.keyRelease(KeyEvent.VK_UP); break;
			        case "DOWN": robot.keyRelease(KeyEvent.VK_DOWN); break;
			        case "LEFT": robot.keyRelease(KeyEvent.VK_LEFT); break;
			        case "RIGHT": robot.keyRelease(KeyEvent.VK_RIGHT); break;
			        case "END": robot.keyRelease(KeyEvent.VK_END); break;
			        case "HOME": robot.keyRelease(KeyEvent.VK_HOME); break;
			        case "INSERT": robot.keyRelease(KeyEvent.VK_INSERT); break;
			        case "DELETE": robot.keyRelease(KeyEvent.VK_DELETE); break;
			        case "SHIFT": robot.keyRelease(KeyEvent.VK_SHIFT); break;		        
			        case "CTRL": robot.keyRelease(KeyEvent.VK_CONTROL); break;
			        case "ALT": robot.keyRelease(KeyEvent.VK_ALT); break;
			        case "A": robot.keyRelease(KeyEvent.VK_A); break;
			        case "B": robot.keyRelease(KeyEvent.VK_B); break;
			        case "C": robot.keyRelease(KeyEvent.VK_C); break;
			        case "D": robot.keyRelease(KeyEvent.VK_D); break;
			        case "E": robot.keyRelease(KeyEvent.VK_E); break;
			        case "F": robot.keyRelease(KeyEvent.VK_F); break;
			        case "G": robot.keyRelease(KeyEvent.VK_G); break;
			        case "H": robot.keyRelease(KeyEvent.VK_H); break;
			        case "I": robot.keyRelease(KeyEvent.VK_I); break;
			        case "J": robot.keyRelease(KeyEvent.VK_J); break;
			        case "K": robot.keyRelease(KeyEvent.VK_K); break;
			        case "L": robot.keyRelease(KeyEvent.VK_L); break;
			        case "M": robot.keyRelease(KeyEvent.VK_M); break;
			        case "N": robot.keyRelease(KeyEvent.VK_N); break;
			        case "O": robot.keyRelease(KeyEvent.VK_O); break;
			        case "P": robot.keyRelease(KeyEvent.VK_P); break;
			        case "Q": robot.keyRelease(KeyEvent.VK_Q); break;
			        case "R": robot.keyRelease(KeyEvent.VK_R); break;
			        case "S": robot.keyRelease(KeyEvent.VK_S); break;
			        case "T": robot.keyRelease(KeyEvent.VK_T); break;
			        case "U": robot.keyRelease(KeyEvent.VK_U); break;
			        case "V": robot.keyRelease(KeyEvent.VK_V); break;
			        case "W": robot.keyRelease(KeyEvent.VK_W); break;
			        case "X": robot.keyRelease(KeyEvent.VK_X); break;
			        case "Y": robot.keyRelease(KeyEvent.VK_Y); break;
			        case "Z": robot.keyRelease(KeyEvent.VK_Z); break;	       
			        case "0": robot.keyRelease(KeyEvent.VK_0); break;
			        case "1": robot.keyRelease(KeyEvent.VK_1); break;
			        case "2": robot.keyRelease(KeyEvent.VK_2); break;
			        case "3": robot.keyRelease(KeyEvent.VK_3); break;
			        case "4": robot.keyRelease(KeyEvent.VK_4); break;
			        case "5": robot.keyRelease(KeyEvent.VK_5); break;
			        case "6": robot.keyRelease(KeyEvent.VK_6); break;
			        case "7": robot.keyRelease(KeyEvent.VK_7); break;
			        case "8": robot.keyRelease(KeyEvent.VK_8); break;
			        case "9": robot.keyRelease(KeyEvent.VK_9); break;
			        case "`": robot.keyRelease(KeyEvent.VK_BACK_QUOTE); break;
			        case "-": robot.keyRelease(KeyEvent.VK_MINUS); break;
			        case "=": robot.keyRelease(KeyEvent.VK_EQUALS); break;	
			        case "[": robot.keyRelease(KeyEvent.VK_OPEN_BRACKET); break;
			        case "]": robot.keyRelease(KeyEvent.VK_CLOSE_BRACKET); break;
			        case "\\": robot.keyRelease(KeyEvent.VK_BACK_SLASH); break;
			        case ";": robot.keyRelease(KeyEvent.VK_SEMICOLON); break;		        
			        case "'": robot.keyRelease(KeyEvent.VK_QUOTE); break;
			        case ",": robot.keyRelease(KeyEvent.VK_COMMA); break;		        
			        case ".": robot.keyRelease(KeyEvent.VK_PERIOD); break;		        
			        case "/": robot.keyRelease(KeyEvent.VK_SLASH); break;		        
			        case " ": robot.keyRelease(KeyEvent.VK_SPACE); break;
			        case "F1": robot.keyRelease(KeyEvent.VK_F1); break;
			        case "F2": robot.keyRelease(KeyEvent.VK_F2); break;
			        case "F3": robot.keyRelease(KeyEvent.VK_F3); break;
			        case "F4": robot.keyRelease(KeyEvent.VK_F4); break;
			        case "F5": robot.keyRelease(KeyEvent.VK_F5); break;
			        case "F6": robot.keyRelease(KeyEvent.VK_F6); break;
			        case "F7": robot.keyRelease(KeyEvent.VK_F7); break;
			        case "F8": robot.keyRelease(KeyEvent.VK_F8); break;
			        case "F9": robot.keyRelease(KeyEvent.VK_F9); break;
			        case "F10": robot.keyRelease(KeyEvent.VK_F10); break;
			        case "F11": robot.keyRelease(KeyEvent.VK_F11); break;
			        case "F12": robot.keyRelease(KeyEvent.VK_F12); break;
			        case "CAPSLOCK": robot.keyRelease(KeyEvent.VK_CAPS_LOCK); break;
			        case "SCROLLLOCK": robot.keyRelease(KeyEvent.VK_SCROLL_LOCK); break;
			        case "NUMLOCK": robot.keyRelease(KeyEvent.VK_NUM_LOCK); break;		        
		        default:
		            break;
		        }
		        Thread.sleep(50);

	    	}
	  
	  
	  public static void keyboard(String inpstring) throws AWTException, InterruptedException {    	
	    	int l;
	    	String[] keybsplit;
	    	if (inpstring.contains("+")) {
	    		keybsplit = inpstring.split("\\+");
	    		for(l=0;l<keybsplit.length;l++) {
	    			keypress(keybsplit[l]);
	    		}
	    		for(l=keybsplit.length-1;l>=0;l--){
	    			keyrelease(keybsplit[l]);
	    		}
	    	} else {
	    		keypress(inpstring);
	    		keyrelease(inpstring);
	    	}
	    }
	  
	    public static void wordtype(String inpstring) throws AWTException, InterruptedException {    	
	    	int l;
	    	for (l = 0;l<inpstring.length();l++) {
	    		char invalue = inpstring.charAt(l); 
	    		String inpvalue = Character.toString(invalue);
	    		keypress(inpvalue);
	    	}
	    	for (l=inpstring.length()-1;l>=0;l--){
	    		char invalue = inpstring.charAt(l); 
	    		String inpvalue = Character.toString(invalue);    		
	    		keyrelease(inpvalue);
	    	}
	    }
	  
	  public static void log(String selectedApp, String node_Name, String logLevel,String msg) throws IOException {
		  	cal = Calendar.getInstance();
		  	rptTme = new SimpleDateFormat("EEEE yyyy:MM:dd hh:mm:ss a");
			reprtTime = rptTme.format(cal.getTime());
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");		
			String cal1 = dateFormat.format(cal.getTime());
			String ExecLogger = "C:\\Selenium\\Results\\"+node_Name+" "+selectedApp+" ExecLog "+cal1+".html";
			FileWriter FW = new FileWriter(ExecLogger,true); 
			BufferedWriter BW = new BufferedWriter(FW);
		
			switch(logType.valueOf(logLevel)){ //switch1

			case warning:
				color = "Chocolate";
				status = "[WARNING]";
				break;
			case info:
				color = "black";
				status = "[INFO]";
				break;
			case error:
				color = "red";
				status = "[ERROR]";
				break;
			case config:
				color = "black";
				status = "[CONFIG]";
				break;
			case validation:
				if (Teststatus.equals("PASS")){
					color = "blue";
					status = "[VALIDATION PASS]";
				}else{
					color = "red";
					status = "[VALIDATION FAIL]";
				}
				break;			
			} //switch1 ends
			BW.write("<br><font color = "+color+" size=2>");
			BW.write("--> ["+reprtTime);
			BW.write("] ["+status+"] - ");
			BW.write(msg);
			BW.write("</font>");
			BW.write("</BODY></HTML>")	 ;  
			BW.close();
			FW.close();
		}
	  
	  public void func_log(String selectedApp, StringBuffer Node_Name, String SheetName) throws IOException {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");	
			cal = Calendar.getInstance();
			String cal1 = dateFormat.format(cal.getTime());
			rptTme = new SimpleDateFormat("EEEE yyyy:MM:dd hh:mm:ss a");
			reprtTime = rptTme.format(cal.getTime());
			String ExecLogger = "C:\\Selenium\\Results\\"+Node_Name+" "+selectedApp+" ExecLog "+cal1+".html";
			FileWriter FW = new FileWriter(ExecLogger,true); 
			BufferedWriter BW = new BufferedWriter(FW);
			BW.write("<br><TABLE cellSpacing=0 cellPadding=0 width=100%");
			BW.write("border=0><TBODY><tr height=24><TD vAlign=center class=subheading align=left width=100%");
			BW.write("<font size=2><B>");
			BW.write("Functionality : "+SheetName);
			BW.write("</B></font></TD></tr></TBODY></TABLE>");
			BW.write("<br><font color = black size=2>--> [");
			BW.write(reprtTime);
			BW.write("] [INFO] - ");
			BW.write("<<<< "+SheetName+" Functionality");
			BW.write("STARTED >>>> </font>");	
			BW.close();
			FW.close();
	} //func_log ends
	  
	  public static void baselog(String selectedApp, StringBuffer Node_Name) throws IOException {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");	
			cal = Calendar.getInstance();
			String cal1 = dateFormat.format(cal.getTime());
			rptTme = new SimpleDateFormat("EEEE yyyy:MM:dd hh:mm:ss a");
			reprtTime = rptTme.format(cal.getTime());
			String ExecLogger = "C:\\Selenium\\Results\\"+Node_Name+" "+selectedApp+" ExecLog "+cal1+".html";	
			File resDir = new File("C:\\Selenium\\Results");
			if (!resDir.exists()) { //if1
				resDir.mkdir();
			} //if1 ends

			File logfile = new File(ExecLogger);//Created object of java File class.
			if (!logfile.exists()){ //if2
				logfile.createNewFile();
				FileWriter FW = new FileWriter(ExecLogger,true); 
				BufferedWriter BW = new BufferedWriter(FW);
			  	BW.write("<HTML><HEAD><BODY><style type=text/css>BODY{PADDING-RIGHT: 0px; PADDING-LEFT: 0px; FONT-SIZE: 11px; PADDING-BOTTOM: 0px; MARGIN: 0px; PADDING-TOP: 0px;FONT-FAMILY: Tahoma, Verdana, Sans-Serif; BACKGROUND-COLOR: #ffffff}.logoTable{FONT-SIZE: 11px; COLOR: #333333;BACKGROUND-COLOR: ");
				BW.write("\"#CCD8FF\"");
				BW.write("; FONT-FAMILY: Tahoma, Verdana, Geneva; TEXT-DECORATION: none}TD{FONT-SIZE: 13px; COLOR: ");
				BW.write("\"black\""); 
				BW.write("; FONT-FAMILY: Tahoma, Verdana, Geneva; TEXT-DECORATION: none}.inner{FONT-SIZE: 15px;}.subheading{BACKGROUND-COLOR:#dcdcdc}</style><TABLE class = ");
				BW.write("\"logoTable\"");
				BW.write(" cellSpacing=0 cellPadding=0 width=");		
				BW.write("\"100%\"");
				BW.write(" border=0><TBODY><TR><TD vAlign=center align=left height=");
				BW.write("\"100%\">");
				BW.write("</td><td align=left width=20%><STRONG>&nbspDate: </STRONG>"+reprtTime+"</td><TD vAlign=center class=inner align=center width=60%><STRONG>&nbspDCOE Automation Execution Summary</center></STRONG></TD><TD vAlign=center align=right width=20% height=31><STRONG>&nbsp</STRONG>");		
				BW.write("&nbsp &nbsp<strong>Code Version :&nbsp");
				BW.write("1.0"+"</strong>");
				BW.write("&nbsp &nbsp</TD></TR></table><br> &nbsp");			
				BW.close();
				FW.close();
			}
	  }
	  
	  public void report(String selectedApp, String SheetName, String TestCaseName, String TestStep) throws IOException, RowsExceededException, WriteException, BiffException{
			//DateFormat logTme = new SimpleDateFormat("hh:mm:ss");
			Calendar cal = Calendar.getInstance();
			//String logTime = logTme.format(cal.getTime());		
			DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");		
			String cal1 = dateFormat.format(cal.getTime());
			String filepath = "Results\\"+selectedApp+" Detailed Report "+cal1+".xls";
			File ifilepath = new File("Results\\"+selectedApp+" Detailed Report "+cal1+".xls");
			String ofilepath = "Results\\"+selectedApp+" Detailed Report "+cal1+"_temp.xls";
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
			    sheet.addCell(new Label(2, 0, "TestCaseName",cellFormat));
			    sheet.addCell(new Label(3, 0, "TestStep",cellFormat));
			    sheet.addCell(new Label(4, 0, "TestStatus",cellFormat));
			    sheet.addCell(new Label(5, 0, "Comments",cellFormat));
			    sheet.addCell(new Label(6, 0, "Error Type",cellFormat));
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
		    sheet1.addCell(new Label(col, newrow,SheetName,cellFormat1));
		    col = 2;
		    widthInChars = 70;
		    sheet1.setColumnView(col, widthInChars);
		    sheet1.addCell(new Label(col, newrow,TestCaseName,cellFormat1));
		    col = 3;
		    widthInChars = 70;
		    sheet1.setColumnView(col, widthInChars);
		    sheet1.addCell(new Label(col, newrow,TestStep,cellFormat1));
		    col = 4;
		    widthInChars = 15;
		    sheet1.setColumnView(col, widthInChars);
		   
		    if (Teststatus.contentEquals("PASS")) { //if2
		    	sheet1.addCell(new Label(col, newrow,Teststatus,passcellFormat));
		    	col = 7;
		 	    widthInChars = 20;
		 	    sheet1.setColumnView(col, widthInChars);
		    	sheet1.addCell(new Label(col, newrow,"",cellFormat1));
		    }else{
		    	sheet1.addCell(new Label(col, newrow,Teststatus,failcellFormat));	   
		    	col = 7;
		 	    widthInChars = 20;
		 	    sheet1.setColumnView(col, widthInChars);
			    sheet1.addCell(new Label(col, newrow,errorcriticality,cellFormat1));
		    } //if2 ends
		    col = 6;
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
		    Teststatus = "PASS";
		    ErrDesc = "";
			
		}
	  
	  public void Execution_Summary_Report(String selectedApp, String SheetName, String TestCaseName) throws IOException, RowsExceededException, WriteException, BiffException{
			//DateFormat logTme = new SimpleDateFormat("hh:mm:ss");
			Calendar cal = Calendar.getInstance();
			//String logTime = logTme.format(cal.getTime());		
			DateFormat dateFormat = new SimpleDateFormat("MM_dd_yyyy");		
			String cal1 = dateFormat.format(cal.getTime());
			String filepath = "C:\\Selenium\\Results\\"+selectedApp+" Final Report "+cal1+".xls";
			File ifilepath = new File("C:\\Selenium\\Results\\"+selectedApp+" Final Report "+cal1+".xls");
			String ofilepath = "C:\\Selenium\\Results\\"+selectedApp+" Final Report "+cal1+"_temp.xls";
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
			    sheet.addCell(new Label(2, 0, "TestCaseName",cellFormat));
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
		    sheet1.addCell(new Label(col, newrow,SheetName,cellFormat1));
		    col = 2;
		    widthInChars = 70;
		    sheet1.setColumnView(col, widthInChars);
		    sheet1.addCell(new Label(col, newrow,TestCaseName,cellFormat1));
		    col = 3;
		    widthInChars = 15;
		    sheet1.setColumnView(col, widthInChars);
		   
		    if (Teststatus=="PASS") { //if2
		    	sheet1.addCell(new Label(col, newrow,Teststatus,passcellFormat));
		    	col = 5;
		 	    widthInChars = 20;
		 	    sheet1.setColumnView(col, widthInChars);
		    	sheet1.addCell(new Label(col, newrow,"",cellFormat1));
		    	ErrDesc="";
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
		    Teststatus = "PASS";
		    ErrDesc = "";
			
		}
}