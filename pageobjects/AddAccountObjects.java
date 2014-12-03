/**
 * 
 */
package pageobjects;

import static pageobjects.ObjectLocator.*;


/**
 * @author 324096
 *
 */
public enum AddAccountObjects 
{
	lnk_SelectAccount("Select/Add Account",LINKTEXT,"Select/Add Account"),
	lnk_AddAccount("Add Accounts",LINKTEXT,"Add Account"),
	txt_BillAccount("billAccount0",ID,"Account Number"),
	txt_Ssn("ssn0",ID,"SSN"),
	drp_EmailAddress("emailDropDown1",ID,"Email Address Dropdown"),
	txt_SearchFor("searchFor",NAME,"Search For"),
	btn_Continue("submit",ID,"Continue Button"),
	btn_ChldContinue("submitBtn",ID,"Continue Button"),
	chk_EMBEnrolled("flEnrolled1",ID,"EMB Enrollment CheckBox"),
	drp_searchType("searchType",NAME,"Search Type"),
	btn_GoButton("#searchInput > input[name=\"search\"]",CSS,"Go Button"),
	rdo_defaultAccountInfo("defaultAccountInfo",NAME,"Default Account Radio Button"),
	btn_Submit("submit",ID,"Submit"),
	
	lnk_interstitialClose("interstitialClose",ID,"Interstitial Close Link"),
	lnk_RemindMeLater("Remind Me Later",LINKTEXT,"Remind Me Later Link"),
	frm_interstitiallightboxiFrame("interstitiallightboxiFrame",ID,"Interstitial Lightbox iFrame"),
	
	
	;
	
	
	
	String strProperty = "";
	ObjectLocator locatorType = null;
	String strObjName = "";
	
	
	
	public String getProperty(){
		return strProperty;
	}

	public ObjectLocator getLocatorType(){
		return locatorType;
	}
	
	public String getObjectName(){
		return strObjName;
	}

	private AddAccountObjects(String strPropertyValue, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.locatorType = locatorType;
		this.strObjName = strObjName;
	}

}
