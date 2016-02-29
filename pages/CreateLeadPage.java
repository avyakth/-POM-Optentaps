package pages;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class CreateLeadPage extends OpentapsWrappers {

	public CreateLeadPage() {

		if (!verifyTitle("Create Lead | opentaps CRM")) {
			Reporter.reportStep("This is NOT Create Lead page", "FAIL");
		}

	}

	public CreateLeadPage enterCompanyName(String companyName) {
		enterByXpath(prop.getProperty("CreateLead.CompanyName.Xpath"), companyName);
		return this;
	}

	public CreateLeadPage enterFirstName(String firstName) {
		enterByXpath(prop.getProperty("CreateLead.FirstName.Xpath"), firstName);
		return this;
	}

	public CreateLeadPage enterLastName(String lastName) {
		enterByXpath(prop.getProperty("CreateLead.LastName.Xpath"), lastName);
		return this;
	}
	
	public ViewLeadPage clickCreateLead() {
		clickByClassName(prop.getProperty("CreateLead.SubmitButton.Class"));
		return new ViewLeadPage();
	}

}
