package businesscomponents;

import org.openqa.selenium.By;

import com.cognizant.framework.Status;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;


/**
 * Business Component Library template
 * @author Cognizant
 */
public class PromotionalMessages extends ReusableLibrary
{
	/**
	 * Constructor to initialize the business component library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
	public PromotionalMessages(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	
	
	public void validateResidentialMessage()
	{
		
		String ResidentialHeader = driver.findElement(By.xpath("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[6]/table/tbody/tr[1]/td[3]/table/tbody/tr/td/div/p/b/span")).getText();
		if((ResidentialHeader.compareTo(dataTable.getData("General_Data", "ResHeader")))==0) 
		{		
    		   	report.updateTestLog("Residential Msg", "Residential Msg Header content is found and correct", Status.PASS);
    	}
		else
		{
			    report.updateTestLog("Residential Msg", "Residential Msg Header content is incorrect", Status.FAIL);
    	}
		String ResidentialMessage = driver.findElement(By.xpath("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[6]/table/tbody/tr[2]/td[6]/table/tbody/tr/td/p/span")).getText();
		if((ResidentialMessage.compareTo(dataTable.getData("General_Data", "ResMessage")))==0) 
		{		
    		   	report.updateTestLog("Residential Msg", "Residential Message content is found and correct", Status.PASS);
    	}
		else
		{
			    report.updateTestLog("Residential Msg", "Residential Message content is incorrect", Status.FAIL);
    	}	
		try
		{
	    	if (driver.findElement(By.linkText("See how")).isDisplayed())
			{
	    		String PTLink = driver.findElement(By.linkText("See how")).getAttribute("href");
	    		System.out.println(PTLink); 
				if((PTLink .compareTo(dataTable.getData("General_Data", "ResTestUrl")))==0) 
				{		
					driver.findElement(By.linkText("See how")).click();
					Thread.sleep(5000);
					report.updateTestLog("Residential Message", "Residential Message Link is correct", Status.PASS);
		    	}
				else
				{
					    report.updateTestLog("Residential Message", "Residential Message Link is incorrect", Status.FAIL);
		    	}
			}
		}
		catch(Exception ex)
		{
			report.updateTestLog("Residential Message", "Residential Message Link is not present", Status.FAIL);
		}
	}

	public void validatepowerTrackMessage()
	{
		
		String PowertrackerHeader = driver.findElement(By.xpath("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[6]/table/tbody/tr[1]/td[2]/table/tbody/tr/td/div/p/b/span")).getText();
		if((PowertrackerHeader.compareTo(dataTable.getData("General_Data", "PTHeader")))==0) 
		{		
    		   	report.updateTestLog("Power Tracker", "Power Tracker Header content is found and correct", Status.PASS);
    	}
		else
		{
			    report.updateTestLog("Power Tracker", "Power Tracker Header content is incorrect", Status.FAIL);
    	}
		String PowertrackerMessage = driver.findElement(By.xpath("//*[@id='frm']/table/tbody/tr[2]/td[3]/table/tbody/tr[2]/td/table/tbody/tr[3]/td/div/div/div/div[6]/table/tbody/tr[2]/td[4]/table/tbody/tr/td/p/span")).getText(); 
		if((PowertrackerMessage.compareTo(dataTable.getData("General_Data", "PTMessage")))==0) 
		{		
    		   	report.updateTestLog("Power Tracker", "Power Tracker Message content is found and correct", Status.PASS);
    	}
		else
		{
			    report.updateTestLog("Power Tracker", "Power Tracker Message content is incorrect", Status.FAIL);
    	}
		try
		{
	    	if (driver.findElement(By.linkText("Here's how")).isDisplayed())
			{
	    		String PTLink = driver.findElement(By.linkText("Here's how")).getAttribute("href");
	    		System.out.println(PTLink);
	    		if((PTLink .compareTo(dataTable.getData("General_Data", "PTTestUrl")))==0) 
				{		
					    driver.findElement(By.linkText("Here's how")).click();
					    Thread.sleep(5000);
						report.updateTestLog("Power Tracker", "Power Tracker Link is correct", Status.PASS);
		    	}
				else
				{
					    report.updateTestLog("Power Tracker", "Power Tracker Link is incorrect", Status.FAIL);
		    	}
			}
		}
		catch(Exception ex)
		{
			 report.updateTestLog("Power Tracker", "Power Tracker Link is not present", Status.FAIL);
		}
	}
	

}