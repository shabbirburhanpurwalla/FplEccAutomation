package supportlibraries;

import com.cognizant.framework.TestParameters;
import org.openqa.selenium.Platform;


/**
 * Class to encapsulate various input parameters required for each test script
 * @author Cognizant
 */
public class SeleniumTestParameters extends TestParameters
{
	public SeleniumTestParameters(String currentScenario, String currentTestcase)
	{
		super(currentScenario, currentTestcase);
	}
	
	
	private Browser browser;
	/**
	 * Function to get the browser for a specific test
	 * @return The browser
	 */
	public Browser getBrowser()
	{
		return browser;
	}
	/**
	 * Function to set the browser for a specific test
	 */
	public void setBrowser(Browser browser)
	{
		this.browser = browser;
	}
	
	private String browserVersion;
	/**
	 * Function to get the browserVersion for a specific test
	 * @return The browserVersion
	 */
	public String getBrowserVersion()
	{
		return browserVersion;
	}
	/**
	 * Function to set the browserVersion for a specific test
	 */
	public void setBrowserVersion(String version)
	{
		this.browserVersion = version;
	}
	
	private Platform platform;
	/**
	 * Function to get the platform for a specific test
	 * @return The platform
	 */
	public Platform getPlatform()
	{
		return platform;
	}
	/**
	 * Function to set the platform for a specific test
	 */
	public void setPlatform(Platform platform)
	{
		this.platform = platform;
	}
	
	private String platformVersion;
	/**
	 * Function to get the platformVersion for a specific test
	 * @return The platformVersion
	 */
	public String getPlatformVersion()
	{
		return platformVersion;
	}
	/**
	 * Function to set the platformVersion for a specific test
	 */
	public void setPlatformVerison(String platformverison)
	{
		this.platformVersion = platformverison;
	}
	
	private String resolution;
	/**
	 * Function to get the resolution for a specific test
	 * @return The resolution
	 */
	public String getResolution()
	{
		return resolution;
	}
	/**
	 * Function to set the resolution for a specific test
	 */
	public void setResolution(String resolution1)
	{
		this.resolution = resolution1;
	}
	
	private String description;
	/**
	 * Function to get the resolution for a specific test
	 * @return The resolution
	 */
	public String getDescription()
	{
		return description;
	}
	/**
	 * Function to set the resolution for a specific test
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	
	
	
}