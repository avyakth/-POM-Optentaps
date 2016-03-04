package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import wrappers.OpentapsWrappers;

public class TC07_EditContact extends OpentapsWrappers {
	
	@BeforeClass
	public void startTestCase(){
		browserName 	= "firefox";
		dataSheetName 	= "TC07_EditContact";
		testCaseName 	= "TC07 -EditContact(POM)";
		testDescription = "Find Contact in Opentaps using POM framework";
	}
	@Test(dataProvider="fetchData")
	public void editContact(String username,String password,String loginName,String firstName,String lastName, String firstName1,String lastName1) {
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
		.clickEditButton()
		.enterFirstName(firstName1)
		.enterlastName(lastName1)
		.clickUpdateButton();
	}
}
