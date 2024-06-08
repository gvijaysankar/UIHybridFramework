package TestAutomation.Utilities.Reports;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.testng.Reporter;

import TestAutomation.Scripts.Suite.TrialOrderplacementValidationTest;
import TestAutomation.Utilities.utility.BrowserUIUtils;
import TestAutomation.Utilities.utility.Constants;
import TestAutomation.Utilities.utility.DataSourceException;
import TestAutomation.Utilities.utility.GenericFunctions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;





public class ScreenshotUtilities {

	private static Logger LOGGER = Logger.getLogger(ScreenshotUtilities.class);
	
	public static String takeScreenShot(WebDriver driver,Map<String,String> dataMap) throws DataSourceException, IOException{
			WebDriver augmentedDriver = null;
			String returnUrl = null;
			try{
				String userReadableName ="studentflow";		
				String completeUrl = null;
				String fileNameWithPath = null;
				String fileName = null;
				String filePath = null;
				String urlPath = null;
				String currentUrl = null;
				String htmlUrl = null;
				String iFrameUrl = null;

				if(driver!=null){
					if (null==BrowserUIUtils.getGridHubUrl()){
						
						augmentedDriver = driver;
					}
					else
					{
						
							augmentedDriver = new Augmenter().augment(driver);
					}

					if (null==BrowserUIUtils.getGridHubUrl()){
						filePath = Constants.WIN_SCREENSHOT_FILE_URL_PATH;
						urlPath = Constants.WIN_SCREENSHOT_FILE_URL_PATH;
					}
					else{
						String os = System.getProperty("os.name").toLowerCase();

						// linux or unix
						if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
							filePath = Constants.LINUX_SCREENSHOT_FILE_PATH;
							urlPath = Constants.LINUX_SCREENSHOT_URL_PATH;
						}
						else if (os.indexOf("win") >= 0) {
							filePath = Constants.WIN_SCREENSHOT_FILE_URL_PATH;
							urlPath = Constants.WIN_SCREENSHOT_FILE_URL_PATH;
						}
					}
					String timestamp = new SimpleDateFormat("dd-MM-yyyy_HHmm").format(Calendar.getInstance().getTime());


					String date = (timestamp.replaceAll(timestamp.split("_")[1],"")).replaceAll("_", "");
					File folder = new File(filePath+"/"+date);
					if(!folder.exists()){
						folder.mkdir();
					}


					File scrFile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
					System.out.println("Snapshot scrFile :"+scrFile);
					augmentedDriver.manage().window().maximize();
					fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date())
							+".png";
					
					fileNameWithPath = folder.getAbsolutePath() + "/" + userReadableName+"_"+fileName;				
					FileUtils.copyFile(scrFile, new File(fileNameWithPath));
					//completeUrl = urlPath + "/" + fileName;
					completeUrl = fileNameWithPath;

					currentUrl = augmentedDriver.getCurrentUrl();

					//URL to print
					Reporter.log("<h4 style='color:red'> <b>Screenshot URL:</b> <a href='"+currentUrl+"' target='_blank'>"+currentUrl+"</a> </h4>");
					scrFile.delete();

					//Screen shot to be opened in new window
					htmlUrl = "<h3 style='color:red'><b>Analyze Exceptions with below Screenshot</b> &nbsp; &nbsp; &nbsp; OR &nbsp; &nbsp; &nbsp; <a href='"+completeUrl+"' target='_blank'>Click Here to open Screenshot in New Tab/Window</a></h3>";
					Reporter.log(htmlUrl);

					//iFrame in current window
					iFrameUrl = "<iframe name='inlineframe' src='"+completeUrl+"' frameborder='0' scrolling='auto' width='1024' height='180' marginwidth='5' marginheight='5'></iframe>";
					Reporter.log(iFrameUrl);
					returnUrl = completeUrl;
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
			finally{
			}
			return returnUrl;
		}
	
}

