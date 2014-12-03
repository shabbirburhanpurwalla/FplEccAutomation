package pageobjects;

import static pageobjects.ObjectLocator.*;

public enum LoginPageObjects{

	lnkLogin("Log In",LINKTEXT,"LogIn Link"),
	lnkLogOut("Log Out",LINKTEXT,"LogOut Link"),
	txtUsername("userid", NAME,"UserName"),
	txtPassword("password", NAME,"Password"),
	btnLoginButton("loginButton",ID,"Login Button");
	
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


	private LoginPageObjects(String strPropertyValue, ObjectLocator locatorType, String strObjName){
		this.strProperty = strPropertyValue;
		this.locatorType = locatorType;
		this.strObjName = strObjName;
	}
	
	

}
