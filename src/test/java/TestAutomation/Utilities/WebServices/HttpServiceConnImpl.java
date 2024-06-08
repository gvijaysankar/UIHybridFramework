package TestAutomation.Utilities.WebServices;



import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

import TestAutomation.Utilities.utility.GenericFunctions;

public class HttpServiceConnImpl implements HttpServiceConn {
	private static Logger LOGGER = Logger.getLogger(HttpServiceConnImpl.class);
	private final String CONNECTION_MANAGER_TIMEOUT_STAGE_PROD = "10000";
	private final String SO_TIMEOUT_STAGE_PROD = "60000";
	private final String CONNECTION_MANAGER_TIMEOUT_STAGE_PROD_ACCOUNTS = "30000";
	private final String SO_TIMEOUT_STAGE_PROD_ACCOUNTS = "300000";

	private final String CONNECTION_MANAGER_TIMEOUT_DEV_QA = "20000";
	private final String SO_TIMEOUT_DEV_QA = "60000";
	private final String CONNECTION_MANAGER_TIMEOUT_DEV_QA_ACCOUNTS = "30000";
	private final String SO_TIMEOUT_DEV_QA_ACCOUNTS = "300000";
	
	
	public Map<String, Object> get(String url, Map<String, String> headers) throws SocketTimeoutException {
		LOGGER.info("==>get() :: url- "+url);
		LOGGER.info("==>get() :: headers- "+headers);
		
		GetMethod getMethod = null;
		Map<String , Object> responseMap = null;
		HttpClient httpClient = null;
		
		try {
			httpClient = new HttpClient(getParams(url));			
			getMethod = new GetMethod(URIUtil.encodeQuery(url));

			if(null != headers && headers.size() > 0){
				for(Map.Entry<String, String> entry : headers.entrySet()) {
					getMethod.addRequestHeader(entry.getKey(), entry.getValue());
				}
			}
			httpClient.executeMethod(getMethod);

			responseMap = new LinkedHashMap<String, Object>();			
		
			updateResponseMap(getMethod, responseMap);
			
		} catch(SocketTimeoutException e){
			e.printStackTrace();
			throw new SocketTimeoutException(getExtendedSocketExceptionMessage("; url: "+url));
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			try{
				getMethod.releaseConnection();
			}
			catch(Exception e){
				System.out.println("Exception caught while releasing connection");
			}
			finally{
				getMethod=null;
				httpClient=null;
			}
		}

		LOGGER.info("<==get() :: "+responseMap);
		return responseMap;
	}
	
	public Map<String, Object> get_auth(String url, String user, String pwd) throws SocketTimeoutException {
				
		GetMethod getMethod = null;
		Map<String , Object> responseMap = null;
		HttpClient httpClient = null;
		
		try {
			httpClient = new HttpClient(getParams(url));
			Credentials credentials = new UsernamePasswordCredentials(user, pwd);
			httpClient.getState().setCredentials(AuthScope.ANY,credentials);
			getMethod = new GetMethod(URIUtil.encodeQuery(url));
			
			httpClient.executeMethod(getMethod);

			responseMap = new LinkedHashMap<String, Object>();			
		
			updateResponseMap(getMethod, responseMap);
			
		} catch(SocketTimeoutException e){
			e.printStackTrace();
			throw new SocketTimeoutException(getExtendedSocketExceptionMessage("; url: "+url));
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			try{
				getMethod.releaseConnection();
			}
			catch(Exception e){
				System.out.println("Exception caught while releasing connection");
			}
			finally{
				getMethod=null;
				httpClient=null;
			}
		}

		LOGGER.info("<==get() :: "+responseMap);
		return responseMap;
	}

