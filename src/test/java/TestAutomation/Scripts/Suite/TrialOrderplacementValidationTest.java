package TestAutomation.Scripts.Suite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import TestAutomation.Utilities.Reports.ScreenshotUtilities;
import TestAutomation.Utilities.Reports.WritetoHTML;
import TestAutomation.Utilities.utility.*;


public class TrialOrderplacementValidationTest {
	
	private static Logger LOGGER = Logger.getLogger(TrialOrderplacementValidationTest.class);
	String className = this.getClass().getName();
	HashMap<String,String> localesdatils = CommonSuiteUtility.addLangauageCode();
	int pouiFlag = 0;
	String pouiFile = null;
	/*String getMultOfferID = System.getProperty("MULT_OfferID");
	String getcountryDetails = System.getProperty("MULT_CountryList");
	String getENOfferID = System.getProperty("EN_OfferID");
	String ldapID =  System.getProperty("LDAPID");
	String selectplangroup = System.getProperty("Select_Plan_Group");
	*/
	
	String getMultOfferID = "632B3ADD940A7FBB7864AA5AD19B8D28";
	String getcountryDetails = "US";
	String getENOfferID = "";
	String ldapID =  "gvijaysankar@gmail.com";
	String selectplangroup = "Individuals";
	
	
	@Test(groups = { "TrialOrderPlacementValidation" })
	public void OrderPlacmentValidation() throws Exception
	{
		WebDriver driver = null;
		ArrayList<Map<String, String>> offerdatalist = new ArrayList<Map<String, String>>();
		Map<String,String> dataMap1 = new HashMap<String,String>();
		dataMap1.put(Constants.environment, "stage");
		try
		{
			ArrayList<String> OfferIDdetails = new ArrayList<String>();
			ArrayList<String> Countrydetails = new ArrayList<String>();
			OfferIDdetails = GenericFunctions.splitStringValue(getMultOfferID);
			
				String offerid = getMultOfferID;				
					Map<String,String> dataMap = new HashMap<String,String>();
					dataMap.put(Constants.environment, "prod");
					try
					{
						String email = "mercauto";
						String country ="US";
						String langauagecode = localesdatils.get(country);						
						dataMap.put("Country", country);
						dataMap.put("StoreName", country);
						dataMap.put("OfferID", offerid);
						CommonSuiteUtility.updateDataMap(dataMap); 
						CommonSuiteUtility.updateDataMapWithUserDetails(dataMap);
						if(ldapID.isEmpty())
						{
							dataMap.put(Constants.EMAIL_ID,email);
							dataMap.put(Constants.EMAIL_ID,GenericFunctions.generateEmailID(dataMap,"TrialOrder"));
						}
						else
						{
							dataMap.put(Constants.EMAIL_ID,ldapID);
							dataMap.put(Constants.EMAIL_ID,GenericFunctions.generateEmailID(dataMap,"TrialOrder"));
						}
						
						dataMap.put(Constants.Password,"P@ssw0rd1");
							
						LOGGER.info("*********************************** Launching the chrome browser *******************************************");
						driver = BrowserUIUtils.getNewDriver("CHROME","ANY","Windows 7");		
						driver.manage().window().maximize();
						//New URL UCV2
						String URL = "https://commerce.adobe.com/checkout/email/?cli=creative&co=US&lang=en&items%5B0%5D%5Bid%5D=632B3ADD940A7FBB7864AA5AD19B8D28&items%5B0%5D%5Bcs%5D=1";
						
						
						LOGGER.info("******************** URL - "+URL);
						driver.get(URL);
						LOGGER.info("************************************** Hitting URL in the browser *******************************************");
						
						GenericFunctions.waitInSeconds(3);  
						newCartPage(dataMap,driver);            //enter email id on cart page
						newCheckOutPage(dataMap,driver);
						getOrderNumber(dataMap,driver);
					}
					catch(Exception e)
					{
						ScreenshotUtilities.takeScreenShot(driver,dataMap); 
						dataMap.put(Constants.Comments, "FAIL - Some error while validating - Refer Screenshot under selenium folder");
						dataMap.put(Constants.orderNumber,"unable to place Order");
						e.printStackTrace();
					}
					 if(driver != null)
				     {
				       driver.quit();
				     }
					 offerdatalist.add(dataMap);
				
			
			
		}
		catch(Exception e)
		{
			
		}
		finally
		{			 
			 if(driver != null)
		     {
		       driver.quit();
		     }
			 WritetoHTML.writeLog(offerdatalist);
			 for(int a=0;a<offerdatalist.size();a++)
			 {
				 Map<String,Map<String,String>> specMap = new HashMap<String,Map<String,String>>();
				 //Map<String,String> specMap2 = new HashMap<String,String>();						
				 specMap.put("keyvalue",offerdatalist.get(a));
				 LOGGER.info("******************************************************************************************************************"+" \n "
				               +"Email ID : "+specMap.get("keyvalue").get(Constants.EMAIL_ID)+" , Country : "+specMap.get("keyvalue").get("Country")+" , ProductName : "+specMap.get("keyvalue").get("ProductName")+" , \n"
						       +"CartTotalPrice : "+specMap.get("keyvalue").get("Cart_TrialTotalDiscountAmount")+" , COTotalPrice : "+specMap.get("keyvalue").get("CO_TrialTotalDiscountAmount")+" , \n"
						       +"EmailPgBannerStatus : "+specMap.get("keyvalue").get("EmailPgBannerStatus")+" , EmailPgCartItemBannerStatus : "+specMap.get("keyvalue").get("EmailPgCartItemBannerStatus")+" , \n"
						       +"COPgBannerStatus : "+specMap.get("keyvalue").get("COPgBannerStatus")+" , COPgCartItemBannerStatus : "+specMap.get("keyvalue").get("COPgCartItemBannerStatus")+" , \n"
						       +"OrderNumber : "+specMap.get("keyvalue").get(Constants.orderNumber)+" , TrialDetailsOrderConfPg : "+specMap.get("keyvalue").get("TrialDetailsOrderConfPg")+" , \n"
						       +"OfferID : "+specMap.get("keyvalue").get("OfferID")+" \n"
						       +"************************************************************************************************************************");
				 
			 }
			 ValidationOutputResultsSheet(offerdatalist,dataMap1,this.getClass().getName());
		}
	}
	
