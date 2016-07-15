//**********************************************************
//Date of Creation 	: April 15
//Author          	: Ram Kumar Sastha Lakshmanan
//Purpose          	: Create log for each step
//Last Update On 	:
//Updated by       	:
//**********************************************************
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Appium_eventlogger {
	public enum logType
	 {
	     warning, info, config, error, validation; 
	 }
	String color,status;
	DateFormat rptTme = new SimpleDateFormat("EEEE yyyy:MM:dd hh:mm:ss a");
	Calendar cal = Calendar.getInstance();
	String reprtTime = rptTme.format(cal.getTime());
	public void baselog() throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");		
		String cal1 = dateFormat.format(cal.getTime());
		String ExecLogger = "C:\\Appium\\Results\\"+Appium_appselection.selectedApp+" ExecLog "+cal1+".html";	
		File resDir = new File("C:\\Appium\\Results");
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
		} //if2 ends
	
	
} //baselog ends
	public void func_log() throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");		
		String cal1 = dateFormat.format(cal.getTime());
		String ExecLogger = "C:\\Appium\\Results\\"+Appium_appselection.selectedApp+" ExecLog "+cal1+".html";
		FileWriter FW = new FileWriter(ExecLogger,true); 
		BufferedWriter BW = new BufferedWriter(FW);
		BW.write("<br><TABLE cellSpacing=0 cellPadding=0 width=100%");
		BW.write("border=0><TBODY><tr height=24><TD vAlign=center class=subheading align=left width=100%");
		BW.write("<font size=2><B>");
		BW.write("Functionality : "+Appium_driverscript.crntSheet);
		BW.write("</B></font></TD></tr></TBODY></TABLE>");
		BW.write("<br><font color = black size=2>--> [");
		BW.write(reprtTime);
		BW.write("] [INFO] - ");
		BW.write("<<<< "+Appium_driverscript.crntSheet+" Functionality");
		BW.write("STARTED >>>> </font>");	
		BW.close();
		FW.close();
} //func_log ends
	public void log(String logLevel,String msg) throws IOException {
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd");		
		String cal1 = dateFormat.format(cal.getTime());
		String ExecLogger = "C:\\Appium\\Results\\"+Appium_appselection.selectedApp+" ExecLog "+cal1+".html";
		FileWriter FW = new FileWriter(ExecLogger,true); 
		BufferedWriter BW = new BufferedWriter(FW);
		DateFormat rptTme = new SimpleDateFormat("EEEE yyyy:MM:dd hh:mm:ss a");
		Calendar cal = Calendar.getInstance();
		String reprtTime = rptTme.format(cal.getTime());		
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
			if (Appium_driverscript.Teststatus.equals("PASS")){
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
	} //log ends
} //eventlogger ends
