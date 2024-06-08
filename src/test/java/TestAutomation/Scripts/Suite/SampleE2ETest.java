/*
package TestAutomation.suite;



/*
public class Enterprise_AdminConsoleValidations_CommerceTest  {
	
	

	private static Logger LOGGER = Logger.getLogger(Enterprise_AdminConsoleValidations_CommerceTest.class);	
	String className = this.getClass().getName();
	String environment = System.getProperty("environment");
	String OrgID = System.getProperty("OrgID");
	String AdminEmail = System.getProperty("AdminEmail");
	String EmailId = System.getProperty("ldapid");  //mercauto
	String OfferID=System.getProperty("OfferID");
	String password=System.getProperty("password");
	String type = "";
	String countryCode=System.getProperty("countryCode");
	ArrayList<Map<String, String>> validationresults = new ArrayList<Map<String, String>>();	

	
	String aaui_access_token=ReadAccessToken.getEnterpriseStageToken();
	
	
	
	@Test(groups= { "ValidateAdminConsole" })
	public void Enterprise_ETLA_CommerceTestMethod() throws Exception 
	{
		WebDriver driver = null;
		String requestor_id=CommonSuiteUtility.getPropertiesValue_Enterprise();
		//String requestor_id="99062E1B5CD449C40A494024@c62f24cc5b5b7e0e0a494004";
		Map<String,String>dataMap1=new HashMap<String,String>();
		VIPeVIPEnterpriseSuiteUtilities vipeviputilities=new VIPeVIPEnterpriseSuiteUtilities();	
		EnterpriseEVIPETLAUtilities EVIPETLAUtilities =new EnterpriseEVIPETLAUtilities();
		ArrayList<Map<String, String>> updatedprovisiondatakeyname = new ArrayList<Map<String, String>>();	
		Map<String,String> gendraldetails = new HashMap<String,String>();
		ArrayList<String> SpecIDdetails = new ArrayList<String>();
		ArrayList<Map<String,String>> OfferIDdetails = new ArrayList<Map<String,String>>();
		JEMRengaUtilities jemrengautilities=new JEMRengaUtilities();
		
	
		
		 dataMap1.put("aaui_access_token",aaui_access_token);
		 dataMap1.put("adminconsole_access_token",aaui_access_token);
		 dataMap1.put("aaui_api_key","onb_automation_enterprise");
		 dataMap1.put("requestor_id",requestor_id);
		 dataMap1.put("Email Id", EmailId); 	
		 
		 if(password==null || password.isEmpty())
		 {
			 password="P@ssw0rd1";
		 }
		 dataMap1.put("password", password); 
		 if(OfferID==null || OfferID.isEmpty())
		 {
			 dataMap1.put("TestResult", "Fail:::Offer ID Not Entered ");
    		 throw new Exception("Fail:::Offer ID Not Entered ");
		 }	 
		 else
		 {
			
		 }		 
		
		
		 dataMap1.put("AAUI_ORGANIZATION_ID",OrgID);		
		 dataMap1.put("Reseller_Customer_ID", dataMap1.get("AAUI_ORGANIZATION_ID"));
		 dataMap1.put("Admin_Email_Id", AdminEmail);
		 dataMap1.put("countryCode", countryCode);
		 
		
		 
		 				       			
		try
		{    
				 CommonSuiteUtility.updatePropertiesValue("ETLA_Admin","[FTA] "+environment+" - AdminConsole Validation Results");
				 gendraldetails.put(Constants.className,className);
			     gendraldetails.put(Constants.environment,environment);
				 Map<String,String>offertemp=new HashMap<String,String>();
				 offertemp.put("OfferID", OfferID);
				 OfferIDdetails.add(offertemp);
	 		    
		     for(int k=0;k<OfferIDdetails.size();k++)
			 {
		    	 Map<String,String>dataMap=new HashMap<String,String>();
		    	 try
		    	 {
		    	 //String countryCode="";
		    	 Map<String,String>map=new HashMap<String,String>();
		    	 dataMap.putAll(dataMap1);		    	 
		    	// Map<String,String>dataMap=new HashMap<String,String>();
		    	 dataMap.put("TestResult", "PASS");
		    	 map=OfferIDdetails.get(k);		 
		    	 map.put("countryvalue", countryCode);
		    	 map.putAll(EVIPETLAUtilities.getOfferData(map,"ETLA"));	
		    	 countryCode=EVIPETLAUtilities.getcountryForOrderPlacement(map.get("CountryList"));		    	
		    	 String MarketSegment=EVIPETLAUtilities.getmarketSegmentsForOrderPlacement(map.get("MarketSegment"));
		    	 String Offer_ID=map.get("OfferID");
		    	 //dataMap.put("projectID", projectID);
		    	 dataMap.put("productCode", map.get("product_code"));
		    	 dataMap.put("OfferID", Offer_ID);	
		    	 dataMap.put("countryCode", countryCode);	
		    	 dataMap.put("MarketSegment", MarketSegment);	
		    	 dataMap.put("offer_type",map.get("offer_type"));
		    	 dataMap.put("price_point",map.get("price_point"));
		    	 dataMap.put("commitment",map.get("commitment"));
		    	 dataMap.put("term",map.get("term"));
		    	 dataMap.put("product_arrangement_code",map.get("product_arrangement_code"));
		    	 String customer_segment=map.get("customer_segment");	
		    	 String sales_channel=map.get("sales_channel");	
		    	 String buying_program=map.get("buying_program");
		    	 dataMap.put("language",map.get("language"));
		    	 String orgName=EnterpriseEVIPETLAUtilities.getOrgDetails(OrgID,dataMap);
				 
				 if(buying_program.equalsIgnoreCase("ETLA") && sales_channel.equalsIgnoreCase("DIRECT") && customer_segment.equalsIgnoreCase("ENTERPRISE") )
		    	 { 
					 type = "ETLA";
		    		 dataMap.put("Type", "ETLA");
		    		 dataMap.put("customer_segment",customer_segment);
		    		 dataMap.put("sales_channel",sales_channel);
		    	 }
				 else if(buying_program.equalsIgnoreCase("VIP") && sales_channel.equalsIgnoreCase("INDIRECT") && customer_segment.equalsIgnoreCase("ENTERPRISE") )
		    	 { 
					 type = "EVIP";
		    		 dataMap.put("Type", "EVIP");
		    		 dataMap.put("customer_segment",customer_segment);
		    		 dataMap.put("sales_channel",sales_channel);	
		    		 if(orgName.equalsIgnoreCase("null"))
		    		 {
		    			 dataMap.put("TestResult", "Fail:::Org Details not found");
			    		 throw new Exception("Fail:::Org Details not found");
		    		 }
		    		 else
		    		 {
		    			 dataMap.put("OrganizationName",orgName);
		    		 }					 
		    	 }
				 else
				 {
					 LOGGER.info("**************** FAIL::Cannot perform admin console validation,Buying Program not matching either ETLA/EVIP ****************");
		    		 throw new Exception("FAIL::Cannot perform admin console validation,Buying Program not matching either ETLA/EVIP");
				 }
				 
				
				//Getting the Test Data Country Details from the sheet 
				 LOGGER.info("Getting the Test Data Country Details from the sheet");
				EVIPETLAUtilities.getVIPeVIPEnterpriseCountryandRegionDetailsTestData(dataMap);				
				
				 		
				 												 
				//Navigate to the Admin Console				  
				driver = PageObjects.getNewDriver("CHROME","ANY","Windows 7");
			    driver.manage().window().maximize();
				//Navigating to the One Console by logging into console with Admin user ....Starts Here	
				LOGGER.info("Navigating to the One Console by logging into console with Admin user ....Starts Here")	;
				AdminConsoleUIUtility.OneConsoleNavigation_Enterprise(dataMap,driver);
				  				  				  
				//Validating the new Overview Page ....Starts Here	
				LOGGER.info("Validating the new Overview Page ....Starts Here ....Starts Here")	;
				AdminConsoleUIUtility.validateNewOverviewPage(dataMap,driver);		
				
				//vipeviputilities.entitlementValidations_AssignedUser_Wrapper(dataMap);	
				 
				 if(map.get("LabelName").contains("FRL") || map.get("LabelName").contains("NUELL") || map.get("CodeName").contains("frl") || map.get("CodeName").contains("FRL"))
				 {
					 dataMap.put("ENTITLEMENT_VALIDATIONS_ASSIGNED_USERS","Not Performing the Entitlement validation for FRL,NUELL offers"); 
				 }	
				 else
				 {			
		 	         //Creating the domains and Accepting the domains- UI
		 	         LOGGER.info("Creating the domains and Accepting the domains - UI"); //through UI
		 	         EnterpriseEVIPETLAUtilities.addNewDomain(driver,dataMap);
					 				  				  
					 //One Console All Products & Services Page Validations and Assigning T2E User Validations...Starts Here
					 LOGGER.info("One Console All Products & Services Page Validations and Assigning T2E User Validations...Starts Here")	;
					 AdminConsoleUIUtility.OneConsoleOverviewvalidations_eVIPETLA(dataMap,driver);
					   
					  
					 //One Console Product Page& Product Details Page including Add T2E User to the Default Profile ...Starts Here 
					 LOGGER.info("One Console Product Page& Product Details Page including Add User T2E to the Default Profile ...Starts Here");
					 AdminConsoleUIUtility.OneConsoleProductPagevalidations_eVIP_ETLA(dataMap, driver);		
					  				  
					 //Remove T2E User from Product Page from Default Profile ...Starts Here 
		             LOGGER.info("Remove T2E User from Product Page from Default Profile ...Starts Here");
		             AdminConsoleUIUtility.OneConsoleRemoveUservalidations_eVIP_ETLA(dataMap, driver);	              
		             	           	 	          
		 	         //Claiming the T2E Individual Users
		 	         LOGGER.info("Claiming the T2E Individual Users");
		 	         EVIPETLAUtilities.activatingAdminConsoleT2EUsers_wrapper(dataMap);	
		 	           
		 	         //Creating the domains and Accepting the domains
		 	         LOGGER.info("Creating the domains and Accepting the domains");
		 	         //EVIPETLAUtilities.AdminConsole_createDomainsandAcceptDomains(dataMap);  //through apis
	                                                                                                                   	 	         	 	           
		 	         //Check Added domains and Activated in the org--UI
		 	         LOGGER.info("Check Added domains and Activated in the org--UI"); //through UI
		 	         EnterpriseEVIPETLAUtilities.AgentAdminConsole_getaddeddoaminStatus_wrapper(dataMap);
		 	           
		 	           
		 	         //Creating the new Profile and Add Type 2 and Type 3 users and Remove Type 2 and Type 3 Users
		 	         LOGGER.info("Creating the new Profile and Add Type 2 and Type 3 users and Remove Type 2 and Type 3 Users");
		 	         EVIPETLAUtilities.createNewProfileandAddUsersandRemoveUsersforType2andType3(driver, dataMap); 
		 	           
		 	         //Updating the GUID Details for Type2,Type3 Add Users-Remove Users
		 	         TeamEnterpriseOtherUtilities.updateUserGuid4EntitlementValidations_wrapper(dataMap);
			 	      		 	     
		             //Users page validations - Assigned users are provisioned with the Products and not provisioned for Removed user ....Starts Here
		 	         LOGGER.info("Users page validations - Assigned users are provisioned with the Products and not provisioned for Removed user ....Starts Here");
		 	         EnterpriseEVIPETLAUtilities.getUserDetails_toCheckProductAccess_wrapper(dataMap);
		 	                     	 	           
		 	         //Add User Entitlement Validations...Starts Here
		    		 LOGGER.info("Add User Entitlement  Validations...Starts Here")	;	    		   	    	         
		    		 jemrengautilities.entitlementValidations_AssignedUser_Wrapper(dataMap);		           		 
			         //Add User Entitlement  Validations...Ends Here
		             LOGGER.info("Add User Entitlement Validations...Ends Here");	
		               	    
		             //Remove User Entitlement Validations...Starts Here
		             LOGGER.info("Remove User Entitlement  Validations...Starts Here")	;	                 	        
		             jemrengautilities.entitlementValidations_RemovedUser_Wrapper(dataMap);
			         //Remove User Entitlement  Validations...Ends Here
		             LOGGER.info("Remove User Entitlement Validations...Ends Here");
		             if(type.equalsIgnoreCase("EVIP"))
		             {	            		 
			             //Verify the Product detail in the product page.
			             EnterpriseEVIPETLAUtilities.getBuyMoreProductsList_AdminConsole(dataMap);
		             }
				 }
	 	         				  				 				  				  
				 validationresults.add(dataMap);
		    	 }
		    	 catch(Exception t33)
		    	 {
		    		 validationresults.add(dataMap);
		    	 }
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
			LOGGER.info("****************** Adminconsole Validations results- Sending thru email ***************************");
			EnterpriseEVIPETLAUtilities.adminConsoleValidationWriteLog(validationresults,environment,type,"ETLA_EVIP_AdminConsole");
			
			LOGGER.info("****************** Adminconsole Validations results - Uploading to the excel sheet ***************************");
			E2EExcelWriteUtility.ETLAAdminConsoleValidationResults(validationresults,gendraldetails,type);			

			LOGGER.info("***************** Adminconsole Validations is Completed ***************");
		}
		
	}
	

  

// Map<String,String>TYPE2=EVIPETLAUtilities.createType2user(dataMap,"1"); //"1" is for TYPE2 user no
// Map<String,String>TYPE3=EVIPETLAUtilities.createType3user(dataMap,"1"); //"1" is for TYPE3 user no
 //Map<String,String>T2E=EVIPETLAUtilities.createType2Euser("6FE02CBA5F1AB0E70A49400D@AdobeOrg", dataMap,"1"); //"1" is for T2E user no
 //LOGGER.info(T2E);

/* Existing Orgs
dataMap.put("AAUI_ORGANIZATION_ID","47B17F3F5F44D8370A49400C@AdobeOrg");
dataMap.put("AAUI_CONTRACT_ID","3BE1C97AA925D7FD5C9B"); */




