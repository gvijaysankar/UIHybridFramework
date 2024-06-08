package TestAutomation.Utilities.utility;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.UnexpectedAlertBehaviour;
//import org.openqa.selenium.SeleneseCommandExecutor;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.server.SeleniumServer;


public abstract class BrowserUIUtils {
	public static String gridHubUrl = null;

	static {
		gridHubUrl = getGridHubUrl();
	}
	protected WebDriver driver;

	// For use with Selenium 1 browsers not supported by Selenium 2
	private static SeleniumServer seleniumServer;
	//private static RemoteControlConfiguration remoteControlConfiguration;
	private static Logger LOGGER = Logger.getLogger(BrowserUIUtils.class);

	public enum Driver {
		FIREFOX,
		CHROME,
		IE,
		SAFARI,
		HTMLUNIT;
	}

	public BrowserUIUtils(WebDriver driver) {
		this.driver = driver;
	}

	@SuppressWarnings({ "deprecation", "unused" })
	public static WebDriver getNewDriver(Driver driverType, String browserVersion, String platform) throws Exception{
		DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
		
		if (null==gridHubUrl) {
			//running on local
			desiredCapabilities.setAcceptInsecureCerts(true);
		}
		else {
			//running on Selenium Grid
			if(platform.equals("XP")){
				desiredCapabilities.setPlatform(Platform.XP);
			}
			else if(platform.equals("VISTA")){ 
				desiredCapabilities.setPlatform(Platform.VISTA);
			}
			else if(platform.equals("ANDROID")){ 
				desiredCapabilities.setPlatform(Platform.ANDROID);
			}
			desiredCapabilities.setVersion(browserVersion);
			desiredCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);	
		}
		
