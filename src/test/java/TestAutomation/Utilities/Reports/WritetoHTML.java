package TestAutomation.Utilities.Reports;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import TestAutomation.Scripts.Suite.TrialOrderplacementValidationTest;
import TestAutomation.Utilities.utility.Constants;
import TestAutomation.Utilities.utility.GenericFunctions;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;





public class WritetoHTML {
	
	public static final String resultsFolder = Constants.resultsFolderHTML;
	private static Logger LOGGER = Logger.getLogger(WritetoHTML.class);
	 
	 public static void writeLog(ArrayList<Map<String, String>> status) throws IOException, Exception
		{ 		  File folder=null;	
				try
				{
		        folder = new File(resultsFolder);	
			    //File folder = new File("\\\\REN55703-WX-3\\Outputsheet"+"\\"+"HTMLFiles");
				if(!folder.exists()){
					boolean result =folder.mkdirs();
					if(result) {
						LOGGER.info("The directory is created !");
			        }	
				}
				else {
					LOGGER.info("The directory already exist");
			    }
				}
				catch(Exception e)
				{
					throw new Exception("Unable to Create Output folder");
				}
				
				File file = new File(folder+"\\StudentDetails.html"); 
				StringBuilder buf = new StringBuilder();
				buf.append("<html>" +
				           "<body style=\"background-color:powderblue;\">" +
						   "<p>Hi,\n</p>" +
				           "<p> Students Validation Test Results\n</p>" +  
						   "<p>Check detailed Validation report O/p Results Path - {user}/OutputResults/</P>" +
				           "<p style = \"color:blue; font-weight: bold;\"><u>Student Validation Results</u></p>" +
				           "<table border=\"1\">" +
				           "<tr>" +
				           "<th>  mobileNumber  </th>" +
				           "<th>  email  </th>" +
				           "<th>  URL   </th>" +
				           "<th>  interviewSlotType   </th>" +
				           "<th>  interviewMode   </th>" +
				           "<th>  interviewSubject   </th>" +
				           "<th>  LOGIN_VALIDATIONS   </th>" +
				           "<th>  STUDENTHOME_MOCK_INTERVIEW_VALIDATIONS   </th>" +
				           "<th>  EDIT_CONTACT_DETAILS_VALIDATIONS   </th>" +
				           "<th>  OverAllResult   </th>" +				          
				           "<th>  Comments </th>" +
				           "</tr>");
				for (int i = 0; i < status.size(); i++) {
					Map<String,Map<String,String>> specMap = new HashMap<String,Map<String,String>>();					
					specMap.put("keyvalue",status.get(i));
					
				    buf.append("<tr>" +
				    		   ""+resultsStatusColor(GenericFunctions.getData(specMap,"keyvalue","mobileNumber"))+"</td>" +
				    		   ""+resultsStatusColor(GenericFunctions.getData(specMap,"keyvalue","email"))+"</td>" +                            
				               ""+resultsStatusColor(GenericFunctions.getData(specMap,"keyvalue","URL"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(specMap,"keyvalue","slotType"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(specMap,"keyvalue","interviewMode"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(specMap,"keyvalue","Subject"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(specMap,"keyvalue","LOGIN"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(specMap,"keyvalue","STUDENTHOME_MOCK_INTERVIEW"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(specMap,"keyvalue","EDIT_CONTACT_DETAILS"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(specMap,"keyvalue","TestResult"))+"</td>" +
				               ""+resultsStatusColor(GenericFunctions.getData(specMap,"keyvalue","Comments"))+"</td>" +
                              "</tr>");
				}
				buf.append("</table>" +
						   "<p style = \"color:blue; font-weight: bold;\"> For Failed results,Check the detailed report in the output results sheet</P>" +
						   "</body>" +
				           "</html>");
							
				BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)); 
				bufferedWriter.write(buf.toString()); 
				bufferedWriter.close(); 
			}
		
		public static String resultsStatusColor(String Results)
		{
			String val = Results;
			if(Results.contains("PASS"))
			{
				Results = "PASS";
				val = "<td style = \"background: #FFF; color: green; font-weight: bold;\">"+Results;
			}
			else if(Results.contains("FAIL"))
			{
				Results = "FAIL";
				val = "<td style = \"background: #FFF; color: red; font-weight: bold;\">"+Results;
			}
			else if(Results.contains("Key Not Available") || Results.isEmpty() || Results.equalsIgnoreCase("null"))
			{
				val = "<td>"+" ";
			}	
			else if((!(Results.isEmpty())))
			{
				val = "<td>"+Results;
			}
			return val;
		}
}

