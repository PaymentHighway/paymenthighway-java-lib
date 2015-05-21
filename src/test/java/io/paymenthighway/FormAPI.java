package io.paymenthighway;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;

import io.paymenthighway.connect.FormAPIConnection;

/**
 * Payment Highway Form API Service.
 */
public class FormAPI {

	/*
	 * These need to be defined
	 * either assign directly, via constructor or use setter methods.
	 */
	String serviceUrl = null;
	String signatureKeyId = null;
	String signatureSecret = null;

	/**
	 * Constructors
	 */
	public FormAPI(String serviceUrl, String signatureKeyId,
			String signatureSecret) {
		this.serviceUrl = serviceUrl;
		this.signatureKeyId = signatureKeyId;
		this.signatureSecret = signatureSecret;
	}	
	
	/**
	 * Payment Highway Form API Add Card
	 * @return String response from Payment Highway
	 * @throws IOException
	 */
	public String addCard(List<NameValuePair> nameValuePairs) throws IOException {
		FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		return formApi.addCardRequest(nameValuePairs);
	}
	
	/**
	 * Payment Highway Form API Pay With Card
	 * @return String response from Payment Highway
	 * @throws IOException
	 */
	public String payWithCard(List<NameValuePair> nameValuePairs) throws IOException {
		FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		return formApi.paymentRequest(nameValuePairs);
	}
	
	/**
	 * Payment Highway Form API Add Card and Pay
	 * @return String response from Payment Highway
	 * @throws IOException
	 */
	public String addCardAndPay(List<NameValuePair> nameValuePairs) throws IOException {
		FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, this.signatureKeyId, this.signatureSecret);
		return formApi.addCardAndPayRequest(nameValuePairs);
	}

}
