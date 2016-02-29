package testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.LoginPage;
import wrappers.OpentapsWrappers;

public class TC03_EditLead extends OpentapsWrappers{
	
	@BeforeClass
	public void startTestCase(){
		browserName 	= "firefox";
		dataSheetName 	= "TC03_EditLead";
		testCaseName 	= "TC03 -EditLead (POM)";
		testDescription = "EditLeads in Opentaps using POM framework";
	}
	
	
	@Test(dataProvider="fetchData")
	public void editLead(String username,String password,String loginName,String companyName,String firstName,String lastName,String companyNameEdit, String firstNameEdit, String lastNameEdit) {
		new LoginPage()
		.enterUserName(username)
		.enterPassword(password)
		.clickLogin()
		.verifyLoggedinUserName(loginName)
		.clickCrmsfa()
		.navigateToLeadPage()
		.navigateToFindLeadsPage()
		.enterFirstName(firstName)
		.enterLastName(lastName)
		.enterCompanyName(companyName)
		.clickFindLeadsbutton()
		.clickSearchName()
		.clickEditButton()
		.editCompanyName(companyNameEdit)
		.editFirstName(firstNameEdit)
		.editLastName(lastNameEdit)
		.clickUpdate()
		.verifyFirstName(firstNameEdit);
	}
}