	 public void newCartPage(Map<String,String> dataMap,WebDriver driver) throws Exception 
	 {
		 String Totaldiscountamount = "";
		 String Product_Name = BrowserUIUtils.waitForElementUsingDriver(By.xpath("//div[@class='product-name']"),4,driver).getText();	
		 dataMap.put("ProductName",Product_Name);
		 LOGGER.info("Enter Email address - "+dataMap.get(Constants.EMAIL_ID));
		 BrowserUIUtils.waitForElementUsingDriver(By.xpath("//input[@data-qe-id='user-email-input']"),6,driver).sendKeys(dataMap.get(Constants.EMAIL_ID));
	     ScreenshotUtilities.takeScreenShot(driver,dataMap);                                                                          //Take Screenshot after entering Email id
		 boolean trialtotaldiscountamount = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//div[@data-qe-id='total-with-discount-amount']/span"),driver,"Its not a Trial offer");
		 if(trialtotaldiscountamount)
		 {
			Totaldiscountamount = driver.findElement(By.xpath("//div[@data-qe-id='total-with-discount-amount']/span")).getText();
		 }
		 dataMap.put("Cart_TrialTotalDiscountAmount",GenericFunctions.splitString(Totaldiscountamount));
		 //Check trial banner exists in the Email Page header
		 boolean banner = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//div[@data-qe-id='banner-container']/span[@class='banner-text']"),driver,"********* Blue color trial banner not showing ******");
         if(banner)
         {
         	String bannertext = driver.findElement(By.xpath("//div[@data-qe-id='banner-container']/span[@class='banner-text']")).getText();
         	dataMap.put("EmailPgBannerStatus", "PASS - "+bannertext);
         }
         else
         {
         	dataMap.put("EmailPgBannerStatus", "FAIL - Blue color trial banner not showing in the Email Page header");
         }
         //check cart item trial banner exists in the Email page
         boolean cartitembanner = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//div[@data-qe-id='cart-item-promo-banner']/div[@class='promo-content']"),driver,"********* Yellow color cart Item trial banner not showing ******");
         if(cartitembanner)
         {
         	String bannertext = driver.findElement(By.xpath("//div[@data-qe-id='cart-item-promo-banner']/div[@class='promo-content']")).getText();
         	dataMap.put("EmailPgCartItemBannerStatus", "PASS - "+bannertext);
         }
         else
         {
         	dataMap.put("EmailPgCartItemBannerStatus", "FAIL - Yellow color Cart Item trial banner not showing in the Email Page");
         }
         if(dataMap.get("Country").equalsIgnoreCase("KR"))
		 {
			driver.findElement(By.xpath("//input[@data-qe-id='opt-in-checkbox-consent-third-parties']")).click();
			driver.findElement(By.xpath("//input[@data-qe-id='opt-in-checkbox-consent-info-collection']")).click();
			driver.findElement(By.xpath("//input[@data-qe-id='opt-in-checkbox-consent-info-to-service-providers']")).click();
			driver.findElement(By.xpath("//input[@data-qe-id='opt-in-checkbox-info-transfer-outside-country']")).click();
		 }
		driver.findElement(By.xpath("//button[@data-qe-id='action-button-email']")).click();
			//driver.findElement(By.id("action-container")).findElement(By.className("action-content")).findElement(By.className("action-button-div")).findElement(By.className("action-button")).click();
			//Below code developed for WinBack promo validation
		GenericFunctions.waitInSeconds(3);	
	 }
	
