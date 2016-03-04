package pages;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class ViewContactPage extends OpentapsWrappers {

	public ViewContactPage() {

		if (!verifyTitle("View Contact | opentaps CRM")) {
			Reporter.reportStep("This is NOT View Contact page", "FAIL");
		}

	}

	public ViewContactPage verifyFirstName(String firstName) {
		verifyTextById(prop.getProperty("ViewContact.FirstName.id"), firstName);
		return this;
	}
	
	public EditContactPage clickEditButton() {
		clickByLink(prop.getProperty("ViewContact.Edit.LinkText"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new EditContactPage();
	}
	
	public ViewContactPage ClickDeletebutton() {
		  clickByXpath(prop.getProperty("ViewContact.ContactButton.Xpath"));
		  return this;
	  }
	
	public ViewContactPage alertAcceptOk () {
		try {
			acceptAlert();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
		
	}

}

