/**
 * 
 */
package com.solinor.paymenthighway;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;

import com.solinor.paymenthighway.connect.FormAPIConnection;

/**
 * Payment Highway Form API Service.
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class FormAPI {

	/*
	 * These needs to be defined
	 * either assign directly, via constructor or use setter methods.
	 */
	String serviceUrl = null;
	String signatureKeyId = null;
	String signatureSecret = null;

	/**
	 * Constructors
	 */
	public FormAPI() { 
	}	
	
	public FormAPI(String serviceUrl, String signatureKeyId,
			String signatureSecret) {
		this.serviceUrl = serviceUrl;
		this.signatureKeyId = signatureKeyId;
		this.signatureSecret = signatureSecret;
	}	
	
	/**
	 * Payment Highway Form API Add Card
	 * @param parameters
	 * @return String response from Payment Highway
	 * @throws IOException
	 */
	public String addCard(List<NameValuePair> nameValuePairs) throws IOException {
		FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, 
				this.signatureKeyId, this.signatureSecret);
		return formApi.addCardRequest(nameValuePairs);
	}
	
	/**
	 * Payment Highway Form API Pay With Card
	 * @param parameters
	 * @return String response from Payment Highway
	 * @throws IOException
	 */
	public String payWithCard(List<NameValuePair> nameValuePairs) throws IOException {
		FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret);
		return formApi.paymentRequest(nameValuePairs);
	}
	
	/**
	 * Payment Highway Form API Add Card and Pay
	 * @param parameters
	 * @return String response from Payment Highway
	 * @throws IOException
	 */
	public String addCardAndPay(List<NameValuePair> nameValuePairs) throws IOException {
		FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, 
				this.signatureKeyId, this.signatureSecret);
		return formApi.addCardAndPayRequest(nameValuePairs);
	}
	
	/*
	 * Setters
	 */
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public void setSignatureKeyId(String signatureKeyId) {
		this.signatureKeyId = signatureKeyId;
	}

	public void setSignatureSecret(String signatureSecret) {
		this.signatureSecret = signatureSecret;
	}
}
