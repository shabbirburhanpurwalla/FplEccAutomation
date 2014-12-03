package pageobjects;

import static pageobjects.ObjectLocator.*;



/**
 * @author 324096
 *
 */
public enum EMBEnrollmentObjects 
{
	// EMB Enrollment Process Objects
	lnk_EnrollNow("Enroll Now",LINKTEXT,"Enroll Now"),
	txt_requestedByName("requestedByName",NAME,"Requested By"),
	btn_Submit("Submit",NAME,"Submit Button"),
	lnk_Close("Close",LINKTEXT,"Close Link"),
	lbl_Thankyou("//*[@id='outerMostTable']/tbody/tr[2]/td/table/tbody/tr[2]/td[2]/table/tbody/tr/td/table[1]/tbody/tr[1]/td[1]/h5",XPATH,"Thankyou Page"),
	
	// EMB Enrollment Confirmation Email Objects
	// Header Links Objects
	lnk_Login("/html/body/div/div[1]/table/tbody/tr/td[2]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Login Link"),
	lnk_PayBill("/html/body/div/div[1]/table/tbody/tr/td[3]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Pay Bill Link"),
	lnk_ContactUs("/html/body/div/div[1]/table/tbody/tr/td[4]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Contact Us Link"),
	lbl_CustomerName("/html/body/div/div[1]/table/tbody/tr/td[6]/div[1]/span",XPATH,"Customer Name"),
	lbl_AccountNumber("/html/body/div/div[1]/table/tbody/tr/td[6]/div[2]/span[2]",XPATH,"Account Number"),
	lbl_EmailAddress("/html/body/div/div[1]/table/tbody/tr/td[6]/div[4]/span/table/tbody/tr/td/div/a/span",XPATH,"Email Address"),
	lnk_UpdateEmailAddress("/html/body/div/div[1]/table/tbody/tr/td[6]/div[5]/a/span",XPATH,"Update Email Address"),
	
	// Email Contents Objects
	lbl_CustomerName2("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr/td/div[1]",XPATH,"Customer Name"),
	lbl_EnrollmentMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr/td/div[2]/span",XPATH,"Enrollment Message"),
	// Security Message Objects
	lbl_SecurityMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[1]/span",XPATH,"Security Message"),
	lbl_SecurityLinkText("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[1]/a/span",XPATH,"Security Message Link"),
	lnk_SecurityLink("Learn more",LINKTEXT,"Security Link: Learn More"),
	// ABP enrollment Message Objects
	lbl_ABPEnrollmentMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[2]/span/table/tbody/tr/td/div/span[2]",XPATH,"ABP Enrollment Message"),
	lbl_ABPEnrollmentLinkText("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[2]/span/table/tbody/tr/td/div/a/span",XPATH,"ABP Enrollment Link Text"),
	lnk_ABPEnrollmentLink("Enroll today",LINKTEXT,"ABP Enrollment Link"),
	
	lbl_EMBMessage("/html/body/div/div[5]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div/span",XPATH,"Message in Paragraph"),
	lbl_EmailContentHeader("/html/body/div/div[6]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div[1]/span",XPATH,"Content Header"),
	lbl_EmailContent1("/html/body/div[1]/div[6]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div[2]/span[2]",XPATH,"Email Content 1"),
	lbl_EmailAccountNumber("/html/body/div[1]/div[6]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div[2]/span[4]",XPATH,"Account Number in Email"),
	lbl_EmailEmailAddress("/html/body/div[1]/div[6]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div[2]/span[7]",XPATH,"EmailAddress in Email"),
	lbl_EmailContent2("/html/body/div[1]/div[6]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div[3]/span[3]",XPATH,"Email Content 2"),
	lbl_EmailContent3("/html/body/div[1]/div[6]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div[4]/span[3]",XPATH,"Email Content 3"),
	
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
	lnk_EnergyNews("/html/body/div/div[9]/table/tbody/tr/td[1]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Energy News Link"),
	lnk_PrivacyPolicy("/html/body/div/div[9]/table/tbody/tr/td[2]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Privacy Policy Link"),
	lnk_AboutUs("/html/body/div/div[9]/table/tbody/tr/td[3]/div/span/table/tbody/tr/td/div/a/span",XPATH,"About Us Link"),
	lbl_CopyRight("/html/body/div/div[9]/table/tbody/tr/td[4]/div",XPATH,"Copy Right"),
	lbl_FooterMessage("/html/body/div/div[10]/table/tbody/tr/td/div",XPATH,"Footer Message"),
	
	
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

	private EMBEnrollmentObjects(String strPropertyValue, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.locatorType = locatorType;
		this.strObjName = strObjName;
	}

}
