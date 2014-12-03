package businesscomponents;

import java.io.IOException;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageobjects.EmailContentPageObjects;
import pageobjects.POLPaymentObjects;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import componentgroups.CommonFunctions;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;


/**
 * Business Component Library template
 * @author RaviJadhav
 */
public class POLPayment extends ReusableLibrary
{
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	public static String EmailUrl=null;
	
	public POLPayment(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	/*******************************************************
	 * Function to return WebElement based on Input Objects
	 * @param loginPageEnum
	 * @return
	 */
	
	private  WebElement getPageElement(POLPaymentObjects loginPageEnum)
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
	 * Function to do Pay Online Payment
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void polPayment2()
	{
		//POL Payment Process 
		
		//Click Discontinue link on Account Summary page to Discontinue from EMB Enrollment
		commonFunction.clickIfElementPresent(getPageElement(POLPaymentObjects.lnk_PayBill), POLPaymentObjects.lnk_PayBill.getObjectName());
				
		//Click on otherDue Amount Radio button
		commonFunction.clickIfElementPresent(getPageElement(POLPaymentObjects.rdo_otherDueAmount), POLPaymentObjects.rdo_otherDueAmount.getObjectName());
		
		/*
		//Enter the Amount to be paid
		commmonFunction.clearAndEnterText(getPageElement(POLPaymentObjects.txt_dollars), dataTable.getData("Accounts", "Amount"),POLPaymentObjects.txt_dollars.getObjectName());
		*/
		
		System.out.println(dataTable.getData("Accounts", "Amount"));
		getPageElement(POLPaymentObjects.txt_dollars).sendKeys(dataTable.getData("Accounts", "Amount"));
		//Enter the Requested By Name
		commonFunction.clearAndEnterText(getPageElement(POLPaymentObjects.txt_requestedByName), dataTable.getData("Accounts", "RequestedBy"),POLPaymentObjects.txt_requestedByName.getObjectName());
		
		/*
		//Click on otherDue Amount Radio button
		commmonFunction.clickIfElementPresent(getPageElement(POLPaymentObjects.btn_Pay), POLPaymentObjects.btn_Pay.getObjectName());
		*/
		
		driver.findElement(By.cssSelector("div.button_B > a")).click();
		
		Alert alert = driver.switchTo().alert();
		//String alertText = alert.getText();
		//System.out.println(alertText);
		report.updateTestLog("POL Payment", "Confirmation Message is displayed", Status.PASS);
		alert.accept();
		    
		report.updateTestLog("POL Payment Done", "Payment done successfully", Status.PASS);
		
	}
	
	/*******************************************************
	 * Function to do Pay Online Payment from Fpl.com
	 * @Modified by 324096 on 21 Oct
	 *	
	 *****************************************************
	*/
	public void polPayment()
	{
		//POL Payment Process from Fpl.com
		
		//Click on Pay Bill Link on Account Summary page to do Online Payment
		commonFunction.clickIfElementPresent(getPageElement(POLPaymentObjects.lnk_PayBill), POLPaymentObjects.lnk_PayBill.getObjectName());
		
		//Click on otherDue Amount Radio button
		commonFunction.clickIfElementPresent(getPageElement(POLPaymentObjects.rdo_otherDueAmount), POLPaymentObjects.rdo_otherDueAmount.getObjectName());
		
		String Amount = commonFunction.validateData("Accounts", "Amount", POLPaymentObjects.txt_dollars.getObjectName());
		//Enter the Amount to be paid
		driver.findElement(By.id(POLPaymentObjects.txt_dollars.getProperty())).sendKeys(Amount);
		
		String RequestedBy = commonFunction.validateData("Accounts", "RequestedBy", POLPaymentObjects.txt_requestedByName.getObjectName());
		//Enter the Requested By Name
		commonFunction.clearAndEnterText(getPageElement(POLPaymentObjects.txt_requestedByName), RequestedBy,POLPaymentObjects.txt_requestedByName.getObjectName());
				
		//driver.findElement(By.name("embAccepted")).click();
			
		driver.findElement(By.cssSelector(POLPaymentObjects.btn_Pay.getProperty())).click();
			
		Alert alert = driver.switchTo().alert();
		alert.accept();
		
		//Check whether Thank You page is displayed or not
		if(getPageElement(POLPaymentObjects.lbl_ThankYou).isDisplayed())
		{
			report.updateTestLog("Thank You Page", "Thank you page is displayed, Payment done successfully", Status.PASS);
		}
		else
		{
			report.updateTestLog("Thank You Page", "Thank you page is not displayed, Payment not done, Something went Wrong", Status.FAIL);
		}
	}
	
	/*******************************************************
	 * Function to do Pay Online Payment from SSO Flow
	 * @Modified by 324096 on 21 Oct
	 *	
	 *****************************************************
	*/
	public void polPaymentSSO()
	{
		//POL Payment Process from SSO 
		
		//Click on otherDue Amount Radio button
		commonFunction.clickIfElementPresent(getPageElement(POLPaymentObjects.rdo_otherDueAmount), POLPaymentObjects.rdo_otherDueAmount.getObjectName());
				    
		String Amount = commonFunction.validateData("Accounts", "Amount", POLPaymentObjects.txt_dollars.getObjectName());
		//Enter the Amount to be paid
		driver.findElement(By.id(POLPaymentObjects.txt_dollars.getProperty())).sendKeys(Amount);
		
		
		String RequestedBy = commonFunction.validateData("Accounts", "RequestedBy", POLPaymentObjects.txt_requestedByName.getObjectName());
		//Enter the Requested By Name
		commonFunction.clearAndEnterText(getPageElement(POLPaymentObjects.txt_requestedByName), RequestedBy,POLPaymentObjects.txt_requestedByName.getObjectName());
				
			
		//driver.findElement(By.cssSelector("div.button_B > a")).click();
		driver.findElement(By.cssSelector(POLPaymentObjects.btn_Pay.getProperty())).click();	
		
		Alert alert = driver.switchTo().alert();
		alert.accept();
		    
		//Check whether Thank You page is displayed or not
		if(getPageElement(POLPaymentObjects.lbl_ThankYou).isDisplayed())
		{
			report.updateTestLog("Thank You Page", "Thank you page is displayed, Payment done successfully", Status.PASS);
		}
		else
		{
			report.updateTestLog("Thank You Page", "Thank you page is not displayed, Payment not done, Something went Wrong", Status.FAIL);
		}
		    
		// Do what you want here, you are in the new tab
		driver.close();
		// change focus back to old tab
		//driver.switchTo().window(oldTab);    
	
	}
	
	/*******************************************************
	 * 
	 * Function to find particular POL Payment Confirmation Email
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void findPOLPaymentEmail() throws InterruptedException
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
 		 
 		 System.out.println("|"+title+"|");
 		 System.out.println("|"+dataTable.getData("Accounts", "EmailSubject")+"|");
		 
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
	
	public void getPOLPaymentEmail() throws InterruptedException
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
							 String AccountNumberToCheck = getPageElement(POLPaymentObjects.lbl_AccountNumber).getText();
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
			    report.updateTestLog("Search POL Email", "POL Payment Confirmation Mail is found", Status.PASS);
    	}
		else
		{
			    frameworkParameters.setStopExecution(true);
    			throw new FrameworkException("Search POL Email", "POL Payment Confirmation Mail is not found");
    	}
	}

	
	
	/*******************************************************
	 * 
	 * Function to validate the POL Payment Confirmation Email Content
	 * @throws IOException 
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void validatePOLPaymentEmail() throws InterruptedException, IOException
	{
		navigateToFrame();
				
		// To verify that Customer Name is displayed correctly or not in Email
	    commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_CustomerName), POLPaymentObjects.lbl_CustomerName.getObjectName(),dataTable.getData("Accounts", "CustomerName"));

		//To verify that Account Number is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(POLPaymentObjects.lbl_AccountNumber), POLPaymentObjects.lbl_AccountNumber.getObjectName(),commonFunction.insertCharAt(dataTable.getData("Accounts", "AccountNumber"),'-',5));
		
		// To verify that Email Address is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_EmailAddress), POLPaymentObjects.lbl_EmailAddress.getObjectName(),dataTable.getData("General_Data", "Username").trim());
		
		// To verify that Update Email Address Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_UpdateEmailAddress), POLPaymentObjects.lnk_UpdateEmailAddress.getObjectName(),dataTable.getData("General_Data", "Test_Email_Update_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_UpdateEmailAddress), POLPaymentObjects.lnk_UpdateEmailAddress.getObjectName(),dataTable.getData("General_Data", "QA_Email_Update_Link"));
		
	}
	
	/**
	 *****************************************************
	 * Function to validate the Email Header Links
	 * @throws IOException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validatePOLHeaderLink() throws InterruptedException, IOException
	{
		navigateToFrame();
		// To verify that Login Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_Login), POLPaymentObjects.lnk_Login.getObjectName(),dataTable.getData("General_Data", "Test_Login_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_Login), POLPaymentObjects.lnk_Login.getObjectName(),dataTable.getData("General_Data", "QA_Login_Link"));
		
		navigateToFrame();
		// To verify that Pay Bill Link is displayed correctly or not in Email
		commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_PayBill2), POLPaymentObjects.lnk_PayBill2.getObjectName(),dataTable.getData("Accounts", "Test_PayBill_Link"));
		
		navigateToFrame();
		// To verify that Contact Us Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_ContactUs), POLPaymentObjects.lnk_ContactUs.getObjectName(),dataTable.getData("General_Data", "Test_Contact_Us_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_ContactUs), POLPaymentObjects.lnk_ContactUs.getObjectName(),dataTable.getData("General_Data", "QA_Contact_Us_Link"));
	
	}
	
	/*******************************************************
	 * 
	 * Function to validate the POL Payment Confirmation Email Content
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void validatePOLPaymentEmailContent() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that Customer Name is displayed correctly or not in Email
		commonFunction.verifyElementPresentContainsText(getPageElement(POLPaymentObjects.lbl_CustomerName2), POLPaymentObjects.lbl_CustomerName2.getObjectName(),dataTable.getData("Accounts", "CustomerName"));
				
		/*
		// To verify Pay Online Message is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_POLMessage), POLPaymentObjects.lbl_POLMessage.getObjectName(),dataTable.getData("General_Data", "POLMessage"));
		
			*/
	
		// To verify Security Message is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_SecurityMessage), POLPaymentObjects.lbl_SecurityMessage.getObjectName(),dataTable.getData("General_Data", "SecurityMessage"));
		
		// To verify Security Message Link Text is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_SecurityLinkText), POLPaymentObjects.lbl_SecurityLinkText.getObjectName(),dataTable.getData("General_Data", "SecurityLinkText"));
		
		
		/*
		// To verify FPL email Enroll Message is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_EmailEnrollMessage), POLPaymentObjects.lbl_EmailEnrollMessage.getObjectName(),dataTable.getData("General_Data", "EmailEnrollMessage").trim());
		
		
		// To verify FPL email enrollment Link Text is displayed correctly
	   //	commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_EmailEnrollLinkText), POLPaymentObjects.lbl_EmailEnrollLinkText.getObjectName(),dataTable.getData("General_Data", "EmailEnrollLinkText"));
		
		// To verify FPL Email Message Text is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_FPLEmailMessage), POLPaymentObjects.lbl_FPLEmailMessage.getObjectName(),dataTable.getData("General_Data", "FPLEmailMessage").trim());
		
		// To verify FPL Email Message Text is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_FPLEmailMessage1), POLPaymentObjects.lbl_FPLEmailMessage1.getObjectName(),dataTable.getData("General_Data", "FPLEmailMessage1"));
		
		// To verify Pay Online Link Text is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_PayOnlineLink), POLPaymentObjects.lbl_PayOnlineLink.getObjectName(),dataTable.getData("General_Data", "PayOnlineLink").trim());
		
		System.out.println(dataTable.getData("General_Data", "PayOnlineLink").trim());
		
		// To verify FPL Email Message Text is displayed correctly
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_FPLEmailMessage2), POLPaymentObjects.lbl_FPLEmailMessage2.getObjectName(),dataTable.getData("General_Data", "FPLEmailMessage2").trim());
		*/
	}

