package businesscomponents;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageobjects.EMBDiscontinueObjects;
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
/**
 * @author Ravi
 *
 */
public class EMBDiscontinue extends ReusableLibrary
{
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	public static String EmailUrl=null;
	
	public EMBDiscontinue(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	/*******************************************************
	 * Function to return WebElement based on Input Objects
	 * @param loginPageEnum
	 * @return
	 */
	private  WebElement getPageElement(EMBDiscontinueObjects loginPageEnum)
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
	 * Function to perform EMB Discontinue Process
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void embDiscontinue()
	{
		//Discontinue Process
		
		//Click Discontinue link on Account Summary page to Discontinue from EMB Enrollment
		commonFunction.clickIfElementPresent(getPageElement(EMBDiscontinueObjects.lnk_Discontinue), EMBDiscontinueObjects.lnk_Discontinue.getObjectName());
				
		//Click on discontinue
		commonFunction.clickIfElementPresent(getPageElement(EMBDiscontinueObjects.lnk_Discontinue2), EMBDiscontinueObjects.lnk_Discontinue2.getObjectName());
		
		String RequestedBy = commonFunction.validateData("Accounts", "RequestedBy", EMBDiscontinueObjects.txt_requestedByName.getObjectName());
		//Enter Requested By Name in present textbox
		commonFunction.clearAndEnterText(getPageElement(EMBDiscontinueObjects.txt_requestedByName), RequestedBy,EMBDiscontinueObjects.txt_requestedByName.getObjectName());
				
		//Click Submit Button to submit the request
		commonFunction.clickIfElementPresent(getPageElement(EMBDiscontinueObjects.btn_Submit), EMBDiscontinueObjects.btn_Submit.getObjectName());
		
		//Check whether Thank You page is displayed or not
		if(getPageElement(EMBDiscontinueObjects.lbl_ThankYou).isDisplayed())
		{
			report.updateTestLog("Thank You Page", "Thank you page is displayed, EMB Discontinue successful", Status.PASS);
		}
		else
		{
			report.updateTestLog("Thank You Page", "Thank you page is not displayed, EMB Discontinue not done", Status.FAIL);
		}
		
	}
	
	/*******************************************************
	 * Function to find the particular EMB Discontinue Email
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void findEMBDiscontinueEmail() throws InterruptedException
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
 		 String title = driver.getTitle();
 		
 		 System.out.println(title);
 		 System.out.println(dataTable.getData("Accounts", "EmailSubject"));
 		 
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

	
	/*******************************************************
	 * Function to find the particular EMB Discontinue Email
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void getEMBDiscontinueEmail() throws InterruptedException
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
					 
				     driver.navigate().refresh();
				     
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
				         //report.updateTestLog("Search Email","Email Found in Share Mail box, Account Number: "+ AccountNumber, Status.PASS);
				         
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
							 //report.updateTestLog("Email", "Email is opened", Status.PASS);
							 
					 		 System.out.println("Email found for EMB Discontinue");
					 		 
							 navigateToFrame();
							 String AccountNumberToCheck = getPageElement(EMBDiscontinueObjects.lbl_AccountNumber).getText();
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
				report.updateTestLog("Search EMBD Email", "EMB Discontinue Mail is found", Status.PASS);
    	}
		else
		{
			    frameworkParameters.setStopExecution(true);
    			throw new FrameworkException("Search EMBD Email", "EMB Discontinue Mail is not found");
    	}
			
	}
	
	/*******************************************************
	 * Function to validate the EMB Discontinue Email Content
	 * @throws IOException 
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBDiscontinueMail() throws InterruptedException, IOException
	{
		navigateToFrame();
				
		// To verify that Customer Name is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBDiscontinueObjects.lbl_CustomerName), EMBDiscontinueObjects.lbl_CustomerName.getObjectName(),dataTable.getData("Accounts", "CustomerName"));
		
		// To verify that Account Number is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBDiscontinueObjects.lbl_AccountNumber), EMBDiscontinueObjects.lbl_AccountNumber.getObjectName(),commonFunction.insertCharAt(dataTable.getData("Accounts", "AccountNumber"),'-',5));
		
		// To verify that Email Address is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBDiscontinueObjects.lbl_EmailAddress), EMBDiscontinueObjects.lbl_EmailAddress.getObjectName(),dataTable.getData("General_Data", "Username"));
		
		/*
		// To verify that Update Email Address Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_UpdateEmailAddress), EMBDiscontinueObjects.lnk_UpdateEmailAddress.getObjectName(),dataTable.getData("General_Data", "Test_Email_Update_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_UpdateEmailAddress), EMBDiscontinueObjects.lnk_UpdateEmailAddress.getObjectName(),dataTable.getData("General_Data", "QA_Email_Update_Link"));
		*/
	}	
	
