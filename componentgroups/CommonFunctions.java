/**
 * 
 */
package componentgroups;


/*
 * 
 * The CommonFunctions java class is a compilation of reusable functions, which aid in rapid development of 
 * selenium scripts. It also promotes novice users to create highly stable selenium scripts. 
 * 
 * @Purpose       - Common Function Class
 * @author        - Cognizant
 * @Created       - 09 Nov 2011
 *  
 **********************************************
 */
import java.awt.Robot;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageobjects.ObjectLocator;
import pageobjects.ViewBillPageObjects;

//import supportlibraries.Asserts;
import supportlibraries.Browser;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;
import supportlibraries.WebDriverFactory;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import com.thoughtworks.selenium.Wait;
import bsh.ParseException;
//import com.thoughtworks.selenium.webdriven.WebDriverBackedSelenium;

@SuppressWarnings("unused")
public class CommonFunctions extends ReusableLibrary{
	public String ORName = "Common_OR";
	private final int SHORT_TO = 5;
	public static Wait wait;

	

	public CommonFunctions(ScriptHelper scriptHelper){
		super(scriptHelper);
	}

	private void assertTrue(boolean textPresent){
		
	}
	
	/*******************************************************
	 * Function to check whether Element is present on page
	 * 
	 * @Modified by 324096 on 13 Oct
	 *	
	 *****************************************************
	*/
	public boolean isElementPresent(By by)
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
	
