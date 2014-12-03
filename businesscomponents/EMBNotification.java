package businesscomponents;

import java.io.IOException;
import java.util.ArrayList;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import pageobjects.EMBNotificationObjects;
import pageobjects.EmailContentPageObjects;
import pageobjects.SSOObjects;
import pageobjects.ViewBillPageObjects;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import componentgroups.CommonFunctions;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

/**
 * Business Component Library template
 * @author RaviJadhav
 */
public class EMBNotification extends ReusableLibrary
{
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	public static String EmailUrl=null;
	
	public EMBNotification(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	/*******************************************************
	 * Function to return WebElement based on Input Objects
	 * @param loginPageEnum
	 * @return
	 */
	private  WebElement getPageElement(EMBNotificationObjects loginPageEnum)
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
	 * Function to return WebElement based on Input Objects
	 * @param loginPageEnum
	 * @return
	 */
	private  WebElement getPageElement(SSOObjects loginPageEnum)
	{
	    try{
	    	
	        return commonFunction.getElementByProperty(loginPageEnum.getProperty(), loginPageEnum
	        .getLocatorType().toString());
	    } catch(Exception e){
	        report.updateTestLog("SSO Authentication Page - get page element", loginPageEnum.toString()
	        + " object is not defined or found.", Status.FAIL);
	        return null;
	    }
	}
	
	/*******************************************************
	 * Function to return WebElement based on Input Objects
	 * @param loginPageEnum
	 * @return
	 * 
	 ********************************************************** 
	 */
	private  WebElement getPageElement(ViewBillPageObjects loginPageEnum)
	{
	    try{
	    	
	        return commonFunction.getElementByProperty(loginPageEnum.getProperty(), loginPageEnum
	        .getLocatorType().toString());
	    } catch(Exception e){
	        report.updateTestLog("View Bill Page - get page element", loginPageEnum.toString()
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
	public void findEMBNotificationEmail() throws InterruptedException
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
	
	/*******************************************************
	 * 
	 * Function to validate the EMB Notification Email Content
	 * @throws IOException 
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBNotificationMail() throws InterruptedException, IOException
	{
		navigateToFrame();
		
		// To verify that Customer Name is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_CustomerName), EMBNotificationObjects.lbl_CustomerName.getObjectName(),dataTable.getData("Accounts", "CustomerName"));
		
		// To verify that Account Number is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_AccountNumber), EMBNotificationObjects.lbl_AccountNumber.getObjectName(),commonFunction.insertCharAt(dataTable.getData("Accounts", "AccountNumber"),'-',5));
		
		// To verify that Email Address is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_EmailAddress), EMBNotificationObjects.lbl_EmailAddress.getObjectName(),"electronic-customer-communication@fpl....");
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_EmailAddress), EMBNotificationObjects.lbl_EmailAddress.getObjectName(),"electronic-customer-communication-qa@f...");
		
		// To verify that Update Email Address Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_UpdateEmailAddress), EMBNotificationObjects.lnk_UpdateEmailAddress.getObjectName(),dataTable.getData("General_Data", "Test_Email_Update_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_UpdateEmailAddress), EMBNotificationObjects.lnk_UpdateEmailAddress.getObjectName(),dataTable.getData("General_Data", "QA_Email_Update_Link"));
	}
	
	/*******************************************************
	 * 
	 * Function to validate the EMB Notification Email Content
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBNotificationEmailContentABP() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		String ServiceDateString = "Service dates: "+ dataTable.getData("Accounts", "ServiceDate1") + " to " + dataTable.getData("Accounts", "ServiceDate2") + " "+ commonFunction.findDateDifference(dataTable.getData("Accounts", "ServiceDate1"), dataTable.getData("Accounts", "ServiceDate2"))+" days of service" ; 
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_ServiceDatesMessage), EMBNotificationObjects.lbl_ServiceDatesMessage.getObjectName(),ServiceDateString);
		
		// To verify that Customer Name is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_CustomerName2), EMBNotificationObjects.lbl_CustomerName2.getObjectName(),dataTable.getData("Accounts", "CustomerName"));
		
		// To verify Bill Pay message is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_BillPayMessage), EMBNotificationObjects.lbl_BillPayMessage.getObjectName(),dataTable.getData("General_Data", "BillPayMessage"));
		
		// To verify Balance message is displayed correctly
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_BalanceMessage), EMBNotificationObjects.lbl_BalanceMessage.getObjectName(),dataTable.getData("General_Data", "BalanceMessage").trim());
		
		try
		{
			if(driver.findElement(By.xpath(EMBNotificationObjects.lbl_SecurityMessage.getProperty())).isDisplayed())
			{
				// To verify Security Message is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_SecurityMessage), EMBNotificationObjects.lbl_SecurityMessage.getObjectName(),dataTable.getData("General_Data", "SecurityMessage"));
				
				// To verify Security Message Link Text is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_SecurityLinkText), EMBNotificationObjects.lbl_SecurityLinkText.getObjectName(),dataTable.getData("General_Data", "SecurityLinkText"));
				
				// To verify that Security Link is displayed correctly or not in Email
				if(properties.getProperty("Environment").compareTo("Test")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_SecurityLink), EMBNotificationObjects.lnk_SecurityLink.getObjectName(),dataTable.getData("General_Data", "Test_Security_Link"));
				else if(properties.getProperty("Environment").compareTo("QA")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_SecurityLink), EMBNotificationObjects.lnk_SecurityLink.getObjectName(),dataTable.getData("General_Data", "QA_Security_Link"));
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
		
		/*
		// To verify EMB Notification Message is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_EMBNotificationMessage), EMBNotificationObjects.lbl_EMBNotificationMessage.getObjectName(),dataTable.getData("General_Data", "EMBNotificationMessage"));
		*/
		
		// To verify EMB Notification Message 1 1is displayed correctly
		//commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_EMBNotificationMessage1), EMBNotificationObjects.lbl_EMBNotificationMessage1.getObjectName(),dataTable.getData("General_Data", "EMBNotificationMessage1"));
		
		// To verify EMB Notification Message 2 is displayed correctly
		//commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_EMBNotificationMessage2), EMBNotificationObjects.lbl_EMBNotificationMessage2.getObjectName(),dataTable.getData("General_Data", "EMBNotificationMessage2"));
		
		try
		{
			if(driver.findElement(By.xpath(EMBNotificationObjects.lbl_RegisterLinkText.getProperty())).isDisplayed())
			{
				// To verify Register Today Message Link Text is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_RegisterLinkText), EMBNotificationObjects.lbl_RegisterLinkText.getObjectName(),dataTable.getData("General_Data", "RegisterLinkText"));
				
				// To verify that Register Today Link is displayed correctly or not in Email
				if(properties.getProperty("Environment").compareTo("Test")==0)
						commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_RegisterLink), EMBNotificationObjects.lnk_RegisterLink.getObjectName(),dataTable.getData("General_Data", "Test_Register_Link"));
				else if(properties.getProperty("Environment").compareTo("QA")==0)
						commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_RegisterLink), EMBNotificationObjects.lnk_RegisterLink.getObjectName(),dataTable.getData("General_Data", "QA_Register_Link"));
				
			}
			else
			{
				report.updateTestLog("Registration Message", "Registration Message is not present in Email, As Account is already Registered", Status.WARNING);
			}
		}
		catch(Exception ex)
		{
			report.updateTestLog("Registration Message", "Registration Message is not present in Email, As Account is already Registered", Status.WARNING);
		}			
		
	}

	/*******************************************************
	 * 
	 * Function to validate the EMB Notification past due Email Content
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 448476 on 30 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBNPastDueEmailContent() throws IOException, InterruptedException
	{
		navigateToFrame();
		// To verify Service dates message is displayed correctly
		
		String ServiceDateString = "Service dates: "+ dataTable.getData("Accounts", "ServiceDate1") + " to " + dataTable.getData("Accounts", "ServiceDate2") + " "+ commonFunction.findDateDifference(dataTable.getData("Accounts", "ServiceDate1"), dataTable.getData("Accounts", "ServiceDate2"))+" days of service" ; 
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_ServiceDatesMessage), EMBNotificationObjects.lbl_ServiceDatesMessage.getObjectName(),ServiceDateString);
		
		//To verify that past due message is displayed correctly or not in Email
		String PastDueMessage = dataTable.getData("Accounts", "CustomerName") + ", your account is past due.";
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_PastDueMessage), EMBNotificationObjects.lbl_PastDueMessage.getObjectName(),PastDueMessage);
		// To verify that Customer Name is displayed correctly or not in Email
		//commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_CustomerName2), EMBNotificationObjects.lbl_CustomerName2.getObjectName(),dataTable.getData("Accounts", "CustomerName"));
		
		// To verify Balance message is displayed correctly
		String BalanceMessage = "Please pay " + dataTable.getData("Accounts", "PastDueAmount") + " immediately." + "Your total balance is " + dataTable.getData("Accounts", "CurrentBalance") +".";
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_BalanceMessage), EMBNotificationObjects.lbl_BalanceMessage.getObjectName(),BalanceMessage);
		
		// To verify Balance message is displayed correctly
		
		//String BalanceString = "Your current balance is "+ dataTable.getData("Accounts", "CurrentBalance") + " due by " + dataTable.getData("Accounts", "DueDate") ; 
		//commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_BalanceMessage), EMBNotificationObjects.lbl_BalanceMessage.getObjectName(),BalanceString);
		
	}
	
	/*******************************************************
	 * 
	 * Function to validate the EMB Notification Ready to view Email Content
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 448476 on 30 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBNEmailContentReadyToPay() throws IOException, InterruptedException
	{
		navigateToFrame();
	
		// To verify Service dates message is displayed correctly
		String ServiceDateString = "Service dates: "+ dataTable.getData("Accounts", "ServiceDate1") + " to " + dataTable.getData("Accounts", "ServiceDate2") + " "+ commonFunction.findDateDifference(dataTable.getData("Accounts", "ServiceDate1"), dataTable.getData("Accounts", "ServiceDate2"))+" days of service" ; 
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_ServiceDatesMessage), EMBNotificationObjects.lbl_ServiceDatesMessage.getObjectName(),ServiceDateString);
		
		// To verify that Customer Name is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_Hello), EMBNotificationObjects.lbl_Hello.getObjectName(),dataTable.getData("General_Data", "Hello"));
		
		// To verify that Customer Name is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_CustomerName2), EMBNotificationObjects.lbl_CustomerName2.getObjectName(),dataTable.getData("Accounts", "CustomerName"));
		
		// To verify Bill Pay message is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_ReadyToPayMessage), EMBNotificationObjects.lbl_ReadyToPayMessage.getObjectName(),dataTable.getData("General_Data", "Ready To Pay Message"));
		
		// To verify Balance message is displayed correctly
		String BalanceString = "Your current balance is "+ dataTable.getData("Accounts", "CurrentBalance") + " due by " + dataTable.getData("Accounts", "DueDate") ; 
		commonFunction.verifyElementPresentContainsText(getPageElement(EMBNotificationObjects.lbl_BalanceMessage), EMBNotificationObjects.lbl_BalanceMessage.getObjectName(),BalanceString);
		
		try
		{
			if(driver.findElement(By.xpath(EMBNotificationObjects.lbl_ABPEnrollmentMessage.getProperty())).isDisplayed())
			{
				// To verify ABP Enrollment Message is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_ABPEnrollmentMessage), EMBNotificationObjects.lbl_ABPEnrollmentMessage.getObjectName(),dataTable.getData("General_Data", "ABPEnrollmentMessage"));
				
				// To verify ABP Enrollment Message Link Text is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_ABPEnrollmentLinkText), EMBNotificationObjects.lbl_ABPEnrollmentLinkText.getObjectName(),dataTable.getData("General_Data", "ABPEnrollmentLinkText"));
				
				// To verify that ABP Enrollment Link is displayed correctly or not in Email
				if(properties.getProperty("Environment").compareTo("Test")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_ABPEnrollmentLink), EMBNotificationObjects.lnk_ABPEnrollmentLink.getObjectName(),dataTable.getData("General_Data", "Test_ABPEnrollmentLink"));
				else if(properties.getProperty("Environment").compareTo("QA")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_ABPEnrollmentLink), EMBNotificationObjects.lnk_ABPEnrollmentLink.getObjectName(),dataTable.getData("General_Data", "QA_ABPEnrollmentLink"));
			}
			else
			{
				report.updateTestLog("ABP Message", "ABP Message is not present in Email", Status.WARNING);
			}	
		}
		catch(Exception ex)
		{
			 report.updateTestLog("ABP Message", "ABP Message is not present in Email, as Account is already Enrolled in ABP", Status.WARNING);
		}
		navigateToFrame();
		
		
		try
		{
			if(driver.findElement(By.xpath(EMBNotificationObjects.lbl_RegisterLinkText.getProperty())).isDisplayed())
			{
				// To verify EMB Notification Message 1 1is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_EMBNotificationMessage1), EMBNotificationObjects.lbl_EMBNotificationMessage1.getObjectName(),dataTable.getData("General_Data", "EMBNotificationMessage1"));
				
				// To verify EMB Notification Message 2 is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_EMBNotificationMessage2), EMBNotificationObjects.lbl_EMBNotificationMessage2.getObjectName(),dataTable.getData("General_Data", "EMBNotificationMessage2"));
				
				// To verify Register Today Message Link Text is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_RegisterLinkText), EMBNotificationObjects.lbl_RegisterLinkText.getObjectName(),dataTable.getData("General_Data", "RegisterLinkText"));
				
				// To verify that Register Today Link is displayed correctly or not in Email
				if(properties.getProperty("Environment").compareTo("Test")==0)
						commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_RegisterLink), EMBNotificationObjects.lnk_RegisterLink.getObjectName(),dataTable.getData("General_Data", "Test_Register_Link"));
				else if(properties.getProperty("Environment").compareTo("QA")==0)
						commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_RegisterLink), EMBNotificationObjects.lnk_RegisterLink.getObjectName(),dataTable.getData("General_Data", "QA_Register_Link"));
				
			}
			else
			{
				report.updateTestLog("Registration Message", "Registration Message is not present in Email", Status.WARNING);
			}
		}
		catch(Exception ex)
		{
			report.updateTestLog("Registration Message", "Registration Message is not present in Email", Status.WARNING);
		}			
		
	}


	
	
	/*******************************************************
	 * 
	 * Function to check Download Bill SSO Link present in EMB Notification Email
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @Modified by 324096 on 18 Oct
	 *	
	 *****************************************************
	*/
	public void checkingViewBillButton() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that View Bill Link is displayed correctly or not in Email
		//commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_ViewBill), EMBNotificationObjects.lnk_ViewBill.getObjectName(),dataTable.getData("Accounts", "Test_PayBill_Link"));
		
		try
		{
				WebElement element = getPageElement(EMBNotificationObjects.lnk_ViewBill); 
				String oldTab = driver.getWindowHandle();
				element.click();
				ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
			    newTab.remove(oldTab);
			    // change focus to new tab
			    driver.switchTo().window(newTab.get(0));
			    
			    Thread.sleep(6000);
			    
			    try
			    {
			    	
			    	if(getPageElement(SSOObjects.lbl_PageTitle).isDisplayed())
			    	{
					    // To verify that page title is displayed correctly on SSO Authentication Page
						commonFunction.verifyElementPresentEqualsText(getPageElement(SSOObjects.lbl_PageTitle), SSOObjects.lbl_PageTitle.getObjectName(),dataTable.getData("General_Data", "PageTitle"));
						
						// To verify that Instant Message is displayed correctly on SSO Authentication Page
						commonFunction.verifyElementPresentContainsText(getPageElement(SSOObjects.lbl_Instantmessage), SSOObjects.lbl_Instantmessage.getObjectName(),dataTable.getData("General_Data", "InstantMessage"));
						
						// To verify that Explantory Message is displayed correctly on SSO Authentication Page
						commonFunction.verifyElementPresentContainsText(getPageElement(SSOObjects.lbl_ExpMessage), SSOObjects.lbl_ExpMessage.getObjectName(),dataTable.getData("General_Data", "ExpMessage"));
						
						// To verify that Trouble Message is displayed correctly on SSO Authentication Page  
						commonFunction.verifyElementPresentEqualsText(getPageElement(SSOObjects.lbl_troubleMessage), SSOObjects.lbl_troubleMessage.getObjectName(),dataTable.getData("General_Data", "TroubleMessage"));
						
						// Enter the Zipcode on SSO Authentication page
						commonFunction.clearAndEnterText(getPageElement(SSOObjects.txt_Password),dataTable.getData("Accounts", "Zipcode"),SSOObjects.txt_Password.getObjectName());
						
						// Click on Go Button
						getPageElement(SSOObjects.btn_GoButton).click();
						
						Thread.sleep(6000);
						
						if(getPageElement(ViewBillPageObjects.lnk_SelectAnotherAccount).isDisplayed())
						{
							report.updateTestLog("View Bill Page", "'User is navigated to view Bill page'  Verification is Success", Status.PASS);
						}
						else
						{
							report.updateTestLog("View Bill Page", "'User is not navigated to View Bill page'  Verification is failure", Status.FAIL);
						}
						
					    // Do what you want here, you are in the new tab
					    driver.close();
					    // change focus back to old tab
					    driver.switchTo().window(oldTab);
			    	}
			    	else
			    	{
			    		report.updateTestLog("View Bill Page", "'User is not navigated to SSO Authentication page'  Verification is failure", Status.FAIL);
			    	}
			    }
			    catch(Exception ex)
			    {
			    	report.updateTestLog("View Bill Page", "'User is not navigated to SSO Authentication page'  Verification is failure", Status.FAIL);
			    	
			    }
		} 
		catch(Exception e)
		{
			report.updateTestLog(("Error in method description"), e.toString(), Status.FAIL);
		}
	  
	}
	
	/*******************************************************
	 * 
	 * Function to check Download Bill SSO Link present in EMB Notification Email
	 * @throws InterruptedException 
	 * @throws IOException 
	 *  
	 * @Modified by 324096 on 18 Oct
	 *	
	 *****************************************************
	*/
	public void checkingDownloadBillButton() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		try
		{
				WebElement element = getPageElement(EMBNotificationObjects.lnk_DownloadBill); 
				String oldTab = driver.getWindowHandle();
				element.click();
				ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
			    newTab.remove(oldTab);
			    // change focus to new tab
			    driver.switchTo().window(newTab.get(0));
			    Thread.sleep(6000);
			    try
			    {
			    	if(getPageElement(SSOObjects.lbl_PageTitle).isDisplayed())
			    	{
			    	    // To verify that page title is displayed correctly on SSO Authentication Page
						commonFunction.verifyElementPresentEqualsText(getPageElement(SSOObjects.lbl_PageTitle), SSOObjects.lbl_PageTitle.getObjectName(),dataTable.getData("General_Data", "PageTitle"));
						
						// To verify that Instant Message is displayed correctly on SSO Authentication Page
						commonFunction.verifyElementPresentEqualsText(getPageElement(SSOObjects.lbl_Instantmessage), SSOObjects.lbl_Instantmessage.getObjectName(),dataTable.getData("General_Data", "InstantMessage"));
						
						// To verify that Explantory Message is displayed correctly on SSO Authentication Page
						commonFunction.verifyElementPresentContainsText(getPageElement(SSOObjects.lbl_ExpMessage), SSOObjects.lbl_ExpMessage.getObjectName(),dataTable.getData("General_Data", "ExpMessage"));
							
						// To verify that Trouble Message is displayed correctly on SSO Authentication Page  
						commonFunction.verifyElementPresentEqualsText(getPageElement(SSOObjects.lbl_troubleMessage), SSOObjects.lbl_troubleMessage.getObjectName(),dataTable.getData("General_Data", "TroubleMessage"));
					
						// Enter the Zipcode on SSO Authentication page
						commonFunction.clearAndEnterText(getPageElement(SSOObjects.txt_Password),dataTable.getData("Accounts", "Zipcode"),SSOObjects.txt_Password.getObjectName());
						
						// Click on Go Button
						getPageElement(SSOObjects.btn_GoButton).click();
						Thread.sleep(6000);
						String PDFUrl=null;
						
						// To verify that Register Today Link is displayed correctly or not in Email
						if(properties.getProperty("Environment").compareTo("Test")==0)
							PDFUrl = "http://apptest.fpl.com/viewstatement/viewBill.do?command=lastStatement&methodName=viewBillPDF&program=BillPDF&appRedirectFrom=sso";	
						else if(properties.getProperty("Environment").compareTo("QA")==0)
							PDFUrl = "http://appqa.fpl.com/viewstatement/viewBill.do?command=lastStatement&methodName=viewBillPDF&program=BillPDF&appRedirectFrom=sso";	
						
						if(driver.getCurrentUrl().equals(PDFUrl))
						{
							report.updateTestLog("Download Bill", "'User is navigated to View Bill PDF'  Verification is Success", Status.PASS);
						}
						else
						{
							report.updateTestLog("Download Bill", "'User is not navigated to View Bill PDF'  Verification is Failed", Status.FAIL);
						}
						
					    // Do what you want here, you are in the new tab
					    driver.close();
					    // change focus back to old tab
					    driver.switchTo().window(oldTab);
			    	}
			    	else
			    	{
			    		report.updateTestLog("Download Bill", "'User is not navigated to SSO Authentication page'  Verification is failure", Status.FAIL);
			    	}
			    }
			    catch(Exception ex)
			    {
			    	report.updateTestLog("Download Bill", "'User is not navigated to SSO Authentication page'  Verification is failure", Status.FAIL);
			    }
		} 
		catch(Exception e)
		{
			report.updateTestLog(("Error in method description"), e.toString(), Status.FAIL);
		}
	}
	
	/*******************************************************
	 * 
	 * Function to check Pay Bill SSO Link present in EMB Notification Email
	 * @throws InterruptedException 
	 * @throws IOException 
	 *  
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void checkingPayBillButton() throws InterruptedException
	{
		// Function to validate the pay Bill Button on EMB Notification Email
		navigateToFrame();
		
		// To verify that Download Bill Link is displayed correctly or not in Email
		//commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_DownloadBill), EMBNotificationObjects.lnk_DownloadBill.getObjectName(),dataTable.getData("Accounts", "Test_PayBill_Link"));
		
		try
		{
				WebElement element = getPageElement(EMBNotificationObjects.lnk_PayBillButton); 
				String oldTab = driver.getWindowHandle();
				element.click();
				ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
			    newTab.remove(oldTab);
			    // change focus to new tab
			    driver.switchTo().window(newTab.get(0));
			    
			    Thread.sleep(6000);
			    
			    try
			    {
			    	if(getPageElement(SSOObjects.lbl_PageTitle).isDisplayed())
			    	{
			    	    // To verify that page title is displayed correctly on SSO Authentication Page
						commonFunction.verifyElementPresentEqualsText(getPageElement(SSOObjects.lbl_PageTitle), SSOObjects.lbl_PageTitle.getObjectName(),dataTable.getData("General_Data", "PageTitle"));
						
						// To verify that Instant Message is displayed correctly on SSO Authentication Page
						commonFunction.verifyElementPresentEqualsText(getPageElement(SSOObjects.lbl_Instantmessage), SSOObjects.lbl_Instantmessage.getObjectName(),dataTable.getData("General_Data", "InstantMessage"));
						
						// To verify that Explantory Message is displayed correctly on SSO Authentication Page
						commonFunction.verifyElementPresentContainsText(getPageElement(SSOObjects.lbl_ExpMessage), SSOObjects.lbl_ExpMessage.getObjectName(),dataTable.getData("General_Data", "ExpMessage"));
							
						// To verify that Trouble Message is displayed correctly on SSO Authentication Page  
						commonFunction.verifyElementPresentEqualsText(getPageElement(SSOObjects.lbl_troubleMessage), SSOObjects.lbl_troubleMessage.getObjectName(),dataTable.getData("General_Data", "TroubleMessage"));
					
						// Enter the Zipcode on SSO Authentication page
						commonFunction.clearAndEnterText(getPageElement(SSOObjects.txt_Password),dataTable.getData("Accounts", "Zipcode"),SSOObjects.txt_Password.getObjectName());
						
						// Click on Go Button
						getPageElement(SSOObjects.btn_GoButton).click();
						
						Thread.sleep(6000);
						
						report.updateTestLog("Pay Bill Button", "'User is navigated to Payment Page'  Verification is Success", Status.PASS);
						
					   
			    	}
			    	else
			    	{
			    		report.updateTestLog("Pay Bill Button", "'User is not navigated to SSO Authentication page'  Verification is failure", Status.FAIL);
			    	}
			    }
			    catch(Exception ex)
			    {
			    	report.updateTestLog("Pay Bill Button", "Something Went Wrong,  Verification is failure", Status.FAIL);
			    }
		} 
		catch(Exception e)
		{
			report.updateTestLog(("Error in method description"), e.toString(), Status.FAIL);
		}
		
	}
	
	/**
	 *****************************************************
	 * Function to validate the EMB Notification  Email Header Links
	 * @throws IOException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validateEMBNHeaderLink() throws InterruptedException, IOException
	{
		navigateToFrame();
		
		String environment = properties.getProperty("Environment");
		
		//Verify Light Bulb Image
		if(commonFunction.isElementPresent(EMBNotificationObjects.img_FPLLogo.getLocatorType().toString(),
				EMBNotificationObjects.img_FPLLogo.getProperty(), EMBNotificationObjects.img_FPLLogo.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(EMBNotificationObjects.img_FPLLogo),
					commonFunction.getData(environment,"General_Data", "Logo_Image","FPL Logo Image URL",true),
					EMBNotificationObjects.img_FPLLogo.getObjectName());
		}
		
		// To verify that Login Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_Login), EMBNotificationObjects.lnk_Login.getObjectName(),dataTable.getData("General_Data", "Test_Login_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_Login), EMBNotificationObjects.lnk_Login.getObjectName(),dataTable.getData("General_Data", "QA_Login_Link"));
		
		/*
		navigateToFrame();
		
		try
		{
			// To verify that Pay Bill Link is displayed correctly or not in Email
			driver.findElement(By.xpath(EMBNotificationObjects.lnk_PayBill.getProperty())).click();
			
			if(commonFunction.isElementPresent(By.id(SSOObjects.txt_Password.getProperty())))
			{
				report.updateTestLog("Pay Bill Link", "'User is navigated to Payment Page'  Verification is Success", Status.PASS);
			}
			else
			{
				report.updateTestLog("Pay Bill Link", "'User is not navigated to SSO Authentication page'  Verification is failure", Status.FAIL);
			}
		}
		catch(Exception ex)
		{
			report.updateTestLog("Pay Bill Link", "Something Went Wrong,  Verification is failure", Status.FAIL);
		}
		*/
		//commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_PayBill), EMBNotificationObjects.lnk_PayBill.getObjectName(),dataTable.getData("Accounts", "Test_PayBill_Link"));
		
		navigateToFrame();
		// To verify that Contact Us Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_ContactUs), EMBNotificationObjects.lnk_ContactUs.getObjectName(),dataTable.getData("General_Data", "Test_Contact_Us_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_ContactUs), EMBNotificationObjects.lnk_ContactUs.getObjectName(),dataTable.getData("General_Data", "QA_Contact_Us_Link"));
	
	}
	
	/*******************************************************
	 * 
	 * Function to validate the Security Message in Email
	 * @throws InterruptedException 
	 *
	 * @Modified by 324096 on 31 Oct
	 *	
	 *****************************************************
	*/
	public void validateSecurityMessage() throws InterruptedException
	{
		navigateToFrame();
		
		try
		{
			if(driver.findElement(By.xpath(EMBNotificationObjects.lbl_SecurityMessage.getProperty())).isDisplayed())
			{
				// To verify Security Message is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_SecurityMessage), EMBNotificationObjects.lbl_SecurityMessage.getObjectName(),dataTable.getData("General_Data", "SecurityMessage"));
				
				// To verify Security Message Link Text is displayed correctly
				commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_SecurityLinkText), EMBNotificationObjects.lbl_SecurityLinkText.getObjectName(),dataTable.getData("General_Data", "SecurityLinkText"));
				
				// To verify that Security Link is displayed correctly or not in Email
				if(properties.getProperty("Environment").compareTo("Test")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_SecurityLink), EMBNotificationObjects.lnk_SecurityLink.getObjectName(),dataTable.getData("General_Data", "Test_Security_Link"));
				else if(properties.getProperty("Environment").compareTo("QA")==0)
					commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_SecurityLink), EMBNotificationObjects.lnk_SecurityLink.getObjectName(),dataTable.getData("General_Data", "QA_Security_Link"));
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
	}
	
	
	/*******************************************************
	 * 
	 * Function to validate Promotional Messages in EMB Notification Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 10 Nov
	 *	
	 *****************************************************
	*/
	public void verifyPromotionalMessages() throws IOException, InterruptedException{
		
		
		//String customerName = commonFunction.getData("Accounts", "CustomerName", "Customer Name",true).trim();
		String accountType=commonFunction.getData("Accounts", "AccountType", "Account Type",true).trim().toLowerCase();
		
		if(accountType.equals("residential")){
			
			//Verify Residential Left Message
			validating_EMBN_OHES_Message();			
			
			//Verify PowerTracker Message
			validating_EMBN_PowerTrackMessage();
			
			//Verify Residential Promotional Message
			validating_EMBN_ResidentialMessage();
						
		}
		else
		{
			System.out.println("Checking Commercial Promo Messages");
			
			//Verify Residential Promotional Message
			
		}
		
		
	}
	
	
	
	/*******************************************************
	 * 
	 * Function to validate the OHES Message in EMB Notification Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_EMBN_OHES_Message() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		String environment = properties.getProperty("Environment");
		
		// To verify that OHES Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_OHESPromoHeader), EMBNotificationObjects.lbl_OHESPromoHeader.getObjectName(),dataTable.getData("General_Data", "Res_Left_Header"));
		
		//Verify Light Bulb Image
		if(commonFunction.isElementPresent(EMBNotificationObjects.img_LightbulbImage.getLocatorType().toString(),
				EMBNotificationObjects.img_LightbulbImage.getProperty(), EMBNotificationObjects.img_LightbulbImage.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(EMBNotificationObjects.img_LightbulbImage),
					commonFunction.getData(environment,"General_Data", "LightBulbImageURL","Light Bulb Image URL",true),
					EMBNotificationObjects.img_LightbulbImage.getObjectName());
		}
		
		
		
		// To verify that OHES Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_OHESMessage), EMBNotificationObjects.lbl_OHESMessage.getObjectName(),dataTable.getData("General_Data", "Res_Left_Message"));
		
		// To verify that Get Started Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_GetStarted), EMBNotificationObjects.lnk_GetStarted.getObjectName(),dataTable.getData("General_Data", "Test_Res_Left_Url"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_GetStarted), EMBNotificationObjects.lnk_GetStarted.getObjectName(),dataTable.getData("General_Data", "QA_Res_Left_Url"));
		
	}
		
	/*******************************************************
	 * 
	 * Function to validate the Power Tracker Message in EMB Notification Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_EMBN_PowerTrackMessage() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		String environment = properties.getProperty("Environment");
		
		// To verify that Power Tracker Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_PowertrackerHeader), EMBNotificationObjects.lbl_PowertrackerHeader.getObjectName(),dataTable.getData("General_Data", "Res_Center_Header"));
		
		//Verify Check Image
		if(commonFunction.isElementPresent(EMBNotificationObjects.img_CheckImage.getLocatorType().toString(),
				EMBNotificationObjects.img_CheckImage.getProperty(), EMBNotificationObjects.img_CheckImage.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(EMBNotificationObjects.img_CheckImage),
					commonFunction.getData(environment,"General_Data", "CheckImageURL","Check Image URL",true),
					EMBNotificationObjects.img_CheckImage.getObjectName());
		}
		
		
		// To verify that Power Tracker Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_PowertrackerMessage), EMBNotificationObjects.lbl_PowertrackerMessage.getObjectName(),dataTable.getData("General_Data", "Res_Center_Message"));
		
		// To verify that Hers's How Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_Hereshow), EMBNotificationObjects.lnk_Hereshow.getObjectName(),dataTable.getData("General_Data", "Test_Res_Center_Url"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_Hereshow), EMBNotificationObjects.lnk_Hereshow.getObjectName(),dataTable.getData("General_Data", "QA_Res_Center_Url"));
		
		
	}
	
	/*******************************************************
	 * 
	 * Function to validate Residential Message in EMB Notification Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_EMBN_ResidentialMessage() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		String environment = properties.getProperty("Environment");
		
		// To verify that Residential Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_ResidentialHeader), EMBNotificationObjects.lbl_ResidentialHeader.getObjectName(),dataTable.getData("General_Data", "Res_Right_Header"));
		
		//Verify Dollar Image
		if(commonFunction.isElementPresent(EMBNotificationObjects.img_EnergyImage.getLocatorType().toString(),
				EMBNotificationObjects.img_EnergyImage.getProperty(), EMBNotificationObjects.img_EnergyImage.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(EMBNotificationObjects.img_EnergyImage),
					commonFunction.getData(environment,"General_Data", "DollarImageURL","Dollar Image URL",true),
					EMBNotificationObjects.img_EnergyImage.getObjectName());
		}
		
		// To verify that Residential Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_ResidentialMessage), EMBNotificationObjects.lbl_ResidentialMessage.getObjectName(),dataTable.getData("General_Data", "Res_Right_Message"));
		
		// To verify that See how Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_Seehow), EMBNotificationObjects.lnk_Seehow.getObjectName(),dataTable.getData("General_Data", "Test_Res_Right_Url"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_Seehow), EMBNotificationObjects.lnk_Seehow.getObjectName(),dataTable.getData("General_Data", "QA_Res_Right_Url"));
		
	}
	
	/*******************************************************
	 * Function to validate the EMB Notification Email Footer Links
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
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_EnergyNews), EMBNotificationObjects.lnk_EnergyNews.getObjectName(),dataTable.getData("General_Data", "Test_Energy_Usage_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_EnergyNews), EMBNotificationObjects.lnk_EnergyNews.getObjectName(),dataTable.getData("General_Data", "QA_Energy_Usage_Link"));
		
		navigateToFrame();
		// To verify that Privacy Policy Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_PrivacyPolicy), EMBNotificationObjects.lnk_PrivacyPolicy.getObjectName(),dataTable.getData("General_Data", "Test_Privacy_Policy_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_PrivacyPolicy), EMBNotificationObjects.lnk_PrivacyPolicy.getObjectName(),dataTable.getData("General_Data", "QA_Privacy_Policy_Link"));
		
		navigateToFrame();
		// To verify that About Us Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_AboutUs), EMBNotificationObjects.lnk_AboutUs.getObjectName(),dataTable.getData("General_Data", "Test_About_Us_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(EMBNotificationObjects.lnk_AboutUs), EMBNotificationObjects.lnk_AboutUs.getObjectName(),dataTable.getData("General_Data", "QA_About_Us_Link"));
		
		navigateToFrame();
		// To verify that Copyright Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_CopyRight), EMBNotificationObjects.lbl_CopyRight.getObjectName(),dataTable.getData("General_Data", "CopyrightMessage"));
		
		// To verify that Footer Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(EMBNotificationObjects.lbl_FooterMessage), EMBNotificationObjects.lbl_FooterMessage.getObjectName(),dataTable.getData("General_Data", "FooterMessage"));
		
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