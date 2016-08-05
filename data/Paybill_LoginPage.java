package com.myfios.mvn.pages;

import java.util.HashMap;

import com.myfios.mvn.functionallibrary.FunctionalLibrary;
import com.myfios.mvn.utilities.ReportStatus;

public class Paybill_LoginPage {
	String strDescription = "", strExpected = "", strActual = "", strFailed = "";
	private static String startTime;
	FunctionalLibrary driver;
	ReportStatus report;
	HashMap<String, String> data;
	String testId;

	// Constructor
	public Paybill_LoginPage(FunctionalLibrary driver, ReportStatus report, HashMap<String, String> data,
			String testId) {
		this.driver = driver;
		this.report = report;
		this.data = data;
		this.testId = testId;

	}

	// Open URL
	public void openURL(String url) throws Exception {
		System.out.println(url);
		strDescription = "Validation of Open URL";
		strExpected = "URL should be launched";
		strActual = "URL is launched successfully";
		strFailed = "URL launched Failed ";
		System.out.println("chekcing data"+url);
		driver.openWebsite(url);
		if (driver.elementExist("pblnkGetStarted", "")) {
			System.out.println("pblnkGetStarted");
			report.reportSteps(testId, strDescription, strExpected, strActual, "Passed", "");
		} else
			report.reportSteps(testId, strDescription, strExpected, strFailed, "Failed", "");
	}

	// Enter userid and click sign in button
	public void KeepMeSignedIN()throws Exception
	{
		strDescription = "Validation of Get Started Button";
		strExpected = "Get Started button should be present under Residential";
		strActual = "Get Started button is present under Residential";
		strFailed = "Get Started button is missing present under Residential";
		driver.click("pblnkGetStarted", "");
		Thread.sleep(5000);
		if(driver.elementExist("pbbtnMouseOverSign", ""))
			report.reportSteps(testId, strDescription, strExpected, strActual, "Passed", "");
		else
			report.reportSteps(testId, strDescription, strExpected, strFailed, "Failed", "");
		driver.mouseOver("pbbtnMouseOverSign", "");
		
		driver.switchToFrame("pbframeforHeaderPart", "");
		
		strDescription = "Validation of MouseOver Function";
		strExpected = "User ID field should be available";
		strActual = "MouseOver iss working, Since User ID field is visible";
		strFailed = "MouseOver is not working";
		Thread.sleep(10000);
	
		if(driver.elementExist("pbtxtSignInTopHeader", ""))
			report.reportSteps(testId, strDescription, strExpected, strActual, "Passed", "");
		else
			report.reportSteps(testId, strDescription, strExpected, strFailed, "Failed", "");

              driver.setText("pbtxtSignInTopHeader", "", data.get("UserName"));
	
		strDescription = "Keep me sign In flow";
		strExpected = "Keep me Signed IN radio button should be clicked";
		strActual = "Keep me Signed IN radio button is clicked";
		strFailed = "Keep me Signed IN radio button is Not Available"; 
		
		driver.click("PBlnkKeepMeSignedIn", ""); 
		
		if(driver.elementExist("pbbtnTopHeaderSign", ""))
			report.reportSteps(testId, strDescription, strExpected, strActual, "Passed", "");
		else
			report.reportSteps(testId, strDescription, strExpected, strFailed, "Failed", "");
		driver.click("pbbtnTopHeaderSign", "");
		driver.switchToParentFrame();
		
		strDescription = "Keep me sign In flow";
		strExpected = "Continue to Site button should be clicked If it is available";
		strActual = "Continue to Site button is clicked";
		strFailed = "Continue to Site button is Not Available, Enter password Page is loaded";
	
		if(driver.elementExist("pbtxtSecretQuestion", ""))
		{
			driver.setText("pbtxtSecretQuestion", "", data.get("SecretQuestion"));
			driver.click("pbbtnSecretQuestion", "");
			
			} 
		if(driver.elementExist("pbtxtEnterYourPassword", ""))
			report.reportSteps(testId, strDescription, strExpected, strActual, "Passed", "");
		else
			report.reportSteps(testId, strDescription, strExpected, strFailed, "Failed", "");
		
			strDescription = "Keep me sign In flow";
			strExpected = "Enter your Password and click continue button";
			strActual = "Password entered is correct and navigate to next page";
			strFailed = "Entered password is wrong, hence staying in the same page";
			
			driver.setText("pbtxtEnterYourPassword", "", data.get("Password"));
			
			driver.click("pbContinuebutton", "");
			
			
		}
}
