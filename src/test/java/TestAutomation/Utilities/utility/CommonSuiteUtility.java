package TestAutomation.Utilities.utility;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.LinkedHashMap;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import TestAutomation.Utilities.utility.*;
import TestAutomation.Utilities.WebServices.HttpServiceConn;


public class CommonSuiteUtility {
	
	private static Logger LOGGER = Logger.getLogger(CommonSuiteUtility.class);
	public static Properties ACCEPT_DETAILS = DatabaseUtil.readProperty("SSCOMClientID.properties");
	static ArrayList<Map<String,String>> userDetailsList = new ArrayList<Map<String,String>>();
	static ArrayList<String> userKeyList=new ArrayList<String>();
	
	public static String accesstoken = "";
	public static String stageaccesstoken = "";
	
	static String[] VIPFILists = {"cc_storage","dc_storage","grace_allowed","story","spark"};
	static String[] EVIPFILists = {"cc_storage","dc_storage","grace_allowed","story","spark","user_group_assignment","user_sync","domain_claiming","data_encryption","asset_sharing_policy_config","support_case_creation_allowed","support_role_assignment_allowed"};
	static String[] ETLAFILists = {"cc_storage","dc_storage","story","spark","user_group_assignment","user_sync","domain_claiming","data_encryption","asset_sharing_policy_config","support_case_creation_allowed","support_role_assignment_allowed","overdelegation_allowed"};
	
	static Properties baseProp,clientProp,secretKeyProp,requestProp,myAdobeReqestProp;

	static{
		baseProp = DatabaseUtil.readProperty("base.properties");
		clientProp = DatabaseUtil.readProperty("clientID.properties");
		secretKeyProp = DatabaseUtil.readProperty("secretKey.properties");
		requestProp= DatabaseUtil.readProperty("request.properties");
		myAdobeReqestProp = DatabaseUtil.readProperty("myAdobeRequest.properties");	
	}
	

	/**
	 * This method reads all the user details from the input sheet like country name,state name,pin code,credit card details etc.
	 * 
	 */
	public static void readUserDetails()
	{
		userDetailsList=(ArrayList<Map<String, String>>) ExcelUtilities.createData(Constants.campaignDetailsFile,Constants.userDetailsSheet);
		userKeyList = (ArrayList)ExcelUtilities.generateKeyList(Constants.campaignDetailsFile,Constants.userDetailsSheet);
	}
	
	/**
	 *This method gets the user details from the input sheet like country name,state name,pin code,credit card details etc for that particular store.
	 * 
	 * @param String(storeName) Store Name of the campaign
	 * @param String(placeOrder) Place order yes/no
	 * @return Map(userDetails) User details of that particular store
	 */
	public static Map<String,String> getUserDetails(String storeName,String placeOrder)
	{
		LOGGER.info("Store name in user details is...."+storeName);
		Map<String,String> userDetailsNewUserMap = new HashMap<String,String>();
		Map<String,String> userDetailsExistingUserMap = new HashMap<String,String>();
		if(placeOrder.equalsIgnoreCase("YES"))
		{
			for(int i = 0; i < userDetailsList.size(); i++){
				if(userDetailsList.get(i).get("Store_Name").equalsIgnoreCase(storeName) && userDetailsList.get(i).get("User Type").equalsIgnoreCase("NEW_USER"))
				{
					userDetailsNewUserMap = userDetailsList.get(i);
					break;
				}
			}
			LOGGER.info("the userdetailnewusermap in getuserDetails is "+userDetailsNewUserMap);
			return userDetailsNewUserMap;
		}
		else
		{
			for(int i = 0; i < userDetailsList.size(); i++){
				LOGGER.info(userDetailsList.get(i).get("Store_Name"));
				if(userDetailsList.get(i).get("Store_Name").equalsIgnoreCase(storeName) && userDetailsList.get(i).get("User Type").equalsIgnoreCase("EXISTING_USER"))
				{
					userDetailsExistingUserMap = userDetailsList.get(i);

				}
			}
			LOGGER.info("the userdetailexistingusermap in getuserDetails is "+userDetailsExistingUserMap);
			return userDetailsExistingUserMap;
		}
	}

	public static Map<String,String> updateDataMapWithUserDetails(Map<String,String> dataMap) throws SocketTimeoutException, DataSourceException
	{	
		Map<String,String> userDetails = new HashMap<String, String>();
		readUserDetails();
		dataMap.put(Constants.placeOrder,"YES");
		userDetails = getUserDetails(dataMap.get(Constants.storeName), dataMap.get(Constants.placeOrder));
		dataMap.putAll(userDetails);
		return dataMap;
	}
	
	 public static HashMap<String,String> addLangauageCode()
	    {
	    	HashMap<String,String> locdict = new HashMap<String, String>();
	    	locdict.put( "AP", "EN");
	    	locdict.put( "ZA", "EN");
			locdict.put( "AT", "DE");
			locdict.put( "AU", "EN");
			locdict.put( "BG", "EN");
			locdict.put( "BE", "EN");
			locdict.put( "CH", "DE");
			locdict.put( "LU", "EN");
			locdict.put( "BR", "PT_BR");
			locdict.put( "CH3", "DE");
			locdict.put( "CH2", "FR");
			locdict.put( "CH1", "IT");
			locdict.put( "BE1", "NL");
			locdict.put( "BE2", "FR");
			locdict.put( "BE3", "EN");
			locdict.put( "LU1", "FR");
			locdict.put( "LU2", "EN");
			locdict.put( "LU3", "DE");		
			locdict.put( "CZ", "CZ");
			locdict.put( "DE", "DE");
			locdict.put( "DK", "DA");
			locdict.put( "KR", "KO_KR");
			locdict.put( "AU", "EN");
			locdict.put( "DE", "DE");
			locdict.put( "EU", "EN");
			locdict.put( "FR", "FR");
			locdict.put( "JP", "JA_JP");
			locdict.put( "UK", "EN");
			locdict.put( "AT", "DE");
			locdict.put( "CH", "DE");
			locdict.put( "LU", "EN");
			locdict.put( "DK", "DA");
			locdict.put( "ES", "ES");
			locdict.put( "FI", "FI");
			locdict.put( "HK1", "HK_EN");
			locdict.put( "HK2", "ZH_TW");
			locdict.put( "HK", "HK_EN");
			locdict.put( "RU", "RU");
			locdict.put( "RU", "RU");
			locdict.put( "ES", "ES");
			locdict.put( "GR", "EN");
			locdict.put( "MT", "EN");
			locdict.put( "CY", "EN");
			locdict.put( "EU", "EN");
			locdict.put( "FI", "FI");
			locdict.put( "FR", "FR");
			locdict.put( "IE", "EN");
			locdict.put( "IT", "IT");
			locdict.put( "NL", "NL");
			locdict.put( "JP", "JA_JP");
			locdict.put( "NO", "NO");
			locdict.put( "NZ", "EN");
			locdict.put("PL", "PL_PL");
			locdict.put( "PT", "PT");
			locdict.put( "SE", "SV");
			locdict.put( "UK", "EN");
			locdict.put( "US", "EN_US");
			locdict.put( "CA_FR", "FR");
			locdict.put( "MX", "EN_US");
			locdict.put( "CA", "EN_US");
			locdict.put( "SK", "SK");
			locdict.put( "SI", "EN");
			locdict.put( "RO", "EN");
			locdict.put( "LV", "lv_LV");
			locdict.put( "LT", "EN");
			locdict.put( "HU", "EN");
			locdict.put( "EE", "EN");
			locdict.put( "AR", "ES");
			locdict.put( "CO", "ES");
			locdict.put( "CL", "ES");
			locdict.put( "CR", "ES");
			locdict.put( "EC", "ES");
			locdict.put( "GT", "ES");
			locdict.put( "PE", "ES");
			locdict.put( "VE", "ES");		
			locdict.put( "IN", "EN");
			locdict.put( "SG", "EN");
			locdict.put( "MY", "EN");
			locdict.put( "PH", "EN_");
			locdict.put( "ID", "EN");
			locdict.put( "TH", "EN");
			locdict.put( "TW", "ZH");
			locdict.put( "TR", "TR");
			locdict.put( "IL", "EN");
			locdict.put( "AE", "EN");
			locdict.put( "BH", "EN");
			locdict.put( "EG", "EN");
			locdict.put( "JO", "EN");
			locdict.put( "KW", "EN");
			locdict.put( "QA", "EN");
			locdict.put( "OM", "EN");
			locdict.put( "SA", "EN");
			locdict.put( "UA", "RU");
			locdict.put( "DZ", "EN");
			locdict.put( "MA", "EN");
			locdict.put( "TN", "EN");
			locdict.put( "YE", "EN");
			locdict.put( "AM", "EN");
			locdict.put( "AZ", "EN");
			locdict.put( "GE", "EN");
			locdict.put( "MD", "EN");
			locdict.put( "TM", "EN");
			locdict.put( "LB", "EN");
			locdict.put( "BY", "RU");
			locdict.put( "KZ", "RU");
			locdict.put( "KG", "RU");
			locdict.put( "TJ", "RU");
			locdict.put( "UZ", "RU");
			locdict.put( "CN", "zh_TW");
			locdict.put( "BO", "ES");
			locdict.put( "DO", "ES");
			locdict.put( "HR", "EN");
			locdict.put( "KE", "EN");
			locdict.put( "LK", "EN");
			locdict.put( "MO", "EN");
			locdict.put( "MU", "EN");
			locdict.put( "NG", "EN");
			locdict.put( "PA", "ES");
			locdict.put( "PY", "ES");
			locdict.put( "SV", "ES");
			locdict.put( "TT", "EN");
			locdict.put( "UY", "ES");
			locdict.put( "VN", "EN");
			
			return locdict;
	    }
	    
	    //Add langauage code to the country
	    public static HashMap<String,String> addLocaleCode()
	    {
	    	HashMap<String,String> locdict = new HashMap<String, String>();
	    	locdict.put( "ZA", "EN");
			locdict.put( "AT", "DE");
			locdict.put( "AU", "EN");
			locdict.put( "BR", "PT_BR");
			locdict.put( "CH", "FR");
			locdict.put( "BE", "NL");
			locdict.put( "BG", "EN");
			locdict.put( "LU", "DE");		
			locdict.put( "CZ", "CS_CZ");
			locdict.put( "DE", "DE");
			locdict.put( "DK", "DA");
			locdict.put( "RU", "RU");
			locdict.put( "HK", "ZH_TW");
			locdict.put( "KR", "KO_KR");
			locdict.put( "ES", "ES");
			locdict.put( "GR", "EN");
			locdict.put( "MT", "EN");
			locdict.put( "CY", "EN");
			locdict.put( "EU", "EN");
			locdict.put( "FI", "FI");
			locdict.put( "FR", "FR");
			locdict.put( "IE", "EN");
			locdict.put( "IT", "IT");
			locdict.put( "NL", "NL");
			locdict.put( "JP", "JA_JP");
			locdict.put( "NO", "NO");
			locdict.put( "NZ", "EN");
			locdict.put("PL", "PL_PL");
			locdict.put( "PT", "PT");
			locdict.put( "SE", "SV");
			locdict.put( "GB", "EN");
			locdict.put( "US", "EN_US");
			locdict.put( "CA", "FR");
			locdict.put( "MX", "EN_US");
			locdict.put( "SK", "SK");
			locdict.put( "SI", "EN");
			locdict.put( "RO", "EN");
			locdict.put( "LV", "lv_LV");
			locdict.put( "LT", "EN");
			locdict.put( "HU", "EN");
			locdict.put( "EE", "EN");
			locdict.put( "AR", "ES");
			locdict.put( "CO", "ES");
			locdict.put( "CL", "ES");
			locdict.put( "CR", "ES");
			locdict.put( "EC", "ES");
			locdict.put( "GT", "ES");
			locdict.put( "PE", "ES");
			locdict.put( "VE", "ES");
			locdict.put( "IN", "EN");  //
			locdict.put( "SG", "EN");
			locdict.put( "MY", "EN");
			locdict.put( "PH", "EN");
			locdict.put( "ID", "EN");
			locdict.put( "TH", "EN");
			locdict.put( "TW", "ZH");
			locdict.put( "TR", "TR");
			locdict.put( "IL", "EN");
			locdict.put( "AE", "EN");
			locdict.put( "BH", "EN");
			locdict.put( "EG", "EN");
			locdict.put( "JO", "EN");
			locdict.put( "KW", "EN");
			locdict.put( "QA", "EN");
			locdict.put( "OM", "EN");
			locdict.put( "SA", "EN");			
			locdict.put( "UA", "RU");
			locdict.put( "DZ", "EN");
			locdict.put( "MA", "EN");
			locdict.put( "TN", "EN");
			locdict.put( "YE", "EN");
			locdict.put( "AM", "EN");
			locdict.put( "AZ", "EN");
			locdict.put( "GE", "EN");
			locdict.put( "MD", "EN");
			locdict.put( "TM", "EN");
			locdict.put( "LB", "EN");
			locdict.put( "BY", "RU");
			locdict.put( "KZ", "RU");
			locdict.put( "KG", "RU");
			locdict.put( "TJ", "RU");
			locdict.put( "UZ", "RU");
			locdict.put( "CN", "zh_TW");
			locdict.put( "BO", "ES");
			locdict.put( "DO", "ES");
			locdict.put( "HR", "EN");
			locdict.put( "KE", "EN");
			locdict.put( "LK", "EN");
			locdict.put( "MO", "EN");
			locdict.put( "MU", "EN");
			locdict.put( "NG", "EN");
			locdict.put( "PA", "ES");
			locdict.put( "PY", "ES");
			locdict.put( "SV", "ES");
			locdict.put( "TT", "EN");
			locdict.put( "UY", "ES");
			locdict.put( "VN", "EN");
						
			return locdict;
	    }
	    

	  
	    public static HashMap<String,String> getAllProcutCode()
	    {
        HashMap<String,String> productcode = new HashMap<String, String>();
	    	
	    	productcode.put("Creative Cloud All Apps","CCLE");
	    	productcode.put("Creative Cloud All Apps + Adobe Stock","CTSK");
	    	productcode.put("Adobe Stock - 10 images a month","STKS");
	    	productcode.put("Adobe Stock - 750 images a month","STKL");
	    	productcode.put("Photoshop CC","PHSP");
	    	productcode.put("Illustrator CC","ILST");
	    	productcode.put("InDesign CC","IDSN");	    	
	    	productcode.put("Acrobat Pro DC","APCC");
	    	productcode.put("Adobe Muse CC","MUSE");
	    	productcode.put("Dreamweaver CC","DRWV");
	    	productcode.put("Animate CC","FLPR");
	    	productcode.put("Premiere Pro CC","PPRO");
	    	productcode.put("After Effects CC","AEFT");
	    	productcode.put("Adobe Audition CC","AUDT");
	    	productcode.put("Adobe XD CC","SPRK");
	    	productcode.put("Dimension CC","ESHR");
	    	productcode.put("InCopy CC","AICY");
	    	productcode.put("Adobe Stock - 40 images a month","STKM");	 
	    	productcode.put("Premiere Rush CC","RUSH");
	    	productcode.put("Adobe Sign","ECHT");
	    	productcode.put("Spark","ASPK");
	    	productcode.put("Adobe Fresco","FRSC");
	    	
	    	productcode.put("Spark with premium features","ASPK");
	    	productcode.put("Photography plan (20 GB)","PHLT");
	    	productcode.put("Photography plan (1 TB)","PLES");
	    	productcode.put("Lightroom CC plan (1 TB)","LPES");
	    	productcode.put("Photoshop","PHSP");
	    	productcode.put("Illustrator","ILST");
	    	productcode.put("InDesign","IDSN");
	    	productcode.put("Acrobat Pro DC","APCC");
	    	productcode.put("Adobe Muse","MUSE");
	    	productcode.put("Dreamweaver","DRWV");
	    	productcode.put("Animate (Flash Pro)","FLPR");
	    	productcode.put("Adobe Premiere Pro","PPRO");
	    	productcode.put("After Effects","AEFT");
	    	productcode.put("Audition","AUDT");
	    	productcode.put("InCopy","AICY");
	    	productcode.put("Adobe XD","SPRK");
	    	productcode.put("Dimension","ESHR");
	    	productcode.put("Premiere RUSH","RUSH");
	    	productcode.put("Lightroom CC plan with 1TB","LCCC");
	    	
	    	return productcode;
	    }
	    