	public Map<String, Object> post(String url, Map<String, String> headers, String requestBody) throws SocketTimeoutException {
		LOGGER.info("==>post() :: url- "+url);
		LOGGER.info("==>post() :: headers- "+headers);
		LOGGER.info("==>post() :: requestBody- "+requestBody);
		
		HttpClient httpClient = null;
		PostMethod postMethod = null;
		Map<String , Object> responseMap = null;

		try {
			httpClient = new HttpClient(getParams(url));
			postMethod = new PostMethod(url);

			if(null != headers && headers.size() > 0) {
				for(Map.Entry<String, String> entry : headers.entrySet()) {
					postMethod.addRequestHeader(entry.getKey(), entry.getValue());
				}
			}

			if(null != requestBody) {
				postMethod.setRequestEntity(new StringRequestEntity(requestBody, null, "UTF-8"));
			}

			httpClient.executeMethod(postMethod);
			responseMap = new LinkedHashMap<String, Object>();			
		
			updateResponseMap(postMethod, responseMap);
			
		} catch(SocketTimeoutException e){
			e.printStackTrace();
			throw new SocketTimeoutException(getExtendedSocketExceptionMessage("; url: "+url));
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			try{
				postMethod.releaseConnection();
			}
			catch(Exception e){
				System.out.println("Exception caught while releasing connection");
			}
			finally{
				postMethod=null;
				httpClient=null;
			}
		}

		LOGGER.info("<==post() :: "+responseMap);
		return responseMap;
	}

	public Map<String, Object> put(String url, Map<String, String> headers, String requestBody) throws SocketTimeoutException {
		LOGGER.info("==>put() :: url- "+url);
		LOGGER.info("==>put() :: headers- "+headers);
		LOGGER.info("==>put() :: requestBody- "+requestBody);
		
		HttpClient httpClient = null;
		PutMethod putMethod = null;
		Map<String , Object> responseMap = null;

		try {
			httpClient = new HttpClient(getParams(url));
			putMethod = new PutMethod(url);

			if(null != headers && headers.size() > 0) {
				for(Map.Entry<String, String> entry : headers.entrySet()) {
					putMethod.addRequestHeader(entry.getKey(), entry.getValue());
				}
			}

			if(null != requestBody){
				putMethod.setRequestEntity(new StringRequestEntity(requestBody, null, "UTF-8"));
			}

			httpClient.executeMethod(putMethod);
			HttpServiceConnUtil.getHeaders(putMethod.getRequestHeaders());

			responseMap = new LinkedHashMap<String, Object>();
			
			updateResponseMap(putMethod, responseMap);
			
		} catch(SocketTimeoutException e){
			e.printStackTrace();
			throw new SocketTimeoutException(getExtendedSocketExceptionMessage("; url: "+url));
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			try{
				putMethod.releaseConnection();
			}
			catch(Exception e){
				System.out.println("Exception caught while releasing connection");
			}
			finally{
				putMethod=null;
				httpClient=null;
			}
		}

		LOGGER.info("<==put() :: "+responseMap);
		return responseMap;
	}

	public Map<String, Object> delete(String url, Map<String, String> headers, String requestBody) throws SocketTimeoutException {
		LOGGER.info("==>delete() :: url- "+url);
		LOGGER.info("==>delete() :: headers- "+headers);
		LOGGER.info("==>delete() :: requestBody- "+requestBody);
		
		HttpClient httpClient = null;
		DeleteMethod deleteMethod = null;
		Map<String , Object> responseMap = null;

		try {
			httpClient = new HttpClient(getParams(url));
			deleteMethod = new DeleteMethod(url);

			if(null != headers && headers.size() > 0) {
				for(Map.Entry<String, String> entry : headers.entrySet()) {
					deleteMethod.addRequestHeader(entry.getKey(), entry.getValue());
				}
			}

			httpClient.executeMethod(deleteMethod);
			HttpServiceConnUtil.getHeaders(deleteMethod.getRequestHeaders());
			responseMap = new LinkedHashMap<String, Object>();	
			
			updateResponseMap(deleteMethod, responseMap);

		} catch(SocketTimeoutException e){
			e.printStackTrace();
			throw new SocketTimeoutException(getExtendedSocketExceptionMessage("; url: "+url));
		} catch (HttpException e) {
			System.err.println("Fatal protocol violation: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Fatal transport error: " + e.getMessage());
			e.printStackTrace();
		} finally {
			// Release the connection.
			try{
				deleteMethod.releaseConnection();
			}
			catch(Exception e){
				System.out.println("Exception caught while releasing connection");
			}
			finally{
				deleteMethod=null;
				httpClient=null;
			}
		}

		LOGGER.info("<==delete() :: "+responseMap);
		return responseMap;
	}

