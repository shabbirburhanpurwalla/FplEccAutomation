package businesscomponents;


import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import componentgroups.CommonFunctions;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import pageobjects.*;

/**
 * Functional Components class
 * @author Cognizant
 */

/*
 * Last modified by: Ravi
 * Last modified on: 10/13/2014
 * Area of modification: Updated Comments
 * */

public class FunctionalComponents extends ReusableLibrary
{
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	
	/*******************************************************
	 * Constructor to initialize the component library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
	public FunctionalComponents(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	/*******************************************************
	 * Function to return WebElement based on Input Objects
	 * @param loginPageEnum
	 * @return
	 * *****************************************************
	 */
	private  WebElement getPageElement(LoginPageObjects loginPageEnum)
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
	 * *****************************************************
	 */
	private  WebElement getPageElement(EmailContentPageObjects loginPageEnum)
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
	 * 
	 */
	private  WebElement getPageElement(AddAccountObjects loginPageEnum)
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
	 * Function to Invoke Application Under Test
	 * Based on the value of 'Environment' in Global Settings, appropriate application is invoked
	 * 
	 * *****************************************************
	 */
	public void invokeApplication()	
	{		
		
		//Read "Environment" variable from Global Settings
		String Environment = properties.getProperty("Environment");
						
		//Read the Application from the global Setting file
		String Application_URL =properties.getProperty("SharedMailbox_Test");
		
		//Set Application_URL to URL based on "Environment" variable from Global Settings
        if(Environment.compareTo("Test")==0)	
			Application_URL =properties.getProperty("ApplicationUrl_Test");
       //Set Application_URL to QA URL
		else if(Environment.compareTo("QA")==0)
			Application_URL =properties.getProperty("ApplicationUrl_QA");
       //Set Application_URL to Production URL
		else if(Environment.compareTo("Production")==0)
			Application_URL =properties.getProperty("ApplicationUrl");
        
        //Invoke Application Under Test
		driver.get(Application_URL);
		
		//Maximize the browser window
        driver.manage().window().maximize();
        
        //Update test log
		report.updateTestLog("Invoke Application", "Invoke the application under test : " + Application_URL, Status.PASS);
		
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
	}
	
	/*******************************************************
	 *Function to log into the application
	 *@Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void login()	
	{
		//Click Log In link on home page
		commonFunction.clickIfElementPresent(getPageElement(LoginPageObjects.lnkLogin), LoginPageObjects.lnkLogin.getObjectName());
		
		//Read userName and password from General Data sheet 
		String username = commonFunction.validateData("General_Data", "Username",LoginPageObjects.txtUsername.getObjectName());
		String password = commonFunction.validateData("General_Data", "Password",LoginPageObjects.txtPassword.getObjectName());
		
		//Enter userName and password in respective text boxes
		commonFunction.clearAndEnterText(getPageElement(LoginPageObjects.txtUsername), username,LoginPageObjects.txtUsername.getObjectName());
		commonFunction.clearAndEnterText(getPageElement(LoginPageObjects.txtPassword), password,LoginPageObjects.txtPassword.getObjectName());
		
		//Click "Log In" button
		commonFunction.clickIfElementPresent(getPageElement(LoginPageObjects.btnLoginButton), LoginPageObjects.btnLoginButton.getObjectName());
		
	}
	
	/**
	 *****************************************************
	 *Function to add non EMB Enrolled Account to User's profile
	 *@Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void addAccount()
	{
		try
		{
			if(commonFunction.isElementPresent(By.id(AddAccountObjects.lnk_interstitialClose.getProperty()))) 
			{
					driver.findElement(By.id(AddAccountObjects.lnk_interstitialClose.getProperty())).click();
			}
			if(commonFunction.isFramePresent(AddAccountObjects.frm_interstitiallightboxiFrame.getProperty()))
			{
					 driver.switchTo().frame(AddAccountObjects.frm_interstitiallightboxiFrame.getProperty());
					 driver.findElement(By.linkText(AddAccountObjects.lnk_RemindMeLater.getProperty())).click();
					 driver.switchTo().defaultContent();  
			} 
		}
		catch(Exception ex)
		{
			
		}
		//Click on Add/Select Account Link on Account Summary Page
		commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.lnk_SelectAccount), AddAccountObjects.lnk_SelectAccount.getObjectName());
		
		//Click on Select Account Link 
		commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.lnk_AddAccount), AddAccountObjects.lnk_AddAccount.getObjectName());
		
		//Read userName and password from General Data sheet 
		String AccountNumber = commonFunction.validateData("Accounts", "AccountNumber", AddAccountObjects.txt_BillAccount.getObjectName());
		String SSN = commonFunction.validateData("Accounts", "SSN", AddAccountObjects.txt_Ssn.getObjectName());
		
		//Enter Account Number And SSN in respective text boxes
		commonFunction.clearAndEnterText(getPageElement(AddAccountObjects.txt_BillAccount), AccountNumber,AddAccountObjects.txt_BillAccount.getObjectName());
		commonFunction.clearAndEnterText(getPageElement(AddAccountObjects.txt_Ssn), SSN,AddAccountObjects.txt_Ssn.getObjectName());
		
		//Click on Continue Button
		commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.btn_Continue), AddAccountObjects.btn_Continue.getObjectName());
			
		try
		{
				if(commonFunction.isElementPresent(By.id(AddAccountObjects.drp_EmailAddress.getProperty()))) 
				{
					WebElement Drpdown = driver.findElement(By.id(AddAccountObjects.drp_EmailAddress.getProperty()));
					Select DrpEmail = new Select(Drpdown);
					DrpEmail.selectByVisibleText(dataTable.getData("General_Data", "Username"));
				} 
				else 
				{
					frameworkParameters.setStopExecution(true);
					throw new FrameworkException("Select Email Address", "Email Address dropdown is not found");
				}
				
				String winHandleBefore = driver.getWindowHandle();
				for(String winHandle : driver.getWindowHandles())
				{
					//Switch to child window
					driver.switchTo().window(winHandle);
					//driver.close();
				}
				/*
				WebElement BtnChldContinue = driver.findElement(By.id(uifields.btn_ChldContinue));
				BtnChldContinue.click();
				*/
				 
