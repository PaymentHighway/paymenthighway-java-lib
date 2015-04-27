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
 * @author tero.kallio
 *
 */
public class FormParameterBuilderTest {

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
		
		String account = "test";
		String merchant = "test_merchantId";
		String amount = "9999";
		String currency = "EUR";
		String orderId = "1000123A";
		String successUrl = "https://www.paymenthighway.fi/";
		String failureUrl = "https://paymenthighway.fi/index-en.html";
		String cancelUrl = "https://solinor.fi";
		String language = "EN";
		
		// create the payment highway request parameters
        FormParameterBuilder formBuilder = 
        		new FormParameterBuilder(this.signatureKeyId, this.signatureSecret);
        List<NameValuePair> nameValuePairs = 
        		formBuilder.getAddCardParameters(account, merchant, amount,
        				currency, orderId, successUrl, failureUrl, cancelUrl, 
        				language);
        
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
		
		String account = "test";
		String merchant = "test_merchantId";
		String amount = "9999";
		String currency = "EUR";
		String orderId = "1000123A";
		String successUrl = "https://www.paymenthighway.fi/";
		String failureUrl = "http://www.solinor.com";
		String cancelUrl = "https://solinor.fi";
		String language = "EN";
		
		// create the payment highway request parameters
        FormParameterBuilder formBuilder = 
        		new FormParameterBuilder(this.signatureKeyId, this.signatureSecret);
        List<NameValuePair> nameValuePairs = 
        		formBuilder.getAddCardParameters(account, merchant, amount,
        				currency, orderId, successUrl, failureUrl, cancelUrl, 
        				language);
        
        // test that Payment Highway accepts this as a request
        FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, 
				this.signatureKeyId, this.signatureSecret);
        
        String response = null;
        try {
			response = formApi.addCardRequest(nameValuePairs);
		} catch (IOException e) {
			e.printStackTrace();
		}
        assertTrue(response.contains("card_number_formatted"));
	}
	@Test
	public void testGetPaymentParameters() {
		
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
        FormParameterBuilder formBuilder = 
        		new FormParameterBuilder(this.signatureKeyId, this.signatureSecret);
        List<NameValuePair> nameValuePairs = 
        		formBuilder.getPaymentParameters(account, merchant, amount,
        				currency, orderId, successUrl, failureUrl, cancelUrl, 
        				language, description);
        
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
	public void testGetPaymentParameters2() {
		
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
        FormParameterBuilder formBuilder = 
        		new FormParameterBuilder(this.signatureKeyId, this.signatureSecret);
        List<NameValuePair> nameValuePairs = 
        		formBuilder.getPaymentParameters(account, merchant, amount,
        				currency, orderId, successUrl, failureUrl, cancelUrl, 
        				language, description);
        
        // test that Payment Highway accepts this as a request
        FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, 
				this.signatureKeyId, this.signatureSecret);
        
        String response = null;
        try {
			response = formApi.paymentRequest(nameValuePairs);
		} catch (IOException e) {
			e.printStackTrace();
		}
        assertTrue(response.contains("card_number_formatted"));
	}
	@Test
	public void testGetAddCardAndPaymentParameters() {
		
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
        FormParameterBuilder formBuilder = 
        		new FormParameterBuilder(this.signatureKeyId, this.signatureSecret);
        List<NameValuePair> nameValuePairs = 
        		formBuilder.getAddCardAndPaymentParameters(account, merchant, amount,
        				currency, orderId, successUrl, failureUrl, cancelUrl, 
        				language, description);
        
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
	public void testGetAddCardAndPaymentParameters2() {
		
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
        FormParameterBuilder formBuilder = 
        		new FormParameterBuilder(this.signatureKeyId, this.signatureSecret);
        List<NameValuePair> nameValuePairs = 
        		formBuilder.getAddCardAndPaymentParameters(account, merchant, amount,
        				currency, orderId, successUrl, failureUrl, cancelUrl, 
        				language, description);
        
        // test that Payment Highway accepts this as a request
        FormAPIConnection formApi = new FormAPIConnection(this.serviceUrl, 
				this.signatureKeyId, this.signatureSecret);
        
        String response = null;
        try {
			response = formApi.addCardAndPayRequest(nameValuePairs);
		} catch (IOException e) {
			e.printStackTrace();
		}
        assertTrue(response.contains("card_number_formatted"));
	}
}
