package businesscomponents;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import pageobjects.ViewBillPageObjects;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import componentgroups.CommonFunctions;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

/**************************************************
 * View Bill
 * 
 * This class contains all the resuable components 
 * used for View Bill flow
 * 
 * @author 387478
 * @modified_by 387478 on 11 November
 * *************************************************
 */
public class ViewBill extends ReusableLibrary{
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	Actions action = new Actions(driver);
	public String accountType;

	public ViewBill(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}

	/***********************************************************
	 * CRAFT View Bill Page
	 * 
	 * This function navigates to View Bill Page after user is 
	 * logged into the application and verifies various elements
	 * present on the page
	 * 
	 * @author 324096
	 * @throws IOException 
	 * @throws AWTException 
	 * @modified by 387478 on Oct 13 - Incorporated Page Object Model
	 * ***********************************************************
	 */
	public void viewBillPage() throws InterruptedException, IOException, AWTException{
		int timeout = 10;

		//Click View Bill Link
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lnk_ViewBill), ViewBillPageObjects.lnk_ViewBill.getObjectName());
		commonFunction.waitForElementVisibility(ViewBillPageObjects.lbl_BalanceLineTooltip.getProperty(),timeout);

		//Retrieve Data from Accounts sheet
		String accountNumber = commonFunction.getData("Accounts", "AccountNumber","Account Number",true);		
		accountNumber = accountNumber.trim();

		//Pad accountnumber with zeros
		if(accountNumber.length()> 0 && accountNumber.length()<10)
			accountNumber = String.format("%010d", Integer.parseInt(accountNumber));

		//Click AccountNumber link
		try{
			driver.findElement(By.linkText(accountNumber)).click();
			report.updateTestLog("Account Number Link", "Account Number '"+ accountNumber +"' Link is present and clicked", Status.PASS);
		}catch(Exception e){
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Account Number Link", "Link '"+accountNumber+"' not displayed");
		}

		//Navigate back to ViewBill page
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lnk_ViewBill), ViewBillPageObjects.lnk_ViewBill.getObjectName());		

		commonFunction.waitForElementVisibility(ViewBillPageObjects.lbl_BalanceLineTooltip.getProperty(),timeout);
		//Verify Another Account link
		/*commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lnk_SelectAnotherAccount), 
											ViewBillPageObjects.lnk_SelectAnotherAccount.getObjectName());*/

		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_SelectAnotherAccount.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_SelectAnotherAccount.getProperty(), ViewBillPageObjects.lnk_SelectAnotherAccount.getObjectName(),true))				
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_SelectAnotherAccount),
					commonFunction.getData(properties.getProperty("Environment"),"General_Data", "SelectAnotherAccount","Select Another Account URL",true),
					ViewBillPageObjects.lnk_SelectAnotherAccount.getObjectName()
			);		

		commonFunction.waitForElementVisibility(ViewBillPageObjects.lbl_BalanceLineTooltip.getProperty(),timeout);
		//Verify customer name is displayed
		getPageElement(ViewBillPageObjects.lbl_CustomerName);	

		//Keep in Mind Label
		commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_KeepInMind.getLocatorType().toString(),
				ViewBillPageObjects.lbl_KeepInMind.getProperty(), ViewBillPageObjects.lbl_KeepInMind.getObjectName());
		Thread.sleep(5000);
		//Get Count of Keep in Mind Messages
		int keepInMindSize =0;
		try{
			keepInMindSize = getPageElements(ViewBillPageObjects.lbl_KeepInMindMsg).size();
			//report.updateTestLog("Keep in Mind Messages",keepInMindSize + " Keep in Mind Messages are displayed", Status.DONE);
		}catch(Exception e){
			report.updateTestLog("Keep in Mind Messages","Keep in Mind Messages not displayed", Status.WARNING);	
		}

		//System.out.println(keepInMindSize);
		if(keepInMindSize > 0){
			String keepInMindMsgProperty;
			for(int i=0; i< keepInMindSize;i++){
				int msgNumber = i +1;
				//System.out.println("Iteration " + msgNumber);

				keepInMindMsgProperty = ViewBillPageObjects.lbl_KeepInMindMsg1.getProperty() + msgNumber;

				if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_KeepInMindMsg1.getLocatorType().toString(),
						keepInMindMsgProperty, ViewBillPageObjects.lbl_KeepInMindMsg1.getObjectName() +" "+msgNumber,true)){
					//System.out.println("Message Found");
					//System.out.println("Locator:" + ViewBillPageObjects.lbl_KeepInMindMsg1.getLocatorType().toString());
					//System.out.println("Property:" + keepInMindMsgProperty);
					String keepInMindMessage = getPageElement(keepInMindMsgProperty,
							ViewBillPageObjects.lbl_KeepInMindMsg1.getLocatorType().toString()).getText();

					//System.out.println("Message Contents:" + keepInMindMessage);
					if(!keepInMindMessage.isEmpty()){
						report.updateTestLog(ViewBillPageObjects.lbl_KeepInMindMsg1.getObjectName()+msgNumber,
								"Message Contents:"+keepInMindMessage, Status.PASS);
						//System.out.println("Message is not empty");	
						if(keepInMindMessage.contains("more")){
							String keepInMindMoreProperty = ViewBillPageObjects.lnk_KeepInMindMore.getProperty() + msgNumber +"']/a";
							//System.out.println("Keep in Mind property"+keepInMindMoreProperty);
							WebElement keepInMindMore = getPageElement(keepInMindMoreProperty,
									ViewBillPageObjects.lnk_KeepInMindMore.getLocatorType().toString());
							Robot robot = new Robot();
							robot.mouseMove(0, 0);
							Actions builder = new Actions(driver);
							builder.moveToElement(keepInMindMore).build().perform();

							report.updateTestLog("Move Mouse over '...more'", "Mouse Moved", Status.SCREENSHOT);

							String keepInMindExpanded="";
							if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_KeepInMindExpandedMsg.getLocatorType().toString(),
									ViewBillPageObjects.lbl_KeepInMindExpandedMsg.getProperty(), ViewBillPageObjects.lbl_KeepInMindExpandedMsg.getObjectName(),false)){						
								keepInMindExpanded = getPageElement(ViewBillPageObjects.lbl_KeepInMindExpandedMsg).getText();
								report.updateTestLog(ViewBillPageObjects.lbl_KeepInMindExpandedMsg.getObjectName(),
										"Expanded Message Contents:"+keepInMindExpanded, Status.PASS);
								getPageElement(ViewBillPageObjects.lbl_KeepInMind).click();
								builder.moveToElement(getPageElement(ViewBillPageObjects.lbl_KeepInMind)).build().perform();

								robot.mouseMove(0, 0);
							}else{
								report.updateTestLog("Keep in Mind Full", "Full Keep in Mind Message not displayed", Status.FAIL);
							}
						}

						if(i!=keepInMindSize-1){
							break;
							/*if(!commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lnk_KepInMindNext), 
									ViewBillPageObjects.lnk_KepInMindNext.getObjectName())){

							}*/
						}

					}else{				
						report.updateTestLog(ViewBillPageObjects.lbl_KeepInMindMsg1.getObjectName() + msgNumber,
								ViewBillPageObjects.lbl_KeepInMindMsg1.getObjectName()+ msgNumber+" is blank.", Status.WARNING);				

					}

				}				
				break;
			}

		}
		/*//lbl_KeepInMindMsg1
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_KeepInMindMsg1.getLocatorType().toString(),
				ViewBillPageObjects.lbl_KeepInMindMsg1.getProperty(), ViewBillPageObjects.lbl_KeepInMindMsg1.getObjectName(),true)){

			String keepInMindMessage = getPageElement(ViewBillPageObjects.lbl_KeepInMindMsg1).getText();
			if(!keepInMindMessage.isEmpty()){
				report.updateTestLog(ViewBillPageObjects.lbl_KeepInMindMsg1.getObjectName(),
						"Keep in Mind Message Contents:"+keepInMindMessage, Status.PASS);

				if(keepInMindMessage.contains("more")){
					WebElement keepInMindMore = getPageElement(ViewBillPageObjects.lnk_KeepInMindMore);
					Robot robot = new Robot();
					robot.mouseMove(0, 0);
					Actions builder = new Actions(driver);
					builder.moveToElement(keepInMindMore).build().perform();

					String keepInMindExpanded="";
					if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_KeepInMindExpandedMsg.getLocatorType().toString(),
							ViewBillPageObjects.lbl_KeepInMindExpandedMsg.getProperty(), ViewBillPageObjects.lbl_KeepInMindExpandedMsg.getObjectName(),false)){						
						keepInMindExpanded = getPageElement(ViewBillPageObjects.lbl_KeepInMindExpandedMsg).getText();
						report.updateTestLog(ViewBillPageObjects.lbl_KeepInMindExpandedMsg.getObjectName(),
								"Keep in Mind Expanded Message Contents:"+keepInMindExpanded, Status.PASS);
						getPageElement(ViewBillPageObjects.lbl_KeepInMind).click();

						robot.mouseMove(0, 0);
					}else{
						report.updateTestLog("Keep in Mind Full", "Full Keep in Mind Message not displayed", Status.WARNING);
					}
				}

			}else{				
				report.updateTestLog(ViewBillPageObjects.lbl_KeepInMindMsg1.getObjectName(),
						ViewBillPageObjects.lbl_KeepInMindMsg1.getObjectName()+ " is blank.", Status.WARNING);				

			}

		}*/




		//Verify Email Address
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_EmailShort.getLocatorType().toString(),
				ViewBillPageObjects.lbl_EmailShort.getProperty(), ViewBillPageObjects.lbl_EmailShort.getObjectName())){
			String email = commonFunction.getData("General_Data", "Username", "Email Address", true);
			String displayedEmail = getPageElement(ViewBillPageObjects.lbl_EmailShort).getText();

			if(displayedEmail.isEmpty()){
				report.updateTestLog("Email Address", "Displayed Email address is blank", Status.WARNING);
			}else{
				//If short email is displayed, verify the short email
				if(displayedEmail.contains("...")){
					String emailToBeDisplayed = email.substring(0,displayedEmail.indexOf("..."))+"...";
					commonFunction.compareText(emailToBeDisplayed.toLowerCase(),getPageElement(ViewBillPageObjects.lbl_EmailShort).getText(),"Short Email Address Verification");
					
					//Move mouse over the email and read data from long email
					try{
						WebElement tooltipIcon = getPageElement(ViewBillPageObjects.lbl_EmailShort);
						Robot robot = new Robot();
						robot.mouseMove(0, 0);
						Actions builder = new Actions(driver);
						builder.moveToElement(tooltipIcon).build().perform();

						if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_EmailLong.getLocatorType().toString(),
								ViewBillPageObjects.lbl_EmailLong.getProperty(), "Full Email Address")){
							commonFunction.compareText(email.toLowerCase(), getPageElement(ViewBillPageObjects.lbl_EmailLong).getText(), "Full Email Address");
							robot.mouseMove(0, 0);
						}else{
							report.updateTestLog("Full Email", "Full Email Not Displayed", Status.WARNING);
						}
					}catch(Exception e){
						report.updateTestLog("Full Email", "ERROR while performing hover on Short Email", Status.WARNING);
					}

				}else
					commonFunction.compareText(email.toLowerCase(), displayedEmail, "Email Address Verification");
			}

		}

		//Contact Us Link		
		commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_ContactUs),
				commonFunction.getData(properties.getProperty("Environment"),"General_Data", "Contact_Us_Link","Contact Us Link",true),
				ViewBillPageObjects.lnk_ContactUs.getObjectName()
		);

		commonFunction.waitForElementVisibility(ViewBillPageObjects.lbl_BalanceLineTooltip.getProperty(),timeout);

		
		/*action.moveToElement(getPageElement(ViewBillPageObjects.lbl_customerName)).perform();
		//Email Update Link		
		commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_EmailUpdate),
				commonFunction.getData(properties.getProperty("Environment"),"General_Data", "EmailUpdate","Email Update URL",true),
				ViewBillPageObjects.lnk_EmailUpdate.getObjectName()
		);*/
		commonFunction.waitForElementVisibility(ViewBillPageObjects.lbl_BalanceLineTooltip.getProperty(),timeout);
		
		//Energy News Link		
		commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_EnergyNews),
				commonFunction.getData(properties.getProperty("Environment"),"General_Data", "EnergyNews","EnergyNews",true),
				ViewBillPageObjects.lnk_EnergyNews.getObjectName()
		);

		commonFunction.waitForElementVisibility(ViewBillPageObjects.lbl_BalanceLineTooltip.getProperty(),timeout);

		

	}

	/***********************************************************
	 * CRAFT - Verify Balance Banner section on View Bill Page
	 * 
	 * 
	 * 
	 * @author 387478
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @modified by 387478 on Oct 29 - Created new CRAFT Keyword
	 * ***********************************************************
	 */
	
	public void verifyBalanceBannerSection() throws IOException, InterruptedException{
		String environment = properties.getProperty("Environment");
		String customerName = commonFunction.getData("Accounts", "CustomerName", "Customer Name",true);
		String dueDate = commonFunction.getData("Accounts", "DueDate","", false);
		String pastDueAmount = commonFunction.getData("Accounts", "PastDueAmount","PastDueAmount",false);		
		String accountBalance = commonFunction.getData("Accounts", "BalanceAccount", "Account Balance", true);

		//Verify validity of displayed customer name
		//String customerName = getPageElement(ViewBillPageObjects.lbl_CustomerName).getText();
		commonFunction.verifyElementPresentEqualsText(getPageElement(ViewBillPageObjects.lbl_CustomerName), "CustomerName", 
				"Hello " + customerName+",");


		float actualAccountBalance=0;
		String accountBalFormatted = commonFunction.formatAmount(accountBalance);


		//Read account balance from common data
		try{
			actualAccountBalance = Float.parseFloat(commonFunction.RemoveSpecialcharactersFromAmount(accountBalance));
			accountBalFormatted = commonFunction.formatAmount(accountBalance);
		}catch(Exception e){
			report.updateTestLog("Account Balance", e.getMessage(), Status.WARNING);

		}

		//Verify Account Balance Label in BlueBand section
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_BalanceLine.getLocatorType().toString(),
				ViewBillPageObjects.lbl_BalanceLine.getProperty(),ViewBillPageObjects.lbl_BalanceLine.getObjectName())){	
			//String balanceLine = getPageElement(ViewBillPageObjects.lbl_BalanceLine).getText(); //get Balance Line from Page
			String accountBalanceMsg=""; // Store the message to be displayed for various Account Balances (zero,credit,positive)


			//Zero Balance
			if(actualAccountBalance == 0)
				accountBalanceMsg = "Your account has a zero balance.";
			//Credit Balance
			else if(actualAccountBalance < 0)
				accountBalanceMsg = "Your account has a credit balance of $" + accountBalFormatted +".";

			//Past Due - Verify greeting line is displayed
			else if(actualAccountBalance != 0 && (!pastDueAmount.isEmpty())){
				//Verify greeting line is displayed
				if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lnk_GreetingLine.getLocatorType().toString(),
						ViewBillPageObjects.lnk_GreetingLine.getProperty(), ViewBillPageObjects.lnk_GreetingLine.getObjectName())){
					String expectedgreetingLine = "Your account is past due - please pay $"+ commonFunction.formatAmount(pastDueAmount) +" immediately.";
					commonFunction.verifyElementPresentEqualsText(getPageElement(ViewBillPageObjects.lnk_GreetingLine), "Past Due Greeting Line",
							expectedgreetingLine);	

				}
				accountBalanceMsg = "Your total balance is $" + accountBalFormatted + ".";			
			}

			//No Previous Past Due
			else if(actualAccountBalance > 0 && (!dueDate.isEmpty())){
				if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lnk_GreetingLine.getLocatorType().toString(),
						ViewBillPageObjects.lnk_GreetingLine.getProperty(), ViewBillPageObjects.lnk_GreetingLine.getObjectName())){
					String expectedgreetingLine = "Ready to pay your FPL bill? It's a snap.";
					commonFunction.verifyElementPresentEqualsText(getPageElement(ViewBillPageObjects.lnk_GreetingLine), "Greeting Line",
							expectedgreetingLine);	

					accountBalanceMsg = "Your current balance is $" + accountBalFormatted +" due by " + dueDate + ".";
				}
			}

			//No previous past due date error
			else if(actualAccountBalance != 0)
				accountBalanceMsg = "Your current balance is $" + accountBalFormatted + ".";

			//Verify if Balance Line is equal to accountBalanceMsg
			commonFunction.verifyElementPresentEqualsText(getPageElement(ViewBillPageObjects.lbl_BalanceLine), "BalanceLine",accountBalanceMsg);

		}
		//Verify PayBill button is displayed in Blue Band section if AccountBalance is greater than zero
		if(actualAccountBalance>0){
			if(!commonFunction.isElementPresent(ViewBillPageObjects.lnk_PayBillblueBand.getLocatorType().toString(),
					ViewBillPageObjects.lnk_PayBillblueBand.getProperty(),"",false))
				report.updateTestLog("Pay Bill Button", "Pay Bill Button is not displayed in blueband section when account balance is greater than zero", Status.FAIL);	
			else{
				report.updateTestLog("Pay Bill Button", "Pay Bill Button is displayed in blueband section when account balance is greater than zero", Status.PASS);
				//Verify PayBill Link
				if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_PayBillblueBand.getLocatorType().toString(), 
						ViewBillPageObjects.lnk_PayBillblueBand.getProperty(), ViewBillPageObjects.lnk_PayBillblueBand.getObjectName(),false)){

					//Verify Image Source
					commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.lnk_PayBillblueBand),
							commonFunction.getData(properties.getProperty("Environment"),"General_Data", "PayBillImage","PayBill Image URL",true),
							ViewBillPageObjects.lnk_PayBillblueBand.getObjectName());

					//Click and Verify URL - Not Working - Instead checking href attribute
					/*commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_PayBillblueBand1),
								commonFunction.getData(properties.getProperty("Environment"),"General_Data", "POL_Link","PayBill URL",true),
								"Pay Bill Link"
								);*/

					commonFunction.compareText(commonFunction.getData(environment,"General_Data", "POL_Link","PayBill URL",true), 
							getPageElement(ViewBillPageObjects.lnk_PayBillblueBand1).getAttribute("href"),
							"Pay Bill URL verification");
				}
			}
		}else{		
			//Verify PayBill button is not displayed in BlueBand section if AccountBalance is less than or equal to zero
			commonFunction.verifyIsElementNotPresent(ViewBillPageObjects.lnk_PayBillblueBand.getProperty(),ViewBillPageObjects.lnk_PayBillblueBand.getLocatorType().toString(),
					ViewBillPageObjects.lnk_PayBillblueBand.getObjectName());
		}
	}

	/***********************************************************
	 * CRAFT - Check My Bill section
	 * 
	 * This function verifies the My Bill section
	 * 
	 * @author 387478
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws bsh.ParseException 
	 * @modified by 387478 on Oct 29 - Created new CRAFT Keyword
	 * ***********************************************************
	 */
	public void checkMyBillSection() throws InterruptedException, IOException, ParseException, bsh.ParseException{
		int timeout = 10;

		//Verify Last Bill Date
		String lastBillDate = commonFunction.getData("Accounts", "LastBillDate", "Last Bill Date",true);
		lastBillDate = commonFunction.convertDatetoSingleDigit(lastBillDate);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_LastBillDate.getLocatorType().toString(),
				ViewBillPageObjects.lbl_LastBillDate.getProperty(), ViewBillPageObjects.lbl_LastBillDate.getObjectName(), true)){
			commonFunction.compareText("as of "+lastBillDate, 
					getPageElement(ViewBillPageObjects.lbl_LastBillDate).getText(), ViewBillPageObjects.lbl_LastBillDate.getObjectName());
		}

		//Verify Select Another Account Link		
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_SelectAnotherAccountMyBill.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_SelectAnotherAccountMyBill.getProperty(), ViewBillPageObjects.lnk_SelectAnotherAccountMyBill.getObjectName(),true))				
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_SelectAnotherAccountMyBill),
					commonFunction.getData(properties.getProperty("Environment"),"General_Data", "SelectAnotherAccount","Select Another Account URL",true),
					ViewBillPageObjects.lnk_SelectAnotherAccountMyBill.getObjectName()
			);	

		commonFunction.waitForElementVisibility(ViewBillPageObjects.lbl_BalanceLineTooltip.getProperty(),timeout);
		//Verify Account Summary Link		
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnl_ViewAccountSummary.getLocatorType().toString(), 
				ViewBillPageObjects.lnl_ViewAccountSummary.getProperty(), ViewBillPageObjects.lnl_ViewAccountSummary.getObjectName(),true))				
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnl_ViewAccountSummary),
					commonFunction.getData(properties.getProperty("Environment"),"General_Data", "AccountSummary","AccountSummary URL",true),
					ViewBillPageObjects.lnl_ViewAccountSummary.getObjectName()
			);

		commonFunction.waitForElementVisibility(ViewBillPageObjects.lbl_BalanceLineTooltip.getProperty(),timeout);
		//Verify View Bill Insert Link		
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_ViewBillInsert.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_ViewBillInsert.getProperty(), ViewBillPageObjects.lnk_ViewBillInsert.getObjectName(),true))				
			commonFunction.verifyLinkInWebPage(getPageElement(ViewBillPageObjects.lnk_ViewBillInsert),ViewBillPageObjects.lnk_ViewBillInsert.getObjectName(),
					commonFunction.getData(properties.getProperty("Environment"),"General_Data", "ViewBillInsert","ViewBillInsert URL",true));					

		commonFunction.waitForElementVisibility(ViewBillPageObjects.lbl_BalanceLineTooltip.getProperty(),timeout);
		
		//Verify Customer Name
		String customerName = commonFunction.getData("Accounts", "CustomerName", "Customer Name",true);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_customerName.getLocatorType().toString(),
				ViewBillPageObjects.lbl_customerName.getProperty(), ViewBillPageObjects.lbl_customerName.getObjectName(), true)){
			commonFunction.compareText(customerName.toUpperCase(), 
					getPageElement(ViewBillPageObjects.lbl_customerName).getText(), ViewBillPageObjects.lbl_customerName.getObjectName());
		}

		//Verify Service Addess 
		String serviceAddress = commonFunction.getData("Accounts", "ServiceAddress", "Service Address",true);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_serviceAddress.getLocatorType().toString(),
				ViewBillPageObjects.lbl_serviceAddress.getProperty(), ViewBillPageObjects.lbl_serviceAddress.getObjectName(), true)){
			commonFunction.compareText(serviceAddress, 
					getPageElement(ViewBillPageObjects.lbl_serviceAddress).getText(), ViewBillPageObjects.lbl_serviceAddress.getObjectName());
		}


		//Verify Service Date Dropdown
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.drpdwn_ServiceDate.getLocatorType().toString(),
				ViewBillPageObjects.drpdwn_ServiceDate.getProperty(), ViewBillPageObjects.drpdwn_ServiceDate.getObjectName())){
			Select dropdown = new Select(getPageElement(ViewBillPageObjects.drpdwn_ServiceDate));
			int billCount = dropdown.getOptions().size();
			String defaultSelection = dropdown.getFirstSelectedOption().getText();
			dropdown = new Select(getPageElement(ViewBillPageObjects.drpdwn_ServiceDate));
			String defaultSelectionValue = dropdown.getFirstSelectedOption().getAttribute("value");
			defaultSelectionValue = commonFunction.convertDateRemoveSlashes(defaultSelectionValue);

			//getPageElement(ViewBillPageObjects.drpdwn_ServiceDate).click();
			report.updateTestLog("'Service Date' dropdown", billCount +" previous bills displayed", Status.PASS);
			
			String billDate = commonFunction.getData("Accounts", "PreviousBillDate", "Previous Bill Date", false);
			if(!billDate.isEmpty()){
				if(billCount>1){
					billDate = commonFunction.convertDatetoSingleDigit(billDate);

					SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");
					Date date = formatter.parse(billDate);
					String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
					try{
						//Select Date
						dropdown.selectByValue(formattedDate);
						Thread.sleep(5000);
						commonFunction.isAlertNotPresent("Bill Not Displayed");
						report.updateTestLog("Select Bill", "Bill dated "+ billDate + " selected successfully.", Status.PASS);
						if(commonFunction.isAlertNotPresent("Bill Not Displayed")){
							commonFunction.compareText("Statement balance as of " + billDate, getPageElement(ViewBillPageObjects.lbl_StatementBalance).getText(), "Statement Balance Updation");
						}							
						dropdown = new Select(getPageElement(ViewBillPageObjects.drpdwn_ServiceDate));
						dropdown.selectByVisibleText(defaultSelection);
						Thread.sleep(5000);
						commonFunction.isAlertNotPresent("Bill Not Displayed");
						report.updateTestLog("Select Bill", "Bill '"+ defaultSelection + "' selected again.", Status.PASS);
						if(commonFunction.isAlertNotPresent("Bill Not Displayed"))
							commonFunction.compareText("Statement balance as of " + defaultSelectionValue, getPageElement(ViewBillPageObjects.lbl_StatementBalance).getText(), "Statement Balance Updation");

						//commonFunction.compareText("Statement balance as of " + billDate, getPageElement(ViewBillPageObjects.lbl_StatementBalance).getText(), "Statement Balance Updation");
					}catch(UnhandledAlertException uae){
						report.updateTestLog("Unexpected Alert", "Alert '"+ uae.getAlertText() + "' displayed.", Status.FAIL);
					}catch(Exception e){
						Thread.sleep(8000);
						commonFunction.isAlertNotPresent("Bill Not Displayed");
						System.out.println(e.getMessage());
						report.updateTestLog("Select Bill from Service Date Dropdown", "Error selecting bill dated '"+ formattedDate +"' from Service Date dropdown. Bill not displayed.", Status.FAIL);
					}
				}else{
					report.updateTestLog("Select Bill from Service Date Dropdown", "Bill dated " + billDate + " not displayed.", Status.FAIL);
					
				}
			}
		}

		commonFunction.isAlertNotPresent("Bill Not Displayed");


		//Verify ABP message
		String isAbpAccount = commonFunction.getData("Accounts", "ABP","ABP",true);

		if(!isAbpAccount.isEmpty()){
			if(isAbpAccount.toLowerCase().equals("yes")){
				String abpDate = commonFunction.getData("Accounts", "ABPWithdrawalDate","ABP Date",true);				

				String abpMessage = "DO NOT PAY. Thank you for using FPL Automatic Bill Pay®. The amount due on your account will be drafted automatically on or after "+ commonFunction.convertDatetoSingleDigit(abpDate)+".";
				if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_ABPMessage.getLocatorType().toString(),
						ViewBillPageObjects.lbl_ABPMessage.getProperty(),ViewBillPageObjects.lbl_ABPMessage.getObjectName())){
					commonFunction.compareText(abpMessage, getPageElement(ViewBillPageObjects.lbl_ABPMessage).getText(), 
							ViewBillPageObjects.lbl_ABPMessage.getObjectName());
				}

			}else{
				if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_ABPMessage.getLocatorType().toString(), 
						ViewBillPageObjects.lbl_ABPMessage.getProperty(), ViewBillPageObjects.lbl_ABPMessage.getObjectName(),false)){
					if(getPageElement(ViewBillPageObjects.lbl_ABPMessage).getText().contains("Automatic Bill Pay")){
						report.updateTestLog(ViewBillPageObjects.lbl_ABPMessage.getObjectName(),
								ViewBillPageObjects.lbl_ABPMessage.getObjectName() + " is present, which is not expected", Status.FAIL);
					}else
						report.updateTestLog(ViewBillPageObjects.lbl_ABPMessage.getObjectName(),
								ViewBillPageObjects.lbl_ABPMessage.getObjectName() + " is not present, which is as expected", Status.PASS);
					
				}else{
					report.updateTestLog(ViewBillPageObjects.lbl_ABPMessage.getObjectName(),
							ViewBillPageObjects.lbl_ABPMessage.getObjectName() + " is not present, which is as expected", Status.PASS);
				}				
										

				commonFunction.waitForElementVisibility(ViewBillPageObjects.lbl_BalanceLineTooltip.getProperty(),timeout);
				}
		}
		
		
		//Verify last bill amount
		String lastBillAmount = commonFunction.getData("Accounts", "StatementBalanceLast","Last Bill Amount",true);
		lastBillAmount = commonFunction.formatAmountWithDollar(lastBillAmount);
		if(!lastBillAmount.trim().isEmpty()){
			//Previous Bill Amount Header
			commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_previousBalance.getLocatorType().toString(),
					ViewBillPageObjects.lbl_previousBalance.getProperty(), ViewBillPageObjects.lbl_previousBalance.getObjectName());
			
			if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_lastBillAmount.getLocatorType().toString(),
					ViewBillPageObjects.lbl_lastBillAmount.getProperty(), ViewBillPageObjects.lbl_lastBillAmount.getObjectName(), true)){
				commonFunction.compareText(lastBillAmount, getPageElement(ViewBillPageObjects.lbl_lastBillAmount).getText(), ViewBillPageObjects.lbl_lastBillAmount.getObjectName());
			}	
			
		}
		
		//Verify Statement Balance Line 
		String statementBalanceLine = "Statement balance as of " + lastBillDate;
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_StatementBalance.getLocatorType().toString(),
				ViewBillPageObjects.lbl_StatementBalance.getProperty(), ViewBillPageObjects.lbl_StatementBalance.getObjectName(), true)){
			commonFunction.compareText(statementBalanceLine, getPageElement(ViewBillPageObjects.lbl_StatementBalance).getText(), ViewBillPageObjects.lbl_StatementBalance.getObjectName());
		}
		
		//Verify Statement Balance Amount 
		String statementBalanceAmount = commonFunction.getData("Accounts", "BalanceAccount", "Total Balance", true);
		statementBalanceAmount = commonFunction.formatAmountWithDollar(statementBalanceAmount);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_StatementBalanceAmount.getLocatorType().toString(),
				ViewBillPageObjects.lbl_StatementBalanceAmount.getProperty(), ViewBillPageObjects.lbl_StatementBalanceAmount.getObjectName(), true)){
			commonFunction.compareText(statementBalanceAmount, getPageElement(ViewBillPageObjects.lbl_StatementBalanceAmount).getText(), ViewBillPageObjects.lbl_StatementBalanceAmount.getObjectName());
		}

		

		//Pay Bill Button
		String accountBalance = commonFunction.getData("Accounts", "BalanceAccount", "Account Balance", false);
		Float actualAccountBalance = (float) 0;
		try{
			actualAccountBalance = Float.parseFloat(commonFunction.RemoveSpecialcharactersFromAmount(accountBalance));
		}catch(Exception e){
			actualAccountBalance = (float) 0;
		}

		if(actualAccountBalance > 0){
			//Verify PayBill button is displayed
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lnk_PayBill.getLocatorType().toString(),
					ViewBillPageObjects.lnk_PayBill.getProperty(),ViewBillPageObjects.lnk_PayBill.getObjectName())){

				//Verify Image Source
				commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.lnk_PayBill),
						commonFunction.getData(properties.getProperty("Environment"),"General_Data", "PayBillImage","PayBill Image URL",true),
						ViewBillPageObjects.lnk_PayBill.getObjectName());

				commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_PayBill),
						commonFunction.getData(properties.getProperty("Environment"),"General_Data", "POL_Link","PayBill URL",true),
						ViewBillPageObjects.lnk_PayBill.getObjectName()
				);
			}
		}
		else{
			commonFunction.verifyIsElementNotPresent(ViewBillPageObjects.lnk_PayBill.getProperty(),ViewBillPageObjects.lnk_PayBill.getLocatorType().toString(),
					ViewBillPageObjects.lnk_PayBill.getObjectName());
			
			if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_CreditAmount.getLocatorType().toString(),
					ViewBillPageObjects.lbl_CreditAmount.getProperty(), ViewBillPageObjects.lbl_CreditAmount.getObjectName(), true)){
				commonFunction.compareText("Credit Amount - DO NOT PAY", getPageElement(ViewBillPageObjects.lbl_CreditAmount).getText(), ViewBillPageObjects.lbl_CreditAmount.getObjectName());
			}
			
			
		}

		commonFunction.waitForElementVisibility(ViewBillPageObjects.lbl_BalanceLineTooltip.getProperty(),timeout);

	}
	
	
	/***********************************************************
	 * CRAFT - Check New Charges scetion
	 * 
	 * This function verifies the New Charges section for Residential and Commercial users
	 * 
	 * @author 387478
	 * @throws IOException 
	 * @throws ParseException 
	 * @throws bsh.ParseException 
	 * @modified by 387478 on Nov 26 - Created new CRAFT Keyword
	 * ***********************************************************
	 */
	public void verifyNewChargesSection(){
		String accountType = commonFunction.getData("Accounts", "AccountType", "AccountType",true);
		String currentBal = commonFunction.getData("Accounts", "StatementBalanceCurrent", "Statement Balance Current",false);
		double currentBalInt = commonFunction.convertStringtoDouble(currentBal);
		
		//Verify New Charges Header
		String dueDate = commonFunction.getData("Accounts", "DueDate", "Due Date Date",true);		
		dueDate = commonFunction.convertDatetoSingleDigit(dueDate);
		String newChargesHeader;
		if(currentBalInt <= 0)
			newChargesHeader = "New Charges";
		else
			newChargesHeader = "New Charges due by " + dueDate;
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_NewChargesHeader.getLocatorType().toString(),
				ViewBillPageObjects.lbl_NewChargesHeader.getProperty(), ViewBillPageObjects.lbl_NewChargesHeader.getObjectName(), true)){
			commonFunction.compareText(newChargesHeader, 
					getPageElement(ViewBillPageObjects.lbl_NewChargesHeader).getText(), ViewBillPageObjects.lbl_NewChargesHeader.getObjectName());
		}
		
		//Verify New Charges Amount
		String newCharges = commonFunction.getData("Accounts", "NewCharges", "New Charges",true);		
		newCharges = commonFunction.formatAmount(newCharges);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_NewChargesAmount.getLocatorType().toString(),
				ViewBillPageObjects.lbl_NewChargesAmount.getProperty(), ViewBillPageObjects.lbl_NewChargesAmount.getObjectName(), true)){
			commonFunction.compareText("$" + newCharges, 
					getPageElement(ViewBillPageObjects.lbl_NewChargesAmount).getText(), ViewBillPageObjects.lbl_NewChargesAmount.getObjectName());
		}

		//Open New Charges Section
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lbl_NewChargesHeader), 
				ViewBillPageObjects.lbl_NewChargesHeader.getObjectName());
		
		//Verify Rate Header
		String rate ="";
		if( (!accountType.isEmpty())&& (accountType.toLowerCase().equals("residential")))
			rate = "Rate: RS-1 RESIDENTIAL SERVICE";
		else
			rate = "Rate: GSD-1 GENERAL SERVICE DEMAND";
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_Rate.getLocatorType().toString(),
				ViewBillPageObjects.lbl_Rate.getProperty(), ViewBillPageObjects.lbl_Rate.getObjectName(), true)){
			commonFunction.compareText(rate , 
					getPageElement(ViewBillPageObjects.lbl_Rate).getText(), ViewBillPageObjects.lbl_Rate.getObjectName());
		}
		
		//Verify Total New Charges header
		String totalNewCharges = "Total new charges";
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_TotalNewCharges.getLocatorType().toString(),
				ViewBillPageObjects.lbl_TotalNewCharges.getProperty(), ViewBillPageObjects.lbl_TotalNewCharges.getObjectName(), true)){
			commonFunction.compareText(totalNewCharges , 
					getPageElement(ViewBillPageObjects.lbl_TotalNewCharges).getText(), ViewBillPageObjects.lbl_TotalNewCharges.getObjectName());
		}
		
		//Verify Total New Charges Amount
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_TotalNewChargesAmount.getLocatorType().toString(),
				ViewBillPageObjects.lbl_TotalNewChargesAmount.getProperty(), ViewBillPageObjects.lbl_TotalNewChargesAmount.getObjectName(), true)){
			commonFunction.compareText(newCharges , 
					getPageElement(ViewBillPageObjects.lbl_TotalNewChargesAmount).getText(), ViewBillPageObjects.lbl_TotalNewChargesAmount.getObjectName());
		}
		
		//Electricity Section
		String customerCharge = commonFunction.getData("Accounts", "CustomerCharge", "Customer Charge",false);
		String nonFuelCharge = commonFunction.getData("Accounts", "NonFuelCharge", "NonFuel Charge",false);
		String fuelCharge = commonFunction.getData("Accounts", "FuelCharge", "Fuel Charge",false);
		
		//ElectricityHeader
		String electricityHeader = "Electricity";
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_ElectricityHeader.getLocatorType().toString(),
				ViewBillPageObjects.lbl_ElectricityHeader.getProperty(), ViewBillPageObjects.lbl_ElectricityHeader.getObjectName(), true)){
			commonFunction.compareText(electricityHeader , 
					getPageElement(ViewBillPageObjects.lbl_ElectricityHeader).getText(), ViewBillPageObjects.lbl_ElectricityHeader.getObjectName());
			
			//Electircity Amount
			//Customer Charge Amount
			Double electricityChargeAmount = commonFunction.convertStringtoDouble(customerCharge) + commonFunction.convertStringtoDouble(nonFuelCharge) +
										commonFunction.convertStringtoDouble(fuelCharge);
			String electricityCharge = commonFunction.formatAmount(Double.toString(electricityChargeAmount));
			commonFunction.compareText(electricityCharge , 
					getPageElement(ViewBillPageObjects.lbl_ElectricityCharges).getText(), ViewBillPageObjects.lbl_ElectricityCharges.getObjectName());
		}
		
		//CustomerChargeLabel
		String customerChargeLabel = "Customer charge";
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_CustomerChargeHeader.getLocatorType().toString(),
				ViewBillPageObjects.lbl_CustomerChargeHeader.getProperty(), ViewBillPageObjects.lbl_CustomerChargeHeader.getObjectName(), true)){
			commonFunction.compareText(customerChargeLabel , 
					getPageElement(ViewBillPageObjects.lbl_CustomerChargeHeader).getText(), ViewBillPageObjects.lbl_CustomerChargeHeader.getObjectName());
			
			//Customer Charge Amount
			customerCharge = commonFunction.formatAmount(customerCharge);
			commonFunction.compareText(customerCharge , 
					getPageElement(ViewBillPageObjects.lbl_CustomerCharge).getText(), ViewBillPageObjects.lbl_CustomerCharge.getObjectName());
		
		
		}
		
		//NonFuelLabel
		String nonFuelLabel = "Non-fuel";
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_NonFuelHeader.getLocatorType().toString(),
				ViewBillPageObjects.lbl_NonFuelHeader.getProperty(), ViewBillPageObjects.lbl_NonFuelHeader.getObjectName(), true)){
			commonFunction.compareText(nonFuelLabel , 
					getPageElement(ViewBillPageObjects.lbl_NonFuelHeader).getText(), ViewBillPageObjects.lbl_NonFuelHeader.getObjectName());
			
			//Non Fuel Amount
			nonFuelCharge = commonFunction.formatAmount(nonFuelCharge);
			commonFunction.compareText(nonFuelCharge , 
					getPageElement(ViewBillPageObjects.lbl_NonFuelCharge).getText(), ViewBillPageObjects.lbl_NonFuelCharge.getObjectName());
		}
		
		//FuelLabel
		String fuelLabel = "Fuel";
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_FuelHeader.getLocatorType().toString(),
				ViewBillPageObjects.lbl_FuelHeader.getProperty(), ViewBillPageObjects.lbl_FuelHeader.getObjectName(), true)){
			commonFunction.compareText(fuelLabel , 
					getPageElement(ViewBillPageObjects.lbl_FuelHeader).getText(), ViewBillPageObjects.lbl_FuelHeader.getObjectName());
			
			//Fuel Charge
			fuelCharge = commonFunction.formatAmount(fuelCharge);
			commonFunction.compareText(fuelCharge , 
					getPageElement(ViewBillPageObjects.lbl_FuelCharge).getText(), ViewBillPageObjects.lbl_FuelCharge.getObjectName());
		}
		
		
		//Verify Late Payment Charges.
		String lpc = commonFunction.getData("Accounts", "LatePaymentCharges", "Late Payment Charges",false);
		double lpcint = commonFunction.convertStringtoDouble(lpc);

		if(lpcint > 0){
			String lpcMessage = "Late payment charge";
			//Verify LPC Header
			if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_LPC.getLocatorType().toString(),
					ViewBillPageObjects.lbl_LPC.getProperty(), ViewBillPageObjects.lbl_LPC.getObjectName(), true)){
				commonFunction.compareText(lpcMessage , 
						getPageElement(ViewBillPageObjects.lbl_LPC).getText(), ViewBillPageObjects.lbl_LPC.getObjectName());
			}			
			//Verify LPC Amount
			String lpcAmount = commonFunction.formatAmount(Double.toString(lpcint));
			if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_LPCAmount.getLocatorType().toString(),
					ViewBillPageObjects.lbl_LPCAmount.getProperty(), ViewBillPageObjects.lbl_LPCAmount.getObjectName(), true)){
				commonFunction.compareText(lpcAmount, 
						getPageElement(ViewBillPageObjects.lbl_LPCAmount).getText(), ViewBillPageObjects.lbl_LPCAmount.getObjectName());
			}			
			
		}else{
			commonFunction.verifyIsElementNotPresent(ViewBillPageObjects.lbl_LPC.getProperty(), ViewBillPageObjects.lbl_LPC.getLocatorType().toString().toString(),
					ViewBillPageObjects.lbl_LPC.getObjectName());
			
			commonFunction.verifyIsElementNotPresent(ViewBillPageObjects.lbl_LPCAmount.getProperty(), ViewBillPageObjects.lbl_LPCAmount.getLocatorType().toString().toString(),
					ViewBillPageObjects.lbl_LPCAmount.getObjectName());
		}
		
		
		
		
		//Close New Charges section
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lbl_NewChargesHeader), 
				ViewBillPageObjects.lbl_NewChargesHeader.getObjectName());
	}
	
	/*************************************************************
	 * CRAFT Download and Verify Bill
	 * This function downloads View Bill and verify the bill
	 * on View Bill Page. Does not work on IE
	 * 
	 * 
	 * @author 387478
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @modified by 387478 on Nov 18
	 * 
	 * *************************************************************
	 */	
	public void downloadAndVerifyBill() throws InterruptedException{		
		action.sendKeys(Keys.HOME);
		try{
			//Click Download Bill Link
			if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_DownloadBill.getLocatorType().toString(),
					ViewBillPageObjects.lnk_DownloadBill.getProperty(), ViewBillPageObjects.lnk_DownloadBill.getObjectName(), true)){

				//Verify Download Bill Image
				commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.lnk_DownloadBill),
						commonFunction.getData(properties.getProperty("Environment"),"General_Data", "DownloadBillImageURL","DownloadBill Image URL",true),
						ViewBillPageObjects.lnk_DownloadBill.getObjectName());

				downloadandVerifyBill(ViewBillPageObjects.lnk_DownloadBill, ViewBillPageObjects.lnk_DownloadBill.getObjectName());
			}
		}catch(UnhandledAlertException uae){
			report.updateTestLog("Unexpected Alert", "Alert '"+ uae.getAlertText() + "' displayed.", Status.FAIL);
			Alert alert = driver.switchTo().alert();
			alert.accept();

		}
	} 
	
	public void checkComparisonsSection() throws bsh.ParseException{
		String electricAmountCurrent = commonFunction.getData("Accounts", "ElectricAmountCurrent", "Current Electric bill amount", true);
		String electricAmountLast = commonFunction.getData("Accounts", "ElectricAmountLast", "Last Month Electric bill amount", true);
		String currentReadDate = commonFunction.getData("Accounts", "ServiceDate2", "Current Read Date", true);
		String lastReadDate = commonFunction.getData("Accounts", "ServiceDate1", "Previous Month Read Date", true);
		String lastReadDate1 = commonFunction.getData("Accounts", "ServiceDate3", "Previous Month Read Date", true);
		String monthComparisonHeader;
		
		//Create Month comparison header
		boolean isDiffPositive = false;
		double electricAmountCurrentint = commonFunction.convertStringtoDouble(electricAmountCurrent);
		double electricAmountLastint = commonFunction.convertStringtoDouble(electricAmountLast);
		double diff = electricAmountCurrentint - electricAmountLastint;
		
		String diffString = commonFunction.formatAmount(Double.toString(diff));
		
		if(diff > 0)
			isDiffPositive = true;
		
		if(!isDiffPositive)
			monthComparisonHeader="Compared to last month, you spent $"+ diffString +" less";
		else
			monthComparisonHeader="Compared to last month, you spent $"+ diffString +" more";
		
		
		//Verify Meter Comparison label is displayed
		commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_MeterComparison.getLocatorType().toString(),
				ViewBillPageObjects.lbl_MeterComparison.getProperty(),ViewBillPageObjects.lbl_MeterComparison.getObjectName());

		//Verify Meter Number
		String meterNumber = commonFunction.getData("Accounts", "MeterNumber", "Meter Number", true);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_meterNumber.getLocatorType().toString(),
				ViewBillPageObjects.lbl_meterNumber.getProperty(), ViewBillPageObjects.lbl_meterNumber.getObjectName(), true)){
			commonFunction.compareText("Meter reading - Meter "+ meterNumber, getPageElement(ViewBillPageObjects.lbl_meterNumber).getText(), ViewBillPageObjects.lbl_meterNumber.getObjectName());
		}

		//Verify Next Meter Read Date
		String nextReadDate = commonFunction.getData("Accounts", "NextReadDate", "Meter Number", true);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_NextReadDate.getLocatorType().toString(),
				ViewBillPageObjects.lbl_NextReadDate.getProperty(), ViewBillPageObjects.lbl_NextReadDate.getObjectName(), true)){
			commonFunction.compareText(nextReadDate, getPageElement(ViewBillPageObjects.lbl_NextReadDate).getText(), ViewBillPageObjects.lbl_NextReadDate.getObjectName());
		}
		
		//Month Compariosn Header
		if(diff!=0)
			if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_MonthComparisonHeader.getLocatorType().toString(),
					ViewBillPageObjects.lbl_MonthComparisonHeader.getProperty(), ViewBillPageObjects.lbl_MonthComparisonHeader.getObjectName(), true)){
				commonFunction.compareText(monthComparisonHeader, getPageElement(ViewBillPageObjects.lbl_MonthComparisonHeader).getText(), ViewBillPageObjects.lbl_MonthComparisonHeader.getObjectName());
			}
		
		//Current Read Date
		currentReadDate = commonFunction.convertDatetoSingleDigit(currentReadDate);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_ReadingDateToCurrentMonth.getLocatorType().toString(),
				ViewBillPageObjects.lbl_ReadingDateToCurrentMonth.getProperty(), ViewBillPageObjects.lbl_ReadingDateToCurrentMonth.getObjectName(), true)){
			commonFunction.compareText(commonFunction.getMonthFromDate(currentReadDate), getPageElement(ViewBillPageObjects.lbl_ReadingDateToCurrentMonth).getText(), ViewBillPageObjects.lbl_ReadingDateToCurrentMonth.getObjectName());
		}
		
		
		//Number of days in current bill
		long numberOfDaysInCurrentBill = commonFunction.findDateDifference(lastReadDate, currentReadDate);

		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_ReadingDateToCurrentMonthDays.getLocatorType().toString(),
				ViewBillPageObjects.lbl_ReadingDateToCurrentMonthDays.getProperty(), ViewBillPageObjects.lbl_ReadingDateToCurrentMonthDays.getObjectName(), true)){
			if(numberOfDaysInCurrentBill<2)
				commonFunction.compareText(numberOfDaysInCurrentBill +" day", getPageElement(ViewBillPageObjects.lbl_ReadingDateToCurrentMonthDays).getText(), ViewBillPageObjects.lbl_ReadingDateToCurrentMonthDays.getObjectName());
			else
				commonFunction.compareText(numberOfDaysInCurrentBill +" days", getPageElement(ViewBillPageObjects.lbl_ReadingDateToCurrentMonthDays).getText(), ViewBillPageObjects.lbl_ReadingDateToCurrentMonthDays.getObjectName());
		}
		
		//Current Month Kwh used
		String Kwh1 = commonFunction.getData("Accounts", "CurrentReading", "Current meter reading", true);
		String Kwh2 = commonFunction.getData("Accounts", "PreviousReading", "Previous Month meter reading", true);
		long kwhUsed = Math.abs(commonFunction.convertStringtoInteger(Kwh1) - commonFunction.convertStringtoInteger(Kwh2));
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_CurrentMonthCons.getLocatorType().toString(),
				ViewBillPageObjects.lbl_CurrentMonthCons.getProperty(), ViewBillPageObjects.lbl_CurrentMonthCons.getObjectName(), true)){
			commonFunction.compareText(kwhUsed + " kWh", getPageElement(ViewBillPageObjects.lbl_CurrentMonthCons).getText(), ViewBillPageObjects.lbl_CurrentMonthCons.getObjectName());
		}
		
		//Current Month AvgCons
		int avgCons = 0;
		try{
			avgCons = Math.round((float)kwhUsed/numberOfDaysInCurrentBill);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_CurrentMonthAvgUsage.getLocatorType().toString(),
				ViewBillPageObjects.lbl_CurrentMonthAvgUsage.getProperty(), ViewBillPageObjects.lbl_CurrentMonthAvgUsage.getObjectName(), true)){
			commonFunction.compareText(avgCons + " kWh/day", getPageElement(ViewBillPageObjects.lbl_CurrentMonthAvgUsage).getText(), ViewBillPageObjects.lbl_CurrentMonthAvgUsage.getObjectName());
		}
		
		//Current Month New Charges
		String newCharges = commonFunction.formatAmount(electricAmountCurrent);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_CurrentMonthCharges.getLocatorType().toString(),
				ViewBillPageObjects.lbl_CurrentMonthCharges.getProperty(), ViewBillPageObjects.lbl_CurrentMonthCharges.getObjectName(), true)){
			commonFunction.compareText("$"+newCharges, getPageElement(ViewBillPageObjects.lbl_CurrentMonthCharges).getText(), ViewBillPageObjects.lbl_CurrentMonthCharges.getObjectName());
		}
		
		
		//Last Read Date
		lastReadDate = commonFunction.convertDatetoSingleDigit(lastReadDate);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_ReadingDateToLastMonth.getLocatorType().toString(),
				ViewBillPageObjects.lbl_ReadingDateToLastMonth.getProperty(), ViewBillPageObjects.lbl_ReadingDateToLastMonth.getObjectName(), true)){
			commonFunction.compareText(commonFunction.getMonthFromDate(lastReadDate), getPageElement(ViewBillPageObjects.lbl_ReadingDateToLastMonth).getText(), ViewBillPageObjects.lbl_ReadingDateToLastMonth.getObjectName());
		}
		
		//Number of days in Last Bill
		long numberOfDaysInLastBill = commonFunction.findDateDifference(lastReadDate, lastReadDate1);
		//System.out.println("Last Read Date:" + lastReadDate);
		//System.out.println("last Read Date1:" + lastReadDate1);
		
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_ReadingDateToLastMonthDays.getLocatorType().toString(),
				ViewBillPageObjects.lbl_ReadingDateToLastMonthDays.getProperty(), ViewBillPageObjects.lbl_ReadingDateToLastMonthDays.getObjectName(), true)){
			if(numberOfDaysInCurrentBill<2)
				commonFunction.compareText(numberOfDaysInLastBill +" day", getPageElement(ViewBillPageObjects.lbl_ReadingDateToLastMonthDays).getText(), ViewBillPageObjects.lbl_ReadingDateToLastMonthDays.getObjectName());
			else
				commonFunction.compareText(numberOfDaysInLastBill +" days", getPageElement(ViewBillPageObjects.lbl_ReadingDateToLastMonthDays).getText(), ViewBillPageObjects.lbl_ReadingDateToLastMonthDays.getObjectName());
		}
		
		//Last Month Kwh used
		String Kwh3 = commonFunction.getData("Accounts", "PreviousReading1", "Current meter reading", true);
		kwhUsed = Math.abs(commonFunction.convertStringtoInteger(Kwh2) - commonFunction.convertStringtoInteger(Kwh3));
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_LastMonthCons.getLocatorType().toString(),
				ViewBillPageObjects.lbl_LastMonthCons.getProperty(), ViewBillPageObjects.lbl_LastMonthCons.getObjectName(), true)){
			commonFunction.compareText(kwhUsed + " kWh", getPageElement(ViewBillPageObjects.lbl_LastMonthCons).getText(), ViewBillPageObjects.lbl_LastMonthCons.getObjectName());
		}
		
		//Last Month AvgCons
		avgCons = 0;
		try{
			avgCons = Math.round((float)kwhUsed/numberOfDaysInCurrentBill);
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_LastMonthAvgUsage.getLocatorType().toString(),
				ViewBillPageObjects.lbl_LastMonthAvgUsage.getProperty(), ViewBillPageObjects.lbl_LastMonthAvgUsage.getObjectName(), true)){
			commonFunction.compareText(avgCons + " kWh/day", getPageElement(ViewBillPageObjects.lbl_LastMonthAvgUsage).getText(), ViewBillPageObjects.lbl_LastMonthAvgUsage.getObjectName());
		}
		
		//Last Month New Charges
		newCharges = commonFunction.formatAmount(electricAmountLast);
		if(commonFunction.isElementPresent(ViewBillPageObjects.lbl_LastMonthCharges.getLocatorType().toString(),
				ViewBillPageObjects.lbl_LastMonthCharges.getProperty(), ViewBillPageObjects.lbl_LastMonthCharges.getObjectName(), true)){
			commonFunction.compareText("$"+newCharges, getPageElement(ViewBillPageObjects.lbl_LastMonthCharges).getText(), ViewBillPageObjects.lbl_LastMonthCharges.getObjectName());
		}
		
		
	}


	/*************************************************************
	 * CRAFT View Bill Page - Account History
	 * This function validates the Account History table displayed
	 * on View Bill Page
	 * 
	 * @author 387478
	 * @throws IOException 
	 * @modified by 387478 on Oct 30- 
	 * 
	 * *************************************************************
	 */	
	public void checkAccountHistory() throws InterruptedException
	{
		action.sendKeys(Keys.HOME).perform();
		//action.sendKeys(Keys.PAGE_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();
		action.sendKeys(Keys.ARROW_DOWN).perform();

		//Verify if Your Bills section is selected by default		    
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lnk_Yourbills.getLocatorType().toString(),
				ViewBillPageObjects.lnk_Yourbills.getProperty(),ViewBillPageObjects.lnk_Yourbills.getObjectName())){
			//Get class attribute to verify if Your Bills is selected by default
			if(getPageElement(ViewBillPageObjects.lnk_Yourbillsproperty).getAttribute("class").equals("active"))			
				report.updateTestLog("Verify 'Your bills' section", "'Your Bills' section is selected by default", Status.PASS);
			else
				report.updateTestLog("Verify 'Your bills' section", "Your bills Link is not selected by default", Status.FAIL);
		} 

		//Find no. of records displayed in Your bills section
		int pastBillCount = getPageElements(ViewBillPageObjects.lnk_Yourbills_history).size();
		if(pastBillCount == 0)
			report.updateTestLog("'Your bills' section", "No previous bills found in 'Your bills' section", Status.WARNING);
		else
			report.updateTestLog("'Your bills' section", pastBillCount + " previous bills found in 'Your bills' section", Status.PASS);


		//Click Your Payments section
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lnk_YourPayments), ViewBillPageObjects.lnk_YourPayments.getObjectName());
		Thread.sleep(4000);

		//Get class attribute to verify if Your Payments is selected after clicking
		if(getPageElement(ViewBillPageObjects.lnk_YourPaymentsProperty).getAttribute("class").equals("active"))		
			report.updateTestLog("Verify 'Your payments' section", "'Your payments' section is displayed and selected after click", Status.PASS);
		else
			report.updateTestLog("Verify 'Your payments' section", "'Your payments' section is not selected after click", Status.FAIL);


		//Find no. of records displayed in Your payments section
		int pastPaymentCount = getPageElements(ViewBillPageObjects.lnk_Yourpayments_history).size();
		if(pastPaymentCount == 0)
			report.updateTestLog("'Your Payments' section", "No previous payments found in 'Your payments' section", Status.WARNING);
		else
			report.updateTestLog("'Your Payments' section", pastPaymentCount + " previous payments found in 'Your payments' section", Status.PASS);

	}	

	/***********************************************************
	 * CRAFT - Verify AMI Graph
	 * 
	 * This function verifies the AMI  Graph for AMI and NON-AMI users
	 * 
	 * @author 387478
	 * @throws IOException 
	 * @modified by 387478 on Nov 11 - Verified AMI Image
	 * ***********************************************************
	 */

	public void verifyAMIGraph() throws InterruptedException{
		String ami = commonFunction.getData("Accounts", "Ami", "Ami", true);
		String environment = properties.getProperty("Environment");

		//Energy Usage Section
		action.sendKeys(Keys.END).perform();
		//Verify if Energy Usage Section Header is displayed
		commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_EnergyUseSectionHeader.getLocatorType().toString(),
				ViewBillPageObjects.lbl_EnergyUseSectionHeader.getProperty(),ViewBillPageObjects.lbl_EnergyUseSectionHeader.getObjectName());

		//If account is AMI enrolled, verify AMI message
		if(ami.toLowerCase().equals("yes")){
			//Verify AMI Legend is displayed and Verify AMI Legend Image
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.img_AmiLegend.getLocatorType().toString(),
					ViewBillPageObjects.img_AmiLegend.getProperty(),ViewBillPageObjects.img_AmiLegend.getObjectName())){

				//Verify correct AMI legend is displayed
				commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_AmiLegend),
						commonFunction.getData(environment,"General_Data", "AMIImageUrl","AMI Legend Image URL",true),
						ViewBillPageObjects.img_AmiLegend.getObjectName());				
			}
			//If AMI Message is displayed, verify the contents
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_AmiMessage.getLocatorType().toString(),
					ViewBillPageObjects.lbl_AmiMessage.getProperty(),ViewBillPageObjects.lbl_AmiMessage.getObjectName())){

				//Verify AMI Message
				try{
					commonFunction.compareText(getPageElement(ViewBillPageObjects.lbl_AmiMessage).getText().substring(2),
							commonFunction.getData("General_Data", "AmiMessage", ViewBillPageObjects.lbl_AmiMessage.getObjectName(), true),
							ViewBillPageObjects.lbl_AmiMessage.getObjectName());
				}catch(Exception e){
					report.updateTestLog(ViewBillPageObjects.lbl_AmiMessage.getObjectName(), "Unable to fetch contencts", Status.WARNING);
				}
				//Verify AMI graph is displayed and click it
				if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.obj_AmiGraph.getLocatorType().toString(),
						ViewBillPageObjects.obj_AmiGraph.getProperty(),ViewBillPageObjects.obj_AmiGraph.getObjectName())){
					/*commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.obj_AmiGraph),
							commonFunction.getData(properties.getProperty("Environment"), "General_Data", "EnergyDashboard_Link", ViewBillPageObjects.lnl_onlineEnergyDashboard.getObjectName(),
									true),ViewBillPageObjects.obj_AmiGraph.getObjectName());*/
				}

				//Click "accessing your online energy dashboard." link
				commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lnl_onlineEnergyDashboard.getLocatorType().toString(),
						ViewBillPageObjects.lnl_onlineEnergyDashboard.getProperty(),ViewBillPageObjects.lnl_onlineEnergyDashboard.getObjectName());

				/*commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnl_onlineEnergyDashboard),
						commonFunction.getData(properties.getProperty("Environment"), "General_Data", "EnergyDashboard_Link", ViewBillPageObjects.lnl_onlineEnergyDashboard.getObjectName(),
								true),ViewBillPageObjects.lnl_onlineEnergyDashboard.getObjectName());*/
			}

		}else{
			//Verify non AMI Legend
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.img_NonAmiLegend.getLocatorType().toString(),
					ViewBillPageObjects.img_NonAmiLegend.getProperty(),ViewBillPageObjects.img_NonAmiLegend.getObjectName())){

				//Verify correct legend is displayed
				commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_NonAmiLegend),
						commonFunction.getData(environment,"General_Data", "NonAMIImageUrl","Non AMI Legend Image URL",true),
						ViewBillPageObjects.img_NonAmiLegend.getObjectName());
			}
			//Verify Non AMI Message
			if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_NonAmiMessage.getLocatorType().toString(),
					ViewBillPageObjects.lbl_NonAmiMessage.getProperty(),ViewBillPageObjects.lbl_NonAmiMessage.getObjectName())){

				commonFunction.compareText(getPageElement(ViewBillPageObjects.lbl_NonAmiMessage).getText().substring(1).trim(),
						commonFunction.getData("General_Data", "NonAmiMessage", ViewBillPageObjects.lbl_NonAmiMessage.getObjectName(), true),
						ViewBillPageObjects.lbl_NonAmiMessage.getObjectName());

				commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.obj_NonAmiGraph.getLocatorType().toString(),
						ViewBillPageObjects.obj_NonAmiGraph.getProperty(),ViewBillPageObjects.obj_NonAmiGraph.getObjectName());				

			}
		}	
	}



	/**
	 * CRAFT Tooltip - KeyWord to check all the tooltips displayed on the ViewBill page
	 * @author 324096
	 * @throws InterruptedException 
	 * @modified_date Oct 15, 2014
	 */

	public void verifyTooltipMessages() throws InterruptedException{
		action.sendKeys(Keys.HOME).perform();
		//Expand New Charges Section to check all the tooltips displayed in New Charges section		    
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lbl_NewChargesHeader), 
				ViewBillPageObjects.lbl_NewChargesHeader.getObjectName());

		String toolTipMessage = "";

		//Verify Balance Line Tooltip Message
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_BalanceLineTooltip, 
				ViewBillPageObjects.lbl_BalanceLineTooltipBub,
				ViewBillPageObjects.lbl_BalanceLineTooltipClose, 
				ViewBillPageObjects.lbl_BalanceLineTooltip.getObjectName());
		if(!toolTipMessage.isEmpty())
			commonFunction.compareText(commonFunction.getData("General_Data", "BalanceLineTooltip", "Balance Line Tooltip", true)
					, toolTipMessage, "Balance Line Tooltip Verification");



		/* //Verify Keep In Mind Tooltip Message
	    verifyTooltipMessage(ViewBillPageObjects.lbl_KeepInMindTooltip, 
	    		ViewBillPageObjects.lbl_KeepInMindTooltipBub, 
	    		ViewBillPageObjects.lbl_KeepInMindTooltip.getObjectName());*/

		//Verify New Charges Table Rate Help Message
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_RATEHelpMsg9, 
				ViewBillPageObjects.lbl_NnewChargesTable_LITR_RATEHelpMsgBub,
				ViewBillPageObjects.lbl_NnewChargesTable_LITR_RATEHelpMsgClose,
				ViewBillPageObjects.lbl_NewChargesTable_LITR_RATEHelpMsg9.getObjectName());

		//Verify CustomerCharge Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_VBDR_CustomerChargeHelpMsg, 
				ViewBillPageObjects.lbl_NewChargesTable_VBDR_CustomerChargeHelpMsgBub, 
				ViewBillPageObjects.lbl_NewChargesTable_VBDR_CustomerChargeHelpMsgClose,
				ViewBillPageObjects.lbl_NewChargesTable_VBDR_CustomerChargeHelpMsg.getObjectName());

		if(!toolTipMessage.isEmpty())
			commonFunction.compareText(commonFunction.getData("General_Data", "CustomerChargeTooltip", "CustomerCharge Tooltip", true)
					, toolTipMessage, "CustomerCharge Tooltip Verification");

		//Verify NonFuel Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_VBDR_NonFuelHelpMsg, 
				ViewBillPageObjects.lbl_NewChargesTable_VBDR_NonFuelHelpMsgBub, 
				ViewBillPageObjects.lbl_NewChargesTable_VBDR_NonFuelHelpMsgClose, 
				ViewBillPageObjects.lbl_NewChargesTable_VBDR_NonFuelHelpMsg.getObjectName());
		if(!toolTipMessage.isEmpty())
			commonFunction.compareText(commonFunction.getData("General_Data", "NonFuelTooltip", "NonFuel Tooltip", true)
					, toolTipMessage, "NonFuel Tooltip Verification");

		//Verify Fuel Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_VBDR_FuelHelpMsg, 
				ViewBillPageObjects.lbl_NewChargesTable_VBDR_FuelHelpMsgBub,
				ViewBillPageObjects.lbl_NewChargesTable_VBDR_FuelHelpMsgClose, 
				ViewBillPageObjects.lbl_NewChargesTable_VBDR_FuelHelpMsg.getObjectName());

		if(!toolTipMessage.isEmpty())
			commonFunction.compareText(commonFunction.getData("General_Data", "FuelTooltip", "Fuel Tooltip", true)
					, toolTipMessage, "Fuel Tooltip Verification");

		//Verify Call Credit Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_ON_CALL_CREDITHelpMsg, 
				ViewBillPageObjects.lbl_NewChargesTable_LITR_ON_CALL_CREDITHelpMsgBub,
				ViewBillPageObjects.lbl_NewChargesTable_LITR_ON_CALL_CREDITHelpMsg.getObjectName());

		if(!toolTipMessage.isEmpty())
			commonFunction.compareText(commonFunction.getData("General_Data", "CallCreditTooltip", "Call Credit Tooltip", true)
					, toolTipMessage, "Call Credit Tooltip Verification");

		//Verify Storm Charge Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_STORM_CHARGE_OTHERHelpMsg, 
				ViewBillPageObjects.lbl_NewChargesTable_LITR_STORM_CHARGE_OTHERHelpMsgBub,
				ViewBillPageObjects.lbl_NewChargesTable_LITR_STORM_CHARGE_OTHERHelpMsg.getObjectName());

		if(!toolTipMessage.isEmpty())
			commonFunction.compareText(commonFunction.getData("General_Data", "StormChargeTooltip", "Storm Charge Tooltip", true)
					, toolTipMessage, "Storm Charge Tooltip Verification");

		//Verify Gross Recipt Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_GROSS_RECEIPTS_TAX_INCRHelpMsg, 
				ViewBillPageObjects.lbl_NewChargesTable_LITR_GROSS_RECEIPTS_TAX_INCRHelpMsgBub,
				ViewBillPageObjects.lbl_NewChargesTable_LITR_GROSS_RECEIPTS_TAX_INCRHelpMsg.getObjectName());

		if(!toolTipMessage.isEmpty())
			commonFunction.compareText(commonFunction.getData("General_Data", "GrossReciptTooltip", "Gross Recipt Tooltip", true)
					, toolTipMessage, "Gross Recipt Tooltip Verification");

		//Verify Franchise Charge Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_FRANCHISE_CHARGEHelpMsg, 
				ViewBillPageObjects.lbl_NewChargesTable_LITR_FRANCHISE_CHARGEHelpMsgBub,
				ViewBillPageObjects.lbl_NewChargesTable_LITR_FRANCHISE_CHARGEHelpMsg.getObjectName());

		if(!toolTipMessage.isEmpty())
			commonFunction.compareText(commonFunction.getData("General_Data", "FranchiseChargeTooltip", "Franchise Charge Tooltip", true)
					, toolTipMessage, "Franchise Charge Tooltip Verification");



		//Verify Florida Sales Tax Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_NewChargesTable_LITR_FLORIDA_SALES_TAXHelpMsg, 
				ViewBillPageObjects.lbl_NewChargesTable_LITR_FLORIDA_SALES_TAXHelpMsgBub,
				ViewBillPageObjects.lbl_NewChargesTable_LITR_FLORIDA_SALES_TAXHelpMsg.getObjectName());

		if(!toolTipMessage.isEmpty())
			commonFunction.compareText(commonFunction.getData("General_Data", "FloridaSalesChargeTooltip", "Florida sales tax Tooltip", true)
					, toolTipMessage, "Florida sales tax Tooltip Verification");



		//Verify Discretionary sales surtax Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects. lbl_NewChargesTable_LITR_DISCRETIONARY_SALE_SURTAXHelpMsg, 
				ViewBillPageObjects. lbl_NewChargesTable_LITR_DISCRETIONARY_SALE_SURTAXHelpMsgBub,
				ViewBillPageObjects. lbl_NewChargesTable_LITR_DISCRETIONARY_SALE_SURTAXHelpMsg.getObjectName());

		if(!toolTipMessage.isEmpty())
			commonFunction.compareText(commonFunction.getData("General_Data", "DiscretionarySalesSurchargeTooltip", "Discretionary Sales Surcharge Tooltip", true)
					, toolTipMessage, "Discretionary Sales Surcharge Tooltip Verification");



		//Verify Utility Tax Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects. lbl_NewChargesTable_LITR_UTILITY_TAXHelpMsg, 
				ViewBillPageObjects. lbl_NewChargesTable_LITR_UTILITY_TAXHelpMsgBub,
				ViewBillPageObjects. lbl_NewChargesTable_LITR_UTILITY_TAXHelpMsg.getObjectName());

		if(!toolTipMessage.isEmpty())
			commonFunction.compareText(commonFunction.getData("General_Data", "UtilityTaxTooltip", "Utility Tax Tooltip", true)
					, toolTipMessage, "Utility Tax Tooltip Verification");


		//Verify Statement Balance Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_StatementBalanceTooltip, 
				ViewBillPageObjects.lbl_StatementBalanceTooltipBub,
				ViewBillPageObjects.lbl_StatementBalanceTooltip.getObjectName());


		//Close New Charges Section    
		commonFunction.clickIfElementPresent(getPageElement(ViewBillPageObjects.lbl_NewChargesHeader), 
				ViewBillPageObjects.lbl_NewChargesHeader.getObjectName()); 

		//Verify Meter Comparison Tooltip    

		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_MeterComparisonTooltip, 
				ViewBillPageObjects.lbl_MeterComparisonTooltipBub,
				ViewBillPageObjects.lbl_MeterComparisonTooltip.getObjectName());

		//Which message should be displayed based on customer usage was more or less
		/*  if(meterComparisonData.contains("more"))
	    	datasheetColumn = "MeterComparisonTooltipUp";
	    else
	    	datasheetColumn = "MeterComparisonTooltipDown";
		 */

		/*if(!toolTipMessage.isEmpty())
		    commonFunction.compareText(commonFunction.RemoveSpecialcharacters
		    		(commonFunction.getData("General_Data", datasheetColumn, "Meter Comparison Tooltip", true))
		    		, commonFunction.RemoveSpecialcharacters(toolTipMessage), "Meter Comparison Tooltip Verification");*/

		//Verify Meter Comparison Day Use Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_MeterComparisonDayUseTooltip, 
				ViewBillPageObjects.lbl_MeterComparisonDayUseTooltipBub, 
				ViewBillPageObjects.lbl_MeterComparisonDayUseTooltipClose,
				ViewBillPageObjects.lbl_MeterComparisonDayUseTooltip.getObjectName());


		//Verify Meter Comparison Units Comsumed Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_MeterComparisonUnitConsTooltip, 
				ViewBillPageObjects.lbl_MeterComparisonUnitConsTooltipBub,
				ViewBillPageObjects.lbl_MeterComparisonUnitConsTooltipClose,
				ViewBillPageObjects.lbl_MeterComparisonUnitConsTooltip.getObjectName());

		//Verify Meter Comparison Average Consumption Tooltip
		toolTipMessage = verifyTooltipMessage(ViewBillPageObjects.lbl_MeterComparisonAvgConsTooltip, 
				ViewBillPageObjects.lbl_MeterComparisonAvgConsTooltipBub,
				ViewBillPageObjects.lbl_MeterComparisonAvgConsTooltipClose,
				ViewBillPageObjects.lbl_MeterComparisonAvgConsTooltip.getObjectName());

	}

	/**************************************************************
	 * CRAFT Function to check various promotional messages displayed on
	 * View Bill page
	 * This function validates if View Bill PDF is displayed
	 * 
	 * @author 324096
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @modified by 387478 on Oct 13 - Incorporated Page Object Model
	 * *************************************************************
	 */
	public void verifyPromotionalMessagesViewBill() throws IOException, InterruptedException{
		action.sendKeys(Keys.END).perform();
		Thread.sleep(3000);

		//String customerName = commonFunction.getData("Accounts", "CustomerName", "Customer Name",true).trim();
		accountType=commonFunction.getData("Accounts", "AccountType", "Account Type",true).trim().toLowerCase();

		if(accountType.equals("residential")){

			//Verify Residential Left Message
			verifyResidentialLeftMessage();			
			action.sendKeys(Keys.END).perform();

			//Verify PowerTracker Message
			verifyResidentialCenterPromoMessage();
			action.sendKeys(Keys.END).perform();

			//Verify Residential Promotional Message
			verifyResidentialRightPromoMessage();			
		}
		else{
			verifyCommercialLeftPromoMessage();			
			action.sendKeys(Keys.END).perform();


			verifyCommercialCenterPromoMessage();
			action.sendKeys(Keys.END).perform();

			//Verify Commerical Promotional Message
			verifyCommercialRightPromoMessage();
		}

		action.sendKeys(Keys.HOME).perform();
	}


	/************************************************************************
	 * @description Method to perform mouse hover on given tooltip
	 * @param element
	 *            - element on which mouse hover action needs to be performed
	 * @param tooltipElement
	 * 			  - element from which text is to be extracted
	 * @param objName
	 * 			  - ObjName of the element which is to be updated in TestLog
	 * @author 387478
	 * @modified_date Oct 15, 2013
	 */
	public String verifyTooltipMessage(ViewBillPageObjects element, ViewBillPageObjects tooltipElement, String objName){
		String tooltipMessage = ""; //initialize ToolTip Message to blank
		try{
			//Verify if TootlTip icon is displayed
			if(commonFunction.isElementPresent(element.getLocatorType().toString(),element.getProperty(),"",false)){
				//Hover the tooltip icon
				WebElement tooltipIcon = getPageElement(element);
				Robot robot = new Robot();
				robot.mouseMove(0, 0);
				Actions builder = new Actions(driver);
				builder.moveToElement(tooltipIcon).build().perform();

				//Verify if tootltip message box is displayed upon hover
				if(commonFunction.isElementPresent(tooltipElement.getLocatorType().toString(),tooltipElement.getProperty(),"",false)){
					//Get the data present in tooltip message
					tooltipMessage = getPageElement(tooltipElement).getText();

					//Trim the message and eliminate the first three characters since they are irrelavent
					tooltipMessage = tooltipMessage.trim().substring(2);	

					//Update Report with the extracted tooltipMessage
					report.updateTestLog(objName,
							"Tooltip Message Contents:"+tooltipMessage, Status.PASS);			

				}else{
					report.updateTestLog(objName,
							"Unable to extract text from tooltip", Status.WARNING);						
				}
			}else{
				report.updateTestLog(objName,
						"Tooltip is not displayed", Status.WARNING);
			}

		} catch(Exception e){
			report.updateTestLog(objName,
					"Tooltip is not present or unable to extract text from tooltip", Status.WARNING);
		}
		return tooltipMessage;
	}

	/**
	 * @description Method to perform mouse hover on given tooltip and close the tooltip
	 * @param element
	 *            - element on which mouse hover action needs to be performed
	 * @param tooltipElement
	 * 			  - element from which text is to be extracted
	 * 
	 * @param tooltipElement
	 * 			  - element to close the tooltip
	 * 
	 * @param objName
	 * 			  - ObjName of the element which is to be updated in TestLog
	 * 
	 * @return String tooltipMessage
	 * 		   Returns empty string if the tooltip message is not found. Returns tooltip message if the tooltip is found 
	 * 
	 * @author 387478
	 * @modified_date Oct 15, 2013
	 */
	public String verifyTooltipMessage(ViewBillPageObjects element, ViewBillPageObjects tooltipElement
			,ViewBillPageObjects tooltipElementClose, String objName){
		String tooltipMessage = ""; //initialize ToolTip Message to blank
		try{
			//Verify if TootlTip icon is displayed
			if(commonFunction.isElementPresent(element.getLocatorType().toString(),element.getProperty(),"",false)){
				//Hover the tooltip icon
				WebElement tooltipIcon = getPageElement(element);
				Robot robot = new Robot();
				robot.mouseMove(0, 0);
				Actions builder = new Actions(driver);
				builder.moveToElement(tooltipIcon).build().perform();

				//Verify if tootltip message box is displayed upon hover
				if(commonFunction.isElementPresent(tooltipElement.getLocatorType().toString(),tooltipElement.getProperty(),"",false)){
					//Get the data present in tooltip message
					tooltipMessage = getPageElement(tooltipElement).getText();

					//Trim the message and eliminate the first three characters since they are irrelavent
					tooltipMessage = tooltipMessage.trim().substring(2);	

					//Update Report with the extracted tooltipMessage

					report.updateTestLog(objName,
							"Tooltip Message Contents:"+tooltipMessage, Status.PASS);

					//Close tooltip message
					if(commonFunction.isElementPresent(tooltipElementClose.getLocatorType().toString(),tooltipElementClose.getProperty(),"",false)){
						try{
							getPageElement(tooltipElementClose).click();
							report.updateTestLog("Close Tooltip",
									"Tooltip is closed", Status.PASS);
						}catch(Exception e){
							report.updateTestLog("Close Tooltip",
									"Unable to close the tooltip", Status.WARNING);
						}
					}else{
						report.updateTestLog("Close Tooltip",
								"Closed tooltip icon is not displayed", Status.WARNING);
					}

				}else{
					report.updateTestLog(objName,
							"Unable to extract text from tooltip", Status.WARNING);						
				}
			}else{
				report.updateTestLog(objName,
						"Tooltip is not present", Status.WARNING);
			}

		} catch(Exception e){
			report.updateTestLog(objName,
					"Tooltip is not present or unable to extract text from tooltip", Status.WARNING);
		}
		return tooltipMessage;
	}

	

	/*********************************************************
	 * Function to return WebElement for ViewBillPageObjects
	 * 
	 * This function takes ViewBillPageObjects object as an
	 * input and returns the WebElement object for the input 
	 * object 
	 * 
	 * @param viewBillPageEnum 
	 * 		  The enum element for which WebElement object needs to be 
	 *        created
	 * @return WebElement
	 * 		   WebElement object corresponding to the input enum is 
	 * 		   returned 
	 * ********************************************************
	 */

	private  WebElement getPageElement(ViewBillPageObjects viewBillPageEnum)
	{
		try{

			return commonFunction.getElementByProperty(viewBillPageEnum.getProperty(), viewBillPageEnum
					.getLocatorType().toString());
		} catch(Exception e){
			report.updateTestLog("Get page element", viewBillPageEnum.toString()
					+ " object is not defined or found.", Status.FAIL);
			return null;
		}
	}

	/*********************************************************
	 * Function to return WebElement for Keep In Mind Message
	 * 
	 * This function takes ViewBillPageObjects object as an
	 * input and returns the WebElement object for the input 
	 * object 
	 * 
	 * 
	 * @return WebElement
	 * 		   WebElement object corresponding to the input enum is 
	 * 		   returned 
	 * @author 387478
	 * ********************************************************
	 */

	private  WebElement getPageElement(String property, String locator)
	{
		try{

			return commonFunction.getElementByProperty(property, locator);
		} catch(Exception e){
			report.updateTestLog("Get page element", locator
					+ " object is not defined or found.", Status.FAIL);
			return null;
		}
	}


	/*********************************************************
	 * Function to return WebElements for ViewBillPageObjects
	 * 
	 * This function takes ViewBillPageObjects object as an
	 * input and returns the WebElement object for the input 
	 * object 
	 * 
	 * @param viewBillPageEnum 
	 * 		  The enum element for which WebElement object needs to be 
	 *        created
	 * @return WebElement
	 * 		   WebElement object corresponding to the input enum is 
	 * 		   returned 
	 * ********************************************************
	 */

	private  List<WebElement> getPageElements(ViewBillPageObjects viewBillPageEnum)
	{
		try{

			return commonFunction.getElementsByProperty(viewBillPageEnum.getProperty(), viewBillPageEnum
					.getLocatorType().toString());
		} catch(Exception e){
			report.updateTestLog("Get page element", viewBillPageEnum.toString()
					+ " object is not defined or found.", Status.FAIL);
			return null;
		}
	}


	/***********************************************************
	 * downloadBill
	 * This method verifies a new window is opened when user 
	 * clicks 'Download Bill' link and takes the screenshot 
	 * of the bill
	 * 
	 * @param lnk_Downloadbill
	 *        ViewBillPageObject which is to be downladed
	 * @throws InterruptedException 
	 * 
	 * ***********************************************************
	 */
	private void downloadBill(ViewBillPageObjects downloadlink, String objName) throws InterruptedException {

		String winHandleBefore = driver.getWindowHandle();
		if(commonFunction.clickIfElementPresent(getPageElement(downloadlink), objName)){
			Actions action = new Actions(driver);			
			String lastHandle="";

			System.out.println(driver.getWindowHandles().size());
			//Find Last opened window
			for(String winHandle : driver.getWindowHandles())
				lastHandle = winHandle;

			//Navigate to the BILL PDF and take screenshot				
			driver.switchTo().window(lastHandle);
			Thread.sleep(10000);
			driver.manage().window().maximize();
			report.updateTestLog(objName, "Take Screesnhot of displayed bill", Status.SCREENSHOT);
			action.sendKeys(Keys.END).perform();
			report.updateTestLog(objName, "Take Screesnhot of displayed bill", Status.SCREENSHOT);

			/*driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "c"));

		            System.out.println("");
		            System.out.println(getClipboardContents());*/

			driver.close(); 


			//Navigate back to original window
			driver.switchTo().window(winHandleBefore);
		}
	}
	
	/***********************************************************
	 * downloadandVerifyBill
	 * This method verifies a new window is opened when user 
	 * clicks 'Download Bill' link, takes screenshot of the 
	 * displayed bill and verifies the bill content
	 * 
	 * @param lnk_Downloadbill
	 *        ViewBillPageObject which is to be downladed
	 * @throws InterruptedException 
	 * 
	 * ***********************************************************
	 */
	private void downloadandVerifyBill(ViewBillPageObjects downloadlink, String objName) throws InterruptedException {

		String winHandleBefore = driver.getWindowHandle();
		if(commonFunction.clickIfElementPresent(getPageElement(downloadlink), objName)){
			Actions action = new Actions(driver);			
			String lastHandle="";

			//Find Last opened window
			for(String winHandle : driver.getWindowHandles())
				lastHandle = winHandle;

			//Navigate to the BILL PDF and take screenshot				
			driver.switchTo().window(lastHandle);
			Thread.sleep(10000);
			driver.manage().window().maximize();
			report.updateTestLog(objName, "Take Screesnhot of displayed bill", Status.SCREENSHOT);
			action.sendKeys(Keys.END).perform();
			report.updateTestLog(objName, "Take Screesnhot of displayed bill", Status.SCREENSHOT);

			String currentURL = driver.getCurrentUrl();
			/*driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
		            driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "c"));

		            System.out.println("");
		            System.out.println(getClipboardContents());*/

			String pdfData = getViewBillPDFData();
			if(!pdfData.isEmpty()){				
				report.updateTestLog("PDF Data", "Data Extracted Successfully", Status.DONE);
				
				String customerName = commonFunction.getData("Accounts", "CustomerName", "Customer Name",true);
				String serviceAddress = commonFunction.getData("Accounts", "ServiceAddress", "Service Address",true);
				String billingAddress = commonFunction.getData("Accounts", "BillingAddress", "Billing Address",true);
				String accountNumber = commonFunction.getData("Accounts", "AccountNumber", "Account Number",true);
				String currentReading = commonFunction.getData("Accounts", "CurrentReading", "Current Reading",true);
				String previousReading = commonFunction.getData("Accounts", "PreviousReading", "Previous Reading",true);
				String meterNumber = commonFunction.getData("Accounts", "MeterNumber", "Meter Number",true);
				String nextReadDate = commonFunction.getData("Accounts", "NextReadDate", "Next Read Date",true);
				String lastBillDate = commonFunction.getData("Accounts", "LastBillDate", "Last Bill Date",true);
				String serviceDate1 = commonFunction.getData("Accounts", "ServiceDate1", "Service Date 1",true);
				String serviceDate2 = commonFunction.getData("Accounts", "ServiceDate2", "Service Date 2",true);
				String newCharges = commonFunction.getData("Accounts", "NewCharges", "New Charges",true);
				String amountOwed = commonFunction.getData("Accounts", "CurrentBalance", "Current Balance",true);
				
				
				System.out.println("PDF Data:" + pdfData);
				action.sendKeys(Keys.HOME).perform();
				
				//Verify Bill Date
				commonFunction.verifyStringContainsText(pdfData, lastBillDate + " Electric Bill", "Electric Bill Date");
				
				//Verify Bill period
				long diff = commonFunction.findDateDifference(serviceDate2, serviceDate1);
				String day="day";
				if(diff>1)
					day="days";
				commonFunction.verifyStringContainsText(pdfData, "For: " + serviceDate1 + " to " + serviceDate2 +" ("+diff+" "+ day+")", "Electric Bill Period");
				
				//Verify Service Address
				commonFunction.verifyStringContainsTextRemoveSpaces(pdfData, "Service Address "+ serviceAddress, "Service Address");
				
				//Verify Account Number
				//Pad accountnumber with zeros
				if(accountNumber.length()> 0 && accountNumber.length()<10)
					accountNumber = String.format("%010d", Integer.parseInt(accountNumber));
				
				accountNumber = commonFunction.insertCharAt(accountNumber, '-', 5);
				commonFunction.verifyStringContainsText(pdfData, "Account Number "+ accountNumber, "Account Number");
				
				//Verify Billing Address
				commonFunction.verifyStringContainsTextRemoveSpaces(pdfData, customerName.toUpperCase() +" "+ billingAddress.toUpperCase(), "Billing Address");
				
				//Verify Meter Summary Section
				commonFunction.verifyStringContainsText(pdfData, "Meter Summary", "Meter Summary Label");
				commonFunction.verifyStringContainsText(pdfData, "Meter reading - Meter " + meterNumber +" Next meter reading " + nextReadDate, "Meter Reading summary line");
				commonFunction.verifyStringContainsText(pdfData, "Current reading "+ currentReading, "Meter Current Reading");
				commonFunction.verifyStringContainsText(pdfData, "Previous reading - "+ previousReading, "Meter Previous Reading");
				int currentReadingnum = Integer.parseInt(currentReading);
				int previousReadingnum = Integer.parseInt(previousReading);
				int usage = currentReadingnum - previousReadingnum;
				commonFunction.verifyStringContainsText(pdfData, "kWh used " + usage, "kWh used");
				
				//Blue Band Section
				//BlueBand Last Bill Amount
				//commonFunction.verifyStringContainsText(pdfData, lastBillDate + " Electric Bill", "Blue Band Last Bill Amount");
				
				//Blue Band New Charges
				commonFunction.verifyStringContainsText(pdfData, "New charges "+commonFunction.formatAmountWithDollar(newCharges), "Blue Band New Charges");
				
				//Total New Charges
				commonFunction.verifyStringContainsText(pdfData, "Total new charges "+commonFunction.formatAmountWithDollar(newCharges), "Total New Charges");
				
				//Total Amount you OWE
				//commonFunction.verifyStringContainsTextRemoveSpaces(pdfData, "Total amount you owe "+commonFunction.formatAmountWithDollar(amountOwed), "Total Amount owed");
				
				
				action.sendKeys(Keys.END).perform();
				
				commonFunction.verifyStringContainsText(pdfData, "Meter Summary", "Meter Summary Label");
				//Verify Promotional Messages
				//Left Promotional Message
				String leftPromoHeader = commonFunction.getData("General_Data", "Res_Left_Header", "Left Promo header",true);
				String leftPromoMessage = commonFunction.getData("General_Data", "Res_Left_Message", "Left Promo header",true);
								
				commonFunction.verifyStringContainsText(pdfData, leftPromoHeader, "Left Promo Header");
				commonFunction.verifyStringContainsTextRemoveSpaces(pdfData, leftPromoMessage, "Left Promo Message");
				
				//Center Promo Message
				String centerPromoHeader = commonFunction.getData("General_Data", "Res_Center_Header", "Center Promo header",true);
				String centerPromoMessage = commonFunction.getData("General_Data", "Res_Center_Message", "Center Promo header",true);
								
				commonFunction.verifyStringContainsText(pdfData, centerPromoHeader, "Center Promo Header");
				commonFunction.verifyStringContainsTextRemoveSpaces(pdfData, centerPromoMessage, "Center Promo Message");
				
				
				//Right Promo Message
				String rightPromoHeader = commonFunction.getData("General_Data", "Res_Right_Header", "Right Promo header",true);
				String rightPromoMessage = commonFunction.getData("General_Data", "Res_Right_Message", "Right Promo header",true);
								
				commonFunction.verifyStringContainsText(pdfData, rightPromoHeader, "Right Promo Header");
				commonFunction.verifyStringContainsTextRemoveSpaces(pdfData, rightPromoMessage, "Right Promo Message");
				
				
			}else{
				report.updateTestLog("PDF Data", "Unable to extract PDF Data. Either pdf is not displayed or error occured during data extraction.", Status.WARNING);
			}
			driver.close(); 
			driver.switchTo().window(winHandleBefore);
		}
	}
	
	
	/**********************************************************
	 * View Bill PDF
	 * This function validates if View Bill PDF is displayed
	 * on clicking 'Download Bill' button in 'Your Bills' section
	 * and takes screenshot of the PDF
	 * 
	 * @author 324096
	 * @throws IOException
	 * 
	 * **********************************************************
	 */
	public void viewBillPDFDownload() throws InterruptedException{		
		//Loop for all 'Download Bill' links in "Your Bills" section 
		try{
			String winHandleBefore1 = driver.getWindowHandle();
			driver.findElement(By.xpath("(//a[contains(text(),'Download Bill')])[2]")).click();
			int count = 0;

			for(String winHandle : driver.getWindowHandles()){
				count++;
				if(!winHandle.equals(winHandleBefore1)){
					if(driver.getWindowHandles().size()>2 && (count == 1)){
						driver.switchTo().window(winHandle);
						driver.close();
						count++;
					}
					driver.switchTo().window(winHandle);
					Thread.sleep(10000);
					driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
					report.updateTestLog("Account History: Your Bills", "User Clicks on Download Bill Link", Status.PASS);
					driver.close();
					break;	
				}
			}

			/*for(String winHandle : driver.getWindowHandles()){
			        if(!winHandle.equals(winHandleBefore1)){
			            driver.switchTo().window(winHandle);
			            Thread.sleep(10000);
			            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			            report.updateTestLog("Account History: Your Bills", "User Clicks on Download Bill Link", Status.PASS);
			            driver.close();
			            break;	
			        }
			    }*/
			driver.switchTo().window(winHandleBefore1);
		}
		catch(Exception ex){
			report.updateTestLog("Account History: Your Bills", "VB PDF not found", Status.FAIL);
		}

	}


	/***********************************************************
	 * verifyViewBillPDF
	 *       Returns the data of pdf in the current window
	 * @author 387478
	 * 
	 * *
	 * @throws InterruptedException **********************************************************
	 */
	private String getViewBillPDFData() throws InterruptedException{
		String pdfData = "";
		try{
			String currentURL = driver.getCurrentUrl();
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			driver.findElement(By.cssSelector("body")).sendKeys(Keys.chord(Keys.CONTROL, "c"));

			driver.get("http://sggs.netne.net");
			driver.navigate().refresh();
			Thread.sleep(1000);
			WebElement element = driver.findElement(By.id("text"));
			Actions action = new Actions(driver);
			action.click(element);
			driver.findElement(By.id("text")).sendKeys(Keys.chord(Keys.CONTROL, "v"));
			pdfData = driver.findElement(By.id("text")).getAttribute("value").trim();
			
			driver.get(currentURL);
			Thread.sleep(1000);
		}catch(Exception e){
			return pdfData;
		}
		return commonFunction.removeNewLineCharacters(pdfData);
	}

	/***********************************************************
	 * verifyOHESPromoMessage
	 * -- This method verifies OHES Header, OHES Message and 'Get Started' link 
	 * 
	 * 
	 * ***********************************************************
	 */
	private void verifyResidentialLeftMessage() throws InterruptedException{
		action.sendKeys(Keys.END).perform();
		String environment = properties.getProperty("Environment");
		//Verify Left header
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_ResLeftHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_ResLeftHeader.getProperty(), ViewBillPageObjects.lbl_ResLeftHeader.getObjectName())){
			String header = getPageElement(ViewBillPageObjects.lbl_ResLeftHeader).getText();		
			commonFunction.compareText(commonFunction.getData("General_Data", "Res_Left_Header","Residential Left Header", true),header
					,ViewBillPageObjects.lbl_ResLeftHeader.getObjectName());
		}

		//Verify Left Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_ResLeftImage.getLocatorType().toString(),
				ViewBillPageObjects.img_ResLeftImage.getProperty(), ViewBillPageObjects.img_ResLeftImage.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_ResLeftImage),
					commonFunction.getData(environment,"General_Data", "Res_Left_ImageUrl",ViewBillPageObjects.img_ResLeftImage.getObjectName(),true),
					ViewBillPageObjects.img_ResLeftImage.getObjectName());
		}
		//Verify Left Message
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_ResLeftMessage.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_ResLeftMessage.getProperty(), ViewBillPageObjects.lbl_ResLeftMessage.getObjectName())){
			String ohesMessage = getPageElement(ViewBillPageObjects.lbl_ResLeftMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Res_Left_Message", "Residential Left Message",true),
					ohesMessage,
					ViewBillPageObjects.lbl_ResLeftMessage.getObjectName());
		}

		//Click Link and Verify URL for 'Get Started'
		//String currentURL = driver.getCurrentUrl();

		action.sendKeys(Keys.END).perform();
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_ResLeftUrl.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_ResLeftUrl.getProperty(), ViewBillPageObjects.lnk_ResLeftUrl.getObjectName(),true)){
			/*		commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_GetStarted),
					commonFunction.getData("General_Data", column,"OHES Get Started URL",true),
					ViewBillPageObjects.lnk_GetStarted.getObjectName()
					);
			driver.get(currentURL);*/
			commonFunction.compareText(commonFunction.getData(environment,"General_Data", "Res_Left_Url",ViewBillPageObjects.lnk_ResLeftUrl.getObjectName(),true), 
					getPageElement(ViewBillPageObjects.lnk_ResLeftUrl).getAttribute("href"),
					ViewBillPageObjects.lnk_ResLeftUrl.getObjectName()+" verification");

		}
		action.sendKeys(Keys.END).perform();
		Thread.sleep(1000);
	}

	/***********************************************************
	 * verifyPowerTrackerPromoMessage
	 * -- This method verifies PowerTracker Header, PowerTracker Message and 'Here's How' link 
	 * 
	 * ***********************************************************
	 */
	private void verifyResidentialCenterPromoMessage() throws InterruptedException{
		Thread.sleep(1000);
		action.sendKeys(Keys.END).perform();
		//Verify PowerTracker Header
		String environment = properties.getProperty("Environment");
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_ResRightHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_ResRightHeader.getProperty(), ViewBillPageObjects.lbl_ResRightHeader.getObjectName())){
			commonFunction.compareText(commonFunction.getData("General_Data", "Res_Center_Header", ViewBillPageObjects.lbl_ResRightHeader.getObjectName(),true),
					getPageElement(ViewBillPageObjects.lbl_ResRightHeader).getText(),ViewBillPageObjects.lbl_ResRightHeader.getObjectName());
		}

		//Verify Residential Center Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_ResCenterImage.getLocatorType().toString(),
				ViewBillPageObjects.img_ResCenterImage.getProperty(), ViewBillPageObjects.img_ResCenterImage.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_ResCenterImage),
					commonFunction.getData(environment,"General_Data", "Res_Center_ImageUrl",ViewBillPageObjects.img_ResCenterImage.getObjectName(),true),
					ViewBillPageObjects.img_ResCenterImage.getObjectName());
		}

		//Verify Residential Center Message
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_ResCenterMessage.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_ResCenterMessage.getProperty(), ViewBillPageObjects.lbl_ResCenterMessage.getObjectName())){
			String powerTrackerMessage = getPageElement(ViewBillPageObjects.lbl_ResCenterMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Res_Center_Message","Residential Center Message",true),
					powerTrackerMessage,ViewBillPageObjects.lbl_ResCenterMessage.getObjectName());

		}
		String currentURL = driver.getCurrentUrl();

		action.sendKeys(Keys.END).perform();
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_ResCenterUrl.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_ResCenterUrl.getProperty(), ViewBillPageObjects.lnk_ResCenterUrl.getObjectName(),true)){
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_ResCenterUrl),
					commonFunction.getData(environment,"General_Data", "Res_Center_Url",ViewBillPageObjects.lnk_ResCenterUrl.getObjectName(),true),
					ViewBillPageObjects.lnk_ResCenterUrl.getObjectName()
			);
			driver.navigate().to(currentURL);
			Thread.sleep(1000);
		}
	}

	private void verifyResidentialRightPromoMessage() throws InterruptedException{
		Thread.sleep(5000);
		action.sendKeys(Keys.END).perform();
		//Verify Right Promotional Message Header
		String environment = properties.getProperty("Environment");
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_ResidentialRightHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_ResidentialRightHeader.getProperty(), ViewBillPageObjects.lbl_ResidentialRightHeader.getObjectName())){
			commonFunction.compareText(commonFunction.getData("General_Data", "Res_Right_Header", "Residential Right Header",true),
					getPageElement(ViewBillPageObjects.lbl_ResidentialRightHeader).getText(),ViewBillPageObjects.lbl_ResidentialRightHeader.getObjectName());
		}

		//Verify Residential Right Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_RightImage.getLocatorType().toString(),
				ViewBillPageObjects.img_RightImage.getProperty(), ViewBillPageObjects.img_RightImage.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_RightImage),
					commonFunction.getData(environment,"General_Data", "PointerImageUrl","Check Image URL",true),
					ViewBillPageObjects.img_RightImage.getObjectName());
		}

		//Verify Right Promotional Message
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_ResidentialRightMessage.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_ResidentialRightMessage.getProperty(), ViewBillPageObjects.lbl_ResidentialRightMessage.getObjectName())){
			String powerTrackerMessage = getPageElement(ViewBillPageObjects.lbl_ResidentialRightMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Res_Right_Message","Residential Right Message",true),
					powerTrackerMessage,ViewBillPageObjects.lbl_ResidentialRightMessage.getObjectName());

		}
		String currentURL = driver.getCurrentUrl();

		//See How URL
		action.sendKeys(Keys.END).perform();
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_ResRightUrl.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_ResRightUrl.getProperty(), ViewBillPageObjects.lnk_ResRightUrl.getObjectName(),true)){
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_ResRightUrl),
					commonFunction.getData(environment,"General_Data", "Res_Right_Url",ViewBillPageObjects.lnk_ResRightUrl.getObjectName(),true),
					ViewBillPageObjects.lnk_ResRightUrl.getObjectName()
			);
			driver.navigate().to(currentURL);
		}
	}



	/***********************************************************
	 * verifyCommercialLeftMessage
	 * -- This method verifies OBEE Header, OBEE Message and 'Let Us Help' link 
	 * 
	 * 
	 * ***********************************************************
	 */
	private void verifyCommercialLeftPromoMessage() throws InterruptedException{
		action.sendKeys(Keys.END).perform();
		String environment = properties.getProperty("Environment");
		//Verify Commercial Left header
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_LeftHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_LeftHeader.getProperty(), ViewBillPageObjects.lbl_LeftHeader.getObjectName())){
			String ohesHeader = getPageElement(ViewBillPageObjects.lbl_LeftHeader).getText();		
			commonFunction.compareText(commonFunction.getData("General_Data", "Com_Left_Header","Commercial Left Header", true),
					ohesHeader,ViewBillPageObjects.lbl_LeftHeader.getObjectName());
		}

		//Verify Icon Arrow Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_IconArrow.getLocatorType().toString(),
				ViewBillPageObjects.img_IconArrow.getProperty(), ViewBillPageObjects.img_IconArrow.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_IconArrow),
					commonFunction.getData(environment,"General_Data", "ArrowImageUrl","Icon Arrow Image URL",true),
					ViewBillPageObjects.img_IconArrow.getObjectName());
		}

		//Verify Commercial Left Message
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_LeftMessage.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_LeftMessage.getProperty(), ViewBillPageObjects.lbl_LeftMessage.getObjectName())){
			String ohesMessage = getPageElement(ViewBillPageObjects.lbl_LeftMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Com_Left_Message", "Commercial Left Message",true),
					ohesMessage.substring(0,ohesMessage.length() - 12),
					ViewBillPageObjects.lbl_LeftMessage.getObjectName());
		}

		//Click Link and Verify URL for 'Lower Bill'
		String currentURL = driver.getCurrentUrl();


		action.sendKeys(Keys.END).perform();

		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_LowerBill.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_LowerBill.getProperty(), ViewBillPageObjects.lnk_LowerBill.getObjectName(),true)){
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_LowerBill),
					commonFunction.getData(environment,"General_Data", "Com_Left_Url","Commercial Left URL",true),
					ViewBillPageObjects.lnk_LowerBill.getObjectName()
			);
			driver.navigate().to(currentURL);
			Thread.sleep(5000);
		}

		action.sendKeys(Keys.END).perform();
	}

	private void verifyCommercialCenterPromoMessage() throws InterruptedException{
		action.sendKeys(Keys.END).perform();
		String environment = properties.getProperty("Environment");
		//Verify Commercial Center header
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_CenterHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_CenterHeader.getProperty(), ViewBillPageObjects.lbl_CenterHeader.getObjectName())){
			String ohesHeader = getPageElement(ViewBillPageObjects.lbl_CenterHeader).getText();		
			commonFunction.compareText(commonFunction.getData("General_Data", "Com_Center_Header","Commercial Center Header", true),
					ohesHeader, ViewBillPageObjects.lbl_CenterHeader.getObjectName());
		}

		//Verify Sun Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_SunImage.getLocatorType().toString(),
				ViewBillPageObjects.img_SunImage.getProperty(), ViewBillPageObjects.img_SunImage.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_SunImage),
					commonFunction.getData(environment,"General_Data", "SunImageUrl","Sun Image URL",true),
					ViewBillPageObjects.img_SunImage.getObjectName());
		}

		//Verify Commercial Center Message
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_CenterMessage.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_CenterMessage.getProperty(), ViewBillPageObjects.lbl_CenterMessage.getObjectName())){
			String ohesMessage = getPageElement(ViewBillPageObjects.lbl_CenterMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Com_Center_Message", "Commercial Center Message",true),
					ohesMessage.substring(0,ohesMessage.length() - 12),
					ViewBillPageObjects.lbl_CenterMessage.getObjectName());
		}

		//Click Link and Verify URL for 'Lower Bill'
		String currentURL = driver.getCurrentUrl();


		action.sendKeys(Keys.END).perform();
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_SolarAction.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_SolarAction.getProperty(), ViewBillPageObjects.lnk_SolarAction.getObjectName(),true)){
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_SolarAction),
					commonFunction.getData(environment,"General_Data", "Com_Center_Url","Commercial Center URL",true),
					ViewBillPageObjects.lnk_SolarAction.getObjectName()
			);
			driver.navigate().to(currentURL);
			Thread.sleep(5000);
		}

		action.sendKeys(Keys.END).perform();
	}

	public void verifyCommercialRightPromoMessage() throws IOException, InterruptedException{
		action.sendKeys(Keys.END).perform();
		String environment = properties.getProperty("Environment");
		//Verify Commercial Right Header
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_RightHeader.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_RightHeader.getProperty(), ViewBillPageObjects.lbl_RightHeader.getObjectName()))
			commonFunction.verifyElementPresentEqualsText(getPageElement(ViewBillPageObjects.lbl_RightHeader), 
					ViewBillPageObjects.lbl_RightHeader.getObjectName(), commonFunction.getData("General_Data", "Com_Right_Header","Commercial Right Header",true));

		//Verify Icon Pointer Image
		if(commonFunction.isElementPresent(ViewBillPageObjects.img_IconPointer.getLocatorType().toString(),
				ViewBillPageObjects.img_IconPointer.getProperty(), ViewBillPageObjects.img_IconPointer.getObjectName(), true)){
			//Verify Image Source
			commonFunction.verifyImageSource(getPageElement(ViewBillPageObjects.img_IconPointer),
					commonFunction.getData(environment,"General_Data", "ArrowImageUrl","Icon Pointer Image URL",true),
					ViewBillPageObjects.img_IconPointer.getObjectName());
		}

		//Verify Commercial Right Message
		if(commonFunction.verifyIfElementIsPresent(ViewBillPageObjects.lbl_RightMessage.getLocatorType().toString(), 
				ViewBillPageObjects.lbl_RightMessage.getProperty(), ViewBillPageObjects.lbl_RightMessage.getObjectName())){				
			String residentialMessage = getPageElement(ViewBillPageObjects.lbl_RightMessage).getText();
			commonFunction.compareText(commonFunction.getData("General_Data", "Com_Right_Message","Commercial Right Message",true),
					residentialMessage.substring(0,residentialMessage.length() - 10),
					ViewBillPageObjects.lbl_RightMessage.getObjectName());
		}

		String currentURL = driver.getCurrentUrl();


		action.sendKeys(Keys.END).perform();
		if(commonFunction.isElementPresent(ViewBillPageObjects.lnk_BlogPost.getLocatorType().toString(), 
				ViewBillPageObjects.lnk_BlogPost.getProperty(), ViewBillPageObjects.lnk_BlogPost.getObjectName(),true)){				
			commonFunction.clickAndVerifyUrl(getPageElement(ViewBillPageObjects.lnk_BlogPost),
					commonFunction.getData(environment,"General_Data", "Com_Right_Url","Commercial Learn How URL",true),
					ViewBillPageObjects.lnk_BlogPost.getObjectName()
			);
			driver.navigate().to(currentURL);
		}		
	}
}

