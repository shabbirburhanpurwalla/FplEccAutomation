package componentgroups;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class Configuration {

	
	
	
	private int waitingTime = 0; 
	private int messageBoxParameter=0;
	
	
	
	public int getWaitingTime()
	{
		return this.waitingTime;
	}
	
	public void setWaitingTime(int time)
	{
		this.waitingTime = time;
	}
	
	public int getMessageBoxParameter()
	{
		return this.messageBoxParameter;
	}
	
	public void setMessageBoxParameter(int param)
	{
		this.messageBoxParameter = param;
	}
	
	
	
	
	public void takeScreenShot(WebDriver driver,String Path)
	{
		try{
				File scrnsht = 	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
				FileUtils.copyFile(scrnsht, new	File(Path));
		   }
	    catch (Exception e) 
	       {
	    		e.printStackTrace();
       	   }
   	    
	}
	
	public void applyWait()
	{
		try 
		  {
			Thread.sleep(this.waitingTime);
		  } 
		  catch (InterruptedException e1) 
		  {
			e1.printStackTrace();
		  }
	}
    
	 public String getDateTime()    
	    {    
	        DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");    
	        return df.format(new Date());    
	    }   

}
