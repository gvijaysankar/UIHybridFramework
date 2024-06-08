package TestAutomation.Utilities.pageObjects;

import java.time.Duration;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
import TestAutomation.Utilities.utility.Constants;
import TestAutomation.Utilities.utility.GenericFunctions;

public class StudentLoginPage 
{
	private WebDriver driver ;
	private WebDriverWait wait =null;
	private static Logger LOGGER = Logger.getLogger(StudentCompleteTest.class);
	@FindBy(id="ContentPlaceHolder1_tbPhoneNumber")
	WebElement elem_mob_num;
	By by_email = By.id("ContentPlaceHolder1_tbEmail");
	By by_pwd = By.id("ContentPlaceHolder1_tbPassword");
	By by_agree_policy_chk = By.id("ckbkPolicyAgreement");
	By by_submit = By.id("ContentPlaceHolder1_btnLogin");
	By by_cpatcha = By.xpath("//*[@id=\"cpatchaTextBox\"]");
	
	public StudentLoginPage (WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void loginTest(Map<String,String>dataMap,WebDriver driver) throws Exception	
	{
		//StringBuilder login =new StringBuilder();
		try
		{	
		LOGGER.info("Started Login---");	
		eneterMobileNumber(dataMap.get("mobileNumber"));		
	    typeEmail(dataMap.get("email"));
	    typePassword(dataMap.get("password"));
	    agreeForPlacementPolicy();
	    GenericFunctions.waitInSeconds(2);  
	    typecapcha(dataMap.get("cpatchano"));
	    clickLogin();
	    GenericFunctions.waitInSeconds(10);
	    
		}
		catch(Exception e)
		{
			dataMap.put("TestResult", "FAIL");
    		LOGGER.info("LOGIN is not Successful");	    	
	    	dataMap.put("LOGIN", "FAIL+ Logn is not Successful, user is not in Home Page");
	    	String comment=dataMap.get("Comments")+","+e.getMessage();
		    dataMap.put("Comments", comment);
		    ScreenshotUtilities.takeScreenShot(driver,dataMap); 
	    	throw new Exception("Login is not Successful");
		}
	}
	public void eneterMobileNumber(String mobNum)
	{
		elem_mob_num.sendKeys(mobNum);
	}
	   public void typeEmail(String email)
		{
		       WebElement elem_email = driver.findElement(by_email);
		       BrowserUIUtils.waitForElementUsingwebElement(elem_email,6,driver).sendKeys(email);
		}


	   public void agreeForPlacementPolicy()
	   {
	       WebElement elem_agree_polciy = driver.findElement(by_agree_policy_chk );
	       WebDriverWait wait=new WebDriverWait(driver, 3);
	       wait.until(ExpectedConditions.visibilityOf(elem_agree_polciy));
	       elem_agree_polciy.click();//we cant perform this since this contol may be invisible 
	   }
	   
	   public void typePassword(String password)
	   {
	       WebElement elem_pwd = driver.findElement(by_pwd);
	       GenericFunctions.waitInSeconds(3);	    
	       elem_pwd .sendKeys(password);//we cant perform this since this contol may be invisible 
	   }
	   
	   public void typecapcha(String cpatchano)
	   {
	       WebElement cpatcha = driver.findElement(by_cpatcha);
	       GenericFunctions.waitInSeconds(3);	    
	       cpatcha.sendKeys("100");//we cant perform this since this contol may be invisible 
	   }
	   public void clickLogin()
	   {
	       WebElement elem_submit =driver.findElement(by_submit);
	       WebDriverWait wait=new WebDriverWait(driver, 3);
	       wait.until(ExpectedConditions.visibilityOf(elem_submit));
	       elem_submit.click();//we cant perform this since this contol may be invisible 
	   }
	   
	   public void verifyRegistration() throws InterruptedException
		  {
			WebElement elem_3char_name = this.driver.findElement(By.id("ContentPlaceHolder1_tbName"));
			GenericFunctions.waitInSeconds(3);	  
			elem_3char_name.sendKeys("van");
			driver.findElement(By.id("ContentPlaceHolder1_tbTotalPaidAmount")).sendKeys("32000");
			driver.findElement(By.id("ContentPlaceHolder1_tbLastPaidAmount")).sendKeys("32000");
			
			WebElement elem_paid_dt =driver.findElement(By.id("ContentPlaceHolder1_tbLastPaymentDate"));
			
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("arguments[0].value='02-03-2023'", elem_paid_dt);
			
			WebElement courseList=this.driver.findElement(By.id("ContentPlaceHolder1_ddlCourse"));
			Select select= new Select(courseList);
			select.selectByVisibleText("java testing");
			
			WebElement elem_email = driver.findElement(By.id("ContentPlaceHolder1_tbUpdateEmailID"));
			GenericFunctions.waitInSeconds(4);	  
			elem_email.sendKeys("vanithareddy797@gmail.com");
			WebElement elem_email_confrm = driver.findElement(By.id("tbConfirmEmailID"));
			elem_email_confrm.sendKeys("vanithareddy797@gmail.com");
			WebElement elem_pwd = this.driver.findElement(By.id("ContentPlaceHolder1_tbPwdUpdate"));
			elem_pwd.sendKeys("vanitha_0523!");
			
			WebElement elem_pwd_cnfrm = driver.findElement(By.id("tbConfirmPwd"));
			elem_pwd_cnfrm.sendKeys("vanitha_0523!");
			WebElement elem_ddl_sec_qstn =driver.findElement(By.id("ContentPlaceHolder1_ddlPwdHintQuestions"));
			Select sel_sec_qstn = new Select (elem_ddl_sec_qstn);
			sel_sec_qstn.selectByVisibleText("What is your favourite color?");
			
			WebElement elem_sec_qstn_ans = driver.findElement(By.id("tbPwdHintAns"));
			elem_sec_qstn_ans.sendKeys("green");
			
			WebElement upd_email = driver.findElement(By.id("btnUpdateEmail"));
			upd_email.click();
			
			WebElement elem_ph_num = driver.findElement(By.id("ContentPlaceHolder1_tbPhoneNumber"));
			GenericFunctions.waitInSeconds(4);	  
			
			String selectAll = Keys.chord(Keys.CONTROL, "a");
			elem_ph_num.sendKeys(selectAll);
			elem_ph_num.sendKeys(Keys.DELETE);
			
			
			elem_ph_num.sendKeys("7330608997");
			
			By by_password = By.id("ContentPlaceHolder1_tbPassword");
			GenericFunctions.waitInSeconds(4);	
			WebElement elem_password = driver.findElement(by_password);
			String act_placeholder_val = elem_password.getAttribute("placeholder");
			String exp_placeholder_val = "Password";
			Assert.assertEquals(exp_placeholder_val, act_placeholder_val);
		  }
}