	/*******************************************************
	 * 
	 * Function to validate the OHES Message in POL Payment Confirmation Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_POL_OHES_Message() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that OHES Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_OHESPromoHeader), POLPaymentObjects.lbl_OHESPromoHeader.getObjectName(),dataTable.getData("General_Data", "Header"));
		
		// To verify that OHES Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_OHESMessage), POLPaymentObjects.lbl_OHESMessage.getObjectName(),dataTable.getData("General_Data", "Message"));
		
		// To verify that Get Started Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_GetStarted), POLPaymentObjects.lnk_GetStarted.getObjectName(),dataTable.getData("General_Data", "TestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_GetStarted), POLPaymentObjects.lnk_GetStarted.getObjectName(),dataTable.getData("General_Data", "QAUrl"));
			
	}
		
	/*******************************************************
	 * 
	 * Function to validate the Power Tracker Message in POL Payment Confirmation Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_POL_PowerTrackMessage() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that Power Tracker Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_PowertrackerHeader), POLPaymentObjects.lbl_PowertrackerHeader.getObjectName(),dataTable.getData("General_Data", "PTHeader"));
		
		// To verify that Power Tracker Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_PowertrackerMessage), POLPaymentObjects.lbl_PowertrackerMessage.getObjectName(),dataTable.getData("General_Data", "PTMessage"));
		
		// To verify that Hers's How Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_Hereshow), POLPaymentObjects.lnk_Hereshow.getObjectName(),dataTable.getData("General_Data", "PTTestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_Hereshow), POLPaymentObjects.lnk_Hereshow.getObjectName(),dataTable.getData("General_Data", "PTQAUrl"));
		
	}
	
	/*******************************************************
	 * 
	 * Function to validate Residential Message in POL Payment Confirmation Email
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validating_POL_ResidentialMessage() throws IOException, InterruptedException
	{
		navigateToFrame();
		
		// To verify that Residential Promo Message Header is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_ResidentialHeader), POLPaymentObjects.lbl_ResidentialHeader.getObjectName(),dataTable.getData("General_Data", "ResHeader"));
		
		// To verify that Residential Promo Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_ResidentialMessage), POLPaymentObjects.lbl_ResidentialMessage.getObjectName(),dataTable.getData("General_Data", "ResMessage"));
		
		// To verify that See how Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_Seehow), POLPaymentObjects.lnk_Seehow.getObjectName(),dataTable.getData("General_Data", "ResTestUrl"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_Seehow), POLPaymentObjects.lnk_Seehow.getObjectName(),dataTable.getData("General_Data", "ResQAUrl"));
	
	}
	
	/*******************************************************
	 * Function to validate POL Payment Confirmation Email Footer Links
	 * @throws IOException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void validatePOLFooterLink() throws InterruptedException, IOException
	{
		navigateToFrame();
		
		// To verify that Energy News Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_EnergyNews), POLPaymentObjects.lnk_EnergyNews.getObjectName(),dataTable.getData("General_Data", "Test_Energy_Usage_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_EnergyNews), POLPaymentObjects.lnk_EnergyNews.getObjectName(),dataTable.getData("General_Data", "QA_Energy_Usage_Link"));
		
		navigateToFrame();
		// To verify that Privacy Policy Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_PrivacyPolicy), POLPaymentObjects.lnk_PrivacyPolicy.getObjectName(),dataTable.getData("General_Data", "Test_Privacy_Policy_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_PrivacyPolicy), POLPaymentObjects.lnk_PrivacyPolicy.getObjectName(),dataTable.getData("General_Data", "QA_Privacy_Policy_Link"));
		
		navigateToFrame();
		// To verify that About Us Link is displayed correctly or not in Email
		if(properties.getProperty("Environment").compareTo("Test")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_AboutUs), POLPaymentObjects.lnk_AboutUs.getObjectName(),dataTable.getData("General_Data", "Test_About_Us_Link"));
		else if(properties.getProperty("Environment").compareTo("QA")==0)
			commonFunction.verifyLinkInWebPage(getPageElement(POLPaymentObjects.lnk_AboutUs), POLPaymentObjects.lnk_AboutUs.getObjectName(),dataTable.getData("General_Data", "QA_About_Us_Link"));
		
		navigateToFrame();
		// To verify that Copyright Message is displayed correctly or not in Email
		commonFunction.verifyElementPresentEqualsText(getPageElement(POLPaymentObjects.lbl_CopyRight), POLPaymentObjects.lbl_CopyRight.getObjectName(),dataTable.getData("General_Data", "CopyrightMessage"));
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