				driver.switchTo().window(winHandleBefore);
				
				if(commonFunction.isElementPresent(By.id(AddAccountObjects.chk_EMBEnrolled.getProperty()))) 
				{
					 if(driver.findElement(By.id(AddAccountObjects.chk_EMBEnrolled.getProperty())).isSelected())
					 {
						 driver.findElement(By.id(AddAccountObjects.chk_EMBEnrolled.getProperty())).click();
					 }
				}
				
				report.updateTestLog("Adding New Account","Email Address is selected Successfully", Status.PASS);
				
				//Click on Submit Button
				commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.btn_Submit), AddAccountObjects.btn_Submit.getObjectName());
		}
		catch(Exception ex)
		{
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Duplicate Account", "Account is already added to Profile");
			
		}
	}
	
	
	/**
	 *****************************************************
	 *Function to select a Account as default to profile
	 *@Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void selectAccount() throws InterruptedException
	{
		try
		{
			if(commonFunction.isElementPresent(By.id(AddAccountObjects.lnk_interstitialClose.getProperty()))) 
			{
					driver.findElement(By.id(AddAccountObjects.lnk_interstitialClose.getProperty())).click();
			}
			if(commonFunction.isFramePresent(AddAccountObjects.frm_interstitiallightboxiFrame.getProperty()))
			{
			 driver.switchTo().frame(AddAccountObjects.frm_interstitiallightboxiFrame.getProperty());
			 driver.findElement(By.linkText(AddAccountObjects.lnk_RemindMeLater.getProperty())).click();
			 driver.switchTo().defaultContent();  
			} 
		}
		catch(Exception ex)
		{
			
		}
		
		//Click on Add/Select Account Link on Account Summary Page
		commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.lnk_SelectAccount), AddAccountObjects.lnk_SelectAccount.getObjectName());
		
		if(commonFunction.isElementPresent(By.name(AddAccountObjects.drp_searchType.getProperty()))) 
		{
			WebElement Drpdown = driver.findElement(By.name(AddAccountObjects.drp_searchType.getProperty()));
			Select DrpSearch = new Select(Drpdown);
			DrpSearch.selectByVisibleText("Account");
		} 
		else 
		{
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Select Search Type", "Account is not found in the dropdown");
		}
		
		//Enter Account Number to be Searched
		commonFunction.clearAndEnterText(getPageElement(AddAccountObjects.txt_SearchFor), dataTable.getData("Accounts", "AccountNumber"),AddAccountObjects.txt_SearchFor.getObjectName());
	
		//Click on Go Button
		commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.btn_GoButton), AddAccountObjects.btn_GoButton.getObjectName());
		
		//Select the Default Account Radio button
		commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.rdo_defaultAccountInfo), AddAccountObjects.rdo_defaultAccountInfo.getObjectName());
		
		try
		{
			if(commonFunction.isElementPresent(By.id(AddAccountObjects.lnk_interstitialClose.getProperty()))) 
			{
					driver.findElement(By.id(AddAccountObjects.lnk_interstitialClose.getProperty())).click();
			}
			if(commonFunction.isFramePresent(AddAccountObjects.frm_interstitiallightboxiFrame.getProperty()))
			{
			 driver.switchTo().frame(AddAccountObjects.frm_interstitiallightboxiFrame.getProperty());
			 driver.findElement(By.linkText(AddAccountObjects.lnk_RemindMeLater.getProperty())).click();
			 driver.switchTo().defaultContent();  
			} 
		}
		catch(Exception ex)
		{
			
		}
		
	}
	
	/**
	 *****************************************************
	 *Function to add EMB Enrolled Account to User's Profile
	 * @throws InterruptedException 
	 *@Modified by 324096 on 31 Oct
	 *	
	 *****************************************************
	*/
	public void addingEMBAccount() throws InterruptedException
	{
		try
		{
			if(commonFunction.isElementPresent(By.id(AddAccountObjects.lnk_interstitialClose.getProperty()))) 
			{
					driver.findElement(By.id(AddAccountObjects.lnk_interstitialClose.getProperty())).click();
			}
			if(commonFunction.isFramePresent(AddAccountObjects.frm_interstitiallightboxiFrame.getProperty()))
			{
			 driver.switchTo().frame(AddAccountObjects.frm_interstitiallightboxiFrame.getProperty());
			 driver.findElement(By.linkText(AddAccountObjects.lnk_RemindMeLater.getProperty())).click();
			 driver.switchTo().defaultContent();  
			} 
		}
		catch(Exception ex)
		{
			
		}
		
		
		//Click on Add/Select Account Link on Account Summary Page
		commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.lnk_SelectAccount), AddAccountObjects.lnk_SelectAccount.getObjectName());
		
		//Click on Select Account Link 
		commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.lnk_AddAccount), AddAccountObjects.lnk_AddAccount.getObjectName());
		
		//Read userName and password from General Data sheet 
		String AccountNumber = commonFunction.validateData("Accounts", "AccountNumber", AddAccountObjects.txt_BillAccount.getObjectName());
		String SSN = commonFunction.validateData("Accounts", "SSN", AddAccountObjects.txt_Ssn.getObjectName());
		
		//Enter Account Number And SSN in respective text boxes
		commonFunction.clearAndEnterText(getPageElement(AddAccountObjects.txt_BillAccount), AccountNumber,AddAccountObjects.txt_BillAccount.getObjectName());
		commonFunction.clearAndEnterText(getPageElement(AddAccountObjects.txt_Ssn), SSN,AddAccountObjects.txt_Ssn.getObjectName());
		
		//Click on Continue Button
		commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.btn_Continue), AddAccountObjects.btn_Continue.getObjectName());
		
		try
		{
				if(commonFunction.isElementPresent(By.id(AddAccountObjects.drp_EmailAddress.getProperty()))) 
				{
					WebElement Drpdown = driver.findElement(By.id(AddAccountObjects.drp_EmailAddress.getProperty()));
					Select DrpEmail = new Select(Drpdown);
					DrpEmail.selectByVisibleText(dataTable.getData("General_Data", "Username"));
					
				} 
				else 
				{
					frameworkParameters.setStopExecution(true);
					throw new FrameworkException("Select Email Address", "Duplicate Account, Account is already added to Profile");
				}
				
				 String winHandleBefore = driver.getWindowHandle();
				 for(String winHandle : driver.getWindowHandles())
				 {
					 //Switch to child window
					 driver.switchTo().window(winHandle);
				 }
				// driver.close();
				 driver.switchTo().window(winHandleBefore);
				 
				 report.updateTestLog("Adding New Account","Email Address is selected Successfully", Status.PASS);
				 	
				 //Click on Submit Button
				 commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.btn_Submit), AddAccountObjects.btn_Submit.getObjectName());
				 
				 selectEMBAccount();
		}
		catch(Exception ex)
		{
			 report.updateTestLog("Adding New Account","Account is already added to Profile", Status.PASS);
			 selectEMBAccount();
			
		}
	}
	
	/**
	 *****************************************************
	 *Function to select EMB Enrolled Account as default to profile
	 *@Modified by 324096 on 31 Oct
	 *	
	 *****************************************************
	*/
	public void selectEMBAccount() throws InterruptedException
	{
		try
		{
			if(commonFunction.isFramePresent(AddAccountObjects.frm_interstitiallightboxiFrame.getProperty()))
			{
			 driver.switchTo().frame(AddAccountObjects.frm_interstitiallightboxiFrame.getProperty());
			 driver.findElement(By.linkText(AddAccountObjects.lnk_RemindMeLater.getProperty())).click();
			 driver.switchTo().defaultContent();  
			} 
		}
		catch(Exception ex)
		{
		}
		//Click on Add/Select Account Link on Account Summary Page
		commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.lnk_SelectAccount), AddAccountObjects.lnk_SelectAccount.getObjectName());
		
		try
		{
				if(commonFunction.isElementPresent(By.name(AddAccountObjects.drp_searchType.getProperty()))) 
				{
					WebElement Drpdown = driver.findElement(By.name(AddAccountObjects.drp_searchType.getProperty()));
					Select DrpSearch = new Select(Drpdown);
					DrpSearch.selectByVisibleText("Account");
				} 
				else 
				{
					frameworkParameters.setStopExecution(true);
					throw new FrameworkException("Select Search Type", "Email Address is not found in the dropdown");
				}
				
				//Enter Account Number to be Searched
				commonFunction.clearAndEnterText(getPageElement(AddAccountObjects.txt_SearchFor), dataTable.getData("Accounts", "AccountNumber"),AddAccountObjects.txt_SearchFor.getObjectName());
			
				//Click on Go Button
				commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.btn_GoButton), AddAccountObjects.btn_GoButton.getObjectName());
				
				try
				{
					if(getPageElement(AddAccountObjects.rdo_defaultAccountInfo).isDisplayed())
					{
						//Select the Default Account Radio button
						commonFunction.clickIfElementPresent(getPageElement(AddAccountObjects.rdo_defaultAccountInfo), AddAccountObjects.rdo_defaultAccountInfo.getObjectName());
					}
					else
					{
						 report.updateTestLog("Selecting Account","Account is already selected as Default to Profile", Status.PASS);
					}
				}
				catch(Exception ex)
				{
					 report.updateTestLog("Selecting Account","Account is already selected as Default to Profile", Status.PASS);
				}
		}
		catch(Exception ex)
		{
			 List<WebElement> oCheckBox = driver.findElements(By.name("defaultAccountInfo"));
			 int iSize = oCheckBox.size();
			 for(int i=0; i < iSize ; i++ ){
			     String sValue = oCheckBox.get(i).getAttribute("value");
			     if (sValue.equalsIgnoreCase(dataTable.getData("Accounts", "AccountNumber")))
			     {
			         oCheckBox.get(i).click();
			         report.updateTestLog("Selecting Account","Account is selected as Default", Status.PASS);
			         break;
			         }
			     }
		}
		try
		{
			if(commonFunction.isElementPresent(By.id(AddAccountObjects.lnk_interstitialClose.getProperty()))) 
			{
					driver.findElement(By.id(AddAccountObjects.lnk_interstitialClose.getProperty())).click();
			}
		}
		catch(Exception ex)
		{
		}
	}
	
	/**
	 *****************************************************
	 *Function to logout of the Application
	 *@Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void logout()
	{
		//Click on LogOut link to logout 
		commonFunction.clickIfElementPresent(getPageElement(LoginPageObjects.lnkLogOut), "LogOut Link");
	}
	
	/*******************************************************
	 * Function to Invoke the WebMail 
	 * Based on the value of 'WebMail' in Global Settings
	 * 
	 * *****************************************************
	 */
	public void invokeWebMail()
	{
		
		//Read "Environment" variable from Global Settings
		String Environment = properties.getProperty("Environment");
				
		//Read the Application from the global Setting file
		String Application_URL =properties.getProperty("SharedMailbox_Test");
		
		//Set Application_URL to Test URL
        if(Environment.compareTo("Test")==0)	
			Application_URL =properties.getProperty("SharedMailbox_Test");
       //Set Application_URL to QA URL
		else if(Environment.compareTo("QA")==0)
			Application_URL =properties.getProperty("SharedMailbox_QA");
       //Set Application_URL to Production URL
		else if(Environment.compareTo("Production")==0)
			Application_URL =properties.getProperty("SharedMailbox");
      
		// Enter the URL in Driver
		driver.get(Application_URL);
		
		// Maximize the Window
		driver.manage().window().maximize();
		
		report.updateTestLog("Invoke Application", "Invoke the application under test : " + Application_URL, Status.PASS);
	}
	
	/*******************************************************
	 * Function to Login into Web Mail
	 * @Modified by 324096 on 10 Oct
	 *	
	 *****************************************************
	*/
	public void loginWebEmail()
	{
		//Read userName and password from General Data sheet 
		String userName = dataTable.getData("General_Data", "WebUsername");
		String password = dataTable.getData("General_Data", "WebPassword");
		
		//Enter userName and password in respective text boxes
		commonFunction.clearAndEnterText(getPageElement(EmailContentPageObjects.txtUsername), userName,EmailContentPageObjects.txtUsername.getObjectName());
		commonFunction.clearAndEnterText(getPageElement(EmailContentPageObjects.txtPassword), password,EmailContentPageObjects.txtPassword.getObjectName());
		
		//Click "Log On" button
		commonFunction.clickIfElementPresent(getPageElement(EmailContentPageObjects.btnLoginButton), EmailContentPageObjects.btnLoginButton.getObjectName());
	}
			
}