/**
 * 
 */
package pageobjects;

import static pageobjects.ObjectLocator.*;


/**
 * @author 324096
 *
 */
public enum SSOObjects 
{
	lbl_PageTitle("pageTitle",ID,"Page Title"),
	lbl_Instantmessage("//*[@id='bannerMsg']/p",XPATH,"Instant Message"),
	lbl_ExpMessage("//*[@id='explanatoryMessage']",XPATH,"Explanatory Message"),
	txt_Password("ssoPW",NAME,"Password Textbox"),
	btn_GoButton("//*[@id='ssoZipGo']/a",XPATH,"Go Button"),
	
	lbl_troubleMessage("//*[@id='troubleMessage']",XPATH,"Trouble Message"),
	lnk_troubleMessageLink("//*[@id='troubleMessage']/a",XPATH,"Trouble Message Link"),
	;
	
	String strProperty = "";
	ObjectLocator locatorType = null;
	String strObjName = "";
	
	public String getProperty(){
		return strProperty;
	}

	public ObjectLocator getLocatorType(){
		return locatorType;
	}
	
	public String getObjectName(){
		return strObjName;
	}

	private SSOObjects(String strPropertyValue, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.locatorType = locatorType;
		this.strObjName = strObjName;
	}

}
