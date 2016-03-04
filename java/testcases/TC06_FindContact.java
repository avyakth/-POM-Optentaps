package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import wrappers.OpentapsWrappers;

public class TC06_FindContact extends OpentapsWrappers{
	
	@BeforeClass
	public void startTestCase(){
		browserName 	= "firefox";
		dataSheetName 	= "TC06_FindContact";
		testCaseName 	= "TC06 - FindContact (POM)";
		testDescription = "Find Contact in Opentaps using POM framework";
	}
	
	
	@Test(dataProvider="fetchData")
	public void findContact(String username,String password,String loginName,String firstName,String lastName) {
		new LoginPage()
		.enterUserName(username)
		.enterPassword(password)
		.clickLogin()
		.verifyLoggedinUserName(loginName)
		.clickCrmsfa()
		.navigateToContactPage()
		.navigateToFindContactPage()
		.enterFirstName(firstName)
		.enterLastName(lastName)		
		.clickFindContactbutton()
		.clickSearchName()
		.verifyFirstName(firstName);
	}
}
