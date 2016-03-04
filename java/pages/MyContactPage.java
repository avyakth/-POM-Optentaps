package pages;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class MyContactPage extends OpentapsWrappers{
	public MyContactPage() {

		if(!verifyTitle("My Contacts | opentaps CRM")){
			Reporter.reportStep("This is NOT My Contacts page", "FAIL");
		}

	}
	
	
	 
	public FindContactPage navigateToFindContactPage() {
		  clickByLink(prop.getProperty("MyContacts.FindContacts.LinkText"));
		  return new FindContactPage();
		  
	  }
	
	public CreateContactPage clickCreateContact() {
		clickByXpath(prop.getProperty("MyContacts.CreateContacts.xpath"));
		return new CreateContactPage();
}
	
}
