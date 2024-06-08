package TestAutomation.Utilities.utility;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import java.io.InputStream;




/**
 * Simple util for accessing the base environment urls
 * *
 */
public class DatabaseUtil {
	
	private static Logger LOGGER = Logger.getLogger(DatabaseUtil.class);

	public enum TYPE {
		hoolihan,load_balancer, url, ccmURL, drURL, cxf_base_external, cxf_base_internal, url_health_check, 
		catalogservice_base, dbConnectionUrl, dbPaymentUsername, 
		dbOlsBackUsername, dbOlsFrontUsername, dbPassword,dbSubscriptionUsername,dbSubscriptionSepMsg,
		dbFrontUserPassword,dbBackUserPassword,dbSubscriptionPassword,dbSepPassword,dbSerialUsername,dbSerialPassword,dbSAPStagingUsername,dbSAPStagingPassword;
	}
	
//	private static final String CXF_BASE_INTERNAL = "cxf_base_internal";
//	private static final String CXF_BASE_EXTERNAL = "cxf_base_external";
	
	private static final String INTOREXT_INTERNAL = "internal";
//	private static final String INTOREXT_EXTERNAL = "external";
	private static final String INTOREXT_EXT_HEALTHCHECK = "external_health_check";
	
	static Properties urlProp;
	
	private DatabaseUtil(){
		// hide the constructor, static methods only
	}

	static{
		urlProp = readProperty("environmentSettings.properties");
	}
	

	/**
	 * @return the base url for ATG using the environment specified at build time
	 * @throws DataSourceException
	 */
	public static String getAtgBase() throws DataSourceException{
//		return DataSourceUtils.getDataTable("environmentSettings.xls", "URLS", "urls").get("atg_base").get("url") ;
		return getBaseUrl(DatabaseUtil.TYPE.url.toString());
	}
	
	
	/**
	 * Method to get the base url of the environment
	 * 
	 * @param type - Pass a string from UrlUtil.TYPE; 
	 * 				 Pass a string of the environment;
	 * 				<br> Example: UrlUtil.TYPE.cxf_base_external.toString()
	 * 				<br> Example: UrlUtil.TYPE.url.toString()
	 * @return 
	 */
	public static String getBaseUrl(String type, String env) throws DataSourceException{
		return urlProp.getProperty(env+ "." + type);
	}
	
	/**
	
	/**
	 * @return the base url for Day using the environment specified at build time
	 * @throws DataSourceException
	 */
	public static String getDayBase() throws DataSourceException{
//		return DataSourceUtils.getDataTable("environmentSettings.xls", "URLS", "urls").get("day_base").get("url") ;
		return getBaseUrl(DatabaseUtil.TYPE.url.toString());
	}
	

	/**
	 * @return the base url for CCM using the environment specified at build time
	 * @throws DataSourceException
	 */
	public static String getCCMBase() throws DataSourceException{
		return getBaseUrl(DatabaseUtil.TYPE.ccmURL.toString());
	}
	
	/**
	 * @return the base url for DR using the environment specified at build time
	 * @throws DataSourceException
	 */
	public static String getDRBase() throws DataSourceException{
		return getBaseUrl(DatabaseUtil.TYPE.drURL.toString());
	}
	
	/**
	 * Method to get the base url of the environment
	 * 
	 * @param type - Pass a string from UrlUtil.TYPE; 
	 * 				<br> Example: UrlUtil.TYPE.cxf_base_external.toString()
	 * 				<br> Example: UrlUtil.TYPE.url.toString()
	 * @return 
	 */
	public static String getBaseUrl(String type) throws DataSourceException{
		return urlProp.getProperty(System.getProperty("environment")+ "." + type);
		
	}
	
	/**
	 * @return the base url for Day using the environment specified at build time
	 * @throws DataSourceException
	 */
	public static String getDylanBase() throws DataSourceException {
//		return DataSourceUtils.getDataTable("environmentSettings.xls", "URLS", "urls").get("dylan_base").get("url") ;
		return getBaseUrl(DatabaseUtil.TYPE.url.toString());
	}

