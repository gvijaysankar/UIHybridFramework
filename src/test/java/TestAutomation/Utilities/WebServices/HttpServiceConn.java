package TestAutomation.Utilities.WebServices;



import java.net.SocketTimeoutException;
import java.util.Map;

public interface HttpServiceConn {
	
	public static String RESPONSEBODY = "RESPONSEBODY";
	public static String RESPONSEHEADERS = "RESPONSEHEADERS";
	public static String REQUESTHEADERS = "REQUESTHEADERS";
	public static String STATUSCODE = "STATUSCODE";
	public static String RESPONSEBODYASSTREAM = "RESPONSEBODYASSTREAM";
	
	/**
	 * 
	 * @param header
	 * @param requestBody
	 * @return
	 * @throws SocketTimeoutException 
	 */
	public Map<String, Object> get(String URL, Map<String, String> header) throws SocketTimeoutException ;
	
	/**
	 * 
	 * @param header
	 * @param requestBody
	 * @return
	 * @throws SocketTimeoutException 
	 */
	public Map<String, Object> post(String URL, Map<String, String> header, String requestBody) throws SocketTimeoutException ;
	
	
	/**
	 * 
	 * @param header
	 * @param requestBody
	 * @return
	 */
	public Map<String, Object> put(String URL, Map<String, String> header, String requestBody) throws SocketTimeoutException ;
	
	
	/**
	 * 
	 * @param header
	 * @param requestBody
	 * @return
	 */
	public Map<String, Object> delete(String URL, Map<String, String> header, String requestBody) throws SocketTimeoutException ;
	
	/**
	 * 
	 * @param header
	 * @param requestBody
	 * @return
	 */
	public Map<String, Object> options(String URL, Map<String, String> header, String requestBody) throws SocketTimeoutException ;
	
	/**
	 * 
	 * @param header
	 * @param requestBody
	 * @return
	 */
	public Map<String, Object> head(String URL, Map<String, String> header, String requestBody) throws SocketTimeoutException ;
	
	

}


