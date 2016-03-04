package pages;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class CreateContactPage extends OpentapsWrappers {

	public CreateContactPage() {
		if (!verifyTitle("Create Contact | opentaps CRM")) {
			Reporter.reportStep("This is NOT Create Contact page", "FAIL");
		}

	}

	
	public CreateContactPage enterFirstName(String firstName) {
		enterById(prop.getProperty("CreateContact.FirstName.id"), firstName);
		return this;
	}

	public CreateContactPage enterLasttName(String lastName) {
		enterById(prop.getProperty("CreateContact.LastName.id"), lastName);
		return this;
	}
	
	
	public ViewContactPage clickCreateContact() {
		clickByName(prop.getProperty("CreateContact.SubmitButton.Name"));
		return new ViewContactPage();
	}

}
