package TestAutomation.Utilities.pageObjects;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import TestAutomation.Scripts.Suite.StudentCompleteTest;
import TestAutomation.Utilities.Reports.ScreenshotUtilities;
import TestAutomation.Utilities.utility.BrowserUIUtils;
import TestAutomation.Utilities.utility.DataSourceException;
import TestAutomation.Utilities.utility.GenericFunctions;

public class StudentHome 
{
	private static Logger LOGGER = Logger.getLogger(StudentCompleteTest.class);
	private WebDriver driver ;
	private WebDriverWait wait =null;
	@FindBy(id="cbxattendmock")
	WebElement elem_attnd_mock_chk;
	
	@FindBy(xpath="//div[@id='mockdate']/input[@type='text']")
	WebElement ele_mock_dt;
	
	@FindBy(id="EveningSlot")
	WebElement elem_evening_slot;
	
	@FindBy(xpath="//*[@id=\"MorningSlot\"]")
	WebElement elem_morning_slot;
	
	@FindBy(xpath="//*[@id=\"divIntrvMode\"]/label[2]")
	WebElement elem_face_to_face;
	
	@FindBy(xpath="//*[@id=\"divIntrvMode\"]/label[3]")
	WebElement elem_virtual_online;
	
	//*[@id="rdf2f"]
	
	@FindBy(xpath="//select[@id='ddlSubList']")
	WebElement elem_mock_sub;
	
	@FindBy(id="conformationchkbx")
	WebElement elem_chk_iwillattend;
	
	@FindBy(id="ContentPlaceHolder1_submit")
	WebElement elem_apply_mock;
	
	@FindBy(xpath="//marquee/p[contains(text(),'Your mock interview has been schedule on')]")
	WebElement elem_mock_attnd_msg;
	
	
	String act_msg="";
	public StudentHome (WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	
	public void mockInterviewFlow(Map<String,String>dataMap,WebDriver driver) throws InterruptedException, DataSourceException, IOException
	{	
		try
		{
			LOGGER.info("Mock Interview Schedule Started..");
			//Select Mock Panel
			elem_attnd_mock_chk.click();
			selectDate(dataMap.get("date"));
			GenericFunctions.waitInSeconds(2); 
			selectIntervIewTimeSlot(dataMap.get("slotType"));
			GenericFunctions.waitInSeconds(2); 
			selectModeOfInterview(dataMap.get("interviewMode"));
			GenericFunctions.waitInSeconds(2); 
			selectMockSubject(dataMap.get("Subject"));
			GenericFunctions.waitInSeconds(2); 
			elem_chk_iwillattend.click();
			elem_apply_mock.click();
			GenericFunctions.waitInSeconds(2); 
			driver.navigate().to("http://skillgun.com/Student/Home.aspx");
			GenericFunctions.waitInSeconds(5); 
			boolean actmsgelement =checkforMessage();		
			if(actmsgelement)
			{			
				 if(act_msg.contains("mock interview has been schedule"))
				    {
				    	dataMap.put("STUDENTHOME_MOCK_INTERVIEW", "PASS");
				    	LOGGER.info("Mock Interview Schedule is Successful..");
				    }
				    else
				    {
				    	dataMap.put("TestResult", "FAIL");
			    		LOGGER.info("Mock Interview is not Successful");	    	
			    		dataMap.put("STUDENTHOME_MOCK_INTERVIEW", "FAIL-Mock Interview is not Scheduled");
			    		ScreenshotUtilities.takeScreenShot(driver,dataMap); 	
				    }
			}
			else
			{
				dataMap.put("TestResult", "FAIL");	 
				LOGGER.info("Mock Interview is not Successful");
		    	String comment=dataMap.get("Comments")+","+"FAIL-Mock Interview is not Scheduled";
			    dataMap.put("Comments", comment);
			    ScreenshotUtilities.takeScreenShot(driver,dataMap); 
				dataMap.put("STUDENTHOME_MOCK_INTERVIEW", "FAIL-Mock Interview is not Scheduled");
			}
		}
		catch(Exception e)
		{	
			dataMap.put("TestResult", "FAIL");
		    LOGGER.info("Mock Interview is not Successful"); 
	    	dataMap.put("STUDENTHOME_MOCK_INTERVIEW", "FAIL-Unable to Schedule Interview");
	    	String comment=dataMap.get("Comments")+","+e.getMessage();
		    dataMap.put("Comments", comment);
		    ScreenshotUtilities.takeScreenShot(driver,dataMap); 
		}
	}
	

	
	public void selectDate(String date)
	{
		GenericFunctions.waitInSeconds(5); 
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].value='"+date+"'", ele_mock_dt);
	}

	public void selectIntervIewTimeSlot(String slotType)
	{
		if(slotType.equalsIgnoreCase("Evening Slot"))
		{
		   
		   elem_evening_slot.click();
		}
		else
		{			
			elem_evening_slot.click();
		}
	}
	
	public void selectModeOfInterview(String mode)
	{
		if(mode.equalsIgnoreCase("Face to face"))
		{
		   
			elem_face_to_face.click();
		}
		else
		{			
			elem_virtual_online.click();
		}
	}
	
	public void selectMockSubject(String subName) throws InterruptedException
	{
		elem_mock_sub.click();
		GenericFunctions.waitInSeconds(3); 
		Select select = new Select (elem_mock_sub);
		select.selectByVisibleText(subName);
		Thread.sleep(8000);
	}


	public boolean clickProfileSettingLink()
	{
		boolean profleSettings=false;
		driver.findElement(By.xpath("//a[@href='ProfileSetting.aspx']")).click();
		GenericFunctions.waitInSeconds(5); 
		String curnt_url = driver.getCurrentUrl();
		String exp_url = "Student/ProfileSetting.aspx";
		 if(curnt_url.contains(exp_url))
		 {
			 profleSettings=true;
		 }
		
		return profleSettings;
	}
	
	public boolean checkforMessage() throws InterruptedException
	{
		boolean actmsgelement=false;
		actmsgelement =BrowserUIUtils.checkElementExistsUsingWebelement(elem_mock_attnd_msg);
		if(!actmsgelement)
		{
			Thread.sleep(3000);
			checkforMessage();
			if(!actmsgelement)
			{
				Thread.sleep(3000);
				checkforMessage();
			}	
		}
		act_msg=elem_mock_attnd_msg.getText();
		return actmsgelement;
		
	}

	
	
}
