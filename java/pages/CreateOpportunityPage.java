package pages;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class CreateOpportunityPage extends OpentapsWrappers{
	public CreateOpportunityPage() {

		if (!verifyTitle("Create Opportunity | opentaps CRM")) {
			Reporter.reportStep("This is NOT Create Opportunity page", "FAIL");
		}

	}

	public CreateOpportunityPage enterOpprtunityName(String OppoName) {
		enterById(prop.getProperty("CreateOpportunity.OpportunityName.Id"), OppoName);
		return this;
	}

	public CreateOpportunityPage enterIntialAccount(String Account) {
		enterById(prop.getProperty("CreateOpportunity.IntialAccount.Id"), Account);
		return this;
	}

	public CreateOpportunityPage enterClosingaDate(String Date) {
		enterById(prop.getProperty("CreateOpportunity.EstimatedCloseDate.Id"), Date);
		return this;
	}
	
	public ViewOpportunityPage clickCreateOpportunity() {
		clickByXpath(prop.getProperty("CreateOpportunity.OpportunityName.Xpath"));
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ViewOpportunityPage();
	}


}
