package TestAutomation.Utilities.utility;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.testng.Reporter;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.security.InvalidKeyException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;





public class GenericFunctions {
	private static Logger LOGGER = Logger.getLogger(GenericFunctions.class);
	public static ArrayList<Map<String,String>> outPutDataList = new ArrayList<Map<String,String>>();
	static String val = "";
	
	 public static String GetHomeDir(){
       String homeDir="";
	    if (isWinOS()){
	        homeDir = System.getProperty("user.home");
	    }
	    else{
	    	  homeDir = System.getProperty("user.home");
	    }
	    //test.log("The current user home directory: " + homeDir);
	    return homeDir;
	}
	 
	 public static boolean isWinOS(){
		 String oSName = System.getProperty("os.name");
		 if ( oSName.indexOf("WIn") > -1 ){
		        return true;
		    }
		    return false;
		 
	 }
	 
		public static String getData(Map<String,Map<String,String>> data,String mapkey,String valuekey)		
		{
			String value = "NULL";
			try
			{
				value = data.get(mapkey).get(valuekey);
				if(value.isEmpty() || value.equalsIgnoreCase("null"))
				{
					value = "Null";
				}
			}
			catch(Exception e)
			{
				value = "Key Not Available";
			}
			return value;
		}	

		
		
		public static String checkJSONObjectKeyStatus(JSONObject obj,String obj1,String key)
		{
			String value = "NULL";
			try
			{
				value = obj.getJSONObject(obj1).getString(key).toString();
				if(value.isEmpty() || value.equalsIgnoreCase("null"))
				{
					value = "Null";
				}
			}
			catch(Exception e)
			{
				value = "FAIL - Key Not Available";
			}
			return value;
		}
		
		public static String checkJSONObjectKeyStatus(JSONObject obj,String obj1,String obj2,String key)
		{
			String value = "NULL";
			try
			{
				value = obj.getJSONObject(obj1).getJSONObject(obj2).getString(key).toString();
				if(value.isEmpty() || value.equalsIgnoreCase("null"))
				{
					value = "Null";
				}
			}
			catch(Exception e)
			{
				value = "FAIL - Key Not Available";
			}
			return value;
		}
		
		public static String checkJSONObjectKeyStatus(JSONObject obj,String obj1,String obj2,String obj3,String key)
		{
			String value = "NULL";
			try
			{
				value = obj.getJSONObject(obj1).getJSONObject(obj2).getJSONObject(obj3).getString(key).toString();
				if(value.isEmpty() || value.equalsIgnoreCase("null"))
				{
					value = "Null";
				}
			}
			catch(Exception e)
			{
				value = "FAIL - Key Not Available";
			}
			return value;
		}
		
		public static JSONArray checkJSONArrayKeyStatus(JSONObject obj,String obj1)
		{
			JSONArray value = new JSONArray();
			try
			{
				value = obj.getJSONArray(obj1);
				if(value.isEmpty() || value.contains("null"))
				{
					value.add("No Data available");
				}
			}
			catch(Exception e)
			{
				value.add("Key Not Available");
			}
			return value;
		}
		
		
		
		public static StringBuilder getDataFrmArray(ArrayList<String> value)
		{
			StringBuilder sb = new StringBuilder();
			if(!(value.isEmpty()))
			{
			    for(int g=0;g<value.size();g++)
			    {
			    	sb.append(value.get(g)).append(",");
			    }
			    sb.deleteCharAt(sb.lastIndexOf(","));	
		    }
		    return sb;
		}
		
		public static String getArrayData(String val)
		{
			String value = "NULL";
				if(val.isEmpty() || val.equalsIgnoreCase("null"))
				{
					value = "Null";
				}
				else
				{
					value = val;
				}
			return value;	
		}
		
		public static ArrayList<String> splitStringValue(String data)
		{
			ArrayList<String> value = new ArrayList<String>();
			if(!(data == null || data.isEmpty()))
			{			
				if (!(data).isEmpty()) {
					if (data.contains(",")) {
						StringTokenizer st = new StringTokenizer(
								data, ",");
						while (st.hasMoreTokens()) {
							String splitval = st.nextToken();
							value.add(splitval.trim());
		
						}
					}
					else
					{
						value.add(data.trim());
					}
				}
			}
			return value;
		}
		
