package com.solinor.paymenthighway;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.solinor.paymenthighway.security.SecureSigner;

/** 
 * Creates name value pairs that can used on the form
 * that sends them to Payment Hiqhway.
 * 
 * Creates a request id, timestamp and signature
 * based on request parameters. 
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class FormParameterBuilder {
	
	private final static String METHOD_POST = "POST";
	
	private final static String SPH_ACCOUNT = "sph-account";
	private final static String SPH_MERCHANT = "sph-merchant";
	private final static String SPH_AMOUNT = "sph-amount";
	private final static String SPH_CURRENCY = "sph-currency";
	private final static String SPH_ORDER = "sph-order";
	private final static String SPH_SUCCESS_URL = "sph-success-url";
	private final static String SPH_FAILURE_URL = "sph-failure-url";
	private final static String SPH_CANCEL_URL = "sph-cancel-url";
	private final static String SPH_REQUEST_ID = "sph-request-id";
	private final static String SPH_TIMESTAMP = "sph-timestamp";
	private final static String LANGUAGE = "language";
	private final static String DESCRIPTION = "description";
	private final static String SIGNATURE = "signature";
	
	private final String addCardUri = "/form/view/add_card";
	private final String payWithCardUri = "/form/view/pay_with_card";
	private final String addCardAndPayUri = "/form/view/add_and_pay_with_card";

	private String signatureKeyId = null;
	private String signatureSecret = null;
	
	/**
	 * Constructor
	 * @param signatureKeyId
	 * @param signatureSecret
	 */
	public FormParameterBuilder(String signatureKeyId, String signatureSecret) {
		this.signatureKeyId = signatureKeyId;
		this.signatureSecret = signatureSecret;
	}
			
	/**
	 * Get parameters for Add Card request
	 * 
	 * @param account
	 * @param merchant
	 * @param amount
	 * @param currency
	 * @param orderId
	 * @param successUrl
	 * @param failureUrl
	 * @param cancelUrl
	 * @param language
	 * @return List<NameValuePair> 
	 */
	public List<NameValuePair> getAddCardParameters(
			String account, String merchant, 
			String amount, String currency, String orderId, 
			String successUrl, String failureUrl, String cancelUrl, 
			String language) {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(SPH_ACCOUNT, account));
		nameValuePairs.add(new BasicNameValuePair(SPH_AMOUNT, amount));
		nameValuePairs.add(new BasicNameValuePair(SPH_CANCEL_URL, cancelUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_CURRENCY, currency));
		nameValuePairs.add(new BasicNameValuePair(SPH_FAILURE_URL, failureUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_MERCHANT, merchant));
		nameValuePairs.add(new BasicNameValuePair(SPH_ORDER, orderId));
		nameValuePairs.add(new BasicNameValuePair(SPH_REQUEST_ID, PaymentHighwayUtility.createRequestId()));
		nameValuePairs.add(new BasicNameValuePair(SPH_SUCCESS_URL, successUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_TIMESTAMP, PaymentHighwayUtility.getUtcTimestamp()));
		nameValuePairs.add(new BasicNameValuePair(LANGUAGE, language));
		
		// sort alphabetically per key
        PaymentHighwayUtility.sortParameters(nameValuePairs);
        
        // create signature
        String signature = this.createSignature(METHOD_POST, this.addCardUri, nameValuePairs);
        nameValuePairs.add(new BasicNameValuePair(SIGNATURE, signature));
        return nameValuePairs;
	}

	/**
	 * Get parameters for Payment request.
	 * 
	 * @param account
	 * @param merchant
	 * @param amount
	 * @param currency
	 * @param orderId
	 * @param successUrl
	 * @param failureUrl
	 * @param cancelUrl
	 * @param language
	 * @param description
	 * @return List<NameValuePair>
	 */
	public List<NameValuePair> getPaymentParameters(
			String account, String merchant, 
			String amount, String currency, String orderId, 
			String successUrl, String failureUrl, String cancelUrl, 
			String language, String description) {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(SPH_ACCOUNT, account));
		nameValuePairs.add(new BasicNameValuePair(SPH_AMOUNT, amount));
		nameValuePairs.add(new BasicNameValuePair(SPH_CANCEL_URL, cancelUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_CURRENCY, currency));
		nameValuePairs.add(new BasicNameValuePair(SPH_FAILURE_URL, failureUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_MERCHANT, merchant));
		nameValuePairs.add(new BasicNameValuePair(SPH_ORDER, orderId));
		nameValuePairs.add(new BasicNameValuePair(SPH_REQUEST_ID, PaymentHighwayUtility.createRequestId()));
		nameValuePairs.add(new BasicNameValuePair(SPH_SUCCESS_URL, successUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_TIMESTAMP, PaymentHighwayUtility.getUtcTimestamp()));
		nameValuePairs.add(new BasicNameValuePair(LANGUAGE, language));
		nameValuePairs.add(new BasicNameValuePair(DESCRIPTION, description));
		
		// sort alphabetically per key
        PaymentHighwayUtility.sortParameters(nameValuePairs);
        
        // create signature
        String signature = this.createSignature(METHOD_POST, this.payWithCardUri, nameValuePairs);
        nameValuePairs.add(new BasicNameValuePair(SIGNATURE, signature));
        return nameValuePairs;
	}
	
	/**
	 * Get parameters for Add Card and Pay request.
	 * 
	 * @param account
	 * @param merchant
	 * @param amount
	 * @param currency
	 * @param orderId
	 * @param successUrl
	 * @param failureUrl
	 * @param cancelUrl
	 * @param language
	 * @param description
	 * @return List<NameValuePair> 
	 */
	public List<NameValuePair> getAddCardAndPaymentParameters(
			String account, String merchant, 
			String amount, String currency, String orderId, 
			String successUrl, String failureUrl, String cancelUrl, 
			String language, String description) {
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(SPH_ACCOUNT, account));
		nameValuePairs.add(new BasicNameValuePair(SPH_AMOUNT, amount));
		nameValuePairs.add(new BasicNameValuePair(SPH_CANCEL_URL, cancelUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_CURRENCY, currency));
		nameValuePairs.add(new BasicNameValuePair(SPH_FAILURE_URL, failureUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_MERCHANT, merchant));
		nameValuePairs.add(new BasicNameValuePair(SPH_ORDER, orderId));
		nameValuePairs.add(new BasicNameValuePair(SPH_REQUEST_ID, PaymentHighwayUtility.createRequestId()));
		nameValuePairs.add(new BasicNameValuePair(SPH_SUCCESS_URL, successUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_TIMESTAMP, PaymentHighwayUtility.getUtcTimestamp()));
		nameValuePairs.add(new BasicNameValuePair(LANGUAGE, language));
		nameValuePairs.add(new BasicNameValuePair(DESCRIPTION, description));
		
		// sort alphabetically per key
        PaymentHighwayUtility.sortParameters(nameValuePairs);
        
        // create signature
        String signature = this.createSignature(METHOD_POST, this.addCardAndPayUri, nameValuePairs);
        nameValuePairs.add(new BasicNameValuePair(SIGNATURE, signature));
        return nameValuePairs;
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
}
