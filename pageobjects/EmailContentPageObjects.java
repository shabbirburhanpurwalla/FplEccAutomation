package pageobjects;

import static pageobjects.ObjectLocator.*;

public enum EmailContentPageObjects{

	txtUsername("username", ID,"Web UserName"),
	txtPassword("password", ID,"Web Password"),
	btnLoginButton("SubmitCreds",ID,"Login Button"),
	frm_EMailFrame("ifBdy",ID,"Mail Body Frame"),
	lnk_ClickHere("aIbBlk",ID,"Click Here Link"),
	txt_AccountNumber("txtS",ID,"Account Number"),
	btn_SearchAccount("//*[@id='imgS']",XPATH,"Search Button"),
	lnk_EmailLink("//*[@id='divSubject']",XPATH,"Email Link"),
	lnk_LogOut("//*[@id='aLogOff']/span",XPATH,"Log Out Link"),
	
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

	private EmailContentPageObjects(String strPropertyValue, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.locatorType = locatorType;
		this.strObjName = strObjName;
	}

}
