package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;

import wrappers.OpentapsWrappers;

public class TC01_CreateLead extends OpentapsWrappers{
	
	@BeforeClass
	public void startTestCase(){
		browserName 	= "firefox";
		dataSheetName 	= "TC01_CreateLead";
		testCaseName 	= "TC01-Create Lead (POM)";
		testDescription = "Create Lead in Opentaps using POM framework";
	}
	
	
	@Test(dataProvider="fetchData")
	public void createLead(String username,String password, String loginName, String companyName, String firstName, String lastName) {
		new LoginPage()
		.enterUserName(username)
		.enterPassword(password)
		.clickLogin()
		.verifyLoggedinUserName(loginName)
		.clickCrmsfa()
		.navigateToLeadPage()
		.navigateToCreateLeadsPage()
		.enterCompanyName(companyName)
		.enterFirstName(firstName)
		.enterLastName(lastName)
		.clickCreateLead()
		.verifyFirstName(firstName)
		;
	}
		
}
