package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;

import wrappers.OpentapsWrappers;

public class TC09_CreateOpportunity extends OpentapsWrappers{
	
	@BeforeClass
	public void startTestCase(){
		browserName 	= "firefox";
		dataSheetName 	= "TC09_CreateOpportunity";
		testCaseName 	= "TC01-Create Opportunity (POM)";
		testDescription = "Create Opportunity in Opentaps using POM framework";
	}
	
	
	@Test(dataProvider="fetchData")
	public void createOpportunity(String username,String password, String loginName, String OpportunityName, String IntialAccount, String Closingdate) {
		new LoginPage()
		.enterUserName(username)
		.enterPassword(password)
		.clickLogin()
		.verifyLoggedinUserName(loginName)
		.clickCrmsfa()
		.navigateToOpportunityPage()
		.navigateToCreateOpportunityPage()
		.enterOpprtunityName(OpportunityName)
		.enterIntialAccount(IntialAccount)
		.enterClosingaDate(Closingdate)
		.clickCreateOpportunity()
		.verifyOpportunityName(OpportunityName)
		;
	}
		
}
