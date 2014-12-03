package businesscomponents;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageobjects.EmailContentPageObjects;
import pageobjects.PayByPhoneObjects;
import com.cognizant.framework.Status;
import componentgroups.CommonFunctions;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;


/**
 * Business Component Library template
 * @author RaviJadhav
 */
public class PayByPhone extends ReusableLibrary
{
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	public static String EmailUrl=null;
	
	public PayByPhone(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	/*******************************************************
	 * Function to return WebElement based on Input Objects
	 * @param loginPageEnum
	 * @return
	 */
	private  WebElement getPageElement(PayByPhoneObjects loginPageEnum)
	{
	    try{
	    	
	        return commonFunction.getElementByProperty(loginPageEnum.getProperty(), loginPageEnum
	        .getLocatorType().toString());
	    } catch(Exception e){
	        report.updateTestLog("Login Page - get page element", loginPageEnum.toString()
	        + " object is not defined or found.", Status.FAIL);
	        return null;
	    }
	}

	/*******************************************************
	 * 
	 * Function to find particular EMB Notification Email
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void findPayByPhoneEmail() throws InterruptedException
	{
		 String winHandleBefore = driver.getWindowHandle();
 		 for(String winHandle : driver.getWindowHandles())
 		 {
 			 //Switch to child window
 			 driver.switchTo().window(winHandle);
 			 driver.manage().window().maximize();
 			 Thread.sleep(3000);
 		 
 	     }
 		 
	 	 if(commonFunction.isElementPresent(By.id(EmailContentPageObjects.lnk_ClickHere.getProperty())))
	     {
	      	  driver.findElement(By.id(EmailContentPageObjects.lnk_ClickHere.getProperty())).click();
	      	  Thread.sleep(3000);
	     }
 	 	
	 	 EmailUrl = driver.getCurrentUrl();
 		
 		 report.updateTestLog("Email", "Email is opened", Status.PASS); 
 		 driver.close();
 		 driver.switchTo().window(winHandleBefore);
		
	}
	
	/**
	 *****************************************************
	 * Function to validate the Pay By Phone Email Header Links
	 * @throws IOException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	
	public void validatePPHHeaderLink() throws InterruptedException, IOException
	{
		navigateToFrame();
		// To verify that Login Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_Login), PayByPhoneObjects.lnk_Login.getObjectName(),dataTable.getData("General_Data", "Test_Login_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_Login), PayByPhoneObjects.lnk_Login.getObjectName(),dataTable.getData("General_Data", "QA_Login_Link"));
		
		navigateToFrame();
		// To verify that Pay Bill Link is displayed correctly or not in Email
		commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_PayBill), PayByPhoneObjects.lnk_PayBill.getObjectName(),dataTable.getData("Accounts", "Test_PayBill_Link"));
		
		navigateToFrame();
		// To verify that Contact Us Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_ContactUs), PayByPhoneObjects.lnk_ContactUs.getObjectName(),dataTable.getData("General_Data", "Test_Contact_Us_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_ContactUs), PayByPhoneObjects.lnk_ContactUs.getObjectName(),dataTable.getData("General_Data", "QA_Contact_Us_Link"));
	
	}
	
	/*******************************************************
	 * 
	 * Function to validate the Pay By Phone Email Content
	 * @throws IOException 
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void validatePayByphoneMail() throws InterruptedException, IOException
	{
		navigateToFrame();
		
		// To verify that Customer Name is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_CustomerName), PayByPhoneObjects.lbl_CustomerName.getObjectName(),dataTable.getData("Accounts", "CustomerName"));
		
		// To verify that Account Number is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(PayByPhoneObjects.lbl_AccountNumber), PayByPhoneObjects.lbl_AccountNumber.getObjectName(),commonFunction.insertCharAt(dataTable.getData("Accounts", "Account_Number"),'-',5));
		
		// To verify that Email Address is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_EmailAddress), PayByPhoneObjects.lbl_EmailAddress.getObjectName(),dataTable.getData("General_Data", "Username"));
		
		// To verify that Update Email Address Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_UpdateEmailAddress), PayByPhoneObjects.lnk_UpdateEmailAddress.getObjectName(),dataTable.getData("General_Data", "Test_Email_Update_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_UpdateEmailAddress), PayByPhoneObjects.lnk_UpdateEmailAddress.getObjectName(),dataTable.getData("General_Data", "QA_Email_Update_Link"));
		
	}
	
	/*******************************************************
	 * 
	 * Function to validate Pay By Phone Email Content
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void validatePayByPhoneMailContent() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that Customer Name is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(PayByPhoneObjects.lbl_CustomerName2), PayByPhoneObjects.lbl_CustomerName2.getObjectName(),dataTable.getData("Accounts", "CustomerName"));
		
		// To verify Security Message is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_SecurityMessage), PayByPhoneObjects.lbl_SecurityMessage.getObjectName(),dataTable.getData("General_Data", "SecurityMessage"));
		
		// To verify Security Message Link Text is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_SecurityLinkText), PayByPhoneObjects.lbl_SecurityLinkText.getObjectName(),dataTable.getData("General_Data", "SecurityLinkText"));
		
		// To verify that Security Link is displayed correctly or not in Email
		commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_SecurityLink), PayByPhoneObjects.lnk_SecurityLink.getObjectName(),dataTable.getData("General_Data", "Test_Security_Link"));
		
		// To verify pay By Phone Message is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_PayByPhoneMessage), PayByPhoneObjects.lbl_PayByPhoneMessage.getObjectName(),dataTable.getData("General_Data", "PayByPhoneMessage").trim());	
	}

	/*******************************************************
	 * 
	 * Function to validate the OHES Message in Pay By Phone Email 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_PPH_OHES_Message() throws IOException, InterruptedException
	{
		navigateToFrame();
				
		// To verify that OHES Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_OHESPromoHeader), PayByPhoneObjects.lbl_OHESPromoHeader.getObjectName(),dataTable.getData("General_Data", "Header1"));
		
		// To verify that OHES Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_OHESMessage), PayByPhoneObjects.lbl_OHESMessage.getObjectName(),dataTable.getData("General_Data", "Message"));
		
		// To verify that Get Started Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_GetStarted), PayByPhoneObjects.lnk_GetStarted.getObjectName(),dataTable.getData("General_Data", "TestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_GetStarted), PayByPhoneObjects.lnk_GetStarted.getObjectName(),dataTable.getData("General_Data", "QAUrl"));
		
	}
		
	/*******************************************************
	 * 
	 * Function to validate the Power Tracker Message in Pay By Phone Email 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_PPH_PowerTrackMessage() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that Power Tracker Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_PowertrackerHeader), PayByPhoneObjects.lbl_PowertrackerHeader.getObjectName(),dataTable.getData("General_Data", "PTHeader"));
		
		// To verify that Power Tracker Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_PowertrackerMessage), PayByPhoneObjects.lbl_PowertrackerMessage.getObjectName(),dataTable.getData("General_Data", "PTMessage"));
		
		// To verify that Hers's How Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_Hereshow), PayByPhoneObjects.lnk_Hereshow.getObjectName(),dataTable.getData("General_Data", "PTTestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_Hereshow), PayByPhoneObjects.lnk_Hereshow.getObjectName(),dataTable.getData("General_Data", "PTTestUrl"));
		
	}
	
	/*******************************************************
	 * 
	 * Function to validate Residential Message in Pay By Phone Email 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_PPH_ResidentialMessage() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that Residential Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_ResidentialHeader), PayByPhoneObjects.lbl_ResidentialHeader.getObjectName(),dataTable.getData("General_Data", "ResHeader"));
		
		// To verify that Residential Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_ResidentialMessage), PayByPhoneObjects.lbl_ResidentialMessage.getObjectName(),dataTable.getData("General_Data", "ResMessage"));
		
		// To verify that See how Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_Seehow), PayByPhoneObjects.lnk_Seehow.getObjectName(),dataTable.getData("General_Data", "ResTestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_Seehow), PayByPhoneObjects.lnk_Seehow.getObjectName(),dataTable.getData("General_Data", "ResQAUrl"));
		
	}
	
	/*******************************************************
	 * Function to validate the Pay By Phone Email Footer Links
	 * @throws IOException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBNFooterLink() throws InterruptedException, IOException
	{
		navigateToFrame();
		
		// To verify that Energy News Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_EnergyNews), PayByPhoneObjects.lnk_EnergyNews.getObjectName(),dataTable.getData("General_Data", "Test_Energy_Usage_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_EnergyNews), PayByPhoneObjects.lnk_EnergyNews.getObjectName(),dataTable.getData("General_Data", "QA_Energy_Usage_Link"));
		
		navigateToFrame();
		// To verify that Privacy Policy Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_PrivacyPolicy), PayByPhoneObjects.lnk_PrivacyPolicy.getObjectName(),dataTable.getData("General_Data", "Test_Privacy_Policy_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_PrivacyPolicy), PayByPhoneObjects.lnk_PrivacyPolicy.getObjectName(),dataTable.getData("General_Data", "QA_Privacy_Policy_Link"));
		
		navigateToFrame();
		// To verify that About Us Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_AboutUs), PayByPhoneObjects.lnk_AboutUs.getObjectName(),dataTable.getData("General_Data", "Test_About_Us_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(PayByPhoneObjects.lnk_AboutUs), PayByPhoneObjects.lnk_AboutUs.getObjectName(),dataTable.getData("General_Data", "QA_About_Us_Link"));
		
		navigateToFrame();
		// To verify that Copyright Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(PayByPhoneObjects.lbl_CopyRight), PayByPhoneObjects.lbl_CopyRight.getObjectName(),dataTable.getData("General_Data", "CopyrightMessage1"));
	}
	
	/*******************************************************
	 * Function to navigate to particular Frame on Web Page
	 * @throws InterruptedException 
	 *  
	 * @Modified by 324096 on 18 Oct
	 *	
	 *****************************************************
	*/
	private void navigateToFrame() throws InterruptedException
	{
		driver.get(EmailUrl);
		Thread.sleep(3000);
		if(commonFunction.isFramePresent(EmailContentPageObjects.frm_EMailFrame.getProperty()))
		{
			driver.switchTo().frame(EmailContentPageObjects.frm_EMailFrame.getProperty());
		}
	}			
}