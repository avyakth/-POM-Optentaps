package pages;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class EditContactPage extends OpentapsWrappers{
	public EditContactPage() {

		if(!verifyTitle("opentaps CRM")){
			Reporter.reportStep("This is NOT edit Contacts page", "FAIL");
		}

	}
	
	 public EditContactPage enterFirstName(String EditfirstName) {
		  
		  enterById(prop.getProperty("EditContact.FirstName.Id"), EditfirstName);
		  return this;
	  }

	 public EditContactPage enterlastName(String EditlastName) {
		  
		  enterById(prop.getProperty("EditContact.LastName.Id"), EditlastName);
		  return this;
	  }
	 
	 public EditContactPage clickUpdateButton() {
		  
		  clickByName(prop.getProperty("EditContact.updatebutton.Name"));
		  return this;
	  }
}
