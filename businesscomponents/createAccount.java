package businesscomponents;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.cognizant.framework.Status;

import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;

public class createAccount extends ReusableLibrary{

	public createAccount(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	public void createAccountonSite(){
		String baseUrl;
		baseUrl = "https://appqa.fpl.com";
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String accountNumber = dataTable.getData("Accounts", "AccountNumber");
		String ssn = dataTable.getData("Accounts", "SSN");
		
		String email = dataTable.getData("Accounts", "RequestedBy");
		int counter = 1;
		if(accountNumber.length()> 0 && accountNumber.length()<10){
			accountNumber = String.format("%010d", Integer.parseInt(accountNumber));
		}
		if(ssn.length()<4){
			ssn = String.format("%04d", Integer.parseInt(ssn));
		}
			try{
				driver.get(baseUrl + "/eca/EcaController?command=displayregistration");
			    driver.findElement(By.id("account")).sendKeys(accountNumber);
			    driver.findElement(By.id("ssn")).sendKeys(ssn);
			    driver.findElement(By.id("verifyButton")).click();
			    Thread.sleep(3000);
			    driver.findElement(By.id("firstName")).sendKeys("Name");
			    driver.findElement(By.id("lastName")).sendKeys("LastName");
			    driver.findElement(By.id("emailAddress")).sendKeys(email);
			    driver.findElement(By.id("emailAddress2")).sendKeys(email);
			    driver.findElement(By.id("password")).sendKeys("Password123");
			    driver.findElement(By.id("password2")).sendKeys("Password123");
			    new Select(driver.findElement(By.id("phraseIdentifier"))).selectByVisibleText("Make or model of your first car?");
			    driver.findElement(By.id("phrase")).sendKeys("maruti");
			    driver.findElement(By.id("phrase2")).sendKeys("maruti");
			    driver.findElement(By.id("termsAndConditions")).click();
			    driver.findElement(By.id("activateButton")).click();
			    Thread.sleep(3000);
			    dataTable.putData("Accounts", "Amount", "Yes");
			    report.updateTestLog("addAccount", accountNumber, Status.SCREENSHOT);
			}catch(Exception e){
				dataTable.putData("Accounts", "Amount", "No");
				report.updateTestLog("addAccount", accountNumber, Status.FAIL);
			}
		
		
	}
	
}
