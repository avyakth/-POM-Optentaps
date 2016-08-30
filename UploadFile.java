package regx;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class UploadFile {

	public static void main(String[] args) throws InterruptedException, AWTException {
		// opening Firefox browser
		FirefoxDriver driver = new FirefoxDriver();
		// maximize the browser
		driver.manage().window().maximize();
		// load the browser with url
		driver.get("http://my.naukri.com/manager/createacc2.php?&othersrcp=7304&wExp=N&gclid=CjwKEAiAmY-3BRDh7pjvg46p1iYSJADQ78gNKinI8UQq81_b394vuaJHgRSuZFAljcR667JrhsxmBhoCMz_w_wcB");
		
		Thread.sleep(10000);
		
		// Store the copied text in the clipboard
		StringSelection stringSelection = new StringSelection("c:\\waittypes.docx");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		   
		// Paste it using Robot class
		Robot robot = new Robot();
		
		// Enter to confirm it is uploaded
		 robot.keyPress(KeyEvent.VK_CONTROL);
         robot.keyPress(KeyEvent.VK_V);
         robot.keyRelease(KeyEvent.VK_V);
         robot.keyRelease(KeyEvent.VK_CONTROL);
         
         Thread.sleep(5000);
         robot.keyPress(KeyEvent.VK_ENTER);
         robot.keyRelease(KeyEvent.VK_ENTER);
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         

	}

}
