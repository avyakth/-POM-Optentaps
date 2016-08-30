package day3;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class SelectFromDropDown {

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
		driver.get("https://www.services.irctc.co.in/cgi-bin/bv60.dll/irctc/services/register.do?click=true");
	
//		Creating an object of Select Class - Also referring to the dropdown list in the UI
		Select dropDown = new Select(driver.findElement(By.name("question")));
		
//		Selecting a dropdown value by index
		dropDown.selectByIndex(5);
		
//		Waiting for 5 secs
		Thread.sleep(5000);
		
//		Selecting a dropdown value by Value
		dropDown.selectByValue("2");
		
		Thread.sleep(5000);
		
//		Selecting a dropdown value by visible text
		dropDown.selectByVisibleText("What is your favorite pass-time?");
		
		
		List<WebElement> allOptions = dropDown.getOptions();
		
		System.out.println("Total number of options in the dropdown : " + allOptions.size());
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
