/**
 * 
 */
package com.solinor.paymenthighway.connect;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.solinor.paymenthighway.PaymentHighwayUtility;
import com.solinor.paymenthighway.security.SecureSigner;

/**
 * PaymentHighway Form API Connections
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class FormAPIConnection {

	private final static String USER_AGENT = "PaymentHighway Java Lib";
	private final static String CONTENT_TYPE = "application/x-www-form-urlencoded";
	private final static String METHOD_POST = "POST";
	
	String serviceUrl = null;
	String signatureKeyId = null;
	String signatureSecret = null;
	
	/**
	 * Constructor
	 */
	public FormAPIConnection(String serviceUrl, 
			String signatureKeyId, 
			String signatureSecret) {	
		
		this.serviceUrl = serviceUrl;
		this.signatureKeyId = signatureKeyId;
		this.signatureSecret = signatureSecret;
	}
		
	/**
	 * Form API call to add card
	 * 
	 * @param formParameters
	 * @return String responseBody
	 * @throws IOException
	 */
	public String addCardRequest(List<NameValuePair> nameValuePairs) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		final String formUri = "/form/view/add_card";
		
        try {
            HttpPost httpPost = new HttpPost(this.serviceUrl + formUri);
            
            // sort alphabetically per key
            PaymentHighwayUtility.sortParameters(nameValuePairs);
            
            // create signature
            String signature = this.createSignature(METHOD_POST, formUri, nameValuePairs);
            nameValuePairs.add(new BasicNameValuePair("signature", signature));
            
            // add request headers
            this.addHeaders(httpPost);
            
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            return httpclient.execute(httpPost, responseHandler);
    
        } finally {
            httpclient.close();
        }
    }
	
	/**
	 * Form API call to make a payment
	 * 
	 * @param paymentParameters
	 * @return 
	 * @throws IOException
	 */
	public String paymentRequest(List<NameValuePair> nameValuePairs) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		final String formPaymentUri = "/form/view/pay_with_card";
		
        try {
            HttpPost httpPost = new HttpPost(this.serviceUrl + formPaymentUri);
            
            // sort alphabetically per key
            PaymentHighwayUtility.sortParameters(nameValuePairs);
            
            // create signature
            String signature = this.createSignature(METHOD_POST, formPaymentUri, nameValuePairs);
            nameValuePairs.add(new BasicNameValuePair("signature", signature));

        	// add request headers
            this.addHeaders(httpPost);
            
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            return httpclient.execute(httpPost, responseHandler);

        } finally {
            httpclient.close();
        }
	}
	
	/**
	 * Form API call to add card and make a payment
	 * 
	 * @param paymentParameters
	 * @return 
	 * @throws IOException
	 */
	public String addCardAndPayRequest(
			List<NameValuePair> nameValuePairs) throws IOException {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		final String formPaymentUri = "/form/view/add_and_pay_with_card";
		
        try {
            HttpPost httpPost = new HttpPost(this.serviceUrl + formPaymentUri);
            
            // sort alphabetically per key
            PaymentHighwayUtility.sortParameters(nameValuePairs);
            
            // create signature
            String signature = this.createSignature(METHOD_POST, formPaymentUri, nameValuePairs);
            nameValuePairs.add(new BasicNameValuePair("signature", signature));

            // add request headers
            this.addHeaders(httpPost);
            
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            return httpclient.execute(httpPost, responseHandler);

	    } finally {
            httpclient.close();
        }
	}	

	/** 
	 * Create a secure signature
	 * 
	 * @param method
	 * @param uri
	 * @param formPaymentSphParameters
	 * @return String signature
	 */
	private String createSignature(String method, String uri, 
				List<NameValuePair> nameValuePairs) {
		
		nameValuePairs = PaymentHighwayUtility.parseSphParameters(nameValuePairs);
		SecureSigner ss = new SecureSigner(this.signatureKeyId, this.signatureSecret);
		return ss.createSignature(method, uri, nameValuePairs, "");
	}
	
	/**
	 * Add headers to request
	 * @param httpPost
	 * @return HttpPost with headers
	 */
	protected void addHeaders(HttpPost httpPost) {
		httpPost.addHeader("User-Agent", USER_AGENT);
        httpPost.addHeader("Content-Type", CONTENT_TYPE);
	}
	
}
