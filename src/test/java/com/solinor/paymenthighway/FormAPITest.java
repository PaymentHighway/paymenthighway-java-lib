/**
 * 
 */
package com.solinor.paymenthighway;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * PaymentHighway Form API tests
 * 
 * @author Tero Kallio <tero.kallio@solinor.com>
 */
public class FormAPITest {

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
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddCard() {
		
		// required fields
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("sph-account", "test"));
        parameters.add(new BasicNameValuePair("sph-amount", "990"));
        parameters.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com/"));
        parameters.add(new BasicNameValuePair("sph-currency", "EUR"));
        parameters.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        parameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        parameters.add(new BasicNameValuePair("sph-order", "1000123A"));
        parameters.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
        parameters.add(new BasicNameValuePair("sph-success-url", "https://www.paymenthighway.fi/"));
        parameters.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        parameters.add(new BasicNameValuePair("language", "EN"));
        
        // required system information and authentication credentials
        // we read this from file, but can be from everywhere
        Properties props = null;
		try {
			props = PaymentHighwayUtility.getProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        String serviceUrl = props.getProperty("service_url");
        String signatureKeyId = props.getProperty("signature_key_id");
        String signatureSecret = props.getProperty("signature_secret");
        
        // create the payment highway service
        FormAPI service = new FormAPI();
        service.setServiceUrl(serviceUrl);
        service.setSignatureKeyId(signatureKeyId);
        service.setSignatureSecret(signatureSecret);
        
        String result = null;
		try {
			result = service.addCard(parameters);
		} catch (IOException e) {
			e.printStackTrace();
		}
        assertTrue(result.contains("Payment Card Information"));
	}
	@Test
	public void testPayWithCard() {
		
		// required fields
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("sph-account", "test"));
        parameters.add(new BasicNameValuePair("sph-amount", "990"));
        parameters.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com/"));
        parameters.add(new BasicNameValuePair("sph-currency", "EUR"));
        parameters.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        parameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        parameters.add(new BasicNameValuePair("sph-order", "1000123A"));
        parameters.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
        parameters.add(new BasicNameValuePair("sph-success-url", "https://www.paymenthighway.fi/"));
        parameters.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        parameters.add(new BasicNameValuePair("language", "EN"));
        parameters.add(new BasicNameValuePair("description", "payment description"));
        
        // required system information and authentication credentials
        // we read this from file, but can be from everywhere
        Properties props = null;
		try {
			props = PaymentHighwayUtility.getProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        String serviceUrl = props.getProperty("service_url");
        String signatureKeyId = props.getProperty("signature_key_id");
        String signatureSecret = props.getProperty("signature_secret");
        
        // create the paymenthighway service
        FormAPI service = new FormAPI();
        service.setServiceUrl(serviceUrl);
        service.setSignatureKeyId(signatureKeyId);
        service.setSignatureSecret(signatureSecret);
        
        String result = null;
		try {
			result = service.payWithCard(parameters);
		} catch (IOException e) {
			e.printStackTrace();
		}
        assertTrue(result.contains("card_number_formatted"));
	}
	@Test
	public void testAddCardAndPay() {
		
		// required fields
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(new BasicNameValuePair("sph-account", "test"));
        parameters.add(new BasicNameValuePair("sph-amount", "990"));
        parameters.add(new BasicNameValuePair("sph-cancel-url", "https://www.solinor.com/"));
        parameters.add(new BasicNameValuePair("sph-currency", "EUR"));
        parameters.add(new BasicNameValuePair("sph-failure-url", "https://www.paymenthighway.fi/"));
        parameters.add(new BasicNameValuePair("sph-merchant", "test_merchantId"));
        parameters.add(new BasicNameValuePair("sph-order", "1000123A"));
        parameters.add(new BasicNameValuePair("sph-request-id", PaymentHighwayUtility.createRequestId()));
        parameters.add(new BasicNameValuePair("sph-success-url", "https://www.paymenthighway.fi/"));
        parameters.add(new BasicNameValuePair("sph-timestamp", PaymentHighwayUtility.getUtcTimestamp()));
        parameters.add(new BasicNameValuePair("language", "EN"));
        parameters.add(new BasicNameValuePair("description", "payment description"));
        
        // required system information and authentication credentials
        // we read this from file, but can be from everywhere
        Properties props = null;
		try {
			props = PaymentHighwayUtility.getProperties();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

        String serviceUrl = props.getProperty("service_url");
        String signatureKeyId = props.getProperty("signature_key_id");
        String signatureSecret = props.getProperty("signature_secret");
        
        // create the paymenthighway service
        FormAPI service = new FormAPI();
        service.setServiceUrl(serviceUrl);
        service.setSignatureKeyId(signatureKeyId);
        service.setSignatureSecret(signatureSecret);
        
        String result = null;
		try {
			result = service.addCardAndPay(parameters);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("add card and pay result="+ result);
        assertTrue(result.contains("Test customer"));
        assertTrue(result.contains("sph-card-form"));
	}
}
