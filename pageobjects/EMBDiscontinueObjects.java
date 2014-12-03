package pageobjects;

import static pageobjects.ObjectLocator.*;


/**
 * @author 324096
 *
 */
public enum EMBDiscontinueObjects 
{
	// EMB Discontinue Process Objects
	lnk_Discontinue("Discontinue",LINKTEXT,"Discontinue"),
	lnk_Discontinue2("discontinue FPL E-Mail Bill.",LINKTEXT,"Discontinue FPL EMail Bill Link"),
	txt_requestedByName("requestedByName",NAME,"Requested By"),
	btn_Submit("Submit",NAME,"Submit Button"),
	lbl_ThankYou("//*[@id='outerMostTable']/tbody/tr[2]/td/table/tbody/tr[2]/td[2]/table/tbody/tr/td[1]/table[1]/tbody/tr[1]/td[1]/h5",XPATH,"Thank You Table"),
	
	// EMB Discontinue Confirmation Email Objects
	// Header Links Objects
	lnk_Login("/html/body/div/div[1]/table/tbody/tr/td[2]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Login Link"),
	lnk_PayBill("/html/body/div/div[1]/table/tbody/tr/td[3]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Pay Bill Link"),
	lnk_ContactUs("/html/body/div/div[1]/table/tbody/tr/td[4]/div/span/table/tbody/tr/td/div/a/span",XPATH,"Contact Us Link"),
	
	lbl_CustomerName("/html/body/div/div[1]/table/tbody/tr/td[6]/div[1]/span",XPATH,"Customer Name"),
	lbl_AccountNumber("/html/body/div/div[1]/table/tbody/tr/td[6]/div[2]/span[2]",XPATH,"Account Number"),
	lbl_EmailAddress("/html/body/div/div[1]/table/tbody/tr/td[6]/div[4]/span/table/tbody/tr/td/div/a/span",XPATH,"Email Address"),
	lnk_UpdateEmailAddress("/html/body/div/div[1]/table/tbody/tr/td[6]/div[5]/a/span",XPATH,"Update Email Address"),
	
	// Email Content Objects
	lbl_CustomerName2("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr/td/div[1]",XPATH,"Customer Name"),
	lbl_DiscontinueMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr/td/div[2]/span[1]",XPATH,"Discontinue Message"),
	
	lbl_DiscontinueMessage1("/html/body/div[1]/div[5]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div[1]",XPATH,"Message Content1"),
	lbl_DiscontinueMessage11("/html/body/div/div[5]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div[1]/span[1]",XPATH,"Email Content 1.1"),
	lbl_DiscontinueMessage12("/html/body/div/div[5]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div[1]/span[2]",XPATH,"Account Number in Email"),
	lbl_DiscontinueMessage13("/html/body/div/div[5]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div[1]/span[4]",XPATH,"Email Content 1.3"),
	lbl_DiscontinueMessage2("/html/body/div[1]/div[5]/span/table/tbody/tr/td/div/span/table/tbody/tr[1]/td[1]/div/span/table/tbody/tr/td/div[2]",XPATH,"Message Content2"),
	
	//Security Message Objects
	lbl_SecurityMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[1]/span",XPATH,"Security Message"),
	lbl_SecurityLinkText("/html/body/div/div[4]/table/tbody/tr[2]/td[2]/div[1]/a/span",XPATH,"Security Message Link"),
	lnk_SecurityLink("Learn more",LINKTEXT,"Security Link: Learn More"),
	
	// ABP Message Objects
	lbl_ABPMessage("/html/body/div[1]/div[4]/table/tbody/tr[2]/td[2]/div[2]/span/table/tbody/tr/td/div/span[2]",XPATH,"ABP Message"),
	lbl_ABPMessageLinkText("/html/body/div[1]/div[4]/table/tbody/tr[2]/td[2]/div[2]/span/table/tbody/tr/td/div/a/span",XPATH,"ABP Message Link Text"),
	lnk_ABPMessageLink("Enroll today",LINKTEXT,"ABP Enrollment Link"),
	lbl_EMBMessage("/html/body/div/div[4]/table/tbody/tr[2]/td[1]/div/table/tbody/tr/td/div[2]/span[1]",XPATH,"EMB Message"),
	
	// Promtional Message Objects
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
	lbl_FooterMessage("/html/body/div[1]/div[10]/table/tbody/tr/td/div",XPATH,"Footer Message"),
	
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

	private EMBDiscontinueObjects(String strPropertyValue, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.locatorType = locatorType;
		this.strObjName = strObjName;
	}

}