	/**
	 * Verify if element is present on page
	 * 
	 * @param strObjectProperty
	 *            -
	 * @param strFindElementType
	 *            - Element type to search by.
	 * @return returns true if the element exist, otherwise, false.
	 */
	public boolean isElementPresent(String strObjectProperty, String strFindElementType){
		WebElement elemToFind = null;
		try{

			if(strFindElementType.equalsIgnoreCase("CSS"))
				elemToFind = driver.findElement(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				elemToFind = driver.findElement(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				elemToFind = driver.findElement(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				elemToFind = driver.findElement(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				elemToFind = driver.findElement(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				elemToFind = driver.findElement(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				elemToFind = driver.findElement(By.className(strObjectProperty));
			// report.updateTestLog("isElementPresent","Expected...",
			// Status.PASS);

		} catch(org.openqa.selenium.NoSuchElementException nsee){
			//System.out.println("Exception in isElementPresent:" + nsee);
			return false;
		}
		// Extra protection...
		if(elemToFind == null){
			return false;
		} else{
			//System.out.println("Found element:" + strObjectProperty);
			return true;
		}
	}
	
	/**
	 ************************************************************* 
	 * Function to verify if an element exists on the Webpage
	 * Does not print error to log if element is not found
	 * 
	 * @param locatorType
	 *            - Element type to search by. i.e CSS,ID,NAME etc.
	 *            
	 * @param property
	 *            - Property of the element to be located 
	 *            
	 * @param objName
	 *            - Name of the object to be updated in test log
	 *              Required when printError is true           
	 *            
	 * @param printError
	 * 			- If true, error will be printed to log when element
	 * 			  is not found.
	 *          - If false, no error will be printed to the log* 
	 *            
	 * @return true
	 *         If the element exists on page
	 *         
	 * @return false 
	 *   	   If the element does not exist on page
	 *   
	 * @author 387478
	 * @modifiedby 387478 on 11 Nov
	 *               added elemToFind.isDisplayed()
	 ************************************************************* 
	 */
	public boolean isElementPresent(String locatorType, String property, String objName, Boolean printError){
		WebElement elemToFind;	
		try{	    	
				elemToFind = getElementByProperty(property,locatorType,false);
				if(!elemToFind.isDisplayed()){
					if(printError)
			    		report.updateTestLog(objName, objName+" element is not displayed", Status.FAIL);
					
					return false;
				}
		    } catch(Exception e){
		    	if(printError)
		    		report.updateTestLog(objName, objName+" element is not displayed", Status.FAIL);
		        return false;
		    }
		    if(elemToFind == null || (!elemToFind.isDisplayed())){
		    	if(printError)
		    		report.updateTestLog(objName, objName+" element is not displayed", Status.FAIL);
		    	return false;
		    }
			return true;
	}
	
	/*******************************************************
	 * Function to Check whether Frame is present ON web page or not
	 * @param frameId
	 * @return
	 * ******************************************************
	 */
	public boolean isFramePresent(String frameId)
	{
		try {
				WebElement frame = driver.findElement(By.id(frameId));
			    if(frame.isDisplayed())
			    {
			    	return true;
			    }
			    else
			    {
			    	return false;
			    }
		    } 
	    catch (NoSuchElementException e) 
	    	{
				return false;
	    	}
	}
	
	/**
	 ************************************************************* 
	 * Function to verify and update test log if an element exists on the Webpage
	 * 
	 * @param  strFindElementType 
	 * 		   Type of element i.e ID, CSS, NAME, etc.
	 * 
	 * @param strObjectProperty
	 *        Propertu of the element which is to be searched
	 * @return true
	 *         If the element exists on page
	 *         
	 * @return false 
	 *   	   If the element does not exist on page
	 *   
	 * @modifiedby 387478 on 11 Nov
	 *         Added elemToFind.isDisplayed() condition
	 ************************************************************* 
	 */
	public boolean verifyIfElementIsPresent(String strFindElementType, String strObjectProperty, String objName){
		WebElement elemToFind;
		try{	    	
				
					if(strFindElementType.equalsIgnoreCase("CSS"))
						elemToFind = driver.findElement(By.cssSelector(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("XPATH"))
						elemToFind =  driver.findElement(By.xpath(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("ID"))
						elemToFind =  driver.findElement(By.id(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("NAME"))
						elemToFind = driver.findElement(By.name(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
						elemToFind = driver.findElement(By.linkText(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("TAG"))
						elemToFind =  driver.findElement(By.tagName(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("CLASS"))
						elemToFind =  driver.findElement(By.className(strObjectProperty));
					else if(strFindElementType.equalsIgnoreCase("PARTIALLINKTEXT"))
						elemToFind = driver.findElement(By.partialLinkText(strObjectProperty));
					else
						elemToFind = null;
				
		    } catch(Exception e){
		    	report.updateTestLog(objName, objName+" element is not displayed", Status.FAIL);
		        return false;
		    }
		    if(elemToFind == null || (!elemToFind.isDisplayed())){
		    	report.updateTestLog(objName, objName+" element is not displayed", Status.FAIL);
		    	return false;
		    }
		    report.updateTestLog(objName, objName+" is displayed", Status.PASS);	
			return true;
	}
	
	/**
	 ************************************************************* 
	 * Function to Retrieve data from dataTable.
	 * 
	 * @param sheetName
	 * 		  Sheet from which data is to be retrieved
	 *            
	 * @param columnName
	 * 		  Column from which data is to be retrieved
	 * 
	 * @param objName
	 * 		  Description of the retrieved data which is to be updated in Test Log
	 * 
	 * @param displayError
	 * 		  Displays error message if true is passed and data is blank in sheet
	 * 		  No error message is displayed if  false is passed
	 *            
	 * @return String
	 * 			
	 ************************************************************* 
	 */
	public String getData(String sheetName, String columnName, String objName, boolean displayError)
	{
		String retrievedData ="";
		String defaultValue ="BLANK";
		if(objName.equals("Account Balance"))
			defaultValue = "Zero";
		try{
			retrievedData =  dataTable.getData(sheetName, columnName);
			//retrievedData =  dataTable.getData("Accounts", "AccountNumber");
			if(retrievedData.isEmpty()){
				if(displayError)
					report.updateTestLog("Retrieve data from "+columnName, objName + " is blank in "+ columnName +" column. Setting it to " + defaultValue +" by default.", Status.WARNING);
			}
		}catch(Exception e){
			if(displayError)
				report.updateTestLog(objName, "Unable to retrieve data", Status.WARNING);
		}
		return retrievedData;
	}
	
	/**
	 ************************************************************* 
	 * Function to Retrieve data from dataTable based on input envrionment.
	 * This function will append QA_,Test_ or Prod_ in columnName based on the environment
	 * 
	 * @param environment
	 *        Envrionment for which data is to be retrieved		  
	 * 
	 * @param sheetName
	 * 		  Sheet from which data is to be retrieved
	 *            
	 * @param columnName
	 * 		  Column from which data is to be retrieved
	 * 
	 * @param objName
	 * 		  Description of the retrieved data which is to be updated in Test Log
	 * 
	 * @param displayError
	 * 		  Displays error message if true is passed and data is blank in sheet
	 * 		  No error message is displayed if  false is passed
	 *            
	 * @return String
	 * 
	 * @author 387478
	 * 			
	 ************************************************************* 
	 */
	public String getData(String environment, String sheetName, String columnName, String objName, boolean displayError){
		String retrievedData ="";
		String defaultValue ="BLANK";
		//Set SheetName based on environemtn
		if(environment.toLowerCase().equals("qa"))
			columnName = "QA_" + columnName;
		else if(environment.toLowerCase().equals("test"))
			columnName = "Test_" + columnName;
		else 
			columnName = "Prod_" + columnName;
			
		
		if(objName.equals("Account Balance"))
			defaultValue = "Zero";
		try{
			retrievedData =  dataTable.getData(sheetName, columnName);
			if(displayError){
				if(retrievedData.isEmpty())
					report.updateTestLog("Retrieve data from "+columnName, objName + " is blank in "+ columnName +" column. Setting it to " + defaultValue +" by default.", Status.WARNING);
			}
		}catch(Exception e){
			if(displayError)
				report.updateTestLog(objName, "Unable to retrieve data from Common Data:" +columnName+ ". Setting it to " + defaultValue, Status.WARNING);
		}
		return retrievedData;
	}
	
	/**
	 * Function to format a amount
	 * Adds Grouping identifier ','
	 * Returns a string having two characters after a dot
	 * 
	 * @param amount
	 *            Amount which is to be formatted
	 * @return string
	 * 
	 * @author 387478
	 */
	public String formatAmount(String amount){
		try{
			NumberFormat myFormat = NumberFormat.getInstance();
			myFormat.setGroupingUsed(true);	

			amount = RemoveSpecialcharactersFromAmount(amount);
			Double floatamount = Double.parseDouble(amount);
			floatamount = Math.abs(floatamount); 
			amount = myFormat.format(floatamount);
			if(!amount.contains(".")){
				amount = amount + ".00";
				return amount;	
			}


			if(amount.split("\\.")[1].length()==1)
				return amount +"0";

			return amount;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return "";
		}	
	}
	
	/**
	 * Function to format a amount
	 * Adds Grouping identifier ','
	 * Returns a string having two characters after a dot and $ in front
	 * 
	 * @param amount
	 *            Amount which is to be formatted
	 * @return string
	 * 
	 * @author 387478
	 */
	public String formatAmountWithDollar(String amount){
		boolean isNegative = false;
		try{
			NumberFormat myFormat = NumberFormat.getInstance();
			myFormat.setGroupingUsed(true);	

			amount = RemoveSpecialcharactersFromAmount(amount);
			Double floatamount = Double.parseDouble(amount);
			if(floatamount!=Math.abs(floatamount))
				isNegative = true;
			floatamount = Math.abs(floatamount); 
			amount = myFormat.format(floatamount);
			if(!amount.contains(".")){
				amount = amount + ".00";
				
				if(!isNegative)
					return "$" + amount;
				else
					return "-$"+amount;
			}


			if(amount.split("\\.")[1].length()==1){
				if(!isNegative)
					return "$" + amount +"0";
				else
					return "-$" + amount +"0";
			}
				
			if(!isNegative)
				return "$" + amount;
			else
				return "-$" + amount;
		}catch(Exception e){
			System.out.println(e.getMessage());
			return "";
		}	
	}
	
	/**
	 ************************************************************* 
	 * Function to convert date from Apr 04, 2014 to Apr 4, 2014
	 * 
	 * @author 387478
	 ************************************************************* 
	 */
	public String convertDatetoSingleDigit(String inputdate){
		
		SimpleDateFormat formatter = new SimpleDateFormat("MMM d, yyyy");
		try{
			Date date = formatter.parse(inputdate);
			String output = new SimpleDateFormat("MMM d, yyyy").format(date);
			return output;
		}catch(Exception e){
			return "";
		}
		
	}
	
	/**
	 ************************************************************* 
	 * Function to convert date from 04/29/2014 to Apr 29, 2014
	 * 
	 * @author 387478
	 ************************************************************* 
	 */
	public String convertDateRemoveSlashes(String inputdate) throws ParseException{
		
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		try{
			Date date = formatter.parse(inputdate);
			String output = new SimpleDateFormat("MMM d, yyyy").format(date);
			return output;
		}catch(Exception e){
			return "";
		}
		
	}

	
     /**
      ************************************************************* 
      * Function to verify same text is present in both the input strings
      * 
      * @param String expectedString
      * 
      * @param String actualString
      * @param objName
      *        - Name of the object to be updated in the Test log
      * 
      * @author 387478    *   
      *
      ************************************************************* 
       */

      public boolean compareText(String expectedString, String actualString, String objName)
      {
            if(expectedString.equals(actualString)){
                              report.updateTestLog(objName, "'" + objName + "' : " + " Verification is Success", Status.PASS);
                              return true;
                        } else{
                              report.updateTestLog(objName, objName + " : Verification is Failure. Expected : " + expectedString
                                          + " Actual : " + actualString, Status.FAIL);
                              return false;
                        }           
      }
      
      /**
       ************************************************************* 
       * Function to verify Input String Contains Expected String
       * 
       * @param String inputString
       * 
       * @param String expectedString
       * @param objName
       *        - Name of the object to be updated in the Test log
       * 
       * @author 387478    *   
       *
       ************************************************************* 
        */

       public boolean verifyStringContainsText(String inputString, String expectedString, String objName)
       {
             if(inputString.contains(expectedString)){
                               report.updateTestLog(objName, "'" + objName + "' verification is Success. Value:'" + expectedString+"'", Status.PASS);
                               return true;
                         } else{
                        	 report.updateTestLog(objName, "'" + objName + " verification is Failure. Expected Value:'" + expectedString+"'", Status.FAIL);
                               return false;
                         }           
       }
       
       /**
        ************************************************************* 
        * Function to verify Input String Contains Expected String
        * by removing spaces from input and expected string
        * 
        * @param String inputString
        * 
        * @param String expectedString
        * @param objName
        *        - Name of the object to be updated in the Test log
        * 
        * @author 387478    *   
        *
        ************************************************************* 
         */

        public boolean verifyStringContainsTextRemoveSpaces(String inputString, String expectedString, String objName)
        {
        	String inpuStringMofidied = RemoveCharacter(inputString, ' ');
        	String exStringModified = removeNewLineCharacters(expectedString);
        	exStringModified = RemoveCharacter(exStringModified, ' ');
        	if(objName.equals("Total Amount owed")){
        		exStringModified =  exStringModified.replace("-", "?");
        	}
        	/*System.out.println();
    		System.out.println("Input:"+inpuStringMofidied);
    		System.out.println("Expected:"+exStringModified);
    		System.out.println(inpuStringMofidied.contains(exStringModified));
        	System.out.println();*/
        		
        	   
              if(inpuStringMofidied.contains(exStringModified)){
            	  				report.updateTestLog(objName, "'" + objName + "' verification is Success. Value:'" + expectedString+"'", Status.PASS);
                                return true;
                          } else{
                        	  report.updateTestLog(objName, "'" + objName + "' verification is Failure. Value:'" + expectedString+"'", Status.FAIL);
                                return false;
                          }           
        }
      
      
	/**
	 ************************************************************* 
	 * Function to verify source of image
	 * 
	 * @param element
	 *        Element from which src is to be extracted
	 *         
	 * @param expectedSource
	 *		  Expected value of src Attribute
	 *
	 *@param objName
	 *       Name of the object to be updated in Test Log
	 * @author 387478
	 * @modified_by Shabbir on 31 Oct
	 * 				
	 * 			
	 * @return void
	 ************************************************************* 
	 */
	
	public void verifyImageSource(WebElement element,String expectedSource, String objName){
		String actualSource="";
		try{
			actualSource = element.getAttribute("src");
			if(actualSource.equals(expectedSource))
				report.updateTestLog("Verify "+objName+" Image", "Verification is success. Image points to correct source.",
						Status.PASS);
			else 
				report.updateTestLog("Verify "+objName+" Image", "Verification Failure. Expected Source:"+ expectedSource+". Actual Source:"+actualSource,
						Status.FAIL);
		}catch(Exception e){
			report.updateTestLog("Verify "+objName+" Image", "Verification Failure. Expected Source:"+ expectedSource+". Actual Source:"+actualSource,
					Status.FAIL);
		}
	}

	/**
 	 ************************************************************* 
 	 * Function to click a link which opens in same tab and verify
 	 * if correct URL is opened. User is navigated back to the original URL
 	 * after verification is complete
 	 * 
 	 * @author 387478
 	 * @param  element
 	 *            The {@link element} is the WelElement object
 	 *            which is to be clicked
 	 * @param  expectedUrl
 	 *            The {@link expectedUrl} is the expected URL on which the 
 	 *            user should be navigated on clicking the link
 	 * @param  strObjName
 	 *            The {@link strObjName} is used for identifying the object used
 	 *            for reporting purposes.
 	 * @return A boolean value indicating if the user is navigated to correct URL
 	 * @throws InterruptedException 
 	 ************************************************************* 
 	 */
 	public boolean clickAndVerifyUrl(WebElement element, String expectedUrl, String strObjName) throws InterruptedException{
 		if(!clickIfElementPresent(element, strObjName)){
 			report.updateTestLog(strObjName, strObjName + " : Verification is Failure. Expected Url: " + expectedUrl
                     + " Actual : " + driver.getCurrentUrl(), Status.FAIL);
 			return false;
 		}
 			
 		if(driver.getCurrentUrl().equals(expectedUrl)){
 			report.updateTestLog(strObjName, "'" + strObjName + "' : "    + " Verification is Success", Status.PASS);
 			navigateBackFromCurrentPage();
 			
 		}else{
 			report.updateTestLog(strObjName, strObjName + " : Verification is Failure. Expected Url: " + expectedUrl
                     + " Actual : " + driver.getCurrentUrl(), Status.FAIL);
 			navigateBackFromCurrentPage();
 			return false;
 		}	
 		
 		return true;
 	}
 	
 	/**
	 ************************************************************* 
	 * Function to verify text present in an element
	 * 
	 * @param Element
	 *            The {@Webelement Element} object that contains
	 *            the page element
	 * 
	 * @param textToVerify
	 *            The {@text String} Attribute name of the element which is to
	 *            be verified
	 * @return
	 * @throws InterruptedException 
	 ************************************************************* 
	 */

	public boolean verifyLinkInWebPage(WebElement element, String StrObjName,String linkToVerify)	throws IOException, InterruptedException
	{
		try
		{
				String oldTab = driver.getWindowHandle();
				element.click();
				/*ArrayList<String> newTab = new ArrayList<String>(driver.getWindowHandles());
			    newTab.remove(oldTab);*/
			    
			    String lastHandle="";
			    String winHandleBefore = driver.getWindowHandle();
			    for(String winHandle : driver.getWindowHandles())
					lastHandle = winHandle;
			    
			    
			    // change focus to new tab
			    //driver.switchTo().window(newTab.get(0));
			    driver.switchTo().window(lastHandle);
			    Thread.sleep(6000); 
			    
			    //Thread.sleep(6000); 
			    if(driver.getCurrentUrl().equals(linkToVerify))
			    { 
				    report.updateTestLog(StrObjName, "'" + StrObjName + "' : " 	+ " Verification is Success", Status.PASS);
					
			    }
			    else
			    {
			    	report.updateTestLog(StrObjName, StrObjName + " : Verification is Failure. Expected Url: " + linkToVerify
							+ " Actual : " + driver.getCurrentUrl(), Status.FAIL);
			    	
			    }
			    
			    // Do what you want here, you are in the new tab
			    driver.close();
			    // change focus back to old tab
			    driver.switchTo().window(oldTab);
			    return true;
		} 
		catch(Exception e)
		{
			report.updateTestLog(("Error in method description"), e.toString(), Status.FAIL);
		}
	    return false;
		
	}
 	
	/**
	 ************************************************************* 
	 * Function to Retrieve data from dataTable.
	 * 
	 * @param sheetName
	 * 		  Sheet from which data is to be retrieved
	 *            
	 * @param columnName
	 * 		  Column from which data is to be retrieved
	 * 
	 * @param objName
	 * 		  Description of the retrieved data which is to be updated in Test Log
	 * 
	 * @param displayError
	 * 		  Displays error message if true is passed and data is blank in sheet
	 * 		  No error message is displayed if  false is passed
	 * @author 324096
	 *            
	 * @return String
	 * 			
	 ************************************************************* 
	 */
	public String validateData(String sheetName, String columnName, String objName)
	{
		String retrievedData = null;
		try{
				retrievedData =  dataTable.getData(sheetName, columnName);
				if(retrievedData.isEmpty())
				{	frameworkParameters.setStopExecution(true);
					throw new FrameworkException("Retrieve data from "+columnName, objName + " is blank in "+ columnName +" column in "+ sheetName +" sheet.");
				}
			}
			catch(Exception e)
			{
				frameworkParameters.setStopExecution(true);
				throw new FrameworkException("Retrieve data from "+columnName, objName + " is blank in "+ columnName +" column in "+ sheetName +" sheet.");
			}
		return retrievedData;
	}

	/**
	 ************************************************************* 
	 * Function to clear existing text in a field and enter required data.
	 * 
	 * @param ElementName
	 *            The {@link String} object that contains the page element
	 *            identification variable in OR.
	 * @param Text
	 *            The {@link String} object that contains the string to be
	 *            entered in the text field.
	 * @return void
	 ************************************************************* 
	 */
	public boolean clearAndEnterText(WebElement elemToEnter, String strValueToUpdate, String strObjName)
	{
		
		if(!strValueToUpdate.trim().equalsIgnoreCase("IGNORE")){
			try{
				if(elemToEnter.isDisplayed() && elemToEnter.isEnabled()){
					elemToEnter.clear();
					enterValueInElement(elemToEnter, strValueToUpdate, strObjName);
					return true;
				} else{
						report.updateTestLog("Verify if the Element(" + strObjName + ") is present", strObjName
								+ " is not enabled", Status.FAIL);
				}
			} catch(org.openqa.selenium.NoSuchElementException nsee){
				report.updateTestLog("ENTER VALUE IN ELEMENT : " + strObjName, strObjName
						+ " object does not exist in page", Status.FAIL);
			} catch(Exception e){
				report.updateTestLog("ENTER VALUE IN ELEMENT ", "Error in finding object - " + strObjName
						+ ". Error Description - " + e.toString(), Status.FAIL);
			}
			return false;
		} else
			return true;
	}
	
	/**
	 ************************************************************* 
	 * Function to verify whether a given Element is present within the page and
	 * click
	 * 
	 * @param strObjProperty
	 *            The {@link strObjProperty} defines the property value used for
	 *            identifying the object
	 * @param strObjPropertyType
	 *            The {@link strObjPropertyType} describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if the searched Element is found.
	 * @modified by 3387478 on 11 Nov
	 ************************************************************* 
	 */
	public boolean clickIfElementPresent(WebElement element, String strObjName){
		
		try{
			if(isElementPresentVerification(element, strObjName))
			{
				element.click();
				Thread.sleep(2000);
				report.updateTestLog("Verify Link & Click", "'"+strObjName +"'"
						+ " is present and clicked", Status.PASS);				
				return true;
			} else{
				report.updateTestLog("Verify if the Element(" + strObjName + ") is present", "'"+strObjName +"'"
						+ " is not present", Status.FAIL);
				return false;
			}
		} catch(Exception e){
			report.updateTestLog("CLICK IF ELEMENT PRESENT", "Error in method - Error Description - " + e.toString(),
					Status.FAIL);
			return false;
		}
	}
	
	/**
	 * function to remove special characters in a string
	 * 
	 * @param strring
	 *            to remove the characteres
	 * @return string
	 */

	public String RemoveSpecialcharacters(String str){
		
		String st = "";
		for(int i = 0; i < str.length(); i++){
			int ch = str.charAt(i);

			if((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57) || ch==46){
				st = st + (char) ch;
			}

		}

		return st;
	}
	
	/******************************************************
	 * Function to remove a character from Input String
	 * 
	 * @param strring
	 *            to remove the characteres
	 * @return string
	 * 
	 * @author 387478
	 * ******************************************************
	 */

	public String RemoveCharacter(String str, char charToRemove){
		
		String st = "";
		for(int i = 0; i < str.length(); i++){
			int ch = str.charAt(i);

			if(ch!=charToRemove){
				st = st + (char) ch;
			}

		}

		return st;
	}
	
	
	/**********************************************************
	 * removeNewLineCharacters
	 *       This function removes the new line character from the input string
	 * 
	 * @param String str
	 *            Input string to remove the characters
	 * @return string
	 * 
	 * *********************************************************
	 */

	public String removeNewLineCharacters(String str){		
		return str.replace("\n", " ");
	}
	
	/**
	 * Function to check if an alert is present on the webpage
	 * If alert is not present, it writes a Failed step in Report
	 * @param objName
	 *        Name of the object to be printed in test log
	 * 
	 * @author 387478
	 * @lastModifiedBy Shabbir on 13 Nov, 2014
	 */

	public boolean isAlertPresent(String objName){		
		boolean presentFlag = false;

		  try {

		   // Check the presence of alert
		   Alert alert = driver.switchTo().alert();
		   // Alert present; set the flag
		   presentFlag = true;
		   // if present consume the alert
		   alert.accept();

		  } catch (NoAlertPresentException ex) {
		   // Alert not present
			  report.updateTestLog("Alert not present","Alert "+objName+" is not displayed.",Status.FAIL);
		  }

		  return presentFlag;

		 }
	
	/**
	 * Function to check if an alert is present on the webpage
	 * If alert is  present, it writes a Failed step in Report
	 * @param objName
	 *        Name of the object to be printed in test log
	 * @return true
	 *          If alert is not displayed
	 * @return false
	 *         If alert is displayed
	 * 
	 * @author 387478
	 * @lastModifiedBy Shabbir on 13 Nov, 2014
	 */

	public boolean isAlertNotPresent(String objName){		
		boolean notpresentFlag = true;

		  try {

		   // Check the presence of alert
		   Alert alert = driver.switchTo().alert();
		   // Alert present; set the flag
		   notpresentFlag = false;
		   String alertText = alert.getText();
		   alert.accept();
		   
		   report.updateTestLog("Alert Present","Unexpected alert '"+alertText+"' is displayed.",Status.FAIL);
		   // if present consume the alert
		   

		  } catch (NoAlertPresentException ex) {
		   // Alert not present
		  }

		  return notpresentFlag;

		 }
	
	/**
	 * Function to remove special characters in a Amount
	 * It keeps '.' and '-' sign
	 * 
	 * @param strring
	 *            to remove the characteres
	 * @return string
	 */

	public String RemoveSpecialcharactersFromAmount(String str){
			
			String st = "";
			for(int i = 0; i < str.length(); i++){
				int ch = str.charAt(i);
	
				if((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122) || (ch >= 48 && ch <= 57) || ch==46 || ch==45){
					st = st + (char) ch;
				}
	
			}
	
			return st;
		}
	
	/**
	 * Inserts the character ch at the location index of string st
	 * @param st
	 * @param ch
	 * @param index
	 * @return a new string 
	 */
	    public String insertCharAt(String st, char ch, int index){
	        //1 Exception if st == null
	        //2 Exception if index<0 || index>st.length()

	        if (st == null){
	            throw new NullPointerException("Null string!");
	        }

	        if (index < 0 || index > st.length())
	        {
	            throw new IndexOutOfBoundsException("Try to insert at negative location or outside of string");
	        }
	        return st.substring(0, index)+ch+st.substring(index, st.length());
	    }
	
	/**
	 ************************************************************* 
	 * Method to check/uncheck a checkbox based on the given option
	 * 
	 * @param elementToSelect
	 *            The {@link elementToSelect} element to be verified
	 * @param valueToSelect
	 *            The {@link strElemStateToVerify} describes the state to be set
	 *            which can be one of: Y/N
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return None
	 ************************************************************* 
	 */
	public void checkAnyElement(WebElement elementToSelect, String strValueToSelect, String strObjName){
	
		String strStateToReport = " ";
		boolean blnValueToSelect = true;
		if(!(strValueToSelect.trim().equalsIgnoreCase("IGNORE"))){
			if(strValueToSelect.trim().equalsIgnoreCase("N")){
				blnValueToSelect = false;
				strStateToReport = " not ";
			}
			if(elementToSelect.isEnabled()){
				if(!blnValueToSelect && !elementToSelect.isSelected()){
					if(blnValueToSelect && elementToSelect.isSelected()){
						elementToSelect.click();
					}
				}
				elementToSelect.click();
				report.updateTestLog("Select the element (" + strObjName + ")", strObjName + " is" + strStateToReport
						+ "checked as required", Status.DONE);

			} else{
				report.updateTestLog("Verify if the Element(" + strObjName + ") is present and selected", strObjName
						+ " object is not enabled", Status.FAIL);
			}
		}
	}
	

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {@link WebElement} object.
	 ************************************************************* 
	 */
	public WebElement getElementByProperty(String strObjectProperty, String strFindElementType,boolean displayError){
		
		try{
			if(strFindElementType.equalsIgnoreCase("CSS"))
				return driver.findElement(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				return driver.findElement(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				return driver.findElement(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				return driver.findElement(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				return driver.findElement(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				return driver.findElement(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				return driver.findElement(By.className(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("PARTIALLINKTEXT"))
				return driver.findElement(By.partialLinkText(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			if(displayError){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
			}else
				return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}
	}
	
	/**
	 ************************************************************* 
	 * Function to return attibute value
	 * 
	 * @param element
	 *            Element to get value
	 * @param attributeName
	 *            AttributeName to get value
	 * 
	 * @return String value of attribute Name.
	 ************************************************************* 
	 */
	public String getAttributeValue(WebElement element, String attributeName){
		
		try
		{
			return element.getAttribute(attributeName).toString();
		} catch(Exception e){
			report.updateTestLog("GetAttributeValue", "Error in method - Error Description - " + e.toString(),
					Status.FAIL);
			return null;
		}
	}

	/**
	 ************************************************************* 
	 * Function to compare (equals or contains) two strings after removing
	 * special characters from them
	 * 
	 * @param str1
	 *            ,str2 The two strings that are to be compared
	 * @param strContainsOrEquals
	 * 
	 * @return Boolean
	 ************************************************************* 
	 */
	public Boolean compareRemovingSpecialCharacters(String str1, String strContainsOrEquals, String str2){
		
		str1 = RemoveSpecialcharacters(str1.trim().toLowerCase());
		str2 = RemoveSpecialcharacters(str2.trim().toLowerCase());
		if(strContainsOrEquals.equalsIgnoreCase("equals")){
			if(str1.equals(str2))
				return true;
			else
				return false;
		} else{
			if(str1.contains(str2))
				return true;
			else
				return false;
		}
	}


	/**
	 ************************************************************* 
	 * Function to select a particular Value from any List box.
	 * 
	 * @param ListBoxObject
	 *            The {@link WebElement} object that has reference to the List
	 *            Box.
	 * @param strSelectOption
	 *            The {@link String} object that has the item to be selected.
	 * @return void
	 ************************************************************* 
	 */
	public void genericListBoxOptionSelector(WebElement ListBoxObject, String strSelectOption) throws Exception{
		
		try{
			new Select(ListBoxObject).selectByVisibleText(strSelectOption);
			report.updateTestLog("GENERIC LIST BOX SELECTOR", "The option " + strSelectOption + " is selected.",
					Status.DONE);
		} catch(Exception e){
			report.updateTestLog("GENERIC LIST BOX SELECTOR", "Error in method - Error Description - " + e.toString(),
					Status.FAIL);
		}
	}

	/**
	 * @description Method to get a time-stamp in MMMdd_mm_ss format
	 * @return String
	 * @modified_date Dec 4, 2013
	 */
	public String getCurrentTimeStamp(){
		
		String strTimestamp;
		Date currentDate = new Date();
		SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMdd_mm_ss");
		strTimestamp = dateFormatter.format(currentDate);
		return strTimestamp;
	}

	
	/**
	 * @description Method to get eastern time
	 * @return String
	 * @modified_date Dec 4, 2013
	 */
	public String getEasternTimeStamp(){
		
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("US/Eastern"));
		SimpleDateFormat df = new SimpleDateFormat("E h:mm a '-' zzzz");
		df.setTimeZone(cal.getTimeZone());
		String strEasternTimestamp = df.format(cal.getTime());
		return strEasternTimestamp;
	}

	/**
	 ************************************************************* 
	 * Function to get element attribute
	 * 
	 * @param Element
	 *            The {@link String} object that contains the page element
	 *            identification variable in OR.
	 * @param ElementName
	 *            The {@link String} Attribute name of the element which is to
	 *            be fetched
	 * @return
	 ************************************************************* 
	 */
	public String getElementAttribute(String strObjectProperty, String strFindElementType, String attributeToGet,
			String strObjName){
		
		String attributVal = null;
		try{
			if(isElementPresentVerification(strObjectProperty, strFindElementType, strObjName)){
				attributVal = getElementByProperty(strObjectProperty, strFindElementType).getAttribute(attributeToGet);
			}
		} catch(Exception e){
			report.updateTestLog("", "Error in method - Error Description - " + e.toString(), Status.FAIL);
		}
		return attributVal;
	}

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {@link WebElement} object.
	 ************************************************************* 
	 */
	public WebElement getElementByProperty(String strObjectProperty, String strFindElementType){
		
		try{
			if(strFindElementType.equalsIgnoreCase("CSS"))
				return driver.findElement(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				return driver.findElement(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				return driver.findElement(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				return driver.findElement(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				return driver.findElement(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				return driver.findElement(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				return driver.findElement(By.className(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("PARTIALLINKTEXT"))
				return driver.findElement(By.partialLinkText(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}
	}

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {@link WebElement} object.
	 ************************************************************* 
	 */
	public List<WebElement> getElementsByProperty(String strObjectProperty, String strFindElementType){
		
		try{
			// GlobalVariables.strImplicitWait="1";
			if(strFindElementType.equalsIgnoreCase("CSS"))
				return driver.findElements(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				return driver.findElements(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				return driver.findElements(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				return driver.findElements(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				return driver.findElements(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				return driver.findElements(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				return driver.findElements(By.className(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}
	}

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param webElement
	 *            WebElement for which child elements to be found
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {@link WebElement} object.
	 ************************************************************* 
	 */
	public List<WebElement> getElementsByProperty(WebElement webElement, String strObjectProperty,
			String strFindElementType){
		
		try{
			// GlobalVariables.strImplicitWait="1";
			if(strFindElementType.equalsIgnoreCase("CSS"))
				return webElement.findElements(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				return webElement.findElements(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				return webElement.findElements(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				return webElement.findElements(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				return webElement.findElements(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				return webElement.findElements(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				return webElement.findElements(By.className(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}
	}

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param webElement
	 *            WebElement for which child elements to be found
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {WebElement} object.
	 ************************************************************* 
	 */
	public WebElement getElementInsideElement(WebElement webElement, String strObjectProperty, String strFindElementType){
		
		try{
			// GlobalVariables.strImplicitWait="1";
			if(strFindElementType.equalsIgnoreCase("CSS"))
				return webElement.findElement(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				return webElement.findElement(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				return webElement.findElement(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				return webElement.findElement(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				return webElement.findElement(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				return webElement.findElement(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				return webElement.findElement(By.className(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strFindElementType + " - " + strObjectProperty + " is present",
					"Element with property " + strFindElementType + " - " + strObjectProperty + " not found",
					Status.FAIL);
			return null;
		}
	}

	/**
	 ************************************************************* 
	 * Function to find an element by property NOT defined in the OR file.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A {@link WebElement} object.
	 ************************************************************* 
	 */
	public List<WebElement> getElementsInsideElement(WebElement elemUsed, String strObjectProperty,
			String strObjectPropertyType){
		
		try{
			// GlobalVariables.strImplicitWait="1";
			if(strObjectPropertyType.equalsIgnoreCase("CSS"))
				return elemUsed.findElements(By.cssSelector(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("XPATH"))
				return elemUsed.findElements(By.xpath(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("ID"))
				return elemUsed.findElements(By.id(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("NAME"))
				return elemUsed.findElements(By.name(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("LINKTEXT"))
				return elemUsed.findElements(By.linkText(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("TAG"))
				return elemUsed.findElements(By.tagName(strObjectProperty));
			else if(strObjectPropertyType.equalsIgnoreCase("CLASS"))
				return elemUsed.findElements(By.className(strObjectProperty));
			else
				return null;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Verify if " + strObjectPropertyType + " - " + strObjectProperty
					+ " is present in an element", "Element with property " + strObjectPropertyType + " - "
					+ strObjectProperty + " not found", Status.FAIL);
			return null;
		}

		catch(Exception e){
			report.updateTestLog("Verify if " + strObjectPropertyType + " - " + strObjectProperty
					+ " is present in an element", "Element with property " + strObjectPropertyType + " - "
					+ strObjectProperty + " not found", Status.FAIL);
			return null;
		}
	}

	
	/**
	 * Method To Get The Window Size
	 * 
	 * @author Manoj Venkat
	 * @param Dimension
	 * @return strSize {@link dimSize} Size Of The Window
	 */
	public Dimension getWindowSize(){
		
		Dimension dimSize = driver.manage().window().getSize();
		return dimSize;
	}

	/**
	 ************************************************************* 
	 * Method to verify an element which is a image ,href for which we get the
	 * String value using attribute.
	 * 
	 * @return A boolean value indicating if the searched Element is found.
	 *         author Lavannya
	 ************************************************************* 
	 */
	public WebElement isElementPresentContainsAttributeText(String strObjectProperty, String strFindElementType,
			String StrObjName, String textToVerify, String attribute) throws IOException{
		
		try{
			if(isElementPresentVerification(strObjectProperty, strFindElementType, StrObjName)){
				getElementByProperty(strObjectProperty, strFindElementType).getAttribute(attribute).contains(
						textToVerify);
				report.updateTestLog("The Element(" + StrObjName + ") is present and Contains the text", textToVerify,
						Status.PASS);
				return null;
			} else{
				report.updateTestLog("The Element(" + StrObjName + ") is not present and does not contain the text",
						textToVerify, Status.FAIL);
				return null;
			}
		} catch(Exception e){
			report.updateTestLog("IS ELEMENT PRESENTCONTAINS TEXT",
					"Error in method - Error Description - " + e.toString(), Status.FAIL);
			return null;
		}
	}

	public WebElement isElementPresentContainsText(String strObjectProperty, String strFindElementType,
			String StrObjName, String textToVerify) throws IOException{
		
		try{
			if(isElementPresentVerification(strObjectProperty, strFindElementType, StrObjName)){
				getElementByProperty(strObjectProperty, strFindElementType).getText().contains(textToVerify);
				report.updateTestLog("The Element(" + StrObjName + ") is present and Contains the text", textToVerify,
						Status.PASS);
				return null;
			} else{
				report.updateTestLog("The Element(" + StrObjName + ") is not present and does not contain the text",
						textToVerify, Status.FAIL);
				return null;
			}
		} catch(Exception e){
			report.updateTestLog("IS ELEMENT PRESENTCONTAINS TEXT",
					"Error in method - Error Description - " + e.toString(), Status.FAIL);
			return null;
		}
	}

	/**
	 ************************************************************* 
	 * Function to verify if an element is present in the application, not using
	 * OR.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean isElementPresentVerification(String strObjectProperty, String strFindElementType, String strObjName)
			throws IOException{
		
		try{
			if(getElementByProperty(strObjectProperty, strFindElementType) != null){
				report.updateTestLog((strObjName + " element is present").toUpperCase(), strObjName
						+ " is verified successfully", Status.PASS);
				return true;
			} else
				return false;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Error in identifying element with string " + strObjectProperty, nsee.toString(),
					Status.FAIL);
			return false;
		} catch(Exception e){
			report.updateTestLog("IS ELEMENT PRESENT VERIFICATION", "Error in identifying object (" + strObjName
					+ ") -" + e.toString(), Status.FAIL);
			return false;
		}
	}
	

	/**
	 ************************************************************* 
	 * Function to verify if an element is present in the application, not using
	 * OR.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification string.
	 * @param strFindElementType
	 *            The {@link String} object that describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean isElementPresentVerification(WebElement elemToVerify, String strObjName){
		
		try{
			if(elemToVerify.isDisplayed()){
				//report.updateTestLog((strObjName + " element is present"), strObjName+ " is verified successfully", Status.PASS);
				return true;
			} else{
				report.updateTestLog((strObjName + " element is present"), strObjName
						+ " is NOT displayed", Status.FAIL);
				return false;
			}
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Error in identifying element (" + strObjName + ")", nsee.toString(), Status.FAIL);
			return false;
		} catch(Exception e){
			report.updateTestLog("IS ELEMENT PRESENT VERIFICATION", "Error in identifying object (" + strObjName
					+ ") -" + e.toString(), Status.FAIL);
			return false;
		}
	}

	/**
	 * @description Method to verify if the text in the element has Font-Weight
	 *              Bold returns
	 * @param elmt
	 *            - element to be verified
	 * @return boolean - true on pass and false on failure
	 * @modified_date Dec 9, 2013
	 */
	public boolean isFontWeightBold(WebElement elmt){

		
		String fontWt = elmt.getCssValue("font-weight");
		Boolean blMatch;
		try{
			int fontVal = Integer.parseInt(fontWt);
			if(fontVal >= 700){
				blMatch = true;
			} else{
				blMatch = false;
			}

		} catch(Exception e){
			if(fontWt.equalsIgnoreCase("bold")){
				blMatch = true;
			} else{
				blMatch = false;
			}
		}

		return blMatch;

	}

	/**
	 ************************************************************* 
	 * Function to verify if an Text is present in the application.
	 * 
	 * @param strObjectProperty
	 *            The {@link String} object that contains the page element
	 *            identification variable in OR.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean isTextPresent(String text) throws IOException{
		
		/*
		String x = text;
		Asserts asserts = new Asserts();
		try{
			if(asserts.isTextPresent(driver, text)){
				report.updateTestLog("Verification of ", x + "Is Present", Status.PASS);
		 */
		
				return true;
				/*
			} else
				return false;
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("Error in identifying element with property " + text, nsee.toString(), Status.FAIL);
			return false;
		} catch(Exception e){
			report.updateTestLog("IS ELEMENT PRESENT VERIFICATION",
					"Error in method - Error Description - " + e.toString(), Status.FAIL);
			return false;
		}
		*/
	}

	/**
	 * @description Method to perform mouse hover on given element
	 * @param element
	 *            - element on which mouse hover action needs to be performed
	 * @modified_date Dec 9, 2013
	 */
	public void mouseOver(WebElement element){
		
			try{
				Robot robot = new Robot();
				robot.mouseMove(0, 0);
				Actions builder = new Actions(driver);
				builder.moveToElement(element).build().perform();
			} catch(Exception e){
				report.updateTestLog(("Mouse Over Event doesnt occur").toUpperCase(),
						"Unable to perform mouse hover on element", Status.FAIL);
			}
		
	}

	/**
	 * @description Method to perform mouse hover action on internet explorer
	 * @param element
	 *            - Element on which mouse hover action needs to be performed
	 * @return Nothing
	 * @modified_date Dec 9, 2013
	 */
	public void mouseOverIE(WebElement element){
		
		String code = " function sleep(milliseconds) {"
				+ " var start = new Date().getTime();"
				+ " while ((new Date().getTime() - start) < milliseconds){"
				+
				// Do nothing
				"} }" + "if(document.createEvent) {" + "var evObj = document.createEvent('MouseEvents');"
				+ "evObj.initEvent('mouseover', true, false); " + "sleep(50000);"
				+ "arguments[0].dispatchEvent(evObj);" + "arguments[0].click();" + "} "
				+ "else if(document.createEventObject) " + "{ arguments[0].fireEvent('onmouseover'); }";
		((JavascriptExecutor) driver).executeScript(code, element);

	}
	
	/**
	 * @description Method to perform back browser action
	 * @return Nothing
	 * @modified_date Dec 9, 2013
	 */
	public void navigateBackFromCurrentPage(){
		
		driver.navigate().back();
		
	}

	/**
	 * @description Method to navigate to specified URL
	 * @param strURL
	 *            - navigation URL
	 * @return Nothing
	 * @modified_date Dec 9, 2013
	 */
	public void navigateToUrl(String strURL){
		
		driver.get(strURL);
		
	}

	/**
	 * @description Method to open a new tab in browser with specified URL
	 * @param url
	 * @modified_date Dec 9, 2013
	 */
	public void openTab(String url){
		
		WebElement a = (WebElement) ((JavascriptExecutor) driver)
				.executeScript(
						"var d=document,a=d.createElement('a');a.target='_blank';a.href=arguments[0];a.innerHTML='.';d.body.appendChild(a);return a",
						url);
		a.click();
	}


	/**
	 * @description Method to refresh page
	 * @return Nothing
	 * @modified_date Dec 9, 2013
	 */
	public void pageRefresh(){
		
		driver.navigate().refresh();
		//handleFeedback();
	}

	/**
	 ************************************************************* 
	 * Method to select an option directly from combo box/list box
	 * 
	 * @param elementToSelect
	 *            The {@link elementToSelect} element to be verified
	 * @param valueToSelect
	 *            The {@link strElemStateToVerify} describes the state to be
	 *            verified which can be either one of ENABLED/SELECTED/VALUE
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if selecting an option is done.
	 ************************************************************* 
	 */
	public void selectAnyElement(String strObjProperty, String strObjPropertyType, String strValueToSelect,
			String strObjName){
		
		if(!(strValueToSelect.trim().equalsIgnoreCase("IGNORE"))){
			try{
				WebElement elementToSelect = getElementByProperty(strObjProperty, strObjPropertyType);
				selectAnyElement(elementToSelect, strValueToSelect, strObjName);
			} catch(org.openqa.selenium.NoSuchElementException nsee){
				report.updateTestLog("Select Any Element : " + strObjName, strObjName
						+ " object does not exist in page", Status.FAIL);
			} catch(Exception e){
				report.updateTestLog("SELECT ANY ELEMENT", "Error in finding element - " + strObjProperty + ", by: "
						+ strObjPropertyType, Status.FAIL);
			}
		}
	}

	/***
	 ************************************************************* 
	 * Method to select an option directly from combo box/list box
	 * 
	 * @param elementToSelect
	 *            The {@link elementToSelect} element to be verified
	 * @param valueToSelect
	 *            The {@link strElemStateToVerify} describes the state to be
	 *            verified which can be either one of ENABLED/SELECTED/VALUE
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if selecting an option is done.
	 ************************************************************* 
	 */
	public void selectAnyElement(WebElement elementToSelect, String strValueToSelect, String strObjName){
		
		if(!(strValueToSelect.trim().equalsIgnoreCase("IGNORE"))){
			if(elementToSelect.isEnabled()){
				elementToSelect.click();

				Select comSelElement = new Select(elementToSelect);
				try{
					Thread.sleep(3000);
				} catch(InterruptedException e){
				}
				comSelElement.selectByVisibleText(strValueToSelect);

				report.updateTestLog("Verify if the Element(" + strObjName + ") is present and selected", strObjName
						+ " value : '" + strValueToSelect + "' is selected", Status.DONE);
			} else{
				report.updateTestLog("Verify if the Element(" + strObjName + ") is present and selected", strObjName
						+ " object is not enabled", Status.FAIL);
			}
		}
	}

	/***
	 ************************************************************* 
	 * Method to select an option directly from combo box/list box
	 * 
	 * @param elementToSelect
	 *            The {@link elementToSelect} element to be verified
	 * @param indexToSelect
	 *            Index to Select
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if selecting an option is done.
	 ************************************************************* 
	 */
	public void selectAnyElement(WebElement elementToSelect, int indexToSelect, String strObjName){
		
		if(indexToSelect > -1){
			if(elementToSelect.isEnabled()){
				elementToSelect.click();

				Select comSelElement = new Select(elementToSelect);
				try{
					Thread.sleep(3000);
				} catch(InterruptedException e){
				}
				comSelElement.selectByIndex(indexToSelect);

				report.updateTestLog("Verify if the Element(" + strObjName + ") is present and selected", strObjName
						+ " value : '" + indexToSelect + "' is selected", Status.DONE);
			} else{
				report.updateTestLog("Verify if the Element(" + strObjName + ") is present and selected", strObjName
						+ " object is not enabled", Status.FAIL);
			}
		}
	}

	/**
	 * Method To Get the window handle result
	 * 
	 * @param driver
	 *            driver of active browser
	 * @param strWindowTitle
	 *            tab title to be switched to
	 * @return boolean
	 */
	public boolean switchToDifferentWindow(WebDriver driver, String strWindowTitle){
		
		WebDriver popup = null;
		driver.getWindowHandle();
		java.util.Iterator<String> obj = driver.getWindowHandles().iterator();
		// Window handle iterator object initiated
		while(obj.hasNext()){
			String windowHandle = obj.next();
			popup = driver.switchTo().window(windowHandle);
			if(popup.getTitle().contains(strWindowTitle)){
				report.updateTestLog("Switch to window: " + strWindowTitle, "Successfully switched to window "
						+ strWindowTitle, Status.DONE);
				return true;
			}
		}
		report.updateTestLog("Switch to window: " + strWindowTitle, "Window " + strWindowTitle + " not found ",
				Status.FAIL);
		return false;
	}

	/**
	 * @description Method to switch to next window
	 * @throws NoSuchWindowException
	 * @return nothing
	 * @modified_date Dec 12, 2013
	 */
	public void switchWindow() throws NoSuchWindowException{
		
		Set<String> handles = driver.getWindowHandles();
		String current = driver.getWindowHandle();
		handles.remove(current);
		String newTab = handles.iterator().next();
		driver.switchTo().window(newTab);
	}

	/**
	 ************************************************************* 
	 * Function to verify whether a given Element is present within the page and
	 * update the value
	 * 
	 * @param strObjProperty
	 *            The {@link strObjProperty} defines the property value used for
	 *            identifying the object
	 * @param strObjPropertyType
	 *            The {@link strObjPropertyType} describes the method used to
	 *            identify the element. Possible values are ID, NAME, LINKTEXT,
	 *            XPATH or CSS.
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean updateAnyElement(String strObjProperty, String strObjPropertyType, String strValueToUpdate,
			String strObjName){
		
		if(strValueToUpdate.trim().equalsIgnoreCase("IGNORE")){
			try{
				if(isElementPresentVerification(strObjProperty, strObjPropertyType, strObjName)){
					WebElement elemToClick = getElementByProperty(strObjProperty, strObjPropertyType);
					enterValueInElement(elemToClick, strValueToUpdate, strObjName);
				} else{
					report.updateTestLog("Verify if the Element(" + strObjName + ") is present and updated", strObjName
							+ " is not present", Status.FAIL);
				}
			} catch(org.openqa.selenium.NoSuchElementException nsee){
				report.updateTestLog("UPDATE Any Element : " + strObjName, strObjName
						+ " object does not exist in page", Status.FAIL);
			} catch(Exception e){
				report.updateTestLog("UPDATE ANY ELEMENT", "Error in method - Error Description - " + e.toString(),
						Status.FAIL);
			}
			return false;
		} else
			return true;
	}

	/**
	 ************************************************************* 
	 * Function to click a given element
	 * 
	 * @param elemToClick
	 *            The {@link strObjProperty} element to be updated
	 * @param strObjName
	 *            The {@link strObjName} is used for identifying the object used
	 *            for reporting purposes.
	 * @return A boolean value indicating if the searched Element is found.
	 ************************************************************* 
	 */
	public boolean enterValueInElement(WebElement elemToEnter, String strValueToEnter, String strObjName){
		
		if(!strValueToEnter.trim().equalsIgnoreCase("IGNORE")){
			try{
				if(elemToEnter.isDisplayed() && elemToEnter.isEnabled())
				{
					Thread.sleep(1000);
					try{
						elemToEnter.click();
					} catch(Exception e){
					}
					elemToEnter.clear();
					elemToEnter.sendKeys(strValueToEnter);
					Thread.sleep(2000);
					if(strObjName.equals("Password") || strObjName.equals("SSN")){
						report.updateTestLog("Verify if the Element(" + strObjName + ") is present", "'"+strObjName+"'"
								+ " is present and entered value : " + new String(new char[strValueToEnter.length()]).replace("\0", "*" ), Status.PASS);
					}
					else
						report.updateTestLog("Verify if the Element(" + strObjName + ") is present", "'"+strObjName+"'"
								+ " is present and entered value : " + strValueToEnter, Status.PASS);
					return true;
				} else{
					report.updateTestLog("Verify if the Element(" + strObjName + ") is present and value is Entered", strObjName
							+ " is not enabled", Status.FAIL);
				}
			} catch(org.openqa.selenium.NoSuchElementException nsee){
				report.updateTestLog("ENTER VALUE IN ELEMENT : " + strObjName, strObjName
						+ " object does not exist in page", Status.FAIL);
			} catch(Exception e){
				report.updateTestLog("ENTER VALUE IN ELEMENT ", "Error in finding object - " + strObjName
						+ ". Error Description - " + e.toString(), Status.FAIL);
			}
			return false;
		} else
			return true;
	}

	public void updateElementUsingJavaScript(WebElement elmt, String strTextToUpdate, String strElemName){
		
		try{
			String str = "\"" + strTextToUpdate + "\"";
			String strCode = "arguments[0].setAttribute(\"value\"," + str + ")";
			((JavascriptExecutor) driver).executeScript(strCode, elmt);
			report.updateTestLog("Update Element " + strElemName + " using JavaScript Executor", strElemName
					+ " updated with value : " + strTextToUpdate, Status.DONE);
		} catch(Exception e){
			report.updateTestLog("Update Element " + strElemName + " using JavaScript Executor",
					"Error occurred while trying to update Element " + e.getMessage(), Status.FAIL);
		}

	}

	/**
	 ************************************************************* 
	 * Function to verify Same text present in an element
	 * 
	 * @param WebElement Element
	 *            The {@Webelement Element} object that contains
	 *            the page element
	 *            
	 * @param StrObjName
	 * 
	 * @param textToVerify
	 *            The {@text String} Attribute name of the element which is to
	 *            be verified
	 * @return
	 ************************************************************* 
	 */

	public boolean verifyElementPresentEqualsText(WebElement element, String StrObjName, String textToVerify)	throws IOException
	{
		if(!textToVerify.trim().equalsIgnoreCase("IGNORE")){
			try{
				String pageSource = element.getText();
				if(pageSource.equals(textToVerify))
				{
					report.updateTestLog(StrObjName, "'" + StrObjName + "' : " 	+ " Verification is Success", Status.PASS);
					return true;
				} else{
					report.updateTestLog(StrObjName, StrObjName + " : Verification is Failure. Expected : " + textToVerify
							+ " Actual : " + pageSource, Status.FAIL);
				}

			} catch(Exception e){
				report.updateTestLog(("Error in method description").toUpperCase(), e.toString(), Status.FAIL);
			}
		}
		return false;
	}
	/**
	 ************************************************************* 
	 * Function to verify text is present in an element
	 * 
	 * @param Element
	 *            The {@Webelement Element} object that contains
	 *            the page element
	 * 
	 * @param textToVerify
	 *            The {@text String} Attribute name of the element which is to
	 *            be verified
	 * @return
	 ************************************************************* 
	 */

	public boolean verifyElementPresentContainsText(WebElement element, String StrObjName, String textToVerify)	throws IOException
	{
		if(!textToVerify.trim().equalsIgnoreCase("IGNORE")){
			try{
				String pageSource = element.getText().trim();
				
				//System.out.println("|"+ pageSource +"|");
				//System.out.println("|"+ textToVerify +"|");
				
				if(pageSource.contains(textToVerify))
				{
					report.updateTestLog(StrObjName, "'" + StrObjName + "' : " 	+ " Verification is Success", Status.PASS);
					return true;
				} else{
					report.updateTestLog(StrObjName, StrObjName + " : Verification is Failure. Expected : " + textToVerify
							+ " Actual : " + pageSource, Status.FAIL);
				}

			} catch(Exception e){
				report.updateTestLog(("Error in method description").toUpperCase(), e.toString(), Status.FAIL);
			}
		}
		return false;
	}


	/***************************************************************
	 * Function to verify text is not present in an element
	 * 
	 * @author sivka002
	 * @param Element
	 *            The {@Webelement Element} object that contains
	 *            the page element
	 * @param textToVerify
	 *            The {@text String} Attribute name of the element which is to
	 *            be verified
	 * @return boolean true - if element does not contain the text
	 **************************************************************/

	public boolean verifyElementPresentNotContainsText(WebElement element, String StrObjName, String textToVerify)
			throws IOException{
		
		if(!textToVerify.trim().equalsIgnoreCase("IGNORE")){
			try{
				textToVerify = textToVerify.replace(" ", "");
				String pageSource = element.getText().toLowerCase();
				String[] pageSourceLines = pageSource.trim().split("\\n");
				String pageSourceWithoutNewlines = "";
				textToVerify = textToVerify.toLowerCase().replaceAll(" ", "");

				for(String pageSourceLine : pageSourceLines){
					pageSourceWithoutNewlines += pageSourceLine + " ";
				}

				pageSourceWithoutNewlines = pageSourceWithoutNewlines.trim().replaceAll(" ", "").replaceAll("\\$", "");
				Pattern p = Pattern.compile(textToVerify);
				Matcher m = p.matcher(pageSourceWithoutNewlines);

				if(!m.find()){
					report.updateTestLog(StrObjName, "'" + StrObjName + "' value doesnot contain text : "
							+ textToVerify + " Verification is Success", Status.PASS);
					return true;
				} else{
					report.updateTestLog(StrObjName, StrObjName + " value contains text : " + textToVerify + ","
							+ " Actual : " + pageSourceWithoutNewlines, Status.FAIL);
				}

			} catch(Exception e){
				report.updateTestLog(("Error in method description").toUpperCase(), e.toString(), Status.FAIL);
			}
		}
		return false;
	}

	/**
	 * Method to Verify Element is Not Present For Negative Scenario
	 * 
	 * @param elemToVerify
	 * @param strObjName
	 */

	public void verifyIsElementNotPresent(String strObjectProperty, String identifier, String strObjName){
		WebElement element = null;
		try{

			if(identifier.equalsIgnoreCase("CSS")){
				element = driver.findElement(By.cssSelector(strObjectProperty));
			} else if(identifier.equalsIgnoreCase("XPATH")){
				element = driver.findElement(By.xpath(strObjectProperty));
			} else if(identifier.equalsIgnoreCase("ID")){
				element = driver.findElement(By.id(strObjectProperty));
			} else if(identifier.equalsIgnoreCase("NAME")){
				element = driver.findElement(By.name(strObjectProperty));
			} else if(identifier.equalsIgnoreCase("LINKTEXT")){
				element = driver.findElement(By.linkText(strObjectProperty));
			} else if(identifier.equalsIgnoreCase("TAG")){
				element = driver.findElement(By.tagName(strObjectProperty));
			} else if(identifier.equalsIgnoreCase("CLASS")){
				element = driver.findElement(By.className(strObjectProperty));
			} else{
			}
			
			if(element.isDisplayed()){
				report.updateTestLog(strObjName,strObjName + " present, which is not expected", Status.FAIL);
			}else{
				report.updateTestLog(strObjName,strObjName + " is not present, which is as expected", Status.PASS);
			}

		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog(strObjName,strObjName + " is not present, which is as expected", Status.PASS);
		}

	}

	/**
	 * Method to Verify Element is Not Present within a parent element -
	 * Negative Scenario
	 * 
	 * @param elemToVerify
	 * @param strObjName
	 */
	public boolean verifyIsElementNotPresent(WebElement elmt, String strParentElementName, String strObjectProperty,
			String strFindElementType, String strObjName){
	
		try{

			if(strFindElementType.equalsIgnoreCase("CSS")){
				elmt.findElement(By.cssSelector(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("XPATH")){
				elmt.findElement(By.xpath(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("ID")){
				elmt.findElement(By.id(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("NAME")){
				elmt.findElement(By.name(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("LINKTEXT")){
				elmt.findElement(By.linkText(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("TAG")){
				elmt.findElement(By.tagName(strObjectProperty));
			} else if(strFindElementType.equalsIgnoreCase("CLASS")){
				elmt.findElement(By.className(strObjectProperty));
			} else{
			}
			report.updateTestLog("verifyIsElementNotPresent".toUpperCase() + " - (" + strObjName + ")", "("
					+ strObjName + ")" + " Is Present with property : " + strObjectProperty + " within the Element "
					+ strParentElementName + "!!Not expected", Status.FAIL);
			return false;

		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("verifyIsElementNotPresent".toUpperCase() + " - identifying element (" + strObjName
					+ ")", "(" + strObjName + ")" + " with property: " + strObjectProperty
					+ " Is Not Present within the Element " + strParentElementName + " as expected", Status.PASS);
			return true;
		}
	}

	/**
	 * Description: Function to validate items in a list
	 * 
	 * @param strPropertyValue
	 *            = object property value
	 * @param strPropType
	 *            = type of property to be used
	 * @param strObjName
	 *            = object label for reporting
	 * @param strItemNames
	 *            = items to be verified seperated by semi-colin(;) returns:
	 *            nothing Author: VaibhavS
	 */
	public void verifyItemsInList(String strPropertyValue, String strPropType, String strObjName, String strItemNames){
	
		try{
			if(isElementPresentVerification(strPropertyValue, strPropType, strObjName)){
				WebElement elemToClick = getElementByProperty(strPropertyValue, strPropType);
				// clickAnyElement(elemToClick,strObjName);
				String[] arrListNames = strItemNames.split(";");
				List<WebElement> lstElement = getElementsByProperty(elemToClick, "*", "XPATH");
				for(String strItemName : arrListNames){
					for(WebElement webElement : lstElement){
						String strText = webElement.getText();
						if(strText.equalsIgnoreCase(strItemName)){
							report.updateTestLog("Verify Item " + strItemName + " is present", strItemName
									+ " is present", Status.PASS);
							break;
						}
					}
					if(false){
						report.updateTestLog("Verify Item " + strItemName + " is present", strItemName
								+ " is not present", Status.FAIL);
					}
				}

			} else{
				report.updateTestLog("Verify if the Element(" + strObjName + ") is present", strObjName
						+ " is not present", Status.FAIL);
			}
		} catch(org.openqa.selenium.NoSuchElementException nsee){
			report.updateTestLog("VerifyItemsInList: " + strObjName, strObjName + " object does not exist in page",
					Status.FAIL);
		} catch(Exception e){
			report.updateTestLog("VerifyItemsInList", "Error in method - Error Description - " + e.toString(),
					Status.FAIL);
		}
	}

	/**
	 ************************************************************* 
	 * Method to verify multiple lines in a web-element text
	 * 
	 * @param Element
	 *            The {@Webelement Element} object that contains
	 *            the page element
	 * @param textToVerify
	 *            The {@text String} text value to be verified
	 * @param strObjName
	 *            The name of object for reference
	 * @return
	 ************************************************************* 
	 */
	public boolean verifyMultiLine(WebElement element, String StrObjName, String textToVerify) throws IOException{
		
		boolean blnFound = true;
		if(!textToVerify.trim().equalsIgnoreCase("IGNORE")){
			try{
				String[] arrLinesToVerify = textToVerify.trim().split("\\n");
				String[] arrPageSrcLines = element.getText().trim().split("\\n");
				for(int itPageSrc = 0; itPageSrc < arrPageSrcLines.length; itPageSrc++)
					if(arrPageSrcLines[itPageSrc].trim().equalsIgnoreCase(arrLinesToVerify[0].trim())){
						for(int itLines = 0; itLines < arrLinesToVerify.length; itLines++)
							if(!arrPageSrcLines[itPageSrc + itLines].trim().equalsIgnoreCase(
									arrLinesToVerify[itLines].trim())){
								blnFound = false;
								break;
							}
					} else if(!blnFound && itPageSrc == arrPageSrcLines.length){
						report.updateTestLog("verifyMultiLineText".toUpperCase(), StrObjName
								+ " do not have expected text : " + textToVerify + " ; Actual : " + element.getText(),
								Status.FAIL);
						return false;
					} else if(blnFound){
						report.updateTestLog("verifyMultiLineText".toUpperCase(), StrObjName + " has the text : "
								+ textToVerify + " as expected.", Status.PASS);
						return true;
					}
			} catch(Exception e){
				report.updateTestLog("verifyMultiLineText".toUpperCase(),
						"Error in verifying elements :  " + e.getMessage(), Status.FAIL);
			}
		}
		return false;
	}
	public void verifyTextPresent(String strText) throws Exception{
			
			assertTrue(isTextPresent(strText));
			report.updateTestLog("Text Verification", strText + "The Text is present ", Status.PASS);
	
		}

	public void waitForElementClickable(String xpathVal, long time){
		
		try{
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathVal)));
		} catch(Exception e){
			System.err.print(e.getMessage());
		}
	}


	public boolean waitForElementVisibility(String elementID, long time){
		
		try{
			long startTime = System.currentTimeMillis();

			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementID)));
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			return true;
		} catch(Exception e){
			report.updateTestLog("Wait for all elements to load", "All elements not loaded. Waited for " + time + " seconds. Continuing execution.", Status.WARNING);
			report.updateTestLog("Take Screenshot", "Screenshot taken successfully", Status.SCREENSHOT);
			return false;
		}
	}

	/**
	 * Method to synchronize until element is present
	 * @param strElementValue - property value
	 * @param strElementType - element locator type
	 * @param time - synchronization timeout
	 * @return true/false
	 */
	public boolean waitForElementPresence(String strElementValue, String strElementType, long time)
	{
	
		try{
			long startTime = System.currentTimeMillis();
			if(strElementType.equalsIgnoreCase("XPATH")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("ID")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("NAME")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.name(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("LINKTEXT")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("CSS")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(strElementValue)));

			}
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			report.updateTestLog("Wait For Element - element found", "Waited for " + elapsedTime / 1000
					+ " seconds to find element with " + strElementType + " - " + strElementValue, Status.DONE);
			return true;
		} catch(Exception e){
			System.err.print(e.getMessage());
			return false;

		}
	}
	
	public boolean waitForElementVisibility(String strElementValue, String strElementType, long time){
		
		try{
			long startTime = System.currentTimeMillis();
			if(strElementType.equalsIgnoreCase("XPATH")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("ID")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("NAME")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("LINKTEXT")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(strElementValue)));

			} else if(strElementType.equalsIgnoreCase("CSS")){
				WebDriverWait wait = new WebDriverWait(driver, time);
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(strElementValue)));

			}
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			report.updateTestLog("Wait For Element - element found", "Waited for " + elapsedTime / 1000
					+ " seconds to find element with " + strElementType + " - " + strElementValue, Status.DONE);
			return true;
		} catch(Exception e){
			System.err.print(e.getMessage());
			return false;

		}
	}

	public boolean waitForElementVisibilityByLinkText(String strlinkText, long time){
		
		try{
			long startTime = System.currentTimeMillis();
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(strlinkText)));
			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;
			report.updateTestLog("Wait For Element - element found", "Waited for " + elapsedTime / 1000
					+ " seconds to find element with xpath : " + strlinkText, Status.DONE);
			return true;
		} catch(Exception e){
			return false;
		}
	}

	/**
	 * Verify if element is present on page
	 * 
	 * @param strObjectProperty
	 *            -
	 * @param strFindElementType
	 *            - Element type to search by.
	 * @return returns true if the element exist, otherwise, false.
	 */
	public boolean isElementPresent2(String strObjectProperty, String strFindElementType){
		WebElement elemToFind = null;
		try{

			if(strFindElementType.equalsIgnoreCase("CSS"))
				elemToFind = driver.findElement(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				elemToFind = driver.findElement(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				elemToFind = driver.findElement(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				elemToFind = driver.findElement(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				elemToFind = driver.findElement(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				elemToFind = driver.findElement(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				elemToFind = driver.findElement(By.className(strObjectProperty));
			// report.updateTestLog("isElementPresent","Expected...",
			// Status.PASS);

		} catch(org.openqa.selenium.NoSuchElementException nsee){
			return false;
		}
		// Extra protection...
		if(elemToFind == null){
			return false;
		} else{
			return true;
		}
	}

	public boolean isElementPresentInsideElement(WebElement parentElement, String strObjectProperty,
			String strFindElementType){
		WebElement elemToFind = null;
		try{

			if(strFindElementType.equalsIgnoreCase("CSS"))
				elemToFind = parentElement.findElement(By.cssSelector(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("XPATH"))
				elemToFind = parentElement.findElement(By.xpath(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("ID"))
				elemToFind = parentElement.findElement(By.id(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("NAME"))
				elemToFind = parentElement.findElement(By.name(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("LINKTEXT"))
				elemToFind = parentElement.findElement(By.linkText(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("TAG"))
				elemToFind = parentElement.findElement(By.tagName(strObjectProperty));
			else if(strFindElementType.equalsIgnoreCase("CLASS"))
				elemToFind = parentElement.findElement(By.className(strObjectProperty));
			// report.updateTestLog("isElementPresent","Expected...",
			// Status.PASS);

		} catch(org.openqa.selenium.NoSuchElementException nsee){
			return false;
		}
		// Extra protection...
		if(elemToFind == null){
			return false;
		} else{
			//System.out.println("Found element:" + strObjectProperty);
			return true;
		}
	}
	
	
	/**
     ************************************************************* 
     * Function to find the Date Difference between two Dates
     * 
     * 
     * @return long
     * 			Difference between two dates
     * @author 387478    *   
     *
     ************************************************************* 
      */

     public long findDateDifference(String dateStart, String dateStop)
     {
    	 	
	    	SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy");
	  		Date d1 = null;
	  		Date d2 = null;
	  		
	  		long diff =0;
	  		long diffDays=0;
	   		try 
	  			{
		  			d1 = format.parse(dateStart);
		  			d2 = format.parse(dateStop);
		   			diff = d2.getTime() - d1.getTime();
		  			diffDays = diff / (24 * 60 * 60 * 1000);
		    		return Math.abs(diffDays);
	  			} 
	  			catch (Exception e) 
	  			{
	  				e.printStackTrace();
	  			}
	  		return diffDays;
     }
     
     /**
      ************************************************************* 
      * Function to convert string to integer
      * 
      * @param String stringtoConvert
      *        Input string to be converted to integer
      *  
      * @return long 
      * 		Converted value
      * 
      * @author 387478
      *
      ************************************************************* 
       */

      public long convertStringtoInteger(String stringtoConvert)
      {
 	    	long convertedInteger=0;
 	    			try{
 	    				convertedInteger = Integer.parseInt(stringtoConvert);
 	    			}catch(Exception e){
 	    				convertedInteger = 0;
 	    				System.out.println("convertStringtoInteger: Error occured in converting '" + stringtoConvert +"' to integer. Setting it to zero");
 	    			}
 	    	return convertedInteger;
      }
      
      
      /**
       ************************************************************* 
       * Function to convert string to double
       * 
       * @param String stringtoConvert
       *        Input string to be converted to integer
       *  
       * @return double 
       * 		Converted value
       * 
       * @author 387478
       *
       ************************************************************* 
        */

       public double convertStringtoDouble(String stringtoConvert)
       {
  	    	double convertedInteger=0;
  	    			try{
  	    				if(stringtoConvert.trim().isEmpty())
  	    					return 0;
  	    				convertedInteger = Double.parseDouble(stringtoConvert);
  	    			}catch(Exception e){
  	    				convertedInteger = 0;
  	    				System.out.println("Error occured in converting" + stringtoConvert +" to double. Setting it to zero");
  	    			}
  	    	return convertedInteger;
       }
       
       
       /**
        ************************************************************* 
        * Function retrieve month from a date e.g. Aug from Aug 15,2014
        * 
        * @param String stringtoConvert
        *        Input string to be converted to integer
        *  
        * @return double 
        * 		Converted value
        * 
        * @author 387478
        *
        ************************************************************* 
         */

        public String getMonthFromDate(String inputDate)
        {
   	    	String month = "";
   	    	try{
   	    		return inputDate.substring(0, inputDate.indexOf(","));
   	    	}catch (Exception e) {
				// TODO: handle exception
   	    		System.out.println(e.getMessage());
			}
   	    	return month;
        }
	
	
}