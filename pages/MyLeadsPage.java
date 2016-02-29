package pages;

import org.testng.annotations.Test;

import utils.Reporter;
import wrappers.OpentapsWrappers;

public class MyLeadsPage extends OpentapsWrappers {
	public MyLeadsPage() {

		if(!verifyTitle("My Leads | opentaps CRM")){
			Reporter.reportStep("This is NOT login page", "FAIL");
		}

	}
	
	 public CreateLeadPage navigateToCreateLeadsPage() {
		  clickByXpath(prop.getProperty("Lead.CreateLead.Xpath"));
		  return new CreateLeadPage();
		  
	  }

	 public FindLeadsPage navigateToFindLeadsPage() {
		 clickByXpath(prop.getProperty("Lead.FindLeadsLink.Xpath"));
		 return (new FindLeadsPage());
	  
  }
  
 
}
