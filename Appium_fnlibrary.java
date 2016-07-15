//**********************************************************
//Date of Creation 	: April 15
//Author          	: Ram Kumar Sastha Lakshmanan
//Purpose          	: List of function libraries
//Last Update On 	:
//Updated by       	:
//**********************************************************
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Appium_fnlibrary {
	static Appium_eventlogger el = new Appium_eventlogger();
	
	public void waitforelement(String elemPropAttr) throws IOException, AWTException, InterruptedException, RowsExceededException, WriteException, BiffException {	
		Appium_reportgenerator rg = new Appium_reportgenerator();
				try{
					WebDriverWait wait1 = new WebDriverWait(Appium_driverscript.driver,20);
					wait1.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elemPropAttr)));
				} catch(Exception e ){		
					if(!Appium_driverscript.ElementAction.toUpperCase().contains("IFEXIST")){
						el.log("error","Object identification timeout");
						screencapture();
						el.log("error",e.getMessage());
						Appium_driverscript.Teststatus = "FAIL";
						Appium_driverscript.ErrDesc = e.getMessage();
						Appium_driverscript.errorcriticality = "CRITICAL";
						rg.report();
						
						String stepinfo = "Execution Interupted";
						Appium_appselection.TxtAreaStep.setText(stepinfo);
						Thread.sleep(1000);    
						stepinfo = "Closing input sheet";
						Appium_appselection.TxtAreaStep.setText(stepinfo);				  
						Appium_driverscript.wb.close();
				      Thread.sleep(1000);
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
				      
						
						} else {
							el.log("warning","Object identification timeout");
							screencapture();
							el.log("warning",e.getMessage());
					}
				}
	}	//waitforelement ends
	

	public static void screencapture() throws IOException, AWTException {		
		File resDir = new File("C:\\Appium\\Results\\Screenshots");
		if (!resDir.exists()) { //if1
			resDir.mkdir();
		} //if1 ends
		File resDir1 = new File("C:\\Appium\\Results\\Screenshots\\"+Appium_appselection.selectedApp);
		if (!resDir1.exists()) { //if2
			resDir1.mkdir();
		} //if2 ends
		
		
		DateFormat scrcpttme = new SimpleDateFormat("yyyy_MM_dd hh_mm_ss a");
		Calendar cal = Calendar.getInstance();
		String scrcptTime = scrcpttme.format(cal.getTime());
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle screenRectangle = new Rectangle(screenSize);	
		Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        ImageIO.write(image, "png", new File(resDir1+"\\"+Appium_appselection.selectedApp+"-"+Appium_driverscript.Module+" "+scrcptTime+".jpg"));	
        el.log("info","Step "+Appium_driverscript.stepNo+": Screen captured - image name : "+resDir1+"\\"+Appium_appselection.selectedApp+"-"+Appium_driverscript.Module+" "+scrcptTime+".jpg");
	} //screencapture ends
	

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
    
    
    public static void keyboard(String inpstring) throws AWTException, InterruptedException {    	
    	int l;
    	String[] keybsplit;
    	if (inpstring.contains("+")) 
    	{
    		keybsplit = inpstring.split("\\+");
    		for(l=0;l<keybsplit.length;l++) 
    		{
    			keypress(keybsplit[l]);
    		}
    		for(l=keybsplit.length-1;l>=0;l--)
    		{
    			keyrelease(keybsplit[l]);
    		}
    	} 
    	else 
    	{
    		keypress(inpstring);
    		keyrelease(inpstring);
    	}
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
		

	public static void mouseclick(String inpkey) throws AWTException, InterruptedException {
    	Robot mouse = new Robot();  
    		Thread.sleep(50);
    		if (!inpkey.contains("(")){
		        switch (inpkey) {       
		        case "CLICK": 
		        	mouse.mousePress(InputEvent.BUTTON1_MASK);
	        		mouse.mouseRelease(InputEvent.BUTTON1_MASK);
	        	break;
		        case "DBLCLICK": case "DOUBLECLICK": 
		        	mouse.mousePress(InputEvent.BUTTON1_MASK);
	        		mouse.mouseRelease(InputEvent.BUTTON1_MASK);
	        		mouse.mousePress(InputEvent.BUTTON1_MASK);
	        		mouse.mouseRelease(InputEvent.BUTTON1_MASK); break;
		        case "RHTCLICK": case "RIGHTCLICK": 
		        	mouse.mousePress(InputEvent.BUTTON3_MASK);
		        	mouse.mouseRelease(InputEvent.BUTTON3_MASK);
		        break;		     
		        case "MOVE":
		        	Robot robot = new Robot(); 
		        	Point coordinates = Appium_driverscript.driver.findElement(By.xpath(Appium_driverscript.elemPropAttr)).getLocation();
		        	robot.mouseMove(coordinates.getX()+10,coordinates.getY()+70);
		        	Thread.sleep(100);
		        break;
		        
		        default:
		            throw new IllegalArgumentException("Cannot PRESS key " + inpkey);
		        }
	    	} else {
	    		Robot robot = new Robot(); 
	    		String mloc = inpkey.substring(inpkey.indexOf("(")+1, inpkey.lastIndexOf(")"));
	    		String[] mlocArray = mloc.split(",");
	    		robot.mouseMove(Integer.parseInt(mlocArray[0]),Integer.parseInt(mlocArray[1]));
	    	}
	        Thread.sleep(300);

    	}
////
} //fnlibrary ends
