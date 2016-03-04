package pages;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class ViewOpportunityPage extends OpentapsWrappers {
	
	public ViewOpportunityPage() {

		if (!verifyTitle("View Opportunity | opentaps CRM")) {
			Reporter.reportStep("This is NOT View Opportunity page", "FAIL");
		}

	}

	public ViewOpportunityPage verifyOpportunityName(String OpportunityName) {
		verifyTextById(prop.getProperty("ViewOpportunity.OpportunityName.Id"), OpportunityName);
		return this;
	}
	
	}
