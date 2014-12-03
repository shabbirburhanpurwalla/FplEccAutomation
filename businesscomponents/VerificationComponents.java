package businesscomponents;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import pageobjects.EmailContentPageObjects;
import pageobjects.LoginPageObjects;
import supportlibraries.DriverScript;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;

/**
 * Verification Components class
 * 
 * @author CTS
 */
public class VerificationComponents extends ReusableLibrary {
	/**
	 * Constructor to initialize the component library
	 * 
	 * @param scriptHelper
	 *            The {@link ScriptHelper} {@link DriverScript}
	 */
	
	public VerificationComponents(ScriptHelper scriptHelper) {
		super(scriptHelper);
	}

	private boolean isElementPresent(By by) 
	{
		try 
		{
			driver.findElement(by);
			return true;
		} 
		catch (NoSuchElementException e) 
		{
				return false;
		}
	}
	
	// Function to Verify that User has logged in Successfully into Application
	public void verifyLoginValidUser()
	{
		if(isElementPresent(By.linkText(LoginPageObjects.lnkLogOut.getProperty()))) {
			report.updateTestLog("Verify Login", "Login succeeded for valid user", Status.PASS);
		} else {
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Verify Login", "Login failed for valid user");
		}
	}
	
	// Function to Verify that User has logged in Successfully into Web Mail
	public void verifyLoginIntoWebMail()
	{
		if(isElementPresent(By.xpath(EmailContentPageObjects.lnk_LogOut.getProperty()))) {
			report.updateTestLog("Verify Login", "Login succeeded for valid user", Status.PASS);
		} else {
			frameworkParameters.setStopExecution(true);
			throw new FrameworkException("Verify Login", "Login failed for valid user");
		}
	}

}