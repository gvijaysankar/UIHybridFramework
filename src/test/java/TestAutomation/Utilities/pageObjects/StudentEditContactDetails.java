package TestAutomation.Utilities.pageObjects;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import TestAutomation.Scripts.Suite.StudentCompleteTest;
import TestAutomation.Utilities.Reports.ScreenshotUtilities;
import TestAutomation.Utilities.utility.BrowserUIUtils;
import TestAutomation.Utilities.utility.DataSourceException;
import TestAutomation.Utilities.utility.GenericFunctions;

public class StudentEditContactDetails 
{
	private static Logger LOGGER = Logger.getLogger(StudentCompleteTest.class);
	String alternateEmail="";
	private WebDriver driver ;
	private WebDriverWait wait =null;
	@FindBy(xpath="//*[@id=\"ContentPlaceHolder1_tbName\"]")
	WebElement name;
	
	@FindBy(xpath="//*[@id=\"ContentPlaceHolder1_RadioButtonList1_0\"]")
	WebElement watsappnumbersamestatusyes;
	
	@FindBy(xpath="//*[@id=\"ContentPlaceHolder1_RadioButtonList1_1\"]")
	WebElement watsappnumbersamestatusno;
	
	
	@FindBy(xpath="//*[@id=\"ContentPlaceHolder1_tbAlterContact\"]")
	WebElement watsappnumber;
	
	@FindBy(id="ContentPlaceHolder1_tbEmail")
	WebElement primaryEmail;
	
	@FindBy(id="ContentPlaceHolder1_tbAlterEmail")
	WebElement altEmail;
	
	@FindBy(xpath="//*[@id=\"ContentPlaceHolder1_tbPALine1\"]")
	WebElement per_addresslane1;
	
	@FindBy(xpath="//*[@id=\"ContentPlaceHolder1_tbPACity\"]")
	WebElement per_city;
	
	@FindBy(xpath="//*[@id='ContentPlaceHolder1_ddlPAState']")
	WebElement per_state;
	
	
	@FindBy(id="ContentPlaceHolder1_tbCALine1")
	WebElement cur_addresslane1;
	
	@FindBy(xpath="//*[@id='ContentPlaceHolder1_tbCACity']")
	WebElement cur_city;
	
	@FindBy(xpath="//*[@id='ContentPlaceHolder1_ddlCAState']")
	WebElement cur_state;
	
	@FindBy(xpath="//*[@id=\"ContentPlaceHolder1_btnSubmit\"]")
	WebElement savecontactdetails;
	
	@FindBy(xpath="//*[@id=\"ContentPlaceHolder1_pmsg\"]")
	WebElement savecontactdetailsmsg;
	
	
	