	/**
	 * @return the Database connection url using the environment specified at build time
	 * @throws DataSourceException
	 */
	public static String getDBConnection(String PropName) throws DataSourceException{
//		return DataSourceUtils.getDataTable("environmentSettings.xls", "URLS", "dbconnection").get(PropName).get("value");
		//TODO: remove if, else-if conditions
		if(PropName.equals(DatabaseUtil.TYPE.dbConnectionUrl.toString())){
			return getBaseUrl(DatabaseUtil.TYPE.dbConnectionUrl.toString());
		} else if(PropName.equals(DatabaseUtil.TYPE.dbOlsBackUsername.toString())){
			return getBaseUrl(DatabaseUtil.TYPE.dbOlsBackUsername.toString());
		} else if(PropName.equals(DatabaseUtil.TYPE.dbOlsFrontUsername.toString())){
			return getBaseUrl(DatabaseUtil.TYPE.dbOlsFrontUsername.toString());
		} else if(PropName.equals(DatabaseUtil.TYPE.dbFrontUserPassword.toString())){                                                           //
            return getBaseUrl(DatabaseUtil.TYPE.dbFrontUserPassword.toString());                                               //                            To check for Front or Back user DB passwords
		} else if(PropName.equals(DatabaseUtil.TYPE.dbBackUserPassword.toString())){                                                                           //
            return getBaseUrl(DatabaseUtil.TYPE.dbBackUserPassword.toString());
		} else if(PropName.equals(DatabaseUtil.TYPE.dbSubscriptionPassword.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSubscriptionPassword.toString());
		} else if(PropName.equals(DatabaseUtil.TYPE.dbSepPassword.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSepPassword.toString());
		} else if(PropName.equals(DatabaseUtil.TYPE.dbPassword.toString())){
			return getBaseUrl(DatabaseUtil.TYPE.dbPassword.toString());
		} else if(PropName.equals(DatabaseUtil.TYPE.dbSubscriptionUsername.toString())){
			return getBaseUrl(DatabaseUtil.TYPE.dbSubscriptionUsername.toString());
		}
		else if(PropName.equals(DatabaseUtil.TYPE.dbSerialUsername.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSerialUsername.toString());
		}
		else if(PropName.equals(DatabaseUtil.TYPE.dbSerialPassword.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSerialPassword.toString());
		}
		else if(PropName.equals(DatabaseUtil.TYPE.dbSAPStagingUsername.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSAPStagingUsername.toString());
        }
        else if(PropName.equals(DatabaseUtil.TYPE.dbSAPStagingPassword.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSAPStagingPassword.toString());
         }

		
		return null;
	} 
    
	
	public static String getDBConnection(String PropName, String environment) throws DataSourceException{
//		return DataSourceUtils.getDataTable("environmentSettings.xls", "URLS", "dbconnection").get(PropName).get("value");
		//TODO: remove if, else-if conditions
		if(PropName.equals(DatabaseUtil.TYPE.dbConnectionUrl.toString())){
			return getBaseUrl(DatabaseUtil.TYPE.dbConnectionUrl.toString(),environment);
		} else if(PropName.equals(DatabaseUtil.TYPE.dbOlsBackUsername.toString())){
			return getBaseUrl(DatabaseUtil.TYPE.dbOlsBackUsername.toString(),environment);
		} else if(PropName.equals(DatabaseUtil.TYPE.dbOlsFrontUsername.toString())){
			return getBaseUrl(DatabaseUtil.TYPE.dbOlsFrontUsername.toString(),environment);
		} else if(PropName.equals(DatabaseUtil.TYPE.dbFrontUserPassword.toString())){                                                           //
            return getBaseUrl(DatabaseUtil.TYPE.dbFrontUserPassword.toString(),environment);                                               //                            To check for Front or Back user DB passwords
		} else if(PropName.equals(DatabaseUtil.TYPE.dbBackUserPassword.toString())){                                                                           //
            return getBaseUrl(DatabaseUtil.TYPE.dbBackUserPassword.toString(),environment);
		} else if(PropName.equals(DatabaseUtil.TYPE.dbSubscriptionPassword.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSubscriptionPassword.toString(),environment);
		} else if(PropName.equals(DatabaseUtil.TYPE.dbSepPassword.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSepPassword.toString(),environment);
		} else if(PropName.equals(DatabaseUtil.TYPE.dbPassword.toString())){
			return getBaseUrl(DatabaseUtil.TYPE.dbPassword.toString(),environment);
		}else if(PropName.equals(DatabaseUtil.TYPE.dbSubscriptionUsername.toString())){
			return getBaseUrl(DatabaseUtil.TYPE.dbSubscriptionUsername.toString(),environment);
		}else if(PropName.equals(DatabaseUtil.TYPE.dbSubscriptionSepMsg.toString())){
			return getBaseUrl(DatabaseUtil.TYPE.dbSubscriptionSepMsg.toString(),environment);
		}
		else if(PropName.equals(DatabaseUtil.TYPE.dbSerialUsername.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSerialUsername.toString(),environment);
		}
		else if(PropName.equals(DatabaseUtil.TYPE.dbSerialPassword.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSerialPassword.toString(),environment);
		}
		else if(PropName.equals(DatabaseUtil.TYPE.dbSAPStagingUsername.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSAPStagingUsername.toString(),environment);
        }
        else if(PropName.equals(DatabaseUtil.TYPE.dbSAPStagingPassword.toString())){
            return getBaseUrl(DatabaseUtil.TYPE.dbSAPStagingPassword.toString(),environment);
        }


		return null;
	} 
	private static String getInternalOrExternal(){
		String intorext = System.getProperty("intorext");
		if (null==intorext){
			intorext = "";
		}
		return intorext;
	}
	
