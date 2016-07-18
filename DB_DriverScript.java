//**********************************************************
//Date of Creation 	: Nov 15
//Author          	: Ram Kumar Sastha Lakshmanan
//Purpose          	: DB Automation
//Last Update On 	: Nov 2015
//Updated by       	: Nov 2015
//**********************************************************

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import oracle.jdbc.OracleTypes;

public class DB_DriverScript {
	//Report
	 public enum logType
	 {
	     warning, info, config, error, pass, fail; 
	 }
	 public static String color;
	 public static String status;
	 public static DateFormat rptTme;
	 public static Calendar cal;
	 public static String reprtTime;
	 public static String Teststatus = null;
	 public static Hashtable Appconfig = new Hashtable();
	 public static Hashtable<String, String> ht = new Hashtable<String, String>();
	public static void main(String[] args) throws BiffException, IOException, ClassNotFoundException, SQLException, RowsExceededException, WriteException {
		// TODO Auto-generated method stub
		Connection conn = null, conn1 = null, conn2 = null, conn3 = null, conn4=null, conn5=null;
		Statement stmt = null, stmt1 = null, stmt2 = null, stmt3, stmt4;
		ResultSet rs = null, rs1 = null;
		Appconfig = appConfig(Appconfig);
		DB_reportgenerator rg = new DB_reportgenerator();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");	
		String cal1 = null;
		try{
		cal = Calendar.getInstance(); 
		cal1 = dateFormat.format(cal.getTime());}catch(Exception e){System.out.println(e.getMessage());}
		baselog(cal1);
		func_log("DB Automation", cal1); 
		
		log("info", "<<DB AUTOMATION STARTED >>");
		
		FileInputStream fis = new FileInputStream("C:\\Selenium\\Datasheet\\AppName-DB_Automation.xls");
		Workbook wb = Workbook.getWorkbook(fis);
		Sheet s = wb.getSheet(1);
		int i = 1;
		String DB_URL = null, USER = null, PASS = null, sql = null, sql1 = null;
		DB_URL = (String)Appconfig.get("DB_URL");
		USER = (String)Appconfig.get("UserID");
		PASS = (String)Appconfig.get("Password");
		String[] columnNames, columnValues, columnValues_DB, ColumnIndex;
		while(i<s.getRows())
		{
			if(s.getCell(3,i).getContents().toUpperCase().contentEquals("Y"))
			{
				switch(s.getCell(1,i).getContents().toUpperCase())
				{
					case "SELECT_QUERY":
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
						stmt = (Statement) ((java.sql.Connection) conn).createStatement();
						sql1 = s.getCell(2,i).getContents();
						sql = queryManipulator(sql1, ht); 
						log("info", "Select Query execution - "+ sql);
						rs = ((java.sql.Statement) stmt).executeQuery(sql);
						columnNames = s.getCell(2,(i+1)).getContents().split(",");
						log("info", "Column Names - "+ columnNames);
						columnValues = s.getCell(2,(i+2)).getContents().split(",");
						log("info", "Column Values - "+ columnValues);
						columnValues_DB = new String[columnValues.length];
						int counter = 0;
						boolean result;
						while(rs.next())
						{
							for(int j=0; j<columnNames.length; j++)
							{
								//String value = rs.getString("\""+columnNames[j]+"\"");
								columnValues_DB[j] = rs.getString(j+1);
								System.out.println(columnValues_DB[j]);
								if(columnValues[j].contains("/"))
								{
									String[] columnMultiValues = columnValues[j].split("/");
									
									result = false;
									for(int k=0; k<columnMultiValues.length; k++)
									{
										if(columnMultiValues[k].contentEquals(columnValues_DB[j]))
										{
											result = true;
											counter++;
											System.out.println(columnValues[j]+"="+columnMultiValues[k]+" Passed");
											log("pass", columnValues[j]+"="+columnMultiValues[k]+" Passed");
											rg.report("DB Automation", false, columnValues[j]+"="+columnMultiValues[k]+" Passed", "PASS", "", "");
											break;
										}
									}
									if(result == false)
									{
										System.out.println(columnValues[j]+"="+columnValues_DB[j]+ " Failed");
										log("fail", columnValues[j]+"="+columnValues_DB[j]+ " Failed");
										rg.report("DB Automation", false, columnValues[j]+"="+columnValues_DB[j]+ " Failed", "FAIL", "CRITICAL", "");
									}
								}
								else
								{
									if(columnValues[j].contentEquals(columnValues_DB[j]))
									{
										counter++;
										System.out.println(columnValues[j]+"="+columnValues_DB[j]+" Passed");
										log("pass", columnValues[j]+"="+columnValues_DB[j]+" Passed");
										rg.report("DB Automation", false, columnValues[j]+"="+columnValues_DB[j]+" Passed", "PASS", "", "");
									}
									else
									{
										System.out.println(columnValues[j]+"="+columnValues_DB[j]+" Failed");
										log("fail", columnValues[j]+"="+columnValues_DB[j]+" Failed");
										rg.report("DB Automation", false, columnValues[j]+"="+columnValues_DB[j]+" Failed", "FAIL", "CRITICAL", "");
									}
								}
							}
							if(counter==columnNames.length)
								break;
						}
						i=i+3;
					break;
					case "STORE_VALUE":
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn4 = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
						stmt3 = (Statement) ((java.sql.Connection) conn4).createStatement();
						sql1 = s.getCell(2,i).getContents();
						sql = queryManipulator(sql1, ht); 
						log("info", "Select Query execution - "+ sql);
						rs = ((java.sql.Statement) stmt3).executeQuery(sql);
						columnNames = s.getCell(2,(i+1)).getContents().split(",");
						log("info", "Column Names - "+ columnNames);
						while(rs.next())
						{
							for(int j=0; j<columnNames.length; j++)
							{
								String value = rs.getString(j+1);
								ht.put(columnNames[j], value);
								System.out.println(ht.get(columnNames[j]));
							}
							break;
						}
						log("pass", "Store Value Passed");
						rg.report("DB Automation", false, "Store Value Passed", "PASS", "", "");
						i=i+2;
					break;
					case "UPDATE_QUERY":
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn1 = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
						stmt1 = (Statement) ((java.sql.Connection) conn1).createStatement();
						sql1 = s.getCell(2,i).getContents();
						sql = queryManipulator(sql1,ht);
						log("info", "Update Query execution - "+ sql);
						System.out.println("sql update: "+sql);
						System.out.println("sql length"+sql.length());
						stmt1.executeUpdate(sql);
						conn1.commit();
						System.out.println("Update Query Passed");
						log("pass", "Update Query Passed");
						rg.report("DB Automation", false, "Update Query Passed", "PASS", "", "");
						i=i+1;
					break;
					case "INSERT_QUERY":
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn3 = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt2 = conn3.createStatement();
						sql1 = s.getCell(2,i).getContents();
						sql = queryManipulator(sql1, ht);
						stmt2.executeUpdate(sql);
						conn3.commit();
						System.out.println("Insert Query Executed");
						log("pass", "Update Query Passed");
						rg.report("DB Automation", false, "Update Query Passed", "PASS", "", "");
						i=i+1;
					break;
					case "DELETE_QUERY":
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn5 = DriverManager.getConnection(DB_URL, USER, PASS);
						stmt4 = conn5.createStatement();
						sql1 = s.getCell(2,i).getContents();
						sql = queryManipulator(sql1, ht);
						stmt4.executeUpdate(sql);
						conn5.commit();
						System.out.println("Insert Query Executed");
						log("pass", "Update Query Passed");
						rg.report("DB Automation", false, "Update Query Passed", "PASS", "", "");
						i=i+1;
					break;
					case "PROCEDURE":
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn2 = (Connection) DriverManager.getConnection(DB_URL,USER,PASS);
						String proc_name = s.getCell(2,i).getContents();
						CallableStatement cstmt = conn2.prepareCall("{call "+ proc_name +"}");
						log("info", "Procedure execution - "+ proc_name);
						int count = 0;
						for (int a=0; a < proc_name.length(); a++)
					    {
					        if (proc_name.charAt(a) == '?')
					        {
					             count++;
					        }
					    }
						for (int b=1; b < count; b++)
					    {
							i=i+1;
							log("info", "Parameter - "+b+ " "+s.getCell(2,i).getContents());
							if(s.getCell(1,i).getContents().toUpperCase().contentEquals("STRING"))
							{
								if(s.getCell(2,i).getContents().toUpperCase().contentEquals("NULL"))
								{
									cstmt.setString(b, null);
								}
								else
								{
									String input = queryManipulator(s.getCell(2,i).getContents(), ht);
									cstmt.setString(b, input);
								}
							}
							else if(s.getCell(1,i).getContents().toUpperCase().contentEquals("INT"))
							{
								String input = queryManipulator(s.getCell(2,i).getContents(), ht);
								cstmt.setInt(b, Integer.parseInt(input));
							}
							else if(s.getCell(1,i).getContents().toUpperCase().contentEquals("FLOAT"))
							{
								String input = queryManipulator(s.getCell(2,i).getContents(), ht);
								cstmt.setFloat(b, Float.parseFloat(input));
							}
							else if(s.getCell(1,i).getContents().toUpperCase().contentEquals("DOUBLE"))
							{
								String input = queryManipulator(s.getCell(2,i).getContents(), ht);
								cstmt.setDouble(b, Double.parseDouble(input));
							}
							else if(s.getCell(1,i).getContents().toUpperCase().contentEquals("LONG"))
							{
								String input = queryManipulator(s.getCell(2,i).getContents(), ht);
								cstmt.setLong(b, Long.parseLong(input));
							}
					    }
						cstmt.registerOutParameter(count, OracleTypes.CURSOR);
						cstmt.execute();
						rs1 = (ResultSet) cstmt.getObject(count);
						int counter1 = 0;
						ColumnIndex = s.getCell(2,(i+1)).getContents().split(",");
						log("info", "Column Index - "+ ColumnIndex);
						columnValues = s.getCell(2,(i+2)).getContents().split(",");
						log("info", "Column Values - "+ columnValues);
						columnValues_DB = new String[columnValues.length];
						int counter2=0;
						while(rs1.next())
						{
							counter1++;
							
							for(int j=0; j<ColumnIndex.length; j++)
							{
								//String value = rs.getString("\""+columnNames[j]+"\"");
								int column_index = Integer.parseInt(ColumnIndex[j]);
								System.out.println("column_index: "+column_index);
								System.out.println("ColumnIndex[j]: "+ColumnIndex[j]);
								columnValues_DB[j] = rs1.getString(column_index);
								System.out.println(columnValues_DB[j]);
								if(columnValues[j].contains("/"))
								{
									String[] columnMultiValues = columnValues[j].split("/");
									
									result = false;
									for(int k=0; k<columnMultiValues.length; k++)
									{
										if(columnMultiValues[k].contentEquals(columnValues_DB[j]))
										{
											result = true;
											counter2++;
											System.out.println(columnValues[j]+"="+columnMultiValues[k]+" Passed");
											log("pass", columnValues[j]+"="+columnMultiValues[k]+" Passed");
											rg.report("DB Automation", false, columnValues[j]+"="+columnMultiValues[k]+" Passed", "PASS", "", "");
											break;
										}
									}
									if(result == false)
									{
										System.out.println(columnValues[j]+"="+columnValues_DB[j]+ " Failed");
										log("fail", columnValues[j]+"="+columnValues_DB[j]+ " Failed");
										rg.report("DB Automation", false, columnValues[j]+"="+columnValues_DB[j]+ " Failed", "FAIL", "CRITICAL", "");
									}
								}
								else
								{
									if(columnValues[j].contentEquals(columnValues_DB[j]))
									{
										counter2++;
										System.out.println(columnValues[j]+"="+columnValues_DB[j]+" Passed");
										log("pass", columnValues[j]+"="+columnValues_DB[j]+" Passed");
										rg.report("DB Automation", false, columnValues[j]+"="+columnValues_DB[j]+" Passed", "PASS", "", "");
									}
									else
									{
										System.out.println(columnValues[j]+"="+columnValues_DB[j]+" Failed");
										log("fail", columnValues[j]+"="+columnValues_DB[j]+" Failed");
										rg.report("DB Automation", false, columnValues[j]+"="+columnValues_DB[j]+" Failed", "FAIL", "CRITICAL", "");
									}
								}
							}
							if(counter2==ColumnIndex.length)
								break;
							
						}
						if(counter1>0)
						{
							System.out.println("Procedure execution has returned "+counter1+" records - Passed");
							log("pass", "Procedure execution has returned "+counter1+" records - Passed");
							rg.report("DB Automation", false, "Procedure execution has returned "+counter1+" records - Passed", "PASS", "", "");
						}
						else
						{
							System.out.println("Procedure execution has returned "+counter1+" records - Failed");
							log("fail", "Procedure execution has returned "+counter1+" records - Failed");
							rg.report("DB Automation", false, "Procedure execution has returned "+counter1+" records - Failed", "FAIL", "CRITICAL", "");
						}
						i=i+3;
					break;
				}
			}
			else
				i++;
		}
		Runtime.getRuntime().exec("WScript C:/Selenium/library/mail.vbs \"DB_Automation\"");
	}
	
