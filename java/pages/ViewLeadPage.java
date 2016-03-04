package pages;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class ViewLeadPage extends OpentapsWrappers {

	public ViewLeadPage() {

		if (!verifyTitle("View Lead | opentaps CRM")) {
			Reporter.reportStep("This is NOT View Lead page", "FAIL");
		}

	}

	public ViewLeadPage verifyFirstName(String firstName) {
		verifyTextContainsByXpath(prop.getProperty("ViewLead.FirstName.XPath"), firstName);
		return this;
	}
	
	public EditLeadsPage clickEditButton() {
		clickByXpath(prop.getProperty("ViewLeadsPage.ID.Xpath"));
		
		return new EditLeadsPage();
	}

	public ViewLeadPage ClickDeletebutton() {
		  clickByXpath(prop.getProperty("ViewLead.DeleteButton.Xpath"));
		  return this;
	  }

}