	public static String getCatalogServiceBase() throws DataSourceException{
//		return DataSourceUtils.getDataTable("environmentSettings.xls", "URLS", "urls").get("catalogservice_base").get("url") ;
		return getBaseUrl(DatabaseUtil.TYPE.catalogservice_base.toString());
	}
	
	public static Properties readProperty(String fileName){
		Properties pro = new Properties();
		  try{

			  
			  InputStream propFile = getExcelFile(fileName);
			  
			  pro.load(propFile);
			  
		  } catch (Exception e) {
			// TODO: handle exception
		}	  
		return pro;
	}
	
	public static Properties ACCEPT_LANGUAGE_PROP = readProperty("acceptLanguage.properties");
	public static String completeUrl = null;
	
	public static String getAcceptLanguage(String locale){
		LOGGER.info("==>getAcceptLanguage() :: "+locale);
		LOGGER.info("<==getAcceptLanguage() :: "+ACCEPT_LANGUAGE_PROP.getProperty(locale));
		return ACCEPT_LANGUAGE_PROP.getProperty(locale);
	}
	
	public static LinkedHashMap<String,LinkedHashMap<String, String>> getDataTable(String xlFileName, String sheetName, String tableName) throws DataSourceException
	{
		LOGGER.debug("==>getDataTable() :: xlFileName: "+xlFileName+"; sheetName: "+sheetName+"; tableName: "+tableName);

		LinkedHashMap<String,LinkedHashMap<String, String>> data = new LinkedHashMap<String, LinkedHashMap<String,String>>();
		try{
			InputStream xlFile = getExcelFile(xlFileName);	

			Workbook workbook = Workbook.getWorkbook(xlFile);
			Sheet sheet = workbook.getSheet(sheetName);
			int startRow,startCol, endRow, endCol;
			Cell tableStart=sheet.findCell(tableName);
			startRow=tableStart.getRow();
			startCol=tableStart.getColumn();

			Cell tableEnd= sheet.findCell(tableName, startCol+1,startRow+1, 100, 64000,  false);                               

			endRow=tableEnd.getRow();
			endCol=tableEnd.getColumn();

			// identify the column headers
			List<String> columns = new ArrayList<String>();
			for (int field=startCol+2;field<endCol;field++){
				columns.add(sheet.getCell(field,startRow).getContents());
			}

			// for each row, create a map of columnn headers and the corresponding value
			for (int i=startRow+1;i<endRow;i++){
				LinkedHashMap<String, String> entry = new LinkedHashMap<String, String>();
				for (int j=startCol+2;j<endCol;j++){
					entry.put(columns.get(j-(startCol+2)),sheet.getCell(j,i).getContents());
				}
				data.put(sheet.getCell(startCol+1,i).getContents(), entry);
			}
		}
		catch (Exception e)    {
			throw new DataSourceException("Unable to load datasource",e);
		}

		LOGGER.debug("<==getDataTable() :: "+data);
		return data;
	}
	
