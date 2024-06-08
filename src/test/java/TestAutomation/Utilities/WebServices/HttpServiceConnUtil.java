package TestAutomation.Utilities.WebServices;



import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.Header;


public class HttpServiceConnUtil {
	
	public HttpServiceConnUtil() {
		
	}
	
	/**
	 * 
	 * Converts the Header Array into a Map which contains Header Information 
	 * with Key Value pairs
	 * 
	 * @param headArr
	 * @return Map<String,String>
	 */
	public static Map<String, String> getHeaders(Header[] headArr) {
		
		Map<String, String> headerMap = null;
		
		if(null != headArr &&  headArr.length > 0) {
						
			headerMap = new LinkedHashMap<String, String>();
			
			for(Header header : headArr) {
				
				headerMap.put(header.getName(), header.getValue());
								
			}
			
		}
		
		return headerMap;
		
	}
	
	/**
	 * 
	 * Converts the Header Array into a Map which contains Header Information 
	 * with Key Value pairs
	 * 
	 * @param headArr
	 * @return Map<String,String>
	 */
	public static Map<String, String> getHeaders(org.apache.commons.httpclient.Header[] headArr) {
		
		Map<String, String> headerMap = null;
		
		if(null != headArr &&  headArr.length > 0) {
						
			headerMap = new LinkedHashMap<String, String>();
			
			for(org.apache.commons.httpclient.Header header : headArr) {
				
				headerMap.put(header.getName(), header.getValue());
								
			}
			
		}
		
		return headerMap;
		
	}

}

