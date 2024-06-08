package TestAutomation.Utilities.Reports;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import TestAutomation.Scripts.Suite.TrialOrderplacementValidationTest;
import TestAutomation.Utilities.utility.Constants;
import TestAutomation.Utilities.utility.ExcelUtilities;
import TestAutomation.Utilities.utility.GenericFunctions;
import jxl.Workbook;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;





public class WritetoExcel {
	
	public static final String resultsFolder = Constants.resultsFolderExcel;
	private static Logger LOGGER = Logger.getLogger(WritetoExcel.class);
	public static int pouiFlag = 0;
	public static String pouiFile = null;
	 
	 public static void ValidationOutputResultsSheet(ArrayList<Map<String, String>> validationDetails,Map<String, String> dataMap,String className) throws WriteException
	 {		
  		ArrayList<String> ColoumnNamesKeyListSheet1 = new ArrayList<String>();
	
  		ColoumnNamesKeyListSheet1.add("mobileNumber"); 	
  		ColoumnNamesKeyListSheet1.add("email");
  		ColoumnNamesKeyListSheet1.add("URL");
  		ColoumnNamesKeyListSheet1.add("slotType");
  		ColoumnNamesKeyListSheet1.add("interviewMode");
  		ColoumnNamesKeyListSheet1.add("Subject");
  		ColoumnNamesKeyListSheet1.add("LOGIN");
  		ColoumnNamesKeyListSheet1.add("STUDENTHOME_MOCK_INTERVIEW");
  		ColoumnNamesKeyListSheet1.add("EDIT_CONTACT_DETAILS");    
  		ColoumnNamesKeyListSheet1.add("EDITED_DETAILS");
  		ColoumnNamesKeyListSheet1.add("TestResult");
  		ColoumnNamesKeyListSheet1.add("Comments");
  		  		   		
		WritableCellFormat w = new WritableCellFormat();
		w.setBackground(Colour.RED);
		String os = System.getProperty("os.name").toLowerCase();
		String filepath="";
		if(os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0) {
			filepath=resultsFolder;
		}
		else if (os.indexOf("win") >= 0) {
			filepath=resultsFolder;
		}
		try
		{
			if(pouiFlag==0){
				pouiFile = ExcelUtilities.outputToSharedFolder(filepath,dataMap,className);
				WritableWorkbook workbook = Workbook.createWorkbook(new File(pouiFile
						));

				WritableSheet sheet1 = workbook.createSheet(
						"StudentValidation_Results", 0);
				
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

