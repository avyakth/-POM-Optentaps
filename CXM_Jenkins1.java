import java.io.IOException;
import java.util.concurrent.TimeUnit;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.junit.runner.JUnitCore;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.net.URL;

public class CXM_Jenkins1 {
	private String hubUrl="http://oneselenium.verizon.com:4444/wd/hub";
	
	public WebDriver driver;
	String baseUrl = "http://114.8.144.17/cofeecxmdit_6.15.10.33/index.jsp";
	

	@BeforeTest
	public void setUp() throws Exception
	{
		/* ProfilesIni profile = new ProfilesIni();
		 FirefoxProfile ffprofile = profile.getProfile("default");	
		 ffprofile.setPreference("toolkit.startup.max_resumed_crashes", "-1");
		 DesiredCapabilities caps = DesiredCapabilities.firefox(); 
		 caps.setBrowserName("firefox");
		 caps.setPlatform(Platform.VISTA);
         //caps.setCapability(FirefoxDriver.PROFILE, ffprofile);
         //caps.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
         //caps.setPlatform(Platform.WINDOWS);
        
         driver=new FirefoxDriver(ffprofile); 
         //driver=new RemoteWebDriver(new URL(hubUrl),caps );
         driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);*/
		ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<RemoteWebDriver>();
		FirefoxProfile ffprofile = new FirefoxProfile();
		 DesiredCapabilities caps = DesiredCapabilities.firefox(); 
		 caps.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
		 threadDriver.set(new RemoteWebDriver(new URL(hubUrl), caps));
		 driver = threadDriver.get();
		 driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		 driver.manage().deleteAllCookies();
		 driver.manage().window().maximize();
	}
	
	@Test
	public void testLogin() throws BiffException, IOException, RowsExceededException, WriteException {
		System.out.println("Login Test Case executed");
		driver.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		driver.findElement(By.xpath(".//*[@id='btnAgree']")).click();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath(".//*[@id='userId']")).sendKeys("v006593");
		driver.findElement(By.xpath(".//*[@id='password']")).sendKeys("123");
		driver.findElement(By.xpath(".//*[@id='btnLogin']")).click();
	}
	
	@Test(dependsOnMethods = {"testLogin"})
	public void testDashboard() throws BiffException, IOException, RowsExceededException, WriteException {
		System.out.println("Dashboard Test Case executed");
		driver.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[2]/div[2]/form/input")).click();
		/*WebDriverWait wait = new WebDriverWait(driver, 120);
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("HomeButton"))); 
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("HomeButton")));*/
		System.out.println(driver.findElement(By.id("HomeButton")));
		Assert.assertEquals(true, driver.findElement(By.id("HomeButton")).isDisplayed());
	}
	@Test(dependsOnMethods = {"testDashboard"})
	public void testEWOSummaryDetailsPage() throws BiffException, IOException, RowsExceededException, WriteException {
		System.out.println("Engineering Work Order Summary details page Test Case executed");
		driver.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("ProfileButton")).click();
		Assert.assertEquals(true, driver.findElement(By.id("JobsButton")).isDisplayed());
	}
	
	@Test(dependsOnMethods = {"testEWOSummaryDetailsPage"})
	public void testEWOSearchResults() throws BiffException, IOException, RowsExceededException, WriteException {
		System.out.println("EWO Search Results Test Case executed");
		driver.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);
		driver.findElement(By.id("JobsButton")).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("JOBSFRAME");
		try{
			driver.findElement(By.xpath("html/body/div[7]/div[2]/div[5]/a/span[2]")).click();
		}catch(Exception e){
		}
		driver.findElement(By.id("EWONumber")).sendKeys("1A0AG8Y");
		driver.findElement(By.xpath("//*[@id='EWOState' and @tabindex='3']")).click();
		driver.switchTo().defaultContent();
		driver.switchTo().frame("JOBSFRAME");
		driver.findElement(By.id("jurFilter")).sendKeys("MA");
		driver.findElement(By.xpath(".//*[@id='JurState']/li[32]/label")).click();
		driver.findElement(By.xpath("html/body/div[11]/div[2]/div[2]/a[2]/span[2]")).click();
		driver.findElement(By.id("btnGetEWO")).click();
	}
	
	@Test(dependsOnMethods = {"testEWOSearchResults"})
	public void testJobConfirmWorkOrderDownload() throws BiffException, IOException, RowsExceededException, WriteException {
		System.out.println("Job Confirm Work Order Download Test Case executed");
		driver.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);
		driver.findElement(By.xpath("html/body/div[7]/div[1]/div/div/div[5]/div[2]/div/div/div")).click();
		driver.switchTo().defaultContent();
		Assert.assertEquals(true, driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[2]/div[1]/h2")).isDisplayed());
	}
	
	@Test(dependsOnMethods = {"testJobConfirmWorkOrderDownload"})
	public void testEngineeringWorkOrderSummaryPopup() throws BiffException, IOException, RowsExceededException, WriteException {
		System.out.println("Engineering Work Order Summary Popup Test Case executed");
		driver.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[2]/div[2]/form/input[2]")).click();
		driver.switchTo().defaultContent();
		Assert.assertEquals(true, driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[2]/div[1]/h2")).isDisplayed());
	}
	
	@Test(dependsOnMethods = {"testEngineeringWorkOrderSummaryPopup"})
	public void testEWOSummary() throws BiffException, IOException, RowsExceededException, WriteException {
		System.out.println("EWO Summary Test Case executed");
		driver.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("JOBSFRAME");
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='jobdetail']/div[1]")));
		Assert.assertEquals(true, driver.findElement(By.xpath(".//*[@id='jobdetail']/div[1]")).isDisplayed());
	}
	
	@Test(dependsOnMethods = {"testEWOSummary"})
	public void testPrints() throws BiffException, IOException, RowsExceededException, WriteException {
		System.out.println("Prints Test Case executed");
		driver.manage().timeouts().implicitlyWait(600, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='jobdetail']/div[3]")));
		Assert.assertEquals(true, driver.findElement(By.xpath(".//*[@id='jobdetail']/div[3]")).isDisplayed());
	}
	public static void main (String[] args) throws Exception {
		JUnitCore.main("packagename.AppLaunchable");
		}
}
