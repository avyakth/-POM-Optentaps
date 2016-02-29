package pages;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class HomePage extends OpentapsWrappers{

	public HomePage() {

		if(!verifyTitle("Opentaps Open Source ERP + CRM")){
			Reporter.reportStep("This is NOT Home page", "FAIL");
		}

	}
	// Verify the username
	public HomePage verifyLoggedinUserName(String userName){
		verifyTextContainsByXpath(prop.getProperty("Home.UserName.Xpath"), userName);
		return this;
	}
	
	public LoginPage clickLogout(){
		clickByClassName(prop.getProperty("Home.Logout.Class"));
		return new LoginPage();
	}
	
	public MenuPage clickCrmsfa()
	{
		clickByXpath(prop.getProperty("Home.CRMSFA.Xpath"));
		return new MenuPage();
	}

	
}
