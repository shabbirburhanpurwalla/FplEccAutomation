package businesscomponents;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageobjects.EMBEnrollmentObjects;
import pageobjects.EmailContentPageObjects;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import componentgroups.CommonFunctions;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

/**
 * Business Component Library template
 * @author RaviJadhav
 */
public class EMBEnrollment extends ReusableLibrary
{
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	public static String EmailUrl=null;
	
	public EMBEnrollment(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	/*******************************************************
	 * Function to return WebElement based on Input Objects
	 * @param loginPageEnum
	 * @return
	 * ******************************************************
	 */
	private  WebElement getPageElement(EMBEnrollmentObjects loginPageEnum)
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
	 * Function to perform EMB Enrollment
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void embEnrollment()
	{
		//Enrollment Process 
		
		//Click Enroll Now link on Account Summary page to Enroll in EMB
		commonFunction.clickIfElementPresent(getPageElement(EMBEnrollmentObjects.lnk_EnrollNow), EMBEnrollmentObjects.lnk_EnrollNow.getObjectName());
		
		String RequestedBy = commonFunction.validateData("Accounts", "RequestedBy", EMBEnrollmentObjects.txt_requestedByName.getObjectName());
		//Enter Requested By Name in present textbox
		commonFunction.clearAndEnterText(getPageElement(EMBEnrollmentObjects.txt_requestedByName), RequestedBy,EMBEnrollmentObjects.txt_requestedByName.getObjectName());
		
		//Click Submit Button to submit the request
		commonFunction.clickIfElementPresent(getPageElement(EMBEnrollmentObjects.btn_Submit), EMBEnrollmentObjects.btn_Submit.getObjectName());
		
		//Check whether Thank You page is displayed or not
		if(getPageElement(EMBEnrollmentObjects.lbl_Thankyou).isDisplayed())
		{
			report.updateTestLog("Thank You Page", "Thank you page is displayed, EMB Enrollment successful", Status.PASS);
		}
		else
		{
			report.updateTestLog("Thank You Page", "Thank you page is not displayed, EMB Enrollment not done", Status.FAIL);
		}
		
	}
	
	/*******************************************************
	 * 
	 * Function to find particular EMB Enrollment Email
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void findEMBEnrollmentEmail() throws InterruptedException
	{
		
		 String winHandleBefore = driver.getWindowHandle();
 		 for(String winHandle : driver.getWindowHandles())
 		 {
 			 //Switch to child window
 			 driver.switchTo().window(winHandle);
 			 driver.manage().window().maximize();
 			 Thread.sleep(3000);
 		 }
 		 
 		 try
 		 {
		 	 if(commonFunction.isElementPresent(By.id(EmailContentPageObjects.lnk_ClickHere.getProperty())))
		     {
		      	  driver.findElement(By.id(EmailContentPageObjects.lnk_ClickHere.getProperty())).click();
		      	  Thread.sleep(3000);
		     }
 		 }
 		 catch(Exception ex)
 		 {
 			 
 		 }
	 	 EmailUrl = driver.getCurrentUrl();
 		 
	 	 System.out.println(driver.getTitle());
	 	 
	 	 String title = driver.getTitle();
	 	 if(title.contains(dataTable.getData("Accounts", "EmailSubject")))
		 {
			 report.updateTestLog("Email", "Email is opened", Status.PASS); 
	 		 driver.close();
	 		 driver.switchTo().window(winHandleBefore);
		 }
		 else
		 {
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Find Email", "Incorrect Email is Opened");
		 }
	}
	
	public void getEMBEnrollmentEmail() throws InterruptedException
	{

	    System.out.println("Waiting for Email");
	    boolean testEmail = true;
	    boolean EmailFound=false;
	    int count=0;
		
		try
        {
			do
			{
				  	 System.out.println("Will be waiting for Next: "+ (7-count)+" minutes");
					 if(count!=0)
					 {
						  Thread.sleep(60000);
					 }	  
				  
				     String AccountNumber = commonFunction.insertCharAt(dataTable.getData("Accounts", "AccountNumber"),'-',5);
					 
				     driver.findElement(By.id(EmailContentPageObjects.txt_AccountNumber.getProperty())).clear();
					 driver.findElement(By.id(EmailContentPageObjects.txt_AccountNumber.getProperty())).sendKeys(AccountNumber);
					 driver.findElement(By.xpath(EmailContentPageObjects.btn_SearchAccount.getProperty())).click();
					 
					 Thread.sleep(15000);
					 
					 try
					 {
						 Actions action = new Actions(driver);
				         WebElement element=driver.findElement(By.xpath(EmailContentPageObjects.lnk_EmailLink.getProperty()));
				
				         //Double click
				         action.doubleClick(element).perform();
				         Thread.sleep(5000);
						 
				         System.out.println("Mail found for this Account Number");
				         String winHandleBefore = driver.getWindowHandle();
				 		 for(String winHandle : driver.getWindowHandles())
				 		 {
				 			 //Switch to child window
				 			 driver.switchTo().window(winHandle);
				 			 driver.manage().window().maximize();
				 			 Thread.sleep(3000);
				 		 }
				 		 
				 		 try
				 		 {
						 	 if(commonFunction.isElementPresent(By.id(EmailContentPageObjects.lnk_ClickHere.getProperty())))
						     {
						      	  driver.findElement(By.id(EmailContentPageObjects.lnk_ClickHere.getProperty())).click();
						      	  Thread.sleep(3000);
						     }
				 		 }
				 		 catch(Exception ex)
				 		 {
				 			 
				 		 }
				 		 EmailUrl = driver.getCurrentUrl();
					 	 System.out.println(driver.getTitle());
					 	 String title = driver.getTitle();
					 	 if(title.contains(dataTable.getData("Accounts", "EmailSubject")))
						 {
					 		 System.out.println("Email found for EMB Enrollment");
							 navigateToFrame();
							 String AccountNumberToCheck = getPageElement(EMBEnrollmentObjects.lbl_AccountNumber).getText();
							 System.out.println(AccountNumberToCheck);
							 if(AccountNumberToCheck.contains(AccountNumber))
							 {
								 EmailFound = true;
								 System.out.println("Correct Email Found");
								 driver.close();
						 		 driver.switchTo().window(winHandleBefore);
								 break;
							 }
							 else
							 {
								 System.out.println("Incorrect Email Found");
							 }
						 }
					 	 driver.close();
				 		 driver.switchTo().window(winHandleBefore);
					 }
					 catch(Exception ex)
					 {
						 System.out.println("Mail not Found, going for next iteration");
					 }
				     count++;
			         if(count<7)
					 {
					    if(EmailFound)
					    {
					    		testEmail=false;
					    }
					    else
					    {
					    		testEmail=true;
					    }
					 }else
					 {
					    	testEmail=false;
					 }
         }while(testEmail);
			
		
        }
		catch(Exception ex)
		{
			System.out.println(ex.getMessage());
		}
			
		if(EmailFound) 
		{		 
			    report.updateTestLog("Search EMBE Email", "EMB Enrollment Mail is found", Status.PASS);
    	}
		else
		{
			    frameworkParameters.setStopExecution(true);
    			throw new FrameworkException("Search EMBE Email", "EMB Enrollment Mail is not found");
    	}
	}
	
	/*******************************************************
	 * 
	 * Function to validate the EMB Enrollment Email Content
	 * @throws IOException 
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBEnrollmentMail() throws InterruptedException, IOException
	{
		navigateToFrame();
		
		// To verify that Customer Name is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_CustomerName), EMBEnrollmentObjects.lbl_CustomerName.getObjectName(),dataTable.getData("Accounts", "CustomerName"));
		
		// To verify that Account Number is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBEnrollmentObjects.lbl_AccountNumber), EMBEnrollmentObjects.lbl_AccountNumber.getObjectName(),commonFunction.insertCharAt(dataTable.getData("Accounts", "AccountNumber"),'-',5));
		
		// To verify that Email Address is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_EmailAddress), EMBEnrollmentObjects.lbl_EmailAddress.getObjectName(),dataTable.getData("General_Data", "Username"));
		
		// To verify that Update Email Address Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_UpdateEmailAddress), EMBEnrollmentObjects.lnk_UpdateEmailAddress.getObjectName(),dataTable.getData("General_Data", "Test_Email_Update_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_UpdateEmailAddress), EMBEnrollmentObjects.lnk_UpdateEmailAddress.getObjectName(),dataTable.getData("General_Data", "QA_Email_Update_Link"));
		
	}
	
	/*******************************************************
	 * 
	 * Function to validate the EMB Enrollment Email Content
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBEnrollmentEmailContent() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that Customer Name is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBEnrollmentObjects.lbl_CustomerName2), EMBEnrollmentObjects.lbl_CustomerName2.getObjectName(),dataTable.getData("Accounts", "CustomerName"));
				
		// To verify Enrollment Message is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_EnrollmentMessage), EMBEnrollmentObjects.lbl_EnrollmentMessage.getObjectName(),dataTable.getData("General_Data", "EMBEnrollmentMessage"));
		
		try
		{
				// To verify Security Message is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_SecurityMessage), EMBEnrollmentObjects.lbl_SecurityMessage.getObjectName(),dataTable.getData("General_Data", "SecurityMessage"));
				
				// To verify Security Message Link Text is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_SecurityLinkText), EMBEnrollmentObjects.lbl_SecurityLinkText.getObjectName(),dataTable.getData("General_Data", "SecurityLinkText"));
				
				// To verify that Security Link is displayed correctly or not in Email
				if(properties.getProperty("Environment").compareTo("Test")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_SecurityLink), EMBEnrollmentObjects.lnk_SecurityLink.getObjectName(),dataTable.getData("General_Data", "Test_Security_Link"));
				else if(properties.getProperty("Environment").compareTo("QA")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_SecurityLink), EMBEnrollmentObjects.lnk_SecurityLink.getObjectName(),dataTable.getData("General_Data", "QA_Security_Link"));
				
		}
		catch(Exception ex)
		{
			report.updateTestLog("Security Message", "Security Message is not present in Email", Status.FAIL);
		}
		navigateToFrame();
		
		try
		{
				// To verify ABP Enrollment Message is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_ABPEnrollmentMessage), EMBEnrollmentObjects.lbl_ABPEnrollmentMessage.getObjectName(),dataTable.getData("General_Data", "ABPEnrollmentMessage"));
				
				// To verify ABP Enrollment Message Link Text is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_ABPEnrollmentLinkText), EMBEnrollmentObjects.lbl_ABPEnrollmentLinkText.getObjectName(),dataTable.getData("General_Data", "ABPEnrollmentLinkText"));
				
				// To verify that ABP Enrollment Link is displayed correctly or not in Email
				if(properties.getProperty("Environment").compareTo("Test")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_ABPEnrollmentLink), EMBEnrollmentObjects.lnk_ABPEnrollmentLink.getObjectName(),dataTable.getData("General_Data", "Test_ABPEnrollmentLink"));
				else if(properties.getProperty("Environment").compareTo("QA")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_ABPEnrollmentLink), EMBEnrollmentObjects.lnk_ABPEnrollmentLink.getObjectName(),dataTable.getData("General_Data", "QA_ABPEnrollmentLink"));
				
		}
		catch(Exception ex)
		{
			 report.updateTestLog("ABP Message", "ABP Message is not present in Email as Account is already Enrolled in ABP", Status.PASS);
		}
		navigateToFrame();
		
		// To verify Email Content Text below Blue Banner is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_EMBMessage), EMBEnrollmentObjects.lbl_EMBMessage.getObjectName(),dataTable.getData("General_Data", "EMBMessage"));
		
		// To verify Email Content Header is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_EmailContentHeader), EMBEnrollmentObjects.lbl_EmailContentHeader.getObjectName(),dataTable.getData("General_Data", "EMBEEmailContentHeader"));
		
		// To verify Email Content 1 is displayed correctly
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBEnrollmentObjects.lbl_EmailContent1), EMBEnrollmentObjects.lbl_EmailContent1.getObjectName(),dataTable.getData("General_Data", "EMBEEmailContent1"));
		
		// To verify that Account Number is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBEnrollmentObjects.lbl_EmailAccountNumber), EMBEnrollmentObjects.lbl_EmailAccountNumber.getObjectName(),dataTable.getData("Accounts", "AccountNumber"));
		
		// To verify that Email Address is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_EmailEmailAddress), EMBEnrollmentObjects.lbl_EmailEmailAddress.getObjectName(),dataTable.getData("General_Data", "Username"));
		
		// To verify Email Content 2 is displayed correctly
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBEnrollmentObjects.lbl_EmailContent2), EMBEnrollmentObjects.lbl_EmailContent2.getObjectName(),dataTable.getData("General_Data", "EMBEEmailContent2"));
		
		// To verify Email Content 3 is displayed correctly
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBEnrollmentObjects.lbl_EmailContent3), EMBEnrollmentObjects.lbl_EmailContent3.getObjectName(),dataTable.getData("General_Data", "EMBEEmailContent3"));
		
		
	}

	/**
	 *****************************************************
	 * Function to validate the Email Header Links
	 * @throws IOException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBEHeaderLink() throws InterruptedException, IOException
	{
		navigateToFrame();
		
		// To verify that Login Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_Login), EMBEnrollmentObjects.lnk_Login.getObjectName(),dataTable.getData("General_Data", "Test_Login_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_Login), EMBEnrollmentObjects.lnk_Login.getObjectName(),dataTable.getData("General_Data", "QA_Login_Link"));
		
		navigateToFrame();
		// To verify that Pay Bill Link is displayed correctly or not in Email
		commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_PayBill), EMBEnrollmentObjects.lnk_PayBill.getObjectName(),dataTable.getData("Accounts", "Test_PayBill_Link"));
		
		navigateToFrame();
		// To verify that Contact Us Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_ContactUs), EMBEnrollmentObjects.lnk_ContactUs.getObjectName(),dataTable.getData("General_Data", "Test_Contact_Us_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_ContactUs), EMBEnrollmentObjects.lnk_ContactUs.getObjectName(),dataTable.getData("General_Data", "QA_Contact_Us_Link"));
		
	}
	
	/*******************************************************
	 * 
	 * Function to validate the OHES Message in EMB Enrollment Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_EMBE_OHES_Message() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that OHES Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_OHESPromoHeader), EMBEnrollmentObjects.lbl_OHESPromoHeader.getObjectName(),dataTable.getData("General_Data", "Header"));
		
		// To verify that OHES Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_OHESMessage), EMBEnrollmentObjects.lbl_OHESMessage.getObjectName(),dataTable.getData("General_Data", "Message"));
		
		// To verify that Get Started Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_GetStarted), EMBEnrollmentObjects.lnk_GetStarted.getObjectName(),dataTable.getData("General_Data", "TestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_GetStarted), EMBEnrollmentObjects.lnk_GetStarted.getObjectName(),dataTable.getData("General_Data", "QAUrl"));
		
	}
		
	/*******************************************************
	 * 
	 * Function to validate the Power Tracker Message in EMB Enrollment Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_EMBE_PowerTrackMessage() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that Power Tracker Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_PowertrackerHeader), EMBEnrollmentObjects.lbl_PowertrackerHeader.getObjectName(),dataTable.getData("General_Data", "PTHeader"));
		
		// To verify that Power Tracker Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_PowertrackerMessage), EMBEnrollmentObjects.lbl_PowertrackerMessage.getObjectName(),dataTable.getData("General_Data", "PTMessage"));
		
		// To verify that Here's How Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_Hereshow), EMBEnrollmentObjects.lnk_Hereshow.getObjectName(),dataTable.getData("General_Data", "PTTestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_Hereshow), EMBEnrollmentObjects.lnk_Hereshow.getObjectName(),dataTable.getData("General_Data", "PTQAUrl"));
		
	}
	
	/*******************************************************
	 * 
	 * Function to validate Residential Message in EMB Enrollment Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_EMBE_ResidentialMessage() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that Residential Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_ResidentialHeader), EMBEnrollmentObjects.lbl_ResidentialHeader.getObjectName(),dataTable.getData("General_Data", "ResHeader"));
		
		// To verify that Residential Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_ResidentialMessage), EMBEnrollmentObjects.lbl_ResidentialMessage.getObjectName(),dataTable.getData("General_Data", "ResMessage"));
		
		// To verify that See how Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_Seehow), EMBEnrollmentObjects.lnk_Seehow.getObjectName(),dataTable.getData("General_Data", "ResTestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_Seehow), EMBEnrollmentObjects.lnk_Seehow.getObjectName(),dataTable.getData("General_Data", "ResQAUrl"));
		
	}
	
	/*******************************************************
	 * Function to validate the EMB Enrollment Email Footer Links
	 * @throws IOException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBEFooterLink() throws InterruptedException, IOException
	{
		navigateToFrame();
		// To verify that Energy News Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_EnergyNews), EMBEnrollmentObjects.lnk_EnergyNews.getObjectName(),dataTable.getData("General_Data", "Test_Energy_Usage_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_EnergyNews), EMBEnrollmentObjects.lnk_EnergyNews.getObjectName(),dataTable.getData("General_Data", "QA_Energy_Usage_Link"));
		
		navigateToFrame();
		// To verify that Privacy Policy Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_PrivacyPolicy), EMBEnrollmentObjects.lnk_PrivacyPolicy.getObjectName(),dataTable.getData("General_Data", "Test_Privacy_Policy_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_PrivacyPolicy), EMBEnrollmentObjects.lnk_PrivacyPolicy.getObjectName(),dataTable.getData("General_Data", "QA_Privacy_Policy_Link"));
		
		navigateToFrame();
		// To verify that About Us Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_AboutUs), EMBEnrollmentObjects.lnk_AboutUs.getObjectName(),dataTable.getData("General_Data", "Test_About_Us_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBEnrollmentObjects.lnk_AboutUs), EMBEnrollmentObjects.lnk_AboutUs.getObjectName(),dataTable.getData("General_Data", "QA_About_Us_Link"));
		
		navigateToFrame();
		// To verify that Copyright Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_CopyRight), EMBEnrollmentObjects.lbl_CopyRight.getObjectName(),dataTable.getData("General_Data", "CopyrightMessage"));
		
		// To verify that Footer Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBEnrollmentObjects.lbl_FooterMessage), EMBEnrollmentObjects.lbl_FooterMessage.getObjectName(),dataTable.getData("General_Data", "FooterMessage"));
		
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