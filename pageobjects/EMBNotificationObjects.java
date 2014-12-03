package pageobjects;

import static pageobjects.ObjectLocator.*;


/**
 * @author 324096
 *
 */
public enum EMBNotificationObjects 
{
	// EMB Norification Email Objects
	// Header Links Objects
	img_FPLLogo("/html/body/div/div[1]/table/tbody/tr/td[1]/div/span/a/img",XPATH,"FPL Logo"),
	lnk_Login("/html/body/div/div[1]/table/tbody/tr/td[2]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Login Link"),
	lnk_PayBill("/html/body/div/div[1]/table/tbody/tr/td[3]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Pay Bill Link"),
	lnk_ContactUs("/html/body/div/div[1]/table/tbody/tr/td[4]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Contact Us Link"),
	//Email content Objects
	lbl_CustomerName("/html/body/div/div[1]/table/tbody/tr/td[6]/div[1]/span",XPATH,"Customer Name"),
	lbl_AccountNumber("/html/body/div/div[1]/table/tbody/tr/td[6]/div[2]/span[3]",XPATH,"Account Number"),
	lbl_EmailAddress("/html/body/div/div[1]/table/tbody/tr/td[6]/div[4]/span/table/tbody/tr/td/div/span[1]",XPATH,"Email Address"),
	lnk_UpdateEmailAddress("/html/body/div/div[1]/table/tbody/tr/td[6]/div[5]/a[1]/span",XPATH,"Update Email Address"),
	
	lbl_ServiceDatesMessage("/html/body/div/div[3]/span/table/tbody/tr/td/div/span/table/tbody/tr/td[1]/div",XPATH,"service Dates Message"),
	
	lbl_Hello("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[1]/span[1]",XPATH,"Hello"),
	lbl_CustomerName2("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[1]/span[2]",XPATH,"Customer Name"),
	lbl_BillPayMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[1]/span[4]",XPATH,"Discontinue Message"),
	lbl_BalanceMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[2]",XPATH,"Current Balance Message"),
	
	lnk_PayBillButton("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[2]/td/div/span/table/tbody/tr/td[1]/div/span/table/tbody/tr/td/div/span/a/img",XPATH,"Pay Bill Link"),
	lnk_ViewBill("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[2]/td/div/span/table/tbody/tr/td[2]/div/span/table/tbody/tr/td/div/span/a/img",XPATH,"View Bill Link"),
	lnk_DownloadBill("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[2]/td/div/span/table/tbody/tr/td[3]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Download Bill Link"),
	
	// Security Message Objects
	lbl_SecurityMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[1]/span",XPATH,"Security Message"),
	lbl_SecurityLinkText("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[1]/a/span",XPATH,"Security Message Link"),
	lnk_SecurityLink("Learn more",LINKTEXT,"Security Link: Learn More"),
	
	lbl_BillViewMessage("/html/body/div/div[5]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div",XPATH,"EMB Message"),
	
	//Past Due Email Content
	lbl_PastDueMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[1]",XPATH,"Past Due Message"),
	
	
	//Ready to pay mail content
	lbl_ReadyToPayMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr[1]/td/div[1]/span[4]",XPATH,"Ready To Pay Message"),
	
	// ABP enrollment Message Objects
	lbl_ABPEnrollmentMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[2]/span/table/tbody/tr/td/div/span[2]",XPATH,"ABP Enrollment Message"),
	lbl_ABPEnrollmentLinkText("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[2]/span/table/tbody/tr/td/div/a/span",XPATH,"ABP Enrollment Link Text"),
	lnk_ABPEnrollmentLink("Enroll today",LINKTEXT,"ABP Enrollment Link"), 
	
	
	
	//Notification Objects
	lbl_EMBNotificationMessage("/html/body/div/div[5]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div",XPATH,"EMB Notification Message"),
	lbl_EMBNotificationMessage1("/html/body/div/div[7]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[2]/div/span/table/tbody/tr/td/div/span[1]",XPATH,"EMB Notification Message 1"),
	lbl_EMBNotificationMessage2("/html/body/div/div[7]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[2]/div/span/table/tbody/tr/td/div/span[2]",XPATH,"EMB Notification Message 2"),
	
	// Register Link Objects
	lbl_RegisterLinkText("/html/body/div/div[7]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[2]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Regiser Today link Text"),
	lnk_RegisterLink("Register today",LINKTEXT,"Register Link:Register Today"),
	
	
	// Promotional Messages Objects
	lbl_OHESPromoHeader("/html/body/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div/span",XPATH,"OHES Promo Header"),
	lbl_OHESMessage("/html/body/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[2]/td[2]/div/span/table/tbody/tr/td/div",XPATH,"OHES Message"),
	lnk_GetStarted("Calculate your savings",LINKTEXT,"Calculate your savings Link"),
	img_LightbulbImage("/html/body/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[2]/td[1]/div/span/img",XPATH,"LightBulb Image"),
	
	lbl_PowertrackerHeader("/html/body/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[2]/div/span/table/tbody/tr/td/div/span",XPATH,"Power Tracker Header"),
	lbl_PowertrackerMessage("/html/body/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[2]/td[4]/div/span/table/tbody/tr/td/div/span",XPATH,"Power Tracker Message"),
	lnk_Hereshow("See solar in your community",LINKTEXT,"See solar in your community Link"),
	img_CheckImage("/html/body/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[2]/td[3]/div/span/img",XPATH,"Check Image"),
	
	lbl_ResidentialHeader("/html/body/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[3]/div/span/table/tbody/tr/td/div/span",XPATH,"Residential Header"),
	lbl_ResidentialMessage("/html/body/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[2]/td[6]/div/span/table/tbody/tr/td/div/span",XPATH,"Residential Message"),
	lnk_Seehow("Read our blog post",LINKTEXT,"Read our blog post Link"),
	img_EnergyImage("/html/body/div/div[8]/span/table/tbody/tr/td/div/span/table/tbody/tr[2]/td[5]/div/span/img",XPATH,"Energy Image"),
	
	// Footer Links Objects
	lnk_EnergyNews("/html/body/div/div[9]/table/tbody/tr/td[1]/div/table/tbody/tr/td[1]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Energy News Link"),
	lnk_PrivacyPolicy("Privacy Policy",LINKTEXT,"Privacy Policy Link"),
	lnk_AboutUs("About Us",LINKTEXT,"About Us Link"),
	lbl_CopyRight("/html/body/div/div[9]/table/tbody/tr/td[2]/div",XPATH,"Copy Right"),
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

	private EMBNotificationObjects(String strPropertyValue, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.locatorType = locatorType;
		this.strObjName = strObjName;
	}

}
