package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import wrappers.OpentapsWrappers;

public class TC08_DeleteContact extends OpentapsWrappers{
	
	@BeforeClass
	public void startTestCase(){
		browserName 	= "firefox";
		dataSheetName 	= "TC08_DeleteContact";
		testCaseName 	= "TC08_DeleteContact (POM)";
		testDescription = "DeleteContact in Opentaps using POM framework";
	}
	
	
	@Test(dataProvider="fetchData")
	public void DeleteContact(String username,String password,String loginName,String firstName,String lastName, String companyName) {
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
		.ClickDeletebutton()
		.alertAcceptOk();

	}	
	}