		public static String generateSerialNumber()
		{
			String value = "2264-3764-722";
			value = value+System.currentTimeMillis();
			return value;			
		}

	
	//Get spec sheet data
	public static ArrayList<Map<String,String>> getSpecDataFrmInputSheet(String filename,String sheetname)
	{
	  ArrayList<Map<String,String>> data = new ArrayList<Map<String,String>>();
	   try
		  {
			 data = (ArrayList<Map<String, String>>) ExcelUtilities.createData(filename,sheetname);
		  }
		 catch(Exception e)
			{
					//ToDO
			}
			return data;
	}


			
			public static String getCreatePersonRequestBody(Map<String, String> dataMap) {
				
				Date date= new Date();
				Calendar dob= Calendar.getInstance();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				
				//setting the current date
				dob.setTime(date);
				
				//setting dob to 25yrs backdate from current date
				int year = dob.get(Calendar.YEAR);
				dob.set(year-25, dob.get(Calendar.MONTH), dob.get(Calendar.DAY_OF_MONTH));
				String dateOfBirth = dateFormat.format(dob.getTime());
				
				String requestBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
										"<ReqBody version=\"1.9.11\" clientId=\"adobedotcom2\" options=\"prettyOutput\">"+
										  "<req dest=\"UserManagement\" api=\"createPerson\">"+
										    "<ClientAppDescriptor />"+
										    "<Person>"+
										      "<firstName>"+dataMap.get(Constants.CUSTOMER_FIRST_NAME)+"</firstName>"+
										      "<lastName>"+dataMap.get(Constants.CUSTOMER_LAST_NAME)+"</lastName>" +
										      "<eMail>"+dataMap.get(Constants.EMAIL_ID)+"</eMail>"+
										      "<dob>"+dateOfBirth+"</dob>"+
										      "<shortForm>true</shortForm>"+
										      "<countryCode>"+dataMap.get(Constants.Country)+"</countryCode>"+
										      "<credentials class=\"Credentials\">"+
										        "<password>"+dataMap.get(Constants.Password)+"</password>"+
										        "<username>"+dataMap.get(Constants.EMAIL_ID)+"</username>"+
										      "</credentials>"+
										    "</Person>"+
										    "<AuthRequest />"+
										  "</req>"+
										"</ReqBody>";
				
				return requestBody;
			}
			
			public static String getcountryvalue(String storename)
			{
				String country = storename;
				/*if(country.equalsIgnoreCase("OLS-EDU"))
				{
					country = "US";
				}
				else
				{
					if(country.contains("EDU"))
					{
						country = country.substring(8);
					}
					else
					{
						country = country.substring(4);
					}	
				}*/
				if(country.equalsIgnoreCase("HK1") || country.equalsIgnoreCase("HK2"))
				{
					country = "HK";
				}
				else if(country.equalsIgnoreCase("CH1") || country.equalsIgnoreCase("CH2") || country.equalsIgnoreCase("CH3"))
				{
					country = "CH";
				}
				else if(country.equalsIgnoreCase("BE1") || country.equalsIgnoreCase("BE2") || country.equalsIgnoreCase("BE3"))
				{
					country = "BE";
				}
				else if(country.equalsIgnoreCase("LU1") || country.equalsIgnoreCase("LU2") || country.equalsIgnoreCase("LU3"))
				{
					country = "LU";
				}
				else if(country.equalsIgnoreCase("CA_FR"))
				{
					country = "CA";
				}
				
				if(country.equalsIgnoreCase("UK"))
				{
					country = "GB";
				}
				else if(country.equalsIgnoreCase("AP") || country.equalsIgnoreCase("africa"))
				{
					country = "ZA";
				}
				else
				{
					country = country.toUpperCase();
				}
								
				return country;
			}
	
			public static MultiMap getAuthInfo(String strServiceName){
				LOGGER.info("==>getAuthInfo()");
				MultiMap hm = new MultiValueMap();
				hm.put("base", baseProp.getProperty("cxf")+baseProp.getProperty("authUri"));	
				hm.put("clientId", clientProp.getProperty(strServiceName));
				hm.put("secretKey", secretKeyProp.getProperty(strServiceName));
				hm.put("requestUri", requestProp.getProperty(strServiceName));
				LOGGER.info("<==getAuthInfo()");
				return hm;
			}
			
			public static LinkedHashMap<String, String> getStateCode(String countryName, String stateName, Map<String, Object> respMap)
			{
				//		InputStream responseInputStream = (InputStream)respMap.get(HttpServiceConn.RESPONSEBODYASSTREAM);
				//		CommonParserUtil commonParserUtil=new CommonParserUtil();
				//		Map<String, Object> responseMap = commonParserUtil.getMap(responseInputStream);

				JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(respMap.get(HttpServiceConn.RESPONSEBODY));
				Map<String, Object> responseMap = convertJsonAsMap(jsonObject);

				ArrayList<Map<String, Object>> countries = (ArrayList<Map<String, Object>>)((Map)((ArrayList<JSONObject>)((Map)responseMap.get("reference.reference")).get("reference.sales_regions")).get(0)).get("reference.countries");
				for (Map<String, Object> country : countries) {
					if(((String)country.get("reference.iso_3166_name")).equalsIgnoreCase(countryName))
					{
						ArrayList<Map<String, Object>> states = (ArrayList<Map<String, Object>>) country.get("reference.state_province");
						for (Map<String, Object> state : states) {
							if(((String)state.get("reference.name")).equalsIgnoreCase(stateName))
							{
								LinkedHashMap<String, String> codes = new LinkedHashMap<String, String>();
								codes.put("ServiceKeys.COUNTRY_CODE", country.get("reference.iso_3166_alpha2_code").toString());   //Need to check later doubt
								codes.put("ServiceKeys.STATE_PROVINCE", state.get("reference.code").toString());                   //Need to check later doubt
								return codes;
							}
						}
					}
				}
				return null;
			}

			public static String getCountryName(String countryCode, Map<String, Object> respMap) 
			{
				JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(respMap.get(HttpServiceConn.RESPONSEBODY));
				Map<String, Object> responseMap = convertJsonAsMap(jsonObject);
				if(responseMap.get("reference.reference")!=null && !responseMap.get("reference.reference").equals(""))
				{
					ArrayList<Map<String, Object>> countries = (ArrayList<Map<String, Object>>)((Map)((ArrayList<JSONObject>)((Map)responseMap.get("reference.reference")).get("reference.sales_regions")).get(0)).get("reference.countries");
					for (Map<String, Object> country : countries) {
						if(((String)country.get("reference.iso_3166_alpha2_code")).equalsIgnoreCase(countryCode))
						{
							return country.get("reference.iso_3166_name").toString();
						}
					}
				}
				return null;
			}
			
			public static Map<String,Object> convertJsonAsMap(JSONObject json){
				LOGGER.info("==>convertJsonAsMap()");
				Map<String, Object> map = new HashMap<String, Object>();
				String strKey=null;
				Iterator keys=json.keys();
				getMap(json,map);
				LOGGER.info("<==convertJsonAsMap() :: "+map);
				return map;
			}
			
			private static void getMap(JSONObject o, Map<String, Object> b) {
				LOGGER.debug("==>getMap() :: JSon "+o.toString());
				LOGGER.debug("==>getMap() :: Map "+b);
				
				Iterator ji = o.keys();
				while (ji.hasNext()) {
					String key = (String) ji.next();
					Object val = o.get(key);
					if (val.getClass() == JSONObject.class) {
						Map<String, Object> sub = new HashMap<String, Object>();
						getMap((JSONObject) val, sub);
						b.put(key, sub);
					} else if (val.getClass() == JSONArray.class) {
						List<Object> l = new ArrayList<Object>();
						JSONArray arr = (JSONArray) val;
						for (int a = 0; a < arr.size(); a++) {
							Map<String, Object> sub = new HashMap<String, Object>();
							Object element = arr.get(a);
							if (element instanceof JSONObject) {
								getMap((JSONObject) element, sub);
								l.add(sub);
							} else {
								l.add(element);
							}
						}
						b.put(key, l);
					} else {
						b.put(key, val);
					}
				}
				
				LOGGER.debug("<==getMap() :: "+b);
			}
			

			public static String sendUpdatedEmailIdList(Map<String, String> dataMap,String emailid)
			{
				String val = "Details not available";
				try
				{
					String id1 = dataMap.get("ProjectUser");
					String id2 = dataMap.get("ProjectEmailID");
					if(id1.equalsIgnoreCase("Details not available") && id2.equalsIgnoreCase("Details not available"))
					{
						val = "Details not available";
					}
					else if(id1.contains("@adobe.com") && id2.contains("@adobe.com"))
					{
						if(id1.equalsIgnoreCase(id2))
						{
							val = emailid+","+id1;
						}
						else
						{
							val=emailid+","+id1+","+id2;
						}
					}
					else
					{
						if(id1.contains("@adobe.com"))
						{
							val = emailid+","+id1;
						}
						else if(id2.contains("@adobe.com"))
						{
							val = emailid+","+id2;
						}
					}
				}
				catch(Exception e)
				{
					//TODO
				}
				return val;
			}
			
			public static void setUpdatedEmailIdList(Map<String, String> dataMap,String emailid,String getstatus,String key)
			{
				try
				{
					String updatedemailid = CommonSuiteUtility.sendUpdatedEmailIdList(dataMap,emailid);
					if(updatedemailid.equalsIgnoreCase("Details not available"))
					{
						CommonSuiteUtility.updateEmailIdPropertiesValue(key,emailid);
					}
					else
					{
						if(getstatus.equalsIgnoreCase("false"))
						{
							CommonSuiteUtility.updateEmailIdPropertiesValue(key,emailid);
						}
						else
						{
							CommonSuiteUtility.updateEmailIdPropertiesValue(key,updatedemailid);
						}					
					}					
				}
				catch(Exception e)
				{
					//TODO
				}
			}
			
			public static void setUpdatedEmailIdList(String key,String emailID)
			{
				try
				{
					String getemailid = CommonSuiteUtility.getEmailIdPropertiesValue(key);
					getemailid = getemailid+","+emailID;
					CommonSuiteUtility.updateEmailIdPropertiesValue(key,getemailid);
				}
				catch(Exception e)
				{
					//TODO
				}
			}
			
			public static void updatePropertiesValue(String key,String value) throws IOException
			{
				Properties prop = new Properties();
				InputStream in = new FileInputStream("src/test/resources/Gendral.properties");
				prop.load(in);
				prop.setProperty(key,value);
				prop.store(new FileOutputStream("src/test/resources/Gendral.properties"), null);
			}
			
			public static void updateEmailIdPropertiesValue(String key,String value) throws IOException
			{
				Properties prop = new Properties();
				InputStream in = new FileInputStream("src/test/resources/EmailID.properties");
				prop.load(in);
				prop.setProperty(key,value);
				prop.store(new FileOutputStream("src/test/resources/EmailID.properties"), null);
			}
			
			public static String getEmailIdPropertiesValue(String key) throws IOException
			{
				String value = "";
				Properties prop = new Properties();
				InputStream in = new FileInputStream("src/test/resources/EmailID.properties");
				prop.load(in);
				value = prop.getProperty(key);
                return value;
			}
			
			public static String getPropertiesValue_PerformValidation(String key) throws IOException
			{
				String value = "";
				Properties prop = new Properties();
				InputStream in = new FileInputStream("src/test/resources/Gendral.properties");
				prop.load(in);
				value = prop.getProperty(key);
                return value;
			}
			
			public static String getPropertiesValue(String key) throws IOException
			{
				String value = "";
				Properties prop = new Properties();
				InputStream in = new FileInputStream("src/test/resources/DBSettings.properties");
				prop.load(in);
				value = prop.getProperty(key);
                return value;
			}
			public static int createRandomNumber(int i,int j)
		      {
			      Random ran = new Random();
			      int n = i + ran.nextInt(j);
			      return n;
		      }
			
