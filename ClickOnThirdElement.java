package day3;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class ClickOnThirdElement {

	public static void main(String[] args) {
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
		driver.get("http://www.crystalcruises.com/Calendar.aspx");
		
//		find all "Get-a-Quote" links 
		List<WebElement> allQuotes = driver.findElements(By.linkText("Get-a-Quote"));
		
//		find the count of Get a quote links
		System.out.println("Total count of Get a Quote links : " + allQuotes.size());
		
//		click on the third Get a quote link
//		allQuotes.get(2).click();
		
//		click on the last link
		allQuotes.get(allQuotes.size()-1).click();
	
	
	
	
	
	
	
	
	
	
	
	
	
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