	public static Hashtable appConfig(Hashtable Appconfig) throws BiffException, IOException
	{
		FileInputStream fis = new FileInputStream("C:\\Selenium\\Datasheet\\AppName-DB_Automation.xls");
		Workbook wb = Workbook.getWorkbook(fis);
		Sheet s = wb.getSheet(0);
		
		Appconfig.put(s.getCell(0,0).getContents(), s.getCell(0,1).getContents());
		Appconfig.put(s.getCell(1,0).getContents(), s.getCell(1,1).getContents());
		Appconfig.put(s.getCell(2,0).getContents(), s.getCell(2,1).getContents());

		wb.close();fis.close();
		return Appconfig;
	}
	
	public static String queryManipulator(String sql, Hashtable ht){
		int counter_dollar = 0;
		int length = sql.length();
		char sqlArr[] = new char[length*2];
		char tempArr[] = new char[length*2];
		sqlArr = sql.toCharArray();
		int pos=0;
		for(int i=0; i<length; i++){
			if(sqlArr[i]=='$')
			{
				counter_dollar++;
			}
		}
		for(int j=0; j<counter_dollar; j++){
			pos = index(sqlArr);
			System.out.println("pos: "+pos);
			stringManipulator(sqlArr, tempArr, pos, ht);
			sqlArr = tempArr ;
		}
		System.out.println("SQL Array: "+String.valueOf(sqlArr));
		return String.valueOf(sqlArr);
	}
	
