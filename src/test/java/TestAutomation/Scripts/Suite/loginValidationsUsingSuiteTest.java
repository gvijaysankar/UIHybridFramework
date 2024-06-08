package TestAutomation.Scripts.Suite;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.net.URL;
import java.lang.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.*;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import TestAutomation.Utilities.pageObjects.StudentEditContactDetails;
import TestAutomation.Utilities.pageObjects.StudentHome;
import TestAutomation.Utilities.pageObjects.StudentLoginPage;
import TestAutomation.Utilities.pageObjects.StudentProfileSettings;
import TestAutomation.Utilities.Reports.ScreenshotUtilities;
import TestAutomation.Utilities.Reports.WritetoExcel;
import TestAutomation.Utilities.Reports.WritetoHTML;
import TestAutomation.Utilities.utility.BrowserUIUtils;
import TestAutomation.Utilities.utility.Constants;
import TestAutomation.Utilities.utility.ExcelUtilities;
import TestAutomation.Utilities.utility.GenericFunctions;


public class loginValidationsUsingSuiteTest {
	private static Logger LOGGER = Logger.getLogger(loginValidationsUsingSuiteTest.class);
	String className = this.getClass().getName();
	public DesiredCapabilities cap=new DesiredCapabilities();
	WebDriver driver = null;
	WebDriverWait wait =null;	
	ArrayList<Map<String, String>> validationresults = new ArrayList<Map<String, String>>();	
	
	String mobileNumber = "7708459948";
	String URL = "http://skillgun.com";
	String cpatchano="100";
	String browser="";
	
    @Parameters({"Browser"})
	@BeforeTest
	public void BrowserTest(String Browser) throws Exception {
    	browser=Browser;
    	LOGGER.info("Browser="+browser);
    	//System.setProperty("grid.hub.url","http://192.168.0.135:4444/ui/");
     /*	if(browser.equalsIgnoreCase("chrome"))
    	{
    		cap.setPlatform(Platform.ANY);
    		cap.setBrowserName("chrome");
    		ChromeOptions options=new ChromeOptions();
    		options.merge(cap);
    	}
    	
    	if(browser.equalsIgnoreCase("MicrosoftEdge"))
    	{
    		cap.setPlatform(Platform.ANY);
    		cap.setBrowserName("MicrosoftEdge");
    		EdgeOptions options=new EdgeOptions();
    		options.merge(cap);    		
    	}
    	
    	if(browser.equalsIgnoreCase("firefox"))
    	{
    		cap.setPlatform(Platform.ANY);
    		cap.setBrowserName("firefox");
    		FirefoxOptions options=new FirefoxOptions();
    		options.merge(cap);
    	}

		driver=new RemoteWebDriver(new URL("http://192.168.0.135:4444/wd/hub"),cap); */
  
    	
	}
	
	
	@Test(groups = { "E2EFlow" })
	public void verifystudentDetailworkFlows() throws Exception {	
		Map<String,String> dataMap1 = new HashMap<String,String>();
		dataMap1.put("mobileNumber", mobileNumber);
		dataMap1.put("URL", URL);
		dataMap1.put("cpatchano", cpatchano);
		ExcelUtilities.getstudentDetailsTestData(dataMap1);
		
		try
		{
			Map<String,String>dataMap=new HashMap<String,String>();	 		    	 
		    dataMap.putAll(dataMap1);		    	
		    dataMap.put("TestResult", "PASS");	
		    dataMap.put("Comments", "");
		
		try
		{
		String date=GenericFunctions.generateDate();
	    dataMap.put("date", date);	
		LOGGER.info("*********************************** Launching the chrome browser *******************************************");
		driver = BrowserUIUtils.getNewDriver(browser,"ANY","Windows 7");		
		driver.manage().window().maximize();
		
		LOGGER.info("******************** URL - "+URL);
		driver.get(URL);
		LOGGER.info("************************************** Hitting URL in the browser *******************************************");
		
		GenericFunctions.waitInSeconds(3);  
		StudentLoginPage st_login = new StudentLoginPage(driver);
		st_login.loginTest(dataMap, driver);
	    String exp_url ="skillgun.com/student/home.aspx";
	    String act_url = driver.getCurrentUrl().toLowerCase();
	    if(act_url.contains(exp_url))
	    {
	 
	    	dataMap.put("LOGIN", "PASS");
	    }
	    else
	    {
	    	dataMap.put("TestResult", "FAIL");
    		LOGGER.info("LOGIN is not SUccessful");	    	
	    	dataMap.put("LOGIN", "FAIL+ Logn is Successful, user is not in Home Page");
	    	String comment=dataMap.get("Comments")+","+"Issue while Logging into Application";
		    dataMap.put("Comments", comment);
		    ScreenshotUtilities.takeScreenShot(driver,dataMap); 
	    	throw new Exception("Login is not Successful");
	    }	   
	   
	    GenericFunctions.waitInSeconds(5);
	    validationresults.add(dataMap);
	    if(driver != null)        
        {
          driver.quit();
        }
		}
		
		catch(Exception e)
		{
	  
			validationresults.add(dataMap);
		}
		}
		catch(Exception e)
		{
		
			e.printStackTrace();			
			validationresults.add(dataMap1);
		}	
		
		finally
		{
			 if(driver != null)
		     {
		       driver.quit();
		     }	
			
			WritetoHTML.writeLog(validationresults);
			
			WritetoExcel.ValidationOutputResultsSheet(validationresults,dataMap1,this.getClass().getName());
			
		
			LOGGER.info("**************** Test Validations Completed ***************");
		}
			
		
	}

	
	
}