		switch (driverType) {
		
		case FIREFOX:
			// Disable Native Events on Windows for Firefox Driver.
			try {
				FirefoxProfile firefoxProfile = new FirefoxProfile();
				firefoxProfile.setAcceptUntrustedCertificates(false);
				desiredCapabilities.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
				
				if (null==gridHubUrl) {
					//running on local
					return new FirefoxDriver(desiredCapabilities);
				}
				else{
					//running on Selenium Grid
					//desiredCapabilities.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
					desiredCapabilities.setBrowserName("firsefox");
					desiredCapabilities.setPlatform(Platform.ANY);
					FirefoxOptions options=new FirefoxOptions();
		    		options.merge(desiredCapabilities);
					return new RemoteWebDriver(new URL(gridHubUrl), desiredCapabilities);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		case CHROME:
			if (null==System.getProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY)){
				System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,"chromedriver.exe");
			}
			try {
				if (null==gridHubUrl){
					//running on local
					return new ChromeDriver(desiredCapabilities);
				}
				else{
					//running on Selenium Grid					
					//desiredCapabilities.setBrowserName(DesiredCapabilities.chrome().getBrowserName());
					desiredCapabilities.setBrowserName("chrome");
					desiredCapabilities.setPlatform(Platform.ANY);
					desiredCapabilities.setAcceptInsecureCerts(true);
					ChromeOptions options=new ChromeOptions();
					options.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
			                  UnexpectedAlertBehaviour.IGNORE);
					options.addArguments("--start-maximized");
		    		options.merge(desiredCapabilities);
					return new RemoteWebDriver(new URL(gridHubUrl), options);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		case IE:
			try {
				if (null==gridHubUrl){
					//running on local
					return new InternetExplorerDriver(desiredCapabilities);
				}
				else{
					//running on Selenium Grid
					desiredCapabilities.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
					desiredCapabilities.setCapability("requireWindowFocus", true);
					return new RemoteWebDriver(new URL(gridHubUrl), desiredCapabilities);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			}
		case SAFARI:
			try{
				//initSeleniumRC();
				//Selenium sel = new DefaultSelenium("localhost", 4444, "*safari", UrlUtil.getDayBase());
		//		CommandExecutor executor = new SeleneseCommandExecutor(sel);
				DesiredCapabilities dc = new DesiredCapabilities();
		//		return new RemoteWebDriver(executor, dc);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		case HTMLUNIT:
			return new HtmlUnitDriver(true);
		default:
			throw new Exception("You must choose one of the defined driver types");
		}
	}

	public static String getGridHubUrl(){
		String gridHubUrl = null;
		if(null!=System.getProperty(Constants.GRID_HUB_URL)){
			gridHubUrl = System.getProperty(Constants.GRID_HUB_URL);
		}
		return gridHubUrl;
	}
	public static WebDriver getNewDriver(String browser) throws Exception{
		return getNewDriver(Driver.valueOf(browser.toUpperCase()),"","");
	}

	public static WebDriver getNewDriver(String browser, String browserVersion, String platform) throws Exception{
		return getNewDriver(Driver.valueOf(browser.toUpperCase()), browserVersion, platform);
	}
	
	public WebDriver getDriver(){
		return driver;
	}

	public abstract void open() throws DataSourceException;

	/**
	 * Opens a page using the given relative URL, which must be relative to the prefix provided by UrlUtil.getDayBase().
	 * For example, if UrlUtil.getDayBase() returns "http://dev-c.wcms-d01.corp.adobe.com/" and relativeURL is "products/catalog.html",
	 * the URL opened will be "http://dev-c.wcms-d01.corp.adobe.com/products/catalog.html".
	 * 
	 * @param relativeURL
	 */
	public void open(String relativeURL) throws DataSourceException {
		driver.get(DatabaseUtil.getDayBase() + relativeURL);
	}

	public void close(){
		Set<String> handles = driver.getWindowHandles();
		if (handles.size()>1){
			handles.remove(driver.getWindowHandle());
			driver.close();
			driver.switchTo().window(driver.getWindowHandles().iterator().next());
		} else {
			driver.close();
		}

	}

	/**
	 * Wait for the given element to appear for up to 60 seconds [during dead slow environments]
	 * @param by the locator for a 
	 */
	
	/**
	 * Wait for the given element to appear
	 * @param by the locator for a 
	 */
	public WebElement waitForElementOtherBrowsers(By by, int maxTimeSeconds){
		int loops = maxTimeSeconds*4;
		for (int i=0; i<loops; i++){
			LOGGER.debug("Waiting for element :: "+i);
			try {
				WebElement element = driver.findElement(by);
				if (element.isDisplayed()){
					return element;
				}
				try {
					Thread.sleep(250L);
				} catch (InterruptedException e) {
					// try again;
				}
			} catch (Exception e) {
				// try again
				try {
					Thread.sleep(250L);
				} catch (InterruptedException e1) {
					// try again;
				}
			}
		}
		return null;
	}
	

	private void seleniumHiddenElementWorkaround(WebElement element) {
		// js hack for selenium not interacting with elements with 0px height
		if (!"".equals(element.getAttribute("id"))&&"0px".equals(element.getCssValue("height"))){
			((JavascriptExecutor)driver).executeScript("document.getElementById(\""+element.getAttribute("id")+"\").style.height = \"1px\";");
		}
	}
	

	public String getTitle(){
		return driver.getTitle();
	}
	
	/**
	 * Wait for the given element to disappear for up to 30 seconds
	 * @param by the locator for a 
	 */
	public void waitForElementToDisappear(WebElement element){
		waitForElementToDisappear(element, 30);
	}

	/**
	 * Wait for the given element to disappear for up to 30 seconds
	 * @param by the locator for a 
	 */
	public void waitForElementToDisappear(By by){
		waitForElementToDisappear(by, 30);
	}

	/**
	 * Wait for the given element to disappear
	 * @param by the locator for a 
	 */
	public void waitForElementToDisappear(By by, int maxTimeSeconds){
		int loops = maxTimeSeconds*4;
		for (int i=0; i<loops; i++){
			try {
				WebElement element = null;
				List<WebElement> elements = driver.findElements(by);
				for (WebElement e : elements){
					if (e.isDisplayed()){
						element = e;
					}
				}
				if (!element.isDisplayed()){
					return;
				}
				try {
					Thread.sleep(250L);
				} catch (InterruptedException e) {
					// try again;
				}
			} catch (Exception e) {
				return;
			}
		}
	}
	
	/**
	 * Wait for the given element to disappear
	 * @param by the locator for a 
	 */
	public void waitForElementToDisappear(WebElement element, int maxTimeSeconds){
		int loops = maxTimeSeconds*4;
		for (int i=0; i<loops; i++){
			try {
				if (!element.isDisplayed()){
					return;
				}
				try {
					Thread.sleep(250L);
				} catch (InterruptedException e) {
					// try again;
				}
			} catch (Exception e) {
				return;
			}
		}
	}
	
	 //Check gettext value from webelement
	 public static String getTextValueUsingWebelement(WebElement ele)
	 {
		 String value = "null";
		 try
		 {
			 value = ele.getText();
		 }
		 catch(Exception e)
		 {
			 value = "Field not available in UI";
		 }
		 return value;
	 }
	 
	 //check element Exists in the Page using webElement
	 public static boolean checkElementExistsUsingWebelement(WebElement ele)
	 { 
		 try
		 {		 
		   boolean element = ele.isDisplayed();
		   return element;
		 }
		 catch(Exception e)
		 {
			 return false;
		 }
	 }
	 
		
	 //check element Exists in the Page using driver
	 public static boolean checkElementExistsUsingDriver(By by,WebDriver driver,String msg)
	 { 
		 try
		 {
			 GenericFunctions.waitInSeconds(6);
		   boolean element = driver.findElement(by).isDisplayed();
		   return element;
		 }
		 catch(Exception e)
		 {
			 System.out.println(msg);
			 return false;
		 }
	 }
	 
	//Using driver
		public static WebElement waitForElementUsingDriver(By by, int maxLoop,WebDriver driver)
		{
			int loops = maxLoop;
			WebElement element = null;
			System.out.println("Waiting for the element with By : "+ by);
			for (int i=0; i<loops; i++){
				try {
					element = driver.findElement(by);
					if(element !=null)
					{
						if (element.isDisplayed())
						{
							return element;
						}
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {

					}
				} 
				catch (Exception e) 
				{
					try {
						Thread.sleep(5000);
					} 
					catch (InterruptedException e1) {

					}
				}
			}
			return element;
		}
		

		public static WebElement waitForElementUsingwebElement(WebElement element, int maxLoop,WebDriver driver)
		{
			int loops = maxLoop;
			
			for (int i=0; i<loops; i++){
				try {
					
					if(element !=null)
					{
						if (element.isDisplayed())
						{
							return element;
						}
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {

					}
				} 
				catch (Exception e) 
				{
					try {
						Thread.sleep(5000);
					} 
					catch (InterruptedException e1) {

					}
				}
			}
			return element;
		}
		
		 public static boolean checkElementExistsinPage(String input,WebDriver driver,int wait)
		 { 
			 try
			 {
			   GenericFunctions.waitInSeconds(wait);
			   boolean element = driver.findElement(By.xpath(input)).isDisplayed();
			   return element;
			 }
			 catch(Exception e)
			 {			 
				  return false;
			 }
		 }
		 
		 public static WebElement waitForElement(By by, int maxLoop,WebDriver driver)
			{
				int loops = maxLoop;
				WebElement element = null;
				System.out.println("Waiting for the element with By : "+ by);
				for (int i=0; i<loops; i++){
					try {
						element = driver.findElement(by);
						if(element !=null)
						{
							if (element.isDisplayed())
							{
								return element;
							}
						}
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {

						}
					} 
					catch (Exception e) 
					{
						try {
							Thread.sleep(5000);
						} 
						catch (InterruptedException e1) {

						}
					}
				}
				return element;
			}
		
		public static boolean waitForElement(String input, WebDriver driver,int maxLoop)
		{
			int loops = maxLoop;
			boolean element = false;
			WebElement element1=null;
			System.out.println("Waiting for the element with By : "+ input);
			for (int i=0; i<loops; i++){
				try {
					 element1 = driver.findElement(By.xpath(input));
					if(element1 !=null)
					{
						if (element1.isDisplayed())
						{
							return true;
						}
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {

					}
				} 
				catch (Exception e) 
				{
					try {
						Thread.sleep(5000);
					} 
					catch (InterruptedException e1) {

					}
				}
			}
			return element;
		}
		
		//Using WebElement
		public static WebElement waitForElemeṄntUsingWebElement(By by, int maxLoop,WebElement ele)
		{
			int loops = maxLoop;
			WebElement element = null;
			System.out.println("Waiting for the element with By : "+ by);
			for (int i=0; i<loops; i++){
				try {
					element = ele.findElement(by);
					if(element !=null)
					{
						if (element.isDisplayed())
						{
							return element;
						}
					}
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {

					}
				} 
				catch (Exception e) 
				{
					try {
						Thread.sleep(5000);
					} 
					catch (InterruptedException e1) {

					}
				}
			}
			return element;
		}
		

		 
		 //Checking change region popup exists or not,If exists closing the Popup
		 public static void changeRegionPopupExist(WebDriver driver)
		 {
			try
			{
			    List<WebElement> elements = driver.findElements(By.xpath("//div[@class='modal-frame']"));
				if(elements !=null)
				{
				  for (WebElement element : elements)
				     {
					    //seleniumHiddenElementWorkaround(element);
					    // if (element.isDisplayed()){
						if (element.isDisplayed())
						{
						   //element.sendKeys(Keys.ESCAPE);
						   element.findElement(By.xpath("//span[@class ='close']/a")).click();	
						}
					}
				}
			}
			catch(Exception e)
			{
				
			}
			
		}
			
			public static boolean waitForElement_oneconsole_id(String input, WebDriver driver,int maxLoop)
			{
				int loops = maxLoop;
				boolean element = false;
				WebElement element1=null;
				System.out.println("Waiting for the element with By : "+ input);
				for (int i=0; i<loops; i++){
					try {
						 element1 = driver.findElement(By.id(input));
						if(element1 !=null)
						{
							if (element1.isDisplayed())
							{
								return true;
							}
						}
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {

						}
					} 
					catch (Exception e) 
					{
						try {
							Thread.sleep(5000);
						} 
						catch (InterruptedException e1) {

						}
					}
				}
				return element;
			}

			public static boolean waitForElement_css(String input, WebDriver driver,int maxLoop)
			{
				int loops = maxLoop;
				boolean element = false;
				WebElement element1=null;
				System.out.println("Waiting for the element with By : "+ input);
				for (int i=0; i<loops; i++){
					try {
						 element1 = driver.findElement(By.cssSelector(input));
						if(element1 !=null)
						{
							if (element1.isDisplayed())
							{
								return true;
							}
						}
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {

						}
					} 
					catch (Exception e) 
					{
						try {
							Thread.sleep(5000);
						} 
						catch (InterruptedException e1) {

						}
					}
				}
				return element;
			}
			
			public static boolean waitForElement_xpath(String input, WebDriver driver,int maxLoop)
			{
				int loops = maxLoop;
				boolean element = false;
				WebElement element1=null;
				System.out.println("Waiting for the element with By : "+ input);
				for (int i=0; i<loops; i++){
					try {
						 element1 = driver.findElement(By.xpath(input));
						if(element1 !=null)
						{
							if (element1.isDisplayed())
							{
								return true;
							}
						}
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {

						}
					} 
					catch (Exception e) 
					{
						try {
							Thread.sleep(5000);
						} 
						catch (InterruptedException e1) {

						}
					}
				}
				return element;
			}

		
		

}
