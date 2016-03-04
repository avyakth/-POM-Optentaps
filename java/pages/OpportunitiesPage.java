package pages;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class OpportunitiesPage extends OpentapsWrappers{
	public OpportunitiesPage() {

		if(!verifyTitle("Opportunities | opentaps CRM")){
			Reporter.reportStep("This is NOT Opportunities page", "FAIL");
		}

	}
	
	
	 
	public CreateOpportunityPage navigateToCreateOpportunityPage() {
		  clickByXpath(prop.getProperty("Opportunity.CreateOpportunity.Xpath"));
		  return new CreateOpportunityPage();
		  
	  }
	
	

}
