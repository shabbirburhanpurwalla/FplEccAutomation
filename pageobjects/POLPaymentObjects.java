package pageobjects;

import static pageobjects.ObjectLocator.*;


/** Contains Objects for POL Payment Page
 * @author 324096
 *
 */
public enum POLPaymentObjects 
{
	// POL Payment Process Objects
	lnk_PayBill("Pay Bill",LINKTEXT,"Pay Bill"),
	rdo_otherDueAmount("otherDueAmount",ID,"Other Due Amount RadioButton"),
	txt_dollars("dollars",ID,"Dollar Textbox"),
	txt_requestedByName("requested_by",NAME,"Requested By"),
	btn_Pay("div.button_B > a",CSS,"Pay Button"),
	lbl_ThankYou("//*[@id='outerMostTable']/tbody/tr[2]/td/table/tbody/tr[2]/td[2]/table/tbody/tr/td[1]/table[1]/tbody/tr[3]/td[1]/h5",XPATH,"Thank You Message"),
	
	// POL Payment Confirmation Email Objects
	// Header Links Objects
	lnk_Login("/html/body/div/div[1]/table/tbody/tr/td[2]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Login Link"),
	lnk_PayBill2("/html/body/div/div[1]/table/tbody/tr/td[3]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Pay Bill Link"),
	lnk_ContactUs("/html/body/div/div[1]/table/tbody/tr/td[4]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Contact Us Link"),
	
	// Email Content Objects
	lbl_CustomerName("/html/body/div/div[1]/table/tbody/tr/td[6]/div[1]/span",XPATH,"Customer Name"),
	lbl_AccountNumber("/html/body/div/div[1]/table/tbody/tr/td[6]/div[2]/span[2]",XPATH,"Account Number"),
	lbl_EmailAddress("/html/body/div/div[1]/table/tbody/tr/td[6]/div[4]/span/table/tbody/tr/td/div/a/span",XPATH,"Email Address"),
	lnk_UpdateEmailAddress("/html/body/div/div[1]/table/tbody/tr/td[6]/div[5]/a/span",XPATH,"Update Email Address"),
	
	lbl_CustomerName2("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr/td/div[1]/span[2]",XPATH,"Customer Name"),
	lbl_POLMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr/td/div[2]/span",XPATH,"Pay Online Message"),
	
	// Security Message Objects
	lbl_SecurityMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[1]/span",XPATH,"Security Message"),
	lbl_SecurityLinkText("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[1]/a/span",XPATH,"Security Message Link"),
	
	// EMB Enrollment Message Objects
	lbl_EmailEnrollMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[3]/table/tbody/tr[2]/td[2]/table/tbody/tr/td/p[1]/span",XPATH,"Email Bill Enroll Message"),
	lbl_EmailEnrollLinkText("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[3]/table/tbody/tr[2]/td[2]/table/tbody/tr/td/p[2]/a/span",XPATH,"Email bill Enroll Message Link"),
	
	lbl_FPLEmailMessage("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[4]/table/tbody/tr/td/table/tbody/tr[1]/td[1]/table/tbody/tr/td",XPATH,"FPL Email Message Text"),
	lbl_FPLEmailMessage1("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[5]/table/tbody/tr/td/table/tbody/tr[1]/td[1]/table/tbody/tr/td/p/span[1]",XPATH,"FPL Email Message 1"),
	
	lbl_PayOnlineLink("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[5]/table/tbody/tr/td/table/tbody/tr[1]/td[1]/table/tbody/tr/td/p/a/span",XPATH,"Pay Online Webpage Link"),
	lbl_FPLEmailMessage2("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[5]/table/tbody/tr/td/table/tbody/tr[1]/td[1]/table/tbody/tr/td/p/span[2]",XPATH,"FPL Email Message 1"),
	
	// Promotional Messages Objects
	lbl_OHESPromoHeader("/html/body/div/div[8]/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div/span",XPATH,"OHES Promo Header"),
	lbl_OHESMessage("/html/body/div/div[8]/table/tbody/tr[2]/td[2]/div/span/table/tbody/tr/td/div/span",XPATH,"OHES Message"),
	lnk_GetStarted("Get started",LINKTEXT,"Get Started Link"),
	lbl_PowertrackerHeader("/html/body/div/div[8]/table/tbody/tr[1]/td[2]/div/span/table/tbody/tr/td/div/span",XPATH,"Power Tracker Header"),
	lbl_PowertrackerMessage("/html/body/div/div[8]/table/tbody/tr[2]/td[4]/div/span/table/tbody/tr/td/div/span",XPATH,"Power Tracker Message"),
	lnk_Hereshow("Here's how",LINKTEXT,"Here's how Link"),
	lbl_ResidentialHeader("/html/body/div/div[8]/table/tbody/tr[1]/td[3]/div/span/table/tbody/tr/td/div/span",XPATH,"Residential Header"),
	lbl_ResidentialMessage("/html/body/div/div[8]/table/tbody/tr[2]/td[6]/div/span/table/tbody/tr/td/div/span",XPATH,"Residential Message"),
	lnk_Seehow("See how",LINKTEXT,"See how Link"),
	
	// Footer Links Objects
	lnk_EnergyNews("Energy News",LINKTEXT,"Energy News Link"),
	lnk_PrivacyPolicy("Privacy Policy",LINKTEXT,"Privacy Policy Link"),
	lnk_AboutUs("About Us",LINKTEXT,"About Us Link"),
	lbl_CopyRight("/html/body/div/div[9]/table/tbody/tr/td[4]/div",XPATH,"Copy Right"),
	
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

	private POLPaymentObjects(String strPropertyValue, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.locatorType = locatorType;
		this.strObjName = strObjName;
	}

}
