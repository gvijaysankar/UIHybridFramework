package TestAutomation.Scripts.Suite;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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
/*
 * 
 */

public class StudentCompleteTest {
	private static Logger LOGGER = Logger.getLogger(StudentCompleteTest.class);
	String className = this.getClass().getName();
	ArrayList<Map<String, String>> validationresults = new ArrayList<Map<String, String>>();	
	/*String mobileNumber = System.getProperty("mobileNumber");
	String email = System.getProperty("email");
	String password = System.getProperty("password");
	String ldapID =  System.getProperty("LDAPID"); 
	String URL =  System.getProperty("URL"); 
	
	*/
	String mobileNumber = "9886133936";
	String URL = "http://skillgun.com";
	String cpatchano="100";
	/*String email="gvijaytest@gmail.com";
	String password="7708459948@"; */
		
	@Test(groups = { "vijay" })
	public void verifystudentDetailworkFlows() throws Exception {
	
		WebDriver driver = null;
		WebDriverWait wait =null;	
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
		driver = BrowserUIUtils.getNewDriver("CHROME","ANY","Windows 7");		
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
		    LOGGER.info("Login is Successful");
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
	   
	    StudentHome stud_home=new StudentHome(driver);
	    stud_home.mockInterviewFlow(dataMap, driver);
	    GenericFunctions.waitInSeconds(5); 
	    driver.navigate().refresh();
	    
	    if(stud_home.clickProfileSettingLink())
	    {
	    	StudentProfileSettings stud_profile_settings=new StudentProfileSettings(driver);
	    	if(stud_profile_settings.clickEditContactDetailsLink())
	    	{
	  		  LOGGER.info("User is in Edit Contact Page");
	  	      StudentEditContactDetails stud_edit_contact=new StudentEditContactDetails(driver);
	  	      stud_edit_contact.editContactDetails(dataMap, driver);
	    	}
	    	else
	    	{
		  		LOGGER.info("User unable to Navigate to Edit Contact Page");
		  		dataMap.put("TestResult", "FAIL");
	    		dataMap.put("EDIT_CONTACT_DETAILS", "FAIL-Unable to edit Contact Details,as use unable to Navigate to Edit Contacts Page");
		    	driver.navigate().to("http://skillgun.com/Student/Home.aspx");
	    	}
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
	
	@Test(groups = { "pallefunctional" })
	public void testFunction1() throws Exception {
		LOGGER.info("testfunction1");
		int a=0,b=10;
		
		System.out.println(b/a);
		
	}
	
	@Test(groups = { "pallefunctional" }, dependsOnMethods = "testFunction1")
	public void testFunction2() throws Exception {
		LOGGER.info("testfunction2");
		
	}
	
	@Test(groups = { "pallefunctional" })
	public void testFunction3() throws Exception {
		LOGGER.info("testfunction3");
		
	}

	
	
}
