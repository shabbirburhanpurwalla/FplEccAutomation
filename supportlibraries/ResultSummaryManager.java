package supportlibraries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Properties;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.ReportSettings;
import com.cognizant.framework.ReportTheme;
import com.cognizant.framework.ReportThemeFactory;
import com.cognizant.framework.Settings;
import com.cognizant.framework.TimeStamp;
import com.cognizant.framework.Util;
import com.cognizant.framework.ReportThemeFactory.Theme;


/**
 * Abstract class that manages the result summary creation during a batch execution
 * @author Cognizant
 */
public abstract class ResultSummaryManager
{
	/**
	 * The {@link SeleniumReport} object used for managing the result summary
	 */
	protected static SeleniumReport summaryReport;
	
	private static ReportSettings reportSettings;
	private static String reportPath;
	private static String timeStamp;
	
	private static Date overallStartTime, overallEndTime;
	// All the above variables have been marked as static
	// so that they will maintain their state across multiple threads
	
	/**
	 * The {@link Properties} object with settings loaded from the framework properties file
	 */
	protected Properties properties;
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters =
									FrameworkParameters.getInstance();
	
	protected void setRelativePath()
	{
		String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
		if(relativePath.contains("supportlibraries")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
		}
		frameworkParameters.setRelativePath(relativePath);
	}
	
	protected void initializeTestBatch()
	{
		overallStartTime = Util.getCurrentTime();
		
		properties = Settings.getInstance();
	}
	
	protected void initializeSummaryReport(String runConfiguration, int nThreads)
	{
		frameworkParameters.setRunConfiguration(runConfiguration);
		timeStamp = TimeStamp.getInstance();
		
		initializeReportSettings();
		ReportTheme reportTheme =
				ReportThemeFactory.getReportsTheme(Theme.valueOf(properties.getProperty("ReportsTheme")));
		
		summaryReport = new SeleniumReport(reportSettings, reportTheme);
		
		summaryReport.initialize();
		summaryReport.initializeResultSummary();
		createResultSummaryHeader(nThreads);
	}
	
	protected void initializeReportSettings()
	{
		reportPath = frameworkParameters.getRelativePath() +
						Util.getFileSeparator() + "Results" +
						Util.getFileSeparator() + timeStamp;
		reportSettings = new ReportSettings(reportPath, "");
		
		reportSettings.setDateFormatString(properties.getProperty("DateFormatString"));
		reportSettings.setProjectName(properties.getProperty("ProjectName"));
		reportSettings.generateExcelReports = Boolean.parseBoolean(properties.getProperty("ExcelReport"));
		reportSettings.generateHtmlReports = Boolean.parseBoolean(properties.getProperty("HtmlReport"));
		reportSettings.linkTestLogsToSummary = true;
	}
	
	protected void createResultSummaryHeader(int nThreads)
	{
		summaryReport.addResultSummaryHeading(reportSettings.getProjectName() +
											" - " +	" Automation Execution Result Summary");
		addUsername(properties.getProperty("RemoteUrl"));
		summaryReport.addResultSummarySubHeading("Module",
								": " + properties.getProperty("RunConfiguration"),
								"Environment", ": " + properties.getProperty("Environment"));
		addResultSummaryOperatingSystem(properties.getProperty("RunManager"));
		summaryReport.addResultSummaryTableHeadings();
	}
	
	protected void setupErrorLog() throws FileNotFoundException
	{
		String errorLogFile = reportPath + Util.getFileSeparator() + "ErrorLog.txt";
		System.setErr(new PrintStream(new FileOutputStream(errorLogFile)));
	}
	
	protected void wrapUp()
	{
		overallEndTime = Util.getCurrentTime();
		String totalExecutionTime =
				Util.getTimeDifference(overallStartTime, overallEndTime);
		summaryReport.addResultSummaryFooter(totalExecutionTime);
	}
	
	protected void launchResultSummary()
	{
		if (reportSettings.generateHtmlReports) {
			try {
				Runtime.getRuntime().exec("RunDLL32.EXE shell32.dll,ShellExec_RunDLL " +
												reportPath + "\\HTML Results\\Summary.Html");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (reportSettings.generateExcelReports) {
			try {
				Runtime.getRuntime().exec("RunDLL32.EXE shell32.dll,ShellExec_RunDLL " +
												reportPath + "\\Excel Results\\Summary.xls");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	protected void addResultSummaryOperatingSystem(String runManager)
	{
		  String[] temp;
		  String delimiter = "_";
		  temp = runManager.split(delimiter);
		  summaryReport.addResultSummarySubHeading("Operating System",": " +  temp[1] +" "+ temp[2] ,	"Browser", ": " +  temp[3] +" "+ temp[4] );
	    	
	 }
	
	protected void addUsername(String remoteUrl)
	{
		if(remoteUrl.contentEquals("http://tamaghnaroy2:WAVxPxKew82aqe4yeo6a@hub.browserstack.com/wd/hub"))
    	{
			summaryReport.addResultSummarySubHeading("Date & Time",
					": " + Util.getCurrentFormattedTime(properties.getProperty("DateFormatString")),
					"User", ": " + "Tamaghna Roy");
    	}
    	else if(remoteUrl.contentEquals("http://priyathakur2:6WpqfsiJynbz8kNoCpzJ@hub.browserstack.com/wd/hub"))
    	{
    		summaryReport.addResultSummarySubHeading("Date & Time",
					": " + Util.getCurrentFormattedTime(properties.getProperty("DateFormatString")),
					"User", ": " + "Priya Thakur");
    	}

	}
	 
}