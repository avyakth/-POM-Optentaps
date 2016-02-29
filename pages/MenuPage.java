package pages;

import org.testng.annotations.Test;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class MenuPage extends OpentapsWrappers {
	public MenuPage() {

		if(!verifyTitle("My Home | opentaps CRM")){
			Reporter.reportStep("This is NOT login page", "FAIL");
		}

	}

  public MyLeadsPage navigateToLeadPage() {
	  clickByLink(prop.getProperty("MenuPage.Leads.LinkText"));
	  return new MyLeadsPage();
	  
  }
  
  public MyContactPage navigateToContactPage(){
	  clickByLink(prop.getProperty("MenuPage.Contacts.LinkText"));
	  return new MyContactPage();
  }
	  
//Verify the username
/*	public CreateContactPage clickCreateContact() {
		clickByLink(prop.getProperty("MenuPage.CreateContact.LinkText"));
		return new CreateContactPage();
	}*/
  }