		public static ArrayList<String> splitSemiColonStringValue(String data)
		{
			ArrayList<String> value = new ArrayList<String>();
			if(!(data == null || data.isEmpty()))
			{			
				if (!(data).isEmpty()) {
					if (data.contains(";")) {
						StringTokenizer st = new StringTokenizer(
								data, ";");
						while (st.hasMoreTokens()) {
							String splitval = st.nextToken();
							value.add(splitval.trim());
		
						}
					}
					else
					{
						value.add(data.trim());
					}
				}
			}
			return value;
		}
		
		public static ArrayList<String> splitColonStringValue(String data)
		{
			ArrayList<String> value = new ArrayList<String>();
			if(!(data == null || data.isEmpty()))
			{			
				if (!(data).isEmpty()) {
					if (data.contains(":")) {
						StringTokenizer st = new StringTokenizer(
								data, ":");
						while (st.hasMoreTokens()) {
							String splitval = st.nextToken();
								value.add(splitval.trim());												
						}
					}
					else
					{
						value.add(data.trim());
					}
				}
			}
			return value;
		}

		public static String checkKeyStatus(JSONObject obj,String key)
		{
			String value = "NULL";
			try
			{
				value = obj.getString(key).toString();
				if(value.isEmpty() || value.equalsIgnoreCase("null"))
				{
					value = "Null";
				}
			}
			catch(Exception e)
			{
				value = "Key Not Available";
			}
			return value;
		}
		
		public static String validateStartEndDateKeys(String Value) 
		{
			String status = "";
		  try{			
			if(Value.equalsIgnoreCase("Key Not Available"))
			{
				status = "FAIL - Key Not Available";
			}
			else
			{
				if(!(Value.equalsIgnoreCase("NULL")))
			    {
			    	//get current date to validate
					Calendar calendar = Calendar.getInstance();
				    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-SSS");
				    String currentdate1 = dateFormat.format(calendar.getTime());
				    Date currentdate = (Date)dateFormat.parse(currentdate1); 
				    Date getdatefrmresponse = (Date)dateFormat.parse(Value); 
				    if(currentdate.before(getdatefrmresponse))
				    {
				    	status = "PASS - Date value Greaterthan current date - "+Value;
				    }
				    else
				    {
				    	status = "FAIL - Date value lessthan the current date - "+Value;
				    }
			    }
			    else
			    {
			    	status = "PASS";
			    }		
			}
			
		  }
		  catch(Exception e)
		  {
			  status = "Throws some error msg - Not validated";
		  }
			  
		  return status;	
		}
		
		public static String checkKeyandValue(String key, JsonElement jsonElement)
		{				
			if (jsonElement.isJsonArray()) {
	            for (JsonElement jsonElement1 : jsonElement.getAsJsonArray()) {
	            	checkKeyandValue(key, jsonElement1);
	            }
	        } else {
	            if (jsonElement.isJsonObject()) {
	                Set<Map.Entry<String, JsonElement>> entrySet = jsonElement
	                        .getAsJsonObject().entrySet();
	                for (Map.Entry<String, JsonElement> entry : entrySet) {
	                    String key1 = entry.getKey();
	                    if (key1.equals(key)) {
	                    	val = entry.getValue().toString();
	                    	if(val.equalsIgnoreCase("NULL"))
	                    	{
	                            return val;
	                    	}
	                    	else
	                    	{
	                    		String s = val.substring(0, 1);
	                    		boolean flag = s.matches(".*[a-zA-Z0-9]+.*");
	                    		if(flag)
	                    		{
	                    			 return val;
	                    		}
	                    		else
	                    		{
	                    			val = val.substring(1, val.length()-1); 
	                    			val = val.replaceAll("[\"]", "");
	                                return val;
	                    		}
	                    		
	                    	}
	                    	
	                    }
	                    checkKeyandValue(key, entry.getValue());
	                }
	            } else {
	                if (jsonElement.toString().equals(key)) {
	                	val = jsonElement.toString();  
	                	if(val.equalsIgnoreCase("NULL"))
	                	{
	                        return val;
	                	}
	                	else
	                	{
	                		String s = val.substring(0, 1);
	                		boolean flag = s.matches(".*[a-zA-Z0-9]+.*");
	                		if(flag)
	                		{
	                			 return val;
	                		}
	                		else
	                		{
	                			val = val.substring(1, val.length()-1); 
	                			val = val.replaceAll("[\"]", "");
	                            return val;
	                		}
	                	}
	                }
	            }
	        }
			return val;
		}
			

		
		public static String gnerateRandomNumber(int n)
		{
			String AlphaNumericString = "0123456789abcdefghijklmnopqrstuvxyz"+System.currentTimeMillis(); 
			StringBuilder sb = new StringBuilder(n); 
			for (int i = 0; i < n; i++) 
			{ 
				int index = (int)(AlphaNumericString.length() * Math.random()); 
				sb.append(AlphaNumericString .charAt(index)); 
			} 
			return sb.toString(); 
		}
		public static String gnerateRandomNo(int n)
		{
			StringBuilder sb = new StringBuilder(n);
			Random rnd = new Random();
		    for(int i=0; i < n; i++)
		        sb.append((char)('0' + rnd.nextInt(10)));
		    return sb.toString();
		}
		
