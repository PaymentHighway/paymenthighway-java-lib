/**
 * 
 */
package com.solinor.paymenthighway;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.solinor.paymenthighway.connect.FormAPIConnection;

/**
 */
public class FormBuilderTest {

	Properties props = null;
	private String serviceUrl;
	private String signatureKeyId;
	private String signatureSecret;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// required system information and authentication credentials
		// we read this from file, but can be from everywhere
		try {
			this.props = PaymentHighwayUtility.getProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.serviceUrl = this.props.getProperty("service_url");
		this.signatureKeyId = this.props.getProperty("signature_key_id");
		this.signatureSecret = this.props.getProperty("signature_secret");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddCardParameters() {

		String method = "POST";
		String account = "test";
		String merchant = "test_merchantId";
		String successUrl = "https://www.paymenthighway.fi/";
		String failureUrl = "https://paymenthighway.fi/index-en.html";
		String cancelUrl = "https://solinor.fi";
		String language = "EN";

		// create the payment highway request parameters
		FormBuilder formBuilder = new FormBuilder(method,
				this.signatureKeyId, this.signatureSecret, account, merchant,
				this.serviceUrl, successUrl, failureUrl, cancelUrl, language);

		FormContainer formContainer = formBuilder.generateAddCardParameters();

		List<NameValuePair> nameValuePairs = formContainer.getFields();

		// test that the result has a signature
		Iterator<NameValuePair> it = nameValuePairs.iterator();
		String signature = null;
		while (it.hasNext()) {
			NameValuePair nameValuePair = it.next();
			String name = nameValuePair.getName();

			if (name.equalsIgnoreCase("signature")) {
				signature = nameValuePair.getValue();
			}
		}
		assertTrue(signature != null);
		assertTrue(signature.startsWith("SPH1"));
	}

	@Test
	public void testAddCardParameters2() {

		String method = "POST";
		String account = "test";
		String merchant = "test_merchantId";
		String successUrl = "https://www.paymenthighway.fi/";
		String failureUrl = "http://www.solinor.com";
		String cancelUrl = "https://solinor.fi";
		String language = "EN";

		// create the payment highway request parameters
		FormBuilder formBuilder = new FormBuilder(method,
				this.signatureKeyId, this.signatureSecret, account, merchant,
				this.serviceUrl, successUrl, failureUrl, cancelUrl, language);

		FormContainer formContainer = formBuilder.generateAddCardParameters();

		// test that Payment Highway accepts this as a request
		FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret);

		String response = null;
		try {
			response = formApi.addCardRequest(formContainer.getFields());
		} catch (IOException e) {
			e.printStackTrace();
		}

		assert response != null;
		assertTrue(response.contains("card_number_formatted"));
		assertTrue(response.contains("Payment Highway"));
	}

	@Test
	public void testPaymentParameters() {

		String method = "POST";
		String account = "test";
		String merchant = "test_merchantId";
		String amount = "9999";
		String currency = "EUR";
		String orderId = "1000123A";
		String successUrl = "https://www.paymenthighway.fi/";
		String failureUrl = "https://paymenthighway.fi/index-en.html";
		String cancelUrl = "https://solinor.fi";
		String language = "EN";
		String description = "this is payment description";

		// create the payment highway request parameters
		FormBuilder formBuilder = new FormBuilder(method,
				this.signatureKeyId, this.signatureSecret, account, merchant,
				this.serviceUrl, successUrl, failureUrl, cancelUrl, language);

		FormContainer formContainer = formBuilder.generatePaymentParameters(
				amount, currency, orderId, description);

		// test that the result has a signature
		Iterator<NameValuePair> it = formContainer.getFields().iterator();
		String signature = null;
		while (it.hasNext()) {
			NameValuePair nameValuePair = it.next();
			String name = nameValuePair.getName();

			if (name.equalsIgnoreCase("signature")) {
				signature = nameValuePair.getValue();
			}
		}
		assertTrue(signature != null);
		assertTrue(signature.startsWith("SPH1"));
	}

	@Test
	public void testGetPaymentParameters2() {

		String method = "POST";
		String account = "test";
		String merchant = "test_merchantId";
		String amount = "9999";
		String currency = "EUR";
		String orderId = "1000123A";
		String successUrl = "https://www.paymenthighway.fi/";
		String failureUrl = "http://www.solinor.com";
		String cancelUrl = "https://solinor.fi";
		String language = "EN";
		String description = "this is payment description";

		// create the payment highway request parameters
		FormBuilder formBuilder = new FormBuilder(method, 
				this.signatureKeyId, this.signatureSecret, account, merchant,
				this.serviceUrl, successUrl, failureUrl, cancelUrl, language);

		FormContainer formContainer = formBuilder.generatePaymentParameters(
				amount, currency, orderId, description);

		// test that Payment Highway accepts this as a request
		FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret);

		String response = null;
		try {
			response = formApi.paymentRequest(formContainer.getFields());
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert response != null;
		assertTrue(response.contains("card_number_formatted"));
	}

	@Test
	public void testGetAddCardAndPaymentParameters() {

		String method = "POST";
		String account = "test";
		String merchant = "test_merchantId";
		String amount = "9999";
		String currency = "EUR";
		String orderId = "1000123A";
		String successUrl = "https://www.paymenthighway.fi/";
		String failureUrl = "https://paymenthighway.fi/index-en.html";
		String cancelUrl = "https://solinor.fi";
		String language = "EN";
		String description = "this is payment description";

		// create the payment highway request parameters
		FormBuilder formBuilder = new FormBuilder(method, 
				this.signatureKeyId, this.signatureSecret, account, merchant,
				this.serviceUrl, successUrl, failureUrl, cancelUrl, language);

		FormContainer formContainer = formBuilder
				.generateAddCardAndPaymentParameters(amount, currency, orderId,
						description);

		// test that the result has a signature
		Iterator<NameValuePair> it = formContainer.getFields().iterator();
		String signature = null;
		while (it.hasNext()) {
			NameValuePair nameValuePair = it.next();
			String name = nameValuePair.getName();

			if (name.equalsIgnoreCase("signature")) {
				signature = nameValuePair.getValue();
			}
		}
		assertTrue(signature != null);
		assertTrue(signature.startsWith("SPH1"));
	}

	@Test
	public void testGetAddCardAndPaymentParameters2() {

		String method = "POST";
		String account = "test";
		String merchant = "test_merchantId";
		String amount = "9999";
		String currency = "EUR";
		String orderId = "1000123A";
		String successUrl = "https://www.paymenthighway.fi/";
		String failureUrl = "http://www.solinor.com";
		String cancelUrl = "https://solinor.fi";
		String language = "EN";
		String description = "this is payment description";

		// create the payment highway request parameters
		FormBuilder formBuilder = new FormBuilder(method, 
				this.signatureKeyId, this.signatureSecret, account, merchant,
				this.serviceUrl, successUrl, failureUrl, cancelUrl, language);

		FormContainer formContainer = formBuilder
				.generateAddCardAndPaymentParameters(amount, currency, orderId,
						description);

		// test that Payment Highway accepts this as a request
		FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl,
				this.signatureKeyId, this.signatureSecret);

		String response = null;
		try {
			response = formApi.addCardAndPayRequest(formContainer
					.getFields());
		} catch (IOException e) {
			e.printStackTrace();
		}
		assert response != null;
		assertTrue(response.contains("card_number_formatted"));
	}
}