	private void updateResponseMap(HttpMethodBase httpMethodBase, Map<String , Object> responseMap) throws IOException {
		
		responseMap.put(HttpServiceConn.STATUSCODE, httpMethodBase.getStatusCode());
		responseMap.put(HttpServiceConn.RESPONSEHEADERS, HttpServiceConnUtil.getHeaders(httpMethodBase.getResponseHeaders()));
		
		if(httpMethodBase.getResponseBodyAsString()==null 
				|| "".equals(httpMethodBase.getResponseBodyAsString())) 
		{
			responseMap.put(HttpServiceConn.RESPONSEBODY, "{\"empty\":\"\"}");
			responseMap.put(HttpServiceConn.RESPONSEBODYASSTREAM, new ByteArrayInputStream("{\"empty\":\"\"}".getBytes()));
		}
		else{
			responseMap.put(HttpServiceConn.RESPONSEBODY, httpMethodBase.getResponseBodyAsString());
			responseMap.put(HttpServiceConn.RESPONSEBODYASSTREAM, httpMethodBase.getResponseBodyAsStream());			
		}
	}
	
	public Map<String, Object> options(String URL, Map<String, String> header,
			String requestBody) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Object> head(String URL, Map<String, String> header,
			String requestBody) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private HttpClientParams getParams(String url) {
		LOGGER.info("==>getParams() :: "+url);
		HttpClientParams params = new HttpClientParams();
		
		if(GenericFunctions.isStageOrProdEnvironment()) {
			if(isAccountsServiceLateToRespond(url)){
				params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, new Long(CONNECTION_MANAGER_TIMEOUT_STAGE_PROD_ACCOUNTS));
				params.setParameter(HttpClientParams.SO_TIMEOUT, new Integer(SO_TIMEOUT_STAGE_PROD_ACCOUNTS));
			}
			else{
				params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, new Long(CONNECTION_MANAGER_TIMEOUT_STAGE_PROD));
				params.setParameter(HttpClientParams.SO_TIMEOUT, new Integer(SO_TIMEOUT_STAGE_PROD));
			}
		} 
		else {
			if(isAccountsServiceLateToRespond(url)){
				params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, new Long(CONNECTION_MANAGER_TIMEOUT_DEV_QA_ACCOUNTS));
				params.setParameter(HttpClientParams.SO_TIMEOUT, new Integer(SO_TIMEOUT_DEV_QA_ACCOUNTS));
			}
			else{
				params.setParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, new Long(CONNECTION_MANAGER_TIMEOUT_DEV_QA));
				params.setParameter(HttpClientParams.SO_TIMEOUT, new Integer(SO_TIMEOUT_DEV_QA));
			}
		}
			
		LOGGER.info("<==getParams()");
		return params;
	}

	private String getExtendedSocketExceptionMessage(String extendedMessage) {
		LOGGER.info("==>getSocketExceptionMessage()");
		String strMsg = "";
		
		if(GenericFunctions.isStageOrProdEnvironment()) {
			strMsg = "Didn't get response even after waiting for "+SO_TIMEOUT_STAGE_PROD+" milliseconds; "+extendedMessage;
		} 
		else {
			strMsg = "Didn't get response even after waiting for "+SO_TIMEOUT_DEV_QA+" milliseconds; "+extendedMessage;
		}
			
		LOGGER.info("<==getSocketExceptionMessage()");
		return strMsg;
	}
	
	private boolean isAccountsServiceLateToRespond(String url){
		if(url.matches("(.*)/accounts/(\\d*)(\\w*)/registration(.*)") 
				|| url.matches("(.*)/accounts/(\\d*)(\\w*)/orders(.*)")
					|| url.matches("(.*)/accounts/(\\d*)(\\w*)/my_products(.*)")) {
			return true;
			}
		else {
			return false;
		}
	}
}