		public static String gneraterandEmail()
		{		   	    
		    String emailid = "palleinstitute"+"_Auto_"+System.currentTimeMillis()+"@gmail.com";
		    return emailid;
		}
		public static String gneraterandAddress(String add)
		{		   	    
		    add = "Test"+gnerateRandomNo(5)+" "+add;
		    return add;
		}
		
		public static String generateEmailID(Map<String,String> dataMap,String value)
		{
			String newmailid = "";
			if(value.equalsIgnoreCase("GenerateCode"))
			 {
				 if(dataMap.get(Constants.EMAIL_ID).contains("@"))
				 {
					 newmailid = dataMap.get(Constants.EMAIL_ID);
				 }
				 else
				 {
					 newmailid = dataMap.get(Constants.EMAIL_ID)+"+Auto_"+gnerateRandomNumber(4)+"_"+System.currentTimeMillis()+"@gmail.com";
				 }
			 }
			else if(value.equalsIgnoreCase("TrialOrder") || value.equalsIgnoreCase("OrderPlacement"))
			{
				if(dataMap.get(Constants.EMAIL_ID).contains("@"))
				 {
					 newmailid = dataMap.get(Constants.EMAIL_ID);
				 }
				 else
				 {
					 newmailid = dataMap.get(Constants.EMAIL_ID)+dataMap.get("Country")+"_"+gnerateRandomNumber(4)+"_"+System.currentTimeMillis()+"@gmail.com";
				 }
			}
			else
			{
				if(dataMap.get(Constants.EMAIL_ID).contains("@"))
				 {
					 newmailid = dataMap.get(Constants.EMAIL_ID);
				 }
				 else
				 {
					 newmailid = dataMap.get(Constants.EMAIL_ID)+"_"+gnerateRandomNumber(4)+"_"+System.currentTimeMillis()+"@gmail.com";
				 }
			}		
			return newmailid;
		}
		
		public static String dateCompare(Map<String,String> dataMap)
		{
			String status = "NO";
			try
			{
				String datevalue = dataMap.get("Startdate");
				datevalue = datevalue.replace("+", "-");
				LOGGER.info(" *************** DATE IN SSCOM : "+datevalue+" **********************");	
				String calendar = "2020-07-01T08:01:00+00:00"; //2020-05-20T07:00:00-00:00
				calendar = calendar.replace("+", "-");
			    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-SSS");
			    Date givendate = (Date)dateFormat.parse(calendar);
			    LOGGER.info(" *************** Given DATE : "+givendate+" **********************");
			    Date getdatefrmresponse = (Date)dateFormat.parse(datevalue); 
			    if(givendate.before(getdatefrmresponse))
			    {
			    	status = "NO";
			    }
			    else
			    {
			    	status = "YES";
			    }
			}
			catch(Exception e)
			{
				status = "NO";
			}
			return status;
		}
		
