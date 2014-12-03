package businesscomponents;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import pageobjects.EmailContentPageObjects;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import componentgroups.CommonFunctions;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

/**
 * Business Component Library template
 * @author Cognizant
 */
public class EmailContent extends ReusableLibrary
{
	CommonFunctions commonFunction = new CommonFunctions(scriptHelper);
	public static String EmailUrl=null;
	/**
	 * Constructor to initialize the business component library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
	public EmailContent(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	/**
	 *****************************************************
	 * Function to find the particular Email from Shared Mail Box
	 * @throws IOException 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public void searchEmail() throws InterruptedException, IOException
	{
		 String AccountNumber = commonFunction.insertCharAt(dataTable.getData("Accounts", "AccountNumber"),'-',5);
		 
		 driver.findElement(By.id(EmailContentPageObjects.txt_AccountNumber.getProperty())).sendKeys(AccountNumber);
		 driver.findElement(By.xpath(EmailContentPageObjects.btn_SearchAccount.getProperty())).click();
		 Thread.sleep(15000);
		 
		 try
		 {
			 Actions action = new Actions(driver);
	         WebElement element=driver.findElement(By.xpath(EmailContentPageObjects.lnk_EmailLink.getProperty()));
	
	         //Double click
	         action.doubleClick(element).perform();
	         Thread.sleep(30000);
			 
			 report.updateTestLog("Search Email","Email Found in Share Mail box, Account Number: "+ AccountNumber, Status.PASS);
		 }
		 catch(Exception ex)
		 {
				frameworkParameters.setStopExecution(true);
				throw new FrameworkException("Search Email", "Email not found");
			 
		 }
		
	}
	
}