	public StudentEditContactDetails (WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	} 
	
	
	public void editContactDetails(Map<String,String>dataMap,WebDriver driver) throws InterruptedException, DataSourceException, IOException
	{
		 String editmeddage="";
		 String ramdomno="";
		 StringBuilder udetails=new StringBuilder();
		 udetails.append("{Updated Deatils:");
		try
		{	
			if(!name.getAttribute("readOnly").equals("true"))
		    {	
			name.clear();
			name.sendKeys(dataMap.get("editName"));
			udetails.append(" Edited Name-"+dataMap.get("editName"));
		    }
			String editwatsAppNoStatus=dataMap.get("editwatsAppNoStatus");
			if(editwatsAppNoStatus.equalsIgnoreCase("Yes"))
	    	{
				watsappnumbersamestatusyes.click();
	    	 }
	    	 else
	    	 {
	    		 watsappnumbersamestatusno.click();
	    		 GenericFunctions.waitInSeconds(2); 
	    		 watsappnumber.clear();
	    		 watsappnumber.click();
	    		 GenericFunctions.waitInSeconds(5); 
	    		 if(dataMap.get("editwatsAppNo")=="" || dataMap.get("editwatsAppNo")==null )
	    		 {	 
	    		 ramdomno="99"+GenericFunctions.gnerateRandomNo(8);
	    		 watsappnumber.sendKeys(ramdomno);
	    		 udetails.append(" Added WhatsAppNo-"+ramdomno);
	    		 }
	    		 else
	    		 {
	    			 watsappnumber.sendKeys(dataMap.get("editwatsAppNo"));
	    			 udetails.append(" Added WhatsAppNo-"+dataMap.get("editwatsAppNo"));
	    		 }
	    	 }			
			 GenericFunctions.waitInSeconds(2);
			 alternateEmail=GenericFunctions.gneraterandEmail();			
			 altEmail.clear();
			 altEmail.sendKeys(alternateEmail);
			 udetails.append("Alternate Email-"+alternateEmail);
			 GenericFunctions.waitInSeconds(3);
			 per_addresslane1.clear();
			 String per_addlane1=GenericFunctions.gneraterandAddress(dataMap.get("per_addresslane1"));
			 per_addresslane1.sendKeys(per_addlane1);
			 GenericFunctions.waitInSeconds(3);
			 per_city.clear();
			 per_city.sendKeys(dataMap.get("per_city"));
			 GenericFunctions.waitInSeconds(3);
			 per_state.click();
			 GenericFunctions.waitInSeconds(2); 
			 Select sel_pa_state = new Select (per_state);
	    	 sel_pa_state.selectByVisibleText(dataMap.get("per_state"));
	    	 GenericFunctions.waitInSeconds(3);
	    	 udetails.append(" Permanent Address-"+per_addlane1+ " "+dataMap.get("per_city")+" "+dataMap.get("per_state"));
	    	 
	    	 cur_addresslane1.clear();
	    	 String cur_addlane1=GenericFunctions.gneraterandAddress(dataMap.get("cur_addresslane1"));
	    	 cur_addresslane1.sendKeys(cur_addlane1);
	    	 GenericFunctions.waitInSeconds(3);
			 cur_city.clear();
			 cur_city.sendKeys(dataMap.get("cur_city"));
			 GenericFunctions.waitInSeconds(3);
			 cur_state.click();
			 GenericFunctions.waitInSeconds(2); 
			 Select sel_ca_state = new Select (cur_state);
			 sel_ca_state.selectByVisibleText(dataMap.get("cur_state"));
			 udetails.append(" Current Address-"+cur_addlane1+ " "+dataMap.get("cur_city")+" "+dataMap.get("cur_state")+"}");
			 GenericFunctions.waitInSeconds(3);
	    	 savecontactdetails.click();
	    	 GenericFunctions.waitInSeconds(5);
			 boolean savecontmsgelement =BrowserUIUtils.checkElementExistsUsingWebelement(savecontactdetailsmsg);
			if(savecontmsgelement)
			{
				editmeddage=savecontactdetailsmsg.getText();
				 if(editmeddage.contains("Updated"))
				    {				    	
				    	if(verifyEditedDetails(dataMap))
				    	{
				    		LOGGER.info("Contact Details edited Successfully");
				    		dataMap.put("EDIT_CONTACT_DETAILS", "PASS");
				    		dataMap.put("EDITED_DETAILS", udetails.toString());
				    		driver.navigate().to("http://skillgun.com/Student/Home.aspx");
				    	}
				    	else
				    	{
				    		dataMap.put("TestResult", "FAIL");
				    		LOGGER.info("Edit Contact Details is not Successful");	    	
				    		dataMap.put("EDIT_CONTACT_DETAILS", "FAIL-Unable to edit Contact Details");
				    		dataMap.put("EDITED_DETAILS", "");
					    	driver.navigate().to("http://skillgun.com/Student/Home.aspx");
				    	}
				    	
				    	driver.navigate().to("http://skillgun.com/Student/Home.aspx");
				    	
				    }
				    else
				    {
				    	dataMap.put("TestResult", "FAIL");
			    		LOGGER.info("Edit Contact Details is not Successful");	    	
			    		dataMap.put("EDIT_CONTACT_DETAILS", "FAIL-Unable to edit Contact Details");
			    		dataMap.put("EDITED_DETAILS", "");
			    		ScreenshotUtilities.takeScreenShot(driver,dataMap); 	
				    	driver.navigate().to("http://skillgun.com/Student/Home.aspx");
				    }
			}
			else
			{
				dataMap.put("TestResult", "FAIL");
	    		LOGGER.info("Edit Contact Details is not Successful");	    	
	    		dataMap.put("EDIT_CONTACT_DETAILS", "FAIL-Unable to edit Contact Details");
	    		dataMap.put("EDITED_DETAILS", "");
	    		ScreenshotUtilities.takeScreenShot(driver,dataMap); 	
		    	driver.navigate().to("http://skillgun.com/Student/Home.aspx");
			}
		}
		catch(Exception e)
		{
			dataMap.put("TestResult", "FAIL");
		    LOGGER.info("Edit Contact Details is not Successful"); 
		    String comment=dataMap.get("Comments")+","+e.getMessage();
		    dataMap.put("Comments", comment);
			dataMap.put("EDIT_CONTACT_DETAILS", "FAIL-Unable to edit Contact Details");
			dataMap.put("EDITED_DETAILS", "");
			driver.navigate().to("http://skillgun.com/Student/Home.aspx");
			ScreenshotUtilities.takeScreenShot(driver,dataMap);	    	
		}
		LOGGER.info("Completed Edited Details Function ");
	}
	
	public boolean verifyEditedDetails(Map<String,String>dataMap) throws InterruptedException
	{
		boolean flag=false; 
		String altEmail1=altEmail.getAttribute("value");
	    if(alternateEmail.equalsIgnoreCase(altEmail1) )
	    {
	    	flag=true;
	    }
	    return flag;
	}
	

}