		public static String generateDate()
		{
			Date dt = new Date(new Date().getTime() + 86400000);
			  Calendar calendar = Calendar.getInstance();
		      calendar.setTime(dt);
		      int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		      String[] dayNames = { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"  };
			  //System.out.println(dayNames[dayOfWeek-1]);
			  String tomorrows_day = dayNames[dayOfWeek-1];
			  if(tomorrows_day.equals("Sunday"))
			  {
				  calendar.add(calendar.DATE, 1);
				  dt = calendar.getTime();
				  System.out.println(dt);
			  }
			  int dayNumber = calendar.get(Calendar.DAY_OF_MONTH);
			  SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
		      String monthName = monthFormat.format(dt);
		      int yearNumber = calendar.get(Calendar.YEAR);
		      Reporter.log(dayNumber + "" + monthName + yearNumber);
		      String date_of_mock = dayNumber+ " " + monthName + " "+ yearNumber;
		      Reporter.log(date_of_mock);
		      return date_of_mock;
		}
		
		public static String compareMapsWithReturnDifference(Map<String,String> map1,Map<String,String> map2)
		{
			String errorStatus=Constants.TEST_RESULT_PASS;
			if(!map1.isEmpty()&&!map2.isEmpty()){
				MapDifference< String, String> mapdifference=Maps.difference(map1,map2);	
				if(mapdifference.areEqual()){
			
				}
				else
				{
					errorStatus = mapdifference.entriesDiffering().toString();
					Reporter.log("====>Map difference "+mapdifference.entriesDiffering());

				}
			}
			return errorStatus;
		}	
		
		public static boolean compareMaps(Map<String,String> map1,Map<String,String> map2)
		{
			boolean status=false;
			if(!map1.isEmpty()&&!map2.isEmpty()){
				MapDifference< String, String> mapdifference=Maps.difference(map1,map2);	
				if(mapdifference.areEqual()){
					status= true;
				}
				else
				{
					Reporter.log("====>Map difference "+mapdifference.entriesDiffering());

				}
			}
			return status;
		}
		
		public static Long getCurrentTime()
		{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d  HH:mm:ss z y");
			String strStartTime = sdf.format(cal.getTime()) ;
			Long startTimeMilliSec = cal.getTimeInMillis();
			return startTimeMilliSec;
		}
		
		public static String getTestCaseId(ArrayList<Map<String, String>> returnList, int i)
		{
			StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
			String testCaseId = stackTraceElements[3].getMethodName();

			if(System.getProperty("test.Case")==null)
			{
				testCaseId = testCaseId+"_1";
				System.setProperty("test.Case", testCaseId);
			}
			else
			{
				testCaseId = testCaseId+"_"+(Integer.parseInt(System.getProperty("test.Case").split("_")[1])+1);
				System.setProperty("test.Case", testCaseId);
			}

			return testCaseId;
			
		}
		
		public static ArrayList<Map<String,String>> updateListWithExecutionData(ArrayList<Map<String, String>> returnList,Map<String, String> dataMap) 
		{
			ArrayList<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
			if(!returnList.isEmpty())
			{
				for(int i=0; i<returnList.size(); i++)
				{
					Map<String, String> temp = returnList.get(i);
					for(String key : dataMap.keySet())
					{
						temp.put(key, dataMap.get(key));
					}		
					dataList.add(temp);
				}
			}
			else
			{
				returnList.add(dataMap);
				return returnList;
			}
			return dataList;
		}
		

		public static void updateDataMapAtStartUp(Map<String, String> dataMap) 
		{
			String testClassName = new Throwable().fillInStackTrace().getStackTrace()[1].getFileName();
			testClassName = testClassName.substring(0, testClassName.length()-5);
			dataMap.put(Constants.OPERATING_SYSTEM, "Windows 7");
			dataMap.put(Constants.BROWSER, "Chrome");
			dataMap.put(Constants.TEST_SUITE_NAME, "ONBSuite");

			//get the current start time
			//dataMap.put(CommonConstants.EXEC_START_TIME,UICommonHelper.getCurrentDate());
			dataMap.put(Constants.startTimeMilliSec,getCurrentTime().toString());
			//test could fail at any point of time - so, assume it fails
			dataMap.put(Constants.TESTRESULT,Constants.TEST_RESULT_FAIL);
			dataMap.put("PILLAR", "O2C");
			dataMap.put(Constants.TEST_CASE_DESCRIPTION, testClassName);
		}
		

		/**
		 * This method generates expiry date in the format of dd-mm-yyyy hh:mm::ss format by adding one second to the end date from the input sheet.
		 * 
		 * @param String date
		 * @return String expiryDate
		 */

		public static String getExpireDate(String date)
		{
			SimpleDateFormat dateformatter = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
			int dateValue=Integer.parseInt((date.substring(0, 2)));
			int month=Integer.parseInt((date.substring(3,5)));
			int year=Integer.parseInt((date.substring(6,10)));
			int hour=Integer.parseInt((date.substring(11,13)));
			int minute=Integer.parseInt((date.substring(14,16)));
			int second=Integer.parseInt((date.substring(17,19)));

			Calendar cal = Calendar.getInstance();

			cal.set(year, month-1, dateValue, hour, minute, second);
			cal.add(Calendar.SECOND, 1);
			//System.out.println("Current Date Time : " + dateformatter.format(cal.getTime()));
			//System.out.println();
			return dateformatter.format(cal.getTime());
			//System.out.println("Expired Sku url......"+expireSkuUrl);
		}

		/**
		 * This method generates valid date in the format of dd-mm-yyyy hh:mm::ss format by subtracting one second from the end date from the input sheet.
		 * 
		 * @param String date
		 * @return String validDate
		 */
		public static String getSuccessDate(String date)
		{
			SimpleDateFormat dateformatter = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
			int dateValue=Integer.parseInt((date.substring(0, 2)));
			int month=Integer.parseInt((date.substring(3,5)));
			int year=Integer.parseInt((date.substring(6,10)));
			int hour=Integer.parseInt((date.substring(11,13)));
			int minute=Integer.parseInt((date.substring(14,16)));
			int second=Integer.parseInt((date.substring(17,19)));

			Calendar cal = Calendar.getInstance();

			cal.set(year, month-1, dateValue, hour, minute, second);
			cal.add(Calendar.SECOND, -1);
			//System.out.println("Current Date Time : " + dateformatter.format(cal.getTime()));
			//System.out.println();
			return dateformatter.format(cal.getTime());
			//System.out.println("Expired Sku url......"+expireSkuUrl);
		}

		/**
		 * Method to truncate value to single digit decimal in the format of #.## 
		 * 
		 * @param Double number
		 * @return Double truncated number
		 */
		public static double truncateValue(double number)
		{
			double num = 0.0;
			DecimalFormat format=new DecimalFormat("#.##");
			num = Double.parseDouble(format.format(number));
			return num;
		}

		/**
		 * This method finds the canonical file path based on the file name
		 * 
		 * @param String fileName
		 * @return String filePath
		 */
		public static String getFilePath(String fileName) 
		{
			File f=new File(fileName);
			try {
				return (f.getCanonicalPath().replace("\\", "/").replace("/ui", ""));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		
		/**
		 * This method gets system date in the format of dd-mm-yyyy hh:mm:ss
		 * 
		 * @return Date systemDate
		 */
		public static Date getSystemDate()
		{
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
			Date date = new Date();
			Date daydate=getDateFromString((dateFormat.format(date)));
			return daydate;
		}


		/**
		 * This method gets current system date and time in the format of yyyy/MM/dd HH:mm:ss
		 * 
		 * @return Date date
		 */
		public static Date GetCurrentDateTime() 
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//get current date time with Date()
			Date date = new Date();
			/*		//get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			System.out.println(dateFormat.format(cal.getTime()));*/
			return date;

		}

		/**
		 * This method gets date from a string which is in the format of dd-mm-yyyy hh:mm:ss
		 * 
		 * @param String dateString
		 * @return Date date
		 */
		public static Date getDateFromString(String dateString)
		{
			DateFormat formatter ; 
			Date date = null ; 
			try { 
				String str_date= dateString;

				formatter = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss");
				date = (Date)formatter.parse(str_date);  
			} catch (ParseException e)
			{
				//System.out.println("Exception :"+e);
			}
			return date;
		}

		/**
		 * This metho converts data in an arraylist to a String by seperating the data using comma delimitter
		 * 
		 * @param ArrayList stringList
		 * @return String str
		 */
		public static String convertListToString(ArrayList<String> stringList)
		{
			String str = null;
			for(int i = 0;i < stringList.size();i++)
			{
				if(str == null || str.equals(""))
				{
					str = stringList.get(i);
				}
				else
				{
					str += ","+stringList.get(i);
				}
			}
			return str;
		}


		/**
		 * Method to add various output data to a common array list
		 * 
		 * @param ArrayList (outPutList) is the output obtained after a particular method execution
		 * @return ArrayList (outPutList) data to be send to a method which writes to xml
		 */
		public static ArrayList<Map<String,String>> addToList(ArrayList<Map<String,String>> outPutList)
		{
			outPutDataList.addAll(outPutList);

			return outPutList;

		}

		/**
		 * Getter method to return the ArrayList
		 * 
		 * @return ArrayList outPutDataList
		 */
		public static ArrayList<Map<String,String>> returnList()
		{

			return outPutDataList;

		}
		

		public static ArrayList<String> getUniqueArrayListData(ArrayList<String> list)
		{
			ArrayList<String> al=list;
			HashSet<String> hs = new HashSet<String>();
			hs.addAll(al);
			al.clear();
			al.addAll(hs);
			return al;
		}
		public static Map<Integer,String> removeHash(String hashString)
		{
			int hashIndex=hashString.indexOf("#");
			String firstString=hashString.substring(0,hashIndex-1);
			String secondString=hashString.substring(hashIndex+1,hashString.length());
			Map<Integer,String> hashRemovedMap=new HashMap<Integer,String>();
			hashRemovedMap.put(1, firstString);
			hashRemovedMap.put(2, secondString);
			return hashRemovedMap;
		}
		
		public static String getUTF8(String value) {
			String result = value;
			Charset charsetE = Charset.forName("iso-8859-1");
			CharsetEncoder encoder = charsetE.newEncoder();

			Charset charsetD = Charset.forName("UTF-8");
			CharsetDecoder decoder = charsetD.newDecoder();
			try {
				ByteBuffer bbuf = encoder.encode(CharBuffer.wrap(value));
				CharBuffer cbuf = decoder.decode(bbuf);
				result = cbuf.toString();
			} catch (Exception e) {

			}
			return result;

		}


		public static Map<String,String> compareMaps1(Map<String,String> map1,Map<String,String> map2)
		{
			Map<String,String> statusMap = new HashMap<String, String>(); 

			String mapstatus = "";
			StringBuilder sb = new StringBuilder();
			for (Entry<String, String> entry : map1.entrySet()) {
	            String key = entry.getKey();
	            System.out.println("key - " +key); //Added this line for debugging purpose
	            System.out.println("key " + key + ": "
	                    + entry.getValue().equals(map2.get(key)));
	            if(!(entry.getValue().equals(map2.get(key))))
	            {
	            	mapstatus = "{"+ key + " : (ExceptedResults : "+entry.getValue()+" , ActualResults : "+map2.get(key)+") }";
	            	sb.append(mapstatus).append(",");	
	            }                     
			}
			
			if(sb.length() == 0)
			{
				statusMap.put(Constants.TESTRESULT, "PASS");
				statusMap.put(Constants.ErrorStatus, "SUCCESS");
			}
			else
			{
				sb.deleteCharAt(sb.lastIndexOf(","));
				statusMap.put(Constants.TESTRESULT, "FAIL");
				statusMap.put(Constants.ErrorStatus,sb.toString());
				System.out.println("====>Map difference "+sb.toString());
			}
			return statusMap;
		}
		
		
		//split the String
		public static String splitString(String str) 
	    {
			 StringBuffer alpha = new StringBuffer(),  
				        num = new StringBuffer(), special = new StringBuffer(); 
			try
			{									
	        for (int i=0; i<str.length(); i++) 
	        {
	        	String value = String.valueOf(str.charAt(i));
	            if (Character.isDigit(str.charAt(i))) 
	                num.append(str.charAt(i)); 
	            else if(Character.isAlphabetic(str.charAt(i))) 
	                alpha.append(str.charAt(i));
	            else if(value.equalsIgnoreCase(",") || value.equalsIgnoreCase("."))
	            {
	            	num.append(str.charAt(i));
	            }
	            else
	                special.append(str.charAt(i)); 
	                
	        } 		       
	        System.out.println(alpha); 
	        System.out.println(num.toString()); 
	        System.out.println(special);         
			}
			catch(Exception e)
			{
				 num.append("Error Message");
				 return num.toString();
			}
			if(num.toString().startsWith(","))
			{
				num =  num.deleteCharAt(0);
			}
			if(num.toString().endsWith("."))
			{
				num =  num.deleteCharAt(num.length()-1);
				if(num.toString().endsWith("."))
				{
					num =  num.deleteCharAt(num.length()-1);
				}
			}
				
			return num.toString();
		
	    } 
		
		public static synchronized String decipher(String codedText) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException 
		{
			byte[] linebreak = {}; // Remove Base64 encoder default linebreak
			String secret = "tvnw63ufg9gh5392"; // secret key length must be 16 
			SecretKey key = null;
			Cipher cipher = null;
			Base64 coder = null;

			try {
				key = new SecretKeySpec(secret.getBytes(), "AES");
				cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
				coder = new Base64(32, linebreak, true);
			} 
			catch (Throwable t) 
			{
				t.printStackTrace();
			}

			byte[] encypted = coder.decode(codedText.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] decrypted = cipher.doFinal(encypted);  
			return new String(decrypted);
		}	
		
		//Returns the specified node in the parameter
		public static Object getJsonNode(Object jsonObj, String nodeName) {
			LOGGER.debug("==>getJsonNode()");
			Object node = null;

			if (jsonObj instanceof JSONArray)
			{
				JSONArray jsonArr = (JSONArray) jsonObj;
				for (int i = 0; i < jsonArr.size(); i++) {
					Object arrayElement = jsonArr.get(i);
					if (arrayElement instanceof JSONObject || arrayElement instanceof JSONArray) {
						node = getJsonNode(arrayElement, nodeName);
						if(node!=null)
						{
							return node;
						}
					}
				}
			} 
			else if (jsonObj instanceof JSONObject)
			{
				Iterator keyList = ((JSONObject) jsonObj).keys();
				while (keyList.hasNext()) 
				{
					String key = (String) keyList.next();
					Object value = ((JSONObject) jsonObj).get(key);
					if(key.equalsIgnoreCase(nodeName))
					{
						LOGGER.info("value :: "+value);
						return value;
					}
					node = getJsonNode(value, nodeName);
					if(node!=null)
					{
						return node;
					}
				}
			}
			return node;
		}
		

		
		public static boolean isStageOrProdEnvironment(){
			if("stage".equals(System.getProperty("environment"))
					|| "stage2".equals(System.getProperty("environment")) 
					|| "production".equals(System.getProperty("environment"))) {

				return true;
			}
			else {
				return false;
			}
		}
		
		public static void waitInSeconds(int seconds) {
			LOGGER.info("==> waitInSeconds(): " + seconds + " seconds");
			try {
				LOGGER.info("Wait started at: " + new Date());
				Thread.sleep(seconds * 1000);
				LOGGER.info("Wait completed at: " + new Date());
			} catch (InterruptedException e) {
				e.printStackTrace();
				LOGGER.info(e.getMessage());
			}
			LOGGER.info("<== waitInSeconds()");
		}
		
		  public static int createRandomNumber(int i,int j)
		    {
			      Random ran = new Random();
			      int n = i + ran.nextInt(j);
			      return n;
		    }
		  
		  public static InputStream callURL_getInputTream(String URL)
		    {
				StringBuilder sb = new StringBuilder();
				URLConnection urlConn = null;			
				InputStream inputstream=null;
				try {
					URL url = new URL(URL);
					urlConn = url.openConnection();
					if (urlConn != null)
						urlConn.setReadTimeout(60 * 1000);
					if (urlConn != null && urlConn.getInputStream() != null) 
					{
						inputstream = urlConn.getInputStream();
						
					}
					
				
				} catch (Exception e) {
					throw new RuntimeException("Exception while calling URL:"+ URL, e);
				} 

				return inputstream;
		    }
		  
			public static String callURL(String URL)
			{
					StringBuilder sb = new StringBuilder();
					URLConnection urlConn = null;
					InputStreamReader in = null;
					try {
						URL url = new URL(URL);
						urlConn = url.openConnection();
						if (urlConn != null)
							urlConn.setReadTimeout(60 * 1000);
						if (urlConn != null && urlConn.getInputStream() != null) {
							in = new InputStreamReader(urlConn.getInputStream(),
									Charset.defaultCharset());
							BufferedReader bufferedReader = new BufferedReader(in);
							if (bufferedReader != null) {
								int cp;
								while ((cp = bufferedReader.read()) != -1) {
									sb.append((char) cp);
								}
								bufferedReader.close();
							}
						}
					in.close();
					} catch (Exception e) {
						throw new RuntimeException("Exception while calling URL:"+ URL, e);
					} 
			 
					return sb.toString();
			}
		
		






	
}

