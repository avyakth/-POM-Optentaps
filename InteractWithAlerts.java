package day3;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class InteractWithAlerts {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
//		launch firefox
//		FirefoxDriver driver = new FirefoxDriver();
		
//		setting chromedriver path - system property
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		
//		Launch Chrome
		ChromeDriver driver = new ChromeDriver();
		
//		InternetExplorerDriver driver = new InternetExplorerDriver();
		
//		maximize the browser
		driver.manage().window().maximize();
		
//		Set default waiting time for finding an element (after the page loads)
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);		

//		navigate to the url
		driver.get("file:///C:/Users/Babu/Desktop/TL/Alert.html");
		
//		Click Alert box button
//		driver.findElement(By.xpath("//*[@id='contentblock']/section/div[1]/div/div/button")).click();

//		Click Confirm box button
//		driver.findElement(By.xpath("//*[@id='contentblock']/section/div[2]/div/div/button")).click();
	
//		Click Prompt box button
//		driver.findElement(By.xpath("//*[@id='contentblock']/section/div[3]/div/div/button")).click();
		
		
		Thread.sleep(5000);	
		
		Alert newAlert = driver.switchTo().alert();
		
//		newAlert.sendKeys("Google");
		
		System.out.println(newAlert.getText());
		Thread.sleep(5000);
				
		
//		Clicking OK on the alert
		newAlert.accept();
	
		
//		Clicking Cancel on the alert
//		newAlert.dismiss();
				
		Thread.sleep(5000);	

//		Getting title of the webpage
		System.out.println(driver.getTitle());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