	public static void stringManipulator(char[] sqlArr, char[] tempArr, int pos, Hashtable ht){
		char[] variable = new char[20];
		for(int k=0; k<pos; k++){
			tempArr[k] = sqlArr[k];
		}
		int var_counter = 0;
		for(int k=(pos+1); k<sqlArr.length; k++){
			if((sqlArr[k]!=' ')&&(sqlArr[k]!='\''))
				variable[var_counter] = sqlArr[k];
			else
				break;
			var_counter++;
		}
		variable[var_counter]='\0';
		String variable1 = String.valueOf(variable);
		System.out.println("variable: "+variable1);
		System.out.println("variable length: "+variable1.length());
		System.out.println(ht.get("mon"));
		String value = (String) ht.get(variable1.trim());
		System.out.println(ht);
		//String value = (String) ht.get("\""+variable1+"\"");
		System.out.println("value: "+value);
		char[] valueArr = value.toCharArray();
		for(int k=0, l=pos; k<valueArr.length; k++, l++){
			tempArr[l] = valueArr[k];
		}
		System.out.println("valueArr.length"+valueArr.length);
		System.out.println("variable.length"+variable1.trim().length());
		for(int k=(pos+valueArr.length), l = (pos + variable1.trim().length() + 1); l<sqlArr.length; k++,l++){
			tempArr[k] = sqlArr[l];
		}
		tempArr[sqlArr.length - variable.length + valueArr.length] = '\0';
		
	}
	
