import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class CXM_Jenkins2 {
	protected ThreadLocal<RemoteWebDriver> threadDriver = null;
	//private String hubUrl="http://oneselenium.verizon.com:4444/wd/hub";
	private String hubUrl="http://114.9.172.236:4445/wd/hub";
	public WebDriver driver = null;
	String baseUrl = "http://114.8.144.17/cofeecxmdit_6.15.10.33/index.jsp";
	static String crntSheet = "";
	static String Teststatus = "";
	
	@BeforeTest
	public void setUp() throws Exception
	{
		try{
		ThreadLocal<RemoteWebDriver> threadDriver = new ThreadLocal<RemoteWebDriver>();
		 System.out.println("0");
		 DesiredCapabilities caps = DesiredCapabilities.firefox(); 
		 System.out.println("1");
		 FirefoxProfile ffprofile = new FirefoxProfile();
		 System.out.println("2");
		 caps.setCapability(FirefoxDriver.PROFILE, ffprofile);
		 System.out.println("3");
		 caps.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
		 System.out.println("4");
		 threadDriver.set(new RemoteWebDriver(new URL(hubUrl), caps));
		 System.out.println("5");
		 driver = threadDriver.get();
		 System.out.println("6");
		 driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		 System.out.println("7");
		 driver.manage().deleteAllCookies();
		 System.out.println("8");
		 driver.manage().window().maximize();
		 System.out.println("9");
		 Jenkins_eventlogger.baselog();
		 Jenkins_eventlogger.func_log();
		 
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
	
	@Test
	public void testLogin() throws BiffException, IOException, RowsExceededException, WriteException {
		crntSheet="Login";
		Teststatus = "PASS";
		System.out.println("Login Test Case executed");
		try{
		driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		driver.findElement(By.xpath(".//*[@id='btnAgree']")).click();
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath(".//*[@id='userId']")).sendKeys("v006593");
		driver.findElement(By.xpath(".//*[@id='password']")).sendKeys("123");
		driver.findElement(By.xpath(".//*[@id='btnLogin']")).click();
		Jenkins_eventlogger.log("info", "Login test case has been passed");
		}catch(Exception e){
			System.out.println(e.getMessage());
			Teststatus = "FAIL";
			Jenkins_eventlogger.log("error", "Login test case has been failed");
		}
	}
	
	@Test(dependsOnMethods = {"testLogin"})
	public void testDashboard() throws BiffException, IOException, RowsExceededException, WriteException {
		try{
			crntSheet="Dashboard";
			Teststatus = "PASS";
		System.out.println("Dashboard Test Case executed");
		driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
		driver.switchTo().defaultContent();
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[2]/div[2]/form/input")).click();
		/*WebDriverWait wait = new WebDriverWait(driver, 120);
		//wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("HomeButton"))); 
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id("HomeButton")));*/
		System.out.println(driver.findElement(By.id("HomeButton")));
		Assert.assertEquals(true, driver.findElement(By.id("HomeButton")).isDisplayed());
		Jenkins_eventlogger.log("info", "Dashboard test case has been passed");
		}catch(Exception e){
			System.out.println(e.getMessage());
			Teststatus = "FAIL";
			Jenkins_eventlogger.log("error", "Dashboard test case has been failed");
		}
	}
	@Test(dependsOnMethods = {"testDashboard"})
	public void testEWOSummaryDetailsPage() throws BiffException, IOException, RowsExceededException, WriteException {
		try{
			crntSheet="EWO Summary Details Page";
			Teststatus = "PASS";
		System.out.println("Engineering Work Order Summary details page Test Case executed");
		driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
		driver.switchTo().defaultContent();
		driver.findElement(By.id("ProfileButton")).click();
		Assert.assertEquals(true, driver.findElement(By.id("JobsButton")).isDisplayed());
		Jenkins_eventlogger.log("info", "EWO Summary Details test case has been passed");
		}catch(Exception e){
			System.out.println(e.getMessage());
			Teststatus = "FAIL";
			Jenkins_eventlogger.log("error", "EWO Summary Details Page test case has been failed");
		}
	}
	
	@Test(dependsOnMethods = {"testEWOSummaryDetailsPage"})
	public void testEWOSearchResults() throws BiffException, IOException, RowsExceededException, WriteException {
		try{
		System.out.println("EWO Search Results Test Case executed");
		driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
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
		Jenkins_eventlogger.log("info", "EWO Search Results test case has been passed");
		}catch(Exception e){
			System.out.println(e.getMessage());
			Teststatus = "FAIL";
			Jenkins_eventlogger.log("error", "EWO Search Results test case has been failed");
		}
	}
	
	@Test(dependsOnMethods = {"testEWOSearchResults"})
	public void testJobConfirmWorkOrderDownload() throws BiffException, IOException, RowsExceededException, WriteException {
		try{
		System.out.println("Job Confirm Work Order Download Test Case executed");
		driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
		System.out.println("1");
		driver.findElement(By.xpath("html/body/div[7]/div[1]/div/div/div[5]/div[2]/div/div/div")).click();
		System.out.println("2");
		driver.switchTo().defaultContent();
		System.out.println("3");
		Assert.assertEquals(true, driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[2]/div[1]/h2")).isDisplayed());
		System.out.println("4");
		Jenkins_eventlogger.log("info", "Job Confirm Work Order Download test case has been passed");
		}catch(Exception e){
			System.out.println(e.getMessage());
			Teststatus = "FAIL";
			Jenkins_eventlogger.log("error", "Job Confirm Work Order Download test case has been failed");
		}
	}
	
	@Test(dependsOnMethods = {"testJobConfirmWorkOrderDownload"})
	public void testEngineeringWorkOrderSummaryPopup() throws BiffException, IOException, RowsExceededException, WriteException {
		try{
		System.out.println("Engineering Work Order Summary Popup Test Case executed");
		driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
		System.out.println("5");
		driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[2]/div[2]/form/input[2]")).click();
		driver.switchTo().defaultContent();
		Assert.assertEquals(true, driver.findElement(By.xpath("html/body/table/tbody/tr[2]/td[2]/div[1]/h2")).isDisplayed());
		Jenkins_eventlogger.log("info", "Engineering Work Order Summary Popup test case has been passed");
		}catch(Exception e){
			System.out.println(e.getMessage());
			Teststatus = "FAIL";
			Jenkins_eventlogger.log("error", "Engineering Work Order Summary Popup test case has been failed");
		}
	}
	
	@Test(dependsOnMethods = {"testEngineeringWorkOrderSummaryPopup"})
	public void testEWOSummary() throws BiffException, IOException, RowsExceededException, WriteException {
		try{
		System.out.println("EWO Summary Test Case executed");
		driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("JOBSFRAME");
		WebDriverWait wait = new WebDriverWait(driver, 150);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='jobdetail']/div[1]")));
		Assert.assertEquals(true, driver.findElement(By.xpath(".//*[@id='jobdetail']/div[1]")).isDisplayed());
		}catch(Exception e){
			System.out.println(e.getMessage());
			Teststatus = "FAIL";
			Jenkins_eventlogger.log("error", "EWO Summary Test Case test case has been failed");
		}
	}
	
	@Test(dependsOnMethods = {"testEWOSummary"})
	public void testPrints() throws BiffException, IOException, RowsExceededException, WriteException {
		try{
		System.out.println("Prints Test Case executed");
		driver.manage().timeouts().implicitlyWait(150, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 150);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//*[@id='jobdetail']/div[3]")));
		Assert.assertEquals(true, driver.findElement(By.xpath(".//*[@id='jobdetail']/div[3]")).isDisplayed());
		}catch(Exception e){
			System.out.println(e.getMessage());
			Teststatus = "FAIL";
			Jenkins_eventlogger.log("error", "Prints Test Case test case has been failed");
		}
	}
	/*public static void main (String[] args) throws Exception {
		JUnitCore.main("packagename.AppLaunchable");
		}*/
}