	/**
	 *****************************************************
	 * Function to validate the Email Header Links
	 * @throws IOException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBDHeaderLink() throws InterruptedException, IOException
	{
		navigateToFrame();
		
		// To verify that Login Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_Login), EMBDiscontinueObjects.lnk_Login.getObjectName(),dataTable.getData("General_Data", "Test_Login_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_Login), EMBDiscontinueObjects.lnk_Login.getObjectName(),dataTable.getData("General_Data", "QA_Login_Link"));
		
		navigateToFrame();
		// To verify that Pay Bill Link is displayed correctly or not in Email
		commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_PayBill), EMBDiscontinueObjects.lnk_PayBill.getObjectName(),dataTable.getData("Accounts", "Test_PayBill_Link"));
		
		navigateToFrame();
		// To verify that Contact Us Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_ContactUs), EMBDiscontinueObjects.lnk_ContactUs.getObjectName(),dataTable.getData("General_Data", "Test_Contact_Us_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_ContactUs), EMBDiscontinueObjects.lnk_ContactUs.getObjectName(),dataTable.getData("General_Data", "QA_Contact_Us_Link"));
		
	}
	
	/*******************************************************
	 * 
	 * Function to validate the EMB Discontinue Email Content
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBDiscontinueEmailContent() throws IOException, InterruptedException
	{
		navigateToFrame();	
		
		// To verify that Customer Name is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBDiscontinueObjects.lbl_CustomerName2), EMBDiscontinueObjects.lbl_CustomerName2.getObjectName(),dataTable.getData("Accounts", "CustomerName"));
		
		// To verify Discontinue Message is displayed correctly
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBDiscontinueObjects.lbl_DiscontinueMessage), EMBDiscontinueObjects.lbl_DiscontinueMessage.getObjectName(),dataTable.getData("General_Data", "DiscontinueMessage"));
				
		try
		{
			if(driver.findElement(By.xpath(EMBDiscontinueObjects.lbl_SecurityMessage.getProperty())).isDisplayed())
			{
				// To verify Security Message is displayed correctly
				commonFunction.verifyElementPresentContainsText(getPageElement(EMBDiscontinueObjects.lbl_SecurityMessage), EMBDiscontinueObjects.lbl_SecurityMessage.getObjectName(),dataTable.getData("General_Data", "SecurityMessage"));
						
				// To verify Security Message Link Text is displayed correctly
				commonFunction.verifyElementPresentContainsText(getPageElement(EMBDiscontinueObjects.lbl_SecurityLinkText), EMBDiscontinueObjects.lbl_SecurityLinkText.getObjectName(),dataTable.getData("General_Data", "SecurityLinkText"));
				
				// To verify that Security Link is displayed correctly or not in Email
				if(properties.getProperty("Environment").compareTo("Test")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_SecurityLink), EMBDiscontinueObjects.lnk_SecurityLink.getObjectName(),dataTable.getData("General_Data", "Test_Security_Link"));
				else if(properties.getProperty("Environment").compareTo("QA")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_SecurityLink), EMBDiscontinueObjects.lnk_SecurityLink.getObjectName(),dataTable.getData("General_Data", "QA_Security_Link"));
			}
			else
			{
				report.updateTestLog("Security Message", "Security Message is not present in Email", Status.WARNING);
			}
		}
		catch(Exception ex)
		{
			report.updateTestLog("Security Message", "Security Message is not present in Email", Status.WARNING);
		}
		navigateToFrame();	
		
		try
		{
				if(driver.findElement(By.xpath(EMBDiscontinueObjects.lbl_ABPMessage.getProperty())).isDisplayed())
				{
						// To verify that ABP Message is displayed correctly or not in Email 
						commonFunction.verifyElementPresentContainsText(getPageElement(EMBDiscontinueObjects.lbl_ABPMessage), EMBDiscontinueObjects.lbl_ABPMessage.getObjectName(),dataTable.getData("General_Data", "ABPEnrollmentMessage"));
										
						// To verify that ABP Enrollment Link Text is displayed correctly or not in Email
						commonFunction.verifyElementPresentContainsText(getPageElement(EMBDiscontinueObjects.lbl_ABPMessageLinkText), EMBDiscontinueObjects.lbl_ABPMessageLinkText.getObjectName(),dataTable.getData("General_Data", "ABPEnrollmentLinkText"));
								
						// To verify that ABP Enrollment Link is displayed correctly or not in Email
						if(properties.getProperty("Environment").compareTo("Test")==0)
							commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_ABPMessageLink), EMBDiscontinueObjects.lnk_ABPMessageLink.getObjectName(),dataTable.getData("General_Data", "Test_ABPEnrollmentLink"));
						else if(properties.getProperty("Environment").compareTo("QA")==0)
							commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_ABPMessageLink), EMBDiscontinueObjects.lnk_ABPMessageLink.getObjectName(),dataTable.getData("General_Data", "QA_ABPEnrollmentLink"));
				}
				else
				{
					report.updateTestLog("ABP Message", "ABP Message is not present in Email as Account is already Enrolled in ABP", Status.WARNING);
				}
				
		}
		catch(Exception ex)
		{
			 report.updateTestLog("ABP Message", "ABP Message is not present in Email as Account is already Enrolled in ABP", Status.WARNING); 
		}
		
		navigateToFrame();	
		
		// To verify that Email Content in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBDiscontinueObjects.lbl_DiscontinueMessage11), EMBDiscontinueObjects.lbl_DiscontinueMessage11.getObjectName(),dataTable.getData("General_Data", "EMBEEmailContent11").trim());
		
		// To verify that Email Content in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBDiscontinueObjects.lbl_DiscontinueMessage12), EMBDiscontinueObjects.lbl_DiscontinueMessage12.getObjectName(),commonFunction.insertCharAt(dataTable.getData("Accounts", "AccountNumber"),'-',5));
		
		// To verify that Email Content in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBDiscontinueObjects.lbl_DiscontinueMessage13), EMBDiscontinueObjects.lbl_DiscontinueMessage13.getObjectName(),dataTable.getData("General_Data", "EMBEEmailContent13").trim());
			
		// To verify that Email Content in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBDiscontinueObjects.lbl_DiscontinueMessage2), EMBDiscontinueObjects.lbl_DiscontinueMessage2.getObjectName(),dataTable.getData("General_Data", "EMBEEmailContent2"));
		
	}
	
	/*******************************************************
	 * 
	 * Function to validate the OHES Message in EMB Discontinue Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_EMBD_OHES_Message() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that OHES Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBDiscontinueObjects.lbl_OHESPromoHeader), EMBDiscontinueObjects.lbl_OHESPromoHeader.getObjectName(),dataTable.getData("General_Data", "Header"));
		
		// To verify that OHES Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBDiscontinueObjects.lbl_OHESMessage), EMBDiscontinueObjects.lbl_OHESMessage.getObjectName(),dataTable.getData("General_Data", "Message"));
		
		// To verify that Get Started Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_GetStarted), EMBDiscontinueObjects.lnk_GetStarted.getObjectName(),dataTable.getData("General_Data", "TestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_GetStarted), EMBDiscontinueObjects.lnk_GetStarted.getObjectName(),dataTable.getData("General_Data", "QAUrl"));
		
	}
		
	/*******************************************************
	 * 
	 * Function to validate the Power Tracker Message in EMB Discontinue Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_EMBD_PowerTrackMessage() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that Power Tracker Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBDiscontinueObjects.lbl_PowertrackerHeader), EMBDiscontinueObjects.lbl_PowertrackerHeader.getObjectName(),dataTable.getData("General_Data", "PTHeader"));
		
		// To verify that Power Tracker Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBDiscontinueObjects.lbl_PowertrackerMessage), EMBDiscontinueObjects.lbl_PowertrackerMessage.getObjectName(),dataTable.getData("General_Data", "PTMessage"));
		
		// To verify that Hers's How Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_Hereshow), EMBDiscontinueObjects.lnk_Hereshow.getObjectName(),dataTable.getData("General_Data", "PTTestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_Hereshow), EMBDiscontinueObjects.lnk_Hereshow.getObjectName(),dataTable.getData("General_Data", "PTQAUrl"));
		
	}
	
	/*******************************************************
	 * 
	 * Function to validate Residential Message in EMB Discontinue Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_EMBD_ResidentialMessage() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that Residential Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBDiscontinueObjects.lbl_ResidentialHeader), EMBDiscontinueObjects.lbl_ResidentialHeader.getObjectName(),dataTable.getData("General_Data", "ResHeader"));
		
		// To verify that Residential Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBDiscontinueObjects.lbl_ResidentialMessage), EMBDiscontinueObjects.lbl_ResidentialMessage.getObjectName(),dataTable.getData("General_Data", "ResMessage"));
		
		// To verify that See how Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_Seehow), EMBDiscontinueObjects.lnk_Seehow.getObjectName(),dataTable.getData("General_Data", "ResTestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_Seehow), EMBDiscontinueObjects.lnk_Seehow.getObjectName(),dataTable.getData("General_Data", "ResQAUrl"));
		
	}

	/*******************************************************
	 * Function to validate the EMB Discontinue Email Footer Links
	 * @throws IOException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBDFooterLink() throws InterruptedException, IOException
	{
		navigateToFrame();
		// To verify that Energy News Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_EnergyNews), EMBDiscontinueObjects.lnk_EnergyNews.getObjectName(),dataTable.getData("General_Data", "Test_Energy_Usage_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_EnergyNews), EMBDiscontinueObjects.lnk_EnergyNews.getObjectName(),dataTable.getData("General_Data", "QA_Energy_Usage_Link"));
		
		navigateToFrame();
		// To verify that Privacy Policy Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_PrivacyPolicy), EMBDiscontinueObjects.lnk_PrivacyPolicy.getObjectName(),dataTable.getData("General_Data", "Test_Privacy_Policy_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_PrivacyPolicy), EMBDiscontinueObjects.lnk_PrivacyPolicy.getObjectName(),dataTable.getData("General_Data", "QA_Privacy_Policy_Link"));
		
		navigateToFrame();
		// To verify that About Us Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_AboutUs), EMBDiscontinueObjects.lnk_AboutUs.getObjectName(),dataTable.getData("General_Data", "Test_About_Us_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBDiscontinueObjects.lnk_AboutUs), EMBDiscontinueObjects.lnk_AboutUs.getObjectName(),dataTable.getData("General_Data", "QA_About_Us_Link"));
		
		navigateToFrame();
		// To verify that Copyright Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBDiscontinueObjects.lbl_CopyRight), EMBDiscontinueObjects.lbl_CopyRight.getObjectName(),dataTable.getData("General_Data", "CopyrightMessage"));
		
		// To verify that Footer Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBDiscontinueObjects.lbl_FooterMessage), EMBDiscontinueObjects.lbl_FooterMessage.getObjectName(),dataTable.getData("General_Data", "FooterMessage"));
				
		
	}
	
	/*******************************************************
	 * Function to navigate to particular Frame on Web Page
	 * @throws InterruptedException 
	 * @Modified by 324096 on 18 Oct
	 *	
	 *****************************************************
	*/
	private void navigateToFrame() throws InterruptedException 
	{
		driver.get(EmailUrl);
		Thread.sleep(5000);
		if(commonFunction.isFramePresent(EmailContentPageObjects.frm_EMailFrame.getProperty()))
		{
			driver.switchTo().frame(EmailContentPageObjects.frm_EMailFrame.getProperty());
		}
	}	
	
	
}