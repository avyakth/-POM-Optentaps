package collections;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GetTable {
	
	public static void main(String[] args) throws InterruptedException {
		
		FirefoxDriver driver = new FirefoxDriver();
		

		//		maximize the browser
		driver.manage().window().maximize();

		//		Set default waiting time for finding an element (after the page loads)
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);		

		//		navigate to the url
		driver.get("http://erail.in/");
		
		driver.findElementById("txtStationFrom").clear();
		driver.findElementById("txtStationFrom").sendKeys("SBC",Keys.TAB);
		driver.findElementById("txtStationTo").clear();
		driver.findElementById("txtStationTo").sendKeys("CBE",Keys.TAB);
		
		WebElement table = driver.findElementByXPath("//table[@class='DataTable TrainList']");
		
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
		
		System.out.println(tableRows.size());
		System.out.println(tableRows.get(0).findElements(By.tagName("td")).size());
		
		
		for (WebElement tableRow : tableRows) {
			List<WebElement> tableData = tableRow.findElements(By.tagName("td"));
			
			System.out.println(tableData.get(0).getText());
			System.out.println(tableData.get(1).getText());
		}
		
		tableRows.get(tableRows.size()-1).findElements(By.tagName("td")).get(1).click();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
	}

}
