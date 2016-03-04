package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import wrappers.OpentapsWrappers;

public class TC05_CreateContact extends OpentapsWrappers{
	
	@BeforeClass
	public void startTestCase(){
		browserName 	= "firefox";
		dataSheetName 	= "TC05_CreateContact";
		testCaseName 	= "TC05-Create Contact (POM)";
		testDescription = "Create Contacts in Opentaps using POM framework";
	}
	
	
	@Test(dataProvider="fetchData")
	public void createContact(String username,String password, String loginName,  String firstName, String lastName) {
		new LoginPage()
		.enterUserName(username)
		.enterPassword(password)
		.clickLogin()
		.verifyLoggedinUserName(loginName)
		.clickCrmsfa()
		.navigateToContactPage()
		.clickCreateContact()
		.enterFirstName(firstName)
		.enterLasttName(lastName)
		.clickCreateContact()
		.verifyFirstName(firstName)
		;
	}
		
}