	public static InputStream getExcelFile(String xlFileName){
		LOGGER.debug("==>getExcelFile()");
		// Load the workbook 
		InputStream xlFile = ClassLoader.getSystemResourceAsStream(getBaseFilePath()+xlFileName);
		//		LOGGER.debug(" getBaseFilePath() : "+getBaseFilePath());

		// If not found, try again without base path
		if (null==xlFile){
			xlFile = ClassLoader.getSystemResourceAsStream(xlFileName);
		}
		LOGGER.debug("<==getExcelFile()");
		return xlFile;
	}
	

	public static String getBaseFilePath(){
		LOGGER.debug("==>getBaseFilePath()");
		String environment = System.getProperty("environment");
		if (null==environment){
			environment = "";
		} else {
			if (environment.equals("production")){}

			environment = environment + "/";
		}

		//while running on Linux (Web Services tests), we won't have the following property (mostly) - xlsx would be checked-in
		if(null==System.getProperty("test_data_location")){
			//Just passing relative path; E.g., "stage/" OR just "/"
			return environment;
		}
		else{
			//we shall have this mostly for selenium tests & we shall read the settings.xml from user's profile - .m2/settings.xml
			//Pass along with local setting; E.g., "C:/CayenneTestData/stage/" OR just "C:/CayenneTestData/"
			return System.getProperty("test_data_location")+environment;
		}
	}	
	
	/**
	 * @param data - the data to be filtered
	 * @param column - the field to filter on 
	 * @param regex - the pattern to match against
	 * @return
	 */
	public static LinkedHashMap<String,LinkedHashMap<String, String>> filterByColumn(Map<String,LinkedHashMap<String, String>> data, String column, String regex){
		LOGGER.debug("==>filterByColumn()");
		LinkedHashMap<String,LinkedHashMap<String, String>> result = new LinkedHashMap<String, LinkedHashMap<String,String>>();
		for (String row: data.keySet()){
			if (data.get(row).get(column).matches(regex)){
				result.put(row, data.get(row));
			}
		}
		LOGGER.debug("<==filterByColumn()");
		return result;
	}

	/**
	 * @param data - the data to be filtered
	 * @param regex - the pattern to match against
	 * @return
	 */
	public static LinkedHashMap<String,LinkedHashMap<String, String>> filterEntries(Map<String,LinkedHashMap<String, String>> data, String regex){
		LOGGER.debug("==>filterEntries()");
		LinkedHashMap<String,LinkedHashMap<String, String>> result = new LinkedHashMap<String, LinkedHashMap<String,String>>();
		for (String row: data.keySet()){
			if (row.matches(regex)){
				result.put(row, data.get(row));
			}
		}
		LOGGER.debug("<==filterEntries()");
		return result;
	}

	/**
	 * @param data - the data to be filtered
	 * @param columns - the columns to include
	 * @return
	 */
	public static LinkedHashMap<String,LinkedHashMap<String, String>> getColumns(Map<String,LinkedHashMap<String, String>> data, List<String> columns){
		LOGGER.debug("==>getColumns()");
		LinkedHashMap<String,LinkedHashMap<String, String>> result = new LinkedHashMap<String, LinkedHashMap<String,String>>();
		for (String row: data.keySet()){
			LinkedHashMap<String,String> newRow = new LinkedHashMap<String, String>();
			for (String column : columns){
				newRow.put(column, data.get(row).get(column));
			}
			result.put(row, newRow);
		}
		LOGGER.debug("<==getColumns()");
		return result;
	}

	/**
	 * Convert the input into a table array for use as a {@link DataProvider}
	 * @param data the data to be converted
	 * @return
	 */
	public static String[][] getTableArray(Map<String,LinkedHashMap<String, String>> data){
		LOGGER.debug("==>getTableArray()");
		String[][] tabArray=null;
		int ci,cj;
		tabArray=new String[data.size()][data.values().iterator().next().size()];
		ci=0;

		for (String key : data.keySet()){
			cj=0;
			for (String value : data.get(key).values()){
				tabArray[ci][cj]= value;
				cj++;
			}
			ci++;
		}
		LOGGER.debug("<==getTableArray()");
		return(tabArray);
	}

