package TestAutomation.Utilities.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import TestAutomation.Scripts.Suite.StudentCompleteTest;
import TestAutomation.Utilities.utility.GenericFunctions;

public class StudentProfileSettings 
{
   
	private static Logger LOGGER = Logger.getLogger(StudentCompleteTest.class);
	private WebDriver driver ;
	private WebDriverWait wait =null;
	
	
	@FindBy(id="ContentPlaceHolder1_hlEditContact")
	WebElement editEducationaldetails;
	

	
	public StudentProfileSettings (WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public boolean clickEditContactDetailsLink()
   {
	  boolean editContact=false;
	  driver.findElement(By.linkText("Edit Contact Details")).click();
	  GenericFunctions.waitInSeconds(5); 
	  String curnt_url = driver.getCurrentUrl().toLowerCase();
	  String exp_url = "editcontactdetails.aspx";
	  if(curnt_url.contains(exp_url))
	   {
		  editContact=true;
	   }
		
	   return editContact;
   }
}