	 //Enter details user and card details on checkout page
	 public void newCheckOutPage(Map<String,String> dataMap,WebDriver driver) throws Exception 
	 {
		 String Totaldiscountamount = "";
		 GenericFunctions.waitInSeconds(5);
		 ScreenshotUtilities.takeScreenShot(driver,dataMap); 
		 LOGGER.info("Enter User details -First name and Last Name");
		 //Check trial banner exists in the Checkout Page header
		 boolean banner = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//div[@data-qe-id='banner-container']/span[@class='banner-text']"),driver,"********* Blue color trial banner not showing ******");
         if(banner)
         {
         	String bannertext = driver.findElement(By.xpath("//div[@data-qe-id='banner-container']/span[@class='banner-text']")).getText();
         	dataMap.put("COPgBannerStatus", "PASS - "+bannertext);
         }
         else
         {
         	dataMap.put("COPgBannerStatus", "FAIL - Blue color trial banner not showing in the Checkout Page header");
         }
         //check cart item trial banner exists in the Checkout page
         boolean cartitembanner = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//div[@data-qe-id='cart-item-promo-banner']/div[@class='promo-content']"),driver,"********* Yellow color Cart Item trial banner not showing ******");
         if(cartitembanner)
         {
         	String bannertext = driver.findElement(By.xpath("//div[@data-qe-id='cart-item-promo-banner']/div[@class='promo-content']")).getText();
         	dataMap.put("COPgCartItemBannerStatus", "PASS - "+bannertext);
         }
         else
         {
         	dataMap.put("COPgCartItemBannerStatus", "FAIL - Yellow color Cart Item trial banner not showing in the Checkout Page");
         }
         
         boolean status = driver.findElement(By.xpath("//input[@data-qid='firstName']")).isDisplayed();
		 if(status)
		 {
			driver.findElement(By.xpath("//input[@data-qid='firstName']")).sendKeys(dataMap.get(Constants.CUSTOMER_FIRST_NAME));
			driver.findElement(By.xpath("//input[@data-qid='lastName']")).sendKeys(dataMap.get(Constants.CUSTOMER_LAST_NAME));
			if(dataMap.get(Constants.storeName).equalsIgnoreCase("JP"))
			{
				driver.findElement(By.xpath("//input[@data-qid='firstNamePronunciation']")).sendKeys(dataMap.get(Constants.CUSTOMER_FIRST_NAME));
				driver.findElement(By.xpath("//input[@data-qid='lastNamePronunciation']")).sendKeys(dataMap.get(Constants.CUSTOMER_LAST_NAME));
			}
		 }
		else
		{
			driver.findElement(By.xpath("(//input[@placeholder='First name'])[position()=2]")).sendKeys(dataMap.get(Constants.CUSTOMER_FIRST_NAME));
			driver.findElement(By.xpath("(//input[@placeholder='Last name'])[position()=2]")).sendKeys(dataMap.get(Constants.CUSTOMER_LAST_NAME));
			if(dataMap.get(Constants.storeName).equalsIgnoreCase("JP"))
			{
				driver.findElement(By.xpath("(//input[@placeholder='First'])[position()=2]")).sendKeys(dataMap.get(Constants.CUSTOMER_FIRST_NAME));
				driver.findElement(By.xpath("(//input[@placeholder='Last'])[position()=2]")).sendKeys(dataMap.get(Constants.CUSTOMER_LAST_NAME));
			}
		}
		LOGGER.info("***************** Enter credit card details ****************************");
		driver.findElement(By.xpath("//input[@data-qid='creditCardNumber']")).sendKeys(dataMap.get(Constants.CARD_NUMBER));
		String country = dataMap.get(Constants.Country);
		if(country.equalsIgnoreCase("IL") || country.equalsIgnoreCase("TR") || country.equalsIgnoreCase("BR") || country.equalsIgnoreCase("IN") || country.equalsIgnoreCase("SG") || country.equalsIgnoreCase("MY") || country.equalsIgnoreCase("ID") || country.equalsIgnoreCase("TH") || country.equalsIgnoreCase("TW") ||country.equalsIgnoreCase("PH") || country.equalsIgnoreCase("KR") || country.equalsIgnoreCase("AR") || country.equalsIgnoreCase("CO") || country.equalsIgnoreCase("CL") || country.equalsIgnoreCase("PE") || country.equalsIgnoreCase("VE") || country.equalsIgnoreCase("CR") || country.equalsIgnoreCase("EC") || country.equalsIgnoreCase("GT"))
		{
			//Enter cvcode
			driver.findElement(By.xpath("//input[@data-qid='creditCardVerificationValue']")).sendKeys(dataMap.get("Security Code"));
		}
		//Scroll down the page
		JavascriptExecutor jse1 = ((JavascriptExecutor) driver);
		jse1.executeScript("window.scrollTo(0, document.body.scrollHeight);");
		
		LOGGER.info("***************** Select credit card expiry month ****************************");
		driver.findElement(By.xpath("//div[@data-qid='creditCardExpiryMonth']")).click();
        driver.findElement(By.xpath("(//span[@class='month-left'])[position()=10]")).click();
        LOGGER.info("***************** Select credit card expire year ****************************");
		driver.findElement(By.xpath("//div[@data-qid='creditCardExpiryYear']")).click();
		driver.findElement(By.xpath("//li[@data-value='2020']")).click();	
		//select state value
		boolean statecode = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//coral-select[@data-attr-name='billingAddress.stateProvince.code']"),driver,"state Code not require to select");
		if(statecode)
		{
			String stateval = dataMap.get(Constants.State);
			driver.findElement(By.xpath("//coral-select[@data-attr-name='billingAddress.stateProvince.code']")).click();
			WebElement ele = driver.findElement(By.xpath("//coral-select[@data-attr-name='billingAddress.stateProvince.code']"));
			List<WebElement> state =  ele.findElement(By.className("coral-Overlay")).findElement(By.className("coral3-SelectList")).findElements(By.className("coral3-SelectList-item"));
			for(int k=0;k<state.size();k++)
			{
				String val = state.get(k).getText();
				if(stateval.equalsIgnoreCase(val))
				{
					state.get(k).click();
				}
			}					
		}
					
		//For AR and CL country need to tax code
		if(country.equalsIgnoreCase("AR") || country.equalsIgnoreCase("CL") || country.equalsIgnoreCase("CA") || country.equalsIgnoreCase("BR") || country.equalsIgnoreCase("CA_FR"))
		{	
			if(country.equalsIgnoreCase("AR") || country.equalsIgnoreCase("CL") || country.equalsIgnoreCase("BR"))
			{
				driver.findElement(By.xpath("//input[@data-qid='taxId']")).sendKeys(dataMap.get(Constants.CPF));
				//driver.findElement(By.xpath("//input[@data-qid='city']")).sendKeys(dataMap.get("City"));
			}					
			if(country.equalsIgnoreCase("AR") || country.equalsIgnoreCase("CA") || country.equalsIgnoreCase("BR") || country.equalsIgnoreCase("CA_FR"))
			{						
				//select state value
				driver.findElement(By.xpath("//div[@data-qid='stateProvince']")).click();
				JavascriptExecutor jse = ((JavascriptExecutor) driver);
				jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
				if(country.equalsIgnoreCase("AR"))
				{
					driver.findElement(By.xpath("//ul[@data-qid='stateProvinceList']//li[@data-value='18']")).click(); 
				}
				if(country.equalsIgnoreCase("CA") || country.equalsIgnoreCase("CA_FR"))
				{
					driver.findElement(By.xpath("//ul[@data-qid='stateProvinceList']//li[@data-value='ON']")).click(); 
				}
				if(country.equalsIgnoreCase("BR"))
				{
					driver.findElement(By.xpath("//ul[@data-qid='stateProvinceList']//li[@data-value='RJ']")).click(); 
				}
				GenericFunctions.waitInSeconds(2);
			}
			else
			{
				//select state value
				driver.findElement(By.xpath("//div[@data-qid='stateProvince']")).click();
				JavascriptExecutor jse = ((JavascriptExecutor) driver);
				jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
				//driver.findElement(By.xpath("//li[text()='Antofagasta']")).click();
				driver.findElement(By.xpath("//ul[@data-qid='stateProvinceList']//li[@data-value='03']")).click();
				GenericFunctions.waitInSeconds(2);
			}
		}				
		LOGGER.info("***************** Enter the postal code ****************************");
		boolean postalcode = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//input[@data-qid='postalCode']"),driver,"Postal code field not available");
		if(postalcode)
		{
			driver.findElement(By.xpath("//input[@data-qid='postalCode']")).sendKeys("");
			GenericFunctions.waitInSeconds(3);
			driver.findElement(By.xpath("//input[@data-qid='postalCode']")).clear();
			driver.findElement(By.xpath("//input[@data-qid='postalCode']")).sendKeys(dataMap.get(Constants.CUSTOMER_ZIP_CODE));  
		}
		
		LOGGER.info("***************** Enter the city details ****************************");
		boolean city = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//input[@data-qid='city']"),driver,"City field not available");
		if(city)
		{
			driver.findElement(By.xpath("//input[@data-qid='city']")).sendKeys("");
			GenericFunctions.waitInSeconds(2);
			driver.findElement(By.xpath("//input[@data-qid='city']")).clear();
			GenericFunctions.waitInSeconds(2);
			driver.findElement(By.xpath("//input[@data-qid='city']")).sendKeys(dataMap.get("City"));
		}
				
		LOGGER.info("***************** Enter the billing address details ****************************");
		boolean address = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//input[@data-qid='street1']"),driver,"Street address field not available");
		if(address)
		{
			driver.findElement(By.xpath("//input[@data-qid='street1']")).sendKeys(dataMap.get(Constants.CUSTOMER_ADDRESS_STREET1));
			//driver.findElement(By.xpath("//input[@data-attr-name='billingAddress.street1']")).click();
		}
		
		LOGGER.info("***************** Enter the business address details ****************************");
		boolean billingaddress = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//input[@placeholder='Billing address']"),driver,"billing address field not available");
		if(billingaddress)
		{
			driver.findElement(By.xpath("//input[@placeholder='Billing address']")).sendKeys(dataMap.get(Constants.CUSTOMER_ADDRESS_STREET1));	
			//driver.findElement(By.xpath("//input[@placeholder='Billing address']")).click();
		}
		
		boolean trialtotaldiscountamount = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//div[@data-qe-id='total-with-discount-amount']/span"),driver,"Its not a Trial offer");
		if(trialtotaldiscountamount)
		{
			Totaldiscountamount = driver.findElement(By.xpath("//div[@data-qe-id='total-with-discount-amount']/span")).getText();
		}
		dataMap.put("CO_TrialTotalDiscountAmount",GenericFunctions.splitString(Totaldiscountamount));
		//enter company details for Team
		if(selectplangroup.equalsIgnoreCase("Business"))
		{
			driver.findElement(By.xpath("//input[@data-qid='companyName']")).sendKeys("Adobe");
			if(dataMap.get(Constants.storeName).equalsIgnoreCase("JP"))
			{
				driver.findElement(By.xpath("//input[@data-qid='companyName']")).sendKeys("Adobe");
			}
			LOGGER.info("***************** Enter the phone number details ****************************");
			//boolean phonenumber = CheckCommonHelper.checkElementExistsUsingDriver(By.xpath("//input[@data-qid='primaryPhone']"),driver,"Phone Number field not available");
			//if(phonenumber)
			//{
				driver.findElement(By.xpath("//input[@data-qid='primaryPhone']")).sendKeys(dataMap.get(Constants.CUSTOMER_PHONE_NUMBER));
			//}
			//Common.takeScreenShot(driver,dataMap);
			//dataMap.putAll(getCheckoutPgDetails(driver));                                                                      //get details from checkout page like product name and price details.
			JavascriptExecutor jse = ((JavascriptExecutor) driver);
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
			driver.findElement(By.xpath("//button[@data-qe-id='action-button-payment']")).click();		
		}
		
		if(selectplangroup.equalsIgnoreCase("Students and Teachers") || selectplangroup.equalsIgnoreCase("Schools and Universities"))
		{
			ScreenshotUtilities.takeScreenShot(driver,dataMap); 
			//dataMap.putAll(getCheckoutPgDetails(driver));                              //get details from checkout page like product name and price details.	
			JavascriptExecutor jse2 = ((JavascriptExecutor) driver);
			jse2.executeScript("window.scrollTo(0, document.body.scrollHeight);");					
			driver.findElement(By.xpath("//button[@data-qe-id='action-button-payment']")).click();                                                 //Click continue button											
			boolean selectareainstr = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//div[@data-qid='fieldOfStudy']"),driver,"Area of Instruction field not available");
			if(selectareainstr)
			{
				driver.findElement(By.xpath("//div[@data-qid='fieldOfStudy']")).click();
				driver.findElement(By.xpath("//li[@data-value='ART_PHOTOGRAPHY_THEATER_DRAMA']")).click();
			}
			GenericFunctions.waitInSeconds(2);	
			if(selectplangroup.equalsIgnoreCase("Students and Teachers"))
			{
				//if(dataMap.get(Constants.Education_Status).contains(Constants.student))
				//{
					//Select graduationMonth
					driver.findElement(By.xpath("//div[@data-qid='graduationMonth']")).click();
			        driver.findElement(By.xpath("(//span[@class='month-left'])[position()=4]")).click();
					//Select graduationYear
					driver.findElement(By.xpath("//div[@data-qid='graduationYear']")).click();
					driver.findElement(By.xpath("//li[@data-value='2020']")).click();						
				//}
			}
			boolean schoolname = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//input[@data-qid='schoolName']"),driver,"School name field not available");
			if(schoolname)
			{
				driver.findElement(By.xpath("//input[@data-qid='schoolName']")).sendKeys("OxfordOxford");
			}
			else
			{
				boolean schoolname1 = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//div[@data-qid='schoolName']"),driver,"School name field not available");
				if(schoolname1)
				{
					WebElement ele = driver.findElement(By.xpath("//div[@data-qid='schoolName']"));
					ele.findElement(By.xpath("(//input[@type='text'])[position()=3]")).sendKeys("OxfordOxford");
				}
			}					
			ScreenshotUtilities.takeScreenShot(driver,dataMap);
			GenericFunctions.waitInSeconds(5);	
			JavascriptExecutor jse = ((JavascriptExecutor) driver);
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
			driver.findElement(By.xpath("//button[@data-qe-id='action-button-academic']")).click(); 
		}
		else if(selectplangroup.equalsIgnoreCase("Individuals"))
		{
			ScreenshotUtilities.takeScreenShot(driver,dataMap); 
			//dataMap.putAll(getCheckoutPgDetails(driver));                                                                                 //get details from checkout page like product name and price details.
			JavascriptExecutor jse = ((JavascriptExecutor) driver);
			jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
			driver.findElement(By.xpath("//button[@data-qe-id='action-button-payment']")).click();			                               //Click placeorder button.
		}					                                              			
		GenericFunctions.waitInSeconds(16);
		ScreenshotUtilities.takeScreenShot(driver,dataMap); 		
	 }
	 
