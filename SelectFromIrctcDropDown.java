package day3;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class SelectFromIrctcDropDown {

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
		
//		find the number of dropdowns
		List<WebElement> allDropDowns = driver.findElements(By.tagName("select"));
		
		System.out.println("Count of dropdowns" + allDropDowns.size());
		
//		Go to the LAST dropdown
//		Select the last value in the dropdown
		
//		allDropDowns.size()-1
		
		Select lastDropDown = new Select(allDropDowns.get(allDropDowns.size()-4));
//		lastDropDown.selectByIndex(lastDropDown.getOptions().size()-1);
		
		List<WebElement>  allOptions = lastDropDown.getOptions();
		
		for (WebElement webElement : allOptions) {
			System.out.println(webElement.getText());
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}

}
