package TestAutomation.Utilities.utility;



import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



public  class ExcelUtilities {

	private static Logger LOGGER = Logger.getLogger(ExcelUtilities.class);
	
	/**
	 * Method to get two dimensional Object array from Excel Sheet data.
	 *  
	 * @param fileName -  File Name or Complete File Path if External Source 
	 * @param sheetName - Name of the Excel Sheet to fetch records from.
	 * 
	 * @return Object[][] - Contains maps.  
	 */
	public static List<Map<String, String>> createData(String fileName , String sheetName) {
		final String NULLROW = "NULLROW";
		//	  	Object [] [] datProviderData = null;
		List<Map<String, String>> excelDataList = null;
		List<String> keyList = null;
		Map<String, String> dataMap = null;
		//	  	String[] numericStringArr = null;
		//	  	String numericString = null;
		//	  	int colCount = 1;
		//	  	int totalColumns = 0;
		Date date = null;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		String formattedDate = null;
		InputStream xlFile = DatabaseUtil.getExcelFile(fileName);

		try 
		{
			keyList = new ArrayList<String>();
			OPCPackage opc = OPCPackage.open(xlFile);

			Workbook wb1 = WorkbookFactory.create(opc);
			Sheet sheet = wb1.getSheet(sheetName);
			XSSFFormulaEvaluator formulaEv = new XSSFFormulaEvaluator( (XSSFWorkbook) wb1);


			//			int rowNum = sheet.getLastRowNum();

			//			datProviderData = new Object [sheet.getLastRowNum()- sheet.getFirstRowNum()][colCount];
			excelDataList = new ArrayList<Map<String, String>>();

			for (Row row : sheet) {

				if(row.getRowNum() == sheet.getFirstRowNum()) {
					//            		totalColumns = row.getLastCellNum();
					for (int fr = 0; fr < row.getLastCellNum(); fr++) {
						if(null != row.getCell(fr)){
							keyList.add(row.getCell(fr).toString());
						} else {
							keyList.add(NULLROW);
						}          				
					}

				} else if (row.getRowNum() > sheet.getFirstRowNum() ){
					dataMap = new LinkedHashMap<String, String>();
					for (int cn = 0 ; cn < keyList.size();cn++ ) {

						if(!keyList.get(cn).equals(NULLROW)) {

							if(null == row.getCell(cn)){

								dataMap.put(keyList.get(cn).toString(), "");        

							} else {

                                Cell cell = row.getCell(cn, Row.CREATE_NULL_AS_BLANK);
								
								if(cell.getCellType() == Cell.CELL_TYPE_BLANK) {
									dataMap.put(keyList.get(cn).toString(),"");
								} 
								else if (cell.getCellType() != Cell.CELL_TYPE_NUMERIC) {
									dataMap.put(keyList.get(cn).toString(),(row.getCell(cn).toString()));
								} 
								else if (HSSFDateUtil.isCellDateFormatted(cell))
								{   
									CellValue cValue = formulaEv.evaluate(cell);
									double dv = cValue.getNumberValue();
									date = HSSFDateUtil.getJavaDate(dv);

									//String dateFmt = cell.getCellStyle().getDataFormatString();
									//String strValue = new CellDateFormatter(dateFmt).format(date); 
									formattedDate  = formatter.format(date);
									dataMap.put(keyList.get(cn).toString(),formattedDate);
								}
								else {
									dataMap.put(keyList.get(cn).toString(),getValueAsString(row.getCell(cn).toString()));
								}
							}
						}
					}

					//datProviderData [row.getRowNum() - (sheet.getFirstRowNum()) - 1][0] = dataMap;
					excelDataList.add(dataMap);
				}
			}
			opc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return excelDataList;
	}

	private static String getValueAsString(String value){
		BigDecimal decimal = null;
		Double checkForDbl = Double.valueOf(value);

		if( (checkForDbl - (checkForDbl.intValue())) > 0 ) {
			decimal = new BigDecimal(value);
		} else {
			decimal = new BigDecimal(Double.valueOf(value));
		}
		return decimal.toPlainString();
	}

	public static Map<String, String> getMappedData(String fileName , String sheetName) {
		Map<String, String> dataMap = new HashMap<String, String>();

		InputStream xlFile = DatabaseUtil.getExcelFile(fileName);

		try 
		{
			OPCPackage opc = OPCPackage.open(xlFile);
			Workbook wb1 = WorkbookFactory.create(opc);
			Sheet sheet = wb1.getSheet(sheetName);

			for (Row row : sheet) {
			}
			opc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataMap;
	}

	public static List generateKeyList(String fileName , String sheetName) 
	{
		List<String> keyList = null;
		InputStream xlFile = DatabaseUtil.getExcelFile(fileName);
		try
		{
			keyList = new ArrayList<String>();
			OPCPackage opc = OPCPackage.open(xlFile);

			Workbook wb1 = WorkbookFactory.create(opc);
			Sheet sheet = wb1.getSheet(sheetName);
			for(Row row : sheet){
				if(row.getRowNum() == sheet.getFirstRowNum()) {
					for (int fr = 0; fr < row.getLastCellNum(); fr++) {
						if(null != row.getCell(fr)){
							keyList.add(row.getCell(fr).toString());
						}     				
					}
				}
			}
			opc.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return keyList;
	}
	
	public static ArrayList<Map<String, String>> readDataFromExcel(String filepath) throws IOException 
	{
		ArrayList<Map<String, String>> outputdetails = new ArrayList<Map<String, String>>();
		String path = filepath; 
		LOGGER.info("*********************************** Reading data From the output sheet path *******************************************");
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(path));
		Sheet sheet = workbook.getSheetAt(0);		   
		int lastRow = sheet.getLastRowNum();	
		LOGGER.info("*********************************** No of rows in Excel - "+ lastRow +"*******************************************");
		//Looping over entire row
		for(int i=1; i<=lastRow; i++)
		{
		   System.out.print("Value - "+i);
		   Map<String, String> outputdetails1 = new HashMap<String, String>(); 
		   Row row = sheet.getRow(0);
		   Row row1 = sheet.getRow(i);		   
		   int lastcell = row.getLastCellNum();
		   LOGGER.info("*********************************** No of Column in Excel - "+ lastcell +"*******************************************");
		   for(int j=0; j<=lastcell-1; j++)
		   {
			  Map<String, String> dataMap = new HashMap<String, String>();  		   
		      Cell valueCell = row1.getCell(j);
		      Cell keyCell = row.getCell(j);		   
		      String value = valueCell.getStringCellValue().trim();
		      String key = keyCell.getStringCellValue().trim(); 		   
		      //Putting key & value in dataMap
		      dataMap.put(key, value);		   
		     //Putting dataMap to excelFileMap
		     outputdetails1.putAll(dataMap);
		   }
		   outputdetails.add(outputdetails1);
		 }
		 return outputdetails;
	}
	
	
	
    public static Map<String,String> getstudentDetailsTestData(Map<String,String>dataMap1)
    {
        ArrayList<Map<String,String>> data = new ArrayList<Map<String,String>>();
        data =	(ArrayList<Map<String, String>>) ExcelUtilities.createData("StudentTestData.xlsx","Data");
        for(int j=0;j<data.size();j++)			
         {
	         Map<String,Map<String,String>> map = new HashMap<String,Map<String,String>>();
	         Map<String,String> map2 = new HashMap<String,String>();				
	         map.put("keyvalue",data.get(j));
	         map2.clear();
	         map2.putAll(map.get("keyvalue"));		
	         if(dataMap1.get("mobileNumber").equalsIgnoreCase(map2.get("mobileNumber")))
	          {
	        	 dataMap1.putAll(map2);
		        break;
	          }		
	
          }	

        return dataMap1;
    }
    
    public static ArrayList<Map<String,String>> getstudentCompleteDetailsTestData()
    {
        ArrayList<Map<String,String>> data = new ArrayList<Map<String,String>>();
        data =	(ArrayList<Map<String, String>>) ExcelUtilities.createData("StudentTestData.xlsx","Data");
        return data;
    }

	public static synchronized String outputToSharedFolder(String path,Map<String,String> dataMap,String className) throws Exception {
		LOGGER.info("==> outputToSharedFolder()");
		File requestLogFolder = new File(path);
		if(!requestLogFolder.exists()){
			requestLogFolder.mkdir();
		}
		if(System.getProperty("os.name") != "Linux")
		{

		}

		String timestamp = new SimpleDateFormat("dd-MM-yyyy_HHmm").format(Calendar.getInstance().getTime());
		
		String var = "Student_Validations";

		String date = (timestamp.replaceAll(timestamp.split("_")[1],"")).replaceAll("_", "");

		File folder = new File(path+"/"+date);
		if(!folder.exists()){
			folder.mkdir();
		}
		
		File requestLogFile = new File(folder.getAbsolutePath()+"/"+var+"_"+"_["+timestamp+"].xls");
		if(!requestLogFile.exists())
		{
			requestLogFile.createNewFile();
		}
		LOGGER.info("<== outputToSharedFolder()");
		return folder.getAbsolutePath()+"/"+var+"_"+"_["+timestamp+"].xls";
	}

	public static ArrayList<String> tofillColorWithRowsParameter(ArrayList<String> keyList,String key,int i, int j,int rows,ArrayList<Map<String, String>> outputData,WritableSheet sheet,WritableCellFormat w,ArrayList<String> errorList,String strTobeCompared) throws RowsExceededException, WriteException
	{
		if(outputData.get(i)
				.get(keyList.get(j))!=null){
		if(!outputData.get(i)
				.get(keyList.get(j)).equalsIgnoreCase(strTobeCompared))
				{
					Label label = new Label(j, rows+i, outputData.get(i)
							.get(keyList.get(j)),w);
					sheet.addCell(label);
					errorList.add(key+" "+outputData.get(i)
							.get(keyList.get(j)));
					
				}
				else
				{
					Label label = new Label(j, rows+i, outputData.get(i)
							.get(keyList.get(j)));
					sheet.addCell(label);
				}
		}
		return errorList;
		
		
	}
	
	public static ArrayList<String> tofillColorWithoutRowsParameter(ArrayList<String> keyList,String key,int i, int j,ArrayList<Map<String, String>> outputData,WritableSheet sheet,WritableCellFormat w,ArrayList<String> errorList,String strTobeCompared) throws RowsExceededException, WriteException
	{
		if(outputData.get(i - 1)
				.get(keyList.get(j))!=null){
		if(!outputData.get(i - 1)
		.get(keyList.get(j)).equalsIgnoreCase(strTobeCompared))
		{
			Label label = new Label(j, i, outputData.get(i - 1)
					.get(keyList.get(j)),w);
			sheet.addCell(label);
			errorList.add(key+" "+outputData.get(i - 1)
					.get(keyList.get(j)));
		}
		else
		{
			Label label = new Label(j, i, outputData.get(i - 1)
					.get(keyList.get(j)));
			sheet.addCell(label);
		}
		}
		return errorList;
	}

    
    
}

