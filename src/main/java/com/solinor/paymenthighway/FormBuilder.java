package com.solinor.paymenthighway;

import com.solinor.paymenthighway.security.SecureSigner;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates parameters that can used on the form that sends them to 
 * Payment Hiqhway.
 * 
 * Creates a request id, timestamp and signature based on request parameters.
 */
public class FormBuilder {

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

	private String method = METHOD_POST;
	private String baseUrl = null;
	private String signatureKeyId = null;
	private String signatureSecret = null;
	private String account = null;
	private String merchant = null;
	private String successUrl = null;
	private String failureUrl = null;
	private String cancelUrl = null;
	private String language = null;


	public FormBuilder(String method, String signatureKeyId,
			String signatureSecret, String account, String merchant,
			String baseUrl, String successUrl, String failureUrl,
			String cancelUrl, String language) {
		this.method = method;
		this.signatureKeyId = signatureKeyId;
		this.signatureSecret = signatureSecret;
		this.account = account;
		this.merchant = merchant;
		this.baseUrl = baseUrl;
		this.successUrl = successUrl;
		this.failureUrl = failureUrl;
		this.cancelUrl = cancelUrl;
		this.language = language;
	}

	/**
	 * Get parameters for Add Card request
	 * 
	 * @return FormContainer
	 */
	public FormContainer generateAddCardParameters() {

		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair(SPH_ACCOUNT, this.account));
		nameValuePairs.add(new BasicNameValuePair(SPH_CANCEL_URL, this.cancelUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_FAILURE_URL, this.failureUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_MERCHANT, this.merchant));
		nameValuePairs.add(new BasicNameValuePair(SPH_REQUEST_ID, PaymentHighwayUtility.createRequestId()));
		nameValuePairs.add(new BasicNameValuePair(SPH_SUCCESS_URL, this.successUrl));
		nameValuePairs.add(new BasicNameValuePair(SPH_TIMESTAMP, PaymentHighwayUtility.getUtcTimestamp()));
		
		// sort alphabetically per key
		PaymentHighwayUtility.sortParameters(nameValuePairs);

		// create signature
		String addCardUri = "/form/view/add_card";
		String signature = this.createSignature(addCardUri, nameValuePairs);
		
		nameValuePairs.add(new BasicNameValuePair(LANGUAGE, this.language));
		nameValuePairs.add(new BasicNameValuePair(SIGNATURE, signature));
		
		return new FormContainer(this.method, this.baseUrl, addCardUri,
				nameValuePairs);
	}

	/**
	 * Get parameters for Payment request.
	 *
	 * @param amount
	 * @param currency
	 * @param orderId
	 * @param description
	 * @return FormContainer
	 */
	public FormContainer generatePaymentParameters(String amount, String currency, String orderId, String description) {

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

		// sort alphabetically per key
		PaymentHighwayUtility.sortParameters(nameValuePairs);

		// create signature
		String payWithCardUri = "/form/view/pay_with_card";
		String signature = this.createSignature(payWithCardUri, nameValuePairs);
		
		nameValuePairs.add(new BasicNameValuePair(DESCRIPTION, description));
		nameValuePairs.add(new BasicNameValuePair(LANGUAGE, language));
		nameValuePairs.add(new BasicNameValuePair(SIGNATURE, signature));

		return new FormContainer(this.method, this.baseUrl, payWithCardUri, nameValuePairs);
	}

	/**
	 * Get parameters for Add Card and Pay request.
	 * 
	 * @param amount
	 * @param currency
	 * @param orderId
	 * @param description
	 * @return FormContainer
	 */
	public FormContainer generateAddCardAndPaymentParameters(String amount, String currency, String orderId, String description) {

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

		// sort alphabetically per key
		PaymentHighwayUtility.sortParameters(nameValuePairs);

		// create signature
		String addCardAndPayUri = "/form/view/add_and_pay_with_card";
		String signature = this.createSignature(addCardAndPayUri, nameValuePairs);
		
		nameValuePairs.add(new BasicNameValuePair(DESCRIPTION, description));
		nameValuePairs.add(new BasicNameValuePair(LANGUAGE, language));
		nameValuePairs.add(new BasicNameValuePair(SIGNATURE, signature));

		return new FormContainer(method, this.baseUrl, addCardAndPayUri, nameValuePairs);
	}

	/**
	 * Create a secure signature
	 *
	 * @param uri
	 * @return String signature
	 */
	private String createSignature(String uri, List<NameValuePair> nameValuePairs) {

		nameValuePairs = PaymentHighwayUtility.parseSphParameters(nameValuePairs);
		SecureSigner ss = new SecureSigner(this.signatureKeyId, this.signatureSecret);
		return ss.createSignature(this.method, uri, nameValuePairs, "");
	}
}