	public static List<String> getColumnValues(String column, LinkedHashMap<String,LinkedHashMap<String, String>> data){
		LOGGER.debug("==>getColumnValues()");
		List<String> uniqueColumnEntries = new ArrayList<String>();
		for (LinkedHashMap<String, String> entry : data.values()){
			String columnValue = entry.get(column);
			if (!uniqueColumnEntries.contains(columnValue)){
				uniqueColumnEntries.add(columnValue);
			}
		}
		LOGGER.debug("<==getColumnValues()");
		return uniqueColumnEntries;
	}

	/**
	 * Gets the first entry that matches the pattern in the column
	 * @param data
	 * @param column
	 * @param regex
	 * @return
	 */
	public static LinkedHashMap<String, String> getEntryByColumn(LinkedHashMap<String,LinkedHashMap<String, String>> data, String column, String regex){
		LinkedHashMap<String, LinkedHashMap<String, String>> filtered = filterByColumn(data, column, regex);
		return filtered.get(filtered.keySet().iterator().next());
	}


	private static String getBaseFilePathMinusOne(){
		LOGGER.info("==>getBaseFilePathMinusOne()");
		String environment = System.getProperty("environment");
		if (null==environment){
			environment = "";
		} else {
			environment = environment.substring(0,environment.length()-2) + System.getProperty("file.separator");
		}
		LOGGER.info("<==getBaseFilePathMinusOne()");
		return environment;
	}

	/**
	 * @param xlFilePath - The name of the source xls spreadsheet
	 * @param sheetName - The name of the sheet with the data table to be loaded
	 * @param tableName - The name of the data table to be loaded
	 * @return A map containing each row in the data table with the first column 
	 * as its key and a map of column names and the corresponding entries as the values
	 * @throws DataSourceException
	 */
	public static LinkedHashMap<String,List<LinkedHashMap<String, String>>> getAllDataTable(String xlFileName, String sheetName, String tableName) {
		LOGGER.debug("==>getAllDataTable()");
		LinkedHashMap<String,List<LinkedHashMap<String, String>>> data = new LinkedHashMap<String, List<LinkedHashMap<String, String>>>();
		try{
			InputStream xlFile = getExcelFile(xlFileName);

			Workbook workbook = Workbook.getWorkbook(xlFile);
			Sheet sheet = workbook.getSheet(sheetName);
			int startRow,startCol, endRow, endCol;
			Cell tableStart=sheet.findCell(tableName);
			startRow=tableStart.getRow();
			startCol=tableStart.getColumn();

			Cell tableEnd= sheet.findCell(tableName, startCol+1,startRow+1, 100, 64000,  false);                               
			LOGGER.debug("tableEnd" +tableEnd + " : "+tableEnd.getRow());

			endRow=tableEnd.getRow();
			endCol=tableEnd.getColumn();

			// identify the column headers
			List<String> columns = new ArrayList<String>();
			for (int field=startCol+2;field<endCol;field++){
				columns.add(sheet.getCell(field,startRow).getContents());
			}

			// for each row, create a map of columnn headers and the corresponding value
			LOGGER.debug("startRow" +startRow+ " : "+endRow);
			List<LinkedHashMap<String, String>> list = new ArrayList<LinkedHashMap<String, String>>();
			for (int i=startRow+1;i<endRow;i++){
				LOGGER.debug("row : "+i);
				LinkedHashMap<String, String> entry = new LinkedHashMap<String, String>();
				for (int j=startCol+2;j<endCol;j++){
					entry.put(columns.get(j-(startCol+2)),sheet.getCell(j,i).getContents());
				}
				if(data.get(sheet.getCell(startCol+1,i).getContents())==null) {
					list = new ArrayList<LinkedHashMap<String,String>>();
				} 
				list.add(entry);
				data.put(sheet.getCell(startCol+1,i).getContents(), list);
				LOGGER.debug(" content : "+sheet.getCell(startCol+1,i).getContents());
			}
		}
		catch (Exception e)    {
			e.printStackTrace();
		}

		LOGGER.debug("<==getAllDataTable()");
		return data;
	}

}