	 public void getOrderNumber(Map<String,String> dataMap,WebDriver driver) throws Exception 
	 {
		 boolean orderstatus = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("(//span[@class='bold-detail'])[position()=2]"),driver,"Order number not available");  	//Get Order number
		 if(orderstatus)
		 {
			String Ordernumber = driver.findElement(By.xpath("(//span[@class='bold-detail'])[position()=2]")).getText();                           //Get Order number from the Page.
			dataMap.put(Constants.orderNumber, Ordernumber);
			LOGGER.info("********************* Order Placed successfully, Order Number - "+Ordernumber+"  ********************************************");
			boolean trialdetailsOrderConfPg = BrowserUIUtils.checkElementExistsUsingDriver(By.xpath("//div[@data-qe-id='confirmation-banner-container']/div[@class='banner-text']"),driver,"Trial details not available in order confirmation page");
			if(trialdetailsOrderConfPg)
			{
			     String text = driver.findElement(By.xpath("//div[@data-qe-id='confirmation-banner-container']/div[@class='banner-text']")).getText();
			     if(text.contains("trial"))
				 {
					dataMap.put("TrialOrderConfPgStatus", "PASS - "+text);
				 }
				 else
				 {
					dataMap.put("TrialOrderConfPgStatus", "FAIL - Trial text not contain in the banner of Thank you message :"+text);
				 }
		    }
			else
			{
				dataMap.put("TrialOrderConfPgStatus", "FAIL - Thankyou message not displaying in the order confirmation page");
			}
			String trialdetails = driver.findElement(By.xpath("(//span[@class='bold-detail'])[position()=4]")).getText();
			dataMap.put("TrialDetailsOrderConfPg","You'll be charged: "+ trialdetails);
		}
		else
		{
			LOGGER.info("***************************** Order number not available ,showing some error message.check Screenshot ***************************");
			dataMap.put(Constants.Comments,"FAIL - Order number not available ,showing some error message.check Screenshot");
			dataMap.put(Constants.orderNumber, "FAIL - Order number not available");
		}
		BrowserUIUtils.waitForElementUsingDriver(By.id("confirmation-action-button"),5,driver).click();                                                              //Set Password
		BrowserUIUtils.waitForElementUsingDriver(By.id("password"),5,driver).sendKeys(dataMap.get(Constants.Password));                 //Enter Password value in the field
		GenericFunctions.waitInSeconds(5);
		driver.findElement(By.id("create_account")).click();                                                                                   //Click Create account button.
		GenericFunctions.waitInSeconds(8);
	 }

	 public void ValidationOutputResultsSheet(ArrayList<Map<String, String>> validationDetails,Map<String, String> dataMap,String className) throws WriteException
	 {		
   		ArrayList<String> ColoumnNamesKeyListSheet1 = new ArrayList<String>();
	
   		ColoumnNamesKeyListSheet1.add("OfferID"); 		
   		ColoumnNamesKeyListSheet1.add(selectplangroup);
   		ColoumnNamesKeyListSheet1.add(Constants.EMAIL_ID);
   		ColoumnNamesKeyListSheet1.add(Constants.Password);
   		ColoumnNamesKeyListSheet1.add("Country");
   		ColoumnNamesKeyListSheet1.add("ProductName");
   		ColoumnNamesKeyListSheet1.add(Constants.orderNumber);
   		ColoumnNamesKeyListSheet1.add("Cart_TrialTotalDiscountAmount");
   		ColoumnNamesKeyListSheet1.add("CO_TrialTotalDiscountAmount");
   		ColoumnNamesKeyListSheet1.add("EmailPgBannerStatus");
   		ColoumnNamesKeyListSheet1.add("EmailPgCartItemBannerStatus");
   		ColoumnNamesKeyListSheet1.add("COPgBannerStatus");
   		ColoumnNamesKeyListSheet1.add("COPgCartItemBannerStatus");
   		ColoumnNamesKeyListSheet1.add("TrialDetailsOrderConfPg");
   		ColoumnNamesKeyListSheet1.add(Constants.Comments);
   		  		   		
		WritableCellFormat w = new WritableCellFormat();
		w.setBackground(Colour.RED);
		String os = System.getProperty("os.name").toLowerCase();
		String filepath="";
		if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
			filepath=Constants.ONB_TrialOrderUI_FOLDER_LINUX;
		}
		else if (os.indexOf("win") >= 0) {
			filepath=Constants.ONB_TrialOrderUI_FOLDER_WIN;
		}
		try
		{
			if(pouiFlag==0){
				pouiFile = ExcelUtilities.outputToSharedFolder(filepath,dataMap,className);
				WritableWorkbook workbook = Workbook.createWorkbook(new File(pouiFile
						));

				WritableSheet sheet1 = workbook.createSheet(
						"UI_TrialOrderValidation_Results", 0);
				
				int i = 0, j = 0;
					for (i = 0; i < validationDetails.size() + 1; i++) {
						for (j = 0; j < ColoumnNamesKeyListSheet1.size(); j++) {
							if (i == 0) {
								Label label = new Label(j, i, ColoumnNamesKeyListSheet1.get(j));
								sheet1.addCell(label);
							} else {
								if(validationDetails.get(i-1).get(ColoumnNamesKeyListSheet1.get(j)) == null)
								{
									Label label = new Label(j, i,
											validationDetails.get(i - 1).get(ColoumnNamesKeyListSheet1.get(j)));
									sheet1.addCell(label);
								}
								else
								{
									if(validationDetails.get(i-1).get(ColoumnNamesKeyListSheet1.get(j)).contains("FAIL"))
									{
										Label label = new Label(j, i,
												validationDetails.get(i - 1).get(ColoumnNamesKeyListSheet1.get(j)),w);
										sheet1.addCell(label);
									}
									else
									{
										Label label = new Label(j, i,
												validationDetails.get(i - 1).get(ColoumnNamesKeyListSheet1.get(j)));
										sheet1.addCell(label);
									}
								}
							}
						}
					}
				pouiFlag=1;
				workbook.write();
				workbook.close();
			}
			else
			{
				Workbook workbook = null;

				try {
					workbook = Workbook.getWorkbook(new File(pouiFile));
				} catch (BiffException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new Exception("Exception while writing in Excel output :"+e.getMessage());
				}

				WritableWorkbook copy;

				copy = Workbook.createWorkbook(new File(pouiFile), workbook);
				WritableSheet sheet5 = copy.getSheet(0);
				
				int rows = sheet5.getRows();
				for (int i = 0; i < validationDetails.size(); i++) {
					for (int j = 0; j < ColoumnNamesKeyListSheet1.size(); j++) {
						if(validationDetails.get(i).get(ColoumnNamesKeyListSheet1.get(j)) == null)
						{							
							Label label = new Label(j, rows + i,
									validationDetails.get(i).get(ColoumnNamesKeyListSheet1.get(j)));
							sheet5.addCell(label);
						}
						else
						{
							if(validationDetails.get(i).get(ColoumnNamesKeyListSheet1.get(j)).contains("FAIL"))
							{
								 Label label = new Label(j, rows + i, validationDetails.get(i)
												.get(ColoumnNamesKeyListSheet1.get(j)),w);
								sheet5.addCell(label);
							}
							else
							{
								Label label = new Label(j, rows + i,
										validationDetails.get(i).get(ColoumnNamesKeyListSheet1.get(j)));
								sheet5.addCell(label);
							}
						}
					}
				}
				copy.write();
				copy.close();
			}
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	 }
	
}