			public static String getPropertiesValue_Enterprise() throws IOException
			{
				int n = createRandomNumber(0,9);
				String value = "";
				Properties prop = new Properties();
				InputStream in = new FileInputStream("src/test/resources/Gendral.properties");
				prop.load(in);
				String key="ETLA_BUSINESS_USER"+n;
				value = prop.getProperty(key);
                return value;
			}
			
			public static void writeThrowErrorMsg(String filename,String ProjectID,String enviornment,String path) throws IOException 
			{
				File folder = new File("//var/www/html/OnBoarding/"+"HTMLFiles");		
			    //File folder = new File("\\\\REN55703-WX-3\\Outputsheet"+"\\"+"HTMLFiles");
				if(!folder.exists()){
					folder.mkdir();
				}
				File file = new File("//var/www/html/OnBoarding/HTMLFiles/"+filename+".html"); 
				//File file = new File("\\\\REN55703-WX-3\\Outputsheet\\HTMLFiles\\"+value+".html"); 
				
				StringBuilder buf = new StringBuilder();
				buf.append("<html>" +
				           "<body style=\"background-color:powderblue;\">" +
				           "<p> "+ ProjectID +" - Validation Failed on "+enviornment+",Check the validation error details in the below path \n</p>" +
				           "<p>Check detailed Validation report O/p Results Path - "+Constants.machine_name+"/"+path+"</P>" +
				           "<p> ********************</P>" +
				           "<p> ********************</P>" +
				           "<p style = \"color:blue; font-weight: bold;\"> Throws some error message while validating.So,unable send validation results thru mail</p>" +	
			               "<p style = \"color:blue; font-weight: bold;\"> For more details about validation error message, check output results sheet or exection logs</p>");		
				buf.append("</body>" +
				           "</html>");
				
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)); 
				bufferedWriter.write(buf.toString()); 
				bufferedWriter.close();
			}
			
			public static void writeUOPThrowErrorMsg(String filename,String ProjectID,String value, String enviornment) throws IOException 
			{
				//File folder = new File("//var/www/html/OnBoarding/"+"HTMLFiles");		
			    File folder = new File("\\\\REN55703-WX-3\\Outputsheet"+"\\"+"HTMLFiles");
				if(!folder.exists()){
					folder.mkdir();
				}
				//File file = new File("//var/www/html/OnBoarding/HTMLFiles/"+filename+".html"); 
				File file = new File("\\\\REN55703-WX-3\\Outputsheet\\HTMLFiles\\"+filename+".html"); 
				
				StringBuilder buf = new StringBuilder();
				buf.append("<html>" +
				           "<body style=\"background-color:powderblue;\">" +
				           "<p> "+ProjectID +" - Validation Failed on "+enviornment+",Check the validation error details \n</p>" +
				           "<p> ********************</P>" +
				           "<p> ********************</P>" +
				           "<p style = \"color:blue; font-weight: bold;\"> Throws some error message while validating.So,unable send validation results thru mail</p>" +	
				           "<p> ********************</P>" +
		                   "<p> ********************</P>" +
		                   "<p style = \"color:blue; font-weight: bold;\"> UOP Request Event Details</p>" +
		                   "<table border=\"1\">" +
		    	           "<tr>" +
		    	           "<th>  Request Event Details   </th>" +
		    	           "</tr>" );
		    	buf.append("<tr>" +
		    			   "<td>" +value );
		    	buf.append("</table>" +
				           "</body>" +
				           "</html>");
				
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)); 
				bufferedWriter.write(buf.toString()); 
				bufferedWriter.close();
			}

			public static void writeNoContentLog(String value) throws IOException 
			{ 			
					File folder = new File("//var/www/html/OnBoarding/"+"HTMLFiles");		
				    //File folder = new File("\\\\REN55703-WX-3\\Outputsheet"+"\\"+"HTMLFiles");
					if(!folder.exists()){
						folder.mkdir();
					}
					File file = new File("//var/www/html/OnBoarding/HTMLFiles/"+value+".html"); 
					//File file = new File("\\\\REN55703-WX-3\\Outputsheet\\HTMLFiles\\"+value+".html"); 
					
					StringBuilder buf = new StringBuilder();
					buf.append("<html>" +
					           "<body style=\"background-color:powderblue;\">" +
					           "<p style = \"color:blue; font-weight: bold;\"> Not Received any event from SSCOM ,So validation not performed</p>");		
					buf.append("</body>" +
					           "</html>");
					
					BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)); 
					bufferedWriter.write(buf.toString()); 
					bufferedWriter.close();
			}
			
			public static void writeNotMatchingLog(String value,String check) throws IOException 
			{ 			
					File folder = new File("//var/www/html/OnBoarding/"+"HTMLFiles");		
				    //File folder = new File("\\\\REN55703-WX-3\\Outputsheet"+"\\"+"HTMLFiles");
					if(!folder.exists()){
						folder.mkdir();
					}
					File file = new File("//var/www/html/OnBoarding/HTMLFiles/"+value+".html"); 
					//File file = new File("\\\\REN55703-WX-3\\Outputsheet\\HTMLFiles\\"+value+".html"); 
					
					StringBuilder buf = new StringBuilder();
					if(check.equalsIgnoreCase("Gendral"))
					{
						buf.append("<html>" +
						           "<body style=\"background-color:powderblue;\">" +
						           "<p style = \"color:blue; font-weight: bold;\"> Details not matching for validation likes ProjectID,saleschannel,businesssegment</p>");		
						buf.append("</body>" +
						           "</html>");
					}
					else if(check.equalsIgnoreCase("Inapps"))
					{
						buf.append("<html>" +
						           "<body style=\"background-color:powderblue;\">" +
						           "<p style = \"color:blue; font-weight: bold;\"> Contract creation validation not implementated for Inapps offer</p>");		
						buf.append("</body>" +
						           "</html>");
					}
					else if(check.equalsIgnoreCase("JEM"))
					{
						buf.append("<html>" +
						           "<body style=\"background-color:powderblue;\">" +
						           "<p style = \"color:blue; font-weight: bold;\"> Unable to Perform JEM Validation for Partner request.Verify manually</p>");		
						buf.append("</body>" +
						           "</html>");
					}
					
					BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)); 
					bufferedWriter.write(buf.toString()); 
					bufferedWriter.close();
			}
			
			public static String getcountryForOrderPlacement(String value,ArrayList<String> countrylist,String check)
			{
				Random rand = new Random(); 
				String country = "";
				
				if(value.contains("MUN"))
				{
					if(check.equalsIgnoreCase("SESI"))
					{
						if(countrylist.contains("CA"))
						{
							country = "CA";
						}
						else
						{
							country = countrylist.get(rand.nextInt(countrylist.size()));
						}
					}
					else
					{
						if(countrylist.contains("US"))
						{
							country = "US";
						}
						else
						{
							country = countrylist.get(rand.nextInt(countrylist.size()));
						}
					}
				}
				else if(value.contains("MUA"))
				{
					if(check.equalsIgnoreCase("SESI"))
					{
						if(countrylist.contains("TR"))
						{
							country = "TR";
						}
						else if(countrylist.contains("IL"))
						{
							country = "IL";
						}
						else if(countrylist.contains("KR"))
						{
							country = "KR";
						}
						else
						{
							country = countrylist.get(rand.nextInt(countrylist.size()));
						}
					}
					else
					{
						if(countrylist.contains("KR"))
						{
							country = "KR";
						}
						else
						{
							country = countrylist.get(rand.nextInt(countrylist.size()));
						}
					}
				}
				else if(value.contains("MAU"))
				{
					if(countrylist.contains("AU"))
					{
						country = "AU";
					}
					else
					{
						country = countrylist.get(rand.nextInt(countrylist.size()));
					}
				}
				else if(value.contains("JPL"))
				{
					country = "JP";
				}
				else if(value.contains("MUS"))
				{
					if(countrylist.contains("BR"))
					{
						country = "BR";
					}
					else
					{
						country = countrylist.get(rand.nextInt(countrylist.size()));
					}
				}
				else if(value.contains("MUL"))
				{
					if(countrylist.contains("FR"))
					{
						country = "FR";
					}
					else
					{
						country = countrylist.get(rand.nextInt(countrylist.size()));
					}
				}
				else if(value.contains("EUW"))
				{
					country = "GB";
				}
				else if(value.contains("MUE"))
				{
					if(check.equalsIgnoreCase("SESI"))
					{
						StringBuilder sb = new StringBuilder();
						if(countrylist.contains("DE"))
						{
							sb.append("DE").append(",");
						}
						if(countrylist.contains("FR"))
						{
							sb.append("FR").append(",");
						}
						if(countrylist.contains("NO"))
						{
							sb.append("NO").append(",");
						}
						if(countrylist.contains("CH"))
						{
							sb.append("CH").append(",");
						}
						if(countrylist.contains("PL"))
						{
							sb.append("PL").append(",");
						}
						if(sb.length() == 0)
						{
							country = countrylist.get(rand.nextInt(countrylist.size()));
							sb.append(country).append(",");
						}
	                    sb = sb.deleteCharAt(sb.length()-1);
						country = sb.toString();
					
					}
					else
					{
						StringBuilder sb = new StringBuilder();
						if(countrylist.contains("DE"))
						{
							country = "DE";
						}
						else if(countrylist.contains("FR"))
						{
							country = "FR";
						}
						else if(countrylist.contains("NO"))
						{
							country = "NO";
						}
						else if(countrylist.contains("CH"))
						{
							country = "CH";
						}
						else if(countrylist.contains("PL"))
						{
							country = "PL";
						}
						else
						{
							country = countrylist.get(rand.nextInt(countrylist.size()));
						}
					}
				}				
				return country;
			}
			
			public static ArrayList<String>  getcountrylist(ArrayList<String> countrylist,String value)
			{
				ArrayList<String>  country = new ArrayList<String>();
				for(int i=0;i<countrylist.size();i++)
				{
					if(value.toUpperCase().contains(countrylist.get(i).toUpperCase()))
					{
						country.add(countrylist.get(i));
					}
				}
				return country;
			}
			
			public static ArrayList<String>  getcountryForOrderPlacement(String value,ArrayList<String> countrylist)
			{
				Random rand = new Random(); 
				ArrayList<String>  country = new ArrayList<String>();
				
				if(value.equalsIgnoreCase("MULT"))
				{
					if(countrylist.contains("US"))
					{
						country.add("US");
					}
					if(countrylist.contains("KR"))
					{
						country.add("KR");
					}
					if(countrylist.contains("AU"))
					{
						country.add("AU");
					}
					if(countrylist.contains("JP"))
					{
						country.add("JP");
					}
					if(countrylist.contains("BR"))
					{
						country.add("BR");
					}
					if(countrylist.contains("DE"))
					{
						country.add("DE");
					}
					if(countrylist.contains("FR"))
					{
						country.add("FR");
					}
					if(countrylist.contains("NO"))
					{
						country.add("NO");
					}
					if(countrylist.contains("CH"))
					{
						country.add("CH");
					}
					if(countrylist.contains("PL"))
					{
						country.add("PL");
					}
					if(countrylist.contains("RU"))
					{
						country.add("RU");
					}
				}
				else if(value.equalsIgnoreCase("EN"))
				{
					country.add("GB");
				}
				else
				{
					country.add(countrylist.get(rand.nextInt(countrylist.size())));
				}
				
				if(country.size() == 0)
				{
					country.add(countrylist.get(rand.nextInt(countrylist.size())));
				}
				return country;
			}
			
			//Get T&C overall status from the validation
			public static Map<String, String> getOverallTCStatus(ArrayList<Map<String, String>> tcdetails)
			{
				Map<String, String> validationMap = new HashMap<String,String>();
				Set<String> GendralTC = new HashSet<String>();
				Set<String> BoletoTC = new HashSet<String>();
				Set<String> AllpagoTC = new HashSet<String>();
				Set<String> ChargeTaxesTC = new HashSet<String>();
				
				for(int j=0;j<tcdetails.size();j++)			
				{
					Map<String,Map<String,String>> specMap = new HashMap<String,Map<String,String>>();					
					specMap.put("keyvalue",tcdetails.get(j));
					GendralTC.add(GenericFunctions.getData(specMap,"keyvalue","GendralT&C"));	
					BoletoTC.add(GenericFunctions.getData(specMap,"keyvalue","BoletoT&C"));
					AllpagoTC.add(GenericFunctions.getData(specMap,"keyvalue","AllpagoT&C"));
					ChargeTaxesTC.add(GenericFunctions.getData(specMap,"keyvalue","ChargeTaxesT&C"));
				}
				String GendralTC1 = GendralTC.toString().replaceAll("Key Not Available", "");
				String BoletoTC1 = BoletoTC.toString().replaceAll("Key Not Available", "").replaceAll(",", "");
				String AllpagoTC1 = AllpagoTC.toString().replaceAll("Key Not Available", "").replaceAll(",", "");
				String ChargeTaxesTC1 = ChargeTaxesTC.toString().replaceAll("Key Not Available", "").replaceAll(",", "");
				
				if(GendralTC.toString().contains("FAIL"))
				{
					validationMap.put("GendralT&C", GendralTC1.toString());
				}
				else if(GendralTC.toString().contains("WARNING"))
				{
					validationMap.put("GendralT&C", GendralTC1.toString());
				}
				else
				{
					if(GendralTC.toString().isEmpty())
					{
						validationMap.put("GendralT&C", "FAIL");
					}
					else
					{
						validationMap.put("GendralT&C", "PASS");
					}					
				}
				if(BoletoTC.toString().contains("FAIL"))
				{
					validationMap.put("BoletoT&C", BoletoTC1.toString());
				}
				else if(BoletoTC.toString().contains("WARNING"))
				{
					validationMap.put("BoletoT&C", BoletoTC1.toString());
				}
				else
				{
					if(BoletoTC.toString().isEmpty())
					{
						validationMap.put("BoletoT&C", "FAIL");
					}
					else
					{
					    validationMap.put("BoletoT&C", "PASS");
					}
				}
				if(AllpagoTC.toString().contains("FAIL"))
				{
					validationMap.put("AllpagoT&C", AllpagoTC1.toString());
				}
				else if(AllpagoTC.toString().contains("WARNING"))
				{
					validationMap.put("AllpagoT&C", AllpagoTC1.toString());
				}
				else
				{
					if(AllpagoTC.toString().isEmpty())
					{
						validationMap.put("AllpagoT&C", "FAIL");
					}
					else
					{
					    validationMap.put("AllpagoT&C", "PASS");
					}
				}

				if(ChargeTaxesTC.toString().contains("FAIL"))
				{
					validationMap.put("ChargeTaxesT&C", ChargeTaxesTC1.toString());
				}
				else if(ChargeTaxesTC.toString().contains("WARNING"))
				{
					validationMap.put("ChargeTaxesT&C", ChargeTaxesTC1.toString());
				}
				else
				{
					if(ChargeTaxesTC.toString().isEmpty())
					{
						validationMap.put("ChargeTaxesT&C", "FAIL");
					}
					else
					{
					    validationMap.put("ChargeTaxesT&C", "PASS");
					}
				}
				return validationMap;
			}
			
			//Get Pricing overall status from the validation
			public static Map<String, String> getOverallPricingStatus(ArrayList<Map<String, String>> datadetails,ArrayList<String> pass,ArrayList<String> fail)
			{
				Map<String, String> validationMap = new HashMap<String,String>();
				Set<String> pricingchk = new HashSet<String>();
				
				for(int j=0;j<datadetails.size();j++)			
				{
					Map<String,Map<String,String>> specMap = new HashMap<String,Map<String,String>>();					
					specMap.put("keyvalue",datadetails.get(j));
					pricingchk.add(GenericFunctions.getData(specMap,"keyvalue","AOSRespPricingStatus"));	
				}
				if(pricingchk.contains("FAIL"))
				{
					if(pass.size() == 0)
					{
						validationMap.put("AOSRespPricingStatus", "FAIL - all countries");
					}
					else
					{
						validationMap.put("AOSRespPricingStatus", "FAIL - "+fail.toString());
					}
				}
				else
				{
					validationMap.put("AOSRespPricingStatus", "PASS");
				}
				return validationMap;
			}
			
			//Get Pricing overall status from the validation
			public static Map<String, String> getOverallPricingStatus(ArrayList<Map<String, String>> datadetails,ArrayList<String> pass,ArrayList<String> fail,String key)
			{
				Map<String, String> validationMap = new HashMap<String,String>();
				Set<String> pricingchk = new HashSet<String>();
				
				for(int j=0;j<datadetails.size();j++)			
				{
					Map<String,Map<String,String>> specMap = new HashMap<String,Map<String,String>>();					
					specMap.put("keyvalue",datadetails.get(j));
					pricingchk.add(GenericFunctions.getData(specMap,"keyvalue",key));	
				}
				if(pricingchk.contains("FAIL"))
				{
					if(pass.size() == 0)
					{
						validationMap.put(key, "FAIL - all countries");
					}
					else
					{
						validationMap.put(key, "FAIL - "+fail.toString());
					}
				}
				else
				{
					validationMap.put(key, "PASS");
				}
				return validationMap;
			}
			
			public static Map<String, String> getOverallFILocalizedStatus(ArrayList<Map<String, String>> datadetails,String value,Map<String,String> countrydetails)
			{
				Map<String, String> validationMap = new HashMap<String,String>();
				
				return validationMap;
			}
			
			//Get FI content Localized overall status from the validation
			public static Map<String, String> getOverallFIContentStatus(ArrayList<Map<String, String>> datadetails,String type,Map<String,String> countrydetails)
			{
				Map<String, String> validationMap = new HashMap<String,String>();
				Set<String> fistatus = new HashSet<String>();
				for(int j=0;j<datadetails.size();j++)			
				{
					Map<String,Map<String,String>> specMap = new HashMap<String,Map<String,String>>();					
					specMap.put("keyvalue",datadetails.get(j));
					fistatus.add(GenericFunctions.getData(specMap,"keyvalue","FILocalized_Status"));					
				}
				if(type.equalsIgnoreCase("AOS"))
				{
					if(fistatus.contains("FAIL"))
					{
						validationMap.put("AOSFIContentLocalizedStatus", "FAIL - for "+countrydetails.get("FILocalized_Status"));
					}
					else
					{
						validationMap.put("AOSFIContentLocalizedStatus", "PASS");
					}
				}
				else
				{
					if(fistatus.contains("FAIL"))
					{
						validationMap.put("MCSFIContentLocalizedStatus", "FAIL - for "+countrydetails.get("FILocalized_Status"));
					}
					else
					{
						validationMap.put("MCSFIContentLocalizedStatus", "PASS");
					}
				}
				return validationMap;
			}
			
			
			//Get MCS Localized overall status from the validation
			public static Map<String, String> getOverallMCSStatus(ArrayList<Map<String, String>> datadetails,String value,Map<String,String> countrydetails)
			{
				Map<String, String> validationMap = new HashMap<String,String>();
				Set<String> mcsloc = new HashSet<String>();
				Set<String> mcsicon = new HashSet<String>();
				Set<String> mcscontent= new HashSet<String>();
				Set<String> plantype = new HashSet<String>();
				Set<String> startedstatus = new HashSet<String>();
				Set<String> provisionstatus = new HashSet<String>();
				Set<String> mcshelphref = new HashSet<String>();
				Set<String> mcshelptext = new HashSet<String>();
				Set<String> hrefloading = new HashSet<String>();
				
				for(int j=0;j<datadetails.size();j++)			
				{
					Map<String,Map<String,String>> specMap = new HashMap<String,Map<String,String>>();					
					specMap.put("keyvalue",datadetails.get(j));
					mcsloc.add(GenericFunctions.getData(specMap,"keyvalue","MCSLocStatus"));	
					mcsicon.add(GenericFunctions.getData(specMap,"keyvalue","MCSICONStatus"));
					mcscontent.add(GenericFunctions.getData(specMap,"keyvalue","MCSContentLocalized_Status"));
					plantype.add(GenericFunctions.getData(specMap,"keyvalue","plan_type_status"));
					startedstatus.add(GenericFunctions.getData(specMap,"keyvalue","Started_link_Status"));
					provisionstatus.add(GenericFunctions.getData(specMap,"keyvalue","provisioned_Status"));
					mcshelphref.add(GenericFunctions.getData(specMap,"keyvalue","MCSHelpHrefLinksStatus"));
					mcshelptext.add(GenericFunctions.getData(specMap,"keyvalue","MCSHelpTextStatus"));
					hrefloading.add(GenericFunctions.getData(specMap,"keyvalue","HelpLinksloadingStatus"));					
				}
				if(value.equalsIgnoreCase("FIG"))
				{
					if(mcshelphref.contains("FAIL"))
					{
						validationMap.put("FIGMCSHelpHrefLinksStatus", "FAIL - for "+countrydetails.get("MCSHelpHrefLinksStatus"));
					}
					else
					{
						validationMap.put("FIGMCSHelpHrefLinksStatus", "PASS");
					}
					if(mcshelptext.contains("FAIL"))
					{
						validationMap.put("FIGMCSHelpTextStatus", "FAIL - for "+countrydetails.get("MCSHelpTextStatus"));
					}
					else
					{
						validationMap.put("FIGMCSHelpTextStatus", "PASS");
					}
					if(hrefloading.contains("FAIL"))
					{
						validationMap.put("FIGHelpLinksloadingStatus", "FAIL - for "+countrydetails.get("HelpLinksloadingStatus"));
					}
					else
					{
						validationMap.put("FIGHelpLinksloadingStatus", "PASS");
					}
					
					if(mcsloc.contains("FAIL"))
					{
						validationMap.put("FIGMCSLocStatus", "FAIL - for "+countrydetails.get("MCSLocStatus"));
					}
					else
					{
						validationMap.put("FIGMCSLocStatus", "PASS");
					}
					if(mcsicon.contains("FAIL"))
					{
						validationMap.put("FIGMCSICONStatus", "FAIL - for "+countrydetails.get("MCSICONStatus"));
					}
					else
					{
						validationMap.put("FIGMCSICONStatus", "PASS");
					}
					if(mcscontent.contains("FAIL"))
					{
						validationMap.put("FIGMCSContentLocalizedStatus", "FAIL - for "+countrydetails.get("MCSContentLocalized_Status"));
					}
					else
					{
						validationMap.put("FIGMCSContentLocalizedStatus", "PASS");
					}
					if(provisionstatus.contains("FAIL"))
					{
						validationMap.put("FIGProvisionedUserStatus", "FAIL - for "+countrydetails.get("provisioned_Status"));
					}
					else
					{
						validationMap.put("FIGProvisionedUserStatus", "PASS");
					}
				}
				else
				{
					if(mcshelphref.contains("FAIL"))
					{
						validationMap.put("MCSHelpHrefLinksStatus", "FAIL - for "+countrydetails.get("MCSHelpHrefLinksStatus"));
					}
					else
					{
						validationMap.put("MCSHelpHrefLinksStatus", "PASS");
					}
					if(mcshelptext.contains("FAIL"))
					{
						validationMap.put("MCSHelpTextStatus", "FAIL - for "+countrydetails.get("MCSHelpTextStatus"));
					}
					else
					{
						validationMap.put("MCSHelpTextStatus", "PASS");
					}
					if(hrefloading.contains("FAIL"))
					{
						validationMap.put("HelpLinksloadingStatus", "FAIL - for "+countrydetails.get("HelpLinksloadingStatus"));
					}
					else
					{
						validationMap.put("HelpLinksloadingStatus", "PASS");
					}
					
					if(mcsloc.contains("FAIL"))
					{
						validationMap.put("MCSLocStatus", "FAIL - for "+countrydetails.get("MCSLocStatus"));
					}
					else
					{
						validationMap.put("MCSLocStatus", "PASS");
					}
					if(mcsicon.contains("FAIL"))
					{
						validationMap.put("MCSICONStatus", "FAIL - for "+countrydetails.get("MCSICONStatus"));
					}
					else
					{
						validationMap.put("MCSICONStatus", "PASS");
					}
	
					if(plantype.contains("FAIL"))
					{
						validationMap.put("PlanTypeStatus", "FAIL - for "+countrydetails.get("plan_type_status"));
					}
					else
					{
						validationMap.put("PlanTypeStatus", "PASS");
					}
					if(mcscontent.contains("FAIL"))
					{
						validationMap.put("MCSContentLocalizedStatus", "FAIL - for "+countrydetails.get("MCSContentLocalized_Status"));
					}
					else
					{
						validationMap.put("MCSContentLocalizedStatus", "PASS");
					}
					if(startedstatus.contains("FAIL"))
					{
						validationMap.put("GettingStartedlinkStatus", "FAIL - for "+countrydetails.get("Started_link_Status"));
					}
					else
					{
						validationMap.put("GettingStartedlinkStatus", "PASS");
					}
					if(provisionstatus.contains("FAIL"))
					{
						validationMap.put("ProvisionedUserStatus", "FAIL - for "+countrydetails.get("provisioned_Status"));
					}
					else
					{
						validationMap.put("ProvisionedUserStatus", "PASS");
					}
				}
				return validationMap;
			}
			
			public static String FIValidation(String value,JSONObject jsobj,String code,String family,String offervalue)
			{	
				String status = "FAIL";
				if(code.equalsIgnoreCase("cc_storage"))
				{
					Map<String,String> specdata = new HashMap<String,String>();	
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","cc_storage");
					specdata.put("type", "QUOTA");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "true");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "PERSON");
					/*if(value.equalsIgnoreCase("ETLA") || value.equalsIgnoreCase("EVIP") || value.equalsIgnoreCase("VIP"))
					{
						if(family.equalsIgnoreCase("INCOPY"))
						{
							specdata.put("cap", "100");
						}
						else
						{
							specdata.put("cap", "1024");
						}					
					}
					else
					{
						specdata.put("cap", "100");
					}	*/			
					//specdata.put("unit", "GB");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					//respdata.put("cap", jsobj.getJSONObject("charging_model").get("cap").toString());
					//respdata.put("unit", jsobj.getJSONObject("charging_model").get("unit").toString());
					
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - cc_storage : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{						
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - cc_storage :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - cc_storage : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}				
				}
				else if(code.equalsIgnoreCase("dc_storage"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","dc_storage");
					specdata.put("type", "QUOTA");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "PERSON");
					/*if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("cap", "1024");
					}
					else
					{
						specdata.put("cap", "100");
					}
					specdata.put("unit", "GB");*/
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					//respdata.put("cap", jsobj.getJSONObject("charging_model").get("cap").toString());
					//respdata.put("unit", jsobj.getJSONObject("charging_model").get("unit").toString());
					
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - dc_storage : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - dc_storage :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - dc_storage : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}			
				}
				else if(code.equalsIgnoreCase("grace_allowed"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","grace_allowed");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					if(offervalue.equalsIgnoreCase("NUELL"))
					{
						specdata.put("delegation_type", "MACHINE");
						respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					}
					else
					{
						//specdata.put("delegation_type", "NA");
					}
					
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}					
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{						
						if(offervalue.equalsIgnoreCase("NUELL"))
						{
							status =  "PASS";
						}
						else
						{
							if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
							{
								status =  "PASS";
							}
							else
							{
								status =  "FAIL - grace_allowed : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
							}
						}
					}
					else
					{						
						if(offervalue.equalsIgnoreCase("NUELL"))
						{
							status =  "FAIL - grace_allowed : "+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{													
							if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
							{
								status =  "FAIL - grace_allowed :"+ statusMap.get(Constants.ErrorStatus);
							}
							else
							{
								status =  "FAIL - grace_allowed : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
							}
						}
					}
				}
				else if(code.equalsIgnoreCase("purchase_auth_allowed"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","purchase_auth_allowed");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					if(offervalue.equalsIgnoreCase("NUELL"))
					{
						specdata.put("delegation_type", "MACHINE");
						respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					}
					else
					{
						//specdata.put("delegation_type", "NA");
					}
					
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					String delegation_type = jsobj.get("delegation_type").toString();					
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{						
						if(offervalue.equalsIgnoreCase("NUELL"))
						{
							status =  "PASS";
						}
						else
						{
							if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
							{
								status =  "PASS";
							}
							else
							{
								status =  "FAIL - purchase_auth_allowed : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
							}
						}
					}
					else
					{
						if(offervalue.equalsIgnoreCase("NUELL"))
						{
							status =  "FAIL - purchase_auth_allowed : "+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{													
							if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
							{
								status =  "FAIL - purchase_auth_allowed :"+ statusMap.get(Constants.ErrorStatus);
							}
							else
							{
								status =  "FAIL - purchase_auth_allowed : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
							}
						}
					}
				}
				else if(code.equalsIgnoreCase("story"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","story");
					specdata.put("type", "SERVICE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "true");
						specdata.put("delegation_configurable", "true");
					}
					//specdata.put("delegation_type", "PERSON");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - story : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - story :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - story : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("spark"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","spark");
					specdata.put("type", "SERVICE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "true");
						specdata.put("delegation_configurable", "true");
					}
					//specdata.put("delegation_type", "PERSON");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - spark : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - spark :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - spark : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
					
				}
				else if(code.equalsIgnoreCase("user_group_assignment"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","user_group_assignment");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - user_group_assignment : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - user_group_assignment :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - user_group_assignment : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("user_sync"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","user_sync");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - user_sync : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - user_sync :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - user_sync : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("domain_claiming"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","domain_claiming");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - domain_claiming : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - domain_claiming :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - domain_claiming : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("data_encryption"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","data_encryption");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - data_encryption : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - data_encryption :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - data_encryption : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("asset_sharing_policy_config"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","asset_sharing_policy_config");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - asset_sharing_policy_config : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - asset_sharing_policy_config :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - asset_sharing_policy_config : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("support_case_creation_allowed"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","support_case_creation_allowed");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - support_case_creation_allowed : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - support_case_creation_allowed :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - support_case_creation_allowed : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("support_role_assignment_allowed"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","support_role_assignment_allowed");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - support_role_assignment_allowed : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - support_role_assignment_allowed :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - support_role_assignment_allowed : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("overdelegation_allowed"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","overdelegation_allowed");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "true");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - overdelegation_allowed : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - overdelegation_allowed :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - overdelegation_allowed : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("deployment_permission_management"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","deployment_permission_management");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillment_configurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegation_configurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegation_type").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - deployment_permission_management : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - deployment_permission_management :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - deployment_permission_management : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				return status;
			}
			
					
			public static String chkAllFIfieldsStatus(String value,JSONObject jsobj,String code,String offervalue,String family,String segment)
            {
        	   StringBuilder sb = new StringBuilder();
        	   String status = "";
        	   if(value.equalsIgnoreCase("NO"))
        	   {
	        	   if(jsobj.get("type").toString().equalsIgnoreCase("DESKTOP"))
	        	   {
	        		   /*if(segment.equalsIgnoreCase("ETLA"))
	        		   {
		        		   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
		        		   {
		        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
		        		   }
		        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
		        		   {
		        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
		        		   }
	        		   }*/
	        		   if(offervalue.equalsIgnoreCase("NUELL"))
	        		   {
	        			   if(!(jsobj.get("delegation_type").toString().equalsIgnoreCase("MACHINE")))
	        			   {
	        				   sb.append("delegation_type : (ExceptedResults : MACHINE , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
	        			   }
	        		   }
	        		   else
	        		   {
	        			   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegation_type").toString().equalsIgnoreCase("ORGANIZATION"))
	        			   {
	        				   //TODO
	        			   }
	        			   else
	        			   {
	        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
	        			   }
	        		   }

	        		   if(sb.length() == 0)
	        		   {
	        			   status = "PASS";
	        		   }
	        		   else
	        		   {
	        			   status = code+": FAIL - "+sb.toString();
	        		   }
	        	   }
	        	   if(jsobj.get("type").toString().equalsIgnoreCase("FEATURE"))
	        	   {
	        		   if(code.equalsIgnoreCase("overdelegation_allowed"))
	        		   {
	        			   /*if(segment.equalsIgnoreCase("ETLA"))
		        		   {
		        			   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
			        		   {
			        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
			        		   }
			        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
			        		   }
		        		   }*/
		        		   if(offervalue.equalsIgnoreCase("NUELL"))
		        		   {
		        			   if(code.equalsIgnoreCase("user_group_assignment") || code.equalsIgnoreCase("user_permission_management") || code.equalsIgnoreCase("user_sync") || code.equalsIgnoreCase("domain_claiming") || code.equalsIgnoreCase("data_encryption") || code.equalsIgnoreCase("support_case_creation_allowed") || code.equalsIgnoreCase("support_role_assignment_allowed"))
			        		   {
		        				   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegation_type").toString().equalsIgnoreCase("ORGANIZATION"))
			        			   {
			        				   //TODO
			        			   }
			        			   else
			        			   {
			        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
			        			   }
			        		   }
		        			   else if(code.equalsIgnoreCase("laboratory_license_mgmt"))
		        			   {
		        				   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("MACHINE"))
		        				   {
		        					   //TODO
		        				   }
		        				   else
		        				   {
		        					   sb.append("delegation_type : (ExceptedResults : should have either - NA/MACHINE , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
		        				   }
		        			   }
		        			   else
		        			   {
		        				   if(!(jsobj.get("delegation_type").toString().equalsIgnoreCase("MACHINE")))
				        		   {
				        			   sb.append("delegation_type : (ExceptedResults : MACHINE , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
				        		   }
		        			   }
		        		   }
		        		   else
		        		   {
		        			   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegation_type").toString().equalsIgnoreCase("ORGANIZATION"))
		        			   {
		        				   //TODO
		        			   }
		        			   else
		        			   {
		        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
		        			   }
		        		   }
		        		  
		        		   if(sb.length() == 0)
		        		   {
		        			   status = "PASS";
		        		   }
		        		   else
		        		   {
		        			   status = code+": FAIL - "+sb.toString();
		        		   }
	        		   }
	        		   else
	        		   {
	        			   if(segment.equalsIgnoreCase("ETLA"))
	        			   {
	        				   if(family.equalsIgnoreCase("SIGN") || family.contains("SIGN"))
	        				   {
		        				   if(code.equalsIgnoreCase("sign_service_level") || code.equalsIgnoreCase("consumption_start_date") || code.equalsIgnoreCase("consumption_cycle_duration"))
		        				   {
		        					   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("true")))
					        		   {
					        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
					        		   }
					        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
					        		   }
		        				   }
		        				   else if(code.equalsIgnoreCase("msft_integration_readiness"))
		        				   {
		        					   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
					        		   }
					        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
					        		   }
		        				   }
		        				   else
		        				   {
				        			   /*if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
					        		   }
					        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
					        		   }*/
		        				   }
	        				   }
	        				   else
	        				   {
	        					   /*if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
				        		   }
				        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
				        		   }*/
	        				   }
	        			   }
	        			   if(offervalue.equalsIgnoreCase("NUELL"))
		        		   {
		        			   if(code.equalsIgnoreCase("user_group_assignment") || code.equalsIgnoreCase("user_permission_management") || code.equalsIgnoreCase("user_sync") || code.equalsIgnoreCase("domain_claiming") || code.equalsIgnoreCase("data_encryption") || code.equalsIgnoreCase("support_case_creation_allowed") || code.equalsIgnoreCase("support_role_assignment_allowed"))
			        		   {
		        				   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegation_type").toString().equalsIgnoreCase("ORGANIZATION"))
			        			   {
			        				   //TODO
			        			   }
			        			   else
			        			   {
			        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
			        			   }
			        		   }
		        			   else if(code.equalsIgnoreCase("laboratory_license_mgmt"))
		        			   {
		        				   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("MACHINE"))
		        				   {
		        					   //TODO
		        				   }
		        				   else
		        				   {
		        					   sb.append("delegation_type : (ExceptedResults : should have either - NA/MACHINE , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
		        				   }
		        			   }
		        			   else
		        			   {
		        				   if(!(jsobj.get("delegation_type").toString().equalsIgnoreCase("MACHINE")))
				        		   {
				        			   sb.append("delegation_type : (ExceptedResults : MACHINE , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
				        		   }
		        			   }
		        		   }
	        			   else
	        			   {
	        				   if(code.equalsIgnoreCase("sbst_alchemist_feature_access") || code.equalsIgnoreCase("sbst_designer_feature_access") || code.equalsIgnoreCase("sbst_painter_feature_access") || code.equalsIgnoreCase("fresco_feature_access") || code.equalsIgnoreCase("premiere_rush_cc_feature_access") || code.equalsIgnoreCase("robohelp_server_feature_access") || code.equalsIgnoreCase("fmsv_feature_access"))
	        				   {
	        					   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegation_type").toString().equalsIgnoreCase("ORGANIZATION"))
	    	        			   {
	    	        				   //TODO
	    	        			   }
	    	        			   else
	    	        			   {
	    	        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
	    	        			   }
	        				   }
	        				   else
	        				   {
	        					   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegation_type").toString().equalsIgnoreCase("ORGANIZATION"))
	    	        			   {
	    	        				   //TODO
	    	        			   }
	    	        			   else
	    	        			   {
	    	        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
	    	        			   }
	        				   }
	        			   }		        		  
		        		   if(sb.length() == 0)
		        		   {
		        			   status = "PASS";
		        		   }
		        		   else
		        		   {
		        			   status = code+": FAIL - "+sb.toString();
		        		   }
	        		   }
	        		   
	        	   }
	        	   if(jsobj.get("type").toString().equalsIgnoreCase("SERVICE"))
	        	   {
	        		   if(segment.equalsIgnoreCase("ETLA"))
	        		   {
	        			   if(family.equalsIgnoreCase("SIGN") || family.contains("SIGN"))
	        			   {
		        			   if(code.equalsIgnoreCase("acom_esign"))
		        			   {
		        				   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
				        		   }
				        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
				        		   }
		        			   }
		        			   else
		        			   {	        				   	        			   
				        		   /*if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("true")))
				        		   {
				        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
				        		   }
				        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("true")))
				        		   {
				        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
				        		   }*/
		        			   }
	        			   }
	        			   else
	        			   {
	        				   /*if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
			        		   }
			        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
			        		   }*/
	        			   }
	        		   }
        			   if(code.equalsIgnoreCase("dma_acp_cs"))
        			   {
        				   if(!(jsobj.get("delegation_type").toString().equalsIgnoreCase("ORGANIZATION")))
    	        		   {
    	        			   sb.append("delegation_type : (ExceptedResults : ORGANIZATION , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
    	        		   }
        			   }
        			   else
        			   {
        				   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegation_type").toString().equalsIgnoreCase("ORGANIZATION"))
	        			   {
	        				   //TODO
	        			   }
	        			   else
	        			   {
	        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
	        			   }
        			   }
	        		   
	        		   if(sb.length() == 0)
	        		   {
	        			   status = "PASS";
	        		   }
	        		   else
	        		   {
	        			   status = code+": FAIL - "+sb.toString();
	        		   }
	        	   }
        	   }
        	   else
        	   {
        		   if(jsobj.get("type").toString().equalsIgnoreCase("DESKTOP"))
	        	   {
        			   /*if(segment.equalsIgnoreCase("ETLA"))
        			   {
		        		   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
		        		   {
		        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
		        		   }
		        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
		        		   {
		        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
		        		   }
        			   }*/
	        		   if(!(jsobj.get("delegation_type").toString().equalsIgnoreCase("MACHINE")))
	        		   {
	        			   sb.append("delegation_type : (ExceptedResults : MACHINE , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
	        		   }
	        		   if(sb.length() == 0)
	        		   {
	        			   status = "PASS";
	        		   }
	        		   else
	        		   {
	        			   status = code+": FAIL - "+sb.toString();
	        		   }
	        	   }
	        	   if(jsobj.get("type").toString().equalsIgnoreCase("FEATURE"))
	        	   {
	        		   if(code.equalsIgnoreCase("overdelegation_allowed"))
	        		   {
	        			   /*if(segment.equalsIgnoreCase("ETLA"))
	        			   {
		        			   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
			        		   {
			        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
			        		   }
			        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
			        		   }
	        			   }*/
		        		   if(!(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA")))
		        		   {
		        			   sb.append("delegation_type : (ExceptedResults : NA , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
		        		   }
		        		   if(sb.length() == 0)
		        		   {
		        			   status = "PASS";
		        		   }
		        		   else
		        		   {
		        			   status = code+": FAIL - "+sb.toString();
		        		   }
	        		   }
	        		   else
	        		   {
	        			   int p = 0;
		        		   for(int k=0;k<EVIPFILists.length;k++)
		        		   {	        			   
		        			   if(EVIPFILists[k].equalsIgnoreCase(code))
		        			   {
		        				   p = 1; 
		        				   if(segment.equalsIgnoreCase("ETLA"))
		        				   {
		        					   if(family.equalsIgnoreCase("SIGN") || family.contains("SIGN"))
			        				   {
				        				   if(code.equalsIgnoreCase("sign_service_level") || code.equalsIgnoreCase("consumption_start_date") || code.equalsIgnoreCase("consumption_cycle_duration"))
				        				   {
				        					   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("true")))
							        		   {
							        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
							        		   }
							        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
							        		   {
							        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
							        		   }
				        				   }
				        				   else if(code.equalsIgnoreCase("msft_integration_readiness"))
				        				   {
				        					   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
							        		   {
							        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
							        		   }
							        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
							        		   {
							        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
							        		   }
				        				   }
				        				   else
				        				   {
						        			   /*if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
							        		   {
							        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
							        		   }
							        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
							        		   {
							        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
							        		   }*/
				        				   }
			        				   }
			        				   else
			        				   {
			        					   /*if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
						        		   }
						        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
						        		   }*/
			        				   }
		        				   }
		        				   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegation_type").toString().equalsIgnoreCase("ORGANIZATION"))
			        			   {
			        				   //TODO
			        			   }
			        			   else
			        			   {
			        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
			        			   }
			   	        		   if(sb.length() == 0)
			   	        		   {
			   	        			   status = "PASS";
			   	        		   }
			   	        		   else
			   	        		   {
			   	        			   status = code+": FAIL - "+sb.toString();
			   	        		   }	        				  
		        			   }	        			
		        		   }
		        		   if(p == 0)
	        			   {
		        			   if(segment.equalsIgnoreCase("ETLA"))
		        			   {
		        				   if(family.equalsIgnoreCase("SIGN") || family.contains("SIGN"))
		        				   {
			        				   if(code.equalsIgnoreCase("sign_service_level") || code.equalsIgnoreCase("consumption_start_date") || code.equalsIgnoreCase("consumption_cycle_duration"))
			        				   {
			        					   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("true")))
						        		   {
						        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
						        		   }
						        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
						        		   }
			        				   }
			        				   else if(code.equalsIgnoreCase("msft_integration_readiness"))
			        				   {
			        					   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
						        		   }
						        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
						        		   }
			        				   }
			        				   else
			        				   {
					        			   /*if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
						        		   }
						        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
						        		   }*/
			        				   }
		        				   }
		        				   else
		        				   {
		        					   /*if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
					        		   }
					        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
					        		   }*/
		        				   }
		        			   }		        			   
		   	        		   if(!(jsobj.get("delegation_type").toString().equalsIgnoreCase("MACHINE")))
		   	        		   {
		   	        			   if(code.equalsIgnoreCase("disallow_expert_sessions"))
		   	        			   {
			   	        				 if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("MACHINE"))
			   	        				 {
			   	        					 //TODO
			   	        				 }
			   	        				 else
			   	        				 {
			   	        					sb.append("delegation_type : (ExceptedResults : should have either - NA/MACHINE , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
			   	        				 }
		   	        			   }
		   	        			   else
		   	        			   {
		   	        				   sb.append("delegation_type : (ExceptedResults : MACHINE , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
		   	        			   }		   	        			   
		   	        		   }
		   	        		   if(sb.length() == 0)
		   	        		   {
		   	        			   status = "PASS";
		   	        		   }
		   	        		   else
		   	        		   {
		   	        			   status = code+": FAIL - "+sb.toString();
		   	        		   }	   
	        			   }
	        		   }	        		   	        		  
	        	   }
	        	   if(jsobj.get("type").toString().equalsIgnoreCase("SERVICE"))
	        	   {
	        		   if(segment.equalsIgnoreCase("ETLA"))
	        		   {
	        			   if(family.equalsIgnoreCase("SIGN") || family.contains("SIGN"))
	        			   {
		        			   if(code.equalsIgnoreCase("acom_esign"))
		        			   {
		        				   if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
				        		   }
				        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
				        		   }
		        			   }
		        			   else
		        			   {	        				   	        			   
				        		   /*if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("true")))
				        		   {
				        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
				        		   }
				        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("true")))
				        		   {
				        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
				        		   }*/
		        			   }
	        			   }
	        			   else
	        			   {
	        				   /*if(!(jsobj.get("fulfillment_configurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
			        		   }
			        		   if(!(jsobj.get("delegation_configurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
			        		   }*/
	        			   }
	        		   }
	        		   if(code.equalsIgnoreCase("dma_acp_cs"))
        			   {
        				   if(!(jsobj.get("delegation_type").toString().equalsIgnoreCase("ORGANIZATION")))
    	        		   {
    	        			   sb.append("delegation_type : (ExceptedResults : ORGANIZATION , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
    	        		   }
        			   }
        			   else
        			   {
        				   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegation_type").toString().equalsIgnoreCase("ORGANIZATION"))
	        			   {
	        				   //TODO
	        			   }
	        			   else
	        			   {
	        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
	        			   }
        			   }
	        		   if(sb.length() == 0)
	        		   {
	        			   status = "PASS";
	        		   }
	        		   else
	        		   {
	        			   status = code+": FAIL - "+sb.toString();
	        		   }
	        	   }
        	   }
        	  return status; 
           }
			
			public static String caosFIValidation(String value,JSONObject jsobj,String code,String family,String offervalue)
			{	
				String status = "FAIL";
				if(code.equalsIgnoreCase("cc_storage"))
				{
					Map<String,String> specdata = new HashMap<String,String>();	
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","cc_storage");
					specdata.put("type", "QUOTA");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "true");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "PERSON");
					/*if(value.equalsIgnoreCase("ETLA") || value.equalsIgnoreCase("EVIP") || value.equalsIgnoreCase("VIP"))
					{
						if(family.equalsIgnoreCase("INCOPY"))
						{
							specdata.put("cap", "100");
						}
						else
						{
							specdata.put("cap", "1024");
						}					
					}
					else
					{
						specdata.put("cap", "100");
					}*/				
					specdata.put("unit", "GB");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					respdata.put("cap", jsobj.getJSONObject("chargingModel").get("cap").toString());
					respdata.put("unit", jsobj.getJSONObject("chargingModel").get("unit").toString());
					
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - cc_storage : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{						
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - cc_storage :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - cc_storage : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}				
				}
				else if(code.equalsIgnoreCase("dc_storage"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","dc_storage");
					specdata.put("type", "QUOTA");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "PERSON");
					/*if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("cap", "1024");
					}
					else
					{
						specdata.put("cap", "100");
					}
					specdata.put("unit", "GB");*/
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					//respdata.put("cap", jsobj.getJSONObject("chargingModel").get("cap").toString());
					//respdata.put("unit", jsobj.getJSONObject("chargingModel").get("unit").toString());
					
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - dc_storage : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - dc_storage :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - dc_storage : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}			
				}
				else if(code.equalsIgnoreCase("grace_allowed"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","grace_allowed");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					if(offervalue.equalsIgnoreCase("NUELL"))
					{
						specdata.put("delegation_type", "MACHINE");
						respdata.put("delegation_type", jsobj.get("delegationType").toString());
					}
					else
					{
						//specdata.put("delegation_type", "NA");
					}
					
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}					
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{						
						if(offervalue.equalsIgnoreCase("NUELL"))
						{
							status =  "PASS";
						}
						else
						{
							if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
							{
								status =  "PASS";
							}
							else
							{
								status =  "FAIL - grace_allowed : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
							}
						}
					}
					else
					{						
						if(offervalue.equalsIgnoreCase("NUELL"))
						{
							status =  "FAIL - grace_allowed : "+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{													
							if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
							{
								status =  "FAIL - grace_allowed :"+ statusMap.get(Constants.ErrorStatus);
							}
							else
							{
								status =  "FAIL - grace_allowed : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
							}
						}
					}
				}
				else if(code.equalsIgnoreCase("purchase_auth_allowed"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","purchase_auth_allowed");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					if(offervalue.equalsIgnoreCase("NUELL"))
					{
						specdata.put("delegation_type", "MACHINE");
						respdata.put("delegation_type", jsobj.get("delegationType").toString());
					}
					else
					{
						//specdata.put("delegation_type", "NA");
					}
					
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					String delegation_type = jsobj.get("delegationType").toString();					
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{						
						if(offervalue.equalsIgnoreCase("NUELL"))
						{
							status =  "PASS";
						}
						else
						{
							if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
							{
								status =  "PASS";
							}
							else
							{
								status =  "FAIL - purchase_auth_allowed : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
							}
						}
					}
					else
					{
						if(offervalue.equalsIgnoreCase("NUELL"))
						{
							status =  "FAIL - purchase_auth_allowed : "+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{													
							if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
							{
								status =  "FAIL - purchase_auth_allowed :"+ statusMap.get(Constants.ErrorStatus);
							}
							else
							{
								status =  "FAIL - purchase_auth_allowed : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
							}
						}
					}
				}
				else if(code.equalsIgnoreCase("story"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","story");
					specdata.put("type", "SERVICE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "true");
						specdata.put("delegation_configurable", "true");
					}
					//specdata.put("delegation_type", "PERSON");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - story : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - story :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - story : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("spark"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","spark");
					specdata.put("type", "SERVICE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "true");
						specdata.put("delegation_configurable", "true");
					}
					//specdata.put("delegation_type", "PERSON");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - spark : "+"delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - spark :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - spark : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
					
				}
				else if(code.equalsIgnoreCase("user_group_assignment"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","user_group_assignment");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - user_group_assignment : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - user_group_assignment :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - user_group_assignment : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("user_sync"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","user_sync");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - user_sync : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - user_sync :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - user_sync : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("domain_claiming"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","domain_claiming");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - domain_claiming : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - domain_claiming :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - domain_claiming : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("data_encryption"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","data_encryption");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap =GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - data_encryption : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - data_encryption :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - data_encryption : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("asset_sharing_policy_config"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","asset_sharing_policy_config");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - asset_sharing_policy_config : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - asset_sharing_policy_config :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - asset_sharing_policy_config : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("support_case_creation_allowed"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","support_case_creation_allowed");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - support_case_creation_allowed : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - support_case_creation_allowed :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - support_case_creation_allowed : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("support_role_assignment_allowed"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","support_role_assignment_allowed");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - support_role_assignment_allowed : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - support_role_assignment_allowed :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - support_role_assignment_allowed : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("overdelegation_allowed"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","overdelegation_allowed");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "true");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - overdelegation_allowed : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - overdelegation_allowed :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - overdelegation_allowed : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				else if(code.equalsIgnoreCase("deployment_permission_management"))
				{
					Map<String,String> specdata = new HashMap<String,String>();		
					Map<String,String> respdata = new HashMap<String,String>();	
					//Excepted details to validate against the response data
					specdata.put("code","deployment_permission_management");
					specdata.put("type", "FEATURE");
					if(value.equalsIgnoreCase("ETLA"))
					{
						specdata.put("fulfillment_configurable", "false");
						specdata.put("delegation_configurable", "false");
					}
					//specdata.put("delegation_type", "NA");
					//Get details from AOS response
					respdata.put("code", jsobj.get("code").toString());
					respdata.put("type", jsobj.get("type").toString());
					if(value.equalsIgnoreCase("ETLA"))
					{
						respdata.put("fulfillment_configurable", jsobj.get("fulfillmentConfigurable").toString());
						respdata.put("delegation_configurable", jsobj.get("delegationConfigurable").toString());
					}
					//respdata.put("delegation_type", jsobj.get("delegation_type").toString());
					String delegation_type = jsobj.get("delegationType").toString();
					Map<String,String> statusMap = GenericFunctions.compareMaps1(specdata,respdata);
					if(statusMap.get(Constants.TESTRESULT).equalsIgnoreCase("PASS"))
					{
						status =  "PASS";
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "PASS";
						}
						else
						{
							status =  "FAIL - deployment_permission_management : "+"delegation_type should have either - NA/PERSON/ORGANIZATION";
						}
					}
					else
					{
						if(delegation_type.equalsIgnoreCase("NA") || delegation_type.equalsIgnoreCase("PERSON") || delegation_type.equalsIgnoreCase("ORGANIZATION"))
						{
							status =  "FAIL - deployment_permission_management :"+ statusMap.get(Constants.ErrorStatus);
						}
						else
						{
							status =  "FAIL - deployment_permission_management : "+ statusMap.get(Constants.ErrorStatus) + ", delegation_type value should have either - NA/PERSON/ORGANIZATION";
						}
					}
				}
				return status;
			}
			
			public static String caosChkAllFIfieldsStatus(String value,JSONObject jsobj,String code,String offervalue,String family,String segment)
            {
        	   StringBuilder sb = new StringBuilder();
        	   String status = "";
        	   if(value.equalsIgnoreCase("NO"))
        	   {
	        	   if(jsobj.get("type").toString().equalsIgnoreCase("DESKTOP"))
	        	   {
	        		   /*if(segment.equalsIgnoreCase("ETLA"))
	        		   {
		        		   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
		        		   {
		        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillmentConfigurable").toString()+")").append(",");
		        		   }
		        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
		        		   {
		        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegationConfigurable").toString()+")").append(","); 
		        		   }
	        		   }*/
	        		   if(offervalue.equalsIgnoreCase("NUELL"))
	        		   {
	        			   if(!(jsobj.get("delegationType").toString().equalsIgnoreCase("MACHINE")))
	        			   {
	        				   sb.append("delegation_type : (ExceptedResults : MACHINE , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
	        			   }
	        		   }
	        		   else
	        		   {
	        			   if(jsobj.get("delegationType").toString().equalsIgnoreCase("NA") || jsobj.get("delegationType").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegationType").toString().equalsIgnoreCase("ORGANIZATION"))
	        			   {
	        				   //TODO
	        			   }
	        			   else
	        			   {
	        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
	        			   }
	        		   }

	        		   if(sb.length() == 0)
	        		   {
	        			   status = "PASS";
	        		   }
	        		   else
	        		   {
	        			   status = code+": FAIL - "+sb.toString();
	        		   }
	        	   }
	        	   if(jsobj.get("type").toString().equalsIgnoreCase("FEATURE"))
	        	   {
	        		   if(code.equalsIgnoreCase("overdelegation_allowed"))
	        		   {
	        			   /*if(segment.equalsIgnoreCase("ETLA"))
		        		   {
		        			   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
			        		   {
			        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillmentConfigurable").toString()+")").append(",");
			        		   }
			        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegationConfigurable").toString()+")").append(",");
			        		   }
		        		   }*/
		        		   if(offervalue.equalsIgnoreCase("NUELL"))
		        		   {
		        			   if(code.equalsIgnoreCase("user_group_assignment") || code.equalsIgnoreCase("user_permission_management") || code.equalsIgnoreCase("user_sync") || code.equalsIgnoreCase("domain_claiming") || code.equalsIgnoreCase("data_encryption") || code.equalsIgnoreCase("support_case_creation_allowed") || code.equalsIgnoreCase("support_role_assignment_allowed"))
			        		   {
		        				   if(jsobj.get("delegationType").toString().equalsIgnoreCase("NA") || jsobj.get("delegationType").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegationType").toString().equalsIgnoreCase("ORGANIZATION"))
			        			   {
			        				   //TODO
			        			   }
			        			   else
			        			   {
			        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
			        			   }
			        		   }
		        			   else if(code.equalsIgnoreCase("laboratory_license_mgmt"))
		        			   {
		        				   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("MACHINE"))
		        				   {
		        					   //TODO
		        				   }
		        				   else
		        				   {
		        					   sb.append("delegation_type : (ExceptedResults : should have either - NA/MACHINE , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
		        				   }
		        			   }
		        			   else
		        			   {
		        				   if(!(jsobj.get("delegationType").toString().equalsIgnoreCase("MACHINE")))
				        		   {
				        			   sb.append("delegation_type : (ExceptedResults : MACHINE , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
				        		   }
		        			   }
		        		   }
		        		   else
		        		   {
		        			   if(jsobj.get("delegationType").toString().equalsIgnoreCase("NA") || jsobj.get("delegationType").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegationType").toString().equalsIgnoreCase("ORGANIZATION"))
		        			   {
		        				   //TODO
		        			   }
		        			   else
		        			   {
		        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
		        			   }
		        		   }
		        		  
		        		   if(sb.length() == 0)
		        		   {
		        			   status = "PASS";
		        		   }
		        		   else
		        		   {
		        			   status = code+": FAIL - "+sb.toString();
		        		   }
	        		   }
	        		   else
	        		   {
	        			   if(segment.equalsIgnoreCase("ETLA"))
	        			   {
	        				   if(family.equalsIgnoreCase("SIGN") || family.contains("SIGN"))
	        				   {
	        					   if(code.equalsIgnoreCase("sign_service_level") || code.equalsIgnoreCase("consumption_start_date") || code.equalsIgnoreCase("consumption_cycle_duration"))
		        				   {
		        					   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("true")))
					        		   {
					        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
					        		   }
					        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
					        		   }
		        				   }
		        				   else if(code.equalsIgnoreCase("msft_integration_readiness"))
		        				   {
		        					   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
					        		   }
					        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
					        		   }
		        				   }
		        				   else
		        				   {
				        			   /*if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
					        		   }
					        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
					        		   {
					        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
					        		   }*/
		        				   }
	        				   }
	        				   else
	        				   {
			        			   /*if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillmentConfigurable").toString()+")").append(",");
				        		   }
				        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegationConfigurable").toString()+")").append(",");
				        		   }*/
	        				   }
	        			   }
	        			   if(offervalue.equalsIgnoreCase("NUELL"))
		        		   {
		        			   if(code.equalsIgnoreCase("user_group_assignment") || code.equalsIgnoreCase("user_permission_management") || code.equalsIgnoreCase("user_sync") || code.equalsIgnoreCase("domain_claiming") || code.equalsIgnoreCase("data_encryption") || code.equalsIgnoreCase("support_case_creation_allowed") || code.equalsIgnoreCase("support_role_assignment_allowed"))
			        		   {
		        				   if(jsobj.get("delegationType").toString().equalsIgnoreCase("NA") || jsobj.get("delegationType").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegationType").toString().equalsIgnoreCase("ORGANIZATION"))
			        			   {
			        				   //TODO
			        			   }
			        			   else
			        			   {
			        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
			        			   }
			        		   }
		        			   else if(code.equalsIgnoreCase("laboratory_license_mgmt"))
		        			   {
		        				   if(jsobj.get("delegation_type").toString().equalsIgnoreCase("NA") || jsobj.get("delegation_type").toString().equalsIgnoreCase("MACHINE"))
		        				   {
		        					   //TODO
		        				   }
		        				   else
		        				   {
		        					   sb.append("delegation_type : (ExceptedResults : should have either - NA/MACHINE , ActualResults : "+jsobj.get("delegation_type").toString()+")").append(",");
		        				   }
		        			   }
		        			   else
		        			   {
		        				   if(!(jsobj.get("delegationType").toString().equalsIgnoreCase("MACHINE")))
				        		   {
				        			   sb.append("delegation_type : (ExceptedResults : MACHINE , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
				        		   }
		        			   }
		        		   }
	        			   else
	        			   {
	        				   if(code.equalsIgnoreCase("sbst_alchemist_feature_access") || code.equalsIgnoreCase("sbst_designer_feature_access") || code.equalsIgnoreCase("sbst_painter_feature_access") || code.equalsIgnoreCase("fresco_feature_access") || code.equalsIgnoreCase("premiere_rush_cc_feature_access") || code.equalsIgnoreCase("robohelp_server_feature_access") || code.equalsIgnoreCase("fmsv_feature_access"))
	        				   {
	        					   if(jsobj.get("delegationType").toString().equalsIgnoreCase("NA") || jsobj.get("delegationType").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegationType").toString().equalsIgnoreCase("ORGANIZATION"))
	    	        			   {
	    	        				   //TODO
	    	        			   }
	    	        			   else
	    	        			   {
	    	        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
	    	        			   }
	        				   }
	        				   else
	        				   {
	        					   if(jsobj.get("delegationType").toString().equalsIgnoreCase("NA") || jsobj.get("delegationType").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegationType").toString().equalsIgnoreCase("ORGANIZATION"))
	    	        			   {
	    	        				   //TODO
	    	        			   }
	    	        			   else
	    	        			   {
	    	        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
	    	        			   }
	        				   }
	        			   }		        		  
		        		   if(sb.length() == 0)
		        		   {
		        			   status = "PASS";
		        		   }
		        		   else
		        		   {
		        			   status = code+": FAIL - "+sb.toString();
		        		   }
	        		   }
	        		   
	        	   }
	        	   if(jsobj.get("type").toString().equalsIgnoreCase("SERVICE"))
	        	   {
	        		   if(segment.equalsIgnoreCase("ETLA"))
	        		   {
	        			   if(family.equalsIgnoreCase("SIGN") || family.contains("SIGN"))
	        			   {
	        				   if(code.equalsIgnoreCase("acom_esign"))
		        			   {
		        				   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
				        		   }
				        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
				        		   }
		        			   }
		        			   else
		        			   {	        				   	        			   
				        		   /*if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("true")))
				        		   {
				        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
				        		   }
				        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("true")))
				        		   {
				        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
				        		   }*/
		        			   }
	        			   }
	        			   else
	        			   {
	        				   /*if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillmentConfigurable").toString()+")").append(",");
			        		   }
			        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegationConfigurable").toString()+")").append(","); 
			        		   }*/
	        			   }		        		  
	        		   }
        			   if(code.equalsIgnoreCase("dma_acp_cs"))
        			   {
        				   if(!(jsobj.get("delegationType").toString().equalsIgnoreCase("ORGANIZATION")))
    	        		   {
    	        			   sb.append("delegation_type : (ExceptedResults : ORGANIZATION , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
    	        		   }
        			   }
        			   else
        			   {
        				   if(jsobj.get("delegationType").toString().equalsIgnoreCase("NA") || jsobj.get("delegationType").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegationType").toString().equalsIgnoreCase("ORGANIZATION"))
	        			   {
	        				   //TODO
	        			   }
	        			   else
	        			   {
	        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
	        			   }
        			   }
	        		   
	        		   if(sb.length() == 0)
	        		   {
	        			   status = "PASS";
	        		   }
	        		   else
	        		   {
	        			   status = code+": FAIL - "+sb.toString();
	        		   }
	        	   }
        	   }
        	   else
        	   {
        		   if(jsobj.get("type").toString().equalsIgnoreCase("DESKTOP"))
	        	   {
        			   /*if(segment.equalsIgnoreCase("ETLA"))
        			   {
		        		   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
		        		   {
		        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillmentConfigurable").toString()+")").append(",");
		        		   }
		        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
		        		   {
		        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegationConfigurable").toString()+")").append(","); 
		        		   }
        			   }*/
	        		   if(!(jsobj.get("delegationType").toString().equalsIgnoreCase("MACHINE")))
	        		   {
	        			   sb.append("delegation_type : (ExceptedResults : MACHINE , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
	        		   }
	        		   if(sb.length() == 0)
	        		   {
	        			   status = "PASS";
	        		   }
	        		   else
	        		   {
	        			   status = code+": FAIL - "+sb.toString();
	        		   }
	        	   }
	        	   if(jsobj.get("type").toString().equalsIgnoreCase("FEATURE"))
	        	   {
	        		   if(code.equalsIgnoreCase("overdelegation_allowed"))
	        		   {
	        			   /*if(segment.equalsIgnoreCase("ETLA"))
	        			   {
		        			   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
			        		   {
			        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillmentConfigurable").toString()+")").append(",");
			        		   }
			        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegationConfigurable").toString()+")").append(",");
			        		   }
	        			   }*/
		        		   if(!(jsobj.get("delegationType").toString().equalsIgnoreCase("NA")))
		        		   {
		        			   sb.append("delegation_type : (ExceptedResults : NA , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
		        		   }
		        		   if(sb.length() == 0)
		        		   {
		        			   status = "PASS";
		        		   }
		        		   else
		        		   {
		        			   status = code+": FAIL - "+sb.toString();
		        		   }
	        		   }
	        		   else
	        		   {
	        			   int p = 0;
		        		   for(int k=0;k<EVIPFILists.length;k++)
		        		   {	        			   
		        			   if(EVIPFILists[k].equalsIgnoreCase(code))
		        			   {
		        				   p = 1; 
		        				   if(segment.equalsIgnoreCase("ETLA"))
		        				   {
		        					   if(family.equalsIgnoreCase("SIGN") || family.contains("SIGN"))
		        					   {
		        						   if(code.equalsIgnoreCase("sign_service_level") || code.equalsIgnoreCase("consumption_start_date") || code.equalsIgnoreCase("consumption_cycle_duration"))
				        				   {
				        					   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("true")))
							        		   {
							        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
							        		   }
							        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
							        		   {
							        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
							        		   }
				        				   }
				        				   else if(code.equalsIgnoreCase("msft_integration_readiness"))
				        				   {
				        					   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
							        		   {
							        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
							        		   }
							        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
							        		   {
							        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
							        		   }
				        				   }
				        				   else
				        				   {
						        			   /*if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
							        		   {
							        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
							        		   }
							        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
							        		   {
							        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
							        		   }*/
				        				   }
		        					   }
		        					   else
		        					   {
		        						   /*if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
					   	        		   {
					   	        			  sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillmentConfigurable").toString()+")").append(",");
					   	        		   }
					   	        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
					   	        		   {
					   	        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegationConfigurable").toString()+")").append(",");
					   	        		   }*/
		        					   }				        			   
		        				   }
		        				   if(jsobj.get("delegationType").toString().equalsIgnoreCase("NA") || jsobj.get("delegationType").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegationType").toString().equalsIgnoreCase("ORGANIZATION"))
			        			   {
			        				   //TODO
			        			   }
			        			   else
			        			   {
			        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
			        			   }
			   	        		   if(sb.length() == 0)
			   	        		   {
			   	        			   status = "PASS";
			   	        		   }
			   	        		   else
			   	        		   {
			   	        			   status = code+": FAIL - "+sb.toString();
			   	        		   }	        				  
		        			   }	        			
		        		   }
		        		   if(p == 0)
	        			   {
		        			   if(segment.equalsIgnoreCase("ETLA"))
		        			   {
		        				   if(family.equalsIgnoreCase("SIGN") || family.contains("SIGN"))
		        				   {
		        					   if(code.equalsIgnoreCase("sign_service_level") || code.equalsIgnoreCase("consumption_start_date") || code.equalsIgnoreCase("consumption_cycle_duration"))
			        				   {
			        					   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("true")))
						        		   {
						        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
						        		   }
						        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
						        		   }
			        				   }
			        				   else if(code.equalsIgnoreCase("msft_integration_readiness"))
			        				   {
			        					   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
						        		   }
						        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
						        		   }
			        				   }
			        				   else
			        				   {
					        			   /*if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
						        		   }
						        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
						        		   {
						        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(",");
						        		   }*/
			        				   }
		        				   }
		        				   else
		        				   {
		        					   /*if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
				   	        		   {
				   	        			  sb.append("fulfillment_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("fulfillmentConfigurable").toString()+")").append(",");
				   	        		   }
				   	        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
				   	        		   {
				   	        			   sb.append("delegation_configurable : (ExceptedResults : false , ActualResults : "+jsobj.get("delegationConfigurable").toString()+")").append(",");
				   	        		   }*/
		        				   }			        			  
		        			   }
		   	        		   if(!(jsobj.get("delegationType").toString().equalsIgnoreCase("MACHINE")))
		   	        		   {
		   	        			   if(code.equalsIgnoreCase("disallow_expert_sessions"))
		   	        			   {
			   	        				 if(jsobj.get("delegationType").toString().equalsIgnoreCase("NA") || jsobj.get("delegationType").toString().equalsIgnoreCase("MACHINE"))
			   	        				 {
			   	        					 //TODO
			   	        				 }
			   	        				 else
			   	        				 {
			   	        					sb.append("delegation_type : (ExceptedResults : should have either - NA/MACHINE , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
			   	        				 }
		   	        			   }
		   	        			   else
		   	        			   {
		   	        			      sb.append("delegation_type : (ExceptedResults : MACHINE , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
		   	        			   }
		   	        		   }
		   	        		   if(sb.length() == 0)
		   	        		   {
		   	        			   status = "PASS";
		   	        		   }
		   	        		   else
		   	        		   {
		   	        			   status = code+": FAIL - "+sb.toString();
		   	        		   }	   
	        			   }
	        		   }	        		   	        		  
	        	   }
	        	   if(jsobj.get("type").toString().equalsIgnoreCase("SERVICE"))
	        	   {
	        		   if(segment.equalsIgnoreCase("ETLA"))
	        		   {
	        			   if(family.equalsIgnoreCase("SIGN") || family.contains("SIGN"))
	        			   {
	        				   if(code.equalsIgnoreCase("acom_esign"))
		        			   {
		        				   if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
				        		   }
				        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("false")))
				        		   {
				        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
				        		   }
		        			   }
		        			   else
		        			   {	        				   	        			   
				        		   /*if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("true")))
				        		   {
				        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillment_configurable").toString()+")").append(",");
				        		   }
				        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("true")))
				        		   {
				        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegation_configurable").toString()+")").append(","); 
				        		   }*/
		        			   }
	        			   }
	        			   else
	        			   {
	        				   /*if(!(jsobj.get("fulfillmentConfigurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("fulfillment_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("fulfillmentConfigurable").toString()+")").append(",");
			        		   }
			        		   if(!(jsobj.get("delegationConfigurable").toString().equalsIgnoreCase("true")))
			        		   {
			        			   sb.append("delegation_configurable : (ExceptedResults : true , ActualResults : "+jsobj.get("delegationConfigurable").toString()+")").append(","); 
			        		   }*/
	        			   }
		        		   
	        		   }
	        		   if(code.equalsIgnoreCase("dma_acp_cs"))
        			   {
        				   if(!(jsobj.get("delegationType").toString().equalsIgnoreCase("ORGANIZATION")))
    	        		   {
    	        			   sb.append("delegation_type : (ExceptedResults : ORGANIZATION , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
    	        		   }
        			   }
        			   else
        			   {
        				   if(jsobj.get("delegationType").toString().equalsIgnoreCase("NA") || jsobj.get("delegationType").toString().equalsIgnoreCase("PERSON") || jsobj.get("delegationType").toString().equalsIgnoreCase("ORGANIZATION"))
	        			   {
	        				   //TODO
	        			   }
	        			   else
	        			   {
	        				   sb.append("delegation_type : (ExceptedResults : should have either - NA/PERSON/ORGANIZATION , ActualResults : "+jsobj.get("delegationType").toString()+")").append(",");
	        			   }
        			   }
	        		   if(sb.length() == 0)
	        		   {
	        			   status = "PASS";
	        		   }
	        		   else
	        		   {
	        			   status = code+": FAIL - "+sb.toString();
	        		   }
	        	   }
        	   }
        	  return status; 
           }
			

			
			public static JSONObject getStatus(String details,String name)
			{
				JSONObject status = new JSONObject();
				try
				{					
					if(details.toString().contains("FAIL"))
					{
						status.put(name, "FAIL");
						String val = details.toString();
						val = val.replaceAll("PASS","").replaceAll("FAIL","").replaceAll("-","");
						status.put("issues",val.trim());
					}
					else
					{
						status.put(name, "PASS");
					}
				}
				catch(Exception e)
				{
					status.put(name, "FAIL");	
					status.put("issues","Throws some error message while generating results in JSON format");
				}
				return status;
			}
			
			public static String getTimeStamp()
			{
				String timestamp = "";
				try
				{					
					long yourmilliseconds = System.currentTimeMillis();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");    
					Date resultdate = new Date(yourmilliseconds);
					timestamp = sdf.format(resultdate);				
				}
				catch(Exception e)
				{
					//TODO
				}
				return timestamp;
			}
			
			public static String getGendralTimeStamp()
			{
				String timestamp = "";
				try
				{					
					long yourmilliseconds = System.currentTimeMillis();
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");    
					Date resultdate = new Date(yourmilliseconds);
					timestamp = sdf.format(resultdate);				
				}
				catch(Exception e)
				{
					//TODO
				}
				return timestamp;
			}
			

			public static String replaceNotRequireStringContent(String value)
			{
				String results = "FAIL";
				try
				{
					results = value;
					results = results.replaceAll("Warning GetStartedlinks details missing in the AOS Response", "").replaceAll("PASS", "").replaceAll("FAIL", "");
					results = results.replaceAll("Warning MCSHelpLinks details missing in the AOS Response","");
					results = results.replaceAll("Warning FIGHelpLinks details missing in the AOS Response","");
					results = results.replaceAll("Warning Channel details missing in the AOS Response","");
					results = results.replaceAll("Check Warning CountryCrossVerificationstatus column value in the result sheet","");
					results = results.replaceAll("WARNING - CO country not available in the offer, Still non Default Charge Taxes TermsandCondition available", "");
					results = results.replaceAll("WARNING - BR country not available in the offer Still non Default ALLPAGO TermsandCondition available", "");
					results = results.replaceAll("WARNING - Trial PI details missing in the AOS offer response", "");
					//results = results.replaceAll("WARNING - TRIAL PI - {payment_instrument_required : (ExceptedResults : true , ActualResults : false)}","");
					results = results.replaceAll("SPECID found in PH","");
					results = results.replaceAll("-", "");
				}
				catch(Exception e)
				{
					//TODO
				}
				return results;
			}
			
			public static String getDetailToAppend(String details,String country)
			{
				String status = "";
				try
				{
				  if(details.contains("FAIL") || details.isEmpty() || details.contains("Key Not Available") || details.equalsIgnoreCase("null"))
				  {
					  System.out.print("Value - "+country); // Added this line of code for debugging purpose
					  status = "FAIL - "+country+",";
				  }
				}
				catch(Exception e)
				{
					status = country+",";
				}		
				return status;
			}
			
			//Verify the countrylist available in the response,based on the availability of the country and add to arraylist
			public static ArrayList<String> countryCheck(ArrayList<String> countryresp,ArrayList<String> countrylist)
			{
				ArrayList<String> finalcountrylist = new ArrayList<String>();
				try
				{
					for(int i=0;i<countrylist.size();i++)
					{
						if(countryresp.contains(countrylist.get(i)))
						{
							finalcountrylist.add(countrylist.get(i));
						}
					}
				}
				catch(Exception e)
				{
					//TODO
				}
				return finalcountrylist;
			}
			
			
			
			public static String checkValueExists(StringBuilder appendedvalue,String value)
			{
				String chkstatus = "";
				try {
					String test = appendedvalue.toString();				
					if(test.contains(value))
					{
						//TODO
					}
					else
					{
						chkstatus = value;
					}
				}
				catch(Exception e)
				{
					
				}
				return chkstatus;
			}
			
			public static String compareDate(String date)
			{
				String status = "";
				try
				{
					String datevalue = date;
					datevalue = datevalue.replace("+", "-");
					LOGGER.info(" *************** DATE IN SSCOM : "+datevalue+" **********************");	
					Calendar calendar = Calendar.getInstance();
				    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss-SSS");
				    String currentdate1 = dateFormat.format(calendar.getTime());
				    Date currentdate = (Date)dateFormat.parse(currentdate1);
				    LOGGER.info(" *************** CURRENT DATE : "+currentdate+" **********************");
				    Date getdatefrmresponse = (Date)dateFormat.parse(datevalue); 
				    if(currentdate.before(getdatefrmresponse))
				    {
				    	 status = "CAOS";
				    	 LOGGER.info(" *************** Offer IS IN FURTHER DATE **********************");
				    	 LOGGER.info(" *************** PLANNING TO PREFORM VALIDATION ON CAOS RESPONSE  **********************");
				    }
				    else
				    {
				    	status = "AOS";
				    	LOGGER.info(" *************** Offer IS NOT IN FURTHER DATE **********************");
				    	LOGGER.info(" *************** PLANNING TO PREFORM VALIDATION ON AOS RESPONSE  **********************");
				    }
				}
				catch(Exception e)
				{
					status = "CAOS";
					LOGGER.info(" *************** WHILE COMPARING DATE FOUND SOME ISSUE **********************");
			    	LOGGER.info(" *************** PLANNING TO PREFORM VALIDATION ON CAOS RESPONSE  **********************");
				}
				return status;
			}
			
			public static void updateDataMap(Map<String, String> dataMap)throws SocketTimeoutException{
				dataMap.put(Constants.environment,System.getProperty("environment"));

				String country = dataMap.get(Constants.storeName); 
				System.out.println(country);
				dataMap.put(Constants.country, dataMap.get(Constants.storeName).toUpperCase());
				if (dataMap.get(Constants.storeName).equalsIgnoreCase("CA_FR")) {
					country = "CA";
					dataMap.put(Constants.country, country);
				}
				
				if (dataMap.get(Constants.storeName).equalsIgnoreCase("HK1")) 
				{
					country = "HK1";
					dataMap.put(Constants.country, country);
				}
				if(dataMap.get(Constants.storeName).equalsIgnoreCase("HK2"))
				{
					country = "HK2";
					dataMap.put(Constants.country, country);
				}
				if(dataMap.get(Constants.storeName).equalsIgnoreCase("RU"))
				{
					country = "RU";
					dataMap.put(Constants.country, country);
				}
				if (country.equals("EU")) {
					
			    	if((dataMap.get(Constants.COUNTRY_LS).equalsIgnoreCase("BG")))
			    	   {
			    	    country = "BG";
			    	    dataMap.put(Constants.country, country);
			    	   }
			    	else if ((dataMap.get(Constants.COUNTRY_LS).equalsIgnoreCase("CY")))
			    	   {
			    	    country = "CY";
			    	    dataMap.put(Constants.country, country);
			    	   }
			    	else if ((dataMap.get(Constants.COUNTRY_LS).equalsIgnoreCase("EE")))
			    	   {
			    	    country = "EE";
			    	    dataMap.put(Constants.country, country);
			    	   }
			    	else if ((dataMap.get(Constants.COUNTRY_LS).equalsIgnoreCase("HU")))
			    	   {
			    	    country = "HU";
			    	    dataMap.put(Constants.country, country);
			    	   }
			    	else if ((dataMap.get(Constants.COUNTRY_LS).equalsIgnoreCase("LV")))
			    	   {
			    	    country = "LV";
			    	    dataMap.put(Constants.country, country);
			    	   }
			    	else if ((dataMap.get(Constants.COUNTRY_LS).equalsIgnoreCase("LT")))
			    	   {
			    	    country = "LT";
			    	    dataMap.put(Constants.country, country);
			    	   }
			    	else if ((dataMap.get(Constants.COUNTRY_LS).equalsIgnoreCase("MT")))
			    	   {
			    	    country = "MT";
			    	    dataMap.put(Constants.country, country);
			    	   }
			    	else if ((dataMap.get(Constants.COUNTRY_LS).equalsIgnoreCase("RO")))
			    	   {
			    	    country = "RO";
			    	    dataMap.put(Constants.country, country);
			    	   }
			    	else if ((dataMap.get(Constants.COUNTRY_LS).equalsIgnoreCase("SK")))
			    	   {
			    	    country = "SK";
			    	    dataMap.put(Constants.country, country);
			    	   }
			    	else if ((dataMap.get(Constants.COUNTRY_LS).equalsIgnoreCase("SI")))
			    	   {
			    	    country = "SI";
			    	    dataMap.put(Constants.country, country);
			    	   }
			    	 else
			    	   {
			    	    country = "GR";
			    	    dataMap.put(Constants.country, country);
			    	   }
					
				}		
				if (country.equals("AP")) {
					country = "ZA";
					dataMap.put(Constants.country, country);
				}
				if (country.equals("UK")) {
					country = "GB";
					dataMap.put(Constants.country, country);
				}
				
				String acceptLanguage = null;
		        if(dataMap.get(Constants.country).equalsIgnoreCase("NO"))
		        {
		            acceptLanguage = "NO_NO";
		        }
		        else if(dataMap.get(Constants.country).equalsIgnoreCase("DK"))
		        {
		        	acceptLanguage = "DA_DK";
		        }
		        else if(dataMap.get(Constants.country).equalsIgnoreCase("NL"))
		        {
		        	acceptLanguage = "NL_NL";
		        }
		        else if(dataMap.get(Constants.country).equalsIgnoreCase("HK1"))
		        {
		        	acceptLanguage = "HK_EN";
		        }
		        else if(dataMap.get(Constants.country).equalsIgnoreCase("HK2"))
		        {
		        	acceptLanguage = "HK_ZH";
		        }
		        else if(dataMap.get(Constants.country).equalsIgnoreCase("RU"))
		        {
		        	acceptLanguage = "RU";
		        }
		        else if(country.equalsIgnoreCase("PA") || country.equalsIgnoreCase("PY") || country.equalsIgnoreCase("UY") || country.equalsIgnoreCase("SV") || country.equalsIgnoreCase("BO") || country.equalsIgnoreCase("DO") || country.equalsIgnoreCase("AR") || country.equalsIgnoreCase("CO") || country.equalsIgnoreCase("CL") || country.equalsIgnoreCase("PE") || country.equalsIgnoreCase("VE") || country.equalsIgnoreCase("CR") || country.equalsIgnoreCase("EC") || country.equalsIgnoreCase("GT"))
		        {
		        	acceptLanguage = "ES";
		        }
		        else if(country.equalsIgnoreCase("HR") || country.equalsIgnoreCase("KE") || country.equalsIgnoreCase("LK") || country.equalsIgnoreCase("MO") || country.equalsIgnoreCase("MU") || country.equalsIgnoreCase("NG") || country.equalsIgnoreCase("TT") || country.equalsIgnoreCase("VN"))
		        {
		        	acceptLanguage = "EN";
		        }
		        else if(country.equalsIgnoreCase("KR"))
		        {
		        	acceptLanguage = "KO_KR";
		        }
		        else
		        {
		               acceptLanguage = DatabaseUtil.getAcceptLanguage(dataMap
		                            .get(Constants.country));   
		        }
		        
		        if(dataMap.get(Constants.storeName).equalsIgnoreCase("CA_FR")) 
				{
		        	acceptLanguage = "FR_CA";
				}
		        if(dataMap.get(Constants.storeName).equalsIgnoreCase("EU") || dataMap.get(Constants.storeName).equalsIgnoreCase("GR") || dataMap.get(Constants.storeName).equalsIgnoreCase("CY") || dataMap.get(Constants.storeName).equalsIgnoreCase("MT") || dataMap.get(Constants.storeName).equalsIgnoreCase("SI") || dataMap.get(Constants.storeName).equalsIgnoreCase("SK") || dataMap.get(Constants.storeName).equalsIgnoreCase("RO") || dataMap.get(Constants.storeName).equalsIgnoreCase("LV") || dataMap.get(Constants.storeName).equalsIgnoreCase("LT") || dataMap.get(Constants.storeName).equalsIgnoreCase("HU") || dataMap.get(Constants.storeName).equalsIgnoreCase("EE") || dataMap.get(Constants.storeName).equalsIgnoreCase("BG"))
				{
		        	acceptLanguage = "EN_XEU";			
				}
					
				country = country.replaceAll("\\d*$", "");
				dataMap.put(Constants.country, country);
				dataMap.put(Constants.storeName, dataMap.get(Constants.storeName).replaceAll("\\d*$", ""));
				dataMap.put(Constants.ACCEPT_LANGUAGE, acceptLanguage);
			}
			
		
           
}

