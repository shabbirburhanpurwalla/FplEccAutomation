package componentgroups;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		
		//System.out.println("-------- Oracle JDBC Connection Testing ------");
    	try 
    	{
 				Class.forName("oracle.jdbc.driver.OracleDriver");
     	} 
    	catch (ClassNotFoundException e) 
    	{
 			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
		//	return null;
 		}
 
		System.out.println("Oracle JDBC Driver Registered!");
 		Connection connection = null;
 		Statement statement =null;
 		try 
 		{
 			connection = DriverManager.getConnection("jdbc:oracle:thin:@goxsd1477:1748:CSODST", "ECC_APP_OWNER","ecc!owner!2012");
 		} 
 		catch (SQLException e) 
 		{
 			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			//return null;
 		}
 
		if (connection != null) 
		{
			System.out.println("You made it, take control your database now!");
		try 
		{ 	
			statement = connection.createStatement();  
			String AccountNumber= "5935986348";
			String Application =  "View Bill";
			
            ResultSet resultset = statement.executeQuery("select * from SSO_URL where Fpl_Account_Number="+AccountNumber +" and Fpl_Application='"+Application+"'");
			/*
            String queryString = "select su.fpl_account_number,su.FPL_APPLICATION, su.SSO_STRM_SV_URL, su.SHARED_SECRET_ANSWER" + 
            " from SSO_URL su, ODS_YEARLY oy LEFT OUTER JOIN ECC_MANAGE_PILOT emp ON oy.bill_account_number = emp.ky_ba where" + 
            " su.fpl_account_number=oy.bill_account_number" +
            " and emp.ky_ba=oy.bill_account_number" +
            " and su.curr_bill_date=oy.curr_bill_date" + 
            " and su.FPL_APPLICATION='"+Application+"' " +
            " and su.fpl_account_number="+AccountNumber +" " +
            " and su.URL_EXPIRED='N' " + 
            " and su.url_expire_after_date> sysdate and rownum < 10" +
            " order by FPL_ACCOUNT_NUMBER;";
            
            System.out.println(queryString);
            
			ResultSet resultset = statement.executeQuery(queryString);
			*/
            resultset.next();
            
            System.out.println( resultset.getString("SSO_STRM_SV_URL"));
        } 
		catch (Exception e) {  
            System.out.println("The exception raised is:" + e);  
        }  	
		finally
		{
			statement.close();  
			
		}
			
		
		} else {
			System.out.println("Failed to make connection!");
		}

	}

}