	public static int index(char array[]){
		int index=0;
		for(int i=0; i<array.length; i++){
			if(array[i]=='$')
			{
				index = i;
				break;
			}
		}
		System.out.println("index: "+index);
		return index;
	}
	
	public static void func_log(String SheetName, String cal1) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");	
		cal = Calendar.getInstance();
		//String cal1 = dateFormat.format(cal.getTime());
		rptTme = new SimpleDateFormat("EEEE yyyy:MM:dd hh:mm:ss a");
		reprtTime = rptTme.format(cal.getTime());
		String ExecLogger = "C:\\Selenium\\Results\\DB Automation "+cal1+".html";
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
		BW.write("<<<< "+SheetName);
		BW.write("STARTED >>>> </font>");	
		BW.close();
		FW.close();
} //func_log ends
	
	 public static void log(String logLevel,String msg) throws IOException {
		  	cal = Calendar.getInstance();
		  	rptTme = new SimpleDateFormat("EEEE yyyy:MM:dd hh:mm:ss a");
			reprtTime = rptTme.format(cal.getTime());
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");		
			String cal1 = dateFormat.format(cal.getTime());
			String ExecLogger = "C:\\Selenium\\Results\\DB Automation "+cal1+".html";
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
			case pass:
				color = "blue";
				status = "[VALIDATION PASS]";
				break;
			case fail:
				color = "red";
				status = "[VALIDATION FAIL]";
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
	 
	 public static void baselog(String cal1) throws IOException {
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");	
			cal = Calendar.getInstance();
			//String cal1 = dateFormat.format(cal.getTime());
			rptTme = new SimpleDateFormat("EEEE yyyy:MM:dd hh:mm:ss a");
			reprtTime = rptTme.format(cal.getTime());
			String ExecLogger = "C:\\Selenium\\Results\\DB Automation "+cal1+".html";
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
}
